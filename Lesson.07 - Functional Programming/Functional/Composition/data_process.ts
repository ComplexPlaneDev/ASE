#!/usr/bin/env ts-node

// -------------------------------
// 1. Define types
// -------------------------------

interface RawUser {
    id: string;
    name: string;
    email: string;
    phone?: string;
}

type Result<T> = { success: true; value: T } | { success: false; error: string };

// -------------------------------
// 2. Helper Functions
// -------------------------------

// Compose utility: right-to-left application
const compose =
    <A, B, C>(f: (b: B) => C, g: (a: A) => B) =>
    (x: A): C =>
        f(g(x));

// Pipe utility: left-to-right application
const pipe =
    <A, B, C>(f: (a: A) => B, g: (b: B) => C) =>
    (x: A): C =>
        g(f(x));

type Func = (...args: any[]) => any;
const advPipe = <T extends Func, U extends Func[], R extends Func>
    (...fns: [T, ...U, R]): (...args: Parameters<T>) => ReturnType<R> =>
        fns.reduce((f, g) =>
            (...args: [...Parameters<T>]) => g(f(...args)));

// -------------------------------
// 3. Step 1: Clean Input
// -------------------------------

const cleanInput = (user: RawUser): RawUser => ({
    id: String(user.id || '').trim(),
    name: String(user.name || '').trim(),
    email: String(user.email || '').trim(),
    phone: String(user.phone)?.trim()
});

// -------------------------------
// 4. Step 2: Validate Required Fields
// -------------------------------

const validateUser = (user: RawUser): Result<RawUser> => {
    if (!user.id) return { success: false, error: 'ID is required' };
    if (!user.name) return { success: false, error: 'Name is required' };
    if (!user.email) return { success: false, error: 'Email is required' };
    return { success: true, value: user };
};

// -------------------------------
// 5. Step 3: Format Phone Number
// -------------------------------

const formatPhone = (user: RawUser): RawUser => {
    const phone = user.phone
        ? user.phone.replace(/[^0-9]/g, '') // remove non-digits
        : '';
    return { ...user, phone };
};

// -------------------------------
// 6. Step 4: Mask Sensitive Info
// -------------------------------

const maskSensitiveInfo = (user: RawUser): Omit<RawUser, 'id'> => {
    const maskedEmail = user.email.replace(/(.*)@(.*)/, '****@$2');
    return {
        name: user.name,
        email: maskedEmail,
        phone: user.phone,
    };
};

// -------------------------------
// 7. Build Reusable Pipelines Using Function Composition
// -------------------------------

// Option 1: Left-to-right with pipe
const processUser =
    pipe(
        cleanInput,
        pipe(
            validateUser,
            pipe(
                (result) => {
                    if (!result.success) {
                        throw new Error(result.error);
                    }

                    return result.value;
                },
                pipe(
                    formatPhone,
                    maskSensitiveInfo
                )
            )
        )
    )
;

const advProcessUser =
    advPipe(
        cleanInput,
        validateUser,
        (result) => {
            if (!result.success) {
                throw new Error(result.error);
            }

            return result.value;
        },
        formatPhone,
        maskSensitiveInfo
    )
;

// Option 2: Right-to-left with compose
const transformUser =
    compose(
        maskSensitiveInfo,
        compose(
            formatPhone,
            compose(
                (result: Result<RawUser>) => {
                    if (!result.success) {
                        throw new Error(result.error);
                    }

                    return result.value;
                },
                compose(
                    validateUser,
                    cleanInput
                )
            )
        )
    )
;

// -------------------------------
// 8. Try It Out
// -------------------------------

const rawData = {
    id: ' 123 ',
    name: ' Alice Smith ',
    email: ' alice@example.com ',
    phone: ' (555) 123-4567 ',
};

try {
    const cleanedUser = cleanInput(rawData);
    const validatedUser = validateUser(cleanedUser);
    if (!validatedUser.success) {
        throw new Error(validatedUser.error);
    }

    const formattedUser = formatPhone(validatedUser.value);
    const maskedUser = maskSensitiveInfo(formattedUser);

    console.log('Manually processed user:\n', maskedUser, "\n");

    // Or use composed version
    console.log('Using composed transformUser:\n', transformUser(rawData), "\n");

    // Or use piped version
    console.log('Using piped processUser:\n', processUser(rawData), "\n");

    console.log('Using advanced piped processUser:\n', advProcessUser(rawData), "\n");
} catch (error) {
    console.error('Processing failed:', (error as Error).message);
}