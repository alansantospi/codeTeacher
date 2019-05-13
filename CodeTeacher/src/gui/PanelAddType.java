package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.panel.WebPanel;

import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.ImplementsAnalyzer;
import codeteacher.analyzers.SuperClassAnalyzer;
import codeteacher.analyzers.TestSet;
import gui.component.AutoCompleteBehaviourClassPath;
import gui.component.ComponentUtils;
import utils.StringUtils;

public class PanelAddType extends WebPanel{
	private FrameTestField frame;
	private JTextField txtTypeName;
	private JTextField txtSuperclass;
	
	public PanelAddType(FrameTestField frame) {
		this.frame = frame;
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
        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        add(panel_1, BorderLayout.CENTER);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{0, 295, 0, 47, 52, 0};
        gbl_panel_1.rowHeights = new int[]{63, 0, 0, 0, 0};
        gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);
        
        JLabel lblName = new JLabel("Name");
        lblName.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.insets = new Insets(0, 0, 5, 5);
        gbc_lblName.gridx = 0;
        gbc_lblName.gridy = 0;
        panel_1.add(lblName, gbc_lblName);
        
        txtTypeName = new JTextField();
        txtTypeName.setColumns(10);
        GridBagConstraints gbc_txtTypeName = new GridBagConstraints();
        gbc_txtTypeName.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtTypeName.insets = new Insets(0, 0, 5, 5);
        gbc_txtTypeName.gridx = 1;
        gbc_txtTypeName.gridy = 0;
        panel_1.add(txtTypeName, gbc_txtTypeName);
        
        JCheckBox chkRecursive = new JCheckBox("Recursive");
        GridBagConstraints gbc_checkBox = new GridBagConstraints();
        gbc_checkBox.insets = new Insets(0, 0, 5, 5);
        gbc_checkBox.gridx = 2;
        gbc_checkBox.gridy = 0;
        panel_1.add(chkRecursive, gbc_checkBox);
        
        JCheckBox chkMatchCase = new JCheckBox("Match case");
        GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
        gbc_checkBox_1.insets = new Insets(0, 0, 5, 5);
        gbc_checkBox_1.gridx = 3;
        gbc_checkBox_1.gridy = 0;
        panel_1.add(chkMatchCase, gbc_checkBox_1);
        
        JCheckBox chkRegex = new JCheckBox("Regex");
        GridBagConstraints gbc_checkBox_2 = new GridBagConstraints();
        gbc_checkBox_2.insets = new Insets(0, 0, 5, 0);
        gbc_checkBox_2.gridx = 4;
        gbc_checkBox_2.gridy = 0;
        panel_1.add(chkRegex, gbc_checkBox_2);
        
        JLabel lblSuperclass = new JLabel("Superclass");
        lblSuperclass.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblSuperclass = new GridBagConstraints();
        gbc_lblSuperclass.insets = new Insets(0, 0, 5, 5);
        gbc_lblSuperclass.gridx = 0;
        gbc_lblSuperclass.gridy = 1;
        panel_1.add(lblSuperclass, gbc_lblSuperclass);
        
        txtSuperclass = new JTextField();
        txtSuperclass.setColumns(10);
        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
        gbc_textField_1.insets = new Insets(0, 0, 5, 5);
        gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_1.gridx = 1;
        gbc_textField_1.gridy = 1;
        panel_1.add(txtSuperclass, gbc_textField_1);
        
        AutoCompleteBehaviourClassPath autoComplete = new AutoCompleteBehaviourClassPath(txtSuperclass);

		autoComplete.setInitialPopupSize(new Dimension(txtSuperclass.getWidth() + 500, txtSuperclass.getHeight()));
        
        JPanel pnlInterfaces = new JPanel();
        GridBagConstraints gbc_panel_3 = new GridBagConstraints();
        gbc_panel_3.fill = GridBagConstraints.BOTH;
        gbc_panel_3.gridwidth = 3;
        gbc_panel_3.insets = new Insets(0, 0, 5, 5);
        gbc_panel_3.gridx = 0;
        gbc_panel_3.gridy = 2;
        panel_1.add(pnlInterfaces, gbc_panel_3);
        GridBagLayout gbl_panel_3 = new GridBagLayout();
        gbl_panel_3.columnWidths = new int[]{0, 295, 0, 0};
        gbl_panel_3.rowHeights = new int[]{0, 0};
        gbl_panel_3.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_panel_3.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        pnlInterfaces.setLayout(gbl_panel_3);
        
        JLabel lblInterfaces = new JLabel("Interfaces");
        GridBagConstraints gbc_lblInterfaces = new GridBagConstraints();
        gbc_lblInterfaces.insets = new Insets(0, 0, 0, 5);
        gbc_lblInterfaces.gridx = 0;
        gbc_lblInterfaces.gridy = 0;
        pnlInterfaces.add(lblInterfaces, gbc_lblInterfaces);
        lblInterfaces.setHorizontalAlignment(SwingConstants.LEFT);
        
        DefaultTableModel model = new DefaultTableModel();
//		tableExceptions.setToolTipText(I18N.getVal(""));
		model.addColumn("Name");
		model.addColumn("Value");
		
        JPanel pnlExceptions = new PanelException(model);

		JScrollPane scrollPane = new JScrollPane();
		pnlExceptions.add(scrollPane);
        pnlInterfaces.add(pnlExceptions);
        
        JButton btnOk = new JButton("Ok");
        btnOk.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		boolean recursive = chkRecursive.isSelected();
        		boolean matchCase = chkMatchCase.isSelected();
				boolean regex = chkRegex.isSelected();
				String className = txtTypeName.getText();
				int value = 0;
//				Object value = spinValue.getValue();
				
				ClassAnalyzer classAnalyzer = new ClassAnalyzer(className, recursive, matchCase, regex, value);
				DefaultMutableTreeNode node = frame.addToTree(classAnalyzer);
				
				String superClassName = txtSuperclass.getText();
				if (!StringUtils.isEmpty(superClassName)) {
					int superClassValue = 0;
					SuperClassAnalyzer superClassAnalyzer = new SuperClassAnalyzer(classAnalyzer, superClassName, superClassValue);
					frame.addToTree(node, superClassAnalyzer);
				}
				
				for (int row = 0; row < model.getRowCount(); row++) {
					String interfaceName = model.getValueAt(row, 0).toString();
					String interfaceValue = model.getValueAt(row, 1).toString();
					Float floatValue = Float.valueOf(interfaceValue);
					
					ImplementsAnalyzer implementsAnalyzer = new ImplementsAnalyzer(classAnalyzer, interfaceName, floatValue.intValue());
					frame.addToTree(node, implementsAnalyzer);
				}				
				
				
        	}
        });
        GridBagConstraints gbc_btnOk = new GridBagConstraints();
        gbc_btnOk.insets = new Insets(0, 0, 0, 5);
        gbc_btnOk.gridx = 3;
        gbc_btnOk.gridy = 3;
        panel_1.add(btnOk, gbc_btnOk);
	}

}
