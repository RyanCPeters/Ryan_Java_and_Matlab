package ColorMazed;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Ryan Peters
 * @date 8/1/2017
 */
public class ColorMaze {
	/**
	 *
	 */
	private static final String[] DIRS = {"up", "right", "left", "down", "this point is a dead end"};
	/**
	 *
	 */
	static final String smplFile = "OG.txt";
	/**
	 *
	 */
	static final String chllngFile = "ChallengeInput.txt";
	/**
	 *
	 */
	static final String PATH = "C:\\Coding projects\\projectRepository\\JSprojects\\practiceProblems\\DailyProgrammer\\src\\ColorMazed\\";

	private char[] sequence;
	private ArrayList<String> theMaze;
	private int posInSequence;
	boolean firstPosFound;

	/** public ColorMaze()
	 * a default parameterless constructor that provides a quick and easy call handle for testing if the logic is
	 * working well enough to solve the sample problem.
	 *
	 */
	public ColorMaze() {
		this(false);
	}

	/** public ColorMaze(boolean useChallenge)
	 * This constructor proivdes a convenient means to request the solution to either the sample, or the challenge maze.
	 * @param useChallenge a boolean statement that lets you choose if you want to solve the sample problem, or the
	 *                     challenge problem.
	 */
	public ColorMaze(boolean useChallenge) {
		this(((useChallenge)? chllngFile : smplFile));
	}

	/** package private constructor ColorMaze(String fileName)
	 * this is where the work actually starts, as it contains the initializing logic for
	 * accessing the text file containing the maze datas.
	 * @param fileName the name for the text file containing the maze data
	 */
	ColorMaze(String fileName) {
		String s = "oops! the output of what the solution path should be was never updated back to the caller.";
		posInSequence = 0;
		firstPosFound = false;
		try {
			theMaze = (ArrayList<String>) Files.readAllLines(Paths.get(PATH+fileName));
			for (int i = 0; i < theMaze.size(); i++) {
				theMaze.set(i, theMaze.get(i).trim().replace(" ",""));
			}
			sequence = (theMaze.remove(0)).toCharArray();
			s = navigateMaze();
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException occurred");
			System.err.println("shit has dun hit da fan! the file named " + fileName + " was not found!");
		} catch (IOException io) {
			System.err.println(io.getMessage());
			io.printStackTrace();
			System.err.println("IOException occurred");
			System.err.println("shit has dun hit da fan! the file named " + fileName + " resulted in an IOException");
		}
		System.out.println(s);
	}

	/**
	 *
	 * @return
	 */
	private String navigateMaze(){
		ArrayDeque<APoint> route = new ArrayDeque<>();

		int  xBound, yBound;
		xBound = theMaze.get(0).length()-1;
		yBound = theMaze.size() - 1;
		posInSequence = 0;

		/* when reassigning lines to the relevantLines array, be sure the lines are in the following order:
		 *
		 * 0: our current line position
		 *     the line corresponding to curPoint.y
		 *
		 * 1: the line above current position
		 *     the line corresponding to curPoint.y - 1
		 *
		 * 2[optional]: the line bellow current position, if it exists
		 *               the line corresponding to curPoint.y + 1
		 *
		 */
		String[] relevantLines = {theMaze.get(yBound), theMaze.get(yBound- 1)};

		APoint initPoint = new APoint();
		initPoint.setY(yBound);
		initPoint.setX(xBound);

		initPoint = setStartPos(initPoint, relevantLines);
		route.push(initPoint);

		while (route.peek() != null && route.peek().y > 0){
			int moveTo = 4;
			ArrayList<String> dirsToCheck = new ArrayList<>();
			dirsToCheck.addAll(Arrays.asList(DIRS));
			if(route.peek().y == yBound) {
				relevantLines = new String[]{theMaze.get(route.peek().y), theMaze.get(route.peek().y - 1)};
				dirsToCheck.remove("down");
			}else{
				relevantLines =
						new String[]{theMaze.get(route.peek().y), theMaze.get(route.peek().y - 1), theMaze.get(route.peek().y + 1)};

			}// end of the if(curPoint.y < yBound) {}else{} block
			if(route.peek().x == xBound)dirsToCheck.remove("right");
			else if(route.peek().x == 0)dirsToCheck.remove("left");

			for (int i = 0; i <  dirsToCheck.size()-1; i++) {
				if(checkNextDir(route.peek().x,dirsToCheck.get(i),relevantLines)){
					moveTo = i;
					break;
				}// end of if(checkNextDir(curPoint.x,DIRS[i],relevantLines))
			}// end of for (int i = 0; i < dirsToCheck; i++)

			if(moveTo != 4) {
				route.peek().setExitDir(DIRS[moveTo]);
				route.push(createNextPoint(route.peek(),moveTo));
			}else{
				route.pop();
				stepBackPosInSeq();
			}

		}// end of while (curPoint.y > 0) loop


		return generateMazeOutput(route,xBound,yBound);
	}// end of navigateMaze method

