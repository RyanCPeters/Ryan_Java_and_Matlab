/**
 * @author Ryan Peters
 * @date 1/12/2017
 */
public class H4_main {
	public static void main(String[] args) {

//		try { //Case4: test insert with overflow
//            IntQ iq = new IntQ(5);
//            for (int i = 0; i <= 6; i++) {
//                iq.insert(2001 + i);
//            }
//            System.out.print("\tCase 4 overflow: " + iq.toString());
//            System.out.println("\n\tlength = " + iq.getLength());
//		} catch(Exception e) {
//		    System.out.println("Case 4 crashed:\n\t" + e.toString());
//		}

//		try { //Case6: test delete all
//            IntQ iq = new IntQ(5);
//		    System.out.println("\ntest case 6:\n\tiq.getCapacity() = "+iq.getCapacity());
//
//            iq.insert(2001); iq.insert(2002); iq.insert(2003);
//
//            System.out.println("\tinserted 3 elements: \n\t" + iq.toString());
//            System.out.println("\tfirst  delete: " + iq.delete());
//            System.out.println("\tadd 99 ...");
//            iq.insert(99);
//            String[] strArray = iq.toString().split(":");
//            System.out.println("length of intQ according to iq.getLength() = "+iq.getCapacity());
//
//            for(String sub : strArray){
//                System.out.println("\t"+sub);
//            }
//            System.out.println("length of intQ according to strArray.length() = "+strArray.length);
//            System.out.print("\tdeleting all remainders ... \n\t");
//            while (!iq.isEmpty()) {
//                System.out.print(" " + iq.delete());
//            }
//            System.out.println("\n\t" + iq.toString());
//          } catch(Exception e) {
//             System.out.println("Case#6\n crashed: " + e.toString());
//          }

		try { //Case8: circular insertion
			IntQ iq = new IntQ(3);
			System.out.println("\ntest case 8:\n\tiq.getCapacity() = " + iq.getCapacity());
			iq.insert(2001);
			iq.insert(2002);
			iq.insert(2003);
			System.out.println("\tinserted 3 elements:\n\t" + iq.toString());
			System.out.println("\tfirst  delete: " + iq.delete());
			System.out.println("\tsecond delete: " + iq.delete());
			System.out.println("\tadd 2004 ... ");
			iq.insert(2004);
			String[] strArray = iq.toString().split("\n");
			for (String sub : strArray) {
				System.out.println("\t" + sub);
			}
			System.out.println("\tthird  delete: " + iq.delete());
			System.out.println("\tfourth delete: " + iq.delete());
			System.out.println("\t" + iq.toString());
		} catch (Exception e) {
			System.out.println("Case 8\n crashed: " + e.toString());
			e.printStackTrace();
		}

	}
}
