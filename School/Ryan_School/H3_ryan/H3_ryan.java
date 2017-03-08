import java.util.Scanner;

/**
 * @author Ryan Peters
 * @date 1/10/2017
 */
public class H3_ryan {
	// INPUT_COUNT is the number of expected inputs from the user
	private final int INPUT_COUNT = 4;
	private InputScrubber scrub;
	private ProductionWorker fng;   // fng reads as "Funny New Guys"
	private boolean inputValid;
	// it's the scanMan zibidy-zute'-zo-dote-zote!!!
	private Scanner scanMan;

	/**
	 * This overload of the contructor gives the client code an option to call for the toString() method as a static state
	 * even though H3_ryan constructors are not static.
	 *
	 * @param shouldPrint
	 */
	public H3_ryan(boolean shouldPrint) {
		this();
		System.out.println(fng.toString());
	}

	/**
	 * instantiates the ProductionWorker class object and asks the user for input
	 */
	public H3_ryan() {
		String[] inputReqs = {
				"The employee's firse and last name names eg \"Baracka O'Mama\"::",

				"The employee number in the form of DDD-L where each D is a single digit and L is a letter on the interval of A-M eg \"3234-H\"::",

				"Which shift are they working? For the day shift enter the number 1, otherwise enter 2 for the night shift::",

				"Finally, what is the employee's hourly pay rate? please specify the amount  in the form of \"15.25\"::"
		};
		String[] userArgs = new String[INPUT_COUNT];
		scanMan = new Scanner(System.in);
		System.out.println("Hello, and welcome to H3_ryan-mart! to register a new employee please enter " +
				"the following:");

		for (int i = 0; i < INPUT_COUNT; i++) {
			System.out.print(inputReqs[i]);
			if (scanMan.hasNextLine()) {
				String berner = scanMan.nextLine();
				userArgs[i] = berner.substring(berner.indexOf("::") + 1).trim();
			}


		}// end of for loop
		for (String str : userArgs) {
			if ((str.equals(null))) {
				System.err.println("We have an error in the storage of user inputs. String[] userArgs " +
						"has a null value");
			}
		}
		inputValid = false;
		scrub = new InputScrubber(userArgs);
		while (!inputValid) {
			try {
				inputValid = true;
				fng = new ProductionWorker(
						scrub.getName(),
						scrub.getPrefix(),
						scrub.getSuffix(),
						scrub.getShift(),
						scrub.getRate()
				);
			} catch (InvalidEmployeeNumber inum) {
				inputValid = false;
				System.out.println(inum.getMessage());
				System.out.println("It appears that an  invalid value was entered for the employee number.");
				System.out.println("You entered " + userArgs[1]);
				System.out.println("Please make your entry in the form of DDD-L, where L is any capitol leter A to M eg. 123-B::");
				if (scanMan.hasNextLine()) {
					String berner = scanMan.nextLine();
					userArgs[1] = berner.substring(berner.indexOf("::") + 1).trim();
					scrub = new InputScrubber(userArgs);
				}
				//            inum.printStackTrace();
				//            System.err.println(inum.getClass());
				//            System.err.println(inum.getMessage());

			} catch (InvalidShift ishift) {
				inputValid = false;
				System.out.println("It appears an invalid value was entered for the shift selection.");
				System.out.println("please enter 1 for day shift or 2 for night shift ::");
				if (scanMan.hasNextLine()) {
					String berner = scanMan.nextLine();
					userArgs[2] = berner.substring(berner.indexOf("::") + 1).trim();
					scrub = new InputScrubber(userArgs);
				}
				//            ishift.printStackTrace();
				//            System.err.println(ishift.getClass());
				//            System.err.println(ishift.getMessage());
			} catch (InvalidPayRate irate) {
				inputValid = false;
				System.out.println("It appears an invalid value was entered for the pay rate selection.");
				System.out.println("please specify the amount  in the form of \"15.25\"::");
				if (scanMan.hasNextLine()) {
					String berner = scanMan.nextLine();
					userArgs[3] = berner.substring(berner.indexOf("::") + 1).trim();
					scrub = new InputScrubber(userArgs);
				}
				//            irate.printStackTrace();
				//            System.err.println(irate.getClass());
				//            System.err.println(irate.getMessage());
			}
		}


	}// end of H3_ryan constructor

	public static void main(String[] args) {
		new H3_ryan(true);


	}

	/**
	 * @return the String object for production worker's toString() method.
	 */
	@Override
	public String toString() {
		return fng.toString();
	}

	private class InputScrubber {
		protected String name;
		protected int prefix;
		protected String suffix;
		protected int shift;
		protected Double rate;

		InputScrubber(String[] args) {
			this.name = nameScrub(args[0]);
			this.prefix = employeeNumberIntScrub(args[1]);
			this.suffix = employeeNumberStrScrub(args[1]);
			this.shift = shiftScrub(args[2]);
			this.rate = rateScrub(args[3]);

		}

		protected String nameScrub(String argZero) {
			return argZero = argZero.trim();
		}

		protected Integer employeeNumberIntScrub(String argOne) {

			StringBuilder sb = new StringBuilder();
			for (char ch : argOne.toCharArray()) {
				if (!(ch == ' ')) {
					sb.append(ch);
				}
			}
			String s = sb.toString().trim().substring(0, 3);
			return Integer.parseInt(s);
		}

		protected String employeeNumberStrScrub(String argOne) {
			return argOne.trim().substring(argOne.trim().length() - 1);
		}

		protected Integer shiftScrub(String argTwo) {
			return Integer.parseInt(argTwo.trim());
		}

		protected Double rateScrub(String argThree) {
			return Double.parseDouble(argThree.trim());
		}

		protected String getName() {
			return name;
		}

		protected int getPrefix() {
			return prefix;
		}

		protected String getSuffix() {
			return suffix;
		}

		protected int getShift() {
			return shift;
		}

		protected Double getRate() {
			return rate;
		}


	}// end of InputScrubber
}// end of H3_ryan class
