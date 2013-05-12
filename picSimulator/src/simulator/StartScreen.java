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
		Dimension dim = getToolkit().getScreenSize();
		setSize(1920/2, 1080/2);
				
		JPanel logo = new JPanel();
		JLabel img = new JLabel(new ImageIcon(getToolkit().getImage("./resources/splashScreen.png").getScaledInstance(1920/2, 1080/2, Image.SCALE_SMOOTH)));
		logo.add(img);
		add(logo);
		
		setUndecorated(true);
		setVisible(true);
		//setLocationRelativeTo(getParent());
		// Releatove to the display
		setLocation(dim.width/2 - this.getWidth()/2, dim.height/2 - this.getHeight()/2);
		setAlwaysOnTop(true);
		this.pack();
	}
	
}
