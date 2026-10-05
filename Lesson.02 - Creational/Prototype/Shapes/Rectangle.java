/**
 * Concrete Prototype: Rectangle
 *
 * Knows how to clone itself, producing an independent deep copy.
 */
public class Rectangle extends Shape {

    private int width;
    private int height;

    public Rectangle() {}

    /**
     * Copy constructor — copies all fields including parent's.
     */
    private Rectangle(Rectangle source) {
        super(source);          // copies color, x, y
        this.width = source.width;
        this.height = source.height;
    }

    @Override
    public Rectangle clone() {
        return new Rectangle(this);
    }

    // --- Rectangle-specific ---

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof Rectangle other)) return false;
        return width == other.width && height == other.height;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        return String.format("Rectangle{color='%s', x=%d, y=%d, width=%d, height=%d}",
                getColor(), getX(), getY(), width, height);
    }
}
