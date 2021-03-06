package simulator;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageButton extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension dim = new Dimension(60, 60);
	private String path = "./resources/";

	/*
	 * Customized Icon with new functions
	 */
	public ImageButton(String imagePath, String imageMouseOverPath, String imagePressedPath, String tooltip) {
	
		ImageIcon iconNormal = new ImageIcon(getToolkit().getImage(path + imagePath).getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH));
		ImageIcon iconMouseOver = new ImageIcon(getToolkit().getImage(path + imageMouseOverPath).getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH));
		ImageIcon iconPressed = new ImageIcon(getToolkit().getImage(path + imagePressedPath).getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH));
		
		// Button settings
		setPreferredSize(dim);
		setSize(dim);
		setBorder(null);
		setContentAreaFilled(false);
		setOpaque(false);
		setFocusPainted(false);
		setVisible(true);
		
		if( imageMouseOverPath != null ) {
			setIcon(iconNormal);
		} else {
			setText("No Image");
		}
		if( imageMouseOverPath != null ) {
			setRolloverIcon(iconMouseOver);
		}
		if( imagePressedPath != null ) {
			setSelectedIcon(iconPressed);
			setPressedIcon(iconPressed);
		}
		if( tooltip != null ) {
			setToolTipText(tooltip);
		}
	} 

}
