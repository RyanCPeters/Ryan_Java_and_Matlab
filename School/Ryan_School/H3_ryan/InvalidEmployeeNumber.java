/**
 * @author Ryan Peters
 * @date 1/10/2017
 */
public class InvalidEmployeeNumber extends Exception {
	/**
	 *
	 */
	public InvalidEmployeeNumber() {
		super();
	}

	/**
	 * @param s the string object for the specific message detailing the exception event.
	 */
	public InvalidEmployeeNumber(String s) {
		super(s);
	}

}
