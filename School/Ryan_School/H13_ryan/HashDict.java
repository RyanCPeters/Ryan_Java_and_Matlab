/**
 * @author Ryan Peters
 * @date 3/17/2017
 */
public class HashDict<K, V> implements IDict<K, V> {
	private static final int DEFAULT_CAP = 16;
	private int size;
	private int cap;//bucket capacity for the hash table
	private HashEntry<K, V>[] theArray;

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
		this.theArray = (HashEntry<K, V>[]) new HashEntry[cap];
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
	 * checks if the dictionary contains any key-value pairs, returning true if there
	 * are none.
	 *
	 * @return true if size of the dictionary is zero, false if >0.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * return the value to which the specified key is mapped, or null if this
	 * dictionary contains no mapping for the key.
	 *
	 * @param key the key to some value that may or may not be in this dictionary,
	 *            depends upon if key has a mapping.
	 * @return the value associated with this key, or null if key is not mapped.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public V get(K key) {
		HashEntry nodeGetter = theArray[Math.abs(key.hashCode()) % cap];
		while (nodeGetter.getKey().hashCode() != key.hashCode() && nodeGetter.hasNext()) {
			nodeGetter = nodeGetter.next;
		}
		if (nodeGetter.getKey() == key) {
			return (V) nodeGetter.value;
		}
		return null;
	}

