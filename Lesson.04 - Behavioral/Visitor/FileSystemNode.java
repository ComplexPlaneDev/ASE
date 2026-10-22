/**
 * FileSystemNode  (Element interface)
 *
 * The base type of a composite file system hierarchy.
 *
 * Two concrete types exist:
 *   - FileNode      (leaf)       — a single file with name, extension, size
 *   - DirectoryNode (composite)  — a directory containing other nodes
 *
 * The Visitor pattern is a natural fit for composite structures because:
 *   - You need to apply operations (size, search, report) across the tree
 *   - The hierarchy is stable (files and directories don't change)
 *   - The operations grow over time
 *
 * ── Visitor-controlled traversal ──────────────────────────────────────────
 *
 * Each visitor controls its own traversal strategy by calling
 * child.accept(this) from within visit(DirectoryNode).
 * This is more flexible than element-controlled traversal because:
 *   - Some visitors may abort early (SearchVisitor on first match)
 *   - Some visitors may skip certain subtrees (access-controlled dirs)
 *   - Some visitors need pre/post directory hooks (ReportVisitor)
 *
 * ─────────────────────────────────────────────────────────────────────────
 */
public interface FileSystemNode {
    String getName();
    void accept(FileSystemVisitor visitor);
}
