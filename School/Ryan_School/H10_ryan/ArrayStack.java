
import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * @author Ryan Peters
 * @date 2/18/2017
 */
public class ArrayStack<E> implements IStack<E> {
	// a default capacity setting for the initialization of the array should the client not specifiy one
	private final int DEFAULTCAP = 20;
	// the cap value is a convenience reference for where the last index position in the array is.
	private int cap;
	// the head is the index position of the last-in object.
	private int head;
	// the array form of the element type for the stack that we will store data in
	private E[] theArray;
	// the number of elements in the array
	private int size;

	/**
	 * @param initCap
	 */
	public ArrayStack(int initCap) {
		this();
		this.theArray = (E[]) new Object[initCap];
		this.cap = initCap;
	}

	/**
	 *
	 */
	public ArrayStack() {
		this.theArray = (E[]) new Object[DEFAULTCAP];
		this.cap = 0;
		this.size = 0;
		this.head = 0;
	}

	/**
	 * @return
	 */
	int getCapacity() {
		return cap;
	}

	/**
	 * @param capacity
	 */
	void ensureCapacity(int capacity) {
		double mult = 2.0;
		if (capacity >= cap) {
			if (cap > 1000000) mult = 1.25;
			cap = (int) ((capacity) * mult);
			theArray = Arrays.copyOf(theArray, cap);
		}
	}

	/**
	 * @return
	 */
	public String toString() {
		String str = "\n{ ";
		if (!isEmpty()) {
			int idx = 0;
			while (idx < head) {
				str += theArray[idx++] + ", ";
			}
			str += theArray[idx];
		}
		str += " }size = " + size;
		return str;
	}

	/**
	 * @return
	 */
	@Override
	public int getSize() {
		return size;
	}

	/**
	 * @return
	 */
	@Override
	public boolean isEmpty() {
		return (size <= 0);
	}

	@Override
	public void push(E element) {

		ensureCapacity(++size);
		theArray[head++] = element;

	}

	/**
	 * @return
	 * @throws EmptyStackException
	 */
	@Override
	public E pop() throws EmptyStackException {
		if (isEmpty()) throw new EmptyStackException();
		size--;
		return theArray[--head];
	}

	/**
	 * @return
	 */
	@Override
	public E peek() {
		if (isEmpty()) return null;
		else return theArray[head];
	}


}
