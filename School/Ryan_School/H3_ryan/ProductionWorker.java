/**
 * @author Ryan Peters
 * @date 1/10/2017
 */
public class ProductionWorker extends Employee {
	private int shift;
	private Double rate;


	/**
	 * @param name         the String object representing the employee's name
	 * @param intPrefix    the 4 digit integer value at the front of the employee's id number.
	 * @param stringSuffix the single capitol letter String which should be in the range of
	 */
	public ProductionWorker(String name, int intPrefix, String stringSuffix, int shift, Double rate) throws InvalidEmployeeNumber, InvalidShift, InvalidPayRate {
		super(name, intPrefix, stringSuffix);
		setShift(shift);
		setRate(rate);
	}

	/**
	 * @param name         the String object representing the employee's name
	 * @param intPrefix    the 4 digit integer value at the front of the employee's id number.
	 * @param stringSuffix the single capitol letter character which should be in the range of
	 */
	public ProductionWorker(String name, int intPrefix, Character stringSuffix, int shift, Double rate) throws InvalidEmployeeNumber, InvalidShift, InvalidPayRate {
		this(name, intPrefix, Character.toString(stringSuffix), shift, rate);
	}

	/**
	 * @return
	 */
	public int getShift() {
		return shift;
	}

	/**
	 * @param shift
	 */
	public void setShift(int shift) throws InvalidShift {
		this.shift = shiftValidation(shift);
	}

	private Integer shiftValidation(int shift) throws InvalidShift {
		if (shift < 1 || shift > 2) {
			throw new InvalidShift("An exception occured when validating the Integer value in ProductionWorker.shiftValidation. The " +
					"value was found to be " + shift + " when it should have been either a 1 or a 2");
		}
		return shift;
	}

	/**
	 * @return
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * 3
	 *
	 * @param rate
	 */
	public void setRate(Double rate) throws InvalidPayRate {
		this.rate = rateValidation(rate);
	}

	private Double rateValidation(Double rate) throws InvalidPayRate {
		if (!(rate > 0)) {
			throw new InvalidPayRate("An exception occured ProductionWorker.rateValidation when it was discoverd that" +
					"rate had a value of" + rate + " when it should be greater than 0");
		}
		return rate;
	}

	@Override
	public String toString() {
		String s;
		if (shift == 1) {
			s = "Day";
		} else {
			s = "Night";
		}
		StringBuilder sb = new StringBuilder("ProductionWorker{" + super.toString());
		sb.append(
				"\nshift = " + s +
						"\npay rate = " + rate
		);
		return sb.toString();
	}
}
