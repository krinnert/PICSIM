MAIN:


//		for (Integer elem : befehlTree.keySet()){
//			befehl.findeBefehl(befehlTree.get(elem).getCode(), befehlTree);
//		}
		
		befehl.starteAbarbeitung(befehlTree);
		 

MANAGER:
	
	
	package simulator.InstructionManager;
	import java.util.BitSet;
	import java.util.TreeMap;

	import simulator.dateiZugriff.Befehl;
	import simulator.memory.DataMemory;

	public class InstructionManager {
		static TreeMap<Integer, Befehl> befehlTree = new TreeMap<Integer, Befehl>();

		private int opCode;
		private int speicherSt;
		private BitSet opCodeBitSet = new BitSet(14);
		private DataMemory data= new DataMemory();
		public int i = 0; 
		
		
		
		//List mit allen befehlen -> ruft ausführen aus
		public void starteAbarbeitung(TreeMap befehlBaum){
			befehlTree= befehlBaum; 
			for (i=0; i< befehlTree.size(); i++){
				befehlTree.get(i);
				findeBefehl(befehlTree.get(i).getCode());
				
				System.out.println(i);
			}
		}
		
		//sucht einen Befehl und führt ihn aus
		public void findeBefehl(int code) {

			opCode = code & 0x3FFF;
			initOpBitSet(opCode);
			
			if (opCodeBitSet.get(13)) {
				// 1xxxxxxxxxxxxx
				if (opCodeBitSet.get(12)) {
					// 11xxxxxxxxxxxx
					switch (opCode >>> 10 & 3) {
					case 0:
//						movlw();
						return;
					case 1:
//						retlw();
						return;
					case 2:
						if (opCodeBitSet.get(9)) {
//							xorlw();
						} else {
							if (opCodeBitSet.get(8)) {
//								andlw();
							} else {
//								iorlw();
							}
						}
						return;
					case 3:
						if (opCodeBitSet.get(9)) {
//							addlw();
						} else {
//							sublw();
						}
						return;
					}
				} else {
					// 10xxxxxxxxxxxx
					if (opCodeBitSet.get(11)) {
//						goTo();
					} else {
//						call();
					}
					return;
				}
			} else {
				// 0xxxxxxxxxxxxx
				if (opCodeBitSet.get(12)) {
					// 01xxxxxxxxxxxx
					switch (opCode >>> 10 & 3) {
					case 0:
//						bcf();
						return;
					case 1:
//						bsf();
						return;
					case 2:
//						btfsc();
						return;
					case 3:
//						btfss();
						return;
					}
				} else {
					// 00xxxxxxxxxxxx
					switch (opCode >>> 8 & 15) {
					case 0:
						if (opCodeBitSet.get(7)) {
//							movwf();
							return;
						} else {
							switch (opCode & 15) {
							case 0:
//								nop();
								return;
							case 3:
//								sleep();
								return;
							case 4:
//								clrwdt();
								return;
							case 8:
//								reTurn();
								return;
							case 9:
//								retfie();
								return;
							}
						}
						return;
					case 1:
						if (opCodeBitSet.get(7)) {
//							clrf();
						} else {
//							clrw();
						}
						return;
					case 2:
//						subwf();
						return;
					case 3:
//						decf();
						return;
					case 4:
//						iorwf();
						return;
					case 5:
//						andwf();
						return;
					case 6:
//						xorwf();
						return;
					case 7:
//						addwf();
						return;
					case 8:
//						movf();
						return;
					case 9:
//						comf();
						return;
					case 10:
//						incf();
						return;
					case 11:
//						decfsz();
						return;
					case 12:
//						rrf();
						return;
					case 13:
//						rlf();
						return;
					case 14:
//						swapf();
						return;
					case 15:
//						incfsz();
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
		


		
	
	}
