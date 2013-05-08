package simulator.dateiZugriff;
import java.io.*;
import java.util.Map.Entry;
import java.util.TreeMap;

public class DateiEinlesen {
	
	int zeilenLaenge; //die l‰nger einer Zeile
	int zeilenNummer=0; //zeilennummer
	String meineTextZeile=""; // sp‰tere ausgabe
	String dateiTextZeile="";//text der datei
	String dateiName; //der name der datei
	TreeMap<Integer, Befehl> befehlTree = new TreeMap<Integer, Befehl>();
	TreeMap<Integer, String> textTree = new TreeMap<Integer, String>();
	
	public void setDatei(String name){ // legt fest wo die datei liegt
		dateiName= name;
	}
	public void berechneDatei(){
		FileReader meinFile= null;
		BufferedReader meinLeseStream= null;
		
		
		try{
			meinFile= new FileReader(dateiName); // legt objekt von der datei an -- welche datei geladen werden soll
			meinLeseStream= new BufferedReader(meinFile); // legt objekt von datei an -- zum lesen
			dateiTextZeile= meinLeseStream.readLine(); // zeile in der man gerade ist
			while (dateiTextZeile != null) { // l‰uft solange die zeile in der man gerade lieﬂt nicht null ist-- dukument fertig
				zeilenNummer++; // z‰hlt zeilennummer hoch
//				zeilenLaenge= dateiTextZeile.length(); // zeigt die l‰nger der zeile in der man gerade ist
//				meineTextZeile= meineTextZeile+zeilenNummer+ "\t| "; // zeile die ausgegeben wird (nummer)
//			 	meineTextZeile= meineTextZeile+zeilenLaenge+ "\t| "; // zeile die ausgegeben wird (l‰nge)
//				meineTextZeile= meineTextZeile+dateiTextZeile; // zeile die ausgegeben wird (text)
//				System.out.println(meineTextZeile);// gibt die eig textzeile aus mit alles was man haben will
			
				//w¸rde nur text ohne befehle speichern
				
				textTree.put(zeilenNummer, dateiTextZeile.substring(27));
				
				
				//speichert: Identifier: speicherst. und in Befehlsklasse: befehl und Zeilennr
				if (dateiTextZeile.charAt(0) != ' ') {
				befehlTree.put(Integer.parseInt(dateiTextZeile.substring(0, 4), 16), new Befehl(Integer.parseInt(dateiTextZeile.substring(5, 9), 16), zeilenNummer));
				}
				
				meineTextZeile=""; // text zeile wird wieder geleert
				dateiTextZeile= meinLeseStream.readLine();//die textzeile der datei lieﬂt die n‰chste zeile
				
			}
			
			//AUSGABE
//			for (Integer elem : befehlTree.keySet())
//				System.out.println(elem + " - " + befehlTree.get(elem).getCode()+ " - " +befehlTree.get(elem).getProgrammzeile());
//
//			for (Integer elem : textTree.keySet())
//				System.out.println(elem + " - " + textTree.get(elem));


			
			meinLeseStream.close(); //schlieﬂt den lesetream
			meinFile.close(); //schlieﬂt die datei
//			System.out.println("################### ende der datei #######################");
		}catch(IOException e){
			System.out.println("Fehler beim Dateizugriff" +e);
		}
		
	}
	public TreeMap<Integer, Befehl> getBefehlTree() {
		return befehlTree;
	}
	public void setBefehlTree(TreeMap<Integer, Befehl> befehlTree) {
		this.befehlTree = befehlTree;
	}
	public TreeMap<Integer, String> getTextTree() {
		return textTree;
	}
	public void setTextTree(TreeMap<Integer, String> textTree) {
		this.textTree = textTree;
	}

	public String getTextString(){
		TreeMap<Integer,String> textTree = this.getTextTree();
		String text ="";
		for(Entry<Integer, String> entry : textTree.entrySet()){
		text = text + entry.getValue() + "\n";
		}

		return text;

		}

}
