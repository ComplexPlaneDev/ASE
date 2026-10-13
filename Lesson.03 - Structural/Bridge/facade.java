class FooA {
    void run() {

    }

    void play() {

    }
}

class FooB {
    void save() {

    }

    void load() {

    }
}

public class facade {
    private FooA fooA = new FooA();
    private FooB fooB = new FooB();

    public void process() {
        fooA.run();
        fooB.save();
    }
}

int main() {
    facade f = new facade();

    f.process();
}
