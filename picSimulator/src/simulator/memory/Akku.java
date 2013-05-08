package simulator.memory;

import java.util.BitSet;

public class Akku {

	int bits;
	DataMemory memory;
	
	
	public Akku(DataMemory mem) {
		bits = 0;
		memory = mem;
	}
	
	public void setAkku(int value){
		bits = value;
		checkOverflow();
	}
	
	public int getAkku(){
		return bits;
	}
	
	public void deleteAkku(){
		bits = 0;
	}
	
	public void checkOverflow(){
		if(bits>255){
			memory.setCarryFlag();
		}
	}
	
	public void writeToMem(int adr){
		memory.writeFileValue(adr, bits);
	}
	
}
