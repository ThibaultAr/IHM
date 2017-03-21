package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import utils.ArdoiseMagique;

public class ArdoiseMagiqueMain {
	
	private static boolean stillPressed = false;
	private static ArdoiseMagique am;
	
	public static class Mouse extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1){
				if(!stillPressed) {
					stillPressed = true;
					am.newCurve();
				}
				am.addPoint(e.getX(), e.getY());
			} else if (e.getButton() == MouseEvent.BUTTON3)
				am.clear();
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			stillPressed = false;
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		am = new ArdoiseMagique();
		
		frame.add(am);
		
		am.addMouseListener(new Mouse());
		
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
