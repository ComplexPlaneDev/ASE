import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * PostOrderIterator  (ConcreteIterator — left → right → root)
 *
 * Non-recursive two-stack implementation.
 *
 * Algorithm (Two-Stack method):
 *   Stack1 drives traversal; Stack2 accumulates the result in reverse.
 *   1. Push root onto Stack1.
 *   2. While Stack1 not empty:
 *        a. Pop from Stack1, push onto Stack2.
 *        b. Push LEFT child then RIGHT child onto Stack1.
 *   3. Drain Stack2 into a result queue → post-order.
 *
 * Why it works: Stack2 captures nodes in the order (root, right, left).
 * Reversing that order (stack pop = LIFO) gives (left, right, root).
 */
public class PostOrderIterator implements CustomIterator {

    private final Deque<Integer> result = new ArrayDeque<>();

    PostOrderIterator(TreeNode root) {
        Deque<TreeNode> stack1 = new ArrayDeque<>();
        Deque<TreeNode> stack2 = new ArrayDeque<>();
        if (root != null) stack1.push(root);
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            if (node.left  != null) stack1.push(node.left);   // left first → processed later
            if (node.right != null) stack1.push(node.right);  // right on top → processed sooner
        }
        // Drain stack2: popping reverses the (root,right,left) order → (left,right,root)
        while (!stack2.isEmpty()) {
            result.add(stack2.pop().value);
        }
    }

    @Override public boolean hasNext()  { return !result.isEmpty(); }
    @Override public Integer next()     {
        if (!hasNext()) throw new NoSuchElementException();
        return result.poll();
    }
}
