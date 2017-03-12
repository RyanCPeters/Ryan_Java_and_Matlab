import java.util.Iterator;
import java.util.Random;

/**
 * A handful of test cases
 *
 * @author Charlie on 3/7/2017.
 */
public class useTreeList {
	public static void main(String[] args) {
		ISortedList<String> list1 = new TreeList<>();
		ISortedList<String> list2 = new TreeList<>();
		ISortedList<Integer> list3 = new TreeList<>();
		ISortedList<Integer> list4 = new TreeList<>();

		for (int i = 1; i < 101; i++) {
			if (i < 51) list3.add(i);
			else list4.add(i);
		}
		System.out.println("list3 pre-addAll() with list3 < 51: " + list3);
		System.out.println("list4 pre-addAll() with list4 > 50: " + list4);
		list3.addAll(list4);// this should use the posterior add method
		System.out.println("\nlist3 post-addAll() with list3 1-100: " + list3);
		System.out.println("list4 post-addAll() with list4 > 50: " + list4);


		list3 = new TreeList<>();
		list4 = new TreeList<>();
		System.out.println("\n-----------------------------------------------------\n");
		for (int i = 1; i < 101; i++) {
			if (i > 50) list3.add(i);
			else list4.add(i);
		}
		System.out.println("list3 pre-addAll() with list3 > 50: " + list3);
		System.out.println("list4 pre-addAll() with list4 < 51: " + list4);
		list3.addAll(list4);// this should use the anterior add method
		System.out.println("\nlist3 post-addAll() with list3 being 1-100: " + list3);
		System.out.println("list4 post-addAll() with list4 < 51: " + list4);


		list3 = new TreeList<>();
		list4 = new TreeList<>();
		System.out.println("\n-----------------------------------------------------\n");
		for (int i = 1; i < 101; i++) {
			if (i % 2 == 0) list3.add(i);
			else list4.add(i);
		}
		System.out.println("list3 pre-addAll() with list3 being evens: " + list3);
		System.out.println("list2 pre-addAll() with list4 being odds: " + list4);
		list3.addAll(list4);// this should use the anterior add method
		System.out.println("\nnlist3 post-addAll() with list3 being 1 to 100: " + list3);
		System.out.println("list4 post-addAll() with list4 still being odds: " + list4);

		list3 = new TreeList<>();
		list4 = new TreeList<>();
		System.out.println("\n-----------------------------------------------------\n");
		Random rand = new Random();
		for (int i = 1; i < 51; i++) {
			int ran = rand.nextInt(200) - 100;
			System.out.println(ran);
			list3.add(ran);
		}
		System.out.println("list3 pre-addAll() with list3 having 50 random values of -100 to 100: " + list3);

		list1.add("apple");
		list1.add("banana");
		list1.add("peaches");
		list1.add("peaches");
		list1.add("cherries");
		list1.add("waffle fries");

		list2.add("bandanna");
		list2.add("youtube");
		list2.add("butts.jpeg");

		System.out.println("\nList 1: " + list1 + "\nSize: " + list1.size());   //List 1: [apple, banana, cherries, peaches, peaches, waffle fries]
		//Size: 6
		System.out.println("List 2: " + list2 + "\nSize: " + list2.size());     //List 2: [bandanna, butts.jpeg, youtube]
		//Size: 3
		System.out.println("" + list1.remove("peaches"));                 //true
		System.out.println("List 1: " + list1 + "\nSize: " + list1.size());     //List 1: [apple, banana, cherries, peaches, waffle fries]
		//Size: 5
		System.out.println("" + list1.remove("donuts"));                  //false
		System.out.println("" + list1.remove("waffle fries"));            //true
		System.out.println("List 1: " + list1 + "\nSize: " + list1.size());     //List 1: [apple, banana, cherries, peaches]
		//Size: 4
		Iterator<String> iter1 = list1.iterator();
		System.out.println(iter1.hasNext() + " ");                              //true
		System.out.println(iter1.next());                                       //apple
//		list1.remove(iter1.);                                                   //List 1: [banana, cherries, peaches]
		System.out.println("List 1: " + list1 + "\nSize: " + list1.size());     //Size: 3
		try {
			iter1.remove();
		} catch (Exception e) {
			System.out.println(e.getMessage());                                 //*exception message*
		}
		System.out.println("List 1: " + list1 + "\nSize: " + list1.size());     //List 1: [banana, cherries, peaches]
		// Size: 3
		list1.addAll(list2);
		System.out.println("List 1: " + list1 + "\nSize: " + list1.size());     //List 1: [banana, bandanna, butts.jpeg, cherries, peaches, youtube]
		//Size: 6
		System.out.println("Removed Head: " + list1.removeHead());              //Removed Head: banana
		System.out.println("List 1: " + list1 + "\nSize: " + list1.size());     //List 1: [bandanna, butts.jpeg, cherries, peaches, youtube]
		//Size: 5
		System.out.println("Removed Tail: " + list1.removeTail());              //Removed Tail: youtube
		System.out.println("List 1: " + list1 + "\nSize: " + list1.size());     //List 1: [bandanna, butts.jpeg, cherries, peaches]
		//Size: 4
		list2.clear();
		System.out.println("List 2: " + list2 + "\nSize: " + list2.size());     //RList 2: []
		//Size: 0
		try {
			list2.removeHead();
		} catch (Exception e) {
			System.out.println(e.getMessage());                                 // *Exception message*
		}
	}
}
