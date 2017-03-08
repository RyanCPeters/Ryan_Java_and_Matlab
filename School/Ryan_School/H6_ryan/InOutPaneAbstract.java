import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * This abstract class extension of JPanel exists to provide a common flagging tool across the different input/output
 * panels in the application. This allows the parent container to easily verify that each component is in the right place
 * and doing the right job.
 *
 * @author Ryan Peters
 * @date 1/19/2017
 */
abstract class InOutPaneAbstract extends JPanel {
	/*by putting the private constant values into the abstract parent class for the input/output panels I can ensure that
	* all interface objects can easily access essential information without having to write redundant code.
	* */
	// pizza prices
	private final double SMALL = 11.99;
	private final double MED = 15.99;
	private final double LARGE = 18.99;
	// topping prices
	private final double OREGANO = 1.05;
	private final double SPINACH = 1.25;
	private final double PEPPERONI = 1.55;
	private final double HAWAIIAN = 1.95;
	// dessert prices
	private final double COOKSUN = 6.95;
	private final double ROOTBEER = 4.95;
	private final double ICECREAM = 4.50;

	private OrderCalculatorGUI parent;

	// the main reason for creating the abstract class is to provide all child classes with a common flagging system
	/**
	 * used to determine if the panel is a part of the inputs or outputs (irrelavant to assignment but nice should I try to implement an expandable window.)
	 * NOTE that the abstract class InOutPaneAbstract sets ioFlagg inOut to be ioFlagg.IMIN as it reduces the amount of
	 * overriding for the constructors of input panels. As there is only one output panel this made the most sense for
	 * reducing redundant code.
	 */
	ioFlagg inOut = ioFlagg.IMIN;


	/**
	 * by default each panel's order state is set to NO, indicating no selection has been made in that category yet.
	 */
	orderFlagg ordrState = orderFlagg.NO;

	/*
	 * because the size of this array depends on the I/O context of the panel it's not initialized until inOut is set.
	 *
	 *      For input panels, this array wild hold a reference to the 4 output panels, including the "this" reference,
	 *      as it helps keep consistent ordering across all children of this abstract class.
	 *
	 *      input panel order is uniform across all panels, by their titel name, in the following order:
	 *      index 0: GreetingsPanel.getMyName() -> "Greetings"
	 *      index 1: PizzaPanel.getMyName() -> "Pizza"
	 *      index 2: ToppingsPanel.getMyName() -> "Toppings"
	 *      index 3: DessertPanel.getMyName() -> "Desserts"
	  */
	private InOutPaneAbstract[] friends;


	// each panel should have it's own border object.
	private Border myBorder;
	private double myCost = 0;
	private String myName = "";// this is to ensure that calls by getMyName() will have a non null target.

	/**
	 * The empty constructor exists for basic initiation of class fields to help prevent NullPointerExceptions.
	 */
	InOutPaneAbstract() {
		super();
		this.myBorder = BorderFactory.createEmptyBorder();
	}

	/**
	 * @param parent the container to hold this object
	 */
	void setMyFrame(OrderCalculatorGUI parent) {
		this.parent = parent;
	}


	/**
	 * Creates a new JPanel with the specified layout manager and buffering
	 * strategy.
	 *
	 * @param layout           the LayoutManager to use
	 * @param isDoubleBuffered a boolean, true for double-buffering, which uses additional memory space to achieve fast, flicker-free
	 */
	InOutPaneAbstract(LayoutManager layout, boolean isDoubleBuffered, wrkFlagg work, ioFlagg inOut) {
		super(layout, isDoubleBuffered);
		setIOContext(inOut);

	}

	/**
	 * Create a new buffered JPanel with the specified layout manager
	 *
	 * @param layout the LayoutManager to use
	 * @param parent
	 */
	InOutPaneAbstract(LayoutManager layout, JPanel parent, wrkFlagg work, ioFlagg inOut) {
		super(layout);
		setIOContext(inOut);
	}

