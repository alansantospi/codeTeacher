package gui;

import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.panel.WebPanel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;

public class PanelAddType extends WebPanel{
	
	public PanelAddType() {
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0};
		gbl_panel.rowHeights = new int[]{0};
		gbl_panel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		// Text buttons group
        WebToggleButton btnClass = new WebToggleButton ( "Class" );
        WebToggleButton btnInterface = new WebToggleButton ( "Interface" );
        WebButtonGroup textGroup = new WebButtonGroup ( true, btnClass, btnInterface );
        textGroup.setButtonsDrawFocus ( true );
        
        panel.add(textGroup);
        
//        add(textGroup);
	}

}
