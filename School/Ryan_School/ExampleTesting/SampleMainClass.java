/**
 * @author Ryan Peters
 * @date 1/20/2017
 */
public class SampleMainClass {
	/**
	 * when you open this file you can comment out the examples you don't want to see so as to only get output for
	 * those you are interested in.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		// use the following for loop to execute samples of the recursion example
//        for (int i = 1234; i < 12345; i += 9) new SampleRecurser(i);

		// use the following to see the samplesubclass and how it's calls relate to and are impacted by the sample superclass
//        new SampleSubClass(20);

		// the following is for checking out the drawing shapes example from the midterm 1
//        new SampleGUIQFromMidterm1();

		// this call will use the canvas example from oracle's javadoc on the Canvas class.
//	    new SampleUsingACanvasObj();

		System.out.println("in main, calling empty sample constructor overload class should see a value of 0");
		new SampleContructorOverloading();
		System.out.println("\nin main, calling int sample constructor overload class should see a value of 10");
		new SampleContructorOverloading(10);
		System.out.println("\nin main, calling int sample constructor overload class should see a value of 1");
		new SampleContructorOverloading("");

	}
}
