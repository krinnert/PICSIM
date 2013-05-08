package simulator.dateiZugriff;

public class Befehl {
	private int Code;
	private int Programmzeile;
	
	public Befehl(int codeX, int progX){
		Code= codeX;
		Programmzeile= progX;
	}
	
	public int getCode() {
		return Code;
	}
	public void setCode(int code) {
		Code = code;
	}
	public int getProgrammzeile() {
		return Programmzeile;
	}
	public void setProgrammzeile(int programmzeile) {
		Programmzeile = programmzeile;
	}
	
	
}

	
