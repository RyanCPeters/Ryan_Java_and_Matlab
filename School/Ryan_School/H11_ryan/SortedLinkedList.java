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

	public SortedLinkedList() {
		size = 0;
		head = head.next = tail = tail.next = null;

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
		head = head.next;
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
		head = tail = null;
	}

	/**
	 * Compares this object with the specified object for order.  Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 * <p>
	 * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
	 * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
	 * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
	 * <tt>y.compareTo(x)</tt> throws an exception.)
	 * <p>
	 * <p>The implementor must also ensure that the relation is transitive:
	 * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
	 * <tt>x.compareTo(z)&gt;0</tt>.
	 * <p>
	 * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
	 * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
	 * all <tt>z</tt>.
	 * <p>
	 * <p>It is strongly recommended, but <i>not</i> strictly required that
	 * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
	 * class that implements the <tt>Comparable</tt> interface and violates
	 * this condition should clearly indicate this fact.  The recommended
	 * language is "Note: this class has a natural ordering that is
	 * inconsistent with equals."
	 * <p>
	 * <p>In the foregoing description, the notation
	 * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
	 * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
	 * <tt>0</tt>, or <tt>1</tt> according to whether the value of
	 * <i>expression</i> is negative, zero or positive.
	 *
	 * @param o the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException   if the specified object's type prevents it
	 *                              from being compared to this object.
	 */
	@Override
	public int compareTo(E o) {
		return 0;
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
			this.pos = 0;
			this.canRemove = false;
			this.prev = this.curr = head;

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
			if(pos > 0) {
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
		Node(E value, Node next){
			this.value = value;
			this.next = next;
		}
		Node(E value){
			this(value, null);
		}
	}
}
