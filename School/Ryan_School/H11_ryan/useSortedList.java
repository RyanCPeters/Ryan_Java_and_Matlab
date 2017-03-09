import java.util.Iterator;

/**
 * A handful of test cases
 * @author Charlie on 3/7/2017.
 */
public class useSortedList {
	public static void main(String[] args) {
		SortedLinkedList<String> list1 = new SortedLinkedList<>();
		SortedLinkedList<String> list2 = new SortedLinkedList<>();

//	    for (int i = 1; i < 101; i++) {
//		    if (i < 51) list1.add(i);
//		    else list2.add(i);
//	    }
//	    System.out.println("list1 pre-addAll() with list1 < 51: " + list1);
//	    System.out.println("list2 pre-addAll() with list2 > 50: " + list2);
//	    list1.addAll(list2);// this should use the posterior add method
//	    System.out.println("list1 post-addAll() with list1 < 51: " + list1);
//	    System.out.println("list2 post-addAll() with list2 > 50: " + list2);
//
//
//	    list1 = new SortedLinkedList<>();
//	    list2 = new SortedLinkedList<>();
//	    System.out.println("-----------------------------------------------------");
//	    for (int i = 1; i < 101; i++) {
//		    if (i > 50) list1.add(i);
//		    else list2.add(i);
//	    }
//	    System.out.println("list1 pre-addAll() with list1 > 50: " + list1);
//	    System.out.println("list2 pre-addAll() with list2 < 51: " + list2);
//	    list1.addAll(list2);// this should use the anterior add method
//	    System.out.println("list1 post-addAll() with list1 > 50: " + list1);
//	    System.out.println("list2 post-addAll() with list2 < 51: " + list2);

//
//	    list1 = new SortedLinkedList<>();
//	    list2 = new SortedLinkedList<>();
//	    System.out.println("-----------------------------------------------------");
//	    for (int i = 1; i < 101; i++) {
//		    if (i % 2 == 0) list1.add(i);
//		    else list2.add(i);
//	    }
//	    System.out.println("list1 pre-addAll() with list1 being evens: " + list1);
//	    System.out.println("list2 pre-addAll() with list2 being odds: " + list2);
//	    list1.addAll(list2);// this should use the anterior add method
//	    System.out.println("list1 post-addAll() with list1 being 1 to 100: " + list1);
//	    System.out.println("list2 post-addAll() with list2 still being odds: " + list2);

		list1.add("apple");
		list1.add("banana");
		list1.add("peaches");
		list1.add("peaches");
		list1.add("cherries");
		list1.add("waffle fries");

		list2.add("bandanna");
		list2.add("youtube");
		list2.add("butts.jpeg");

		System.out.println("\nList 1: " + list1 + "\nSize: " + list1.size());
		System.out.println("List 2: " + list2 + "\nSize: " + list2.size());
		System.out.println("" + list1.remove("peaches"));
		System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
		System.out.println("" + list1.remove("donuts"));
		System.out.println("" + list1.remove("waffle fries"));
		System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
		Iterator<String> iter1 = list1.iterator();
		System.out.print(iter1.hasNext() + " ");
		System.out.println(iter1.next());
		iter1.remove();
		System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
		try {
			iter1.remove();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
		list1.addAll(list2);
		System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
		System.out.println("Removed Head: " + list1.removeHead());
		System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
		System.out.println("Removed Tail: " + list1.removeTail());
		System.out.println("List 1: " + list1 + "\nSize: " + list1.size());
		list2.clear();
		System.out.println("List 2: " + list2 + "\nSize: " + list2.size());
		try {
			list2.removeHead();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
