#include <memory>
#include <iostream>
#include <vector>

using namespace std;

class BadB;

class BadA {
    shared_ptr<BadB> _b;

public:
    explicit BadA() {
        cout << "BadA allocated" << endl;
    }

    ~BadA() {
        cout << "BadA deallocated" << endl;
    }

    void setB(const shared_ptr<BadB> &b) {
        _b = b;
    }
};

class BadB {
    shared_ptr<BadA> _a;

public:
    explicit BadB(const shared_ptr<BadA> &a) : _a(a) {
        cout << "BadB allocated" << endl;
    }

    ~BadB() {
        cout << "BadB deallocated" << endl;
    }
};

class NiceB;

class NiceA {
    shared_ptr<NiceB> _b;

public:
    explicit NiceA() {
        cout << "NiceA allocated" << endl;
    }

    ~NiceA() {
        cout << "NiceA deallocated" << endl;
    }

    void setB(const shared_ptr<NiceB> &b) {
        _b = b;
    }
};

class NiceB {
    weak_ptr<NiceA> _a;

public:
    explicit NiceB(const shared_ptr<NiceA> &a) : _a(a) {
        cout << "NiceB allocated" << endl;
    }

    ~NiceB() {
        cout << "NiceB deallocated" << endl;
    }
};

int main(int, char **) {

    shared_ptr<BadA> badA = make_shared<BadA>();
    shared_ptr<BadB> badB = make_shared<BadB>(badA);
    badA->setB(badB);

    badA.reset();
    badB.reset();

    shared_ptr<NiceA> niceA = make_shared<NiceA>();
    shared_ptr<NiceB> niceB = make_shared<NiceB>(niceA);
    niceA->setB(niceB);

    niceA.reset();
    niceB.reset();

    getchar();

    return 0;
}