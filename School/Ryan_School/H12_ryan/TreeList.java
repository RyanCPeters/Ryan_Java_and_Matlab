import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * @author Ryan Peters
 * @date 3/9/2017
 */
public class TreeList<E extends Comparable> implements ISortedList<E> {

	private int size;

	private BinaryTreeNode root;
	private BinaryTreeNode targetableNode;

	private enum NavigationFlags {SEEK, FOUND, IDLE, HIDENODE, NODEHIDDEN, DESTROY, DESTROYED, CLEAR}

	private NavigationFlags shouldSeek = NavigationFlags.IDLE;
	private NavigationFlags shouldHide = NavigationFlags.IDLE;
	private NavigationFlags shouldDestroy = NavigationFlags.IDLE;

	public TreeList() {
		this(null, null, 0);

	}

	protected TreeList(BinaryTreeNode root, int size) {
		this(root, null, size);
	}

	private TreeList(BinaryTreeNode root, BinaryTreeNode targetableNode, int size) {
		this.size = size;
		this.targetableNode = targetableNode;
		if (root == null) root = new BinaryTreeNode(null);
		this.root = root;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * starting at the root node, traverses left until it finds a node with a null value for .left; This method
	 * also sets the tree's targetableNode field to be equal to that node allowing peripheral functionality to be
	 * easily executed on that node.
	 *
	 * @return the lowest data value of type E in the tree.
	 */
	@Override
	public E getHead() {
		targetableNode = headHunter(root);
		return targetableNode.data;
	}

	/**
	 * This method takes a node, and then traverses left until it finda a node where .left is null; it then returns
	 * this last non null node.
	 *
	 * @param node any arbitrary node in the tree
	 * @return the first node in the given node's left side subtree to have a null value for .left;
	 */
	private BinaryTreeNode headHunter(BinaryTreeNode node) {
		while (node.left != null) node = node.left;
		return node;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public E getTail() {
		return tailChaser(root).data;
	}

	private BinaryTreeNode tailChaser(BinaryTreeNode node) {
		return (node.right != null) ? tailChaser(node.right) : node;
	}


	/**
	 *
	 */
	@Override
	public int indexOf(E value) {
		shouldSeek = NavigationFlags.SEEK;
		int pos = seeker(value, root, 0);
		if (shouldSeek != NavigationFlags.FOUND) pos = -1;
		shouldSeek = NavigationFlags.IDLE;
		return pos;
	}

	/**
	 * @param value
	 * @param node
	 * @param pos
	 * @return
	 */
	private int seeker(E value, BinaryTreeNode node, int pos) {
		if (shouldDestroy == NavigationFlags.CLEAR) node.parent = null;
		if (node != null) {
			if (shouldSeek == NavigationFlags.SEEK && node.left != null) {
				pos = seeker(value, node.left, pos);
			}
			pos++;
			if (shouldSeek == NavigationFlags.SEEK && node.data.compareTo(value) == 0) {
				shouldSeek = NavigationFlags.FOUND;

			}
			if (shouldSeek == NavigationFlags.SEEK && node.right != null) {
				pos = seeker(value, node.right, pos);
			}
		}
		if (shouldSeek == NavigationFlags.SEEK && shouldDestroy == NavigationFlags.DESTROY) {
			targetableNode = node;
			if (node.left != null) shiftAndDestroyTail(node.left);
			else if (node.right != null) node.right.parent = node.parent;

			shouldDestroy = NavigationFlags.DESTROYED;
		}
		return pos;
	}

	@Override
	public boolean contains(E value) {
		return indexOf(value) >= 0;
	}

	@Override
	public void add(E value) {
		if (root.data != null) {
			shouldHide = NavigationFlags.HIDENODE;
			hideLikeNinja(value, root);
			shouldHide = NavigationFlags.IDLE;
		} else {
			root.data = value;
		}

		size++;
	}

	/**
	 * this method does the dirty work of finding where in the tree a node should be added. In essence, this method
	 * is hiding the value in the tree, but according to an in-order traversal logic.
	 *
	 * @param value the data value to be stored once the propper spot is found
	 * @param node  the value parameter will be compared against the data in this node and a decision will be made from
	 *              there about what direction to proceed in.
	 */
	private void hideLikeNinja(E value, BinaryTreeNode node) {
		if (node != null) {
			if (shouldHide == NavigationFlags.HIDENODE) {
				int relativeVal = node.data.compareTo(value);
				if (relativeVal >= 0) {
					if (!node.hasLeft()) {
						node.setLeft(new BinaryTreeNode(value, node));
//						System.out.println("node.data = " + node.data);
//						System.out.println("node.left.data = " + node.left.data);
						shouldHide = NavigationFlags.NODEHIDDEN;
					} else {
						hideLikeNinja(value, node.left);
					}
				} else if (relativeVal < 0) {
					if (!node.hasRight()) {
						node.setRight(new BinaryTreeNode(value, node));
						shouldHide = NavigationFlags.NODEHIDDEN;
					} else {
						hideLikeNinja(value, node.right);
					}
				} else hideLikeNinja(value, (node.data.compareTo(value) <= 0) ? node.left : node.right);

			}
		}
	}
	@Override
	public void addAll(ISortedList other) {
		Iterator otherIter = other.iterator();
		while (otherIter.hasNext()) {
			add((E) otherIter.next());
		}
	}

	@Override
	public E removeHead() {
		E data = getHead();
		if (targetableNode.right != null) {
			shiftAndDestroyHead(targetableNode.right);
		} else {
			targetableNode.parent.left = null;
		}
		size--;
		return data;
	}

	/**
	 * This method takes a root/subroot node and finds the largetst data point in its left subtree,
	 * it then swaps data from the given root and that of the found node. It saves the root's originol data and
	 * removes the node that it was saved at. Implicit in this function is that the subtree of the deleted node point
	 * is preserved.
	 *
	 * @param node
	 */
	private E shiftAndDestroyTail(BinaryTreeNode node) {
		E returnable = node.data;
		BinaryTreeNode nodesTail = tailChaser(node);
		node.data = nodesTail.data;

		// the bellow two lines
		nodesTail.parent.right = nodesTail.left;
		nodesTail.left.parent = nodesTail.parent;
		return returnable;
	}

	private E shiftAndDestroyHead(BinaryTreeNode node) {
		BinaryTreeNode nodesHead = headHunter(node);
		node.parent.left = nodesHead;
		return nodesHead.data;
	}
	@Override
	public E removeTail() {
		size--;
		return shiftAndDestroyTail(root);
	}

	@Override
	public boolean remove(E value) {

		shouldDestroy = NavigationFlags.DESTROY;
		shouldSeek = NavigationFlags.SEEK;
		seeker(value, root, 0);
		boolean destroyer = (shouldDestroy == NavigationFlags.DESTROYED);
		shouldSeek = shouldDestroy = NavigationFlags.IDLE;
		if (destroyer) size--;
		return destroyer;
	}

	@Override
	public void clear() {
		shouldDestroy = NavigationFlags.CLEAR;
		seeker(null, root, 0);
		shouldDestroy = NavigationFlags.IDLE;
		root = null;
		size = 0;
	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator iterator() {
		return new MyIter();
	}

	/**
	 * Returns a string representation of the object. In general, the
	 * {@code toString} method returns a string that
	 * "textually represents" this object. The result should
	 * be a concise but informative representation that is easy for a
	 * person to read.
	 * It is recommended that all subclasses override this method.
	 * <p>
	 * The {@code toString} method for class {@code Object}
	 * returns a string consisting of the name of the class of which the
	 * object is an instance, the at-sign character `{@code @}', and
	 * the unsigned hexadecimal representation of the hash code of the
	 * object. In other words, this method returns a string equal to the
	 * value of:
	 * <blockquote>
	 * <pre>
	 * getClass().getName() + '@' + Integer.toHexString(hashCode())
	 * </pre></blockquote>
	 *
	 * @return a string representation of the object.
	 */
	@Override
	public String toString() {
		String s = "{ ";
		MyIter iter = new MyIter();
		if (iter.hasNext()) {
			s += iter.next();
		}
		while (iter.hasNext()) {
			s += ", " + iter.next();
		}
		s += " }";
		return s;
	}

	private class MyIter implements Iterator {
				private int pos;
		private BinaryTreeNode target;
		private Stack<BinaryTreeNode> breadCrumbs;


		MyIter() {
			this.pos = -1;
			this.target = root;
			this.breadCrumbs = new Stack<>();
			breadCrumbs.push(target);
			while (target.hasLeft()) {
				target = target.left;
				breadCrumbs.push(target);
			}
		}

		/**
		 * Returns true if the iteration has more elements.
		 * (In other words, returns true if {@link #next} would
		 * return an element rather than throwing an exception.)
		 *
		 * @return {@code true} if the iteration has more elements
		 */
		@Override
		public boolean hasNext() {
			return !breadCrumbs.empty() && pos+1 < size;
		}

		/**
		 * Returns the next element in the iteration.
		 *
		 * @return the next element in the iteration
		 * @throws NoSuchElementException if the iteration has no more elements
		 */
		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) throw new NoSuchElementException("There are no more nodes to traverse");
			if (breadCrumbs.peek().data == null) throw new NoSuchElementException("there appears to be no data here");
			target = breadCrumbs.pop();
			if (target.hasLeft()) {
				breadCrumbs.push(target.left);
			} else if (target.hasRight()) {
				breadCrumbs.push(target.right);
			}
			if (!breadCrumbs.empty()) {
				while (breadCrumbs.peek().hasLeft()) {
					breadCrumbs.push(breadCrumbs.peek().left);
					breadCrumbs.peek();
				}
			}
			pos++;
			return target.data;
		}


	}

	/**
	 * This private inner class is used to represent the data nodes within the BinaryTree, each node can hold a
	 * a generic data value along with left, right, and parent BinaryTreeNode(BTN) pointers.
	 * <p>
	 * The left BTN pointer should always hold values that satisfy: (btn.data.compareTo(otherData) <= 0)
	 * <p>
	 * The right BTN pointer should always hold values that satisfy: (btn.data.compareTo(otherData) > 0)
	 * <p>
	 * The parent BTN pointer is a convenience pointer for tracing back to the root from any given node in the tree.
	 */
	private class BinaryTreeNode {
		private E data;
		private BinaryTreeNode left;
		private BinaryTreeNode right;
		private BinaryTreeNode parent;

		/**
		 * creates a basic node that holds data and instantiates the left and right node pointers to null.
		 *
		 * @param data a generic data type that the client declares at the instantiation of the binary tree.
		 */
		BinaryTreeNode(E data) {
			this(data, null, null, null);
		}

		BinaryTreeNode(E data, BinaryTreeNode parent) {
			this(data, null, null, parent);
		}

		/**
		 * @param data
		 * @param left
		 * @param right
		 */
		BinaryTreeNode(E data, BinaryTreeNode left, BinaryTreeNode right, BinaryTreeNode parent) {
			this.data = data;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}

		private void setLeft(BinaryTreeNode node) {
			this.left = node;
		}

		private void setRight(BinaryTreeNode node) {
			this.right = node;
		}
		private boolean hasLeft() {
			return left != null;
		}

		private boolean hasRight() {
			return right != null;
		}

		private boolean hasChild() {
			return hasLeft() || hasRight();
		}
	}
}
