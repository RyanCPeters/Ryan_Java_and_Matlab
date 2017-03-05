import java.util.Scanner;
import java.util.Stack;

/**
 * This prgoram will prompt users for a prefix expression, then return the evaluation of that expression.
 *
 * @author Ryan Peters
 * @date 2/11/2017
 */
public class H9_ryan {
	private final int MOD = 37;// the ASCII value of the % operator
	private final int MULT = 42;// the ASCII value of the * operator
	private final int PLUS = 43;// the ASCII value of the + operator
	private final int MINUS = 8722; // the ASCII value of the - operator
	private final int DIV = 47; // the ASCII value of the / operator
	private final int ZERO = 48; // the ASCII value for zero
	private final int NINE = 57; // the ASCII value for nine
	private Stack<String> strStack;
	private Stack<Integer> operands;
	private Scanner scanMan;

	/** the constructor instantiates the needed auxiliary stacks and collects the user input that will be passed to
	 * the calculation method
	 */
	public H9_ryan() {
		strStack = new Stack<>();
		operands = new Stack<>();
		scanMan = new Scanner(System.in);
		String input = "";
		System.out.println("Please enter the expression you want evaluated");
		if (scanMan.hasNext()) {
			input += scanMan.nextLine().trim();
		}
		calculation(input);
	}

	/*the calculation method takes a string representing the collected input from the user, and then parses that
	* string from right to left (via a stack) where it pushes operands onto an auxiliary stack until it finds an
	* operator. At that time it will pop the first two operands off the operand stack and evaluate them according to
	* the current operator.
	* */
	private void calculation(String input) {
		boolean noErr = true;
		for (String s : input.split(" ")) {
			if(!(s.compareTo(" ") == 0 || s.compareTo("")==0 ))strStack.add(s);
		}
		while (!strStack.isEmpty()) {
			if (strStack.peek().charAt(0) > ZERO && strStack.peek().charAt(0) < NINE) {
				operands.push(Integer.parseInt(strStack.pop()));
			} else if(operands.size() > 1){
				int num1 = operands.pop();
				int num2 = operands.pop();
				switch (strStack.pop().charAt(0)) {
					case MOD:
						operands.push(num1 % num2);
						break;
					case MULT:
						operands.push(num1 * num2);
						break;
					case PLUS:
						operands.push(num1 + num2);
						break;
					case MINUS:
						operands.push(num1 - num2);
						break;
					case DIV:
						operands.push(num1 / num2);
						break;
					default:
						break;
				}
			}else {
				System.out.println("Sorry, but it seems the program ran into a problem, there are too many operators" +
						" for the number of operands given.");
				noErr = false;
				break;
			}
		}
		if(noErr)System.out.println("The output of the given expression is " + operands.toString());
	}

	public static void main(String[] args) {
		new H9_ryan();
	}
}
