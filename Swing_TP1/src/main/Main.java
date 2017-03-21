package main;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JMenuBar menu = new JMenuBar();
		JMenu fichier = new JMenu("Fichier");
		JMenu edition = new JMenu("Edition");
		JMenu format = new JMenu("Format");

		JMenuItem nouveau = new JMenuItem("Nouveau");
		JMenuItem ouvrir = new JMenuItem("Ouvrir");
		JMenuItem enregistrer = new JMenuItem("Enregistrer");
		JMenuItem mep = new JMenuItem("Mise en page");
		JMenuItem imprimer = new JMenuItem("Imprimer");
		JMenuItem quitter = new JMenuItem("Quitter");
		
		JTextArea area = new JTextArea();
		
		JLabel recherche = new JLabel("Recherche : ");
		JTextField field = new JTextField();
		JButton bLeft = new JButton("<-");
		JButton bright = new JButton("->");
		JButton surligner = new JButton("Tout surligner");
		
		frame.getContentPane().setLayout(new BorderLayout());
		
		fichier.add(nouveau);
		fichier.add(ouvrir);
		fichier.add(enregistrer);
		fichier.addSeparator();
		fichier.add(mep);
		fichier.add(imprimer);
		fichier.addSeparator();
		fichier.add(quitter);
		
		menu.add(fichier);
		menu.add(edition);
		menu.add(format);

		field.setColumns(10);
		panel.add(recherche);
		panel.add(field);
		panel.add(bLeft);
		panel.add(bright);
		panel.add(surligner);
		
		frame.setJMenuBar(menu);
		frame.add(area, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.SOUTH);
		
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
