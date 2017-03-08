/**
 * this is taking the example from class where we were asked to take a number like 234 and convert it into 223344.
 *
 * @author Ryan Peters
 * @date 1/21/2017
 */
public class SampleRecurser {

	public SampleRecurser(int n) {
		System.out.println("useing the recursingByTerneery method with n = " + n + " :" + recursingByTernary(n));
//        System.out.println("useing the recursingByIfBlock method with n = " + n + " :" + recursingByIfBlock(n));
		System.out.println();

	}

	/* the ternery operation used in this version of the solution has the benefit of being readable and concise while
	* also using the return zen that's spoken of in the textbook. In order to make reading it easier, see the method
	* which follows this and notice how the different parts from both forms are used in the same way. Thus the ternery
	* operation has an implicit if/else structure to it's assertions.*/
	private int recursingByTernary(int n) {

		// note that the parenthesis around the conditional are optional,
		// so that (n / 10 == 0 ) is the same as n / 10 == 0
		int tester = n % 10;
		System.out.println("the given n is : " + n + " becomes " + tester + " + " + tester * 10 + " + " + tester * 100);
		return (n < 10) ? n + 10 * n + 100 * n : recursingByTernary(n / 10) * 1000 + 111 * (n % 10);
		//return 'conditional'? if true : if false ;
	}

	// notice that this version of the solution is identical to the above version. But by using the explicit if/else
	// statements we are forcing the bytecode to store implicit variables for what lines it needs to go to in order to
	// continue the statement.
//    private int recursingByIfBlock(int n) {
//        if (n / 10 == 0) {
//            return n * 11;
//        } else {
//            return recursingByIfBlock(n / 10) * 100 + 11 * (n % 10);
//        }
//    }
}
