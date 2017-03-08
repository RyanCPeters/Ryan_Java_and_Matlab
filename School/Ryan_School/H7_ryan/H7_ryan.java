import java.util.Formatter;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author Ryan Peters
 * @date 1/21/2017
 */
public class H7_ryan {
	/**
	 * @param args command line args if there were any
	 */
	public static void main(String[] args) {
//
//        for (int i = 1; i < 2629; i++) {
//            double time1 = currentTimeMillis();
//            new recursionClass(.2, 2, i);
//            time1 = currentTimeMillis() - time1;
//            System.out.println("for i = " + i + " time took " + time1 + " milli seconds ");
//        }

		new recursionClass();
	}

	/**
	 * Using this
	 */
	protected static class recursionClass {
		private final String FORMATT_INSTRCTNS = "%1$-7s %2$s %3$s";
		private double percentGrowth;
		private int endDay;
		private double population;
		private StringBuilder sb;

		/**
		 * the paramaterized constructor allows for easy testing with different numbers in a unit testing configuration.
		 *
		 * @param percentGrowth
		 * @param startPop
		 * @param endDay
		 */
		protected recursionClass(double percentGrowth, double startPop, int endDay) {
			this.percentGrowth = percentGrowth + 1.0;
			this.population = startPop;
			this.endDay = endDay;
			sb = new StringBuilder();
			Formatter formatter = new Formatter(sb, Locale.US);
			formatter.format(FORMATT_INSTRCTNS, "Day", "Organisms", "\n");
			recursPopulationCalc(1, startPop, formatter);
			//System.out.println(sb.toString());
		}

		/**
		 * The empty constructor prompts the use for input data to use in the recursion
		 */
		protected recursionClass() {
			Scanner scanMan = new Scanner(System.in);

			System.out.println("Enter the starting number of organisms:\n");

			if (scanMan.hasNextDouble()) {
				this.population = (scanMan.nextDouble());
			}
			while (population < 2) {
				System.out.println("\nThe population you entered was invalid, please enter a value greater or equal to 2 :\n");
				if (scanMan.hasNextDouble()) {
					this.population = (scanMan.nextDouble());
				}
			}// end population validation loop

			System.out.println("\nEnter daily population increase percentage as a decimal:\n");
			if (scanMan.hasNextDouble()) {
				this.percentGrowth = (scanMan.nextDouble());
			}
			while (percentGrowth < 0) {
				System.out.println("\nThe percentage you entered for daily growth was invalid, please enter a value greter than 0\n");
				if (scanMan.hasNextDouble()) {
					this.percentGrowth = (scanMan.nextDouble());
				}
			}// end percentGrowth validatoin loop
			this.percentGrowth += 1.0;
			System.out.println("\nEnter number of days the organisms multiply\n");
			if (scanMan.hasNextInt()) {
				this.endDay = (scanMan.nextInt());
			}
			while (endDay < 1) {
				System.out.println("\nthe number of days you entered was invalid, please enter a number greater than or equal to 1\n");
				if (scanMan.hasNextInt()) {
					this.endDay = (scanMan.nextInt());
				}
			}// end endDay validation loop


			sb = new StringBuilder();
			Formatter formatter = new Formatter(sb, Locale.US);
			formatter.format(FORMATT_INSTRCTNS, "\nDay", "Organisms", "\n");
			sb.append(String.format("%1$15s %2$s", "", "\n").replace(" ", "-"));
			recursPopulationCalc(1, population, formatter);
			System.out.println(sb.toString());

			scanMan.close();
		}

		/**
		 * @param currentDay
		 * @param currentPop
		 * @param formatter  the Formatter object is used to create a formatted string that is passed into my appendable
		 *                   string object (i'm using a StringBuilder) that will later be used to easily print the
		 *                   accumulated data.
		 */
		protected void recursPopulationCalc(Integer currentDay, Double currentPop, Formatter formatter) {
			formatter.format(FORMATT_INSTRCTNS, currentDay.toString(), (currentPop).toString(), "\n");
			if (currentDay < endDay)
				recursPopulationCalc(currentDay + 1, currentPop * (percentGrowth), formatter);
		}


	}
}
