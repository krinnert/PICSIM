package simulator;

import simulator.InstructionManager.InstructionManager;
import simulator.dateiZugriff.DateiEinlesen;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		StartScreen start = new StartScreen();

		InstructionManager befehl = new InstructionManager();
		DateiEinlesen datei = new DateiEinlesen();

		new GUI(datei, befehl);
		
		Thread.sleep(1000);
		start.dispose();
	}

}
