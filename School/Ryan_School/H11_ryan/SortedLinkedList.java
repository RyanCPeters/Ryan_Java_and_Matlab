import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Ryan Peters
 * @date 3/5/2017
 */
public class SortedLinkedList<E extends Comparable> implements ISortedList<E>{
	private int size;
	private Node head;
	private Node tail;
	private Node root;
	public SortedLinkedList() {
		size = 0;
		root = new Node(true);
		head = head.next = tail = tail.next = null;

		root.next = head;

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
		return head.value;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public E getTail() {
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
			if(target.compareTo(value) == 0)return iter.getPos();
		}
		return -1;
	}


	@Override
	public boolean contains(E value) {
		if (indexOf(value) < 0) {
			return false;
		}
		return true;
	}

	@Override
	public void add(E value) {
		tail = tail.next = new Node(value);
		tail.next = null;
		size++;

	}

	@Override
	public void addAll(ISortedList<E> other) {
		Iterator<E> iter = other.iterator();
		while(iter.hasNext()){
			add(iter.next());
		}
	}

	@Override
	public E removeHead() {
		E val = head.value;
		root.next = head = head.next;
		size--;
		return val;
	}

	@Override
	public E removeTail() {
		E val = tail.value;
		MyIter iter = (MyIter)iterator();
		while(iter.hasNext() && iter.getNode().next != tail){
			iter.next();
		}
		tail = iter.getNode();
		tail.next = null;
		size--;
		return val;
	}

	@Override
	public boolean remove(E value) {
		MyIter iter = (MyIter)iterator();
		while(iter.hasNext()){
			E check = iter.next();
			if(check.compareTo(value) == 0){
				iter.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public void clear() {
		root.next = head = head.next = tail = tail.next = null;
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
	private class MyIter implements Iterator<E> {
		private int pos;
		private boolean canRemove;
		private Node curr;
		private Node prev;

		public MyIter() {
			this.pos = -1;
			this.canRemove = false;
			this.prev = this.curr = root;

		}


		protected Node getNode(){return curr;}
		protected int getPos(){return pos;}

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
		public E next() throws NoSuchElementException{
			if(curr == null)return null;
			if(!curr.isRoot) {
				prev = curr;
				curr = curr.next;
			}
			pos++;

			return curr.value;
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
			if(canRemove){
				prev.next = curr = curr.next;
				canRemove = false;
				size--;
			}
		}
	}

	private class Node {
		E value;
		Node next;
		boolean isRoot;
		Node(E value, Node next){
			this.value = value;
			this.next = next;
		}
		Node(E value){
			this(value, null);
		}

		Node(boolean isRoot) {
			this(null, null);
			this.isRoot = isRoot;
		}
	}
}
