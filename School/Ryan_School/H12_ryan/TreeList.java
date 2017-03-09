import java.util.Iterator;

/**
 * @author Ryan Peters
 * @date 3/9/2017
 */
public class TreeList<E extends Comparable> implements ISortedList<E> {
	private int size;
	private BinaryTreeNode root;

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public E getHead() {
		return null;
	}

	@Override
	public E getTail() {
		return null;
	}

	@Override
	public int indexOf(E value) {
		return 0;
	}

	@Override
	public boolean contains(E value) {
		return false;
	}

	@Override
	public void add(E value) {

	}

	@Override
	public void addAll(ISortedList other) {

	}

	@Override
	public E removeHead() {
		return null;
	}

	@Override
	public E removeTail() {
		return null;
	}

	@Override
	public boolean remove(E value) {
		return false;
	}

	@Override
	public void clear() {

	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator iterator() {
		return null;
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
		return super.toString();
	}

	private class BinaryTreeNode {
		public E Data;
		public BinaryTreeNode left;
		public BinaryTreeNode right;

		public BinaryTreeNode(E data) {
			Data = data;
		}

		public BinaryTreeNode(E data, BinaryTreeNode left, BinaryTreeNode right) {
			Data = data;
			this.left = left;
			this.right = right;
		}
	}
}
