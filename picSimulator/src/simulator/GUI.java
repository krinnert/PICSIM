package simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.SplashScreen;
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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
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
	private JList<String> list;
	private JTable table;
	private DataMemory mem;
	private DefaultListModel<String> listModel;
	private ImageButton play = null;
	private ImageButton pause = null;
	
	public GUI(final DateiEinlesen readFile, InstructionManager commands) {
		super("pic Simulator");
		mem = commands.getMemory();
		Dimension displaySize = getToolkit().getScreenSize();
		
		setSize(1200, 800);
		setLocation(displaySize.width/2 - getWidth()/2, displaySize.height/2 - getHeight()/2);
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
		
		JScrollPane tableScroller = new JScrollPane();
		tableScroller.setBorder(null);
		tableScroller.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
		listModel = new DefaultListModel<String>();
		
		JScrollPane listScroller = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(Menu, GroupLayout.DEFAULT_SIZE, 1182, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(listScroller, GroupLayout.PREFERRED_SIZE, 437, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(BoxSchalter, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tableScroller, GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE))
						.addComponent(BoxLED, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(Menu, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(listScroller, GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(BoxSchalter, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tableScroller, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(BoxLED, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		
		list = new JList<String>();
		listScroller.setViewportView(list);
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
		
		JCheckBox S7 = new JCheckBox("Schalter 7");
		S7.setBorder(null);
		S7.setBounds(12, 188, 95, 25);
		BoxSchalter.add(S7);
		
		JCheckBox S8 = new JCheckBox("Schalter 8");
		S8.setBorder(null);
		S8.setBounds(12, 215, 95, 25);
		BoxSchalter.add(S8);
		
		JCheckBox S9 = new JCheckBox("Schalter 9");
		S9.setBorder(null);
		S9.setBounds(12, 243, 95, 25);
		BoxSchalter.add(S9);
		
		JCheckBox S10 = new JCheckBox("Schalter 10");
		S10.setBorder(null);
		S10.setBounds(12, 271, 95, 25);
		BoxSchalter.add(S10);
		
		JCheckBox S11 = new JCheckBox("Schalter 11");
		S11.setBorder(null);
		S11.setBounds(12, 300, 95, 25);
		BoxSchalter.add(S11);
		
		JCheckBox S12 = new JCheckBox("Schalter 12");
		S12.setBorder(null);
		S12.setBounds(12, 329, 95, 25);
		BoxSchalter.add(S12);
		
		
		
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
		
		
		tableScroller.setViewportView(table);
		
		//-----------------------------------------------------
		// Menu-Items
		ImageButton insert = new ImageButton("insertCode.png","insertCodeMO.png", "insertCodePressed.png", "Insert the Code");
		ImageButton backward = new ImageButton("Backward.png", "BackwardMO.png", "BackwardP.png", "Backwards");
		pause = new ImageButton("Pause.png", "PauseMO.png", "PauseP.png", "Pause");
		play = new ImageButton("Play.png", "PlayMO.png", "PlayP.png", "Play");
		ImageButton forward = new ImageButton("Forward.png", "ForwardMO.png", "ForwardP.png", "Forwards");
		ImageButton settings = new ImageButton("settings.png","settingsMO.png", "settingsPressed.png", "Settings");
		
		// Events of the Menu-Items
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
				// Just LST-Files are permitted
				chooser.setFileFilter( new FileFilter(){ 
				      @Override public boolean accept( File file ) { 
				    	  return file.isDirectory() || file.getName().toLowerCase().endsWith( ".lst" ); 
				      } 
				      @Override public String getDescription() { 
				    	  return ".lst"; 
				      } 
				 });
				// Read the file and insert the code in the List
				int state = chooser.showOpenDialog( null ); 
			    if ( state == JFileChooser.APPROVE_OPTION ) { 
			    	String filePath = chooser.getSelectedFile().getAbsolutePath();
			    	readFile.berechneDatei(filePath);
			    	list.setModel(listModel);
			    	readFile.insert(listModel);
			    } else {	
			    	System.out.println( "Cancel" );
			    }
			}
		});
		
		pause.setVisible(false);
		pause.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				play.setVisible(true);
				pause.setVisible(false);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		
		play.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				play.setVisible(false);
				pause.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		
		// Layout of the Menu-Items
		Menu.setLayout(new BorderLayout(30,30));
		JPanel west = new JPanel(new GridLayout(1,4,10,0));
		JPanel toggle = new JPanel(new BorderLayout());
		pause.setLocation(0, 12);
		toggle.setBackground(null);
		toggle.add(pause, BorderLayout.CENTER);
		toggle.add(play);
		west.setBackground(null);
		west.add(insert);
		west.add(backward);
		west.add(toggle);
		west.add(forward);
		Menu.add(west, BorderLayout.WEST);
		west.setLocation(30, 30);
		Menu.add(settings, BorderLayout.EAST);
		Menu.setBorder(new EmptyBorder(0, 20, 0, 20));
		
		
		//-----------------------------------------------------
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
