import java.util.Iterator;
import java.util.NoSuchElementException;

/**A single link list of nodes that store generic data objects. Those data objects must be of class types that implement
 * the Comparable Interface.
 * @author Ryan Peters
 * @date 3/5/2017
 */
public class SortedLinkedList<E extends Comparable> implements ISortedList<E>{
	private int size;
	private Node tail;
	private Node head;
	private Node anteriorAddHead;
	private Node anteriorAddTail;
	private enum HeadOrTail {HEAD, TAIL, MID, CURR, PREV}



	/**
	 * creates an empty list with a default setting for the head and tail nodes being null.
	 */
	public SortedLinkedList() {
		this.size = 0;
		this.head = new Node(HeadOrTail.HEAD);
		this.tail = new Node(HeadOrTail.TAIL);
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
		if (head.next == null) throw new IllegalStateException("head.next == null");
		return head.next.value;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public E getTail() {
		if (tail == null) throw new IllegalStateException("tail == null");
		return tail.value;
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
		// this add method is used for data that needs to be meshed inside of the existing set
		if (size > 0) {
			MyIter iter = new MyIter();
			boolean thereYet = false;

			boolean preInsert = false;
			/* true for pre-insert(default behavior of my insert method), false for
			* post-insert; pre and post insertion should be thought of in relation to
			* the position of the data given to the iterVal variable.*/
			while (iter.hasNext() && !thereYet) {
				E iterVal = iter.next();
				if (iterVal != null && iterVal.compareTo(value) >= 0) {
					iter.prependInsertion(value);
					thereYet = true;
				} else if (iter.getCurrPointer() == tail || iter.getCurrPointer().next == null) {
					iter.postpendInsertion(value);
					thereYet = true;
				}
			}
		} else {
			// this represents the initial non head/tail node to be added to the list
			head.next = tail = new Node(value, tail.next);
		}
		size++;
	}

	/**
	 * As the name implies, this method adds new elements to the back end of the current collection of data.
	 * This is a private method, and as such it is used under the assumption that the new data set being
	 * added contains only data that is larger than that of the current set.
	 *
	 * @param val a single element from the external data set.
	 */
	private void append(E val) {
		tail = tail.next = new Node(val);
		size++;
	}

	/**
	 * as with the append, the name of this method emplies its use. This method adds data on to the front of the
	 * current set under the assumption the external data has been vetted to only contain elements smaller than any
	 * contained in this set.
	 *
	 * @param val a single element from the external data set
	 */
	private void prepend(E val) {
		anteriorAddTail = anteriorAddTail.next = new Node(val);
		size++;
	}

	/**
	 *
	 * @param other
	 */
	@Override
	public void addAll(ISortedList<E> other) {
		if (other.getHead().compareTo(this.getTail()) > 0) {
//			System.out.println("reached an append state for addAll");
			for (E elem : other) {
//				System.out.println(this);
				append(elem);
//				System.out.println(this);
			}
//			System.out.println("post append condition");
		} else if (head.next.value.compareTo(other.getTail()) > 0) {
//			System.out.println("reached a prependInsertion state for addAll\n");
			anteriorAddHead = new Node(null, HeadOrTail.HEAD);
			for (E elem : other) {
//				System.out.println(elem);
//				System.out.println(this);
				if (anteriorAddHead.next == null) {

					anteriorAddHead.next = anteriorAddTail = new Node(elem);
					size++;
				} else {
//					System.out.println("about to prependInsertion :D");
					prepend(elem);
				}
//				System.out.println(this);
			}
//			System.out.println("post prependInsertion condition\n");
			anteriorAddTail.next = head.next;
			head.next = anteriorAddHead.next;
			anteriorAddHead = anteriorAddTail = null;
		}else {
//			System.out.println("entered an insertion add state for addAll");
			for (E elem : other) {
//				System.out.println(elem);
				add(elem);
			}
//			System.out.println("post insertaion add condition");
		}
	}

	/**
	 *
	 * @return
	 */
	@Override
	public E removeHead() {
		if (size == 0 || head.next == null) return null;
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
		if (tail.next == null) return null;
		E val = tail.next.value;
		MyIter iter = (MyIter)iterator();
		while (iter.hasNext()) {
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

			if (iter.next().compareTo(value) == 0) {
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
		head.next = tail.next = null;
		size = 0;
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
		Iterator iter = new MyIter();
		String s = "{ ";
		if (iter.hasNext()) s += iter.next();
		while (iter.hasNext()) {
			s += ", "+iter.next();

		}
		s += " }";
		return s;
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

		private Node getPrevPointer(){
			return prev.next;
		}

		private void prependInsertion(E value) {
			prev.next.next = new Node(value, curr.next);
			pos++;
		}

		private void postpendInsertion(E value) {
			if (curr.next == tail) {
				tail = tail.next = new Node(value);
			} else if (curr.next.next == null) {
				curr.next.next = tail = new Node(value);
			} else {
				curr.next.next = new Node(value, curr.next.next);
			}

		}

		private Node getCurrPointer() {
			return curr.next;
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
			return pos + 1 < size && head.next != null;
		}

		/**
		 * Returns the next element in the iteration.
		 *
		 * @return the next element in the iteration
		 * @throws NoSuchElementException if the iteration has no more elements
		 */
		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException("there isn't a next node to reference");
			}
			if(curr.next.next != null && curr.next.next.value == null) {
				throw new NoSuchElementException("there is a node here, but no data is stored");

			}else if(curr.next.next == null){
				return null;
			}


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
				if (pos == 0) {
					head.next = head.next.next;
				} else if (curr.next.next == null || curr.next.next.myType == HeadOrTail.TAIL) {
					curr.next = prev.next.next = tail.next;
				} else {
					curr.next = prev.next.next = curr.next.next;
				}
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
