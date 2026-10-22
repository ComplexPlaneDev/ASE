import java.util.StringJoiner;

/**
 * TreeDemo
 *
 * Inserts integers into a BST and traverses it three ways,
 * demonstrating how different Iterator strategies produce
 * different orderings over the same underlying data structure.
 *
 *        Tree structure:
 *
 *              4
 *            /   \
 *           2     6
 *          / \   / \
 *         1   3 5   7
 */
public class TreeDemo {

    public static void main(String[] args) {

        BinaryTree tree = new BinaryTree();
        for (int v : new int[]{4, 2, 6, 1, 3, 5, 7}) {
            tree.insert(v);
        }

        System.out.println("╔═════════════════════════════════════════════════════════════╗");
        System.out.println("║      Binary Tree — Three Iterator Strategies                ║");
        System.out.println("╚═════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  Tree:       4");
        System.out.println("            /   \\");
        System.out.println("           2     6");
        System.out.println("          / \\   / \\");
        System.out.println("         1   3 5   7\n");

        print("In-Order   (L → root → R)  → sorted :", tree.inOrderIterator());
        print("Pre-Order  (root → L → R)  → structure:", tree.preOrderIterator());
        print("Post-Order (L → R → root)  → bottom-up:", tree.postOrderIterator());
    }

    private static <T extends Comparable<T>> void print(String label, CustomIterator it) {
        StringJoiner sj = new StringJoiner(", ");
        while (it.hasNext()) sj.add(String.valueOf(it.next()));
        System.out.printf("  %-42s [%s]%n", label, sj);
    }
}
