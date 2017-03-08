import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

/**
 * This program will take an integer input between 0 and 6 inclusive, and will generate a Koch snowflake fractal drawing.
 *
 * @author Ryan Peters
 * @date 1/30/2017
 */
public class H8_ryan extends JFrame {
	// the variable imgEL should be read as the image edge length, the image isn't going to scale with the window so
	// this can be a constant
	private final int IMG_EL = 640;

	// MAXN denotes the highest level of fractal this program can attempt before hitting a stack overflow
	private final int MAXN = 6;
	// The angle 60 degrees expressed in radians is Pi over three ie. Pi/3.
	private final Double SIXTY = Math.PI / 3;
	private final Double SIN_SIXTY = Math.sin(SIXTY);

	// Now to establish a reference point in the center of the drawing surface
	// IMG_C reads as image center
	private final Double IMG_C = IMG_EL / 2.0; // if IMG_EL is 600, this should be 300
	private final Double BRDR_E = IMG_EL / 20.0;// if IMG_EL is 600, this should be 30

	private Double topWidth = IMG_EL - 2.0 * BRDR_E;// this variable corresponds to the width of the top level fractal, ie. the base triangle.
	private Double height = topWidth * Math.sin(SIXTY);


	public static void main(String[] args) {
		new H8_ryan();


	}

	/**
	 * Constructs a new frame that is initially invisible.
	 * <p>
	 * This constructor sets the component's locale property to the value
	 * returned by <code>JComponent.getDefaultLocale</code>.
	 *
	 * @throws HeadlessException if GraphicsEnvironment.isHeadless()
	 *                           returns true.
	 * @see GraphicsEnvironment#isHeadless
	 * @see Component#setSize
	 * @see Component#setVisible
	 * @see JComponent#getDefaultLocale
	 */
	public H8_ryan() throws HeadlessException {
		Scanner scanMAn = new Scanner(System.in);
		System.out.println("\nPlease enter an integer value between 0 and " + MAXN + "\n");
		int n = -1;
		if (scanMAn.hasNextInt()) {
			n = scanMAn.nextInt();
		}
		int loopCount = 0;
		while ((n < 0 || n > MAXN) && loopCount < 10) {
			loopCount++;
			System.out.println("\nThe value you entered was not between 0 and " + MAXN + " please try again\n");
			if (scanMAn.hasNextInt()) {
				n = scanMAn.nextInt();
			} else if (scanMAn.hasNext()) {
				scanMAn.next();
			}
		}
		if (loopCount >= 10) {
			System.out.println("There appears to be a problem in the data input system. The user should seek aid in " +
					"debugging, program closing now.");
			System.exit(0);
		}


		new JFrame("Level " + n + " Koch Curve Snowflake");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


		DrawingPane dPane = new DrawingPane(n);
		setContentPane(dPane);
		BoxLayout box = new BoxLayout(dPane, BoxLayout.X_AXIS);
		dPane.setLayout(box);
		dPane.add(Box.createGlue());
		setSize(IMG_EL, IMG_EL);
		validate();
		setVisible(true);

	}

	//////////////////////////////////////////////////////////////////////////////////////////////////
	private class DrawingPane extends JPanel {
		// the fractal level we need to recurse to.
		int n;
		MyPoint topCenter = new MyPoint(280, 40);
		MyPoint botRight = new MyPoint(440, 320);
		MyPoint botLeft = new MyPoint(120, 320);

		/**
		 * Creates a new <code>JPanel</code> with a double buffer
		 * and a flow layout.
		 */
		public DrawingPane(int n) {
			new JPanel();
			this.n = n;
			this.setSize(IMG_EL, IMG_EL);
			System.out.println("the sin of 60 is :" + SIN_SIXTY);
			setVisible(true);

		}


