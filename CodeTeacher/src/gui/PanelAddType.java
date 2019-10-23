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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;

import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.ImplementsAnalyzer;
import codeteacher.analyzers.MethodAnalyzer;
import codeteacher.analyzers.SuperClassAnalyzer;
import codeteacher.behave.ExecutorImpl;
import gui.component.AutoCompleteBehaviourClassPath;
import gui.component.table.ExecutorImplModel;
import gui.component.table.FieldAnalyzerModel;
import gui.component.table.ImplementsAnalyzerModel;
import gui.component.table.MethodAnalyzerModel;
import utils.StringUtils;

public class PanelAddType extends WebPanel {
	private FrameTestField frame;
	private JTextField txtTypeName;
	private JTextField txtSuperclass;
	private JCheckBox chkRecursive;
	private JCheckBox chkMatchCase;
	private JCheckBox chkRegex;
	private DefaultTableModel model;
	private JPanel pnlAnalyzers;
	private PanelAddAnalyzer<FieldAnalyzer> pnlFields;
	private PanelAddAnalyzer<ImplementsAnalyzer> pnlImplements;
	private PanelAddAnalyzer<MethodAnalyzer>  pnlMethods;

	public PanelAddType(FrameTestField frame) {
		this.frame = frame;
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0 };
		gbl_panel.rowHeights = new int[] { 0 };
		gbl_panel.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		// Text buttons group
		WebToggleButton btnClass = new WebToggleButton("Class");
		WebToggleButton btnInterface = new WebToggleButton("Interface");
		WebButtonGroup textGroup = new WebButtonGroup(true, btnClass, btnInterface);
		textGroup.setButtonsDrawFocus(true);

