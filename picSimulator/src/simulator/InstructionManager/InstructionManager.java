package simulator.InstructionManager;

import java.util.BitSet;
import java.util.TreeMap;

import simulator.dateiZugriff.Befehl;
import simulator.memory.Akku;
import simulator.memory.DataMemory;
//break, stack laufzeit
public class InstructionManager {
	static TreeMap<Integer, Befehl> befehlTree = new TreeMap<Integer, Befehl>();
	private DataMemory mem = new DataMemory();
	private Akku akku = new Akku(mem);

	private int opCode;
	private int speicherSt;
	private BitSet opCodeBitSet = new BitSet(14);
	private DataMemory data = new DataMemory();
	public int i = 0;
	private boolean pause=true;
	private boolean next=false;
	private int laufzeit=0;
	
	private int[] RAio = new int[5];
	private int[] RBio = new int[8];
	
	private boolean ra0;
	private boolean ra1;
	private boolean ra2;
	private boolean ra3;
	private boolean ra4;
	private boolean rb0;
	private boolean rb1;
	private boolean rb2;
	private boolean rb3;
	private boolean rb4;
	private boolean rb5;
	private boolean rb6;
	private boolean rb7;


	// List mit allen befehlen -> ruft ausführen aus
	public void starteAbarbeitung(TreeMap befehlBaum) {
		befehlTree = befehlBaum;
		
		//Init für RA/B-IO-Arrays - WO
		for (int b = 0; b < RAio.length; b++) {
			RAio[b]=1;
		}
		for (int b = 0; b < RBio.length; b++) {
			RBio[b]=1;
		}
		
		
		//Starte eigentliches Programm
		for (i = 0; i < befehlTree.size(); i++) {
			
			while(getPause()==true){
				
				//hier ein sleep weil sonst true nicht erkannt wird weil takt zu hoch ist
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(next==true){
					next=false;
					break;
				}
				
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			befehlTree.get(i);
			findeBefehl(befehlTree.get(i).getCode());
			
//			System.out.println("******Statusregisterwert:  " + mem.readFileValue(3));
//			System.out.println("******zeile:" + i +"  F Register: "+mem.readFileValue(0x0f));
		}

	}
	public boolean getNext() {
		return next;
	}


	public void setNext(boolean next) {
		this.next = next;
	}
	
	
	//Getter für special register
	public int getAkku(){
		return akku.getAkku();
	}
	public int getC(){
		return mem.readBitValue(0x83, 0);
	}
	public int getDC(){
		return mem.readBitValue(0x83, 1);
	}
	public int getZ(){
		return mem.readBitValue(0x83, 2);
	}
	
	public int getAktuelleZeile(){
		//changesv
		return befehlTree.get(i).getProgrammzeile(); 
	}
	
	public int a = 0;

	//getter und setter für ein/ausgänge
	public boolean getRA0(){
		if(mem.readBitValue(5, 0)==1){
			ra0=true;
		}
		if(mem.readBitValue(5, 0)==0){
			ra0=false;
		}
		if (RAio[0]==1) {
			return false;	
		} else {
			return ra0;
		}
		
	}
	public boolean getRA1(){
		if(mem.readBitValue(5, 1)==1){
			ra1=true;
		}
		if(mem.readBitValue(5, 1)==0){
			ra1=false;
		}
		if (RAio[1]==1) {
			return false;	
		} else {
			return ra1;
		}
		
	}
	public boolean getRA2(){
		if(mem.readBitValue(5, 2)==1){
			ra2=true;
		}
		if(mem.readBitValue(5, 2)==0){
			ra2=false;
		}
		if (RAio[2]==1) {
			return false;	
		} else {
			return ra2;
		}
	}
	public boolean getRA3(){
		if(mem.readBitValue(5, 3)==1){
			ra3=true;
		}
		if(mem.readBitValue(5, 3)==0){
			ra3=false;
		}
		if (RAio[3]==1) {
			return false;	
		} else {
			return ra3;
		}
	}
	public boolean getRA4(){
		if(mem.readBitValue(5, 4)==1){
			ra4=true;
		}
		if(mem.readBitValue(5, 4)==0){
			ra4=false;
		}
		if (RAio[4]==1) {
			return false;	
		} else {
			return ra4;
		}
	}
	public boolean getRB0(){
		if(mem.readBitValue(6, 0)==1){
			rb0=true;
		}
		if(mem.readBitValue(6, 0)==0){
			rb0=false;
		}
		if (RBio[0]==1) {
			return false;	
		} else {
			return rb0;
		}
	}
	public boolean getRB1(){
		if(mem.readBitValue(6, 1)==1){
			rb1=true;
		}
		if(mem.readBitValue(6, 1)==0){
			rb1=false;
		}
		if (RBio[1]==1) {
			return false;	
		} else {
			return rb1;
		}
	}
	public boolean getRB2(){
		if(mem.readBitValue(6, 2)==1){
			rb2=true;
		}
		if(mem.readBitValue(6, 2)==0){
			rb2=false;
		}
		if (RBio[2]==1) {
			return false;	
		} else {
			return rb2;
		}
	}
	public boolean getRB3(){
		if(mem.readBitValue(6, 3)==1){
			rb3=true;
		}
		if(mem.readBitValue(6, 3)==0){
			rb3=false;
		}
		if (RBio[3]==1) {
			return false;	
		} else {
			return rb3;
		}
	}
	public boolean getRB4(){
		if(mem.readBitValue(6, 4)==1){
			rb4=true;
		}
		if(mem.readBitValue(6, 4)==4){
			rb4=false;
		}
		if (RBio[4]==1) {
			return false;	
		} else {
			return rb4;
		}
	}
	public boolean getRB5(){
		if(mem.readBitValue(6, 5)==1){
			rb5=true;
		}
		if(mem.readBitValue(6, 5)==0){
			rb5=false;
		}
		if (RBio[5]==1) {
			return false;	
		} else {
			return rb5;
		}
	}
	public boolean getRB6(){
		if(mem.readBitValue(6, 6)==1){
			rb6=true;
		}
		if(mem.readBitValue(6, 6)==0){
			rb6=false;
		}
		if (RBio[6]==1) {
			return false;	
		} else {
			return rb6;
		}
	}
	public boolean getRB7(){
		if(mem.readBitValue(6, 7)==1){
			rb7=true;
		}
		if(mem.readBitValue(6, 7)==0){
			rb7=false;
		}
		if (RBio[7]==1) {
			return false;	
		} else {
			return rb7;
		}
	}
	public void setRA0(int value){
		mem.writeBitValue(5, 0, value);
	}
	public void setRA1(int value){
		mem.writeBitValue(5, 1, value);
	}
	public void setRA2(int value){
		mem.writeBitValue(5, 2, value);
	}
	public void setRA3(int value){
		mem.writeBitValue(5, 3, value);
	}
	public void setRA4(int value){
		mem.writeBitValue(5, 4, value);
	}
	public void setRB0(int value){
		mem.writeBitValue(6, 0, value);
	}
	public void setRB1(int value){
		mem.writeBitValue(6, 1, value);
	}
	public void setRB2(int value){
		mem.writeBitValue(6, 2, value);
	}
	public void setRB3(int value){
		mem.writeBitValue(6, 3, value);
	}
	public void setRB4(int value){
		mem.writeBitValue(6, 4, value);
	}
	public void setRB5(int value){
		mem.writeBitValue(6, 5, value);
	}
	public void setRB6(int value){
		mem.writeBitValue(6, 6, value);
	}
	public void setRB7(int value){
		mem.writeBitValue(6, 7, value);
	}

	
	
	public DataMemory getMemory(){
//		return data;
		data = mem;
		return data;
	}
	
	
	public boolean getPause() {
		return pause;
	}


	public void setPause(boolean pauses) {
		pause = pauses;
	}

	// sucht einen Befehl und führt ihn aus
	public void findeBefehl(int code) {

		opCode = code & 0x3FFF;
		initOpBitSet(opCode);

		if (opCodeBitSet.get(13)) {
			// 1xxxxxxxxxxxxx
			if (opCodeBitSet.get(12)) {
				// 11xxxxxxxxxxxx
				switch (opCode >>> 10 & 3) {
				case 0:
					movlw();
					return;
				case 1:
					retlw();
					return;
				case 2:
					if (opCodeBitSet.get(9)) {
						// xorlw();
					} else {
						if (opCodeBitSet.get(8)) {
							// andlw();
						} else {
							// iorlw();
						}
					}
					return;
				case 3:
					if (opCodeBitSet.get(9)) {
						addlw();
					} else {
						// sublw();
					}
					return;
				}
			} else {
				// 10xxxxxxxxxxxx
				if (opCodeBitSet.get(11)) {
					goTo();
				} else {
					call();
				}
				return;
			}
		} else {
			// 0xxxxxxxxxxxxx
			if (opCodeBitSet.get(12)) {
				// 01xxxxxxxxxxxx
				switch (opCode >>> 10 & 3) {
				case 0:
					bcf();
					return;
				case 1:
					bsf();
					return;
				case 2:
					btfsc();
					return;
				case 3:
					btfss();
					return;
				}
			} else {
				// 00xxxxxxxxxxxx
				switch (opCode >>> 8 & 15) {
				case 0:
					if (opCodeBitSet.get(7)) {
						movwf();
						return;
					} else {
						switch (opCode & 15) {
						case 0:
							nop();
							return;
						case 3:
							// sleep();
							return;
						case 4:
							// clrwdt();
							return;
						case 8:
							reTurn();
							return;
						case 9:
							// retfie();
							return;
						}
					}
					return;
				case 1:
					if (opCodeBitSet.get(7)) {
						// clrf();
					} else {
						clrw();
					}
					return;
				case 2:
					subwf();
					return;
				case 3:
					// decf();
					return;
				case 4:
					// iorwf();
					return;
				case 5:
					// andwf();
					return;
				case 6:
					// xorwf();
					return;
				case 7:
					 addwf();
					return;
				case 8:
					// movf();
					return;
				case 9:
					comf();
					return;
				case 10:
					incf();
					return;
				case 11:
					decfsz();
					return;
				case 12:
					 rrf();
					return;
				case 13:
					 rlf();
					return;
				case 14:
					// swapf();
					return;
				case 15:
					incfsz();
					return;
				}
			}

		}
	}

	private void initOpBitSet(int opCode) {
		opCodeBitSet.clear();

		for (int i = 0; i < 14; i++) {
			if (opCode % 2 != 0) {
				opCodeBitSet.set(i);
			}
			opCode = opCode >>> 1;
		}
	}

	public void movlw() {
		akku.setAkku((opCode & 255));
//		System.out.println("MOVLW: Akkuwert danach: " + akku.getAkku());
	}

	private void movwf() {
		int stelle= opCode&127;
		
		//alternative zur bugbehebung beim auslesen von 0x85 -> wird aber nicht benutzt
		if((opCode&0xFF) == 0x85){
			String zahl = Integer.toBinaryString(akku.getAkku());
			int zahlZ= Integer.parseInt(zahl);
			RAio[0]=zahlZ%10;
			zahlZ /= 10;
			RAio[1]=zahlZ%10;
			zahlZ /= 10;
			RAio[2]=zahlZ%10;
			zahlZ /= 10;
			RAio[3]=zahlZ%10;
			zahlZ /= 10;
			RAio[4]= zahlZ%10;
			
		}
		if((opCode&0xFF) == 0x86){
			String zahl = Integer.toBinaryString(akku.getAkku());
			int zahlZ= Integer.parseInt(zahl);
			RBio[0]=zahlZ%10;
			zahlZ /= 10;
			RBio[1]=zahlZ%10;
			zahlZ /= 10;
			RBio[2]=zahlZ%10;
			zahlZ /= 10;
			RBio[3]=zahlZ%10;
			zahlZ /= 10;
			RBio[4]=zahlZ%10;
			zahlZ /= 10;
			RBio[5]=zahlZ%10;
			zahlZ /= 10;
			RBio[6]=zahlZ%10;
			zahlZ /= 10;
			RBio[7]=zahlZ%10;
			zahlZ /= 10;
			
		}
		
		//direkte oder indir. adressierung?
		if(stelle!=0){
			akku.writeToMem(stelle);
//			System.out.println("MOVWF: Stelle: " + (opCode & 127)
//					+ "  Inhalt danach " + mem.readFileValue((opCode & 127)));
		}else{
			akku.writeToMem(mem.readFileValue(4));
//			System.out.println("MOVWF: Stelle: " + mem.readFileValue(4)
//					+ "  Inhalt danach " + mem.readFileValue(mem.readFileValue(4)));
		}
		
//		System.out.println("MOVWF: Stelle: " + (opCode & 127)
//				+ "  Inhalt danach " + mem.readFileValue((opCode & 127)));
	}

	private void clrw() {
		akku.deleteAkku();
//		System.out.println("CLRW: Akku danach: " + akku.getAkku());
	}

	private void incf() {

		int tmp = opCode;
		int inhalt = mem.readFileValue((opCode & 127)) + 1;

		if (inhalt > 0xFF) {
			inhalt = 0;
			mem.setZeroFlag();
		} else {
			mem.deleteZeroFlag();
		}

		if (tmp > 128) {
			mem.writeFileValue((opCode & 127), inhalt);
		} else {
			akku.setAkku(inhalt);
		}
//		System.out.println("Incf -  RegisterStelle: " + (opCode & 127)
//				+ "Wert danach: " + mem.readFileValue((opCode & 127)));
	}

	private void bcf() {
//		 System.out.println("bcf davor: "+mem.readFileValue(opCode&127));
		int bit = opCode >> 7;
		bit &= 0b111;
		mem.writeBitValue((opCode & 127), bit, 0);
//		 System.out.println("nach bcf: "+mem.readFileValue(opCode&127));
	}

	private void bsf() {
//		System.out.println("BSF davor: " + mem.readFileValue(opCode & 127));
		int bit = opCode >> 7;
		bit &= 0b111;
		mem.writeBitValue((opCode & 127), bit, 1);
//		System.out.println("Bit set file: " + mem.readFileValue(opCode & 127));
	}

	private void addlw() {
		// System.out.println(akku.getAkku());
		int result = akku.getAkku() + (opCode & 255);

		int maske = 0b1111;
		int tmp = opCode & maske;
		int akkuWertMaskiert = akku.getAkku() & 15;

		if (result > 0xFF) {
			mem.setCarryFlag();
			result = result - 256;
		} else {
			mem.deleteCarryFlag();
		}
		if (result == 0) {
			mem.setZeroFlag();
		} else {
			mem.deleteZeroFlag();
		}
		if ((akkuWertMaskiert + tmp) > 15) {
			mem.setDC();
		} else {
			mem.deleteDC();
		}

		akku.setAkku(result);
//		 System.out.println(akku.getAkku());
	}
	private void addwf(){
		int adr = opCode & 127;
		int inhalt;
		
		if (adr != 0) {	
		inhalt = mem.readFileValue(adr) + akku.getAkku();
		}else{
		inhalt = mem.readFileValue(mem.readFileValue(4))+ akku.getAkku();
		}
		
		int speicherort = opCode >> 7;

		inhalt &= 255;
		
		

		int maske = 0b1111;
		int tmp = opCode & maske;
		int akkuWertMaskiert = akku.getAkku() & 15;

	
		if (inhalt > 0xFF) {
			mem.setCarryFlag();
			inhalt = inhalt - 256;
		} else {
			mem.deleteCarryFlag();
		}
		if (inhalt == 0) {
			mem.setZeroFlag();
		} else {
			mem.deleteZeroFlag();
		}
		if ((akkuWertMaskiert + tmp) > 15) {
			mem.setDC();
		} else {
			mem.deleteDC();
		}
		
		speicherort &= 0b1;
		if (speicherort == 0) {
			akku.setAkku(inhalt);
//			System.out.println("HELP --- Inhalt indirect: "+ mem.readFileValue(0));
//			System.out.println("akku nach addwf: " + inhalt);
		} else {
			mem.writeFileValue(opCode & 127, inhalt);
//			System.out.println("Speicherst" + (opCode&127) + "nach addwf: "+ inhalt);
		}
		
		
	}

	private void subwf() {
//		System.out.println("Memory: "+mem.readFileValue(opCode&127) +" - Minus - "+ akku.getAkku());
		int adr = opCode & 127;
		int inhalt = mem.readFileValue(adr) - akku.getAkku();
		int speicherort = opCode >> 7;
		
//		System.out.println(inhalt);
//		inhalt &= 0xFF;

		if (inhalt < 0) {
			inhalt *= (-1);
		}
		

		int maske = 0b1111;
		int tmp = opCode & maske;
		int akkuWertMaskiert = akku.getAkku() & 15;

		if (inhalt > 0xFF) {
			mem.setCarryFlag();
			inhalt = inhalt - 256;
		} else {
			mem.deleteCarryFlag();
		}
		if (inhalt == 0) {
			mem.setZeroFlag();
		} else {
			mem.deleteZeroFlag();
		}
		if ((akkuWertMaskiert + tmp) > 15) {
			mem.setDC();
		} else {
			mem.deleteDC();
		}
		
		speicherort &= 0b1;
		if (speicherort == 0) {
			akku.setAkku(inhalt);
//			System.out.println("Akku nach subwf: "+ akku.getAkku());
		} else {
			mem.writeFileValue(opCode & 127, inhalt);
		}

	}

	private void nop() {
		// System.out.println("nop");
	}

	private void btfss() {
		int bit = opCode >> 7;
		bit &= 0b111;

		if ((mem.readBitValue((opCode & 127), bit)) == 0) {
			// System.out.println("dont skip");
		} else {
			// System.out.println("skip");
			i++;
		}
	}

	private void btfsc() {
		int bit = opCode >> 7;
		bit &= 0b111;

		if ((mem.readBitValue((opCode & 127), bit)) == 0) {
			// System.out.println("skip");
			i++;
		} else {
			// System.out.println("dont skip");
		}
	}

	private void goTo() {
		i = (opCode & 2047) - 1;
		// i= mem.getLath(3, 4) + opCode & 2047;
//		System.out.println("GOTO: gehe zu: " + i);
//		if (i==40) {
//			mem.writeBitValue(6, 0, 1);
//		}
//		if (i==46) {
//			ausgabe();
//			System.exit(1);
//		}
		// Achtung: es entsteht dauerschleife wenn rb=0 --- muss in GUI gesetzt
		// werden damits weitergeht
	}

	private void comf() {
		// inhalt komplimentieren
//		System.out.println("Mem vor com: "+ (mem.readFileValue(opCode&127)));
		int inhalt = mem.readFileValue((opCode & 127));
		inhalt = ~inhalt;
		inhalt &= 255;

		// inhalt = 0 ?
		if (inhalt == 0) {
			// System.out.println("set zero");
			mem.setZeroFlag();
		} else {
			// System.out.println("clear zero");
			mem.deleteZeroFlag();
		}

		// Speicherort wählen
		int speicherort = opCode >> 7;
		speicherort &= 0b1;
		if (speicherort == 0) {
			akku.setAkku(inhalt);
//			System.out.println("Akku nach com: "+akku.getAkku());
		} else {
			mem.writeFileValue(opCode & 127, inhalt);
//			System.out.println("Register nach com: "+mem.readFileValue(opCode&127));
		}
	}

	private void decfsz() {
		
//		System.out.println("DEC nach durchlauf: " +a + "  "+ (mem.readFileValue(12)-1));
//		System.out.println();
//		if (a==17) {
//			System.exit(1);	
//		}
//		a++;
		
		int inhalt = mem.readFileValue((opCode & 127)) - 1;
		if (inhalt != 0) {

			int speicherort = opCode >> 7;
			speicherort &= 0b1;
			if (speicherort == 0) {
				akku.setAkku(inhalt);
			} else {
				mem.writeFileValue(opCode & 127, inhalt);
			}
			// System.out.println("springe nicht");

		} else {
			nop();
//			System.out.println("Springe durch decfsz");
			i++;
		}
	}

	private void incfsz() {
		int inhalt = mem.readFileValue((opCode & 127)) + 1;
		if (inhalt != 0) {

			int speicherort = opCode >> 7;
			speicherort &= 0b1;
			if (speicherort == 0) {
				akku.setAkku(inhalt);
			} else {
				mem.writeFileValue(opCode & 127, inhalt);
			}

		} else {
			nop();
			// System.out.println("Springe ");
			i++;
		}
	}

	private void call() {
		int adr = (opCode & 2047) - 1;
		mem.schreibeAufStack(i );

		i = adr;
//		System.out.println("gehe zu adr: " + i);
		// i= mem.getLath(3, 4) + adr;
		// System.out.println(mem.leseStack());
	}

	private void retlw() {
		int value = opCode & 255;
		akku.setAkku(value);
		i = mem.leseStack();
//		System.out.println("gehe zurück zu Adr: " + i);
	}

	private void reTurn() {
		i = mem.leseStack();
	}
	private void rlf(){
		int inhalt = mem.readFileValue(opCode&127);
		
		inhalt = (inhalt << 1);
		
		if (inhalt > 0xFF) {
			inhalt = inhalt + mem.getCarryFlag();
			inhalt &= 255;
			mem.setCarryFlag();
		} else {
			inhalt = inhalt + mem.getCarryFlag();
			inhalt &= 255;
			mem.deleteCarryFlag();
		}
		
		int speicherort = opCode >> 7;
		speicherort &= 0b1;
		if (speicherort == 0) {
			akku.setAkku(inhalt);
		} else {
			mem.writeFileValue(opCode & 127, inhalt);
		}
	}
	private void rrf(){
		int inhalt = mem.readFileValue(opCode&127);
		int helper = inhalt & 1; 
		
//		System.out.println("Inhalt: "+ Integer.toBinaryString(inhalt));
//		System.out.println("Carry: "+mem.getCarryFlag());
		inhalt = (inhalt >>> 1);
		
		if (mem.getCarryFlag()==1) {
			inhalt += 127;
		}
		
		if (helper == 1) {
			mem.setCarryFlag();
		} else {
			mem.deleteCarryFlag();
		}
		
		int speicherort = opCode >> 7;
		speicherort &= 0b1;
		if (speicherort == 0) {
			akku.setAkku(inhalt);
		} else {
			mem.writeFileValue(opCode & 127, inhalt);
		}
		
//		System.out.println("Inhalt after: "+Integer.toBinaryString(inhalt));
//		System.out.println("Carry after: "+mem.getCarryFlag());
	}
	public void ausgabe(){
		//nur zu testzwecken
		System.out.println("Reg: Akku" + akku.getAkku());
		System.out.println("Reg: 0  " + mem.readFileValue(0));
		System.out.println("Reg: 2  " + mem.readFileValue(2));
		System.out.println("Reg: 3  " + mem.readFileValue(3));
		System.out.println("Reg: 4  " + mem.readFileValue(4));
		System.out.println("Reg: 5  " + mem.readFileValue(5));
		System.out.println("Reg: 6  " + mem.readFileValue(6));
		System.out.println("Reg: 12 " + mem.readFileValue(12));
		
		System.out.println("Memory F: " + mem.readFileValue(0x0f));
		System.out.println("Memory E: " + mem.readFileValue(0x0e));
		
		System.out.println(mem.readFileValue(0x10));
		System.out.println(mem.readFileValue(0x11));
		System.out.println(mem.readFileValue(0x12));
		System.out.println(mem.readFileValue(0x1f));
		
	}
	public int returnStack{
		
	}


}