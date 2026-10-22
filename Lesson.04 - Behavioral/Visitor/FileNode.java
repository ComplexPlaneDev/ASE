/**
 * FileNode  (leaf Element)
 *
 * Represents a single file with a name, extension, and size in bytes.
 */
public class FileNode implements FileSystemNode {

    private final String name;
    private final String extension;
    private final long   sizeBytes;

    public FileNode(String name, String extension, long sizeBytes) {
        this.name      = name;
        this.extension = extension;
        this.sizeBytes = sizeBytes;
    }

    @Override public String getName()       { return name;      }
    public        String getExtension()     { return extension; }
    public        long   getSizeBytes()     { return sizeBytes; }

    public String getFullName()             { return name + "." + extension; }

    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("%s.%s (%,d B)", name, extension, sizeBytes);
    }
}
