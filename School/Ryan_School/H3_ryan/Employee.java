/**
 * The abstract class Employee offers a template for instantiating an employee's ID number and associating their name
 * to that number.
 * <p>
 * The employee class has a private inner class called EmployeeData which serves only as a means to process and store the
 * ID number which is an AlphaNumeric mixture
 *
 * @author Ryan Peters
 * @date 1/10/2017
 */
public abstract class Employee {
	private String name;
	private EmployeeData idNumber;
	private final int PREFIX_LEN = 3;// the number of digits that should be in the int portion of the employee number
	private final int SUFFIX_LEN = 1;// the number of letters that should be in the employee number
	private final int[] SUFFIX_INTERVAL = {'A', 'M'};//the Ascii value for the char 'A' is 65 and for 'M' it's 77

	/**
	 * @param name         the String object representing the employee's name
	 * @param intPrefix    the 4 digit integer value at the front of the employee's id number.
	 * @param stringSuffix the single capitol letter character which should be in the range of
	 */
	public Employee(String name, int intPrefix, Character stringSuffix) throws InvalidEmployeeNumber {
		this(name, intPrefix, Character.toString(stringSuffix));
	}

	/**
	 * @param name         the String object representing the employee's name
	 * @param intPrefix    the 4 digit integer value at the front of the employee's id number.
	 * @param stringSuffix the single capitol letter String which should be in the range of
	 */
	public Employee(String name, int intPrefix, String stringSuffix) throws InvalidEmployeeNumber {
		this.name = nameValidation(name).trim();
		this.idNumber = setEmployeeNumber(intPrefix, stringSuffix);

	}


	/**
	 *
	 */
	public void setEmployeeName(String s) {
		this.name = nameValidation(s);
	}

	/* checks that the string passed to it is SUFFIX_LEN(a constant int value) Capitol letter on the interval from A to M.
	*  The .charAt(x) call returns a char representation of the String object's first and only letter which can be compared
	*  against integer values from SUFFIX_INTERVAL[0] to SUFFIX_INTERVAL[1] which expresses Ascii characters as their int
	*  reference values.
	*
	*  A failed check will result in a thrown InvalidEmployeeNumber Exception wht a context specific message attached.*/
	private String suffixValidation(String validate) throws InvalidEmployeeNumber {
		if (validate.length() > SUFFIX_LEN || validate.length() < SUFFIX_LEN || validate.charAt(0) <= SUFFIX_INTERVAL[0] || validate.charAt(0) >= SUFFIX_INTERVAL[1]) {
			throw new InvalidEmployeeNumber("An exception occured when validating the alphabetic component of the" +
					" " + getClass() + " employee ID number, the provided string component \"" + validate + "\" must be a single capitol " +
					"letter on the interval from A to M inclusive.");

		}
		return validate;
	}// end of suffixValidation

	/*Checks that the Integer passed to it is PREFIX_LEN digits long, no more and no less. Failing the conditional check will result
	* in a thrown InvalidEmployeeNumber Exception with a context specific message attached.
	* */
	private Integer prefixValidation(Integer validate) throws InvalidEmployeeNumber {
		if (validate.toString().length() < PREFIX_LEN || validate.toString().length() > PREFIX_LEN) {
			throw new InvalidEmployeeNumber("An exception occured when validating the numeric component of the " +
					getClass() + " ID number, the provided integer component \"" + validate + "\" must be a four(4) digit " +
					"number on the interval from 0 to 9999 inclusive.");
		}
		return validate;
	}// end of prefixValidation


	private String nameValidation(String name) {
		StringBuilder sb = new StringBuilder();
		char[] charCheck = name.trim().toCharArray();
		sb.append(charCheck[0]);
		for (int i = 1; i < charCheck.length; i++) {
			if (!(charCheck[i] == ' ' && charCheck[i - 1] == ' ')) {
				sb.append(charCheck[i]);
			}
		}// end for i<charCheck.len loop
//        System.out.println(sb.toString().trim());
		return sb.toString().trim();
	}// end nameValidation

	/**
	 * @param prefix the 4 digit integer component of the employee's id number
	 * @param suffix the single capitol letter component of the employee's id number.
	 * @return a boolean value that confirms if the idNumber object has been updated or not in the scenario that the code
	 * which called this method isn't the one handling exceptions but needs a convenient means to verify the update.
	 * @throws InvalidEmployeeNumber
	 */
	public EmployeeData setEmployeeNumber(int prefix, String suffix) throws InvalidEmployeeNumber {
		return new EmployeeData(prefixValidation(prefix), suffixValidation(suffix));
	}

	/**
	 * This overload of the setEmployeeNumber method processes data in the event that the given input uses a Character
	 * instead of a String.
	 */
	public void setEmployeeNumber(int prefix, char suffix) throws InvalidEmployeeNumber {
		setEmployeeNumber(prefix, Character.toString(suffix));
	}

	/**
	 * @return returns the string object for the employee's name.
	 */
	public String getEmployeeName() {
		return this.name;
	}

	/**
	 * @return the EmployeeData object which holds the integer and string values that compose the employee's id number.
	 */
	public EmployeeData getEmployeeNumber() {
		return idNumber;
	}

	/**
	 * @return the returned string object will tell you the employee's name, and ID number
	 */
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("\nEmployee name: " + this.getEmployeeName());
		sb.append("\nEmployee number: " + getEmployeeNumber().toString());
		return sb.toString();
	}

	/**
	 * This internal class serves a s a convenient mechanism for the processing, storing and delivery of the employee's
	 * AlphaNumeric ID number.
	 */
	private class EmployeeData {
		private Integer intPrefix;
		private String stringSuffix;

		/**
		 * @param intPrefix    the integer portion to the employee's ID number.
		 * @param stringSuffix the string portion to the employee's ID number.
		 */
		protected EmployeeData(int intPrefix, String stringSuffix) {
			this.intPrefix = intPrefix;
			this.stringSuffix = stringSuffix;
		}

		/**
		 * @return the string object representing the alphabetic portion of the employee id number
		 */
		public String getString() {
			return stringSuffix;
		}

		/**
		 * @return the int type object for the integer portion of the employee id number.
		 */
		public int getNum() {
			return intPrefix;
		}

		/**
		 * @param prefix the integer portion to the employee's ID number.
		 */
		public void setPrefix(Integer prefix) {
			this.intPrefix = prefix;
		}

		/**
		 * @param suffix the string portion to the employee's ID number.
		 */
		public void setSuffix(String suffix) {
			this.stringSuffix = suffix;
		}

		/**
		 * Overriden toString() method that converts the integer portion of the employee number to a string and concatenates
		 * the string suffix for the number onto it.
		 *
		 * @return a new string object created by converting the integer poriton of thes id into a string and concatenating
		 * the originol string suffix portion onto it. And yes the dash ( - ) portion is put in it's correct spot as well.
		 */
		@Override
		public String toString() {
			return intPrefix.toString() + "-" + stringSuffix;
		}


	}


}
