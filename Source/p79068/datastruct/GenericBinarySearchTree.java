package p79068.datastruct;


public abstract class GenericBinarySearchTree<E extends Comparable<E>, N extends GenericBinaryTreeNode<E,N>> extends BinaryTree<E,N> {
	
	public GenericBinarySearchTree() { }
	
	
	public E get(E obj) {
		return get(root, obj);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(toString(root));
		if (sb.substring(Math.max(sb.length() - 2, 0), sb.length()).equals(", "))
			sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		return sb.toString();
	}
	
	
	/* The following methods should be static, but their type parameters would run way too long. */
	
	private E get(N node, E obj) {
		if (node == null)
			return null;
		else if (obj.compareTo(node.value) < 0)
			return get(node.left, obj);
		else if (obj.compareTo(node.value) > 0)
			return get(node.right, obj);
		else
			return node.value;
	}
	
	
	private String toString(N node) {
		if (node == null)
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append(toString(node.left));
		sb.append(node.value).append(", ");
		sb.append(toString(node.right));
		return sb.toString();
	}
	
}