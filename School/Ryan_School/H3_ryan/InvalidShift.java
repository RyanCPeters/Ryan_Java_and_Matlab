/**
 * @author Ryan Peters
 * @date 1/10/2017
 */
public class InvalidShift extends Exception {
	/**
	 *
	 */
	public InvalidShift() {
		super();
	}

	/**
	 * @param s detailed error message generated at the point of the exception
	 */
	public InvalidShift(String s) {
		super(s);
	}

}
