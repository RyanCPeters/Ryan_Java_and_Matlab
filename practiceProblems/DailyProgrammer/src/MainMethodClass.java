import ColorMazed.ColorMaze;

import java.util.Arrays;

/**
 * @author Ryan Peters
 * @date 7/13/2017
 */
public class MainMethodClass {
	public static void main(String[] args) {
//		testLongestPalindrome();
//		testThreeSum(true);
		testColorMaze();

	}

	public static void testThreeSum(boolean useRando) {
		int arrayLen = 10000;
		int interval = 10000;

		if (useRando) {
			threeSumStringFormat(new IntArrayGen(arrayLen, interval).getTheArray());
		} else {
			threeSumStringFormat(new Integer[]{4, 5, -1, -2, -7, 2, -5, -3, -7, -3, 1});
			System.out.println();
			threeSumStringFormat(new Integer[]{-1, -6, -3, -7, 5, -8, 2, -8, 1});
			System.out.println();
			threeSumStringFormat(new Integer[]{-5, -1, -4, 2, 9, -9, -6, -1, -7});
			System.out.println();
			threeSumStringFormat(new Integer[]{9, -6, -5, 9, 8, 3, -4, 8, 1, 7, -4, 9, -9, 1, 9, -9, 9, 4, -6, -8});

		}


	}

	static void testColorMaze(){
		new ColorMaze();
	}

	static void threeSumStringFormat(Integer[] theArray){
		int[] myArray = Arrays.stream(theArray).sorted().mapToInt(Integer::intValue).toArray();

		StringBuilder sb = new StringBuilder("{ " + myArray[0]);

		for (int i = 1; i < myArray.length; i++) {
			if (i % 25 == 0)
				sb.append("\n" + myArray[i]);
			else
				sb.append(", " + myArray[i]);

		}
		sb.append(" }");
		System.out.println(sb.toString());

		new ThreeSum(myArray);
	}



	private static void testWarCardGame(){

	}

	private static void testLongestPalindrome(){
		long[] results;
		long tic, toc;
		int spacing;

		LongestPalindrome lngPal = new LongestPalindrome();

		tic = System.nanoTime();
		System.out.println("for n = " + 2);
		results = lngPal.palinSeeker(2);
		spacing = (((Long) results[0]).toString() + "the palindrome is ").length();
		toc = System.nanoTime() - tic;
		System.out.println("it took " + (toc * 1E-9) + " seconds");
		System.out.printf(
				"%1$" + spacing + "s \n%2$" + spacing + "s \n%3$" + spacing + "s \n",
				"the palindrome is " + results[0],
				"low factor is " + results[1],
				"high factor is " + results[2]);

//		System.out.println(lngPal.isPdrome(testVal, ((Long) testVal).toString()));

		for (int i = 3; i < 12; i += 2) {


			System.out.println();
			System.out.println("for n = " + i);
			tic = System.nanoTime();
			results = lngPal.palinSeeker(i);
			spacing = (((Long) results[0]).toString() + "the palindrome is ").length();
			toc = System.nanoTime() - tic;
			System.out.println("it took " + (toc * 1E-9) + " seconds");
			System.out.printf(
					"%1$" + spacing + "s \n%2$" + spacing + "s \n%3$" + spacing + "s \n",
					"the palindrome is " + results[0],
					"low factor is " + results[1],
					"high factor is " + results[2]);
		}

	}

}
