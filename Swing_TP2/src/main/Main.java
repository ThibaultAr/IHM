package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {

	private static JLabel label;
	
	private static class CloseWindow extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("Fermeture en cours");
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		label = new JLabel("0");
		JButton incr = new JButton("Incrémenter");
		
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		incr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Click");
				label.setText((Integer.parseInt(label.getText()) + 1)+"");
			}
			
		});
		
		frame.add(label);
		frame.add(incr);
		
		frame.setVisible(true);
		frame.pack();
		frame.addWindowListener(new CloseWindow());
	}
}