package simulator;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.SystemColor;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import simulator.InstructionManager.InstructionManager;
import simulator.dateiZugriff.DateiEinlesen;
import simulator.memory.DataMemory;
import javax.swing.LayoutStyle.ComponentPlacement;

public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DataMemory mem;
	private DefaultListModel<String> listModel;
	private JList<String> list;
	
	public GUI(final DateiEinlesen readFile, InstructionManager commands) {
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

		
		JPanel BoxSchalter = new JPanel();
		BoxSchalter.setBorder(new TitledBorder(null, "Schalter", TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.textHighlight));
		
		JPanel BoxLED = new JPanel();
		BoxLED.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LEDs", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 153, 255)));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
		listModel = new DefaultListModel<String>();
		list = new JList<String>();
		
		JPanel boxControlling = new JPanel();
		boxControlling.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Controlling", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 153, 255)));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(Menu, GroupLayout.DEFAULT_SIZE, 1182, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(list, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
						.addComponent(boxControlling, GroupLayout.PREFERRED_SIZE, 567, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(BoxSchalter, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(BoxLED, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 565, GroupLayout.PREFERRED_SIZE))
					.addGap(20))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(Menu, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(BoxSchalter, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
								.addComponent(BoxLED, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
							.addGap(101))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 560, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(98)
					.addComponent(boxControlling, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(588, Short.MAX_VALUE))
		);
		BoxSchalter.setLayout(null);
		
		JCheckBox S1 = new JCheckBox("Schalter 1");
		S1.setBorder(null);
		S1.setBounds(12, 20, 95, 25);
		BoxSchalter.add(S1);
		
		JCheckBox S2 = new JCheckBox("Schalter 2");
		S2.setBorder(null);
		S2.setBounds(12, 47, 95, 25);
		BoxSchalter.add(S2);
		
		JCheckBox S3 = new JCheckBox("Schalter 3");
		S3.setBorder(null);
		S3.setBounds(12, 75, 95, 25);
		BoxSchalter.add(S3);
		
		JCheckBox S4 = new JCheckBox("Schalter 4");
		S4.setBorder(null);
		S4.setBounds(12, 103, 95, 25);
		BoxSchalter.add(S4);
		
		JCheckBox S5 = new JCheckBox("Schalter 5");
		S5.setBorder(null);
		S5.setBounds(12, 132, 95, 25);
		BoxSchalter.add(S5);
		
		JCheckBox S6 = new JCheckBox("Schalter 6");
		S6.setBorder(null);
		S6.setBounds(12, 161, 95, 25);
		BoxSchalter.add(S6);
		
		
		
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
		
		ImageButton insert = new ImageButton(60,"insertCode.png","insertCodeMO.png", "insertCodePressed.png", "Insert the Code");
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
			    	File file = chooser.getSelectedFile();
			    	String filePath = file.getAbsolutePath();
			    	
			    	list.setModel(listModel);
			    	//listModel.addElement(file.toString());
			    	String a = "hallo";
			    	for(int i=0; i<=30; i++) {
			    		listModel.addElement(a);
			    	}
			    	
			    	
			    } else {	
			    	System.out.println( "Cancel" );
			    }
			}
		});
		
		Menu.add(insert);
		ImageButton settings = new ImageButton(60, "settings.png","settingsMO.png", "settingsPressed.png", "Settings");
		settings.setLocation(100, 13);
		Menu.add(settings);
		
		// Controlling
		ImageButton backward = new ImageButton(30,"Backward.png", "BackwardMO.png", "BackwardP.png", "Backwards");
		boxControlling.add(backward);
		ImageButton play = new ImageButton(30,"Play.png", "PlayMO.png", "PlayP.png", "Play");
		ImageButton pause = new ImageButton(30,"Pause.png", "PauseMO.png", "PauseP.png", "Pause");
		boxControlling.add(play);
		ImageButton forward = new ImageButton(30,"Forward.png", "ForwardMO.png", "ForwardP.png", "Forwards");
		boxControlling.add(forward);
		
		
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
