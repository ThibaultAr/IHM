package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorChooser {
	
	private static JSlider redSlide = new JSlider(0, 255);
	private static JSlider greenSlide = new JSlider(0, 255);
	private static JSlider blueSlide = new JSlider(0, 255);
	
	private static JTextField redText = new JTextField();
	private static JTextField greenText = new JTextField();
	private static JTextField blueText = new JTextField();
	private static JTextField hexa = new JTextField();
	private static JPanel color = new JPanel();
	
	static class ChangeColorListener implements ActionListener, ChangeListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int r = Integer.parseInt(redText.getText());
			int g = Integer.parseInt(greenText.getText());
			int b = Integer.parseInt(blueText.getText());
			
			if(e.getSource().equals(redText)) redSlide.setValue(r);
			if(e.getSource().equals(greenText)) greenSlide.setValue(g);
			if(e.getSource().equals(blueText)) blueSlide.setValue(b);
			
			Color bg = new Color(r, g, b);
			hexa.setText(Integer.toHexString(bg.getRGB()));
			color.setBackground(bg);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			int r = redSlide.getValue();
			int g = greenSlide.getValue();
			int b = blueSlide.getValue();
			
			if(e.getSource().equals(redSlide)) redText.setText("" + redSlide.getValue());
			if(e.getSource().equals(greenSlide)) greenText.setText("" + greenSlide.getValue());
			if(e.getSource().equals(blueSlide)) blueText.setText("" + blueSlide.getValue());
			
			Color bg = new Color(r, g, b);
			hexa.setText(Integer.toHexString(bg.getRGB()));
			color.setBackground(bg);
		}
		
	}
	
	public static void main(String[] argv) {
		JFrame frame = new JFrame("Color chooser");
		
		
		redText.setColumns(3);
		greenText.setColumns(3);
		blueText.setColumns(3);
		hexa.setColumns(8);
		
		redText.setText("0");
		greenText.setText("0");
		blueText.setText("0");
		redSlide.setValue(0);
		greenSlide.setValue(0);
		blueSlide.setValue(0);
		
		hexa.setEditable(false);
		
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		
		color.setPreferredSize(new Dimension(100, 100));
		
		left.setLayout(new GridLayout(3, 2));
		left.add(redSlide);
		left.add(redText);
		left.add(greenSlide);
		left.add(greenText);
		left.add(blueSlide);
		left.add(blueText);
		
		right.setLayout(new GridLayout(2, 1));
		right.add(hexa);
		right.add(color);
		
		color.setBackground(Color.white);
		
		frame.setMinimumSize(new Dimension(500, 300));
		
		frame.add(left, BorderLayout.WEST);
		frame.add(right, BorderLayout.EAST);
		
		frame.pack();
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		redText.addActionListener(new ChangeColorListener());
		greenText.addActionListener(new ChangeColorListener());
		blueText.addActionListener(new ChangeColorListener());
		
		redSlide.addChangeListener(new ChangeColorListener());
		greenSlide.addChangeListener(new ChangeColorListener());
		blueSlide.addChangeListener(new ChangeColorListener());
	}
}
