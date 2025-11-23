#!/usr/bin/env ts-node

// -------------------------------
// 1. Define types
// -------------------------------

type NotificationChannel = 'email' | 'sms' | 'push';

interface CustomNotification {
    recipient: string;
    message: string;
    channel: NotificationChannel;
}

type NotificationSender = (notification: CustomNotification) => void;

// -------------------------------
// 2. Base notification handlers (functions as values)
// -------------------------------

const sendEmail: NotificationSender = ({ recipient, message }) => {
    console.log(`Sending email to ${recipient}: "${message}"`);
};

const sendSMS: NotificationSender = ({ recipient, message }) => {
    console.log(`Sending SMS to ${recipient}: "${message}"`);
    if (Math.random() < 0.5) {
        throw new Error("SMS sending failed!"); // Simulate a failure
    }
};

const sendPush: NotificationSender = ({ recipient, message }) => {
    console.log(`Sending push to ${recipient}: "${message}"`);
};

// -------------------------------
// 3. Higher-order functions that enhance behavior
// -------------------------------

// Logs before and after sending
const withLogging = (sender: NotificationSender): NotificationSender =>
    (notification) => {
        try {
            console.log(`Preparing to send notification via ${notification.channel.toUpperCase()}`);
            sender(notification);
            console.log(`Notification sent\n`);
        } catch (error) {
            console.error(`Error sending notification: ${(error as Error).message}\n`);
        }
    };

// Simulates retry logic
const withRetry = (sender: NotificationSender, retries = 3): NotificationSender =>
    (notification) => {
        let attempt = 0;
        const maxRetries = retries;

        while (attempt < maxRetries) {
            try {
                console.log(`Attempt ${attempt + 1} to send notification...`);
                sender(notification);
                return;
            } catch (error) {
                if (++attempt >= maxRetries) {
                    console.error(`Failed to send notification after ${maxRetries} attempts.`);
                    throw error; // Rethrow after max retries;
                }
            }
        }
    };

// Rate limiter middleware
const withRateLimit = (sender: NotificationSender, limit = 5): NotificationSender => {
    let count = 0;
    const max = limit;

    return (notification) => {
        if (++count >= max) {
            console.warn(`Rate limit reached. Cannot send more notifications.`);
            return;
        }

        sender(notification);
    };
};

// -------------------------------
// 4. Select appropriate sender based on channel
// -------------------------------

const getNotificationSender = (channel: NotificationChannel): NotificationSender => {
    switch (channel) {
        case 'email':
            return sendEmail;
        case 'sms':
            return sendSMS;
        case 'push':
            return sendPush;
        default:
            throw new Error(`Unsupported channel: ${channel}`);
    }
};

// -------------------------------
// 5. Build configurable dispatchers using HOFs
// -------------------------------

const sendEmailWithLogging = withLogging(sendEmail);
const sendSMSWithRetryAndLogging = withLogging(withRetry(sendSMS, 2));
const sendPushWithRateLimit = withRateLimit(sendPush);

// -------------------------------
// 6. Usage Examples
// -------------------------------

const notif1: CustomNotification = {
    recipient: "alice@example.com",
    message: "Your order has shipped!",
    channel: "email"
};

const notif2: CustomNotification = {
    recipient: "+1234567890",
    message: "Your promo code is active.",
    channel: "sms"
};

const notif3: CustomNotification = {
    recipient: "user_789",
    message: "New notification from your app.",
    channel: "push"
};

// Try sending multiple messages to trigger rate limit
sendEmailWithLogging(notif1);
sendSMSWithRetryAndLogging(notif2);
sendPushWithRateLimit(notif3);
sendPushWithRateLimit(notif3);
sendPushWithRateLimit(notif3);
sendPushWithRateLimit(notif3);
sendPushWithRateLimit(notif3); // This one should hit the limit