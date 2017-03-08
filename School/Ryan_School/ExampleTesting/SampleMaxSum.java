import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Ryan Peters
 * @date 1/26/2017
 */
public class SampleMaxSum {
	public SampleMaxSum() {

		ArrayList<Integer> list0 = new ArrayList<>(Arrays.asList());
		System.out.println("maxSum(" + list0 + ", 10) is\n" + maxSum(list0, 10));
		ArrayList<Integer> list1 = new ArrayList<>(Arrays.asList());
		System.out.println("maxSum(" + list1 + ", 20) is\n" + maxSum(list1, 20));
		ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList());
		System.out.println("maxSum(" + list2 + ", 7) is\n" + maxSum(list2, 7));
		ArrayList<Integer> list3 = new ArrayList<>(Arrays.asList());
		System.out.println("maxSum(" + list3 + ", 40) is\n" + maxSum(list3, 40));
		ArrayList<Integer> list4 = new ArrayList<>(Arrays.asList());
		System.out.println("maxSum(" + list4 + ", 42) is\n" + maxSum(list4, 42));
		ArrayList<Integer> list5 = new ArrayList<>(Arrays.asList());
		System.out.println("maxSum(" + list5 + ", 19) is\n" + maxSum(list5, 19));
		ArrayList<Integer> list6 = new ArrayList<>(Arrays.asList());
		System.out.println("maxSum(" + list6 + ", 10) is\n" + maxSum(list6, 10));
		ArrayList<Integer> list7 = new ArrayList<>(Arrays.asList());
		System.out.println("maxSum(" + list7 + ", 13) is\n" + maxSum(list7, 13));
	}

	public SampleMaxSum(ArrayList<Integer> aList, int bound) {
		System.out.println("maxSum(" + aList + ", " + bound + ") is\n" + maxSum(aList, bound));
	}

	public int maxSum(ArrayList<Integer> aList, int bound) {
		// initial check for the empty and low bound conditions
		if (aList.isEmpty() || bound <= 0) return 0;
		ArrayList<Integer> myList = new ArrayList<>(aList);
		Collections.sort(myList);
		int greatest = myList.size() - 1;
		greatest = findGreatest(myList, bound, greatest);
		int least = 0;

		// what we should do if only one element of the list is < bound
		if (least == greatest) return myList.get(greatest);
		// put mid as close to exactly in the middle of least and greatest as possible to quickly eliminate potential steps
		int mid = (least + greatest) / 2;
		// time to start recurring through the options
		return privMaxSum(myList, bound, least, mid, greatest, 0);
	}

	/**
	 * @param myList
	 * @param bound
	 * @param greatest
	 * @return
	 */
	private int findGreatest(ArrayList<Integer> myList, int bound, int greatest) {
		return (myList.get(greatest) >= bound) ? findGreatest(myList, bound, greatest--) : greatest;
	}

	/**
	 * @param myList
	 * @param bound
	 * @param mid
	 * @return
	 */
	private int cycleMidUp(ArrayList<Integer> myList, int bound, int mid) {
		return (myList.get(mid) < bound) ? cycleMidUp(myList, bound, mid++) : mid;
	}

	/**
	 * @param myList
	 * @param bound
	 * @param mid
	 * @return
	 */
	private int cycleMidDn(ArrayList<Integer> myList, int bound, int mid) {
		return (myList.get(mid) > bound) ? cycleMidDn(myList, bound, mid--) : mid;
	}

	/**
	 * @param myList   a local version of the given Integer list that is safe to modify and has already been sorted and
	 *                 pruned of any values greater than the bound.
	 * @param bound    the int value that we cannot match or exceed
	 * @param least    the index position of the least most value that we can safely use in the array.
	 * @param greatest the index position of the greatest value we can still use in the array.
	 * @return
	 */
	private int privMaxSum(ArrayList<Integer> myList, int bound, int least, int mid, int greatest, int currentSum) {
		if (greatest - least >= 2) {
			if (myList.get(least) + myList.get(greatest) < bound) {// in this condition we get to use mid
				currentSum = myList.get(least) + myList.get(greatest);
				mid = (bound < myList.get(least) + myList.get(mid) + myList.get(greatest)) ?
						cycleMidDn(myList, bound - currentSum, mid) :
						cycleMidUp(myList, bound - currentSum, mid);
			} else if (least > 0) {

			}

		} else {// here we can't use mid so we find the maximum value we can get from least and greatest
//            currentSum =
		}
		return currentSum;
	}


}
