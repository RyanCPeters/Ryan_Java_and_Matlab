import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * @author Ryan Peters
 * @date 3/17/2017
 */
public class HashDict<K, V> implements IDict<K, V>, Iterable {
	private static final int DEFAULT_CAP = 16;
	private int size;
	private int cap;//bucket capacity for the hash table
	private HashEntry[] theArray;
	private HashEntry curr;
	private HashEntry prev;

	/**
	 *
	 */
	public HashDict() {
		this(0, DEFAULT_CAP);
	}

	/**
	 * @param size
	 * @param cap
	 */
	@SuppressWarnings("unchecked")
	private HashDict(int size, int cap) {
		this.size = size;
		this.cap = cap;
		this.theArray = (HashEntry[]) new Object[cap];
	}

	/**
	 * @param initCapacity
	 */
	public HashDict(int initCapacity) {
		this(0, initCapacity);
	}

	/**
	 * gets the number of element pairs in this dictionary
	 *
	 * @return the number of key-value pairs in this dictionary
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * checks if the dictionary contains any key-value pairs, returning true if there are none.
	 *
	 * @return true if size of the dictionary is zero, false if >0.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * return the value to which the specified key is mapped, or null if this dictionary contains no mapping for the key.
	 *
	 * @param key the key to some value that may or may not be in this dictionary, depends upon if key has a mapping.
	 * @return the value associated with this key, or null if key is not mapped.
	 */
	@Override
	public V get(K key) {
		return (setCurr(key)) ? curr.value : null;
	}

	/**
	 * Associates the given value with the given key in this dictionary.
	 *
	 * @param key   the given key for mapping the given value in the dictionary
	 * @param value the value to be stored at the position the given key will be mapped to.
	 * @return any value that had been previously mapped to the given key.
	 */
	@Override
	public V put(K key, V value) {
		V oldVal = null;
		if (setCurr(key)) {
			oldVal = curr.value;
			curr.value = value;
		} else {
			while (curr.next != null) {
				curr = curr.next;
			}
			curr = new HashEntry(key, value);
		}
		size++;
		return oldVal;
	}

	/**
	 * replace the pair for the specified key only if the pair
	 * currently maps key to a non-null value.
	 *
	 * @param key   Is this key supposed to be paired to the
	 *              new value or the old?
	 * @param value the given value with which any non-null preexisting value should be replaced.
	 * @return returns the previous value associated with the
	 * specified key, or null if there was no mapping for key.
	 */
	@Override
	public V replace(K key, V value) {
		V oldVal = null;
		if (setCurr(key)) {
			oldVal = curr.value;
			curr.value = value;
		}
		return oldVal;
	}

	/**
	 * remove from this dictionary the pair whos key is the specified key if
	 * such pair is present.
	 *
	 * @param key
	 * @return the old value associated with the given key, or null if there is none.
	 */
	@Override
	public V remove(K key) {
		V oldVal = null;
		if (setCurr(key)) {
			oldVal = curr.value;
			removeCurr();

		}
		size--;
		return oldVal;
	}

	/**
	 * Remove from this dictionary thepair whos key is the specified key ONLY if the specified key is currently mapped
	 * to the specified value.
	 *
	 * @param key
	 * @param value
	 * @return true if the pair was removed
	 */
	@Override
	public boolean remove(K key, V value) {
		V oldVal = null;
		if (setCurr(key) && curr.value == value) {
			oldVal = curr.value;
			removeCurr();
			size--;
		}
		return oldVal != null;
	}

	/**
	 * return true is this dictionary contains a pair for the specified key
	 *
	 * @param key
	 * @return
	 */
	@Override
	public boolean containsKey(K key) {
		return setCurr(key);
	}

	/**
	 * return true if this dictionary maps to one or more keys to the specified value
	 *
	 * @param value
	 * @return
	 */
	@Override
	public boolean containsValue(V value) {
		boolean contained = false;
		for (int i = 0; i < size && !contained; i++) {
			curr = theArray[i];
			while (curr.hasNext() && !contained) {
				contained = (curr.value == value);
				curr = curr.next;
			}
		}
		return contained;
	}

	/**
	 * Empty the dictionary and set the size to zero.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void clear() {
		this.theArray = (HashEntry[]) new Object[cap];
		size = 0;
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
		HashIter iter = new HashIter();
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

	/*this private helper method takes a key value and maps it to it's rightful position in the dictionary, and then
		 * it returns a boolean according to if there was a pre-existing mapping to that same position.
		 */
	private boolean setCurr(K key) {
		prev = null;
		curr = theArray[Math.abs(key.hashCode() % cap)];
		boolean preMapped = (curr.key == key);
		while (!preMapped && curr.next != null) {
			prev = curr;
			curr = curr.next;
			preMapped = (curr.key == key);
		}

		return preMapped;
	}

	/* this private method is set up under the assumption that it will only be called after setCurr() has run.
	 */
	private void removeCurr() {
		if (prev != null) {
			prev.next = curr.next;
		} else if (curr.hasNext()) {
			curr.value = curr.next.value;
			if (curr.next.hasNext()) {
				curr.next = curr.next.next;
			} else {
				curr.next = null;
			}
		}
	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator iterator() {
		return new HashIter();
	}

	/**
	 * The private inner class responsible for tracking the mapping of values and their key within the hash table.
	 */
	private class HashEntry {
		private K key;
		private V value;
		private HashEntry next;

		/**
		 * @param key
		 * @param value
		 */
		protected HashEntry(K key, V value) {
			this.key = key;
			this.value = value;
			next = null;
		}

		protected HashEntry() {
			this(null, null);
		}

		private boolean hasNext() {
			return curr.next != null;
		}
	}

	private class HashIter implements Iterator {
		private int pos;
		private int idx;
		private HashEntry targetEntry;

		HashIter() {
			// just to clearly spell it out that we start as nulls :)
			targetEntry = null;

			idx = pos = -1;
		}

		/**
		 * Returns {@code true} if the iteration has more elements.
		 * (In other words, returns {@code true} if {@link #next} would
		 * return an element rather than throwing an exception.)
		 *
		 * @return {@code true} if the iteration has more elements
		 */
		@Override
		public boolean hasNext() {
			return pos + 1 < size;
		}

		/**
		 * Returns the next element in the iteration.
		 *
		 * @return the next element in the iteration
		 * @throws NoSuchElementException if the iteration has no more elements
		 */
		@Override
		public V next() {
			if (!hasNext()) throw new NoSuchElementException("the iterator has reached the end dictionary");
			if (idx == -1) {
				targetEntry = theArray[++idx];

			} else {
				if (targetEntry.hasNext()) {
					targetEntry = targetEntry.next;
				} else if (idx + 1 < theArray.length) {
					targetEntry = theArray[++idx];
				}
			}
			if (targetEntry.value == null)
				throw new NoSuchElementException("there was no data value saved for this entry");
			return targetEntry.value;
		}
	}
}
