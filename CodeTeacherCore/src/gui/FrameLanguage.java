package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class FrameLanguage extends JFrame{
	
	public FrameLanguage() {
		JComboBox<String> combo = new JComboBox<>();
		combo.addItem("PT");
		combo.addItem("EN");
		combo.addActionListener (new ActionListener () {
			  public void actionPerformed(ActionEvent e) {
				
				Locale brazilian = new Locale("en", "US");
				Locale.setDefault(brazilian);
				
			}
		});
			
		getContentPane().add(combo);
	}
	
	public static void main(String[] args) {
		FrameLanguage frame = new FrameLanguage();
		frame.pack();
		frame.setVisible(true);
	}

}
