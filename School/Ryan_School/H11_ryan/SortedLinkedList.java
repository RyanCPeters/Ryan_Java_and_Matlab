import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Ryan Peters
 * @date 3/5/2017
 */
public class SortedLinkedList<E extends Comparable> implements ISortedList<E>{
	private int size;
	private Node tail;
	private Node head;

	private enum HeadOrTail {HEAD, TAIL, MID, CURR, PREV}

	;

	/**
	 * creates an empty list with a default setting for the head and tail nodes being null.
	 */
	public SortedLinkedList() {
		size = 0;
		head = new Node(HeadOrTail.HEAD);
		tail = new Node(HeadOrTail.TAIL);
	}

	/**returns the number of elements in the list
	 *
	 * @return an int value for the number of elements
	 */
	@Override
	public int size() {
		return size;
	}

	/**returns the boolean statement for if the list has no elements and is thus empty.
	 *
	 * @return true if the list is empty, otherwise false.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**returns the first element in the list without removing that element from the list.
	 *
	 * @return the first element in the list.
	 */
	@Override
	public E getHead() {
		return head.next.value;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public E getTail() {
		return tail.next.value;
	}

	/**returns the first index position of the node in the sorted list that contains the given value, if
	 * no elements contain that value then this method returns -1.
	 *
	 * @param value
	 * @return
	 */
	@Override
	public int indexOf(E value) {
		MyIter iter = (MyIter) iterator();
		while(iter.hasNext()){
			E target = iter.next();
			if (target.compareTo(value) == 0) return iter.pos;
		}
		return -1;
	}

	/**
	 * @param value
	 * @return
	 */
	@Override
	public boolean contains(E value) {
		if (indexOf(value) < 0) return false;
		return true;
	}

	/**
	 *
	 * @param value
	 */
	@Override
	public void add(E value) {
		MyIter iter = new MyIter();
		boolean thereYet = false;
		E iterVal;
		if (size > 0) {

			while (iter.hasNext() && !thereYet) {
				iterVal = (iter.peepNextVal() != null) ? iter.next() : null;
				if (iter.checkForEndsNear() == HeadOrTail.TAIL) {
					iter.insert(value);
					thereYet = true;
				} else if (iter.checkForEndsFar() == HeadOrTail.TAIL) {
					thereYet = true;
				} else if (iterVal != null && iterVal.compareTo(value) > 0) {
					iter.insert(value);
					thereYet = true;
				}
			}


		} else {
			tail.next = head.next = new Node(value);
		}
		size++;
	}

	/**
	 *
	 * @param other
	 */
	@Override
	public void addAll(ISortedList<E> other) {
		Iterator<E> otherIter = other.iterator();
		if (!checkOrdered(other)) {

		}
		while (otherIter.hasNext()) {
			add(otherIter.next());
		}
	}

	private boolean checkOrdered(ISortedList<E> other) {
		boolean isOrdered;
		Iterator otherIter = other.iterator();

		E value1 = otherIter.hasNext() ? (E) otherIter.next() : null;
		E value2 = otherIter.hasNext() ? (E) otherIter.next() : null;

		isOrdered = value1 != null && ((value2 != null && (value1.compareTo(value2) < 0)) || value2 == null);

		while (otherIter.hasNext() && isOrdered) {
			value1 = otherIter.hasNext() ? (E) otherIter.next() : null;
			value2 = otherIter.hasNext() ? (E) otherIter.next() : null;

			if (value1 != null) {

				if ((value2 != null && value1.compareTo(value2) < 0) || value2 == null) isOrdered = true;
				else return false;

			} else return false;
		}// close of while loop
		return true;
	}// end of checkOrdered method

	/**
	 *
	 * @return
	 */
	@Override
	public E removeHead() {
		if (size == 0) return null;
		E val = head.next.value;
		head.next = head.next.next;
		size--;
		return val;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public E removeTail() {
		E val = tail.next.value;
		MyIter iter = (MyIter)iterator();
		while (iter.checkForEndsFar() != HeadOrTail.TAIL){
			iter.next();
		}
		tail.next = iter.prev.next;
		size--;
		return val;
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	@Override
	public boolean remove(E value) {
		MyIter iter = (MyIter)iterator();
		while (iter.hasNext()) {
			E check = iter.peepCurrVal();
			if(check.compareTo(value) == 0){
				iter.remove();
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 */
	@Override
	public void clear() {
		head.next = tail = null;
	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator<E> iterator() {
		return new MyIter();
	}

	/** A custom iterator set up for traversing the nodes in a this sorted single linked list class object.
	 *
	 */
	private class MyIter implements Iterator<E> {
		private int pos;
		private boolean canRemove;
		private Node curr;
		private Node prev;


		/**
		 *
		 */
		public MyIter() {
			this.pos = -1;
			this.canRemove = false;
			this.curr = new Node(head, HeadOrTail.CURR);
			this.prev = new Node(head, HeadOrTail.PREV);

		}

		/**
		 * reveals the value stored at the node that curr is currently pointing at without advancing current
		 *
		 * @return the value stored at the node that curr is currently pointing at
		 */
		private E peepCurrVal() {
			return curr.next.value;
		}

		private E peepNextVal() {
			return curr.next.next.value;
		}

		/**
		 * This method helps check if the node following the one that curr is pointing at has the TAIL enum flag.
		 *
		 * @return the HeadOrTail enum flag assigned to the node following the current target of curr.
		 */
		private HeadOrTail checkForEndsFar() {
			return (curr.next.next != null) ? curr.next.next.myType : HeadOrTail.TAIL;
		}

		/**
		 * This method allows the checking of the HeadOrTail enum flag for the current node.
		 *
		 * @return the HeadOrTail enum flag assigned to the current target node of curr.
		 */
		private HeadOrTail checkForEndsNear() {
			return curr.next.myType;
		}

		/**
		 * A method for inserting a new node directly before the node curr is pointing at. It utilizes the fact that
		 * the iterator already has a pointer for both current target node and the immediately preceding node.
		 *
		 * @param val
		 */
		private void insert(E val) {
			Node n = new Node(val, curr.next.next);
			prev.next.next = curr.next = n;
		}

		/**
		 * Returns true if the iteration has more elements.
		 * (In other words, returns true if next() would
		 * return an element rather than throwing an exception.)
		 *
		 * @return {@code true} if the iteration has more elements
		 */
		@Override
		public boolean hasNext() {
			return pos+1 < size;
		}

		/**
		 * Returns the next element in the iteration.
		 *
		 * @return the next element in the iteration
		 * @throws NoSuchElementException if the iteration has no more elements
		 */
		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) throw new NoSuchElementException("there isn't a next node to reference");
			if (curr.next.next.value == null)
				throw new NoSuchElementException("there is a node here, but no data is stored");
			prev.next = curr.next;
			curr.next = curr.next.next;
			pos++;
			canRemove = true;
			return curr.next.value;
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
				if (pos == 0) head.next = head.next.next;
				if (curr.next.next.myType == HeadOrTail.TAIL) tail.next = prev.next;
				prev.next.next = curr.next.next;
				curr.next = prev.next;
				canRemove = false;
				size--;
			} else {
				throw new IllegalStateException("\n\t\tthe next() method has not yet been called, \n" +
						"\t\tor the remove() method has already been called after the last call to the next() method");
			}
		}
	}

	/**
	 * the single linked nodes that are used to build the sorted list.
	 */
	private class Node {
		E value;
		Node next;
		HeadOrTail myType = HeadOrTail.MID;

		/** sets the generic value to the given value and the next Node pointer to the given pointer.
		 *
		 * @param value a generic comparable object
		 * @param next a single link list node
		 */
		Node(E value, Node next) {
			this.value = value;
			this.next = next;
		}

		/** sets the generic value to the given value then setns the next Node pointer to be null.
		 *
		 * @param value a generic comparable object
		 */
		Node(E value) {
			this(value, null);
		}

		/**
		 * Sets both the next Node pointer and the enum HeadOrTail flag to the given parameters then sets the value
		 * field to be null.
		 *
		 * @param next a single link list node
		 * @param type the enum HeadOrTail flag used to identify the head and tail Nodes in the list.
		 */
		Node(Node next, HeadOrTail type) {
			this(null, next);
			this.myType = type;
		}

		/** sets the HeadOrTail enum flag to the given parameter, then sets the generic value field along with the
		 *  next Node pointer to be null.
		 *
		 * @param type the enum HeadOrTail flag used to identify the head and tail Nodes in the list.
		 */
		Node(HeadOrTail type) {
			this(null, type);
		}
	}
}
