import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class creates a window requesting the user to input the total sales for the month, then it will calculate the
 * amount owed for state and county sales tax which will be presented to the user in a separate window that displays
 * how much is owed. That output is in 3 forms, first the amount owed to the state, then the amount owed to the county,
 * then the sum of those two values as a total taxes owed.
 *
 * @author Ryan Peters
 * @date 1/18/2017
 */
public class SalesTax extends JFrame {
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
	public SalesTax() throws HeadlessException {
		TheHouse win = new TheHouse();
	}

	/**
	 * Creates a new, initially invisible <code>Frame</code> with the
	 * specified title.
	 * <p>
	 * This constructor sets the component's locale property to the value
	 * returned by <code>JComponent.getDefaultLocale</code>.
	 *
	 * @param title the title for the frame
	 * @throws HeadlessException if GraphicsEnvironment.isHeadless()
	 *                           returns true.
	 * @see GraphicsEnvironment#isHeadless
	 * @see Component#setSize
	 * @see Component#setVisible
	 * @see JComponent#getDefaultLocale
	 */
	public SalesTax(String title) throws HeadlessException {
		super(title);
		TheHouse win = new TheHouse();
	}

	interface IHandyNotifier {

		FlagStatus getWorkStatus();

		void setWorkStatus(FlagStatus werket);

		/**
		 * be sure to set 0 to THE_HOUSE, 1 to IMIN, and 2 for IMOUT
		 *
		 * @param buddy  the container object's in/out flag
		 * @param friend the container object in question
		 */
		void updateFriendList(FlagStatus buddy, Container friend);

		FlagStatus areYouIn();

		void setInOut(FlagStatus InOut);

		enum FlagStatus {THE_HOUSE, IMOUT, IMIN, WORKING, UNEMPLOYED}

	}

	protected class TheHouse extends JFrame implements IHandyNotifier {
		private static final double CTAX_RATE = .02;
		private static final double STAX_RATE = .04;
		private static final int OUT_TXTFIELDS = 3;
		private static final String STR_NSTRCT1 = "Please enter the total sales";
		private static final String STR_NSTRCT2 = "for the past month in the space bellow.";
		private final String[] outFieldPrefix = {
				"County taxes owed = $", "State taxes owed = $", "Total taxes owed = $"};
		// the 4 InOutContainer objects are the constituent parts of the window which handle the presentation of inforamation.
		private InOutContainer contPane;
		private InOutContainer inPane;
		private InOutContainer outPane;
		private InOutContainer nestedCenter;
		private InOutContainer nestedNorth;
		private InOutContainer nestedSouth;
		private InOutContainer nestedEast;
		private InOutContainer nestedWest;
		// input components
		private JButton calcTaxBtn;
		private JTextField inputTxt;
		private JLabel instructLabel;
		// output components
		private JLabel countyLable;
		private JLabel stateLable;
		private JLabel totalLable;
		// the layout constraint references can be a part of the outer class because they will never multi instantiate.
		private GridBagConstraints stateCon;
		private GridBagConstraints countyCon;
		private GridBagConstraints totalCon;
		private GridBagConstraints buttonCons;
		private GridBagConstraints labelCons;
		private GridBagConstraints txtCons;
		// friends and InOrOut are a part of the inter-class flagging used to signify when to update or resize.
		private Container[] friends;
		/**
		 * if there is a need to change the number of output fields in order to accomidate a change in project specs, it
		 * will be necessary to change the element assignment for the taxes float[] object at the start of the
		 * setOutPane() method.
		 * <p>
		 * currently, this array will hold county tax at index 0, state tax at index 1, and total tax at index 2.
		 */
		private double[] taxes;


		public TheHouse() {
			// initializing GridBagLayout Constraint objects
			stateCon = new GridBagConstraints();
			countyCon = new GridBagConstraints();
			totalCon = new GridBagConstraints();
			buttonCons = new GridBagConstraints();
			labelCons = new GridBagConstraints();
			txtCons = new GridBagConstraints();

			new JFrame("Calculate Sales Tax");
			friends = new Container[3];
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			contPane = new InOutContainer();
			contPane.setLayout(new BoxLayout(contPane, BoxLayout.Y_AXIS));
			setContentPane(contPane);

			contPane.add(setInPane());
			inPane.setWorkStatus(FlagStatus.WORKING);
			setMinimumSize(getMinimumSize());
			setPreferredSize(getPreferredSize());
			setMaximumSize(getPreferredSize());
			pack();
			validate();

			setVisible(true);
		}

