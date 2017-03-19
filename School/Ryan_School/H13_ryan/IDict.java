public interface IDict<K, V> {
	int size();

	boolean isEmpty();

	V get(K key);

	V put(K key, V value);

	V replace(K key, V value);

	V remove(K key);

	boolean remove(K key, V value);

	boolean containsKey(K key);

	boolean containsValue(V value);

	void clear();
}
