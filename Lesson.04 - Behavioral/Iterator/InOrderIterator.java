import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * InOrderIterator  (ConcreteIterator — left → root → right)
 *
 * Non-recursive implementation using an explicit stack.
 * For a BST this visits nodes in ascending sorted order.
 *
 * Algorithm:
 *   1. Push all left-spine nodes onto the stack.
 *   2. On each next(): pop, yield the node, then push the left spine
 *      of the right subtree.
 */
public class InOrderIterator implements CustomIterator {

    private final Deque<TreeNode> stack = new ArrayDeque<>();

    InOrderIterator(TreeNode root) {
        pushLeftSpine(root);
    }

    private void pushLeftSpine(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Integer next() {
        if (!hasNext()) throw new NoSuchElementException();
        TreeNode node = stack.pop();
        pushLeftSpine(node.right);  // prepare the right subtree
        return node.value;
    }
}
