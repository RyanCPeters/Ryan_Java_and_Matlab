/**
 * @author Ryan Peters
 * @date 3/22/2017
 */
public class H13_Test {
	public static void main(String[] args) {
		HashDict<String, String> phonebook = new HashDict<String, String>(20);
		try { //Case1: test insertions
			System.out.println("CASE #1: test insertions");
			phonebook.put("Chris", "555-1111");
			phonebook.put("Kathy", "555-2222");
			phonebook.put("Jones", "555-1111");
			phonebook.put("Kathy", "555-3333");
			System.out.println(phonebook.size() + " entries in phonebook:");
			System.out.println(phonebook);
		} catch (Exception e) {
			System.out.println("CASE #1 CRASH: " + e.toString());
		}

		try { //Case2: test deletions
			System.out.println("CASE #2: test deletions");
			System.out.println("after removing (Jones,"
					+ phonebook.remove("Jones") + ") :");
			System.out.println(phonebook);
			boolean removed = phonebook.remove("Kathy", "555-4444");
			System.out.println("after removing (Kathy, 555-4444)");
			System.out.println(phonebook);
			System.out.println("Was (Kathy, 555-4444) removed?  " + removed);
		} catch (Exception e) {
			System.out.println("CASE #2 CRASH: " + e.toString());
		}

		try { //Case3: test clear
			System.out.println("CASE #3: test clear");
			phonebook.clear();
			System.out.println("after clear: " + phonebook);
			System.out.println("Is phonebook now empty? "
					+ phonebook.isEmpty());
		} catch (Exception e) {
			System.out.println("CASE #3 CRASH: " + e.toString());
		}

		try { //Case4: test searching
			System.out.println("CASE #4: test searching");
			phonebook.put("Chris", "555-1111");
			phonebook.put("Kathy", "555-2222");
			phonebook.put("Jones", "555-3333");
			System.out.println(phonebook.size() + " entries in phonebook:");
			System.out.println(phonebook);
			System.out.println("Kathy's number is "
					+ phonebook.get("Kathy"));
			System.out.println("Jerry's number is "
					+ phonebook.get("Jerry"));
			System.out.println("Is Kathy in the phonebook? "
					+ phonebook.containsKey("Kathy"));
			System.out.println("Is Jerry in the phonebook? "
					+ phonebook.containsKey("Jerry"));
			System.out.println("Is 555-1111 a registered # in the phonebook? "
					+ phonebook.containsValue("555-1111"));
			System.out.println("Is 999-9999 a registered # in the phonebook? "
					+ phonebook.containsValue("999-9999"));
		} catch (Exception e) {
			System.out.println("CASE #4 CRASH: " + e.toString());
		}

		try { //Case5: test updating
			System.out.println("CASE #5: test updating");
			System.out.println("Kathy's number "
					+ phonebook.put("Kathy", "555-8888")
					+ " has been updated to "
					+ phonebook.get("Kathy"));
			System.out.println("Chris' number "
					+ phonebook.replace("Chris", "555-9999")
					+ " has been updated to "
					+ phonebook.get("Chris"));
			System.out.println("Jerry's number "
					+ phonebook.replace("Jerry", "555-00009")
					+ " has been updated to "
					+ phonebook.get("Jerry"));
		} catch (Exception e) {
			System.out.println("CASE #5 CRASH: " + e.toString());
		}
	}
}