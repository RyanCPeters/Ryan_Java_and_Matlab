/**
 * @author Ryan Peters
 * @date 2/19/2017
 */
public class SampleContructorOverloading {
	private final int A = 0;
	private final int B = 1;
	private int whenDoesItChange;

	public SampleContructorOverloading() {
		this.whenDoesItChange = A;
		System.out.println("\tempty constructor just set whenDoesItChange to A so it should = 0, truth is = " + whenDoesItChange);
	}

	public SampleContructorOverloading(int input) {
		this();
		System.out.println("\tint constructor just called this() so whenDoesItChange should = 0, and the truth is = " + whenDoesItChange);
		this.whenDoesItChange = input;
		System.out.println("\t\tint constructor received " + input + " as input and set whenDoesItChange to it, truth is = " + whenDoesItChange);
	}

	public SampleContructorOverloading(String overloader) {
		this(1);
		System.out.println("\tString constructor just called this(1), now whenDoesItChange = " + whenDoesItChange);
	}

}
