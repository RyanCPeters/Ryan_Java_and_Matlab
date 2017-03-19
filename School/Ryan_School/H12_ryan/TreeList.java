import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.stream.Stream;

/** This TreeList class allows client code to store data in a binary tree structure with O(logN) add and get times.
 *
 * @author Ryan Peters
 * @date 3/9/2017
 */
public class TreeList<E extends Comparable> implements ISortedList<E> {
	private int size;

	private BinaryTreeNode root;
	private BinaryTreeNode targetNode;
	private BinaryTreeNode targetParent;
	private boolean targetIsRightTrue;


	/**
	 *
	 */
	public TreeList() {
		this(null, null, 0);

	}

	/**
	 *
	 * @param root
	 * @param targetableNode
	 * @param size
	 */
	private TreeList(BinaryTreeNode root, BinaryTreeNode targetableNode, int size) {
		this.size = size;
		this.targetNode = targetableNode;
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
	 * also sets the tree's targetNode field to be equal to that node allowing peripheral functionality to be
	 * easily executed on that node.
	 *
	 * @return the lowest data value of type E in the tree.
	 */
	@Override
	public E getHead() {
		headHunter(root);
		return targetNode.data;
	}

	/**
	 * This method takes a node, and then traverses left until it finda a node where .left is null; it then returns
	 * this last non null node.
	 *
	 * @param node any arbitrary node in the tree
	 * @return the first node in the given node's left side subtree to have a null value for .left;
	 */
	private void headHunter(BinaryTreeNode node) {
		targetNode = node;
		while (targetNode.hasLeft()) {
			targetParent = targetNode;
			targetNode = targetNode.left;
		}
	}

	/**
	 *
	 * @return
	 */
	@Override
	public E getTail() {
		tailChaser(root);
		return targetNode.data;
	}

	/**this method seeks out the greatest node in the tree based upon the given root node.
	 *
	 * @param node the root of the tree in which we are searching out the greatest value available.
	 * @return returns the node containing the largest data value in the tree rooted around the given node.
	 */
	private void tailChaser(BinaryTreeNode node) {
		targetNode = node;
		while (targetNode.hasRight()) {
			targetParent = targetNode;
			targetNode = targetNode.right;
		}
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
		int pos = 0;
		MyIter iter = new MyIter();
		boolean targetNotFound = true;
		while (iter.hasNext() && targetNotFound) {
			if (value.equals(iter.next())) targetNotFound = false;
			if (targetNotFound) pos++;
		}
		return pos;
	}

	/**this method provides a means to locate a specific value in the TreeList.
	 * @param value the value we are looking for in the tree
	 * @param node the current node nextTarget holding a data value that we will check "value" against.
	 * @return
	 */
	private boolean seeker(E value, BinaryTreeNode node) {
		if (node != null) {
			int relativeVal = value.compareTo(node.data);
			relativeVal = (relativeVal < 0) ? -1 : (relativeVal > 0) ? 1 : 0;
			switch (relativeVal) {
				case -1:
					if (node.hasLeft()) {
						targetParent = node;
						targetIsRightTrue = false;
						return seeker(value, node.left);
					}
					break;
				case 0:
					targetNode = node;
					return true;
				case 1:
					if (node.hasRight()) {
						targetParent = node;
						targetIsRightTrue = true;
						return seeker(value, node.right);
					}
					break;
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
		return (root.data != null) && seeker(value, root);
	}

	/**
	 *
	 * @param value
	 */
	@Override
	public void add(E value) {
		if (root.data != null) {
			hideLikeNinja(value, root);
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
		boolean notFound = true;
		while (notFound) {
			if (relativeVal <= 0) {
				if (node.hasLeft()) {
					node = node.left;
					relativeVal = value.compareTo(node.data);
				} else {
					node.left = new BinaryTreeNode(value);
					notFound = false;
				}
			} else {
				if (node.hasRight()) {
					node = node.right;
					relativeVal = value.compareTo(node.data);
				} else {
					node.right = new BinaryTreeNode(value);
					notFound = false;
				}
			}
		}

//		if (relativeVal <= 0) {
//			if (node.hasLeft()) hideLikeNinja(value, node.left);
//			else node.left = new BinaryTreeNode(value);
//		} else {
//			if (node.hasRight()) hideLikeNinja(value, node.right);
//			else node.right = new BinaryTreeNode(value);
//		}
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
		E tail = getTail();
		remove(tail);
		return tail;
	}



	/**
	 * @return
	 */
	@Override
	public E removeHead() {
		E head = getHead();
		remove(head);
		return head;
	}


	/**
	 *
	 * @param value
	 * @return
	 */
	@Override
	public boolean remove(E value) {
		boolean haveTarget = seeker(value, root);
		BinaryTreeNode sacNode = targetNode;
		BinaryTreeNode sacNodeParent = targetParent;
		if (haveTarget) remove(sacNode, sacNodeParent);
		return haveTarget;

	}

	/**
	 * this private helper method for remove takes prefound sacrificial node(sacNode) and it's parent node then handles
	 * the process of ensuring that data is in the right place for node removals.
	 *
	 * @param sacNode       the node we're going to remove
	 * @param sacNodeParent it's parent, important for making sure we can get rid of any pointers to sacNode
	 */
	private void remove(BinaryTreeNode sacNode, BinaryTreeNode sacNodeParent) {
		if (sacNode.hasLeft()) {
			// using tailChaser to set targetable node to be the largest node in sacNode's left subtree, and targetParent as its parent
			tailChaser(sacNode.left);
			sacNode.data = targetNode.data;
			if (targetNode != sacNode.left)
				if (targetNode.hasLeft()) targetParent.right = targetNode.left;
				else targetParent.right = null;
			else sacNode.left = sacNode.left.left;
			targetNode = targetParent = null;
		} else if (sacNode.hasRight()) {
			// this is checking if sacNode is the right side node of it's parent.
			if (targetIsRightTrue) sacNodeParent.right = sacNode.right;
			else sacNodeParent.left = sacNode.right;
		}
		size--;
	}

	/**
	 *
	 */
	@Override
	public void clear() {
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
		Stream.Builder<String> stringStream = Stream.builder();

		stringStream.add("{ ");
		MyIter iter = new MyIter();
		int counter = 0;
		if (iter.hasNext()) {
			stringStream.add(String.format("%6s", iter.next()));
		}
		// returnCarriageAt is the point in the string we should insert a new line return. This will happen when line
		// length is  10 < size/3 < 30
//		int returnCarriageAt = ((size / 3) < 10) ? 10 : ( size / 3)>30 ? (size / 3) : 30;
		int returnCarriageAt = 30;
		while (iter.hasNext()) {
			counter++;
			if (counter % returnCarriageAt == 0) {
				stringStream.add(",\n " + String.format("%4s", iter.next()));

			} else {
				stringStream.add(", " + String.format("%4s", iter.next()));
			}
		}
		stringStream.add(" }");
		Stream<String> streamString = stringStream.build();

		return streamString.reduce("", (n, y) -> n + y);
	}

	/**the private inner class MyIter sets up the iterator which client code can use to traverse the tree in a linear
	 * ordered fashion.
	 *
	 */
	private class MyIter implements Iterator {
		private BinaryTreeNode nextTarget;
		private BinaryTreeNode iterTarget;
		private BinaryTreeNode lastTarget;
		private Stack<BinaryTreeNode> pathStack;
		private boolean canRemove;

		/**
		 *
		 */
		MyIter() {
			canRemove = false;
			pathStack = new Stack<>();
			nextTarget = root;
			pathStack.push(nextTarget);
			while (nextTarget.hasLeft()) {
				nextTarget = nextTarget.left;
				pathStack.push(nextTarget);
			}
			lastTarget = iterTarget = nextTarget;
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
			return !pathStack.empty();
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
			lastTarget = iterTarget;
			iterTarget = nextTarget = pathStack.pop();
			if (iterTarget.data == null) throw new NoSuchElementException("there appears to be no data here");
			if (nextTarget.hasRight()) {
				nextTarget = nextTarget.right;
				pathStack.push(nextTarget);
				while (nextTarget.hasLeft()) {
					nextTarget = nextTarget.left;
					pathStack.push(nextTarget);
				}
			}
			canRemove = true;
			return iterTarget.data;
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
				canRemove = false;
				TreeList.this.remove(iterTarget, lastTarget);
			} else {
				throw new IllegalStateException("\n\t\tthe next() method has not yet been called, \n" +
						"\t\tor the remove() method has already been called after the last call to the next() method");
			}
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

		/**
		 * creates a basic node that holds data and instantiates the left and right node pointers to null.
		 *
		 * @param data a generic data type that the client declares at the instantiation of the binary tree.
		 */
		BinaryTreeNode(E data) {
			this(data, null, null);
		}

		/**
		 * @param data
		 * @param left
		 * @param right
		 */
		private BinaryTreeNode(E data, BinaryTreeNode left, BinaryTreeNode right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}

		private boolean hasLeft() {
			return left != null;
		}

		private boolean hasRight() {
			return right != null;
		}
	}
}
