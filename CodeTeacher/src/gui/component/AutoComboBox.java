package gui.component;

import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.Document;

public class AutoComboBox extends JComboBox<Object> {

	String keyWord[] = { "item1", "item2", "item3" };
	Vector myVector = new Vector();
	private JTextField text;

	public AutoComboBox() {

		setModel(new DefaultComboBoxModel(myVector));
		setSelectedIndex(-1);
		setEditable(true);
		text = (JTextField) this.getEditor().getEditorComponent();
		text.setFocusable(true);
		text.setText("");
		text.addKeyListener(new ComboListener(this, myVector));
		setMyVector();
	}

	/**
	 *
	 * 
	 * set the item list of the AutoComboBox*
	 * 
	 * @param keyWord an String array
	 */

	public void setKeyWord(String[] keyWord) {
		this.keyWord = keyWord;
		setMyVectorInitial();
	}

	private void setMyVector() {
		int a;
		for (a = 0; a < keyWord.length; a++) {
			myVector.add(keyWord[a]);
		}
	}

	private void setMyVectorInitial() {
		myVector.clear();
		int a;
		for (a = 0; a < keyWord.length; a++) {

			myVector.add(keyWord[a]);
		}
	}
	
	public String getText() {
		return text.getText();
	}

	public Document getDocument() {
		return text.getDocument();
	}

}

