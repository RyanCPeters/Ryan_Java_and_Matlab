import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author Ryan Peters
 * @date 1/5/2017
 */
public class H2_ryan extends JFrame {

	/**
	 * the the length in pixels that will be assigned to all 4 edges of the window.
	 */
	final int FRAME_EDGE = 210;

	/**
	 *
	 */
	final int ROWS = 3;
	/**
	 *
	 */
	final int COLS = 3;
	/**
	 *
	 */
	private JPanel panel;

	/**
	 *
	 */
	public enum StampType {
		BOX, EYE, WORD
	}

	private int initStampSize = 50;

	/**
	 * Constructs the initial JFrame for our window as well as our content pane which is a JPanel wich utilizes a
	 * GridBagLayout to manage it's components.
	 */
	public H2_ryan() {
		setTitle("I Love Java Image");

		setSize(FRAME_EDGE, FRAME_EDGE);
//        System.out.println("frame size is what?"+getHeight()+" by "+getWidth());
		setBackground(Color.white);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Border bord = new LineBorder(Color.black);
		Dimension d = new Dimension(COLS * initStampSize, ROWS * initStampSize);

		panel = new JPanel(new GridBagLayout());
		panel.setBorder(bord);
//        System.out.println("and the size of dimension d is: "+d.getSize());
		panel.setMinimumSize(d);
		panel.setPreferredSize(d);
//        System.out.println("panel's accessible context says: "+panel.getAccessibleContext());
		GridBagConstraints c = new GridBagConstraints();


		// our colors are red blue and green...
		Color red = Color.red;
		Color blu = Color.blue;
		Color grn = Color.green;
		Color[] clrOrdr = {grn, red, blu};// read as "color order" and the top row has green then red then blue
		H2_ryan.StampType[] stmpOrdr = {StampType.EYE, StampType.BOX, StampType.WORD};

		int ordinal = 0;
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				ordinal++;
				switch (ordinal) {
					case 1:
						c.anchor = GridBagConstraints.LAST_LINE_END;
						break;
					case 2:
						c.anchor = GridBagConstraints.PAGE_END;
						break;
					case 3:
						c.anchor = GridBagConstraints.LAST_LINE_START;
						break;
					case 4:
						c.anchor = GridBagConstraints.LINE_END;
						break;
					case 5:
						c.anchor = GridBagConstraints.CENTER;
						break;
					case 6:
						c.anchor = GridBagConstraints.LINE_START;
						break;
					case 7:
						c.anchor = GridBagConstraints.FIRST_LINE_END;
						break;
					case 8:
						c.anchor = GridBagConstraints.PAGE_START;
						break;
					case 9:
						c.anchor = GridBagConstraints.FIRST_LINE_START;
						break;
				}
				c.gridx = col;
				c.gridy = row;
				c.weightx = .5;
				c.weighty = .5;
				c.fill = GridBagConstraints.BOTH;


				switch (stmpOrdr[col]) {
					case BOX:
//                        System.out.println("Box at Ordinal#: "+ordinal);
						BoxStamp box = new BoxStamp(clrOrdr[col]);
						box.setBorder(bord);
						panel.add(box, c);
						break;
					case EYE:
						EyeStamp eye = new EyeStamp(clrOrdr[col]);
						eye.setBorder(bord);
						panel.add(eye, c);
						break;
					case WORD:
						WordStamp word = new WordStamp(clrOrdr[col]);
						word.setBorder(bord);
						panel.add(word, c);
						break;
					default:
						break;
				}
			}// end of col for loop
			Color temp = clrOrdr[2];
			clrOrdr[2] = clrOrdr[1];
			clrOrdr[1] = clrOrdr[0];
			clrOrdr[0] = temp;
			H2_ryan.StampType tmp = stmpOrdr[0];
			stmpOrdr[0] = stmpOrdr[1];
			stmpOrdr[1] = stmpOrdr[2];
			stmpOrdr[2] = tmp;
		}// end of row for loop
		setContentPane(panel);
		setVisible(true);
		validate();
