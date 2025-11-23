#!/usr/bin/env ts-node

// -------------------------------
// 1. Define the type for log handlers
// -------------------------------

type LogHandler = (level: string, message: string) => void;

// -------------------------------
// 2. Define reusable logging functions (functions as values)
// -------------------------------

const logToConsole: LogHandler = (level, message) => {
    console.log(`[${level.toUpperCase()}] ${new Date().toISOString()} - ${message}`);
};

const logToFile: LogHandler = (level, message) => {
    // Simulating writing to a file
    console.warn(`[FILE LOG] [${level.toUpperCase()}] ${message}`);
};

const logToAPI: LogHandler = (level, message) => {
    // Simulating sending log to remote service
    console.info(`[SEND TO API] [${level.toUpperCase()}] ${message}`);
};

// -------------------------------
// 3. Store handlers in a registry (functions stored in data structures)
// -------------------------------

const logHandlers: Record<string, LogHandler> = {
    console: logToConsole,
    file: logToFile,
    api: logToAPI
};

// -------------------------------
// 4. Function that accepts another function as argument (higher-order function)
// -------------------------------

const createLogger = (handler: LogHandler): LogHandler => {
    return (level, message) => {
        handler(level, message);
    };
};

// -------------------------------
// 5. Configure loggers based on environment
// -------------------------------

const devLogger = createLogger(logHandlers.console);
const prodLogger = createLogger(logHandlers.api);

// -------------------------------
// 6. Optional: Function to dynamically switch handler at runtime
// -------------------------------

const setCustomLogger = (handlerName: string): LogHandler => {
    const handler = logHandlers[handlerName];
    if (!handler) {
        throw new Error(`Handler "${handlerName}" not found.`);
    }
    return createLogger(handler);
};

// -------------------------------
// 7. Usage Examples
// -------------------------------

devLogger('info', 'User logged in successfully.');
prodLogger('error', 'Database connection failed.');

const alertLogger = setCustomLogger('file');
alertLogger('warn', 'Disk space is low!');