		/**
		 * setInPane handles all the details of creating the constituent components for creating the input elements of
		 * the window.
		 *
		 * @return the newly created inPane InOutContainer object.
		 */
		private InOutContainer setInPane() {
			int minWidth = 382;
			int minHeight = 40;
			Dimension minSize = new Dimension(minWidth, (minHeight / 3) + 1);
			inPane = new InOutContainer(new BorderLayout(2, 2), FlagStatus.IMIN);
			nestedNorth = new InOutContainer(new GridBagLayout(), FlagStatus.IMIN);
			nestedWest = new InOutContainer(new GridBagLayout(), FlagStatus.IMIN);
			nestedSouth = new InOutContainer(new GridBagLayout(), FlagStatus.IMIN);
			nestedCenter = new InOutContainer();


			instructLabel = new JLabel("<html>" + STR_NSTRCT1 + STR_NSTRCT2 + "</html>", SwingConstants.LEFT);
			JLabel testLabel = new JLabel("<html><b>$</b></html>");
			inputTxt = new JTextField(" 11000.21  ");
			testLabel.setLayout(new GridBagLayout());
			testLabel.add(inputTxt, txtCons);
			calcTaxBtn = new JButton("<html><center><b>Calculate<br><center>Tax</b></html>");
			calcTaxBtn.addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (String.valueOf(friends[2]) != "null") {
								getContentPane().remove(1);
								outPane.setWorkStatus(FlagStatus.UNEMPLOYED);
							}
							invalidate();
							getContentPane().add(setOutPane(Double.parseDouble(inputTxt.getText())), 1);
							outPane.setWorkStatus(FlagStatus.WORKING);
							outPane.setMinimumSize(outPane.getMinimumSize());
							outPane.setPreferredSize(outPane.getPreferredSize());
							inPane.setMinimumSize(inPane.getMinimumSize());
							inPane.setPreferredSize(inPane.getPreferredSize());
							setMinimumSize(new Dimension(
									outPane.getMinimumSize().width - 1,
									(outPane.getMinimumSize().height + 6) * 2));
							setPreferredSize(getMinimumSize());
							setMaximumSize(getPreferredSize());
							pack();
							validate();
						}
					}
			);// end of lambda ActionListener() at least I think this is classified as a lambda  ¯\_(ツ)_/¯

			inPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.MAGENTA, Color.red));
			FontMetrics fm;

			fm = instructLabel.getFontMetrics(instructLabel.getFont());
//            instructLabel.setMinimumSize(new Dimension(fm.stringWidth(instructLabel.getText()), (fm.getHeight() + 2)*2));
			instructLabel.setMinimumSize(minSize);
			instructLabel.setPreferredSize(instructLabel.getMinimumSize());


			fm = inputTxt.getFontMetrics(inputTxt.getFont());
			inputTxt.setMinimumSize(new Dimension(fm.stringWidth(inputTxt.getText()), fm.getHeight() + 2));
			inputTxt.setPreferredSize(inputTxt.getMinimumSize());
			testLabel.setMinimumSize(new Dimension(fm.stringWidth(inputTxt.getText()) + 15, fm.getHeight() + 3));
			testLabel.setPreferredSize(testLabel.getMinimumSize());

			nestedNorth.add(instructLabel, labelCons);
			nestedCenter.add(testLabel);

			inPane.add(nestedNorth, BorderLayout.PAGE_START);
			inPane.add(nestedCenter, BorderLayout.CENTER);

			inPaneGBLBuilder();


			inPane.add(calcTaxBtn, BorderLayout.PAGE_END);
