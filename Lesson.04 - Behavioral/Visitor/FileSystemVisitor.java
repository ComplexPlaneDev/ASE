/**
 * FileSystemVisitor  (Visitor interface)
 *
 * Operations available on the file system tree.
 * Implementing classes provide the traversal into children themselves
 * by calling child.accept(this) from within visit(DirectoryNode).
 */
public interface FileSystemVisitor {

    void visit(FileNode      file);
    void visit(DirectoryNode directory);
}
