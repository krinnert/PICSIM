package simulator;
import java.util.TreeMap;

import simulator.InstructionManager.InstructionManager;
import simulator.dateiZugriff.Befehl;
import simulator.dateiZugriff.DateiEinlesen;




public class Main {
	
	static TreeMap<Integer, Befehl> befehlTree = new TreeMap<Integer, Befehl>();
	static TreeMap<Integer, String> textTree = new TreeMap<Integer, String>();

	public static void main(String[] args) throws InterruptedException{
		
		StartScreen start = new StartScreen();
		InstructionManager befehl= new InstructionManager();
		DateiEinlesen datei= new DateiEinlesen();
		datei.berechneDatei("A1.LST");
		befehlTree= datei.getBefehlTree();
		textTree= datei.getTextTree();
		
		GUI gui= new GUI(datei,befehl);
		Thread.sleep(1000);
		start.dispose();

		
		befehl.starteAbarbeitung(befehlTree);
		
		System.out.println();
		
		
		
	}




	
}
