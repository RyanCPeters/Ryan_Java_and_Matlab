

import javax.swing.*;
import java.awt.*;

/**
 * This panel is the container for a JLabel with a greetings message centered in in the window.
 * Is a part of the input structure
 *
 * @author Ryan Peters
 * @date 1/19/2017
 */
class GreetingsPanel extends InOutPaneAbstract {
	private final String GREETING = "Welcome to MoonBuck Pizza";
	JLabel label;

	/**
	 * The empty constructor exists for basic initiation of class fields to help prevent NullPointerExceptions.
	 */
	GreetingsPanel() {
		super();
		this.label = new JLabel(GREETING);
		FontMetrics fm = label.getFontMetrics(label.getFont());
		Dimension dm = new Dimension(fm.stringWidth(label.getText()), fm.getHeight());
		label.setMinimumSize(dm);
		label.setPreferredSize(dm);
		label.setMaximumSize(dm);
		this.add(label);
		setInOut(ioFlagg.IMIN);
		setMyName("Greetings");

	}

	/**
	 * @param parent
	 */
	GreetingsPanel(OrderCalculatorGUI parent) {
		this();
		setMyFrame(parent);
	}


}
