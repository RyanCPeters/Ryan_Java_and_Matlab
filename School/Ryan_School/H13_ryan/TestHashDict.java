import java.util.stream.Stream;

/**
 * @author Ryan Peters
 * @date 3/19/2017
 */
public class TestHashDict {
	public static void main(String[] args) {
		IDict<String, String> stringDict = new HashDict<>(113);
		String demoString = "To sit in solemn silence in a dull, dark dock,\n" +
				"In a pestilential prison, with a life-long lock,\n" +
				"Awaiting the sensation of a short, sharp shock,\n" +
				"From a cheap and chippy chopper on a big black block!\n" +
				"To sit in solemn silence in a dull, dark dock,\n" +
				"In a pestilential prison, with a life-long lock,\n" +
				"Awaiting the sensation of a short, sharp shock,\n" +
				"From a cheap and chippy chopper on a big black block!\n" +
				"A dull, dark dock, a life-long lock,\n" +
				"A short, sharp shock, a big black block!\n" +
				"To sit in solemn silence in a pestilential prison,\n" +
				"And awaiting the sensation\n" +
				"From a cheap and chippy chopper on a big black block!\n";

		Stream.Builder<String> stringStream = Stream.builder();
		stringStream.add(demoString);
		Stream<String> streamString = stringStream.build();
		String[] filteredString = streamString
				.reduce("", (x, y) -> (!y.equals(",") && !y.equals("\n")) ? x + y : x)
				.split(" ");
		String lastElem = "";
		for (String elem : filteredString) {
			stringDict.put(elem + lastElem, elem);
			lastElem = elem;
		}
		System.out.println(
				"Test 1: toString()\n" + stringDict);
		System.out.println(
				"Test 2: containsKey(Tosit)\n" + stringDict.containsKey("Tosit"));
		System.out.println(
				"Test 3: containsKey(tosit)\n" + stringDict.containsKey("tosit"));
		System.out.println(
				"Test 4: containsValue(Tosit)\n" + stringDict.containsValue("Tosit"));
		System.out.println(
				"Test 5: containsValue(tosit)" + stringDict.containsValue("tosit"));
		System.out.println(
				"Test 6: size()" + stringDict.size());
		System.out.println(
				"Test 7: isEmpty()" + stringDict.isEmpty());

	}
}