	String generateMazeOutput(ArrayDeque<APoint> route,int xBound,int yBound){
		System.out.println("The sequence is " + new String(sequence));
		StringBuilder sb = new StringBuilder();
		for (String els : theMaze) {
			for (char ele : els.toCharArray()) {

				sb.append(ele);
				sb.append(" ");
			}
			sb.append("\n");
		}
		paused(5);

		sb.append("\n");
		sb.append("now the solution is: ");
		int count = 0;
		String[] maze = new String[xBound+1];
		StringBuilder s = new StringBuilder();
		String aster = String.format("%"+xBound+"s\n"," ").replace(' ','*');
		for (String els : theMaze) {
			s.append(aster);
			while(route.peek() != null && route.peek().x == count)s.replace(route.peek().y, route.peek().y, route.pop().toString());
			count++;
		}
		System.out.println(s);


		return sb.toString();
	}

	/**
	 *
	 * @param relevantLines
	 * @return
	 */
	APoint setStartPos(APoint initPoint, String[] relevantLines){
		// start the search by finding the starting position
		boolean startFound = false;
		int[] bounds = {0, initPoint.x};
		for (int i = initPoint.x; i >=0 && !startFound; i--){
			for(int k = 0; k < 3 && !startFound; k++){
				if ((i == bounds[0] && k == 1) || (i == bounds[1] && k == 2))continue;
				startFound = relevantLines[0].charAt(i) == sequence[0] && checkNextDir(i, DIRS[k], relevantLines);
			}
			initPoint.x = i;
		}
//		System.out.println("initial point is at: ("+initPoint.x + ", "+ initPoint.y+")");
		initPoint.setMyColor(sequence[0]);
		firstPosFound = true;
		return initPoint;
	}

	/**
	 *
	 * @param current
	 * @param moveTo
	 * @return
	 */
	APoint createNextPoint(APoint current, int moveTo){
		int x = current.x, y = current.y;
		switch (moveTo) {
			case 0:
				y--;
				break;
			case 1:
				x++;
				break;
			case 2:
				x--;
				break;
			case 3:
				y++;
				break;
		}
//		System.out.println("newest point is at ("+x+","+y+")");
		paused(5);
		return new APoint(x,y,advancePosInSeq(),"*");

	}

