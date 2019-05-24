package clases;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.*;

public class Snake extends JFrame implements KeyListener, ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private byte dx;
	private byte dy;
	
	private byte crecer;
	
	private JButton[][] campo;
	private LinkedList<Cuerpo> cuerpo;
	private LinkedList<Cuerpo> crecimiento;
	private byte direccionFinal, direccion;
	private Comida comida;
	private int longitud;
	private int dificultad;
	private float score;
	
	private JButton btnjugar;
	
	
	public Snake(byte tamano, byte crecer, int dificultad) {
		super("Snake");
		this.dx=tamano;
		this.dy=tamano;
		this.crecer=crecer;
		this.dificultad=dificultad;
		this.direccionFinal = 1;
		this.direccion = 1;
		this.score = 0;
		Container co = this.getContentPane();
		this.campo = new JButton[dx][dy];
		JPanel grid = new JPanel(new GridLayout(dx,dy));
		grid.setPreferredSize(new Dimension(690,690));
		for(int i=0; i<campo.length; i++) {
			for(int e=0; e<campo[i].length; e++) {
				campo[i][e] = new JButton();
				campo[i][e].addKeyListener(this);
				grid.add(campo[i][e]);
			}
		}
		co.add(grid, BorderLayout.CENTER);
		this.cuerpo = new LinkedList<Cuerpo>();
		this.crecimiento = new LinkedList<Cuerpo>();
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.btnjugar = new JButton("Jugar");
		this.btnjugar.addActionListener(this);
		//co.add(btnjugar, BorderLayout.SOUTH);
		
		jugar();
		
		JOptionPane.showMessageDialog(null,"Score: "+(int)(this.score*100), "information", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void jugar() {
		limpiar();
		iniciar();
		boolean alive = true;
		while(alive) {
			try {
				Thread.sleep((int)((10000/(100+(float)longitud))/(1+(float)dificultad)));
				//1000/(10+longitud)
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			alive = update();
		}
	}
	
	
	private void limpiar() {
		this.direccionFinal = 1;
		this.direccion = 1;
		System.out.println("ye");
		this.cuerpo.removeAll(cuerpo);
		this.crecimiento.removeAll(crecimiento);
		System.out.println("listas limpias");
		this.longitud=0;
		for(byte i=0; i<this.campo.length; i++) {
			for(byte e=0; e<this.campo[i].length; e++) {
				this.campo[i][e].setBackground(UIManager.getColor("Button.background"));
			}
		}
	}
	
	public boolean update() {

		if(!(this.direccionFinal==1&&this.direccion==3)&&!(this.direccionFinal==2&&this.direccion==4)&&
			!(this.direccionFinal==3&&this.direccion==1)&&!(this.direccionFinal==4&&this.direccion==2)) {
			this.direccionFinal=this.direccion;
		}
		Iterator<Cuerpo> i = this.cuerpo.iterator();
		boolean alive = true;
		Cuerpo cabeza = i.next();
		
		Cuerpo aux;
		cabeza.setDireccion(this.direccionFinal);
		this.campo[cabeza.getY()][cabeza.getX()].setBackground(UIManager.getColor("Button.background"));
		cabeza.mover(cabeza.getDireccion(), dx, dy);
		this.campo[cabeza.getY()][cabeza.getX()].setBackground(Color.RED);
		byte direct = cabeza.getDireccion();
		byte directAnt = 0;
		
		while(i.hasNext()) {
			aux = (Cuerpo) i.next();
			this.campo[aux.getY()][aux.getX()].setBackground(UIManager.getColor("Button.background"));
			directAnt = aux.getDireccion();
			aux.mover(direct, dx, dy);
			direct = directAnt;
			this.campo[aux.getY()][aux.getX()].setBackground(Color.RED);
			if(cabeza.getX()==aux.getX()&&cabeza.getY()==aux.getY()) {
				alive = false;
				System.out.println("muerto");
			}
		}
		this.campo[this.cuerpo.getFirst().getY()][this.cuerpo.getFirst().getX()].setBackground(Color.RED);
		this.actualizarCrecimiento();
		if(cabeza.getX()==comida.getX()&&cabeza.getY()==comida.getY()) {
			System.out.println("ha comido");
			this.crecer(cabeza.getX(), cabeza.getY());
			this.score+=((float)this.longitud)*(((float)this.dificultad+1f)/5f)*(40/this.dx);
			generaComida();
		}
		
		return alive;
	}
	
	private void crecer(byte x,byte y) {
		for(int i=0; i<this.crecer; i++) {
			this.crecimiento.add(new Cuerpo((byte)0, x, y, false, (byte)(this.longitud-1)));
			this.longitud++;
		}
	}
	
	private void actualizarCrecimiento() {
		Iterator<Cuerpo> i = this.crecimiento.iterator();
		Cuerpo aux;
		while(i.hasNext()) {
			aux = (Cuerpo) i.next();
			aux.actualizarTimer();
			if(aux.getTimer()==0) {
				this.cuerpo.add(aux);
				i.remove();
			}
		}
	}
	
	public void generaComida() {
		this.comida = new Comida(dx, dy);
		if(this.campo[comida.getY()][comida.getX()].getBackground()!=Color.RED) {
			this.campo[comida.getY()][comida.getX()].setBackground(Color.BLUE);
		}
		else {
			generaComida();
		}
	}
	
	public void iniciar() {
		this.cuerpo.add(new Cuerpo((byte)2, (byte)5, (byte)0, true));
		this.cuerpo.add(new Cuerpo((byte)2, (byte)4, (byte)0, false));
		this.cuerpo.add(new Cuerpo((byte)2, (byte)3, (byte)0, false));
		this.cuerpo.add(new Cuerpo((byte)2, (byte)2, (byte)0, false));
		this.cuerpo.add(new Cuerpo((byte)2, (byte)1, (byte)0, false));
		this.cuerpo.add(new Cuerpo((byte)2, (byte)0, (byte)0, false));
		this.longitud=this.cuerpo.size();
		System.out.println("gusano iniciado");
		generaComida();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean f = true;
		while(f) {
			Jugar e = new Jugar();
		
			e.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

			
			
			if(!e.getSalir()) {
				try {
					Snake snake = new Snake((byte)e.getTxtfcasillas(), (byte)e.getTxtfcrecer(), e.getDificultad());
					snake.dispose();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			else {
				System.exit(0);
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP&&direccionFinal!=3) {
			this.direccion=1;
			System.out.println("up");
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT&&direccionFinal!=4) {
			this.direccion=2;
			System.out.println("right");
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN&&direccionFinal!=1) {
			this.direccion=3;
			System.out.println("down");
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT&&direccionFinal!=2) {
			this.direccion=4;
			System.out.println("left");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnjugar) {
			System.out.println("ye");
			this.jugar();
		}
	}

	

	
	
}
