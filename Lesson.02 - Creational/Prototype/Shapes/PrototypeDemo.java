import java.util.ArrayList;
import java.util.List;

/**
 * Client / Demo
 *
 * Demonstrates:
 *  1. Basic cloning — clone a shape and modify the copy independently
 *  2. Deep cloning — clone a CompoundShape (object graph)
 *  3. Clone independence — original and copy are fully decoupled
 *  4. Prototype Registry — pre-configured templates cloned on demand
 *  5. Polymorphic cloning — clone through the Shape interface
 */
public class PrototypeDemo {

    public static void main(String[] args) {
        System.out.println("========= Prototype Pattern Demo =========\n");

        // ─── 1. Basic cloning ──────────────────────────────────────────
        System.out.println(">>> 1. Basic Cloning\n");

        Circle original = new Circle();
        original.setColor("Red");
        original.setX(10);
        original.setY(20);
        original.setRadius(50);

        Circle cloned = original.clone();
        cloned.setColor("Blue");    // modify the clone
        cloned.setX(100);           // move it somewhere else

        System.out.println("Original: " + original);
        System.out.println("Clone:    " + cloned);
        System.out.println("Same object?  " + (original == cloned));         // false
        System.out.println("Equal values? " + original.equals(cloned));      // false (different color/x)

        // ─── 2. Deep cloning (object graph) ────────────────────────────
        System.out.println("\n>>> 2. Deep Cloning — CompoundShape\n");

        CompoundShape logo = new CompoundShape();
        logo.setName("Company Logo");
        logo.setColor("Transparent");
        logo.setX(0);
        logo.setY(0);

        Circle dot = new Circle();
        dot.setColor("Green");
        dot.setRadius(5);
        dot.setX(50);
        dot.setY(50);

        Rectangle frame = new Rectangle();
        frame.setColor("Black");
        frame.setWidth(200);
        frame.setHeight(100);
        frame.setX(0);
        frame.setY(0);

        logo.addChild(dot);
        logo.addChild(frame);

        // Clone the entire compound shape
        CompoundShape logoCopy = logo.clone();
        logoCopy.setName("Logo Copy (modified)");

        // Modify a child in the COPY — original must remain unaffected
        logoCopy.getChildren().get(0).setColor("Yellow");  // change dot color in copy

        System.out.println("Original:");
        System.out.println(logo);
        System.out.println("\nClone (modified child):");
        System.out.println(logoCopy);
        System.out.println("\nOriginal's dot still Green? "
                + logo.getChildren().get(0).getColor().equals("Green"));  // true!

        // ─── 3. Polymorphic cloning ────────────────────────────────────
        System.out.println("\n>>> 3. Polymorphic Cloning (through Shape interface)\n");

        List<Shape> shapes = new ArrayList<>();
        shapes.add(original);   // Circle
        shapes.add(frame);      // Rectangle
        shapes.add(logo);       // CompoundShape

        // Clone all shapes without knowing their concrete types
        List<Shape> clonedShapes = new ArrayList<>();
        for (Shape shape : shapes) {
            clonedShapes.add(shape.clone());  // polymorphic dispatch
        }

        System.out.println("Original list:");
        for (Shape s : shapes) System.out.println("  " + s.getClass().getSimpleName() + ": " + s);
        System.out.println("\nCloned list (independent copies):");
        for (Shape s : clonedShapes) System.out.println("  " + s.getClass().getSimpleName() + ": " + s);

        // ─── 4. Prototype Registry ─────────────────────────────────────
        System.out.println("\n>>> 4. Prototype Registry — pre-configured templates\n");

        ShapeRegistry registry = new ShapeRegistry();

        // Pre-configure reusable templates
        Circle smallDot = new Circle();
        smallDot.setColor("Gray");
        smallDot.setRadius(3);

        Circle bigCircle = new Circle();
        bigCircle.setColor("Blue");
        bigCircle.setRadius(100);

        Rectangle card = new Rectangle();
        card.setColor("White");
        card.setWidth(300);
        card.setHeight(200);

        registry.register("small-dot", smallDot);
        registry.register("big-circle", bigCircle);
        registry.register("card", card);

        registry.printRegistry();

        // Get clones from registry and customize
        System.out.println("\nCloning from registry and customizing:");

        Shape dot1 = registry.get("small-dot");
        dot1.setX(10);
        dot1.setY(10);

        Shape dot2 = registry.get("small-dot");
        dot2.setX(30);
        dot2.setY(30);

        Shape myCard = registry.get("card");
        myCard.setColor("LightBlue");
        myCard.setX(0);
        myCard.setY(0);

        System.out.println("  dot1: " + dot1);
        System.out.println("  dot2: " + dot2);
        System.out.println("  card: " + myCard);
        System.out.println("\n  dot1 == dot2? " + (dot1 == dot2));           // false
        System.out.println("  dot1.equals(dot2)? " + dot1.equals(dot2));    // false (different x,y)
    }
}
