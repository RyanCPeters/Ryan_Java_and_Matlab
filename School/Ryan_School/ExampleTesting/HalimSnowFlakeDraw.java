
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class HalimSnowFlakeDraw {
	public static void HalimSnowFlakeDraw(Graphics g, int level, Point frstPoint, Point sndPoint) {

		if (level == 0) {
			g.drawLine(frstPoint.x, frstPoint.y, sndPoint.x, sndPoint.y);
		} else if (level > 1) {

			Point mid = new Point((sndPoint.x - frstPoint.x) / 3, (sndPoint.y - frstPoint.y) / 3);

			Point newOne = new Point((frstPoint.x + mid.x), (frstPoint.y + mid.y));
			Point newTwo = new Point((sndPoint.x - mid.x), (sndPoint.y - mid.y));

			Integer xbuff = (int) (Math.floor(newOne.x + (.5 * mid.x - mid.y * Math.sin(Math.PI / 3))));
			Integer ybuff = (int) (Math.floor(newTwo.y + (.5 * mid.y + mid.x * Math.sin(Math.PI / 3))));

			Point newThree = new Point(xbuff, ybuff);
			Point testThree = new Point(mid.x, mid.y);

			HalimSnowFlakeDraw(g, level - 1, frstPoint, newOne);
			HalimSnowFlakeDraw(g, level - 1, newOne, newThree);
			HalimSnowFlakeDraw(g, level - 1, newThree, newTwo);
			HalimSnowFlakeDraw(g, level - 1, newTwo, sndPoint);

		} else {
			g.drawLine(frstPoint.x, frstPoint.y, sndPoint.x, sndPoint.y);
		}
	}

	private static void stall() {
		try {
			Thread.sleep(275);
		} catch (Exception e) {
		}
	}

	public static JFrame makeWindow(int level) {
		JFrame window = new JFrame("Koch SnowFlake level " + level);
		window.setSize(400, 400);
		window.setLocation(150, 150);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		return window;
	}

	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		for (int i = 0; i < 7; i++) {


			System.out.println("Enter the level for the Koch SnowFlake:");
			int level = console.nextInt();

			Point frstPoint = new Point(50, 250);
			Point sndPoint = new Point(350, 250);
			Point thrdPoint = new Point(200, 50);

			JFrame window = makeWindow(level);

			Container pane = window.getContentPane();
			Graphics g = pane.getGraphics();

			stall();

			HalimSnowFlakeDraw(g, level, frstPoint, sndPoint);
			HalimSnowFlakeDraw(g, level, sndPoint, thrdPoint);
			HalimSnowFlakeDraw(g, level, thrdPoint, frstPoint);
		}
	}
}