//            instructLabel.setVisible(true);
//            inputTxt.setVisible(true);
//            calcTaxBtn.setVisible(true);
//            inPane.setVisible(true);
			inPane.setMinimumSize(inPane.getMinimumSize());
			inPane.setPreferredSize(inPane.getMinimumSize());
			pack();
			return inPane;
		}// end of setInPane() method


		/**
		 * setOutPane handles all of the details involved with creating and placing the components involved with the output
		 * presentation of the window.
		 *
		 * @param sales the value collected from inputTxt
		 * @return the newly created and populated outPane InOutContainer object
		 */
		private InOutContainer setOutPane(double sales) {
			int minWidth = 382;
			int minHeight = 100;
			Dimension minSize = new Dimension(minWidth, (minHeight / 3) + 1);

			outPane = new InOutContainer(new GridBagLayout(), FlagStatus.IMOUT);
			outPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.MAGENTA, Color.red));
			outPane.setInOut(FlagStatus.IMOUT);
//            containerSizeSetter(outPane, INOUT_WIDTH, INOUT_HEIGHT);
			outPaneGBLBuilder();
			GridBagConstraints[] baggage = {countyCon, stateCon, totalCon};
			taxes = new double[OUT_TXTFIELDS];
			taxes[0] = (sales * CTAX_RATE);
			taxes[1] = (sales * STAX_RATE);
			taxes[2] = taxes[0] + taxes[1];
			JLabel[] labels = {countyLable, stateLable, totalLable};
			Border[] outBorders = {
					(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.blue, Color.cyan)),
					(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.cyan, Color.ORANGE)),
					(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.ORANGE, Color.red))};
			for (int i = 0; i < OUT_TXTFIELDS; i++) {
				String s = String.format(outFieldPrefix[i] + "%.2f", taxes[i]);
				labels[i] = new JLabel(s);
				labels[i].setBorder(outBorders[i]);
				labels[i].setMinimumSize(instructLabel.getMinimumSize());
				labels[i].setPreferredSize(instructLabel.getPreferredSize());
				outPane.add(labels[i], baggage[i]);
			}
			updateFriendList(outPane.areYouIn(), outPane);
			outPane.getComponent(0).setMinimumSize(minSize);
			outPane.getComponent(0).setPreferredSize(minSize);
			outPane.getComponent(1).setMinimumSize(minSize);
			outPane.getComponent(1).setPreferredSize(minSize);
			outPane.getComponent(2).setMinimumSize(minSize);
			outPane.getComponent(2).setPreferredSize(minSize);

			return outPane;
		}// end of setOutPane() method

		/*
		*This method creates the GridBagLayout manager and the specific constraint objects for the input pane configuration.
		* */
		private void inPaneGBLBuilder() {
			// specify each constraint object's specific details
			labelCons.insets = buttonCons.insets =
					new Insets(
							0,
							0,
							0,
							0);

			labelCons.gridheight = 1;
			labelCons.gridwidth = 3;
			labelCons.gridx = 0;
			labelCons.gridy = 0;
			labelCons.fill = GridBagConstraints.NONE;
			labelCons.weighty = .3;
			labelCons.weightx = 1;
			labelCons.anchor = GridBagConstraints.LAST_LINE_START;

			txtCons.gridheight = 1;
			txtCons.gridwidth = 3;
			txtCons.gridx = 2;
			txtCons.gridy = 1;
			txtCons.fill = GridBagConstraints.BOTH;
			txtCons.weighty = 1;
			txtCons.weightx = 1;
			txtCons.anchor = GridBagConstraints.LINE_END;
			txtCons.insets = new Insets(2, 15, 2, 15);

			buttonCons.gridheight = 1;
			buttonCons.gridwidth = 2;
			buttonCons.gridx = 0;
			buttonCons.gridy = 2;
			buttonCons.fill = GridBagConstraints.NONE;
			buttonCons.weighty = 1;
			buttonCons.weightx = 1;
			buttonCons.anchor = GridBagConstraints.FIRST_LINE_START;

			// here concludes setting up the gridbagconstraints

			// this action listener is placed in this method to ensure this method never gets called till inPane has been
			// configured to accept these settings.

		}// end inPaneGBLBuilder()

		private void outPaneGBLBuilder() {
			// specify each constraint object's specific details
			totalCon.insets = countyCon.insets = stateCon.insets = new Insets(
					0,
					0,
					0,
					0);
//
			countyCon.gridheight = 1;
			countyCon.gridwidth = 3;
			countyCon.ipady = 5;
			countyCon.ipadx = 10;
			countyCon.gridx = 1;
			countyCon.gridy = 0;
			countyCon.fill = GridBagConstraints.HORIZONTAL;
			countyCon.weighty = 0;
			countyCon.weightx = 1;
			countyCon.anchor = GridBagConstraints.FIRST_LINE_START;

			stateCon.gridheight = 1;
			stateCon.gridwidth = 3;
			stateCon.ipady = 4;
			stateCon.ipadx = 11;
			stateCon.gridx = 1;
			stateCon.gridy = 1;
			stateCon.fill = GridBagConstraints.BOTH;
			stateCon.weighty = 0;
			stateCon.weightx = 1;
			stateCon.anchor = GridBagConstraints.FIRST_LINE_START;

			totalCon.gridheight = 1;
			totalCon.gridwidth = 3;
			totalCon.ipady = 5;
			totalCon.ipadx = 17;
			totalCon.gridx = 1;
			totalCon.gridy = 2;
			totalCon.fill = GridBagConstraints.HORIZONTAL;
			totalCon.weighty = 1;
			totalCon.weightx = 1;
			totalCon.anchor = GridBagConstraints.FIRST_LINE_START;

		}// close outPaneGBLBuilder() method


		/**
		 *
		 */
		public void tellThemAll() {
		}

		@Override
		public FlagStatus getWorkStatus() {// another stub
			return null;
		}

		@Override
		public void setWorkStatus(FlagStatus werket) {
			//Stub out
		}

		/**
		 * @param buddy
		 * @param friend
		 */
		@Override
		public void updateFriendList(FlagStatus buddy, Container friend) {
			if (buddy == FlagStatus.THE_HOUSE) {
				friends[0] = friend;
			} else if (buddy == FlagStatus.IMOUT) {
				friends[2] = friend;
			} else {
				friends[1] = friend;
			}

		}

		@Override
		public FlagStatus areYouIn() {
			return FlagStatus.THE_HOUSE;
		}

		@Override
		public void setInOut(FlagStatus InOut) {

		}


		/**
		 *
		 */
		private class InOutContainer extends JPanel implements IHandyNotifier {
			protected Container[] friends;
			protected FlagStatus employment;
			protected FlagStatus InOrOut;

			/**
			 * Create a new buffered JPanel with the specified layout manager
			 *
			 * @param layout the LayoutManager to use
			 */
			public InOutContainer(LayoutManager layout, FlagStatus InOrOut) {
				super(layout);

				this.friends = new Container[2];
				this.InOrOut = InOrOut;
				employment = FlagStatus.UNEMPLOYED;
			}

			/**
			 * Creates a new <code>JPanel</code> with a double buffer
			 * and a flow layout.
			 */
			public InOutContainer() {
				this.friends = new Container[2];
				employment = FlagStatus.UNEMPLOYED;
			}

			@Override
			public FlagStatus getWorkStatus() {
				return InOrOut;
			}

			@Override
			public void setWorkStatus(FlagStatus werket) {
				employment = werket;
			}

			/**
			 * @param buddy
			 * @param friend
			 */
			@Override
			public void updateFriendList(FlagStatus buddy, Container friend) {
				if (buddy == FlagStatus.THE_HOUSE) {
					friends[0] = friend;
				} else if (buddy == FlagStatus.IMOUT) {
					friends[2] = friend;
				} else {
					friends[1] = friend;
				}

			}

			/**
			 * @return
			 */
			@Override
			public FlagStatus areYouIn() {
				return InOrOut;
			}

			/**
			 * @param InOut
			 */
			@Override
			public void setInOut(FlagStatus InOut) {
				this.InOrOut = InOut;
			}

			protected boolean isMatchinStatus(FlagStatus workStatus) {
				return String.valueOf(this.employment) == String.valueOf(workStatus);
			}

		}// end of InOutContainer class

		/**
		 * This interface allows the assurance of a common flagging tool for use between the frame and it's panel components
		 * to notify of when resizing needs to happen and when to add or remove a component.
		 */

	}// end of TheHouse
}//end of SalesTax class