	/**
	 * Associates the given value with the given key in this dictionary.
	 *
	 * @param key   the given key for mapping the given value in the dictionary
	 * @param value the value to be stored at the position the given key will be
	 *              mapped to.
	 * @return any value that had been previously mapped to the given key.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		HashEntry<K, V> nodePuter = theArray[Math.abs(key.hashCode()) % cap];
		V oldVal = null;
		if (nodePuter == null) {
			theArray[Math.abs(key.hashCode()) % cap] = new HashEntry<>(key, value);
			return oldVal;
		}
		if (nodePuter.key.hashCode() == key.hashCode()) {
			oldVal = nodePuter.value;
			nodePuter.value = value;
		} else if (nodePuter != null) {
			while (nodePuter.next != null) {
				nodePuter = nodePuter.next;
			}
			if (nodePuter.value != null) {
				nodePuter.next = new HashEntry(key, value);
			} else {
				nodePuter.key = key;
				nodePuter.value = value;
			}
		} else {
			theArray[Math.abs(key.hashCode()) % cap] = new HashEntry<>(key, value);
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
	 * @param value the given value with which any non-null preexisting value should
	 *              be replaced.
	 * @return returns the previous value associated with the
	 * specified key, or null if there was no mapping for key.
	 */
	@Override
	public V replace(K key, V value) {
		HashEntry<K, V> nodePuter = theArray[Math.abs(key.hashCode()) % cap];
		V oldVal = null;

		if (nodePuter.key.hashCode() == key.hashCode()) {
			oldVal = nodePuter.value;
			nodePuter.value = value;
		} else {
			while (nodePuter.key.hashCode() != key.hashCode() && nodePuter.hasNext()) {
				nodePuter = nodePuter.next;
			}
			if (nodePuter.key.hashCode() == key.hashCode()) {
				oldVal = nodePuter.value;
				nodePuter.value = value;
			}
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
		HashEntry<V, K> prev = null;
		HashEntry setUpRemove = theArray[Math.abs(key.hashCode()) % cap];
		if (setUpRemove != null) {

			while (setUpRemove.getKey().hashCode() != key.hashCode() && setUpRemove.hasNext()) {
				prev = setUpRemove;
				setUpRemove = setUpRemove.next;
			}
			if (setUpRemove.getKey().hashCode() != key.hashCode()) return oldVal;
			oldVal = (V) setUpRemove.value;
			if (prev != null) {
				prev.next = setUpRemove.next;
			} else {
				theArray[Math.abs(key.hashCode()) % cap] = null;
			}
		} else {
			return oldVal;
		}
		size--;
		return oldVal;
	}

	/**
	 * Remove from this dictionary thepair whos key is the specified key ONLY if the
	 * specified key is currently mapped
	 * to the specified value.
	 *
	 * @param key
	 * @param value
	 * @return true if the pair was removed
	 */
	@Override
	public boolean remove(K key, V value) {
		V oldVal = null;
		HashEntry<V, K> prev = null;
		HashEntry setUpRemove = theArray[Math.abs(key.hashCode()) % cap];
		if (setUpRemove != null) {

			while (setUpRemove.getKey().hashCode() != key.hashCode() && setUpRemove.value != value && setUpRemove.hasNext()) {
				prev = setUpRemove;
				setUpRemove = setUpRemove.next;
			}
			if (setUpRemove.getKey().hashCode() != key.hashCode()) return false;
			if (setUpRemove.value != value) return false;
			oldVal = (V) setUpRemove.value;
			if (prev != null) {
				prev.next = setUpRemove.next;
			} else {
				theArray[Math.abs(key.hashCode()) % cap] = null;
			}
		}
		size--;
		return oldVal != null;
	}

	/**
	 * return true is this dictionary contains a pair for the specified key
	 *
	 * @param key
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean containsKey(K key) {
		HashEntry<K, V> keyFinder = theArray[Math.abs(key.hashCode()) % cap];
		if (keyFinder == null) return false;
		while (keyFinder.key.hashCode() != key.hashCode() && keyFinder.hasNext()) {
			keyFinder = keyFinder.next;
		}
		return keyFinder.key.hashCode() == key.hashCode();
	}

	/**
	 * return true if this dictionary maps to one or more keys to the specified value
	 *
	 * @param value
	 * @return
	 */
	@Override
	public boolean containsValue(V value) {
		HashEntry valFinder;
		boolean contained = false;
		for (int i = 0; i < theArray.length && !contained; i++) {
			valFinder = theArray[i];
			if (valFinder != null) {
				contained = (valFinder.value == value);
				while (valFinder.hasNext() && !contained) {
					valFinder = valFinder.next;
					contained = (valFinder.value == value);
				}
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
		HashEntry hashStringer;
		int idx = 0;
		while (theArray[idx] == null && idx < theArray.length) idx++;
		if (idx >= theArray.length) return "{ }";
		hashStringer = theArray[idx];
		StringBuilder myString = new StringBuilder("{ ");
		if (hashStringer.value != null) myString.append(hashStringer.value);
		int counter = 1;
		while (idx + 1 < theArray.length && idx > -1) {
			if (counter % 15 == 0) myString.append("\n");
			if (hashStringer.hasNext()) {
				hashStringer = hashStringer.next;
			} else {
				idx++;
				while (theArray[idx] == null && idx < theArray.length) idx++;
				if (idx >= theArray.length) return myString + " }";
				hashStringer = theArray[idx];
			}
			if (hashStringer != null && hashStringer.value != null) myString.append(", " + hashStringer.value);
			else idx = -1;
			counter++;
		}
		myString.append(" }");
		return myString.toString();
	}

	/**
	 * The private inner class responsible for tracking the mapping of values and
	 * their key within the hash table.
	 */
	private class HashEntry<k, v> {
		private k key;
		private v value;
		private HashEntry next;

		/**
		 * @param key
		 * @param value
		 */
		protected HashEntry(k key, v value) {
			this.key = key;
			this.value = value;
			next = null;
		}

		protected HashEntry() {
			this(null, null);
		}

		private HashEntry(HashEntry next) {
			this.key = null;
			this.value = null;
			this.next = next;
		}
		private boolean hasNext() {
			return next != null;
		}

		private boolean emptyEntry() {
			return key == null || value == null;
		}

		private k getKey() {
			return key;
		}

		private v getVal() {
			return value;
		}
	}
}
