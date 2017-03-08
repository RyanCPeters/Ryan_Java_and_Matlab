import javax.swing.*;
import java.awt.*;

/**
 * provides the GUI with 3 radio buttons corresponding to small/medium/large pizza orders inside of a titled border.
 * Is a part of the input structure
 *
 * @author Ryan Peters
 * @date 1/19/2017
 */
class PizzaPanel extends InOutPaneAbstract {
	private final Color clr2 = Color.red;
	private final Color clr1 = Color.green;
	private JRadioButton sml;
	private JRadioButton med;
	private JRadioButton lg;

	/**
	 * The empty constructor exists for basic initiation of class fields to help prevent NullPointerExceptions.
	 */
	public PizzaPanel() {
		super();
		ItemListening listen = new ItemListening();
		setMyName("Pizza");
		setMyBorder(clr1, clr2, getMyName());
		setLayout(new GridLayout(3, 1, 0, 20));
		setInOut(ioFlagg.IMIN);
		ButtonGroup bg = new ButtonGroup();


		sml = new JRadioButton("Small");
		sml.addItemListener(listen);
		med = new JRadioButton("Medium");
		med.addItemListener(listen);
		lg = new JRadioButton("Large");
		lg.addItemListener(listen);

		bg.add(sml);
		bg.add(med);
		bg.add(lg);

		add(sml);
		add(med);
		add(lg);
		setThisSize();

	}

	/**
	 * @param parent
	 */
	PizzaPanel(OrderCalculatorGUI parent) {
		this();
		setMyFrame(parent);
	}


}
