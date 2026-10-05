/**
 * Concrete Prototype: Circle
 *
 * Knows how to clone itself, producing an independent deep copy.
 */
public class Circle extends Shape {

    private int radius;

    public Circle() {}

    /**
     * Copy constructor — copies all fields from the source,
     * including those in the parent class.
     */
    private Circle(Circle source) {
        super(source);          // copies color, x, y
        this.radius = source.radius;
    }

    /**
     * Prototype method — returns a deep copy via the copy constructor.
     */
    @Override
    public Circle clone() {
        return new Circle(this);
    }

    // --- Circle-specific ---

    public int getRadius() { return radius; }
    public void setRadius(int radius) { this.radius = radius; }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof Circle other)) return false;
        return radius == other.radius;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + radius;
    }

    @Override
    public String toString() {
        return String.format("Circle{color='%s', x=%d, y=%d, radius=%d}",
                getColor(), getX(), getY(), radius);
    }
}
