package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;

import gui.FrameAddParam.ChangeListener;
import utils.ClassPathUtils;

public class AutoComplete extends JComboBox implements JComboBox.KeySelectionManager {
	private String searchFor;
	private long lap;

	public class CBDocument extends PlainDocument {
		public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
			if (str == null)
				return;
			super.insertString(offset, str, a);
			if (!isPopupVisible() && str.length() != 0)
				fireActionEvent();
		}
	}

	public AutoComplete(Object[] items) {
		super(items);
		lap = new java.util.Date().getTime();
		setKeySelectionManager(this);
		JTextField tf;
		if (getEditor() != null) {
			tf = (JTextField) getEditor().getEditorComponent();
			if (tf != null) {
				tf.setDocument(new CBDocument());
//				addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent evt) {
//						JTextField tf = (JTextField) getEditor().getEditorComponent();
//						String text = tf.getText();
//						ComboBoxModel aModel = getModel();
//						String current;
//						for (int i = 0; i < aModel.getSize(); i++) {
//							current = aModel.getElementAt(i).toString();
//							if (current.toLowerCase().startsWith(text.toLowerCase())) {
//								tf.setText(current);
//								tf.setSelectionStart(text.length());
//								tf.setSelectionEnd(current.length());
//								break;
//							}
//						}
//					}
//				});
				
				tf.getDocument().addDocumentListener(new ChangeListener());
				
			}
		}
	}

	public void updateList(){
		JTextField textParam = (JTextField) getEditor().getEditorComponent();
		String pattern = textParam.getText();
		DefaultComboBoxModel<String> listModel = new DefaultComboBoxModel<String>();

		if (!pattern.equals("")){
			List<String> match = ClassPathUtils.match(pattern);
			for (String value : match) {
				listModel.addElement(value);
			}
		}
		 setModel(listModel);
	}
	
	public int selectionForKey(char aKey, ComboBoxModel aModel) {
		long now = new java.util.Date().getTime();
		if (searchFor != null && aKey == KeyEvent.VK_BACK_SPACE && searchFor.length() > 0) {
			searchFor = searchFor.substring(0, searchFor.length() - 1);
		} else {
			// System.out.println(lap);
			// Kam nie hier vorbei.
			if (lap + 1000 < now)
				searchFor = "" + aKey;
			else
				searchFor = searchFor + aKey;
		}
		lap = now;
		String current;
		for (int i = 0; i < aModel.getSize(); i++) {
			current = aModel.getElementAt(i).toString().toLowerCase();
			if (current.toLowerCase().startsWith(searchFor.toLowerCase()))
				return i;
		}
		return -1;
	}

	public void fireActionEvent() {
		super.fireActionEvent();
	}

	public static void main(String arg[]) {
		JFrame f = new JFrame("AutoCompleteComboBox");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(200, 300);
		Container cp = f.getContentPane();
		cp.setLayout(null);
		// String[] names= {"Beate", "Claudia", "Fjodor", "Fred", "Friedrich", "Fritz",
		// "Frodo", "Hermann", "Willi"};
		// JComboBox cBox= new AutoComplete(names);
		Locale[] locales = Locale.getAvailableLocales();//

		List<String> match = ClassPathUtils.match("");
		JComboBox cBox = new AutoComplete(match.toArray());
		cBox.setBounds(50, 50, 100, 21);
		cBox.setEditable(true);
		cp.add(cBox);
		f.setVisible(true);
	}
	
	public class ChangeListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			updateList();			
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			updateList();	
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			updateList();	
		}
	}
}