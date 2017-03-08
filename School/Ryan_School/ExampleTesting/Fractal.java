import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An implementation of the Koch Snowflake
 */
public class Fractal extends JFrame implements ActionListener {

	MyPanel drawingArea = null;   // The Panel
	public final int size = 1024; // The width of the frame

	/**
	 * Point helper class.  Holds a 2D x and y coordinate pair
	 *
	 * @author Timothy Renner
	 */
	private class Point {
		double x, y; // x and y values of the point

		/**
		 * Construct a point from doubles
		 *
		 * @param _x - x value
		 * @param _y - y value
		 */
		Point(double _x, double _y) {
			x = _x;
			y = _y;
		}

		/**
		 * Construct a point from ints
		 *
		 * @param _x - x value
		 * @param _y - y value
		 */
		Point(int _x, int _y) {
			x = _x;
			y = _y;
		}

		/**
		 * Copy constructor<br>
		 * Construct a point from an existing Point
		 *
		 * @param p - The Point to copy
		 */
		Point(Point p) {
			x = p.x;
			y = p.y;
		}
	}

	/**
	 * The customized JPanel for this example.
	 *
	 * @author Timothy Renner
	 */
	private class MyPanel extends JPanel {

		int recursion = 0;  // How many levels of recursion to use
		final int TRIANGLE = 0;
		final int SNOWFLAKE = 1;
		int whichFractal = SNOWFLAKE;

		/**
		 * Recursively draw the snowflake.  If n == 0, draw the line given.  Otherwise
		 * split it into 4 more lines of the shape  _/\_ and recursively call this method
		 * on each part.
		 *
		 * @param g  - The Graphics context
		 * @param p1 - The first point
		 * @param p2 - The second point
		 * @param n  - The current level of the recursion
		 */
		public void drawSnowFlake(Graphics g,
		                          Point p1,
		                          Point p2,
		                          int n) {

			// Base Case - If we've hit the end of our recursion, draw the line...
			if (n == 0) {
				g.setColor(Color.black);
				g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
				return;
			}

			// Inductive Step - Otherwise...
			// Break line into this:  _/\_
			// Break into four lines = 5 points
			Point[] pts = new Point[5];
			pts[0] = new Point(p1); // Same as p1
			pts[1] = new Point(p1.x + (p2.x - p1.x) / 3.0, p1.y + (p2.y - p1.y) / 3.0); // 1/3 the way from p1 to p2
			// The difficult one.  Extrude a point 90deg out from the line.
			// Find the vector from pt2 to pt1 
			Point vec = new Point(p2.x - p1.x, p2.y - p1.y);
			// And the midpoint... This will be our reference point.
			Point midPoint = new Point((p2.x + p1.x) / 2.0, (p2.y + p1.y) / 2.0);
			// Cross vec with (0,0,1) to get an orthogonal vector in the 2-D plane.  ie:  _|_
			//     a X b = (1,2,3) x (4,5,6) = ((2 x 6 - 3 x 5),-(1 x 6 - 3 x 4),+(1 x 5 - 2 x 4)) = (-3,6,-3).
			double x = vec.y * 1;  // Much simpler w/ only two dimensions.
			double y = -(vec.x * 1);
			// x and y define the new vector.  Normalize it to length 1.
			double rs = Math.sqrt((x * x) + (y * y));
			x /= rs;
			y /= rs;
			// New length is 1/3 the original length
			double newLength = Math.sqrt((Math.pow(p2.x - p1.x, 2)) + Math.pow(p2.y - p1.y, 2)) / 3.0;
			// Now find this middle point.  It is 90 degrees out from the original line, newLength
			//  distance away.
			pts[2] = new Point(x * newLength + midPoint.x, y * newLength + midPoint.y);
			// And now the easier ones.
			pts[3] = new Point(p1.x + (p2.x - p1.x) * 2.0 / 3.0, p1.y + (p2.y - p1.y) * 2.0 / 3.0); // 1/3 the way from p2 to p1
			pts[4] = new Point(p2); // Same as p2

			// Now recursively draw the resulting four lines, dropping the level of recursion by 1.
			drawSnowFlake(g, pts[0], pts[1], n - 1);
			drawSnowFlake(g, pts[1], pts[2], n - 1);
			drawSnowFlake(g, pts[2], pts[3], n - 1);
			drawSnowFlake(g, pts[3], pts[4], n - 1);
		}

