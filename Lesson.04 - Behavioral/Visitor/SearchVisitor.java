import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SearchVisitor  (ConcreteVisitor)
 *
 * Searches the file system tree for all files matching a given extension.
 * Accumulates matches and records the full path of each found file.
 *
 * Demonstrates that visitors can carry their own search parameters
 * via the constructor — the element classes don't need to know about
 * file-extension searching at all.
 */
public class SearchVisitor implements FileSystemVisitor {

    private final String          targetExtension;   // e.g. "java", "xml"
    private final List<String>    matches     = new ArrayList<>();
    private final StringBuilder   currentPath = new StringBuilder();

    public SearchVisitor(String extension) {
        this.targetExtension = extension.toLowerCase().replaceFirst("^\\.", "");
    }

    @Override
    public void visit(FileNode file) {
        if (file.getExtension().equalsIgnoreCase(targetExtension)) {
            matches.add(currentPath + file.getFullName());
            System.out.printf("  [FOUND] %s%s%n", currentPath, file.getFullName());
        }
    }

    @Override
    public void visit(DirectoryNode dir) {
        int previousLength = currentPath.length();
        currentPath.append(dir.getName()).append("/");
        for (FileSystemNode child : dir.getChildren()) {
            child.accept(this);
        }
        currentPath.setLength(previousLength);   // restore path on exit
    }

    public List<String> getMatches() {
        return Collections.unmodifiableList(matches);
    }

    public int getMatchCount() { return matches.size(); }
}
