/**
 * Prototype Interface: Shape
 *
 * Declares the clone() contract that all concrete prototypes must implement.
 *
 * KEY INSIGHT: The client can duplicate any Shape without knowing
 * its concrete class. It just calls shape.clone() and gets
 * an independent copy — no constructor, no "new ConcreteClass(...)".
 *
 * WHY NOT just use Object.clone()?
 *  - Object.clone() is shallow, requires Cloneable marker, and returns Object.
 *  - A custom clone() method gives us type safety, deep-copy control,
 *    and lets us keep constructors private if needed.
 */
public abstract class Shape {

    // Common state shared by all shapes
    private String color;
    private int x;
    private int y;

    // Default constructor
    public Shape() {}

    /**
     * Copy constructor — used by subclasses in their clone() implementation.
     * This is the cleanest way to implement deep copy in Java.
     */
    protected Shape(Shape source) {
        this.color = source.color;
        this.x = source.x;
        this.y = source.y;
    }

    /**
     * The PROTOTYPE METHOD — subclasses return a deep copy of themselves.
     * Return type is Shape (covariant return types make it type-safe).
     */
    public abstract Shape clone();

    // --- Getters and Setters ---

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    /**
     * Two shapes are "equal" if they have the same field values,
     * even if they are different object instances.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Shape other)) return false;
        return x == other.x
                && y == other.y
                && (color != null ? color.equals(other.color) : other.color == null);
    }

    @Override
    public int hashCode() {
        int result = color != null ? color.hashCode() : 0;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }
}
