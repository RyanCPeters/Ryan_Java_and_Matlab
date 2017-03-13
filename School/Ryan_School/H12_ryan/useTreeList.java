import java.util.ArrayList;
import java.util.Iterator;

/**
 * A handful of test cases
 *
 * @author Charlie on 3/7/2017.
 * modified by Ryan on 3/12/2017
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
		System.out.println("list3 pre-addAll() with list3 < 51: \n" + list3);
		System.out.println("list4 pre-addAll() with list4 > 50: \n" + list4);
		list3.addAll(list4);// this should use the posterior add method
		System.out.println("\nlist3 post-addAll() with list3 1-100: \n" + list3);
		System.out.println("list4 post-addAll() with list4 > 50: \n" + list4);


		list3 = new TreeList<>();
		list4 = new TreeList<>();
		System.out.println("\n-----------------------------------------------------\n");
		for (int i = 1; i < 101; i++) {
			if (i > 50) list3.add(i);
			else list4.add(i);
		}
		System.out.println("list3 pre-addAll() with list3 > 50: \n" + list3);
		System.out.println("list4 pre-addAll() with list4 < 51: \n" + list4);
		list3.addAll(list4);// this should use the anterior add method
		System.out.println("\nlist3 post-addAll() with list3 being 1-100: \n" + list3);
		System.out.println("list4 post-addAll() with list4 < 51: \n" + list4);


		list3 = new TreeList<>();
		list4 = new TreeList<>();
		System.out.println("\n-----------------------------------------------------\n");
		for (int i = 1; i < 101; i++) {
			if (i % 2 == 0) list3.add(i);
			else list4.add(i);
		}
		System.out.println("list3 pre-addAll() with list3 being evens: \n" + list3);
		System.out.println("list2 pre-addAll() with list4 being odds: \n" + list4);
		list3.addAll(list4);// this should use the anterior add method
		System.out.println("\nnlist3 post-addAll() with list3 being 1 to 100: \n" + list3);
		System.out.println("list4 post-addAll() with list4 still being odds: \n" + list4);


		/*--------------------------------------------------------------------------------------------
		* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		* ---------------------------------------------------------------------------------------------*/

		/*
		* Please note that there is also a file in the same github repo as this client file that has Ryan's
		* current output when using these test conditions, use that to get an idea of what to expect when
		* using this file.
		*/

		System.out.println("\n-----------------------------------------------------\n");
		list3 = new TreeList<>();
		list4 = new TreeList<>();
		int[] testData = {-4, 6, -17, -41, -33, -5, 80, 60, -8, 12,
				-98, -70, 35, -41, 27, 39, -88, 40, 7, 49,
				-31, -70, -7, -4, -25, -78, 84, 34, -31, 72,
				64, -28, -85, -81, -45, -81, -41, 92, 12, -86,
				-6, -77, -20, 52, 93, -92, -38, 58, -9, -91, 0};
		ArrayList<Integer> sortedComparison = new ArrayList<>(51);