//        System.out.println("frame size is what?"+getSize());
	}// end of H2_ryan constructor


	/**
	 *
	 */
	class Stamp extends JComponent {
		/**
		 * the Stamps object is intended to be a square, so the edge length as given here is 50 pixels
		 */
		final int EDGE = 50;
		/**
		 *
		 */
		Dimension d = new Dimension(EDGE, EDGE);
		/**
		 * this field establishes the height of horizontal rectangles and the width of horizontal rectangles in the box
		 * configurations.
		 */
		final int BOXHW = EDGE / 10;

		/**
		 *
		 */
		Color c;

		/**
		 * @param c the color specific to this stamp's position in the window
		 */
		protected Stamp(Color c) {
			super();
			this.c = c;
			setVisible(true);
		}

		/**
		 *
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
		}


	}

	/**
	 * The BoxStamp method will instantiate as a JComponent which is going to override the paintCompoent(Graphics g) method
	 * and allow us to actually draw the many rectangles needed create one of the 3 types of stamps in our image.
	 */
	class BoxStamp extends Stamp {
		private int boxEdge;

		/**
		 * @param c an integer array that takes an x and a y coordinate in that order
		 */
		protected BoxStamp(Color c) {
			super(c);
			boxEdge = EDGE;
			this.c = c;
			setPreferredSize(new Dimension(boxEdge, boxEdge));
			setBackground(Color.white);

		}// end BStamp constructor

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.white);
			boxDraw(g);


		}


		/**
		 * @param g the Graphics component casted into a Graphics2D object for this Stamps object
		 */
		private void boxDraw(Graphics g) {
			g.setColor(c);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));


			int layerTarget = 4;
			int rectWidth = getWidth() / 10;
			int rectHeight = getHeight() / 10;

                    /*the following 4 variables represent the x or y coordinate values used in defining the outer corners of our
                    * layers of rectangles. these values get mutated at the end of each pass through the "layer" for loop.*/
			int ledge = 0;      //left edge x value
			int redge = getWidth();   //right edge x value
			int tedge = 0;      //top edge y value
			int bedge = getHeight();   //bottom edge y value
