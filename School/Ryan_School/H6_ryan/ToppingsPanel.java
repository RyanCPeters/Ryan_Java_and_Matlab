import javax.swing.*;
import java.awt.*;

/**
 * provides the GUI with 4 check boxes corresponding to JLabels containing text for different toppings, all inside of a
 * titled border.
 * Is a part of the input structure
 *
 * @author Ryan Peters
 * @date 1/19/2017
 */
class ToppingsPanel extends InOutPaneAbstract {

	private final Color clr2 = Color.green;
	private final Color clr1 = Color.yellow;
	private JCheckBox oreg;
	private JCheckBox spin;
	private JCheckBox pep;
	private JCheckBox haw;

	ToppingsPanel() {
		super();
		ItemListening listen = new ItemListening();
		setMyName("Toppings");
		setMyBorder(clr1, clr2, getMyName());
		setInOut(ioFlagg.IMIN);
		setLayout(new GridLayout(4, 1, 0, 9));


		ButtonGroup bg = new ButtonGroup();
		bg.add(oreg);
		bg.add(spin);
		bg.add(pep);
		bg.add(haw);

		oreg = new JCheckBox("Oregano");
		spin = new JCheckBox("Spinach");
		pep = new JCheckBox("Pepperoni");
		haw = new JCheckBox("Hawaiian");

		oreg.addItemListener(listen);
		spin.addItemListener(listen);
		pep.addItemListener(listen);
		haw.addItemListener(listen);

		add(oreg);
		add(spin);
		add(pep);
		add(haw);
	}

	/**
	 * @param parent
	 */
	ToppingsPanel(OrderCalculatorGUI parent) {
		this();
		setMyFrame(parent);
	}


}
