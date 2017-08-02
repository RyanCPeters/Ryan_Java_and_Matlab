import java.util.ArrayList;

/**
 * @author Ryan Peters
 * @date 7/24/2017
 */
public class ThreeSum {
	public ThreeSum(int[] nums) {
		int len = 0;
		for (Integer ele : nums ) {
			int tmp = ele.toString().length();
			len = (tmp > len)? tmp : len;
		}
		ArrayList<String> sols = new ArrayList<>();

		for(int n = 0; n < nums.length; n++){
			if (n > 0 && nums[n] != nums[n - 1]) {
				for (int i = n + 1; i < nums.length; i++) {
					if (i > n + 1 && nums[i] != nums[i - 1]) {
						for (int j = i + 1; j < nums.length; j++) {

							if (j != i + 1 && nums[j] == nums[j - 1]) continue;
							if ((nums[n] + nums[i] + nums[j]) == 0) sols.add(String.format("%+" + len + "d %+" + len + "d %+" + len + "d", nums[n], nums[i], nums[j]));

						}
					}
				}
			}
		}
		System.out.println();
		for (String ele : sols ) {
			System.out.println(ele);
		}
		System.out.println("there were " + sols.size() + " unique solutions");



	}

}
