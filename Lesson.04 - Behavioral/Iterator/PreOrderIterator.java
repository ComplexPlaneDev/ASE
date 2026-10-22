import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * PreOrderIterator  (ConcreteIterator — root → left → right)
 *
 * Non-recursive implementation using an explicit stack.
 *
 * Algorithm:
 *   1. Push root.
 *   2. On each next(): pop, yield the node,
 *      push RIGHT child first (so LEFT is processed first — LIFO).
 */
public class PreOrderIterator implements CustomIterator {

    private final Deque<TreeNode> stack = new ArrayDeque<>();

    PreOrderIterator(TreeNode root) {
        if (root != null) stack.push(root);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Integer next() {
        if (!hasNext()) throw new NoSuchElementException();
        TreeNode node = stack.pop();
        if (node.right != null) stack.push(node.right); // right pushed first
        if (node.left  != null) stack.push(node.left);  // left on top → processed next
        return node.value;
    }
}
