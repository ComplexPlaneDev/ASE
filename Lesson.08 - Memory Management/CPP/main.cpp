#include <iostream>
#include <cstdio>

using namespace std;

class FileScope {
    FILE *_fp;

public:
    explicit FileScope(FILE *fp) : _fp(fp) {}

    FileScope(const FileScope &) = delete;
    FileScope &operator=(const FileScope &) = delete;

    FileScope(FileScope &&other) noexcept : _fp(other._fp) {
        other._fp = nullptr;
    }
    FileScope &operator=(FileScope &&other) noexcept {
        if (this != &other) {
            _fp = other._fp;
            other._fp = nullptr;
        }
        return *this;
    }

    ~FileScope() {
        if (_fp) {
            fclose(_fp);
        }
    }
};

class File {
    FILE *_fp;

public:
    explicit File(const char *name, const char *mode) : _fp(fopen(name, mode)) {
        if (!_fp) {
            throw runtime_error("Failed to open file");
        }
    }

    File(const File &) = delete;
    File &operator=(const File &) = delete;

    File(File &&other) noexcept : _fp(other._fp) {
        other._fp = nullptr;
    }
    File &operator=(File &&other) noexcept {
        if (this != &other) {
            _fp = other._fp;
            other._fp = nullptr;
        }
        return *this;
    }

    ~File() {
        if (_fp) {
            fclose(_fp);
        }
    }

    operator FILE*() const {
        return _fp;
    }
};

int main(int, char **) {
    FILE *fp = fopen("test.out", "a");
    FileScope fileScope(fp);

    FileScope fileScope2 = std::move(fileScope);

    fprintf(fp, "Hello, World!\n");

    File file("test2.out", "a");
    fprintf(file, "Hello, World from File!\n");

    return 0;
}
