package simulator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private String path = "./resources/";
	private int alignment = 0;
	
	public StartScreen() {
		// Get the display-size
		Dimension displaySize = getToolkit().getScreenSize();	
		
		// Load Image
		JPanel logo = new JPanel(new BorderLayout());
		JLabel circles = new JLabel(new ImageIcon(getToolkit().getImage(path + "circles.png").getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		JLabel img = new JLabel(new ImageIcon(getToolkit().getImage(path + "splashScreen.png").getScaledInstance(displaySize.width+alignment, displaySize.height+alignment, Image.SCALE_SMOOTH)));
		logo.add(img);
		add(logo);
	
		// Layout settings
		setUndecorated(true);
		setVisible(true);
		setAlwaysOnTop(true);
		this.pack();
	}
	
}
