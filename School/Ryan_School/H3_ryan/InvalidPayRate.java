/**
 * @author Ryan Peters
 * @date 1/10/2017
 */
public class InvalidPayRate extends Exception {
	/**
	 *
	 */
	public InvalidPayRate() {
		super();
	}

	/**
	 * @param s detailed error message generated at the point of the exception
	 */
	public InvalidPayRate(String s) {
		super(s);
	}
}