	void paused(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	char advancePosInSeq(){
		char oldColor = sequence[posInSequence];
		posInSequence = (posInSequence + 1 < sequence.length) ? posInSequence+1: 0;
		return oldColor;
	}

	char stepBackPosInSeq(){
		char oldColor = sequence[posInSequence];
		posInSequence = (posInSequence - 1 >= 0)? posInSequence-1 : sequence.length;
		return oldColor;
	}

	/** boolean checkNextDir(int x, String dirToCheck, String[] relLines)
	 *
	 * This method will check if the direction indicated by the string dirToCheck contains the correct color for the
	 * the next color step in the prescribed sequence. It will then return true if the character in that direction matches
	 * and will otherwise return false.
	 *
	 * @param x the current x position inside of a single String representation of a row from the maze.
	 *
	 * @param dirToCheck A string which indicates what direction from our current (x,y) position we should look next.
	 *                   This should be one of 4 strings: up, left, right, or down.
	 *
	 * @param relLines a String array that represents the 2 to 3 relevant rows, in the form of lines of text lines,
	 *                 to our current position in the maze.
	 * @return
	 */
	boolean checkNextDir(int x, String dirToCheck, String[] relLines){

		char color = (posInSequence + 1 <= sequence.length-1)?sequence[posInSequence+1] : sequence[0];
		switch (dirToCheck) {
			case "up":
				if (relLines[1].charAt(x) == color) return true;
				break;
			case "left":
				if (relLines[0].charAt(x - 1) == color) return true;
				break;
			case "right":
				if (relLines[0].charAt(x + 1) == color) return true;
				break;
			case "down":
				if (relLines[2].charAt(x) == color) return true;
				break;
			}// end of switch(dirToCheck) block

		return false;
	}// end of checkNextDir method

	/** inner class APoint
	 *  a convenient helper class that provides a means to package (x,y) coordinates with their corresponding color
	 *  from the color maze
	 */
	class APoint implements Comparable{
		int x,y;
		char myColor;
		String exitDir;

		APoint(int x, int y, char myColor, String exitDir) {
			this.x = x;
			this.y = y;
			this.myColor = myColor;
			this.exitDir = exitDir;

		}

		APoint() {
			this(-1,-1, 'i',"*");// passing "i" for the point's color to represent invalid.
		}

		void setX(int x) {
			this.x = x;
		}

		void setY(int y) {
			this.y = y;
		}

		void setMyColor(char myColor) {
			this.myColor = myColor;
		}

		void setExitDir(String exitDir) {
			this.exitDir = exitDir;
		}

		int getX() {
			return x;
		}

		int getY() {
			return y;
		}

		char getMyColor() {
			return myColor;
		}

		String getExitDir() {
			return exitDir;
		}

		/**
		 * Returns a string representation of the object. In general, the
		 * {@code toString} method returns a string that
		 * "textually represents" this object. The result should
		 * be a concise but informative representation that is easy for a
		 * person to read.
		 * It is recommended that all subclasses override this method.
		 * <p>
		 * The {@code toString} method for class {@code Object}
		 * returns a string consisting of the name of the class of which the
		 * object is an instance, the at-sign character `{@code @}', and
		 * the unsigned hexadecimal representation of the hash code of the
		 * object. In other words, this method returns a string equal to the
		 * value of:
		 * <blockquote>
		 * <pre>
		 * getClass().getName() + '@' + Integer.toHexString(hashCode())
		 * </pre></blockquote>
		 *
		 * @return a string representation of the object.
		 */
		@Override
		public String toString() {
			return ""+myColor;
		}

		/**
		 * Compares this object with the specified object for order.  Returns a
		 * negative integer, zero, or a positive integer as this object is less
		 * than, equal to, or greater than the specified object.
		 * <p>
		 * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
		 * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
		 * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
		 * <tt>y.compareTo(x)</tt> throws an exception.)
		 * <p>
		 * <p>The implementor must also ensure that the relation is transitive:
		 * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
		 * <tt>x.compareTo(z)&gt;0</tt>.
		 * <p>
		 * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
		 * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
		 * all <tt>z</tt>.
		 * <p>
		 * <p>It is strongly recommended, but <i>not</i> strictly required that
		 * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
		 * class that implements the <tt>Comparable</tt> interface and violates
		 * this condition should clearly indicate this fact.  The recommended
		 * language is "Note: this class has a natural ordering that is
		 * inconsistent with equals."
		 * <p>
		 * <p>In the foregoing description, the notation
		 * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
		 * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
		 * <tt>0</tt>, or <tt>1</tt> according to whether the value of
		 * <i>expression</i> is negative, zero or positive.
		 *
		 * @param o the object to be compared.
		 * @return a negative integer, zero, or a positive integer as this object
		 * is less than, equal to, or greater than the specified object.
		 * @throws NullPointerException if the specified object is null
		 * @throws ClassCastException   if the specified object's type prevents it
		 *                              from being compared to this object.
		 */
		@Override
		public int compareTo(Object o) {

			if(o instanceof APoint){
				if(this.y > ((APoint) o).getY()){
					return 1;
				}else if( this.y == ((APoint) o).getY()){
					if (this.x > ((APoint) o).getX())return 1;
					else if(this.x == ((APoint) o).getX())return 0;
					else return -1;
				}else return -1;
			}else if(o == null){
				throw new NullPointerException();
			}else {
				throw new ClassCastException(o.getClass().toString());
			}

		}
	}


}
