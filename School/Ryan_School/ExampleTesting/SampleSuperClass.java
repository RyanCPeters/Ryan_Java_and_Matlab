/**
 * @author Ryan Peters
 * @date 1/20/2017
 */
public class SampleSuperClass {
	private int aNum;
	private int bNum;

	public SampleSuperClass(int aNum) {
		this.aNum = aNum;
		System.out.println(this.aNum);
		this.bNum = 8008;


		System.out.println("sup aNum = " + this.getaNum());
		System.out.println("sup bNum = " + this.getbNum());
	}

	public int getbNum() {
		return bNum;
	}

	public int getaNum() {
		return aNum;
	}

	public void setbNum(int bNum) {
		this.bNum = bNum;
	}

	public void setaNum(int bNum) {
		this.aNum = bNum;
	}

}
