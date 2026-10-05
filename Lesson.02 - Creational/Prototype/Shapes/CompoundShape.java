import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Prototype: CompoundShape
 *
 * A shape composed of other shapes (Composite + Prototype).
 * Demonstrates DEEP CLONING of object graphs:
 * when cloned, all child shapes are also cloned recursively.
 */
public class CompoundShape extends Shape {

    private String name;
    private List<Shape> children;

    public CompoundShape() {
        this.children = new ArrayList<>();
    }

    /**
     * Copy constructor — performs a DEEP COPY of the children list.
     * Each child shape is cloned independently.
     */
    private CompoundShape(CompoundShape source) {
        super(source);          // copies color, x, y
        this.name = source.name;
        this.children = new ArrayList<>();
        for (Shape child : source.children) {
            this.children.add(child.clone());  // ← deep clone each child
        }
    }

    @Override
    public CompoundShape clone() {
        return new CompoundShape(this);
    }

    // --- CompoundShape-specific ---

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void addChild(Shape shape) {
        children.add(shape);
    }

    public List<Shape> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof CompoundShape other)) return false;
        return children.equals(other.children);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + children.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("CompoundShape{name='%s', color='%s', x=%d, y=%d, children=[%n",
                name, getColor(), getX(), getY()));
        for (Shape child : children) {
            sb.append("    ").append(child).append("\n");
        }
        sb.append("  ]}");
        return sb.toString();
    }
}
