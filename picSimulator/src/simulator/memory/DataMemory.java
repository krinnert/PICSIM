package simulator.memory;

import java.util.BitSet;
import java.util.Stack;

public class DataMemory {

	public int[] memory;
	boolean bank0;
	int addressOffset = 0x80;
	Stack<Integer> stack = new Stack<Integer>();
	int pclath=0;
	private BitSet pclathBitSet = new BitSet(14);
	
	private void initPCLath() {
		pclathBitSet.clear();

		for (int i = 0; i < 14; i++) {
			if (pclath % 2 != 0) {
				pclathBitSet.set(i);
			}
			pclath = pclath >>> 1;
		}
	}

	public DataMemory() {

		// Int-Array mit der Größe FF
		memory = new int[256];
		this.initMemory();
		
		this.initPCLath();
	}

	

	// Kompletten 8 Bit aus Register an Adresse ausgeben
	public int readFileValue(int address) {

		// Wenn Bank 1 aktiv, dann Offset addieren
//		if (this.checkBank()) {
//			address = address + addressOffset;
//		}
		return memory[address];
	}

	// Komplette 8 Bit in Register an Addresse schreiben
	public void writeFileValue(int address, int value) {

		
		
		// Wird immer zuerst in bank 0 geschrieben und anschließend nach Bank 1
		// gespiegelt

		// Adresse auf Adresse in Bank 1 umwandeln
		int tmpAddress = address & 0b01111111;

		switch (tmpAddress) {
		case 0:
			memory[tmpAddress] = value;
			memory[(tmpAddress | 0b10000000)] = value;
			break;
		case 2:
			memory[tmpAddress] = value;
			memory[(tmpAddress | 0b10000000)] = value;
		case 3:
			memory[tmpAddress] = value;
			memory[(tmpAddress | 0b10000000)] = value;
		case 4:
			memory[tmpAddress] = value;
			memory[(tmpAddress | 0b10000000)] = value;
		case 0x0A:
			memory[tmpAddress] = value;
			memory[(tmpAddress | 0b10000000)] = value;
		case 0x0B:
			memory[tmpAddress] = value;
			memory[(tmpAddress | 0b10000000)] = value;
		default:
			
			if (this.checkBank()) {
				address = address + addressOffset;
			}
			
			memory[address] = value;
			break;
		}

	}

	// Bit an der Adresse lesen
	public int readBitValue(int address, int position) {

		// Wenn Bank 1 aktiv, dann Offset addieren
		if (this.checkBank()) {
			address = address + addressOffset;
		}

		// Nach rechts shiften, bis gewünschtes Bit hinten steht, dann Rest
		// ausblenden
		int value = memory[address] >> position;
		value = value & 0b00000001;

		return value;
	}

	// Bit an der Adresse und Position mit Value schreiben
	public void writeBitValue(int address, int position, int value) {

		// Wird immer zuerst in bank 0 geschrieben und anschließend nach Bank 1
		// gespiegelt
		int mask = (int) Math.pow(2, position);
		int tmpAddress = address & 0b01111111;

		switch (tmpAddress) {
		case 0:
			if (value == 1) {
				memory[tmpAddress] = memory[tmpAddress] | mask;
				memory[(tmpAddress | 0b10000000)] = memory[(tmpAddress | 0b10000000)]
						| mask;
			} else {
				mask = ~mask;
				memory[tmpAddress] = memory[tmpAddress] & mask;
				memory[(tmpAddress | 0b10000000)] = memory[(tmpAddress | 0b10000000)]
						& mask;
			}
			break;
		case 2:
			if (value == 1) {
				memory[tmpAddress] = memory[tmpAddress] | mask;
				memory[(tmpAddress | 0b10000000)] = memory[(tmpAddress | 0b10000000)]| mask;
			}

			else {
				mask = ~mask;
				memory[tmpAddress] = memory[tmpAddress] & mask;
				memory[(tmpAddress | 0b10000000)] = memory[(tmpAddress | 0b10000000)]& mask;
			}
			break;
		case 3:
			if (value == 1) {
				memory[tmpAddress] = memory[tmpAddress] | mask;
				memory[(tmpAddress | 0b10000000)] = memory[(tmpAddress | 0b10000000)]| mask;
			}

			else {
				mask = ~mask;
				memory[tmpAddress] = memory[tmpAddress] & mask;
				memory[(tmpAddress | 0b10000000)] = memory[(tmpAddress | 0b10000000)]
						& mask;
			}
			break;
		case 4:
			if (value == 1) {
				memory[tmpAddress] = memory[tmpAddress] | mask;
				memory[(tmpAddress | 0b10000000)] = memory[(tmpAddress | 0b10000000)]
						| mask;
			}

			else {
				mask = ~mask;
				memory[tmpAddress] = memory[tmpAddress] & mask;
				memory[(tmpAddress | 0b10000000)] = memory[(tmpAddress | 0b10000000)]
						& mask;
			}
			break;
		case 0x0A:
			if (value == 1) {
				memory[tmpAddress] = memory[tmpAddress] | mask;
				memory[(tmpAddress | 0b10000000)] = memory[(tmpAddress | 0b10000000)]
						| mask;
			}

			else {
				mask = ~mask;
				memory[tmpAddress] = memory[tmpAddress] & mask;
				memory[(tmpAddress | 0b10000000)] = memory[(tmpAddress | 0b10000000)]
						& mask;
			}
			break;
		case 0x0B:
			if (value == 1) {
				memory[tmpAddress] = memory[tmpAddress] | mask;
				memory[(tmpAddress | 0b10000000)] = memory[(tmpAddress | 0b10000000)]
						| mask;
			}

			else {
				mask = ~mask;
				memory[tmpAddress] = memory[tmpAddress] & mask;
				memory[(tmpAddress | 0b10000000)] = memory[(tmpAddress | 0b10000000)]
						& mask;
			}
			break;
		default:
			// Wenn Bank 1 aktiv, dann Offset addieren
			if (this.checkBank()) {
				address = address + addressOffset;
			}

			if (value == 1) {

				memory[address] = memory[address] | mask;
			}

			else {
				mask = ~mask;
				memory[address] = memory[address] & mask;
			}

			break;
		}

	}

