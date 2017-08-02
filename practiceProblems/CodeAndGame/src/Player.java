import java.util.ArrayList;
import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/


class Player {
	/*

# = 35
. = 46
? = 63
C = 67
T = 84


*/

	public static void main(String args[]) {
		char[] symbols = {'#', '.', '?', 'C', 'T'};
		Scanner in = new Scanner(System.in);
		int R = in.nextInt(); // number of rows.
		int C = in.nextInt(); // number of columns.
		int A = in.nextInt(); // number of rounds between the time the alarm countdown is activated and the time the alarm goes off.
		ArrayList<String> pathStack = new ArrayList<>();
		String safeSpaces = ".CT";
		String output = "";
		String[] map = new String[R];
		// game loop
		while (true) {
			int KR = in.nextInt(); // row where Kirk is located.
			int KC = in.nextInt(); // column where Kirk is located.
			for (int i = 0; i < R; i++) {
				String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
				if (i == KR - 1 && (safeSpaces.contains(ROW.substring(KC,KC))) && !output.equals("UP")) {
					output = "UP";
					break;
				} else if (i == KR && (safeSpaces.contains(ROW.substring(KC - 1, KC - 1))) && !output.equals("LEFT")) {
					output = "LEFT";
					break;
				} else if (i == KR && (safeSpaces.contains(ROW.substring(KC + 1, KC + 1))) && !output.equals("RIGHT")) {
					output = "RIGHT";
					break;
				} else if (i == KR + 1 && (safeSpaces.contains(ROW.substring(KC, KC))) && !output.equals("DOWN")) {
					output = "DOWN";
					break;
				}
			}
			// Write an action using System.out.println()
			// To debug: System.err.println("Debug messages...");

			System.out.println(output); // Kirk's next move (UP DOWN LEFT or RIGHT).
		}
	}
}