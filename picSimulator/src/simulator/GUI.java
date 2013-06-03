package simulator;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.TreeMap;

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
import simulator.dateiZugriff.Befehl;
import simulator.dateiZugriff.DateiEinlesen;
import simulator.memory.DataMemory;
import javax.swing.JLabel;

public class GUI extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static TreeMap<Integer, Befehl> befehlTree = new TreeMap<Integer, Befehl>();
	static TreeMap<Integer, String> textTree = new TreeMap<Integer, String>();
	final DateiEinlesen readFile;
	final InstructionManager commands;
	
	public Thread t2=null;
	public Thread t3=null;
	public Thread t4= null;
	public Thread updater=null;
	public Thread ledStatus=null;
	
	public int on; 
//	private boolean ra0;
	
	private JList<String> list;
	//private JList<String> list;
	private JTable table;
	public DefaultTableModel tableModel;
	public Object[][] intTable;
	private DataMemory mem;
	private DefaultListModel<String> listModel;
	private ImageButton play = null;
	private ImageButton pause = null;
	
	private LED ledRA1;
	private LED ledRA2;
	private LED ledRA3;
	private LED ledRA4;
	private LED ledRA5;
	private LED ledRB1;
	private LED ledRB2;
	private LED ledRB3;
	private LED ledRB4;
	private LED ledRB5;
	private LED ledRB6;
	private LED ledRB7;
	private LED ledRB8;
	
	private JLabel valueStack;
	private JLabel valueDepth;
	
	
	public GUI( DateiEinlesen readFileX, InstructionManager commandsX) {
		super("pic Simulator");
		this.readFile = readFileX;
		this.commands = commandsX;
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
		
		JPanel BoxLEDra = new JPanel();
		BoxLEDra.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LEDs - RA", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 153, 255)));
		JPanel BoxLEDrb = new JPanel();
		BoxLEDrb.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LEDs - RB", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 153, 255)));
		
		JScrollPane tableScroller = new JScrollPane();
		tableScroller.setBorder(null);
		tableScroller.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
		listModel = new DefaultListModel<String>();
		
		JScrollPane listScroller = new JScrollPane();
		
		JPanel stack = new JPanel();
		stack.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Stack", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 153, 255)));
		
		JPanel laufzeit = new JPanel();
		laufzeit.setLayout(null);
		laufzeit.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Laufzeit", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 153, 255)));
		
		JLabel nameLauzeit = new JLabel("Laufzeit in ms:");
		nameLauzeit.setBounds(12, 28, 100, 16);
		laufzeit.add(nameLauzeit);
		
		JLabel valueLaufzeit = new JLabel("0");
		valueLaufzeit.setBounds(124, 28, 45, 16);
		laufzeit.add(valueLaufzeit);
		
		
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(Menu, GroupLayout.DEFAULT_SIZE, 1182, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(listScroller, GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(BoxSchalter, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tableScroller, GroupLayout.PREFERRED_SIZE, 582, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(BoxLEDra, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
								.addComponent(BoxLEDrb, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(stack, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(laufzeit, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)))
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
							.addGap(14)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(BoxLEDra, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(BoxLEDrb, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
									.addComponent(stack, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
								.addComponent(laufzeit, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		stack.setLayout(null);
		
		JLabel nameStack = new JLabel("Top of Stack: ");
		nameStack.setBounds(12, 28, 81, 16);
		stack.add(nameStack);
		
		JLabel nameDepth = new JLabel("Depth:");
		nameDepth.setBounds(12, 69, 81, 16);
		stack.add(nameDepth);
		
		valueStack = new JLabel("0");
		valueStack.setBounds(124, 28, 45, 16);
		stack.add(valueStack);
		
		valueDepth = new JLabel("0");
		valueDepth.setBounds(124, 69, 45, 16);
		stack.add(valueDepth);
		
		list = new JList<String>();
	//	list = new JList<String>();
		listScroller.setViewportView(list);
		BoxSchalter.setLayout(null);
		
		list.setEnabled(true);
		
		final JCheckBox S1 = new JCheckBox("RA0");
		S1.setBorder(null);
		S1.setBounds(12, 20, 95, 25);
		BoxSchalter.add(S1);
//		if(S1.isSelected() == true){
//			 on = 1;
//		} else {
//			on = 0;
//		}
//		commands.setRA0(on);
		
		S1.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(S1.isSelected() == true){
					 on = 1;
				} else {
					on = 0;
				}
				commands.setRA0(on);
			}
		});
		
		final JCheckBox S2 = new JCheckBox("RA1");
		S2.setBorder(null);
		S2.setBounds(12, 47, 95, 25);
		BoxSchalter.add(S2);
//		if(S2.isSelected() == true){
//			 on = 1;
//		} else {
//			on = 0;
//		}
//		commands.setRA1(on);
			S2.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(S2.isSelected() == true){
					 on = 1;
				} else {
					on = 0;
				}
				commands.setRA1(on);
			}
		});
		
		final JCheckBox S3 = new JCheckBox("RA2");
		S3.setBorder(null);
		S3.setBounds(12, 75, 95, 25);
		BoxSchalter.add(S3);
//		if(S3.isSelected() == true){
//			 on = 1;
//		} else {
//			on = 0;
//		}
//		commands.setRA2(on);
			S3.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(S3.isSelected() == true){
					 on = 1;
				} else {
					on = 0;
				}
				commands.setRA2(on);
			}
		});
		
		
		final JCheckBox S4 = new JCheckBox("RA3");
		S4.setBorder(null);
		S4.setBounds(12, 103, 95, 25);
		BoxSchalter.add(S4);
		S4.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(S4.isSelected() == true){
					 on = 1;
				} else {
					on = 0;
				}
				commands.setRA3(on);
			}
		});
		
		final JCheckBox S5 = new JCheckBox("RA4");
		S5.setBorder(null);
		S5.setBounds(12, 132, 95, 25);
		BoxSchalter.add(S5);
			S5.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(S5.isSelected() == true){
					 on = 1;
				} else {
					on = 0;
				}
				commands.setRA4(on);
			}
		});
		
		final JCheckBox S6 = new JCheckBox("RB0");
		S6.setBorder(null);
		S6.setBounds(12, 161, 95, 25);
		BoxSchalter.add(S6);
			S6.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(S6.isSelected() == true){
					 on = 1;
				} else {
					on = 0;
				}
				commands.setRB0(on);
			}
		});
		
		final JCheckBox S7 = new JCheckBox("RB1");
		S7.setBorder(null);
		S7.setBounds(12, 188, 95, 25);
		BoxSchalter.add(S7);
			S7.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(S7.isSelected() == true){
					 on = 1;
				} else {
					on = 0;
				}
				commands.setRB1(on);
			}
		});
		
		final JCheckBox S8 = new JCheckBox("RB2");
		S8.setBorder(null);
		S8.setBounds(12, 215, 95, 25);
		BoxSchalter.add(S8);
			S8.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(S8.isSelected() == true){
					 on = 1;
				} else {
					on = 0;
				}
				commands.setRB2(on);
			}
		});
		
		final JCheckBox S9 = new JCheckBox("RB3");
		S9.setBorder(null);
		S9.setBounds(12, 243, 95, 25);
		BoxSchalter.add(S9);
			S9.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(S9.isSelected() == true){
					 on = 1;
				} else {
					on = 0;
				}
				commands.setRB3(on);
			}
		});
		
		final JCheckBox S10 = new JCheckBox("RB4");
		S10.setBorder(null);
		S10.setBounds(12, 271, 95, 25);
		BoxSchalter.add(S10);
			S10.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(S10.isSelected() == true){
					 on = 1;
				} else {
					on = 0;
				}
				commands.setRB4(on);
			}
		});
		
		final JCheckBox S11 = new JCheckBox("RB5");
		S11.setBorder(null);
		S11.setBounds(12, 300, 95, 25);
		BoxSchalter.add(S11);
			S11.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(S11.isSelected() == true){
					 on = 1;
				} else {
					on = 0;
				}
				commands.setRB5(on);
			}
		});
		
		final JCheckBox S12 = new JCheckBox("RB6");
		S12.setBorder(null);
		S12.setBounds(12, 329, 95, 25);
		BoxSchalter.add(S12);
			S12.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(S12.isSelected() == true){
					 on = 1;
				} else {
					on = 0;
				}
				commands.setRB6(on);
			}
		});
		
		
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setForeground(SystemColor.activeCaptionText);
		table.setGridColor(SystemColor.textHighlight);
		table.getTableHeader().setReorderingAllowed(false);
		
		
		intTable = fillTable();
		
		tableModel = new DefaultTableModel(
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
			};
		
		table.setModel(tableModel);
				/*new DefaultTableModel(
			intTable,
			new String[] {
				"", "00", "01", "02", "03", "04", "05", "06", "07"
			}
		) {
			/**
			 * 
			
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, true, true, true, true, true, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		 */
		
		tableScroller.setViewportView(table);
		
		//-----------------------------------------------------
		// Menu-Items
		ImageButton insert = new ImageButton("insertCode.png","insertCodeMO.png", "insertCodePressed.png", "Insert the Code");
		ImageButton backward = new ImageButton("Backward.png", "BackwardMO.png", "BackwardP.png", "Backwards");
		pause = new ImageButton("Pause.png", "PauseMO.png", "PauseP.png", "Pause");
		play = new ImageButton("Play.png", "PlayMO.png", "PlayP.png", "Play");
		ImageButton forward = new ImageButton("Forward.png", "ForwardMO.png", "ForwardP.png", "Forwards");
		ImageButton settings = new ImageButton("settings.png","settingsMO.png", "settingsPressed.png", "Settings");
		pause.setVisible(false);
		
		//event for forward
		forward.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				commands.setNext(true);
			}
		});
		
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
			    	befehlTree= readFile.getBefehlTree();
					textTree= readFile.getTextTree();
			    } else {	
			    	System.out.println( "Cancel" );
			    }
			}
		});
		
		pause.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				play.setVisible(true);
				pause.setVisible(false);

				commands.setPause(true);
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
			
				commands.setPause(false);
				
				if(t2 == null){
				t2 = new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
//						System.out.println("Thread für abarbeitung");
						commands.setPause(false);
						commands.starteAbarbeitung(befehlTree);
						
					}
					
				});
				
				t2.start();
				}
				
				if(ledStatus == null){
					ledStatus = new Thread(new Runnable() {
						
						@Override
						public void run() {
							
							while (true) {
								
								try {
									Thread.sleep(1);
//									// Example for LEDs in state ON
									ledRA1.turnState(commands.getRA0());
									ledRA2.turnState(commands.getRA1());
									ledRA3.turnState(commands.getRA2());
									ledRA4.turnState(commands.getRA3());
									ledRA5.turnState(commands.getRA4());
									ledRB1.turnState(commands.getRB0());
									ledRB2.turnState(commands.getRB1());
									ledRB3.turnState(commands.getRB2());
									ledRB4.turnState(commands.getRB3());
									ledRB5.turnState(commands.getRB4());
									ledRB6.turnState(commands.getRB5());
									ledRB7.turnState(commands.getRB6());
									ledRB8.turnState(commands.getRB7());
									if(mem.returnStackSize() > 0) {
										valueStack.setText("" + mem.returnStack());
										valueDepth.setText("" + mem.returnStackSize());
									}else{
										valueStack.setText("0");
										valueDepth.setText("0");
										
									}
								} catch (InterruptedException e) {
								}
								
								
								
							}
							
						}
					});
				ledStatus.start();
				}
				
				if(t3 == null){
//					System.out.println("Thread für zeilen");
					t3 = new Thread(new Runnable(){
						@Override
						public void run() {
							while(true){
								
								list.setSelectedIndex(commands.getAktuelleZeile()-1);
								
							}
						}
					});
					t3.start();
				}
				if(t4 == null){
//					System.out.println("Thread für zeilen");
					t4 = new Thread(new Runnable(){
						@Override
						public void run() {
							while(true){
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								mem = commands.getMemory();
								intTable = updateTable();
								
								
								tableModel.fireTableDataChanged();
								
							}
						}
					});
					t4.start();
				}
				
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
		// LED-Arrays
		ledRA1 = new LED();
		ledRA2 = new LED();
		ledRA3 = new LED();
		ledRA4 = new LED();
		ledRA5 = new LED();
		BoxLEDra.add(ledRA1);
		BoxLEDra.add(ledRA2);
		BoxLEDra.add(ledRA3);
		BoxLEDra.add(ledRA4);
		BoxLEDra.add(ledRA5);
		ledRB1 = new LED();
		ledRB2 = new LED();
		ledRB3 = new LED();
		ledRB4 = new LED();
		ledRB5 = new LED();
		ledRB6 = new LED();
		ledRB7 = new LED();
		ledRB8 = new LED();
		BoxLEDrb.add(ledRB1);
		BoxLEDrb.add(ledRB2);
		BoxLEDrb.add(ledRB3);
		BoxLEDrb.add(ledRB4);
		BoxLEDrb.add(ledRB5);
		BoxLEDrb.add(ledRB6);
		BoxLEDrb.add(ledRB7);
		BoxLEDrb.add(ledRB8);
		
		
		
		// Layout for the hole panel
		getContentPane().setLayout(groupLayout);
				
		// GUI Updater
		if(updater == null){
//			System.out.println("Updater thread von felix");
		updater = new Thread();
		updater.start();
		}
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


public Object[][] updateTable(){
		
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
					tableModel.setValueAt(mem.readFileValue(address), row, column);
				address++;
				}
			}
			
		}
		return intTable;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.validate();
		this.repaint();
	}
}