	public void setCarryFlag() {
		// In beiden Statusregistern das flag setzen
		this.writeBitValue(03, 0, 1);
		this.writeBitValue(83, 0, 1);
	}

	public void deleteCarryFlag() {
		// In beiden Statusregistern das Flag löschen
		this.writeBitValue(0x03, 0, 0);
		this.writeBitValue(0x83, 0, 0);

	}
	
	public int getCarryFlag(){
		return readBitValue(0x83, 0);
	}

	public void setZeroFlag() {
		// In beiden Statusregistern das flag setzen
		this.writeBitValue(0x03, 2, 1);
		this.writeBitValue(0x83, 2, 1);
	}

	public void deleteZeroFlag() {
		// In beiden Statusregistern das Flag löschen
		this.writeBitValue(0x03, 2, 0);
		this.writeBitValue(0x83, 2, 0);
	}

	// Methode um Bank zu ändern
	public void activateBank(boolean bank) {
		// false Bank0
		// true Bank1
		if (bank==false) {
			memory[0x03]=memory[0x03]&0b11011111;
			memory[0x83]=memory[0x83]&0b11011111;
		} else {
			memory[0x03]=memory[0x03]|0b00100000;
			memory[0x83]=memory[0x83]|0b00100000;
		}
	}

	// Methode um Aktive Bank herauszufinden
	public boolean checkBank() {
		if((memory[03]&0b00100000)==0b00100000){
		return true; //RP0 ist 1 --> Bank 1 aktiv	
		}
		else {
			return false; // Bank 0 aktiv
		}
	}
	
	public void setDC(){
				this.writeBitValue(0x03, 1, 1);
				this.writeBitValue(0x83, 1, 1);
	}
	public void deleteDC(){
				this.writeBitValue(0x03, 1, 0);
				this.writeBitValue(0x83, 1, 0);
	}
	public void schreibeAufStack(int adresse){
		stack.push(adresse);
	}
	public int leseStack(){
		return stack.pop();
	}
	public int getLath(int bitLow, int bitHigh){
		int ret= Integer.valueOf(pclathBitSet.get(bitLow, bitHigh).toString());
		return ret;
		//to test
	}
	public int[] getMemory(){
		return memory;
	}
	public void initMemory() {

		// Kompletter Speicherbereich mit 0 überschreiben
		for (int i = 0; i < memory.length; i++) {
			memory[i] = 0b00000000;
		}

		// SFR mit vorbelegten Werten füllen, wenn diese nicht 0 oder unbekannt
		// sind
		// 03h Status: 0001 1xxx
//		memory[0x03] = 0b00011000; - n used
		memory[0x03] = 0b00000000;
		// 81h TMR0:1111 1111
		memory[0x81] = 0b11111111;
		// 83h Status 0001 1xxx
		memory[0x83] = 0b00011000;
		// 85h TRIS A ---1 1111:
		memory[0x85] = 0b00011111;
		// 86h TRIS B 1111 1111
		memory[0x86] = 0b11111111;
		
		
	}
}