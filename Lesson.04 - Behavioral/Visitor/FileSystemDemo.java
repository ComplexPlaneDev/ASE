/**
 * FileSystemDemo — builds a representative file tree and applies
 * two distinct visitors to the same structure.
 */
public class FileSystemDemo {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║  Visitor Pattern — File System Tree                  ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // ── Build file system tree ────────────────────────────────────────
        DirectoryNode root = new DirectoryNode("project")
            .add(new FileNode("pom", "xml",   2_048))
            .add(new FileNode("README", "md", 4_320))
            .add(new DirectoryNode("src")
                .add(new DirectoryNode("main")
                    .add(new DirectoryNode("java")
                        .add(new FileNode("App",           "java", 3_200))
                        .add(new FileNode("Config",        "java", 1_800))
                        .add(new FileNode("PaymentService","java", 8_500)))
                    .add(new DirectoryNode("resources")
                        .add(new FileNode("application", "xml",  1_200))
                        .add(new FileNode("logback",     "xml",    980))))
                .add(new DirectoryNode("test")
                    .add(new FileNode("AppTest",     "java", 4_100))
                    .add(new FileNode("ConfigTest",  "java", 2_700))))
            .add(new DirectoryNode("diagrams")
                .add(new FileNode("architecture", "png",  152_000))
                .add(new FileNode("sequence",     "png",   98_000))
                .add(new FileNode("notes",        "md",     3_400)));

        // ── Visitor 1: Size Calculator ────────────────────────────────────
        System.out.println("\n▶ Visitor 1: Size Calculator (depth-first traversal)\n");
        SizeCalculatorVisitor sizeVisitor = new SizeCalculatorVisitor();
        root.accept(sizeVisitor);
        System.out.printf("%n  Total project size: %,d B  (%s)%n",
                sizeVisitor.getTotalBytes(), sizeVisitor.getTotalFormatted());

        // ── Visitor 2: Search by extension ───────────────────────────────
        System.out.println("\n▶ Visitor 2: Search for .java files\n");
        SearchVisitor javaSearch = new SearchVisitor("java");
        root.accept(javaSearch);
        System.out.printf("%n  Found %d .java file(s)%n", javaSearch.getMatchCount());

        System.out.println("\n▶ Visitor 2: Search for .xml files\n");
        SearchVisitor xmlSearch = new SearchVisitor("xml");
        root.accept(xmlSearch);
        System.out.printf("%n  Found %d .xml file(s)%n", xmlSearch.getMatchCount());
    }
}
