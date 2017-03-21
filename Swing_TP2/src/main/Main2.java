package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main2 {
	private static JLabel label;

	private static class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			label.setText(((JButton) e.getSource()).getText());
		}
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		label = new JLabel("Hello");
		JButton b1 = new JButton("Button 1");
		JButton b2 = new JButton("Button 2");
		JButton b3 = new JButton("Button 3");
		
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		b1.addActionListener(new ButtonListener());
		b2.addActionListener(new ButtonListener());
		b3.addActionListener(new ButtonListener());
		frame.add(label);
		frame.add(b1);
		frame.add(b2);
		frame.add(b3);
		
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