	/**
	 * Create a new buffered JPanel with the specified layout manager
	 *
	 * @param layout the LayoutManager to use
	 * @param parent
	 */
	InOutPaneAbstract(LayoutManager layout, JPanel parent, wrkFlagg work, ioFlagg inOut, Border myBorder) {
		super(layout);
		setIOContext(inOut);
		this.myBorder = myBorder;
	}

	/**
	 * Creates a new <code>JPanel</code> with <code>FlowLayout</code>
	 * and the specified buffering strategy.
	 * If <code>isDoubleBuffered</code> is true, the <code>JPanel</code>
	 * will use a double buffer.
	 *
	 * @param isDoubleBuffered a boolean, true for double-buffering, which
	 *                         uses additional memory space to achieve fast, flicker-free
	 *                         updates
	 * @param parent
	 */
	InOutPaneAbstract(boolean isDoubleBuffered, JPanel parent, wrkFlagg work, ioFlagg inOut) {
		super(isDoubleBuffered);
		setIOContext(inOut);
	}


	/**
	 * @param inOut the new ioFlagg enum to represent this panel's roll in the I/O relationship.
	 */
	void setInOut(ioFlagg inOut) {
		setIOContext(inOut);
	}

	/**
	 * @param name
	 */
	void setMyName(String name) {
		this.myName = name;
	}


	/**
	 * this method is used as an easy shortcut for accessing task related components across multiple containers.
	 * be sure to set 0 to THE_HOUSE, 1 to IMIN, and 2 for IMOUT
	 *
	 * @param friend the container object to add to the friends array
	 */
	void setUpdateFriendList(InOutPaneAbstract friend) {
		if (friend.getioFlagg() == ioFlagg.IMIN) {// if true then friend must be in the same context as this panel and might need to share info.
			switch (friend.getMyName()) {
				case "Greetings":
					friends[0] = friend;
					break;

				case "Pizza":
					friends[1] = friend;
					break;

				case "Toppings":
					friends[2] = friend;
					break;

				case "Dessert":
					friends[3] = friend;
					break;
			}
		}

	}// end of setUpdateFriendsList()

	/**
	 * @param populatedList accepts an InOutPaneAbstract array, this array should be pupulated with the input panes.
	 */
	void setFriendsList(InOutPaneAbstract[] populatedList) {
		this.friends = populatedList;
	}

	/*
	* use this method in place of directly using this.inOut and this.friends as it ensures that all related fields are
	* properly initialized, thus avoiding NullPointerExceptions... hopefully.
	* */
	private void setIOContext(ioFlagg inOut) {
		if (inOut == ioFlagg.IMIN) {
			//this must be an input component and we need to make sure all related fields reflect that
			this.friends = new InOutPaneAbstract[4];
		}
	}

	void setOrderState(orderFlagg ordr) {
		this.ordrState = ordr;
	}

