package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.alee.laf.WebLookAndFeel;

import gui.msg.FrameTestMsg;
import gui.msg.I18N;

public class CodeTeacherApp {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void mainX(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					// Default demo language for now
//			        LanguageManager.setDefaultLanguage ( LanguageManager.ENGLISH );

			        // Demo application skin
			        // It extends default WebLaF skin and adds some custom styling
//			        StyleManager.setDefaultSkin ( WebLafDemoSkin.class.getCanonicalName () );

			        // Look and Feel
			        WebLookAndFeel.install ();
//			        WebLookAndFeel.initializeManagers ();

			        CodeTeacherApp window = new CodeTeacherApp();
			        
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
		frame = new FrameTest();
		frame.setBounds(100, 100, 450, 300);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		// Its very important to call this before setting Look & Feel
		I18N.getVal(FrameTestMsg.ADD_BEHAVIOR);
		
		 // Look and Feel
        WebLookAndFeel.install ();
//        WebLookAndFeel.initializeManagers ();

        CodeTeacherApp window = new CodeTeacherApp();
        
		window.frame.setVisible(true);
		
	}

}
