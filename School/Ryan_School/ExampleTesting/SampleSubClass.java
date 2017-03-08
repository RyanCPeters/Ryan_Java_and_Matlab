/**
 * @author Ryan Peters
 * @date 1/20/2017
 */
public class SampleSubClass extends SampleSuperClass {
	private int aNum;

	public SampleSubClass(int aNum) {
		super(8008);
		this.aNum = aNum;
		System.out.println("sub aNum before using setter = " + this.getaNum());
		System.out.println("sub bNum before using setter = " + this.getbNum());
		this.setbNum(aNum);
		this.setaNum(aNum);

		System.out.println("sub aNum after setter = " + this.getaNum());
		System.out.println("sub bNum = " + this.getbNum());


	}

}
