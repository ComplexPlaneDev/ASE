#include <iostream>
#include <vector>
#include <string>
#include <functional>
#include <queue>
#include <chrono>
#include <thread>

// -------------------------------
// 1. Domain model
// -------------------------------

struct Entity {
    std::string id;
    float x, y;
};

// -------------------------------
// 2. Define closure type
// -------------------------------

using Task = std::function<void()>;

// -------------------------------
// 3. Task scheduler
// -------------------------------

class TaskScheduler {
public:
    void scheduleTask(int delayMs, Task task) {
        tasks.push({delayMs, task});
    }

    void run() {
        while (!tasks.empty()) {
            auto [delay, task] = tasks.front();
            tasks.pop();

            std::cout << "Waiting " << delay << "ms before executing...\n";
            std::this_thread::sleep_for(std::chrono::milliseconds(delay));
            task();
        }
    }

private:
    struct ScheduledTask {
        int delayMs;
        Task task;
    };
    std::queue<ScheduledTask> tasks;
};

int main(int, char **) {
    TaskScheduler scheduler;

    // Closure 1: Spawn entity after delay
    Entity enemy1{"Enemy-001", 10.0f, 20.0f};
    scheduler.scheduleTask(1000, [enemy = enemy1]() {
        std::cout << "Spawning " << enemy.id
                  << " at (" << enemy.x << ", " << enemy.y << ")\n";
    });

    // Closure 2: Play sound effect after delay
    std::string soundFile = "explosion.wav";
    scheduler.scheduleTask(500, [sound = soundFile]() {
        std::cout << "Playing sound: " << sound << "\n";
    });

    // Closure 3: Trigger event with mutable state
    int score = 0;
    scheduler.scheduleTask(750, [&score]() {
        score += 100;
        std::cout << "Score updated: +" << 100
                  << " → Total: " << score << "\n";
    });

    // Run the scheduler
    std::cout << "Starting task scheduler...\n";
    scheduler.run();
}
