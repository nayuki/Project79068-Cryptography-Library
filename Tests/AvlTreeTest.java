import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import p79068.datastruct.AvlTree;
import p79068.datastruct.AvlTreeNode;


public class AvlTreeTest {
	
	private AvlTree<String> tree;
	
	
	@Before
	public void init() {
		tree = new AvlTree<String>();
	}
	
	
	@Test
	public void add() {
		tree.add("alice");
		tree.add("dave");
		tree.add("bob");
		tree.add("carol");
		tree.add("eve");
		System.out.println(tree);
		assertTrue(isHeightOkay(tree.getRoot()));
		assertTrue(isAvlOkay(tree.getRoot()));
		tree.remove("alice");System.out.println(tree);
		tree.remove("bob");System.out.println(tree);
		tree.remove("eve");System.out.println(tree);
		tree.remove("calorl");System.out.println(tree);
		tree.remove("carol");System.out.println(tree);
		tree.remove("dave");System.out.println(tree);
	}
	
	
	private static boolean isHeightOkay(AvlTreeNode<?> node) {
		if (node == null)
			return true;
		else if (!isHeightOkay(node.left) || !isHeightOkay(node.right))
			return false;
		else
			return Math.max(getHeight(node.left), getHeight(node.right) + 1) == node.height;
	}
	
	private static boolean isAvlOkay(AvlTreeNode<?> node) {
		if (node == null)
			return true;
		else
			return Math.abs(getHeight(node.left) - getHeight(node.right)) <= 1;
	}
	
	
	private static int getHeight(AvlTreeNode<?> node) {
		if (node == null)
			return 0;
		else
			return node.height;
	}
}