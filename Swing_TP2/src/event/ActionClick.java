package event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class ActionClick implements ActionListener {

	protected JLabel label;
	
	public ActionClick(JLabel label) {
		this.label = label;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Click");
		this.label.setText((Integer.parseInt(this.label.getText()) + 1)+"");
	}

}
