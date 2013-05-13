package simulator;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class StartScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private String path = "./resources/";
	
	public StartScreen() {
		// Get the display-size
		Dimension displaySize = getToolkit().getScreenSize();
		//JLayeredPane layer = new JLayeredPane();		
		
		// Load Image
		JPanel logo = new JPanel();
		//JLabel circles = new JLabel(new ImageIcon(getToolkit().getImage(path + "circles.png").getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		JLabel img = new JLabel(new ImageIcon(getToolkit().getImage(path + "splashScreen.png").getScaledInstance(displaySize.width+2, displaySize.height+2, Image.SCALE_SMOOTH)));
		logo.add(img);
		add(logo);
		
		
		// Layout settings
		setUndecorated(true);
		setVisible(true);
		setLocation(-6,-6);
		setAlwaysOnTop(true);
		this.pack();
	}
	
}