//		Random rand = new Random();

		int size = 51;
		for (int i = 0; i < size; i++) {
//			int ran = rand.nextInt(200) - 100;
//			System.out.print(ran+", ");
//			if( i%10 == 0 ) System.out.println();
//			list3.add(ran);
			list3.add(testData[i]);
			sortedComparison.add(testData[i]);
		}

		System.out.println("testData was entered in the following order:\n");
		Iterator iter = sortedComparison.iterator();
		int counter = 0;
		while (iter.hasNext()) {
			if (counter % (size / 3) == 0) {
				System.out.print(",\n " + String.format("%4s", iter.next()));
			} else {
				System.out.print(", " + String.format("%4s", iter.next()));
			}
			counter++;
		}
		System.out.println();
		System.out.println();
		sortedComparison.sort(null);
		System.out.println("list3 having 51 random values of -100 to 100: \n" + list3);
		System.out.println();
		System.out.print("sortedComparison contains :");
		iter = sortedComparison.iterator();
		counter = 0;
		while (iter.hasNext()) {
			if (counter % (size / 3) == 0) {
				System.out.print(",\n " + String.format("%4s", iter.next()));
			} else {
				System.out.print(", " + String.format("%4s", iter.next()));
			}
			counter++;
		}
		System.out.println();
		list1.add("apple");
		list1.add("banana");
		list1.add("peaches");
		list1.add("peaches");
		list1.add("cherries");
		list1.add("waffle fries");

		list2.add("bandanna");
		list2.add("youtube");
		list2.add("butts.jpeg");

		System.out.println("\n#1 initial list1 configuration");
		System.out.println("List 1: " + list1 + "\nexpected:{apple, banana, cherries, peaches, peaches, " +
				"waffle fries}\nSize: " + list1.size() + " -> expected: 6\n");

		System.out.println("#2 initial list2 configuration");
		System.out.println("List 2: " + list2 + "\nexpected:[bandanna, butts.jpeg, youtube]\nSize: " +
				list2.size() + " -> expected: 3\n");

		System.out.println("#3 list1.indexOf(cherries)->" + list1.indexOf("cherries") + "->expected: 2\n");
		System.out.println("#4 list1.remove(\"peaches\")->" + list1.remove("peaches") + " -> expected: true\n");

		System.out.println("#5 list1 after having peaches removed");
		System.out.println("List 1: " + list1 + "\nexpected:[apple, banana, cherries, peaches, waffle fries]" +
				"\nSize: " + list1.size() + "->expected: 5\n");


		System.out.println("#6 list1.remove(donuts)-> " + list1.remove("donuts") + " -> expected: false");


		System.out.println("#7 list1.remove(waffle fries)-> " +
				list1.remove("waffle fries") + " -> expected: true\n");

		System.out.println("#8 List 1: " + list1 + "\nexpected:[apple, banana, cherries, peaches]" + "\nSize: " +
				list1.size() + "->expected: 4\n");
		Iterator<String> iter1 = list1.iterator();
		System.out.print("#9 iter1.hasNext()");
		System.out.println(iter1.hasNext() + "->expected: true // " + " ");
		System.out.print("#10 iter1.next()");
		System.out.println(iter1.next() + "->expected: apple \n");
		iter1.remove();
		System.out.println("#11 iter1.remove()-> expected to remove apple");
		System.out.println("#12 List 1: " + list1 + "\nexpected:[banana, cherries, peaches]" + "\nSize: " +
				list1.size() + "->expected: 3\n");
		try {
			System.out.println("#13 calling remove without first calling next(); do we have an error message?----------");
			iter1.remove();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("\n#14 List 1: " + list1 + "\nexpected:[banana, cherries, peaches]" + "\nSize: " +
				list1.size() + "->expected: 3\n");

		list1.addAll(list2);
		System.out.println(
				"#15 List 1: " + list1 + "\n\texpected:[banana, bandanna, butts.jpeg, cherries, peaches, youtube]" +
				"\nSize: " + list1.size() + "->expected: 6\n");

		System.out.println("#16 Removed Head: " + list1.removeHead() + "->expected: banana\n");

		System.out.println("#17 List 1: " + list1 + "\nexpected:[bandanna, butts.jpeg, cherries, peaches, youtube]" +
				"\nSize: " + list1.size() + "->expected: 5\n");

		System.out.println("#18 Removed Tail: " + list1.removeTail() + "->expected: youtube\n");

		System.out.println("#19 List 1: " + list1 + "\nexpected:[bandanna, butts.jpeg, cherries, peaches]" + "\nSize: " +
				list1.size() + "->expectd: 4\n");

		list2.clear();
		System.out.println("#20 List 2: " + list2 + "\nexpected:[]" + "\nSize: " + list2.size() + "->expected: 0\n");

		try {
			list2.removeHead();
			System.out.println("#21 do we have an error message?------------------------");
		} catch (Exception e) {
			System.out.println(e.getMessage());                                 // *Exception message*
		}
	}
}
