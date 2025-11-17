public class FileInfo {
    public enum FileType {
        IMAGE, DOCUMENT, AUDIO
    }

    private final String name;
    private final FileType type;
    private final long size;

    public FileInfo(String name, FileType type, long size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }

    public String getName() { return name; }
    public FileType getType() { return type; }
    public long getSize() { return size; }
}
