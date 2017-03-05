public interface ISortedList<E> extends Iterable<E>, Comparable<E> {
    public int size();
    public boolean isEmpty();
    public E getHead();
    public E getTail();
    public int indexOf(E value);
    public boolean contains(E value);
    public void add(E value);
    public void addAll(ISortedList<E> other);
    public E removeHead();
    public E removeTail();
    public boolean remove(E value);
    public void clear();
}
