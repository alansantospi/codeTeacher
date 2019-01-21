package gui.component;

import java.awt.Color;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import utils.ClassPathUtils;

public class ClassPathSuggestor extends AutoSuggestor {
	
	public ClassPathSuggestor(JTextField textField, Window mainWindow, ArrayList<String> words,
			Color popUpBackground, Color textColor, Color suggestionFocusedColor, float opacity) {
		super(textField, mainWindow, words, popUpBackground, textColor, suggestionFocusedColor, opacity);
	}
	
	// AutoSuggestor(f, frame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED,
	// 0.75f) {
	@Override
	public boolean wordTyped(String typedWord) {
		// create list for dictionary this in your case might be done via calling a
		// method which queries db and returns results as arraylist
		ArrayList<String> words = new ArrayList<>();
		List<String> wordList = ClassPathUtils.match(typedWord);
		words.addAll(wordList);
		
		System.out.println(typedWord);

		setDictionary(words);
		// addToDictionary("bye");//adds a single word
		
		return super.wordTyped(typedWord);// now call super to check for any matches against newest dictionary
	}
	
}