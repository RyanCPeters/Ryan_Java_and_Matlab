import java.util.Random;

/**
 * @author Ryan Peters
 * @date 7/24/2017
 */
public class IntArrayGen {
	Integer[] theArray;


	public IntArrayGen(int length, int interval) {

		theArray = new Integer[length];
		Random rando = new Random();

		for (int i = 0; i < length; i++) {
			theArray[i] = rando.nextInt(interval) - (interval / 2);
		}
	}

	public Integer[] getTheArray() {
		return theArray;
	}
}
