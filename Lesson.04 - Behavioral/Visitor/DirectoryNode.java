import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DirectoryNode  (composite Element)
 *
 * Represents a directory containing zero or more FileSystemNodes.
 * Children may themselves be files or directories, forming a tree.
 *
 * The accept() method calls visitor.visit(this) and nothing else.
 * Traversal into children is the VISITOR's responsibility.
 */
public class DirectoryNode implements FileSystemNode {

    private final String             name;
    private final List<FileSystemNode> children = new ArrayList<>();

    public DirectoryNode(String name) {
        this.name = name;
    }

    @Override public String getName() { return name; }

    public DirectoryNode add(FileSystemNode node) {
        children.add(node);
        return this;   // fluent builder
    }

    /** Unmodifiable view — visitors read but cannot structurally modify. */
    public List<FileSystemNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visit(this);
        // Traversal of children is left to the visitor.
        // See FileSystemVisitor implementations for how each handles it.
    }

    @Override
    public String toString() { return "[DIR] " + name; }
}
