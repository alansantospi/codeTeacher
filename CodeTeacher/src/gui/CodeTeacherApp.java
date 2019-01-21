package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.alee.laf.WebLookAndFeel;
import com.alee.managers.language.LanguageManager;
import com.alee.managers.style.StyleManager;

public class CodeTeacherApp {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					// Default demo language for now
//			        LanguageManager.setDefaultLanguage ( LanguageManager.ENGLISH );

			        // Demo application skin
			        // It extends default WebLaF skin and adds some custom styling
//			        StyleManager.setDefaultSkin ( WebLafDemoSkin.class.getCanonicalName () );

			        // Look and Feel
//			        WebLookAndFeel.install ();
//			        WebLookAndFeel.initializeManagers ();

			        CodeTeacherApp window = new CodeTeacherApp();
			        window.frame = new FrameTest();
			        
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CodeTeacherApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
