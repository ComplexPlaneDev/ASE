(async function demo() {

    async function callGC() {
        for (let i = 0; i < 5; i++) {
            global.gc();
            await new Promise((res) => setTimeout(res, 1000));
        }
    }

    const registry = new FinalizationRegistry((heldValue) => {
        console.log(`\nFinalizationRegistry callback: ${heldValue} was collected`);
    });

    class A {
        constructor() {
            this.buf = Buffer.alloc(1 << 20);
        }
    }

    console.log(">>>>>>>>>>> Plain allocation <<<<<<<<<<");

    let a = new A();
    registry.register(a, "A instance");

    a = null;
    await callGC();

    class BadA {
        static refs = [];

        constructor() {
            this.buf = Buffer.alloc(1 << 20);
        }

        addRef(ref) {
            BadA.refs.push(ref);
        }
    }

    class BadB {
        constructor(badA) {
            this.badA = badA;
            this.badA.addRef(this);
        }
    }

    console.log(">>>>>>>>>>> Bad allocation <<<<<<<<<<");

    let badA = new BadA();
    let badB = new BadB(badA);
    registry.register(badA, "badA instance");
    registry.register(badB, "badB instance");

    badA = null;
    badB = null;
    await callGC();

    class NiceA {
        static weakRefs = [];

        constructor() {
            this.buf = Buffer.alloc(1 << 20);
        }

        addWeakRef(ref) {
            NiceA.weakRefs.push(new WeakRef(ref));
        }
    }

    class NiceB {
        constructor(niceA) {
            this.niceA = niceA;
            this.niceA.addWeakRef(this);
        }
    }

    console.log(">>>>>>>>>>> Nice allocation <<<<<<<<<<");

    let niceA = new NiceA();
    let niceB = new NiceB(niceA);
    registry.register(niceA, "niceA instance");
    registry.register(niceB, "niceB instance");

    niceA = null;
    niceB = null;
    await callGC();
})();
