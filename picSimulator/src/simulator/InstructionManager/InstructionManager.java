package simulator.InstructionManager;

import java.util.BitSet;
import java.util.TreeMap;

import simulator.dateiZugriff.Befehl;
import simulator.memory.Akku;
import simulator.memory.DataMemory;

public class InstructionManager {
	static TreeMap<Integer, Befehl> befehlTree = new TreeMap<Integer, Befehl>();
	private DataMemory mem = new DataMemory();
	private Akku akku = new Akku(mem);

	private int opCode;
	private int speicherSt;
	private BitSet opCodeBitSet = new BitSet(14);
	private DataMemory data = new DataMemory();
	public int i = 0;

	public int a = 0;

	// List mit allen befehlen -> ruft ausf�hren aus
	public void starteAbarbeitung(TreeMap befehlBaum) {
		befehlTree = befehlBaum;
		for (i = 0; i < befehlTree.size(); i++) {
			befehlTree.get(i);
			findeBefehl(befehlTree.get(i).getCode());
//			System.out.println("******Statusregisterwert:  " + mem.readFileValue(3));
//			System.out.println("******zeile:" + i +"  F Register: "+mem.readFileValue(0x0f));
		}

	}

	// sucht einen Befehl und f�hrt ihn aus
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
					// rrf();
					return;
				case 13:
					// rlf();
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
		System.out.println("MOVLW: Akkuwert danach: " + akku.getAkku());
	}

	private void movwf() {
		int stelle= opCode&127;
		
		if(stelle!=0){
			akku.writeToMem(stelle);
			System.out.println("MOVWF: Stelle: " + (opCode & 127)
					+ "  Inhalt danach " + mem.readFileValue((opCode & 127)));
		}else{
			akku.writeToMem(mem.readFileValue(4));
			System.out.println("MOVWF: Stelle: " + mem.readFileValue(4)
					+ "  Inhalt danach " + mem.readFileValue(mem.readFileValue(4)));
		}
		
		System.out.println("MOVWF: Stelle: " + (opCode & 127)
				+ "  Inhalt danach " + mem.readFileValue((opCode & 127)));
	}

	private void clrw() {
		akku.deleteAkku();
		System.out.println("CLRW: Akku danach: " + akku.getAkku());
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
		System.out.println("Incf -  RegisterStelle: " + (opCode & 127)
				+ "Wert danach: " + mem.readFileValue((opCode & 127)));
	}

	private void bcf() {
		 System.out.println("bcf davor: "+mem.readFileValue(opCode&127));
		int bit = opCode >> 7;
		bit &= 0b111;
		mem.writeBitValue((opCode & 127), bit, 0);
		 System.out.println("nach bcf: "+mem.readFileValue(opCode&127));
	}

	private void bsf() {
		System.out.println("BSF davor: " + mem.readFileValue(opCode & 127));
		int bit = opCode >> 7;
		bit &= 0b111;
		mem.writeBitValue((opCode & 127), bit, 1);
		System.out.println("Bit set file: " + mem.readFileValue(opCode & 127));
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
		 System.out.println(akku.getAkku());
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
			System.out.println("HELP --- Inhalt indirect: "+ mem.readFileValue(0));
			System.out.println("akku nach addwf: " + inhalt);
		} else {
			mem.writeFileValue(opCode & 127, inhalt);
			System.out.println("Speicherst" + (opCode&127) + "nach addwf: "+ inhalt);
		}
		
		
	}

	private void subwf() {
		System.out.println("Memory: "+mem.readFileValue(opCode&127) +" - Minus - "+ akku.getAkku());
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
			System.out.println("Akku nach subwf: "+ akku.getAkku());
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
		System.out.println("GOTO: gehe zu: " + i);
		if (i==40) {
			mem.writeBitValue(6, 0, 1);
		}
		if (i==46) {
			ausgabe();
			System.exit(1);
		}
		// Achtung: es entsteht dauerschleife wenn rb=0 --- muss in GUI gesetzt
		// werden damits weitergeht
	}

	private void comf() {
		// inhalt kompl�mentieren
		System.out.println("Mem vor com: "+ (mem.readFileValue(opCode&127)));
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

		// Speicherort w�hlen
		int speicherort = opCode >> 7;
		speicherort &= 0b1;
		if (speicherort == 0) {
			akku.setAkku(inhalt);
			System.out.println("Akku nach com: "+akku.getAkku());
		} else {
			mem.writeFileValue(opCode & 127, inhalt);
			System.out.println("Register nach com: "+mem.readFileValue(opCode&127));
		}
	}

	private void decfsz() {
		
		System.out.println("DEC nach durchlauf: " +a + "  "+ (mem.readFileValue(12)-1));
		System.out.println();
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
			System.out.println("Springe durch decfsz");
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
		System.out.println("gehe zu adr: " + i);
		// i= mem.getLath(3, 4) + adr;
		// System.out.println(mem.leseStack());
	}

	private void retlw() {
		int value = opCode & 255;
		akku.setAkku(value);
		i = mem.leseStack();
		System.out.println("gehe zur�ck zu Adr: " + i);
	}

	private void reTurn() {
		i = mem.leseStack();
	}
	public void ausgabe(){
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

}