		/**
		 * Calls the UI delegate's paint method, if the UI delegate
		 * is non-<code>null</code>.  We pass the delegate a copy of the
		 * <code>Graphics</code> object to protect the rest of the
		 * paint code from irrevocable changes
		 * (for example, <code>Graphics.translate</code>).
		 * <p>
		 * If you override this in a subclass you should not make permanent
		 * changes to the passed in <code>Graphics</code>. For example, you
		 * should not alter the clip <code>Rectangle</code> or modify the
		 * transform. If you need to do these operations you may find it
		 * easier to create a new <code>Graphics</code> from the passed in
		 * <code>Graphics</code> and manipulate it. Further, if you do not
		 * invoker super's implementation you must honor the opaque property,
		 * that is
		 * if this component is opaque, you must completely fill in the background
		 * in a non-opaque color. If you do not honor the opaque property you
		 * will likely see visual artifacts.
		 * <p>
		 * The passed in <code>Graphics</code> object might
		 * have a transform other than the identify transform
		 * installed on it.  In this case, you might get
		 * unexpected results if you cumulatively apply
		 * another transform.
		 *
		 * @param g the <code>Graphics</code> object to protect
		 * @see #paint
		 */
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			goFigureWhereWeAre(g);

		}

		private void goFigureWhereWeAre(Graphics g) {
			// Establish our triangle
			if (n < 1) {
				g.drawLine(topCenter.getXasInt(), topCenter.getYasInt(), botLeft.getXasInt(), botLeft.getYasInt());
				g.drawLine(topCenter.getXasInt(), topCenter.getYasInt(), botRight.getXasInt(), botRight.getYasInt());
				g.drawLine(botRight.getXasInt(), botRight.getYasInt(), botLeft.getXasInt(), botLeft.getYasInt());
			} else {
				breakItDown(n, g, topCenter, botRight);
				breakItDown(n, g, botLeft, topCenter);
				breakItDown(n, g, botRight, botLeft);

			}
		}

		private void breakItDown(int n, Graphics g, MyPoint p1, MyPoint p2) {
			if (n == 0) {
				g.drawLine(p1.getXasInt(), p1.getYasInt(), p2.getXasInt(), p2.getYasInt());
				return;
			}
			// I borrowed fairly heavily from a resource online for this part. I hadn't realized
			// that finding vectors in a virtual space was such a pain in the butt. I much
			// rather doing this on paper it seems :P
			MyPoint[] pts = new MyPoint[5];
			pts[0] = new MyPoint(p1); // Same as p1
			pts[1] = new MyPoint(p1.x + (p2.x - p1.x) / 3, p1.y + (p2.y - p1.y) / 3);
			MyPoint vec = new MyPoint(p2.x - p1.x, p2.y - p1.y);
			MyPoint midPoint = new MyPoint((p2.x + p1.x) / 2, (p2.y + p1.y) / 2);
			double x = vec.y * 1;  // Much simpler w/ only two dimensions.
			double y = -(vec.x * 1);
			int rs = (int) Math.sqrt((x * x) + (y * y));
			x /= rs;
			y /= rs;
			int newLength = ((Double) (Math.sqrt((Math.pow(p2.x - p1.x, 2)) + Math.pow(p2.y - p1.y, 2)) / 3)).intValue();
			// the guy popping out of it all is at idx 2
			pts[2] = new MyPoint(x * newLength + midPoint.x, y * newLength + midPoint.y);
			pts[3] = new MyPoint(p1.x + (p2.x - p1.x) * 2 / 3, p1.y + (p2.y - p1.y) * 2 / 3); // 1/3 the way from p2 to p1
			pts[4] = new MyPoint(p2);// again just a reff to p2

			// Now recursively draw the resulting four lines, dropping the level of recursion by 1.
			breakItDown(n - 1, g, pts[0], pts[1]);
			breakItDown(n - 1, g, pts[1], pts[2]);
			breakItDown(n - 1, g, pts[2], pts[3]);
			breakItDown(n - 1, g, pts[3], pts[4]);

		}

		/**
		 * I saw that a dude did something like this online and I had to try my hand at it. This makes working with the points
		 * waaay easier. I am going to have to start thinking about how I can used this concept more in the future!
		 */
		private class MyPoint {
			double x, y;

			// ahhh, good a double!
			private MyPoint(double x, double y) {
				this.x = x;
				this.y = y;
			}

			// whaaaat! it takes an int!?
			private MyPoint(int x, int y) {
				this.x = x;
				this.y = y;
			}

			// for easy point coyping :D
			private MyPoint(DrawingPane.MyPoint p) {
				this.x = p.x;
				this.y = p.y;
			}

			int getXasInt() {
				return ((Double) x).intValue();
			}

			int getYasInt() {
				return ((Double) y).intValue();
			}
		}// end of point class

	}// end of DrawingPane class
}