	/**
	 * Use this method at some point before you make a call to getMyBorder() or else you risk
	 * getting a NullPointerException
	 *
	 * @param brdrColor1 the border will transition from this color
	 * @param brdrColor2 to this color, but if you want only one color enter the same value as in brdrColor1
	 * @param title      The title to place on the border of this panel.
	 */
	void setMyBorder(Color brdrColor1, Color brdrColor2, String title) {
		myBorder = BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(
						1,
						brdrColor1,
						brdrColor2),
				title);
	}

	/**
	 * a method for setting the minimum and preferred sizes for the panel
	 */
	void setThisSize() {
		setMinimumSize(getMinimumSize());
		setPreferredSize(getPreferredSize());
	}

	/**
	 * a method for setting the minimum and preferred sizes for the panel
	 */
	void setThisSize(JComponent comp) {
		comp.setMinimumSize(getMinimumSize());
		comp.setPreferredSize(getPreferredSize());
	}

	void setMyCost(double cost) {
		myCost += cost;
	}


	/**
	 * @return the orderFlagg state this panel is currently in.
	 */
	orderFlagg getOrdrState() {
		return ordrState;
	}

	/**
	 * @return
	 */
	OrderCalculatorGUI getMyFrame() {
		return parent;
	}

	/**
	 * @param name the string associated with the component that signalled that a product was chosen
	 * @return price associated with the product by the name in the parameter 'name'
	 */
	double getPrice(String name) {
		switch (name) {
			case "Small":
				return SMALL;
			case "Medium":
				return MED;
			case "Large":
				return LARGE;
			case "Oregano":
				return OREGANO;
			case "Spinach":
				return SPINACH;
			case "Pepperoni":
				return SPINACH;
			case "Hawaiian":
				return HAWAIIAN;
			case "Cookie Sunday":
				return COOKSUN;
			case "Root Beer Float":
				return ROOTBEER;
			case "Ice Cream":
				return ICECREAM;
			case "None":
				return 0;
			default:
				return 0;
		}
	}

	/**
	 * @return the friends array of this pane.
	 */
	InOutPaneAbstract[] getFriendsList() {
		return friends;
	}

	/**
	 * @return
	 */
	String getMyName() {
		return myName;
	}

	ioFlagg getioFlagg() {
		return this.inOut;
	}

	/**
	 * @return the Border object stored in the myBorder field.
	 */
	Border getMyBorder() {
		return myBorder;
	}

	/**
	 * This method is only applicable to the input panels. But to ensure proper handeling of a call erroneusly made on
	 * and output panel, a conditional statement will return -1 to signify that something's wrong.
	 *
	 * @return the double value for this panels total costs as tabulated from user inputs
	 */
	double getMyCost() {
		return (getioFlagg() == ioFlagg.IMIN) ? (myCost) : (-1);
	}


	/**
	 * Use these flags to assign each panel an easy and safe mechanism for identifying it's roll in the I/O structure
	 * <p>
	 * <p>
	 * legend:
	 * IMIN : panels possessing this enum have been assigned to some aspect of <b>INPUT</b> handeling.
	 * <p>
	 * IMOUT : panels possessing this enum have been assigned to some aspect of <b>OUTPUT</b> handeling.
	 */
	enum ioFlagg {
		IMIN, IMOUT
	}

	/**
	 * Use these flags to identify if the panel is currently being utilized or if it's only been instantiated but not
	 * currently visible to the user.
	 * <p>
	 * WORKING : panels possessing this enum have been instantiated, assigned to a parent container and are expected to
	 * be visible to the user.
	 * <p>
	 * UNEMPLOYED   : panels possessing this enum have been instantiated, but are not assigned to a parent container and
	 * are expected to not be visible to the user.
	 */
	enum wrkFlagg {
		WORKING, UNEMPLOYED
	}

	/**
	 * this enum is used to indicate that a selection in the given input panel's are has been made.
	 */
	enum orderFlagg {
		YES, NO
	}

	class ItemListening implements ItemListener {
		ItemListening() {
		}

		/**
		 * Invoked when an item has been selected or deselected by the user.
		 * The code written for this method performs the operations
		 * that need to occur when an item is selected (or deselected).
		 *
		 * @param e
		 */
		@Override
		public void itemStateChanged(ItemEvent e) {
			JToggleButton jTog = (JToggleButton) e.getSource();
			if (e.getStateChange() == ItemEvent.SELECTED) {
				setMyCost(getPrice(jTog.getText()));
			} else if (e.getStateChange() == ItemEvent.DESELECTED) {
				setMyCost(-1 * getPrice(jTog.getText()));
			}
			if (getMyCost() < 1) {
				setOrderState(orderFlagg.NO);
			} else {
				setOrderState(orderFlagg.YES);
			}
			getMyFrame().updateOrder();
		}
	}// end of ItemListening inner class
}// end of InOutPaneAbstract class


