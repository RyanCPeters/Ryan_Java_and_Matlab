

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

/**
 * This class extends JFrame and will hold the 4 input panels as well as 2 buttons for calculating the cost of an order
 * or for exiting the app.
 *
 * @author Ryan Peters
 * @date 1/19/2017
 */
class OrderCalculatorGUI extends JFrame {

	private final String TITLE = "Order Calculator";

	private GreetingsPanel greetings;
	private PizzaPanel pizza;
	private ToppingsPanel toppings;
	private DessertPanel dessert;
	private JPanel contentPaneIn;
	// the values correspond to product selections in pizza, toppings and dessert panels, in that order.
	private double[] orderCost = {0, 0, 0};
	private InOutPaneAbstract[] friendsMasterList;
	private JButton calcOrderBtn;
	private JButton cancelBtn;
	// these panels will nest inside of the forderlayout compartments of the content pane
	private JPanel nestedCenter;
	private JPanel nestedNorth;
	private JPanel nestedSouth;


	OrderCalculatorGUI() {
		friendsMasterList = new InOutPaneAbstract[4];
		setTitle(TITLE);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		contentPaneIn = (JPanel) getContentPane();
		contentPaneIn.setLayout(new BorderLayout(4, 5));
		greetings = new GreetingsPanel(this);
		pizza = new PizzaPanel(this);
		toppings = new ToppingsPanel(this);
		dessert = new DessertPanel(this);
//        outPane = new OutputPane(this);

		// populate the friends list for greetings
		friendsMasterList[0] = greetings;
		friendsMasterList[1] = pizza;
		friendsMasterList[2] = toppings;
		friendsMasterList[3] = dessert;

		for (InOutPaneAbstract pane : friendsMasterList) {
			pane.setBorder(pane.getMyBorder());
			pane.setThisSize();
		}

		nestedCenter = new JPanel();
		nestedCenter.setLayout(new BoxLayout(nestedCenter, BoxLayout.X_AXIS));
		nestedNorth = new JPanel();
		nestedNorth.setLayout(new BoxLayout(nestedNorth, BoxLayout.X_AXIS));
		nestedSouth = new JPanel();
		nestedSouth.setLayout(new BoxLayout(nestedSouth, BoxLayout.X_AXIS));


		nestedNorth.add(Box.createHorizontalGlue());
		nestedNorth.add(greetings);
		nestedNorth.add(Box.createHorizontalGlue());


		nestedCenter.add(Box.createGlue());
		nestedCenter.add(pizza);
		nestedCenter.add(Box.createHorizontalStrut(5));
		nestedCenter.add(toppings);
		nestedCenter.add(Box.createHorizontalStrut(5));
		nestedCenter.add(dessert);
		nestedCenter.add(Box.createRigidArea(new Dimension(5, 1)));
		nestedCenter.add(Box.createGlue());


		calcOrderBtn = new JButton("<html>Calculate</html>");
		cancelBtn = new JButton("<html>Cancel<html>");

		// most of the explicit integer values for height or width were just numbers I settled on as a result of trial
		// and error. I need to find a way to more precicely and perhaps automatically implement this kind of ui adjustment.
		greetings.setMinimumSize(new Dimension(nestedNorth.getMinimumSize().width, 20));
		greetings.setPreferredSize(new Dimension(nestedNorth.getPreferredSize().width, 20));
		greetings.setMaximumSize(new Dimension(nestedNorth.getPreferredSize().width, 20));

		nestedNorth.setMinimumSize(new Dimension(nestedNorth.getMinimumSize().width, 30));
		nestedNorth.setPreferredSize(new Dimension(nestedNorth.getPreferredSize().width, 30));
		nestedNorth.setMaximumSize(new Dimension(nestedNorth.getPreferredSize().width, 30));

		toppings.setMinimumSize(toppings.getMinimumSize());
		toppings.setPreferredSize(toppings.getPreferredSize());
		toppings.setMaximumSize(toppings.getPreferredSize());

		pizza.setMinimumSize(toppings.getMinimumSize());
		pizza.setPreferredSize(toppings.getPreferredSize());
		pizza.setMaximumSize(toppings.getMaximumSize());

		dessert.setMinimumSize(toppings.getMinimumSize());
		dessert.setPreferredSize(toppings.getPreferredSize());
		dessert.setMaximumSize(toppings.getPreferredSize());

		nestedCenter.setMinimumSize(new Dimension(nestedCenter.getMinimumSize().width + 50, nestedCenter.getMinimumSize().height));
		nestedCenter.setPreferredSize(nestedCenter.getPreferredSize());
		nestedCenter.setMaximumSize(new Dimension(nestedCenter.getPreferredSize().width + 100, 300));

		nestedSouth.add(Box.createRigidArea(new Dimension(20, 40)));
		nestedSouth.add(calcOrderBtn);
		nestedSouth.add(Box.createHorizontalStrut(10));
		nestedSouth.add(cancelBtn);
		nestedSouth.add(Box.createRigidArea(new Dimension(20, 40)));

		nestedSouth.setMinimumSize(new Dimension(nestedSouth.getMinimumSize().width, 60));
		nestedSouth.setPreferredSize(nestedSouth.getPreferredSize());
		nestedSouth.setMaximumSize(new Dimension(nestedSouth.getPreferredSize().width, 50));


		contentPaneIn.add(nestedCenter, BorderLayout.CENTER);
		contentPaneIn.add(nestedNorth, BorderLayout.NORTH);
		contentPaneIn.add(nestedSouth, BorderLayout.SOUTH);

		contentPaneIn.setMaximumSize(new Dimension(nestedCenter.getMinimumSize().width + 50, nestedCenter.getMinimumSize().height + 60));
		contentPaneIn.setPreferredSize(nestedCenter.getPreferredSize());
		contentPaneIn.setMaximumSize(new Dimension(nestedCenter.getPreferredSize().width + 100, 300));

		calcOrderBtn.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double sum = 0;
				for (int i = 0; i < orderCost.length; i++) {
					sum += orderCost[i];
				}
				String sub = "Subtotal: " + DecimalFormat.getCurrencyInstance().format(sum);
				String tax = "Tax: " + DecimalFormat.getCurrencyInstance().format(sum * .085);
				String total = "Total: $" + DecimalFormat.getCurrencyInstance().format(sum + sum * .085);

				JOptionPane.showMessageDialog(((JRootPane) ((JButton) (e.getSource())).getRootPane()).getParent(), sub + "\n" + tax + "\n" + total);
			}
		});
		cancelBtn.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) ((JButton) e.getSource()).getRootPane().getParent();
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});

		setThisSize();
		pack();
		validate();
		setVisible(true);

	}

	void updateOrder() {
		orderCost[0] = pizza.getMyCost();
		orderCost[1] = toppings.getMyCost();
		orderCost[2] = dessert.getMyCost();

		if (pizza.getOrdrState() == InOutPaneAbstract.orderFlagg.YES) {
			dessert.toggleEnabler(true);
			dessert.setMyBorder();
			dessert.setBorder(dessert.getMyBorder());
		} else {
			dessert.toggleEnabler(false);
		}
	}

	/**
	 * a method for setting the minimum and preferred sizes for the panel
	 */
	void setThisSize() {
		setMinimumSize(getMinimumSize());
		setPreferredSize(getPreferredSize());
	}
}
