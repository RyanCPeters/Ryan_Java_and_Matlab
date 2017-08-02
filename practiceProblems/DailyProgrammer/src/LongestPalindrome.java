/**
 * @author Ryan Peters
 * @date 7/13/2017
 */
public class LongestPalindrome {
	public LongestPalindrome(){

	}
	public LongestPalindrome(int n) {
		palinSeeker(n);
	}

	public long[] palinSeeker(int n){

		long product, loFactor, hiFactor = 9, minBoundary = 1;

		for (int i = 0; i < n-1; i++) {
			hiFactor = (hiFactor * 10) + 9;
			minBoundary *= 10;
		}
		long hi = hiFactor;
		loFactor = minBoundary;
		product = minBoundary * minBoundary;
		long num;
		for (long i = hi; i > loFactor && i > minBoundary; i--) {

			for( long k = i-1; k >= minBoundary; k-- ){
				num = i*k;
				if ( isPdrome(num,((Long)num).toString()) && num > product ) {
					loFactor = k;
					hiFactor = i;
					product = loFactor * hiFactor;
					break;
				}else if(num < product ){
					break;
				}

			}// end of for 'k' loop

		}// end of for 'i' loop

		return new long[]{product, loFactor,hiFactor};
	}

	public boolean isPdrome(long num, String s){
		if (num % 11 == 0) {
			int end = s.length() - 1;
			for (int j = 0; j < ((end + 1)/ 2.0); j++) {
				if (s.charAt(j) != s.charAt((end) - j)) {
					return false;
				}
			}// end of for 'j' loop
			return true;
		}// end of if (num%11 == 0) block
		return false;
	}
}
