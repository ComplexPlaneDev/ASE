#include <iostream>
#include <cstddef>
#include <cstring>

using namespace std;

class SimpleObject {
private:
    static const size_t MAX_OBJECTS = 5;
    static char _pool[];           // Memory pool
    static bool _inUse[];          // Track usage
    int _id;

public:
    explicit SimpleObject(int id) : _id(id) {
        cout << "Constructing object with ID: " << _id << endl;
    }

    ~SimpleObject() {
        cout << "Destroying object with ID: " << _id << endl;
    }

    void* operator new(std::size_t size) {
        cout << "Attempting to allocate memory (size: " << size << " bytes)" << endl;

        for (size_t i = 0; i < MAX_OBJECTS; ++i) {
            if (!_inUse[i]) {
                _inUse[i] = true;
                cout << "Allocated index " << i << endl;
                return _pool + i * sizeof(SimpleObject);
            }
        }

        cerr << "Memory pool full!" << endl;
        throw std::bad_alloc();
    }

    void operator delete(void* ptr, std::size_t size) {
        cout << "Deallocating memory (size: " << size << " bytes)" << endl;

        for (size_t i = 0; i < MAX_OBJECTS; ++i) {
            if (ptr == _pool + i * sizeof(SimpleObject)) {
                _inUse[i] = false;
                cout << "Freed index " << i << endl;
                return;
            }
        }

        cerr << "Invalid pointer passed to delete" << endl;
    }

    void print() const {
        cout << "Object ID: " << _id << endl;
    }
};

char SimpleObject::_pool[MAX_OBJECTS * sizeof(SimpleObject)];
bool SimpleObject::_inUse[MAX_OBJECTS] = {};

int main() {
    try {
        SimpleObject* obj1 = new SimpleObject(1);
        SimpleObject* obj2 = new SimpleObject(2);

        obj1->print();
        obj2->print();

        delete obj1;
        delete obj2;

        SimpleObject* obj3 = new SimpleObject(3);
        obj3->print();

        SimpleObject* many[10];
        for (int i = 0; i < 10; ++i) {
            many[i] = new SimpleObject(i);
        }

    } catch (const bad_alloc &) {
        cerr << "Caught bad_alloc exception" << endl;
    }

    return 0;
}