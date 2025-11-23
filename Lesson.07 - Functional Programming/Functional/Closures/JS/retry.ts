#!/usr/bin/env ts-node

// -------------------------------
// 1. Define types
// -------------------------------

type RetryCondition = (error: any, attempt: number) => boolean;
type DelayStrategy = (attempt: number) => number;

interface RetryPolicy {
    shouldRetry: RetryCondition;
    getDelay: DelayStrategy;
}

type Fetcher<T> = () => Promise<T>;

// -------------------------------
// 2. Closure-based retry policy factory
// -------------------------------

const createRetryPolicy = (
    maxRetries: number,
    baseDelayMs: number,
    condition: (error: any) => boolean
): RetryPolicy => {
    let attemptCount = 0;

    const resetAttempt = () => { attemptCount = 0; };

    return {
        // Captures `maxRetries`, `condition`, and mutates `attemptCount`
        shouldRetry: (error, attempt) => {
            if (attempt > maxRetries) return false;
            return condition(error);
        },

        // Captures `baseDelayMs` and returns increasing delay per attempt
        getDelay: (attempt) => {
            return Math.min(baseDelayMs * Math.pow(2, attempt), 5000); // cap at 5s
        }
    };
};

// Retry only on network errors
const retryOnNetworkError = (maxRetries = 3, baseDelayMs = 1000) =>
    createRetryPolicy(
        maxRetries,
        baseDelayMs,
        (err) => err.message.includes('Network') || err.status === 503
    );

// Retry only on rate limit errors
const retryOnRateLimit = (maxRetries = 5, baseDelayMs = 500) =>
    createRetryPolicy(
        maxRetries,
        baseDelayMs,
        (err) => err.status === 429
    );

// Custom retry policy with dynamic behavior
const customRetryPolicy = (config: {
    maxRetries: number,
    baseDelayMs: number,
    retryIf: (err: any) => boolean
}) => {
    let attemptCount = 0;

    return {
        shouldRetry: (error: any) => {
            if (attemptCount >= config.maxRetries) return false;
            if (!config.retryIf(error)) return false;

            attemptCount++;
            return true;
        },
        getDelay: () => Math.min(config.baseDelayMs * Math.pow(2, attemptCount), 10000)
    };
};

// Retry-aware fetch wrapper
const withRetry = <T>(fetchFn: Fetcher<T>, retryPolicy: RetryPolicy) => {
    return async (): Promise<T> => {
        let attempt = 0;

        while (true) {
            try {
                return await fetchFn();
            } catch (error) {
                attempt++;
                if (retryPolicy.shouldRetry(error, attempt)) {
                    const delay = retryPolicy.getDelay(attempt);
                    console.log(`Attempt ${attempt}: Retrying in ${delay}ms...`);
                    await new Promise(res => setTimeout(res, delay));
                } else {
                    throw error;
                }
            }
        }
    };
};

// Fake API call that fails sometimes
let attemptCount = 0;
const faultyFetch = async <T>(): Promise<T> => {
    attemptCount++;

    if (attemptCount % 3 !== 0) {
        console.log("Network error");
        throw new Error("Network error");
    }

    console.log("Success!");
    return { data: "OK" } as T;
};

// Create retry policies using closures
const networkRetry = retryOnNetworkError(3, 500);
const rateLimitRetry = retryOnRateLimit(5, 1000);

// Apply them to the fetch function
const retryingFetcher = withRetry(faultyFetch, networkRetry);

// Run it
(async () => {
    try {
        await retryingFetcher();
    } catch (err) {
        console.error("All retries failed:", (err as Error).message);
    }
})();