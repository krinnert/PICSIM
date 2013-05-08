package simulator;
import java.awt.Color;
import java.io.*;
import java.util.TreeMap;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import javax.swing.JFrame;

import simulator.InstructionManager.InstructionManager;
import simulator.dateiZugriff.Befehl;
import simulator.dateiZugriff.DateiEinlesen;




public class Main {
	
	static TreeMap<Integer, Befehl> befehlTree = new TreeMap<Integer, Befehl>();
	static TreeMap<Integer, String> textTree = new TreeMap<Integer, String>();

	public static void main(String[] args){

		
		InstructionManager befehl= new InstructionManager();
		
		DateiEinlesen datei= new DateiEinlesen();
		datei.setDatei("A1.LST");
		datei.berechneDatei();
		befehlTree= datei.getBefehlTree();
		textTree= datei.getTextTree();
		
		GUI gui= new GUI(datei,befehl);
		
//		System.out.println(datei.getTextString());
		
		befehl.starteAbarbeitung(befehlTree);
		System.out.println();
	
	}


	
}