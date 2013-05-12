package simulator;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadScreen extends JPanel {

	public JPanel loadScreen() {
		JPanel screen = new JPanel();
		JLabel logo = new JLabel(new ImageIcon(getToolkit().getImage("./resources/Play").getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
		
		setVisible(true);
		setBackground(SystemColor.textHighlight);
		setLayout(new BorderLayout());
		screen.add(logo, BorderLayout.CENTER);
		
		return screen;
	}
}