		panel.add(textGroup);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 295, 0, 47, 52, 0 };
		gbl_panel_1.rowHeights = new int[] { 63, 0, 0, -6, 23, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
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

		chkRecursive = new JCheckBox("Recursive");
		GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox.gridx = 2;
		gbc_checkBox.gridy = 0;
		panel_1.add(chkRecursive, gbc_checkBox);

		chkMatchCase = new JCheckBox("Match case");
		GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
		gbc_checkBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_1.gridx = 3;
		gbc_checkBox_1.gridy = 0;
		panel_1.add(chkMatchCase, gbc_checkBox_1);

		chkRegex = new JCheckBox("Regex");
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

		pnlAnalyzers = new JPanel();
		GridBagConstraints gbc_pnlAnalyzers = new GridBagConstraints();
		gbc_pnlAnalyzers.fill = GridBagConstraints.BOTH;
		gbc_pnlAnalyzers.gridwidth = 3;
		gbc_pnlAnalyzers.insets = new Insets(0, 0, 5, 5);
		gbc_pnlAnalyzers.gridx = 0;
		gbc_pnlAnalyzers.gridy = 2;
		panel_1.add(pnlAnalyzers, gbc_pnlAnalyzers);
		GridBagLayout gbl_pnlAnalyzers = new GridBagLayout();
		gbl_pnlAnalyzers.columnWidths = new int[] { 0, 295, 0, 0 };
		gbl_pnlAnalyzers.rowHeights = new int[] { 0, 0 };
		gbl_pnlAnalyzers.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_pnlAnalyzers.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlAnalyzers.setLayout(gbl_pnlAnalyzers);

		JLabel lblInterfaces = new JLabel("Interfaces");
		GridBagConstraints gbc_lblInterfaces = new GridBagConstraints();
		gbc_lblInterfaces.insets = new Insets(0, 0, 0, 5);
		gbc_lblInterfaces.gridx = 0;
		gbc_lblInterfaces.gridy = 0;
		pnlAnalyzers.add(lblInterfaces, gbc_lblInterfaces);
		lblInterfaces.setHorizontalAlignment(SwingConstants.LEFT);

		model = new DefaultTableModel();
//		tableExceptions.setToolTipText(I18N.getVal(""));
		model.addColumn("Name");
		model.addColumn("Value");

		pnlImplements = new PanelAddAnalyzer(ImplementsAnalyzer.class);
		GridBagConstraints gbc_pnlImplements = new GridBagConstraints();
		gbc_pnlImplements.insets = new Insets(0, 0, 5, 5);
		gbc_pnlImplements.gridx = 0;
		gbc_pnlImplements.gridy = 1;
		pnlAnalyzers.add(pnlImplements, gbc_pnlImplements);

		pnlImplements.btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WebFrame frame = new WebFrame();
				PanelAddException contentPane = new PanelAddException(pnlImplements);
				frame.setContentPane(contentPane);
				
				contentPane.addAction(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ImplementsAnalyzer analyzer = contentPane.createAnalyzer();
						pnlImplements.addAnalyzer(analyzer);
						pnlImplements.updateButtons();
					}
				});
				
				frame.pack();
				frame.center();
				frame.setVisible(true);
			}
		});

		JPanel pnlAnalyzers2 = new JPanel();
		GridBagConstraints gbc_pnlInterfaces2 = new GridBagConstraints();
		gbc_pnlInterfaces2.fill = GridBagConstraints.BOTH;
		gbc_pnlInterfaces2.gridwidth = 3;
		gbc_pnlInterfaces2.insets = new Insets(0, 0, 5, 5);
		gbc_pnlInterfaces2.gridx = 0;
		gbc_pnlInterfaces2.gridy = 3;
		panel_1.add(pnlAnalyzers2, gbc_pnlInterfaces2);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 295, 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlAnalyzers2.setLayout(gbl_panel_4);

		JLabel label = new JLabel("Fields");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_labelFields = new GridBagConstraints();
		gbc_labelFields.insets = new Insets(0, 0, 5, 5);
		gbc_labelFields.gridx = 0;
		gbc_labelFields.gridy = 0;
		pnlAnalyzers2.add(label, gbc_labelFields);
		label.setHorizontalAlignment(SwingConstants.LEFT);

		pnlFields = new PanelAddAnalyzer<>(FieldAnalyzer.class);
		pnlFields.btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WebFrame frame = new WebFrame();
				ClassAnalyzer classAnalyzer = createAnalyzer();
				PanelAddField2 pnlAddField = new PanelAddField2(classAnalyzer, pnlFields);
				frame.setContentPane(pnlAddField);
				frame.pack();
				frame.center();
				frame.setVisible(true);
			}
		});

		GridBagConstraints gbc_panelFields = new GridBagConstraints();
		gbc_panelFields.insets = new Insets(0, 0, 5, 5);
		gbc_panelFields.gridx = 0;
		gbc_panelFields.gridy = 1;
		pnlAnalyzers2.add(pnlFields, gbc_panelFields);

		JPanel pnlAnalyzers3 = new JPanel();
		GridBagConstraints gbc_pnlAnalyzers3 = new GridBagConstraints();
		gbc_pnlAnalyzers3.fill = GridBagConstraints.BOTH;
		gbc_pnlAnalyzers3.gridwidth = 4;
		gbc_pnlAnalyzers3.insets = new Insets(0, 0, 5, 5);
		gbc_pnlAnalyzers3.gridx = 0;
		gbc_pnlAnalyzers3.gridy = 4;
		panel_1.add(pnlAnalyzers3, gbc_pnlAnalyzers3);
		GridBagLayout gbl_pnlAnalyzers3 = new GridBagLayout();
		gbl_pnlAnalyzers3.columnWidths = new int[] { 0, 295, 0, 0 };
		gbl_pnlAnalyzers3.rowHeights = new int[] { 0, 0 };
		gbl_pnlAnalyzers3.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_pnlAnalyzers3.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlAnalyzers3.setLayout(gbl_pnlAnalyzers3);

		JLabel label3 = new JLabel("Methods");
		label3.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_label3 = new GridBagConstraints();
		gbc_label3.insets = new Insets(0, 0, 5, 5);
		gbc_label3.gridx = 0;
		gbc_label3.gridy = 0;
		pnlAnalyzers3.add(label3, gbc_label3);

		pnlMethods = new PanelAddAnalyzer<>(MethodAnalyzer.class);
		pnlMethods.btnAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WebFrame frame = new WebFrame();
				ClassAnalyzer classAnalyzer = createAnalyzer();
				
				PanelAddMethod pnlAddMethod = new PanelAddMethod();
				pnlAddMethod.addAction(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						MethodAnalyzer analyzer = pnlAddMethod.createAnalyzer();
						pnlMethods.addAnalyzer(analyzer);						
					}
				});
				
				frame.setContentPane(pnlAddMethod);
				frame.pack();
				frame.center();
				frame.setVisible(true);
			}
		});
		
		GridBagConstraints gbc_pnlMethods = new GridBagConstraints();
		gbc_pnlMethods.insets = new Insets(0, 0, 5, 5);
		gbc_pnlMethods.gridx = 0;
		gbc_pnlMethods.gridy = 1;
		pnlAnalyzers3.add(pnlMethods, gbc_pnlMethods);

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ClassAnalyzer analyzer = createAnalyzer();

				frame.addToTree(analyzer);

			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 3;
		gbc_btnOk.gridy = 5;
		panel_1.add(btnOk, gbc_btnOk);
		
		JButton btnOkTest = new JButton("Ok Test");
		btnOkTest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ClassAnalyzer analyzer = createAnalyzer();

				frame.addToTree(analyzer);

			}
		});
		GridBagConstraints gbc_btnOkTest = new GridBagConstraints();
		gbc_btnOkTest.insets = new Insets(0, 0, 0, 5);
		gbc_btnOkTest.gridx = 4;
		gbc_btnOkTest.gridy = 5;
		panel_1.add(btnOkTest, gbc_btnOkTest);
	}

	public ClassAnalyzer createAnalyzer() {
		boolean recursive = chkRecursive.isSelected();
		boolean matchCase = chkMatchCase.isSelected();
		boolean regex = chkRegex.isSelected();
		String className = txtTypeName.getText();
		int value = 0;
//		Object value = spinValue.getValue();

		ClassAnalyzer classAnalyzer = new ClassAnalyzer(className, recursive, matchCase, regex, value);
//		DefaultMutableTreeNode node = frame.addToTree(classAnalyzer);

		String superClassName = txtSuperclass.getText();
		if (!StringUtils.isEmpty(superClassName)) {
			int superClassValue = 0;
			SuperClassAnalyzer superClassAnalyzer = new SuperClassAnalyzer(classAnalyzer, superClassName,
					superClassValue);
//			frame.addToTree(node, superClassAnalyzer);
			classAnalyzer.add(superClassAnalyzer);
		}

		ImplementsAnalyzerModel implementsModel = (ImplementsAnalyzerModel) pnlImplements.getModel();
		for (int i = 0; i < implementsModel.getRowCount(); i++) {
			ImplementsAnalyzer row = implementsModel.getRow(i);
			classAnalyzer.add(row);
		}
		
		FieldAnalyzerModel fieldModel = (FieldAnalyzerModel) pnlFields.getModel();
		for (int i = 0; i < fieldModel.getRowCount(); i++) {
			FieldAnalyzer row = fieldModel.getRow(i);
			classAnalyzer.add(row);
		}
		
		MethodAnalyzerModel methodModel = (MethodAnalyzerModel) pnlMethods.getModel();
		for (int i = 0; i < methodModel.getRowCount(); i++) {
			MethodAnalyzer row = methodModel.getRow(i);
			classAnalyzer.add(row);
		}
		return classAnalyzer;
	}
	
	public class AddAnalyzerAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			boolean recursive = chkRecursive.isSelected();
			boolean matchCase = chkMatchCase.isSelected();
			boolean regex = chkRegex.isSelected();
			String className = txtTypeName.getText();
			int value = 0;
//			Object value = spinValue.getValue();

			ClassAnalyzer classAnalyzer = new ClassAnalyzer(className, recursive, matchCase, regex, value);
			DefaultMutableTreeNode node = frame.addToTree(classAnalyzer);

			String superClassName = txtSuperclass.getText();
			if (!StringUtils.isEmpty(superClassName)) {
				int superClassValue = 0;
				SuperClassAnalyzer superClassAnalyzer = new SuperClassAnalyzer(classAnalyzer, superClassName,
						superClassValue);
				frame.addToTree(node, superClassAnalyzer);
			}

			for (int row = 0; row < model.getRowCount(); row++) {
				String interfaceName = model.getValueAt(row, 0).toString();
				String interfaceValue = model.getValueAt(row, 1).toString();
				Float floatValue = Float.valueOf(interfaceValue);

				ImplementsAnalyzer implementsAnalyzer = new ImplementsAnalyzer(classAnalyzer, interfaceName,
						floatValue.intValue());
				frame.addToTree(node, implementsAnalyzer);
			}
		}
	}
}
