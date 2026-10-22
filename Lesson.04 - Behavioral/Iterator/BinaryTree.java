/**
 * BinaryTree  (ConcreteAggregate)
 *
 * A Binary Search Tree that exposes three independent traversal strategies
 * through separate iterator factory methods.
 *
 * The tree's internal structure (nodes, links) is completely hidden from
 * clients — they interact only through Iterator<T>, never through TreeNode.
 *
 * This is the Iterator pattern's core promise: you can change the internal
 * data structure (e.g., replace BST with a Red-Black Tree) without changing
 * a single line of client code.
 */
public class BinaryTree {

    private TreeNode root;

    // ── Mutators ─────────────────────────────────────────────────────────

    public void insert(Integer value) {
        root = insertRec(root, value);
    }

    private TreeNode insertRec(TreeNode node, Integer value) {
        if (node == null) return new TreeNode(value);
        int cmp = value.compareTo(node.value);
        if      (cmp < 0) node.left  = insertRec(node.left,  value);
        else if (cmp > 0) node.right = insertRec(node.right, value);
        // duplicate values are ignored
        return node;
    }

    // ── Iterator factories ───────────────────────────────────────────────

    /**
     * In-order:   left → root → right
     * For a BST, this yields elements in ascending sorted order.
     */
    public CustomIterator inOrderIterator()   { return new InOrderIterator(root);   }

    /**
     * Pre-order:  root → left → right
     * Useful for copying or serialising the tree structure.
     */
    public CustomIterator preOrderIterator()  { return new PreOrderIterator(root);  }

    /**
     * Post-order: left → right → root
     * Useful for deletion (children before parent) or expression evaluation.
     */
    public CustomIterator postOrderIterator() { return new PostOrderIterator(root); }
}
