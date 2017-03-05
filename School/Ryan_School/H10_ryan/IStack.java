

import java.util.EmptyStackException;

/**
 * @author Ryan Peters
 * @date 2/18/2017
 */
public interface IStack<E> {
	/**
	 * This method is used in getting a count of how many elements are in the array object implementing this interface
	 *
	 * @return
	 */
	public int getSize();

	/**
	 * provides a means to determine if the stack is empty
	 *
	 * @return true if size == 0, false if size > 0
	 */
	public boolean isEmpty();

	public void push(E element);

	/**
	 * This method should check if the stack is empty, if it is then it will throw an EmptyStackException, otherwise it
	 * removes the top object from the stack and returns it.
	 *
	 * @return the most recently added object in the stack, of type E
	 * @throws EmptyStackException
	 */
	public E pop() throws EmptyStackException;

	/**
	 * This method functions similarly to the pop method, but it doesn't throw an exception if there is nothing to
	 * return, instead it returns null.
	 *
	 * @return
	 */
	public E peek();
}
