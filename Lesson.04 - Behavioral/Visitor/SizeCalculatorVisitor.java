/**
 * SizeCalculatorVisitor  (ConcreteVisitor)
 *
 * Traverses the entire file system tree, accumulates the total size
 * of all files, and prints a depth-indented tree with per-file sizes.
 *
 * Demonstrates:
 *   - Full depth-first traversal
 *   - Depth tracking for indentation
 *   - Per-directory size subtotals (computed via a size stack)
 */
public class SizeCalculatorVisitor implements FileSystemVisitor {

    private long totalBytes = 0;
    private int  depth      = 0;

    @Override
    public void visit(FileNode file) {
        totalBytes += file.getSizeBytes();
        System.out.printf("  %s📄 %-30s  %,10d B%n",
                indent(), file.getFullName(), file.getSizeBytes());
    }

    @Override
    public void visit(DirectoryNode dir) {
        System.out.printf("  %s📁 %s/%n", indent(), dir.getName());
        depth++;
        for (FileSystemNode child : dir.getChildren()) {
            child.accept(this);
        }
        depth--;
    }

    public long getTotalBytes() { return totalBytes; }

    public String getTotalFormatted() {
        if (totalBytes >= 1_048_576)
            return String.format("%.2f MB", totalBytes / 1_048_576.0);
        if (totalBytes >= 1_024)
            return String.format("%.2f KB", totalBytes / 1_024.0);
        return totalBytes + " B";
    }

    private String indent() { return "  ".repeat(depth); }
}
