import java.util.Iterator;

public class H12_Test {
	public static void main(String[] args) {
		TreeList<String> list1 = new TreeList<>();
		try { //Case1: test insertions
			list1.add("Bob");
			list1.add("Kai");
			list1.add("Ken");
			list1.add("Jim");
			list1.add("Amy");
			System.out.println("CASE #1");
			System.out.println(list1.size() + " elements in list: " + list1);
			System.out.println("Head: " + list1.getHead() + "     Tail: " + list1.getTail());
		} catch (Exception e) {
			System.out.println("CASE #1 CRASH: " + e.toString());
		}

		try { //Case2: test deletions
			System.out.println("CASE #2");
			list1.removeHead();
			System.out.println("after removeHead: " + list1);
			list1.removeTail();
			System.out.println("after removeTail: " + list1);
			list1.remove("Jim");
			System.out.println("after remove Jim: " + list1);
		} catch (Exception e) {
			System.out.println("CASE #2 CRASH: " + e.toString());
		}

		try { //Case3: test clear
			System.out.println("CASE #3");
			list1.clear();
			System.out.println("after clear: " + list1);
		} catch (Exception e) {
			System.out.println("CASE #3 CRASH: " + e.toString());
		}

		try { //Case4: test searches
			list1.add("Bob");
			list1.add("Kai");
			list1.add("Ken");
			list1.add("Jim");
			list1.add("Amy");
			System.out.println("CASE #4");
			System.out.println("list: " + list1);
			System.out.println("Kai is in list? " + list1.contains("Kai"));
			System.out.println("Where is Kai? " + list1.indexOf("Kai"));
			System.out.println("Ben is in list? " + list1.contains("Ben"));
			System.out.println("Where is Ben? " + list1.indexOf("Ben"));
		} catch (Exception e) {
			System.out.println("CASE #4 CRASH: " + e.toString());
		}
		try { //Case5: test iterator
			System.out.println("CASE #5");
			Iterator<String> iter = list1.iterator();
			String noK = "";
			while (iter.hasNext()) {
				String name = iter.next();
				if (name.startsWith("K"))
					iter.remove();
				else
					noK += name + "; ";
			}
			System.out.println("After removal of names starting with K: "
					+ list1);
		} catch (Exception e) {
			System.out.println("CASE #4 CRASH: " + e.toString());
		}

	}
}
