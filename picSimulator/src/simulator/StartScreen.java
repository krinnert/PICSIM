package simulator;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartScreen extends JFrame {

	public StartScreen() {
		// Get the display-size
		Dimension displaySize = getToolkit().getScreenSize();
				
		// Load Image
		JPanel logo = new JPanel();
		JLabel img = new JLabel(new ImageIcon(getToolkit().getImage("./resources/splashScreen.png").getScaledInstance(displaySize.width+2, displaySize.height+2, Image.SCALE_SMOOTH)));
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
