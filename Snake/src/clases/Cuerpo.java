package clases;

public class Cuerpo {
	
	private byte direccion;
	private byte x, y;
	private boolean cabeza;
	private byte timer;
	
	public Cuerpo(byte direccion, byte x, byte y, boolean cabeza) {
		this.direccion = direccion;
		this.x = x;
		this.y = y;
		this.cabeza = cabeza;
	}
	
	public Cuerpo(byte direccion, byte x, byte y, boolean cabeza, byte timer) {
		this(direccion, x, y, cabeza);
		this.timer = timer;
	}
	
	public void mover(byte direccion, byte dx, byte dy) {
		switch(this.direccion) {
			case 1:
				this.y = (byte) ((this.y+dy-1)%dy);
				break;
			case 2:
				this.x = (byte) ((this.x+1)%dx);
				break;
			case 3:
				this.y = (byte) ((this.y+1)%dy);
				break;
			case 4:
				this.x = (byte) ((this.x+dx-1)%dx);
				break;
		}
		this.direccion = direccion;
	}

	public byte getDireccion() {
		return direccion;
	}

	public byte getX() {
		return x;
	}

	public byte getY() {
		return y;
	}

	public boolean isCabeza() {
		return cabeza;
	}

	public void setDireccion(byte direccion) {
		this.direccion = direccion;
	}

	public void setX(byte x) {
		this.x = x;
	}

	public void setY(byte y) {
		this.y = y;
	}

	public void setCabeza(boolean cabeza) {
		this.cabeza = cabeza;
	}
	

	public void actualizarTimer() {
		this.timer--;
	}
	
	public byte getTimer() {
		return this.timer;
	}
}
