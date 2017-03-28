package main;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;

import utils.ArdoiseMagique;

public class ArdoiseMagiqueMain {
	
	private static boolean stillPressed = false;
	private static ArdoiseMagique am;
	
	public static class Mouse extends MouseAdapter {
		
		private int startX, startY;
		
		public Mouse(int startX, int startY) {
			this.startX = startX;
			this.startY = startY;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				System.out.println("Clear");
				am.clear();
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			stillPressed = false;
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if(stillPressed) {
				System.out.println("Move");
			} else {
				stillPressed = true;
				am.newCurve();
			}
			am.addPoint(startX + e.getPoint().x, startY + e.getPoint().y);
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Ardoise Magique");
		am = new ArdoiseMagique();
		frame.setMinimumSize(new Dimension(800, 600));
		
		frame.add(am);		
		frame.pack();
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int startX = frame.getInsets().left;
		int startY = frame.getInsets().top;
		
		Mouse mouse = new Mouse(startX, startY);
		
		am.addMouseListener(mouse);
		am.addMouseMotionListener(mouse);
	}
}