		/**
		 * Recursively draw the Sierpinski Triangle.  Points are passed in in
		 * clockwise order.
		 *
		 * @param g  - Graphics context
		 * @param p1 - The upper point
		 * @param p2 - The lower right point
		 * @param p3 - The lower left point
		 * @param n  - Level of recursion
		 */
		public void drawTriangle(Graphics g,
		                         Point p1,
		                         Point p2,
		                         Point p3,
		                         int n) {
			// Base case.  If n == 0, draw it.
			if (n == 0) {
				g.setColor(Color.black);
				g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
				g.drawLine((int) p2.x, (int) p2.y, (int) p3.x, (int) p3.y);
				g.drawLine((int) p3.x, (int) p3.y, (int) p1.x, (int) p1.y);
				return;
			}
			// Induction Step.  Break apart the triangle into 4 triangles and
			//  recurse on the outside pieces (Think the tri-force ;)
			//       p1
			//       .
			//  mp3 /_\ mp1
			//   p3/\_/\ p2
			//      mp2
			//
			Point mp1 = new Point((p2.x + p1.x) / 2.0, (p2.y + p1.y) / 2.0);
			Point mp2 = new Point((p3.x + p2.x) / 2.0, (p3.y + p2.y) / 2.0);
			Point mp3 = new Point((p3.x + p1.x) / 2.0, (p3.y + p1.y) / 2.0);
			drawTriangle(g, p1, mp1, mp3, n - 1);
			drawTriangle(g, mp1, p2, mp2, n - 1);
			drawTriangle(g, mp3, mp2, p3, n - 1);
		}

		/**
		 * Create endpoints for the Koch Snowflake and for the
		 * Sierpinski Triangle and start the recursion process on
		 * whichever is currently selected.
		 *
		 * @param g - The graphics context
		 */
		public void paintComponent(Graphics g) {
			// Create the snowflake endpoints
			Point p1 = new Point(10, size / 2);
			Point p2 = new Point(size - 10, size / 2);
			// Create the triangle endpoints
			Point a1 = new Point(size / 2, 70);
			Point a2 = new Point(size - 10, size / 2);
			Point a3 = new Point(10, size / 2);

			// Draw the appropriate fractal
			if (whichFractal == SNOWFLAKE) {
				drawSnowFlake(g, p1, p2, recursion);
			} else {
				drawTriangle(g, a1, a2, a3, recursion);
			}
			// Print out the current level of recursion.
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString("Levels of recursion: " + recursion, size / 2 - 100, 60);
		}
	}

	/**
	 * Set up the drawing area.  Create buttons and such.
	 */
	public Fractal() {
		// Create a new Panel
		drawingArea = new MyPanel();
		drawingArea.setVisible(true);
		setPreferredSize(new Dimension(size, (size / 2) + 95));
		// Set the drawingArea panel as its content
		setContentPane(drawingArea);
		// Tell this frame to terminate when the JFrame display is closed.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set the title of the displayed window.
		setTitle("Koch Snowflake");
		// Add two buttons and add action listeners for them
		JButton button1 = new JButton("Less");
		button1.setActionCommand("less");
		button1.addActionListener(this);
		JButton button2 = new JButton("More");
		button2.setActionCommand("more");
		button2.addActionListener(this);
		// Two more buttons to switch between fractals
		JButton button3 = new JButton("Koch Snowflake");
		button3.setActionCommand("snowflake");
		button3.addActionListener(this);
		JButton button4 = new JButton("Sierpinski Triangle");
		button4.setActionCommand("triangle");
		button4.addActionListener(this);
		// Add the buttons to the panel
		drawingArea.add(button1);
		drawingArea.add(button2);
		drawingArea.add(button3);
		drawingArea.add(button4);
		// Make it visible
		setVisible(true);
		// Pack this frame
		pack();
		// Make it visible.
		setVisible(true);
	}

	/**
	 * The action listener.  Triggered by the pressing of any button
	 *
	 * @param e - The event that was triggered.
	 */
	public void actionPerformed(ActionEvent e) {
		// Lower the level of recursion
		if ("less".equals(e.getActionCommand())) {
			drawingArea.recursion--;
			if (drawingArea.recursion < 0) drawingArea.recursion = 0;
		}
		// Or raise it.
		else if ("more".equals(e.getActionCommand())) {
			drawingArea.recursion++;
		}
		// Choose the triangle
		else if ("triangle".equals((e.getActionCommand()))) {
			drawingArea.whichFractal = drawingArea.TRIANGLE;
		}
		// Choose the snowflake
		else if ("snowflake".equals((e.getActionCommand()))) {
			drawingArea.whichFractal = drawingArea.SNOWFLAKE;
		}
		// Trigger a redraw of the frame.
		repaint();
	}


	/**
	 * Our main class... All it does is create an instance of Fractal, which
	 * does all the rest of the work.
	 *
	 * @param args - Command line arguments - not used.
	 */
	public static void main(String[] args) {
		Fractal snowFlake = new Fractal();
	}
}