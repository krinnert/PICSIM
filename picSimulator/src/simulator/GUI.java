package simulator;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import simulator.InstructionManager.InstructionManager;
import simulator.dateiZugriff.DateiEinlesen;
import simulator.memory.DataMemory;

public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DataMemory mem;
	
	public GUI(DateiEinlesen readFile, InstructionManager commands) {
		super("pic Simulator");
		mem = commands.getMemory();
		
		
		setSize(1200, 800);
		setVisible(true);
		addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
			}
			@Override
			public void componentResized(ComponentEvent e) {
			}
			@Override
			public void componentMoved(ComponentEvent e) {
			}
			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel Menu = new JPanel();
		Menu.setBackground(SystemColor.textHighlight);
		
		JTextPane textPane = new JTextPane();

		
		JPanel BoxSchalter = new JPanel();
		BoxSchalter.setBorder(new TitledBorder(null, "Schalter", TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.textHighlight));
		
		JPanel BoxLED = new JPanel();
		BoxLED.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LEDs", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 153, 255)));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(BoxSchalter, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(BoxLED, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 565, GroupLayout.PREFERRED_SIZE))
					.addGap(20))
				.addComponent(Menu, GroupLayout.DEFAULT_SIZE, 1182, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(Menu, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(BoxSchalter, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
								.addComponent(BoxLED, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
						.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE))
					.addGap(101))
		);
		
		
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setForeground(SystemColor.activeCaptionText);
		table.setGridColor(SystemColor.textHighlight);
		table.getTableHeader().setReorderingAllowed(false);
		
		
		Object[][] intTable = fillTable();
		
		
		table.setModel(new DefaultTableModel(
			intTable,
			new String[] {
				"", "00", "01", "02", "03", "04", "05", "06", "07"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, true, true, true, true, true, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		
		scrollPane.setViewportView(table);
		Menu.setLayout(null);
		
		ImageButton insert = new ImageButton("insertCode.png","insertCodeMO.png", "insertCodePressed.png", "Insert the Code");
		insert.setLocation(12, 13);
		insert.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				// You can select just LST-Files
				chooser.setFileFilter( new FileFilter(){ 
				      @Override public boolean accept( File file ) { 
				    	  return file.isDirectory() || file.getName().toLowerCase().endsWith( ".lst" ); 
				      } 
				      @Override public String getDescription() { 
				    	  return ".lst"; 
				      } 
				 });
				int state = chooser.showOpenDialog( null ); 
			    if ( state == JFileChooser.APPROVE_OPTION ) { 
//			    	readFile.berechneDatei(chooser.getSelectedFile());
			    	File file = chooser.getSelectedFile();
			    	String name = chooser.getSelectedFile().getName();
			    } else {	
			    	System.out.println( "Cancel" );
			    }
			}
		});
		
		Menu.add(insert);
		ImageButton settings = new ImageButton("settings.png","settingsMO.png", "settingsPressed.png", "Settings");
		settings.setLocation(100, 13);
		Menu.add(settings);
		
		// LED-Array
		LED led1 = new LED();
		LED led2 = new LED();
		LED led3 = new LED();
		LED led4 = new LED();
		LED led5 = new LED();
		LED led6 = new LED();
		LED led7 = new LED();
		LED led8 = new LED();
		LED led9 = new LED();
		BoxLED.add(led1);
		BoxLED.add(led2);
		BoxLED.add(led3);
		BoxLED.add(led4);
		BoxLED.add(led5);
		BoxLED.add(led6);
		BoxLED.add(led7);
		BoxLED.add(led8);
		BoxLED.add(led9);
		
		// Example for LEDs in state ON
		led2.turnON();
		led8.turnON();
		led9.turnON();
		
		
		GridBagLayout gbl_BoxSchalter = new GridBagLayout();
		gbl_BoxSchalter.columnWidths = new int[]{89, 0};
		gbl_BoxSchalter.rowHeights = new int[] {0, 0, 0, 0, 0, 0};
		gbl_BoxSchalter.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_BoxSchalter.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		BoxSchalter.setLayout(gbl_BoxSchalter);
		
		JCheckBox S1 = new JCheckBox("Schalter 1");
		GridBagConstraints gbc_S1 = new GridBagConstraints();
		gbc_S1.anchor = GridBagConstraints.NORTHWEST;
		gbc_S1.insets = new Insets(0, 0, 5, 0);
		gbc_S1.gridx = 0;
		gbc_S1.gridy = 0;
		BoxSchalter.add(S1, gbc_S1);
		
		JCheckBox S2 = new JCheckBox("Schalter 2");
		GridBagConstraints gbc_S2 = new GridBagConstraints();
		gbc_S2.anchor = GridBagConstraints.NORTHWEST;
		gbc_S2.insets = new Insets(0, 0, 5, 0);
		gbc_S2.gridx = 0;
		gbc_S2.gridy = 1;
		BoxSchalter.add(S2, gbc_S2);
		
		JCheckBox S3 = new JCheckBox("Schalter 3");
		GridBagConstraints gbc_S3 = new GridBagConstraints();
		gbc_S3.anchor = GridBagConstraints.NORTHWEST;
		gbc_S3.insets = new Insets(0, 0, 5, 0);
		gbc_S3.gridx = 0;
		gbc_S3.gridy = 2;
		BoxSchalter.add(S3, gbc_S3);
		
		JCheckBox S4 = new JCheckBox("Schalter 4");
		GridBagConstraints gbc_S4 = new GridBagConstraints();
		gbc_S4.insets = new Insets(0, 0, 5, 0);
		gbc_S4.anchor = GridBagConstraints.NORTHWEST;
		gbc_S4.gridx = 0;
		gbc_S4.gridy = 3;
		BoxSchalter.add(S4, gbc_S4);
		
		JCheckBox S5 = new JCheckBox("Schalter 5");
		GridBagConstraints gbc_S5 = new GridBagConstraints();
		gbc_S5.insets = new Insets(0, 0, 5, 0);
		gbc_S5.anchor = GridBagConstraints.NORTHWEST;
		gbc_S5.gridx = 0;
		gbc_S5.gridy = 4;
		BoxSchalter.add(S5, gbc_S5);
		getContentPane().setLayout(groupLayout);

		
		
		this.pack();
	}
	
	public Object[][] fillTable(){
		
		String[] rowNames = {"00","08","10","18","20","28","30","38","40","48","50","58","60","68","70","78","80","88","90","98","A0","A8","B0","B8","C0","C8","D0","D8","E0","E8","F0","F8","FF"};
		Object[][] intTable = new Object[32][9];
		int address = 0;
		int rowTag = 0x0;
		for(int row=0;row <=31;row++){
			
			for(int column=0;column<=8;column++){
				if(column==0){
					intTable[row][column]=rowNames[rowTag];
					rowTag++;
				}
				else{
				intTable[row][column]=mem.readFileValue(address);
				address++;
				}
			}
			
		}
		return intTable;
	}
}
