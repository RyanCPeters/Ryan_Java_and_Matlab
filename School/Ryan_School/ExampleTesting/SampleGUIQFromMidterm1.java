import javax.swing.*;
import java.awt.*;

/**
 * @author Ryan Peters
 * @date 1/25/2017
 */
public class SampleGUIQFromMidterm1 extends JFrame {
	private final int OVAL_X = 25;
	private final int OVAL_Y = 50;
	private final int OVAL_DIM = 40;
	private final int OVAL_GAP = 80;
	private final int WIN_WID = 200;
	private final int WIN_HIGH = 220;
	private final Color BLU = Color.blue;
	private final Color GRA = Color.gray;
	private final Color BLK = Color.black;
	private MyPane panel;


	public SampleGUIQFromMidterm1() {
		super();
		this.setTitle("Midterm Draw!");
		panel = new MyPane();
		setContentPane(panel);
		setSize(WIN_WID, WIN_HIGH);

		validate();
		setVisible(true);
		repaint();

	}


	private class MyPane extends JPanel {

		MyPane() {
			super();
		}

		/**
		 * Paints each of the components in this container.
		 *
		 * @param g the graphics context.
		 * @see Component#paint
		 * @see Component#paintAll
		 */
		@Override
		public void paintComponents(Graphics g) {
			super.paintComponents(g);
			g.setColor(BLU);
			g.fillOval(OVAL_X, OVAL_Y, OVAL_DIM, OVAL_DIM);
			g.fillOval(OVAL_X + OVAL_GAP, OVAL_Y, OVAL_DIM, OVAL_DIM);
			g.setColor(GRA);
			g.fillRect(OVAL_X + OVAL_DIM / 2, OVAL_Y + OVAL_DIM / 2, OVAL_GAP, OVAL_GAP);
			g.setColor(BLK);
			g.drawLine(OVAL_X + OVAL_DIM / 2, OVAL_Y + OVAL_GAP / 2 + OVAL_DIM / 2, OVAL_X + OVAL_DIM / 2 + OVAL_GAP, OVAL_Y + OVAL_GAP / 2 + OVAL_DIM / 2);
			update(g);

		}

	}
}