//            Color[] testing = {Color.green, Color.blue, new Color(220,0,220), Color.orange, Color.red, Color.BLACK, Color.yellow, Color.darkGray, Color.pink};
			for (int layer = 0; layer < layerTarget; layer++) {
//                System.out.printf("on pass %d :\nledge = %d\nredge = %d\ntedge = %d\nbedge = %d\nrectWidth:%d\nrectHeight:%d\n",layer,ledge,redge,tedge,bedge,rectWidth,rectHeight);

				for (int corner = 0; corner < 4; corner++) {
					int x = 0;//x position for top left corner of rectangle
					int y = 0;//y position for top left corner of rectangle
					int h = 0;//height of rectangle
					int w = 0;//width of rectangle
					switch (corner) {
						case 0://left edge
//                            g2.setColor(testing[layer]);
							x = ledge;
							y = tedge;
							h = bedge - rectHeight - rectHeight * layer;
							w = rectWidth;
//                            System.out.printf("left edge x:%d, y:%d, h:%d, w:%d\n",x,y,h,w);
							break;
						case 1://bottom edge
//                            g2.setColor(testing[layer]);
							x = ledge;
							y = bedge - rectHeight;
							h = rectHeight;
							w = redge - rectWidth - rectWidth * layer;
//                            System.out.printf("bottom edge x:%d, y:%d, h:%d, w:%d\n",x,y,h,w);
							break;
						case 2://right edge
//                            g2.setColor(testing[layer]);
							x = redge - rectWidth;
							y = tedge + rectHeight;
							h = bedge - rectHeight - rectHeight * layer;
							w = rectWidth;
//                            System.out.printf("right edge x:%d, y:%d, h:%d, w:%d\n",x,y,h,w);
							break;
						case 3://top edge
//                            g2.setColor(testing[layer]);
							x = ledge + rectWidth;
							y = tedge;
							h = rectHeight;
							w = redge - rectWidth - rectWidth * layer;
//                            System.out.printf("top edge x:%d, y:%d, h:%d, w:%d\n",x,y,h,w);
							break;
						default:
//                            System.out.println("\n\noh shit, boxes has a default?!?!\n");
							break;
					}// end switch block
//                    System.out.printf("drawing a rectangle at x:%d,y:%d,h:%d,w:%d\n",x,y,h,w);
					g2.drawRect(x, y, w, h);
				}// end corner for loop

                /*Now we update our marg values to represent the outside of the next layer in the process*/
				ledge += rectWidth;
				redge -= rectWidth;
				tedge += rectHeight;
				bedge -= rectHeight;
			}// end layer for loop

		}// end of boxDraw

	}


	class EyeStamp extends Stamp {
		private int eyeEdge;
		private Color circ;


		/**
		 * @param c an integer array that takes an x and a y coordinate in that order
		 */
		protected EyeStamp(Color c) {
			super(c);
			eyeEdge = EDGE;
			setPreferredSize(new Dimension(eyeEdge, eyeEdge));
			circ = c;
			setBackground(Color.white);

		}// end BStamp constructor

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.white);

			eyeDraw(g);
		}

		/**
		 * Draws a pair of eyes via filled circles circumscribed in outlined ovals. The eyes are each centered on the
		 * 1/3 and 2/3 value of this Stamp's width for x-axis and on the 1/2 & 2/3 values of this Stamp's height
		 * for the y-axis.
		 *
		 * @param g the graphics component for this Stamps object
		 */
		private void eyeDraw(Graphics g) {
			int wide = getWidth();
			int height = getHeight();
			// establishing the position of the top left corners for each shape as fractions of stamp's total size.
			int cirxL = wide / 3 - wide / 9;
			int cirxR = 2 * wide / 3 - wide / 9;
			int ciry = 2 * height / 3 - height / 8;
			int ovxL = wide / 3 - wide / 8;
			int ovxR = 2 * wide / 3 - wide / 8;
			int ovy = height / 10;
			int cirRad = wide / 4;
			int ovH = 4 * height / 5;
			int ovW = wide / 4;
	        /* each index of the first order array contains the x,y pairs for the shapes top left corners.
            * the order the shapes appear in is thus:
            * 0:left eye circle
            * 1:left eye oval
            * 2:right eye circle
            * 3:right eye oval      */
			int[][] topLeftCorners = {{cirxL, ciry, cirRad * 9 / 10, cirRad}, {ovxL, ovy, ovW, ovH}, {cirxR, ciry, cirRad * 9 / 10, cirRad}, {ovxR, ovy, ovW, ovH}};

			for (int i = 0; i < topLeftCorners.length; i++) {
				if (i % 2 != 0) {
					g.setColor(Color.black);
					g.drawOval(topLeftCorners[i][0], topLeftCorners[i][1], topLeftCorners[i][2], topLeftCorners[i][3]);
				} else {
					g.setColor(circ);
					g.fillOval(topLeftCorners[i][0], topLeftCorners[i][1], topLeftCorners[i][2], topLeftCorners[i][3]);
				}
			}
		}// end of eyeDraw
	}

	class WordStamp extends Stamp {
		private int wordEdge;

		/**
		 * @param c an integer array that takes an x and a y coordinate in that order
		 */
		protected WordStamp(Color c) {
			super(c);
			wordEdge = EDGE;
			this.c = c;
			setPreferredSize(new Dimension(wordEdge, wordEdge));
			setBackground(Color.white);

		}// end BStamp constructor

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.white);
			wordDraw(g);
		}

		private void wordDraw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			int ledge1, ledge2, ledge3; // ledge should read as "left edge"
			int tedge1, tedge2, tedge3; // tedge should read as "top edge"
			FontMetrics fm = g2.getFontMetrics();
			int width = getWidth();
			int height = getHeight();

			g2.setColor(c);
			// Find out how much the font can grow in width. Turnary operators for the win!!!
			double widthToUse = (double) fm.stringWidth("love") > (double) fm.stringWidth("Java")
					? (double) fm.stringWidth("Love") : (double) fm.stringWidth("Java");
			double widthRatio = (double) getWidth() / widthToUse;

			// input this as the integer value for the font size in the new font
			int newFontSize = (int) (fm.getFont().getSize() * widthRatio);
			//identify which axis is longer and pick the opposite :P
			int comparisonAxis = getWidth() > getHeight() ? getHeight() : getWidth();
			Font f = new Font(fm.getFont().getFontName(), Font.BOLD, Math.min(newFontSize, comparisonAxis) / 3);
			g2.setFont(f);
			fm = g2.getFontMetrics();
            /*setting up our sizes and fonts*/
			Rectangle2D rect1 = fm.getStringBounds("I", g2);
			ledge1 = width / 2 - (int) rect1.getWidth() / 2;
			tedge1 = height / 2 - (int) rect1.getWidth() * 3 / 2;

			Rectangle2D rect2 = fm.getStringBounds("Love", g2);
			ledge2 = width / 2 - (int) rect2.getWidth() / 2;
			tedge2 = height / 2 + (int) rect2.getHeight() / 2;

			Rectangle2D rect3 = fm.getStringBounds("Java", g2);
			ledge3 = width / 2 - (int) rect3.getWidth() / 2;
			tedge3 = height / 2 + (int) rect3.getHeight() * 3 / 2;


			g2.drawString("I", ledge1, tedge1);
			g2.drawString("Love", ledge2, tedge2);
			g2.drawString("Java", ledge3, tedge3);

		}// end of wordDraw
	}// end of WordStamp class object


	public static void main(String[] args) {
		JFrame myWin = new H2_ryan();
	}
}// end of H2_ryan class object

