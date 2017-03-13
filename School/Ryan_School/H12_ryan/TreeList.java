import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** This TreeList class allows client code to store data in a binary tree structure with O(logN) add and get times.
 *
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
	private NavigationFlags shouldClear = NavigationFlags.IDLE;

	/**
	 *
	 */
	public TreeList() {
		this(null, null, 0);

	}

	/**
	 * @param root
	 * @param size
	 */
	private TreeList(BinaryTreeNode root, int size) {
		this(root, null, size);
	}

	/**
	 *
	 * @param root
	 * @param targetableNode
	 * @param size
	 */
	private TreeList(BinaryTreeNode root, BinaryTreeNode targetableNode, int size) {
		this.size = size;
		this.targetableNode = targetableNode;
		if (root == null) root = new BinaryTreeNode(null);
		this.root = root;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 *
	 * @return
	 */
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
		return (node.hasLeft()) ? headHunter(node) : node;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public E getTail() {
		targetableNode = tailChaser(root);
		return targetableNode.data;
	}

	/**
	 *
	 * @param node
	 * @return
	 */
	private BinaryTreeNode tailChaser(BinaryTreeNode node) {
		return (node.hasRight()) ? tailChaser(node.right) : node;
	}


	/** searches for the given E value in the tree and returns it's index position in the tree. Where the tree can be seen
	 * as an in-order array from 0 to N; index 0 is the smallest data value called the head, and N is the largest called
	 * the tail.
	 *
	 * @return an integer value representing the index position of the node containing the given value within the tree --
	 * if the value is not found in the tree, then -1 is returned.
	 *
	 */
	@Override
	public int indexOf(E value) {
		MyIter iter = new MyIter();
		E itersVal = (iter.hasNext()) ? iter.next() : value;
		int pos = 0;
		while (iter.hasNext() && itersVal != value) {
			itersVal = iter.next();
			pos++;
		}
		return pos;
	}

	/**this method provides a means to locate a specific value in the TreeList.
	 * @param value the value we are looking for in the tree
	 * @param node the current node target holding a data value that we will check "value" against.
	 * @return
	 */
	private boolean seeker(E value, BinaryTreeNode node) {

		if (node != null) {
			int relativeVal = (value != null) ? value.compareTo(node.data) : 0;
			boolean goLeft = relativeVal < 0;
			boolean goRight = relativeVal > 0;
			boolean foundIt = relativeVal == 0;
			if (shouldClear == NavigationFlags.CLEAR) {
				node.parent = null;
				goLeft = goRight = true;
				foundIt = false;
			}
			if (goLeft) {
				if (node.hasLeft()) return seeker(value, node.left);
			}
			if (goRight) {
				if (node.hasRight()) return seeker(value, node.right);
			}
			if (foundIt) {
				targetableNode = node;
				return true;
			}
		}
		return false;
	}

	/**
	 * @param value
	 * @return
	 */
	@Override
	public boolean contains(E value) {
		return indexOf(value) >= 0;
	}

	/**
	 *
	 * @param value
	 */
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
		int relativeVal = value.compareTo(node.data);
		if (relativeVal <= 0) {
			if (node.hasLeft()) hideLikeNinja(value, node.left);
			else node.left = new BinaryTreeNode(value, node);
		} else {
			if (node.hasRight()) hideLikeNinja(value, node.right);
			else node.right = new BinaryTreeNode(value, node);
		}

	}

	/**
	 *
	 * @param other
	 */
	@Override
	public void addAll(ISortedList other) {
		Iterator otherIter = other.iterator();
		while (otherIter.hasNext()) {
			add((E) otherIter.next());
		}
	}

	/**
	 * finds the largest data node in the tree and deletes that node, then returns the data it had stored.
	 *
	 * @return
	 */
	@Override
	public E removeTail() {
		return shiftAndDestroyTail(root);
	}

	/**
	 * This method takes a node and finds the largest data point in its left subtree,
	 * it then swaps data from the given node with that of the found node. It saves the root's originol data and
	 * removes the node that it was saved at. Implicit in this function is that the subtree of the deleted node point
	 * is preserved.
	 *
	 * @param node
	 */
	private E shiftAndDestroyTail(BinaryTreeNode node) {
		E returnable;
		BinaryTreeNode sacTail;
		if (node.hasRight()) {
			sacTail = tailChaser(node.right);
			returnable = sacTail.data;
			if (sacTail.hasLeft()) {
				sacTail.parent.right = sacTail.left;
				sacTail.left.parent = sacTail.parent;
				sacTail = null;
			}
			if (sacTail.hasLeft()) {
				sacTail.parent.right = sacTail.left;
			}
		} else if (node.hasLeft()) {
			returnable = node.data;
			if (node.parent.left == node) node.parent.left = node.left;
			else node.parent.right = node.left;
			node.left.parent = node.parent;
			node = null;
		} else {
			returnable = node.data;
			if (node.parent.left == node) node.parent.left = null;
			else node.parent.right = null;
			node = null;
		}
		size--;
		return returnable;
	}

	/**
	 * @return
	 */
	@Override
	public E removeHead() {
		return shiftAndDestroyHead(root);
	}

	/**
	 *
	 * @param node
	 * @return
	 */
	private E shiftAndDestroyHead(BinaryTreeNode node) {
		BinaryTreeNode oldHead = headHunter(node);
		E data = oldHead.data;

		if (root != oldHead) {
			if (oldHead.hasRight()) {
				oldHead.right.parent = oldHead.parent;
				oldHead.parent.left = oldHead.right;
			}
		} else {
			if (root.hasRight()) {
				root.right.parent = null;
				root = root.right;
			}
		}
		size--;
		return data;
	}



	@Override
	public boolean remove(E value) {
		targetableNode = null;
		shouldSeek = NavigationFlags.SEEK;
		boolean isDestroyable = seeker(value, root) && targetableNode.data == value;
		shouldSeek = NavigationFlags.IDLE;
		if (isDestroyable) {
			if (targetableNode.hasLeft()) {
				targetableNode.data = shiftAndDestroyTail(targetableNode.left);
			} else if (targetableNode.hasRight()) {
				if (targetableNode.hasParent()) {
					if (targetableNode.parent.right == targetableNode) {
						targetableNode.parent.right = targetableNode.right;
					} else {
						targetableNode.parent.left = targetableNode.right;
					}
					targetableNode.right.parent = targetableNode.parent;

				} else {// the only node that can have no parent is the root node. thus...
					root = targetableNode.right;
				}
				size--;
			} else {// targetableNode has no children so we can set the parent node pointer for it to null;
				size--;
				if (targetableNode.parent.left == targetableNode) {
					targetableNode.parent.left = null;
				} else targetableNode.parent.right = null;
			}
		}
		return isDestroyable;
	}

	@Override
	public void clear() {
		shouldClear = NavigationFlags.CLEAR;
		seeker(null, root);
		shouldClear = NavigationFlags.IDLE;
		root.right = root.left = null;
		root.data = null;
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
		int counter = 1;
		if (iter.hasNext()) {
			s += iter.next();
		}
		int returnCarriageAt = ((size / 3) > 10) ? size / 3: 10;
		while (iter.hasNext()) {
			if (counter % returnCarriageAt == 0) {
				s += ",\n " + String.format("%4s", iter.next());
			} else {
				s += ", " + String.format("%4s", iter.next());
			}
			counter++;
		}
		s += " }";
		return s;
	}

	private class MyIter implements Iterator {
		private int pos;
		private BinaryTreeNode target;
		private ArrayList<BinaryTreeNode> breadCrumbs;
		private boolean canRemove;

		/**
		 *
		 */
		MyIter() {
			canRemove = false;
			pos = 0;
			if (root != null) target = tailChaser(root);

			breadCrumbs = new ArrayList<>(size);
			breadCrumbs.addAll(buildBreadCrumbs(root));
		}

		/**
		 * @param btn
		 * @return
		 */
		private ArrayList<BinaryTreeNode> buildBreadCrumbs(BinaryTreeNode btn) {
			ArrayList<BinaryTreeNode> returnable = new ArrayList<>();
			if (btn.hasLeft()) returnable.addAll(buildBreadCrumbs(btn.left));
			returnable.add(btn);
			if (btn.hasRight()) returnable.addAll(buildBreadCrumbs(btn.right));
			return returnable;
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
			return !breadCrumbs.isEmpty() && pos < size;
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
			target = breadCrumbs.get(pos++);
			if (target.data == null) throw new NoSuchElementException("there appears to be no data here");
			canRemove = true;
			return target.data;
		}

		/**
		 * Removes from the underlying collection the last element returned
		 * by this iterator (optional operation).  This method can be called
		 * only once per call to {@link #next}.  The behavior of an iterator
		 * is unspecified if the underlying collection is modified while the
		 * iteration is in progress in any way other than by calling this
		 * method.
		 *
		 * @throws UnsupportedOperationException if the {@code remove}
		 *                                       operation is not supported by this iterator
		 * @throws IllegalStateException         if the {@code next} method has not
		 *                                       yet been called, or the {@code remove} method has already
		 *                                       been called after the last call to the {@code next}
		 *                                       method
		 * @implSpec The default implementation throws an instance of
		 * {@link UnsupportedOperationException} and performs no other action.
		 */
		@Override
		public void remove() {
			if (canRemove) {
				target = breadCrumbs.remove(pos - 1);
				canRemove = false;
				if (target.data == null) throw new IllegalStateException("no data at this point to return");
				TreeList.this.remove(target.data);
				pos--;
			} else {
				throw new IllegalStateException("\n\t\tthe next() method has not yet been called, \n" +
						"\t\tor the remove() method has already been called after the last call to the next() method");
			}
		}

		private BinaryTreeNode giveNode(){
			return breadCrumbs.get(pos);
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

		public boolean hasParent() {
			return parent != null;
		}
	}
}
