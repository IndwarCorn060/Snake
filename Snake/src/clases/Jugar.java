package clases;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Jugar extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private JButton btnjugar, btnsalir;
	private JTextField txtfcasillas, txtfcrecer;
	private boolean salir;
	private JComboBox<String> dificultad;
	
	
	public Jugar() {
		super();
		
		this.btnjugar = new JButton("Jugar");
		this.btnsalir = new JButton("Salir");
		this.txtfcasillas = new JTextField("40",4);
		this.txtfcrecer = new JTextField("3",4);
		String[] d = {"Facil", "Medio", "Dificil", "MoonSon"};
		this.dificultad = new JComboBox<String>(d);
		this.btnjugar.addActionListener(this);
		this.btnsalir.addActionListener(this);
		this.salir = false;
		JPanel norte = new JPanel();
		JPanel sur = new JPanel();
		JPanel der = new JPanel();
		Container c = this.getContentPane();
		c.add(btnjugar, BorderLayout.CENTER);
		c.add(norte, BorderLayout.NORTH);
		norte.add(new JLabel("tamaño campo"));
		norte.add(txtfcasillas);
		norte.add(btnsalir);
		c.add(sur, BorderLayout.SOUTH);
		sur.add(new JLabel("Crecimiento por casilla"));
		sur.add(txtfcrecer);
		c.add(der, BorderLayout.EAST);
		der.add(this.dificultad);
		this.dificultad.setSelectedIndex(1);
		this.setTitle("Snake");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setSize(400,200);
		this.setModal(true);
		this.setVisible(true);
		this.pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnjugar) {
			this.dispose();
		}
		else if(e.getSource()==this.btnsalir) {
			this.salir = true;
			this.dispose();
		}
	}

	public boolean getSalir() {
		return salir;
	}

	public int getTxtfcasillas() {
		return ((Integer.parseInt(txtfcasillas.getText())<10)?10:(((Integer.parseInt(txtfcasillas.getText())>100)?100:(Integer.parseInt(txtfcasillas.getText())))));
	}

	public int getTxtfcrecer() {
		return ((Integer.parseInt(txtfcrecer.getText())<0)?1:(((Integer.parseInt(txtfcrecer.getText())>10)?10:(Integer.parseInt(txtfcrecer.getText())))));
	}
	
	public int getDificultad() {
		return this.dificultad.getSelectedIndex();
	}
	
	

}
