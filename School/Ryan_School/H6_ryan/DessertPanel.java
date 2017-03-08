import javax.swing.*;
import java.awt.*;

/**
 * holds 4 radio buttons corresponding to desserts. This panel's contents are slightly grayed out until a pizza selection
 * is made.
 * Is a part of the input structure
 *
 * @author Ryan Peters
 * @date 1/19/2017
 */
class DessertPanel extends InOutPaneAbstract {
	private final Color clr2 = Color.yellow;
	private final Color clr1 = Color.red;
	private final Color DISABLED = Color.gray;
	private JRadioButton non;
	private JRadioButton cooksun;
	private JRadioButton rootbeer;
	private JRadioButton icecream;


	/**
	 * The empty constructor exists for basic initiation of class fields to help prevent NullPointerExceptions.
	 */
	DessertPanel() {
		super();
		ItemListening listen = new ItemListening();
		setMyName("Dessert");
		setMyBorder(DISABLED, DISABLED, getMyName());
		setInOut(ioFlagg.IMIN);

		setLayout(new GridLayout(4, 1, 0, 9));

		non = new JRadioButton("None");
		non.addItemListener(listen);
		cooksun = new JRadioButton("Cookie Sunday");
		cooksun.addItemListener(listen);
		rootbeer = new JRadioButton("Root Beer Float");
		rootbeer.addItemListener(listen);
		icecream = new JRadioButton("Ice Cream");
		icecream.addItemListener(listen);
		toggleEnabler(false);
		ButtonGroup bg = new ButtonGroup();
		bg.add(non);
		bg.add(cooksun);
		bg.add(rootbeer);
		bg.add(icecream);

		setThisSize(non);
		setThisSize(cooksun);
		setThisSize(rootbeer);
		setThisSize(icecream);

		add(non);
		add(cooksun);
		add(rootbeer);
		add(icecream);
		setThisSize();

	}

	/**
	 * @param parent
	 */
	DessertPanel(OrderCalculatorGUI parent) {
		this();
		setMyFrame(parent);
	}


	void setMyBorder() {
		super.setMyBorder(clr1, clr2, getMyName());
		validate();
	}

	void toggleEnabler(boolean setTothis) {
		non.setEnabled(setTothis);
		cooksun.setEnabled(setTothis);
		rootbeer.setEnabled(setTothis);
		icecream.setEnabled(setTothis);


	}

	void setEnableBorder() {
		setMyBorder(clr1, clr2, getMyName());
	}
}
