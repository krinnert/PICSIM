package simulator.memory;

import static org.junit.Assert.*;

import java.lang.reflect.Member;

import org.junit.Test;

import simulator.memory.DataMemory;

public class DataMemoryTest {

	@Test
	public void test() {
		DataMemory mem = new DataMemory();
		mem.initMemory();
		
		assertEquals("Status register testen", 0b00011000, mem.readFileValue(03));
	}

	
	@Test
	public void getBitTest() {
		DataMemory mem = new DataMemory();
		mem.initMemory();
		
		assertEquals("Bit 0 im Statusregister testen", 0b0, mem.readBitValue(03, 0));
		assertEquals("Bit 1 im Statusregister testen", 0b0, mem.readBitValue(03, 1));
		assertEquals("Bit 2 im Statusregister testen", 0b0, mem.readBitValue(03, 2));
		assertEquals("Bit 3 im Statusregister testen", 0b1, mem.readBitValue(03, 3));
		assertEquals("Bit 4 im Statusregister testen", 0b1, mem.readBitValue(03, 4));
		assertEquals("Bit 5 im Statusregister testen", 0b0, mem.readBitValue(03, 5));
		assertEquals("Bit 6 im Statusregister testen", 0b0, mem.readBitValue(03, 6));
		assertEquals("Bit 7 im Statusregister testen", 0b0, mem.readBitValue(03, 7));
	}
	
	@Test
	public void writeBitTest() throws Exception {
		DataMemory mem = new DataMemory();
		mem.initMemory();
		
		assertEquals("Bit 2 im TestRegister vorm schreiben", 0b0, mem.readBitValue(00, 2));
		
		mem.writeBitValue(00, 2, 1);
		
		assertEquals("Bit 2 im TestRegister nach dem schreiben", 0b1, mem.readBitValue(00, 2));
	}
	
	
	@Test
	public void mirrorTest() throws Exception {
		
		DataMemory mem = new DataMemory();
		mem.initMemory();
		
		assertEquals("Bit 2 im Statusregister auf 0 überprüfen", 0b0, mem.readBitValue(3, 2));
		
		mem.writeFileValue(03, 0b11111111);
		
		assertEquals("Bit 2 im StatusRegister Bank 0", 0b1, mem.readBitValue(3, 2));
				assertEquals("Bit 2 im StatusRegister Bank 1", 0b1, mem.readBitValue(03, 2));

	}
	
	@Test
	public void mirrorTestBit() throws Exception {
		
		DataMemory mem = new DataMemory();
		mem.initMemory();
		
		assertEquals("Bit 0 im Statusregister auf 0 überprüfen", 0b0, mem.readBitValue(2, 0));
		
		mem.writeBitValue(02, 0, 1);
		
		assertEquals("Bit 0 im StatusRegister Bank 0", 0b1, mem.readBitValue(2, 0));
		
		assertEquals("Bit 0 im StatusRegister Bank 1", 0b1, mem.readBitValue(0x82, 0));
	}

	@Test
public void mirrorTestBit2() throws Exception {
		
		DataMemory mem = new DataMemory();
		mem.initMemory();
		
		assertEquals("Bit 7 im Statusregister auf 0 überprüfen", 0b0, mem.readBitValue(3, 7));
		
		mem.writeBitValue(03, 7, 1);
		
		assertEquals("Bit 7 im StatusRegister Bank 0", 0b1, mem.readBitValue(3, 7));
		
		assertEquals("Bit 7 im StatusRegister Bank 1", 0b1, mem.readBitValue(0x83, 7));
	}
	


@Test
public void BankTest() throws Exception {
	
	DataMemory mem = new DataMemory();
	mem.initMemory();
	
	
	mem.writeFileValue(0x0C, 0b11111111);
	
	assertEquals("Bank 0 muss alles 255 sein", 255, mem.readFileValue(0x0C));
	
	mem.activateBank(true);
	
	assertEquals("Bank 1 muss noch alles 0 sein", 0, mem.readFileValue(0x0C));
}

}
