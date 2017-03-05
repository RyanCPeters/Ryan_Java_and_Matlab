import java.util.Scanner;

/**
 * @author Ryan Peters
 * @date 2/18/2017
 */
public class H10_ryan {
	private int targetX;
	private int targetY;
	private ArrayStack<String> validSolStack;
	private ArrayStack<String> solsToValidate;

	H10_ryan(int tarX, int tarY) {
		this.targetX = tarX;
		this.targetY = tarY;
		this.validSolStack = new ArrayStack<>();
		this.solsToValidate = new ArrayStack<>();
		solver();
		int uniqueSols = solsToValidate.getSize();
		System.out.println("There were " + uniqueSols + " unique solutions, which are as follows:");
		System.out.println("------------------------------------------------------");
		while (!solsToValidate.isEmpty()) {
			validSolStack.push(solsToValidate.pop());
		}
		while (!validSolStack.isEmpty()) System.out.println(validSolStack.pop());
		System.out.println("enjoy your" + uniqueSols + " unique, valid solutions!");

	}

	/**The solver method uses the given target points x and y values to calculate the number of total possible paths from
	 * origin to target point. It then derives the maximum possible binary expression in a digit sequence that has a length
	 * of targetX + targetY. Then it loops through all possible binary numbers from 0 to the maximum possible expression
	 * and adds to the output stack the values that match length and compositional criteria.
	 *
	 */
	private void solver() {
		final int height = targetY;
		final int width = targetX;
		final int len = height + width;
		final int powerOfTwo = (int) (Math.pow(2, len) - Math.pow(2, height));
		solsToValidate = new ArrayStack<>();
		System.out.println("there are " + String.format("%,d", powerOfTwo) + " possible solutions...\n searching for the valid one's now...");
		String pass;
		int countE;
		for (int i = 0; i <= powerOfTwo; i++) {
			countE = width;
			pass = Integer.toBinaryString(i);

			for (int j = 0; j < pass.length(); j++) {
				if (countE > 0 && pass.charAt(j) == '1'){
					countE--;
				}
			}
			if (countE == 0) {
				pass = String.format("%1$" + len + "s", pass);
				pass = pass.replace(' ', 'N');
				pass = pass.replace('1', 'E');
				pass = pass.replace('0', 'N');
				solsToValidate.push(pass);
			}
		}// end of for-i loop
	}// end of solver()

	public static void main(String[] args) {
		Scanner scanMan = new Scanner(System.in);
		System.out.println("Please enter the desired (x,y) target coordinate in the format of:\n\n\t\tx y\n");

		String target = scanMan.nextLine();
		scanMan = new Scanner(target);

		int tarX = Integer.parseInt(scanMan.next());
		int tarY = Integer.parseInt(scanMan.next());


		new H10_ryan(tarX, tarY);
	}
}
