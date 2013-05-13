package simulator;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LED extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Scaling the Image (width & height)
	private int width = 16;
	private int height = 16;
	private ImageIcon LEDoff = new ImageIcon(getToolkit().getImage("./resources/LEDoff.png").getScaledInstance(width, height, Image.SCALE_SMOOTH));
	private ImageIcon LEDon = new ImageIcon(getToolkit().getImage("./resources/LEDon.png").getScaledInstance(width, height, Image.SCALE_SMOOTH));
	private int position = 0;
	
	public LED() {
		int position = this.position;
		// Sets the Default-Icon ==> off
		this.setIcon(LEDoff);

		// Increase the ID number for the next LED 
		position++;
	}
	
	public void turnState(boolean state) {
		if( state == true ) {
			this.setIcon(LEDon);
		} else if( state == false ) {
			this.setIcon(LEDoff);
		} else {}
	}
	
}
