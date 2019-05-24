package clases;

public class Comida {
	
	byte x, y;
	
	public Comida(byte dx, byte dy) {
		this.x = (byte) (Math.random()*dx);
		this.y = (byte) (Math.random()*dy);
	}

	public byte getX() {
		return x;
	}

	public byte getY() {
		return y;
	}

	public void setX(byte x) {
		this.x = x;
	}

	public void setY(byte y) {
		this.y = y;
	}
	

}
