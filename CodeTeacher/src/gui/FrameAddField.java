package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.BiPredicate;

import javax.swing.AbstractSpinnerModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebEditorPane;
import com.alee.laf.text.WebTextField;

import codeteacher.analyzers.AbstractAnalyzer;
import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.CompositeAnalyzer;
import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.FieldModifierAnalyzer;
import codeteacher.analyzers.FinalFieldAnalyzer;
import codeteacher.analyzers.PrivateFieldAnalyzer;
import codeteacher.analyzers.ProtectedFieldAnalyzer;
import codeteacher.analyzers.PublicFieldAnalyzer;
import codeteacher.analyzers.StaticFieldAnalyzer;
import codeteacher.result.Performance;
import gui.component.AutoComboBox;
import gui.component.ComponentUtils;
import gui.component.jtreefiltering.example.JTreeUtil;
import gui.component.jtreefiltering.example.TradingProjectTreeRenderer;
import gui.component.jtreefiltering.example.TreeFilterDecorator;
import gui.msg.FrameAddOutputMsg;
import gui.msg.FrameTestMsg;
import gui.msg.I18N;

public class FrameAddField extends WebFrame implements ActionListener {

	private static FrameAddOutputMsg msg = FrameAddOutputMsg.TITLE;

	private boolean editMode;

	private FrameAddField thisFrame;
	private WebPanel contentPane;

	/* Class */
	private WebPanel pnlClass;
	private WebLabel lblClassname;
	private WebTextField txtClassName;
	private WebPanel pnlValue;
	private WebLabel lblValue;
	private WebTextField txtValue;
	private WebCheckBox chkClassRegex;
	private WebCheckBox chkClassCase;
	private WebCheckBox chkClassRecursive;

	/* Fields */
	private WebPanel pnlFields;
	private WebLabel lblFieldModifier;
	private WebLabel lblFieldType;
	private WebLabel lblFieldName;
	private WebComboBox comboFieldModifier;
	private WebTextField txtFieldType;
	private WebTextField txtFieldName;
	private WebCheckBox chkFieldStatic;
	private WebCheckBox chkFieldFinal;
	private WebCheckBox chkFieldMatchCase;
	private WebCheckBox chkFieldRegex;
	private JScrollPane scrollPane;
	private WebTable tableFields;

	private AutoComboBox autoComboBox;
	private WebEditorPane editorPane;
	private WebButton btnAddField;
	private WebButton btnRemoveField;

	/* new features */
	private static String ADD_COMMAND = "add";
	private static String REMOVE_COMMAND = "remove";
	private static String CLEAR_COMMAND = "clear";

	private DynamicWebCheckBoxTree checkBoxTree;
	private DefaultMutableTreeNode rootNode;

	private WebPanel pnlTestButtons;
	private WebButton btnRun;

	public FrameAddField() {

		this.thisFrame = this;

		contentPane = new WebPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 87, 208, 30, 199, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 33, 49, 51, 93, 61, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0 };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		pnlValue = new WebPanel();
		GridBagConstraints gbc_Value = new GridBagConstraints();
		gbc_Value.anchor = GridBagConstraints.EAST;
		gbc_Value.gridwidth = 6;
		gbc_Value.insets = new Insets(0, 0, 5, 5);
		gbc_Value.fill = GridBagConstraints.VERTICAL;
		gbc_Value.gridx = 0;
		gbc_Value.gridy = 0;
		contentPane.add(pnlValue, gbc_Value);
		GridBagLayout gbl_Value = new GridBagLayout();
		gbl_Value.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 51, 0 };
		gbl_Value.rowHeights = new int[] { 0, 0 };
		gbl_Value.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_Value.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlValue.setLayout(gbl_Value);

		lblValue = new WebLabel("Value");
		GridBagConstraints gbc_lblValue = new GridBagConstraints();
		gbc_lblValue.insets = new Insets(0, 0, 0, 5);
		gbc_lblValue.gridx = 16;
		gbc_lblValue.gridy = 0;
		pnlValue.add(lblValue, gbc_lblValue);

		txtValue = new WebTextField();
		txtValue.setColumns(10);
		GridBagConstraints gbc_txtValue = new GridBagConstraints();
		gbc_txtValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValue.gridx = 17;
		gbc_txtValue.gridy = 0;
		pnlValue.add(txtValue, gbc_txtValue);

		pnlClass = new WebPanel();
		pnlClass.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlClass = new GridBagConstraints();
		gbc_pnlClass.gridwidth = 6;
		gbc_pnlClass.insets = new Insets(0, 0, 5, 0);
		gbc_pnlClass.fill = GridBagConstraints.BOTH;
		gbc_pnlClass.gridx = 0;
		gbc_pnlClass.gridy = 1;
		contentPane.add(pnlClass, gbc_pnlClass);
		GridBagLayout gbl_pnlClass = new GridBagLayout();
		gbl_pnlClass.columnWidths = new int[] { 0, 295, 0, 0, 47, 52, 0 };
		gbl_pnlClass.rowHeights = new int[] { 63, 0 };
		gbl_pnlClass.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlClass.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlClass.setLayout(gbl_pnlClass);

		lblClassname = new WebLabel("Class name");
		lblClassname.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblClassname = new GridBagConstraints();
		gbc_lblClassname.insets = new Insets(0, 0, 0, 5);
		gbc_lblClassname.gridx = 0;
		gbc_lblClassname.gridy = 0;
		pnlClass.add(lblClassname, gbc_lblClassname);

		txtClassName = new WebTextField();
		txtClassName.setColumns(10);
		GridBagConstraints gbc_txtClassName = new GridBagConstraints();
		gbc_txtClassName.insets = new Insets(0, 0, 0, 5);
		gbc_txtClassName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtClassName.gridx = 1;
		gbc_txtClassName.gridy = 0;
		pnlClass.add(txtClassName, gbc_txtClassName);

		chkClassRecursive = new WebCheckBox(I18N.getVal(msg.RECURSIVE));
		GridBagConstraints gbc_chkClassRecursive = new GridBagConstraints();
		gbc_chkClassRecursive.insets = new Insets(0, 0, 0, 5);
		gbc_chkClassRecursive.gridx = 2;
		gbc_chkClassRecursive.gridy = 0;
		pnlClass.add(chkClassRecursive, gbc_chkClassRecursive);

		chkClassCase = new WebCheckBox(I18N.getVal(msg.CASE_SENSITIVE));
		GridBagConstraints gbc_chkClassMatchCase = new GridBagConstraints();
		gbc_chkClassMatchCase.insets = new Insets(0, 0, 0, 5);
		gbc_chkClassMatchCase.gridx = 4;
		gbc_chkClassMatchCase.gridy = 0;
		pnlClass.add(chkClassCase, gbc_chkClassMatchCase);

		chkClassRegex = new WebCheckBox(I18N.getVal(msg.REGEX));
		GridBagConstraints gbc_chkClassRegex = new GridBagConstraints();
		gbc_chkClassRegex.gridx = 5;
		gbc_chkClassRegex.gridy = 0;
		pnlClass.add(chkClassRegex, gbc_chkClassRegex);

		pnlFields = new WebPanel();
		pnlFields.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlFields = new GridBagConstraints();
		gbc_pnlFields.gridheight = 2;
		gbc_pnlFields.gridwidth = 6;
		gbc_pnlFields.insets = new Insets(0, 0, 5, 0);
		gbc_pnlFields.fill = GridBagConstraints.BOTH;
		gbc_pnlFields.gridx = 0;
		gbc_pnlFields.gridy = 2;
		contentPane.add(pnlFields, gbc_pnlFields);
//		contentPane.add(pnlFields);
		GridBagLayout gbl_pnlFields = new GridBagLayout();
		gbl_pnlFields.columnWidths = new int[] { 0, 0, 0, 0, 119, 0, 7, 0, 20, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlFields.rowHeights = new int[] { 0, 36, 31, 0, 0 };
		gbl_pnlFields.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlFields.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		pnlFields.setLayout(gbl_pnlFields);

		lblFieldModifier = new WebLabel(I18N.getVal(msg.ACCESS_MODIFIER));
		GridBagConstraints gbc_lblFieldModifier = new GridBagConstraints();
		gbc_lblFieldModifier.anchor = GridBagConstraints.WEST;
		gbc_lblFieldModifier.gridwidth = 2;
		gbc_lblFieldModifier.insets = new Insets(0, 0, 5, 5);
		gbc_lblFieldModifier.gridx = 0;
		gbc_lblFieldModifier.gridy = 0;
		pnlFields.add(lblFieldModifier, gbc_lblFieldModifier);

		lblFieldType = new WebLabel(I18N.getVal(msg.FIELD_TYPE));
		GridBagConstraints gbc_lblFieldType = new GridBagConstraints();
		gbc_lblFieldType.anchor = GridBagConstraints.WEST;
		gbc_lblFieldType.insets = new Insets(0, 0, 5, 5);
		gbc_lblFieldType.gridx = 1;
		gbc_lblFieldType.gridy = 0;
		pnlFields.add(lblFieldType, gbc_lblFieldType);

		lblFieldName = new WebLabel(I18N.getVal(msg.FIELD_NAME));
		GridBagConstraints gbc_lblFieldName = new GridBagConstraints();
		gbc_lblFieldName.anchor = GridBagConstraints.WEST;
		gbc_lblFieldName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFieldName.gridx = 7;
		gbc_lblFieldName.gridy = 0;
		pnlFields.add(lblFieldName, gbc_lblFieldName);

		comboFieldModifier = new WebComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 1;
		comboFieldModifier.setModel(new DefaultComboBoxModel(new String[] { I18N.getVal(msg.PRIVATE),
				I18N.getVal(msg.PUBLIC), I18N.getVal(msg.PROTECTED), I18N.getVal(msg.DEFAULT) }));
		pnlFields.add(comboFieldModifier, gbc_comboBox);

		GridBagConstraints gbc_txtFieldType = new GridBagConstraints();
		gbc_txtFieldType.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldType.gridwidth = 6;
		gbc_txtFieldType.insets = new Insets(0, 0, 5, 5);
		gbc_txtFieldType.gridx = 1;
		gbc_txtFieldType.gridy = 1;

		txtFieldType = new WebTextField();
		txtFieldType.setColumns(10);
		pnlFields.add(txtFieldType, gbc_txtFieldType);

//		createAutoSuggestor(txtFieldType, thisFrame);
//		createAutoComboBox(gbc_txtFieldType);
//		createAutoComplete(txtFieldType);
//		AutoCompleteBehaviourClassPath autoComplete = new AutoCompleteBehaviourClassPath(txtFieldType);

//		autoComplete.setInitialPopupSize(new Dimension(txtFieldType.getWidth()+500, txtFieldType.getHeight()));

		txtFieldName = new WebTextField();
		txtFieldName.setColumns(10);
		GridBagConstraints gbc_txtFieldName = new GridBagConstraints();
		gbc_txtFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldName.gridwidth = 8;
		gbc_txtFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_txtFieldName.gridx = 7;
		gbc_txtFieldName.gridy = 1;
		pnlFields.add(txtFieldName, gbc_txtFieldName);

		chkFieldStatic = new WebCheckBox(I18N.getVal(msg.STATIC));
		chkFieldStatic.setHorizontalAlignment(SwingConstants.LEFT);
		chkFieldStatic.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GridBagConstraints gbc_chkFieldStatic = new GridBagConstraints();
		gbc_chkFieldStatic.insets = new Insets(0, 0, 5, 5);
		gbc_chkFieldStatic.gridx = 1;
		gbc_chkFieldStatic.gridy = 2;
		pnlFields.add(chkFieldStatic, gbc_chkFieldStatic);

		chkFieldFinal = new WebCheckBox(I18N.getVal(msg.FINAL));
		chkFieldFinal.setHorizontalAlignment(SwingConstants.LEFT);
		chkFieldFinal.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GridBagConstraints gbc_chkFieldFinal = new GridBagConstraints();
		gbc_chkFieldFinal.insets = new Insets(0, 0, 5, 5);
		gbc_chkFieldFinal.gridx = 2;
		gbc_chkFieldFinal.gridy = 2;
		pnlFields.add(chkFieldFinal, gbc_chkFieldFinal);

		btnAddField = new WebButton(I18N.getVal(msg.ADD));
		btnAddField.addActionListener(new AddFieldListener());
		GridBagConstraints gbc_btnAddField = new GridBagConstraints();
		gbc_btnAddField.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddField.gridx = 4;
		gbc_btnAddField.gridy = 2;
		pnlFields.add(btnAddField, gbc_btnAddField);

		btnRemoveField = new WebButton(I18N.getVal(msg.REMOVE));
		GridBagConstraints gbc_btnRemoveField = new GridBagConstraints();
		gbc_btnRemoveField.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveField.gridx = 7;
		gbc_btnRemoveField.gridy = 2;
		pnlFields.add(btnRemoveField, gbc_btnRemoveField);
		btnRemoveField.addActionListener(new RemoveFieldListener());

		chkFieldMatchCase = new WebCheckBox(I18N.getVal(msg.CASE_SENSITIVE));
		GridBagConstraints gbc_chkFieldMatchCase = new GridBagConstraints();
		gbc_chkFieldMatchCase.insets = new Insets(0, 0, 5, 5);
		gbc_chkFieldMatchCase.gridx = 13;
		gbc_chkFieldMatchCase.gridy = 2;
		pnlFields.add(chkFieldMatchCase, gbc_chkFieldMatchCase);

		chkFieldRegex = new WebCheckBox(I18N.getVal(msg.REGEX));
		GridBagConstraints gbc_chkFieldRegex = new GridBagConstraints();
		gbc_chkFieldRegex.insets = new Insets(0, 0, 5, 0);
		gbc_chkFieldRegex.gridx = 14;
		gbc_chkFieldRegex.gridy = 2;
		pnlFields.add(chkFieldRegex, gbc_chkFieldRegex);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 15;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		pnlFields.add(scrollPane, gbc_scrollPane);

		tableFields = new WebTable((TableModel) null);
		tableFields.setToolTipText("Double click on item to edit param");
		tableFields.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { I18N.getVal(msg.FIELD_NAME), I18N.getVal(msg.FIELD_TYPE) }));
		// panel_1.add(table, gbc_table);
		tableFields.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableFields.getTableHeader().setReorderingAllowed(false);

//		tableFields.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		scrollPane.setColumnHeaderView(tableFields);
		scrollPane.setViewportView(tableFields);

		/* Tests */
		WebPanel pnlTests = new WebPanel();
		GridBagConstraints gbc_pnlTests = new GridBagConstraints();
		gbc_pnlTests.gridheight = 4;
		gbc_pnlTests.gridwidth = 6;
		gbc_pnlTests.insets = new Insets(0, 0, 5, 0);
		gbc_pnlTests.fill = GridBagConstraints.BOTH;
		gbc_pnlTests.gridx = 0;
		gbc_pnlTests.gridy = 4;
		contentPane.add(pnlTests, gbc_pnlTests);
		GridBagLayout gbl_pnlTests = new GridBagLayout();
		gbl_pnlTests.columnWidths = new int[] { 213, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlTests.rowHeights = new int[] { 23, 0, 0, 0, 0, 0, 0 };
		gbl_pnlTests.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlTests.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlTests.setLayout(gbl_pnlTests);

		WebButton addButton = new WebButton("Add");
		addButton.setActionCommand(ADD_COMMAND);
		addButton.addActionListener(this);

		WebButton removeButton = new WebButton("Remove");
		removeButton.setActionCommand(REMOVE_COMMAND);
		removeButton.addActionListener(this);

		WebButton clearButton = new WebButton("Clear");
		clearButton.setActionCommand(CLEAR_COMMAND);
		clearButton.addActionListener(this);

		pnlTestButtons = new WebPanel(new GridLayout(0, 3));
		pnlTestButtons.add(addButton);
		pnlTestButtons.add(removeButton);
		pnlTestButtons.add(clearButton);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.anchor = GridBagConstraints.SOUTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 5;
		pnlTests.add(pnlTestButtons, gbc_panel);

		rootNode = new DefaultMutableTreeNode(msg.FIELDS);
//		String[] names = new String[] { "Alice", "Bob", "Carol", "Mallory" };
//		for (String name : names) {
//			DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
//			rootNode.add(node);
//		}
		checkBoxTree = new DynamicWebCheckBoxTree(rootNode);
//		checkBoxTree.setVisibleRowCount(7);

		JTreeUtil.setTreeExpandedState(checkBoxTree, true);
		TreeFilterDecorator filterDecorator = TreeFilterDecorator.decorate(checkBoxTree, AnalyzerMatcher.create());

		checkBoxTree.setCellRenderer(new TradingProjectTreeRenderer(() -> filterDecorator.getFilterField().getText()));

		JPanel topPanel = new JPanel();
		topPanel.add(filterDecorator.getFilterField());
		WebButton expandBtn = new WebButton("Expand All");
		expandBtn.addActionListener(ae -> {
			JTreeUtil.setTreeExpandedState(checkBoxTree, true);
		});
		topPanel.add(expandBtn);
		WebButton collapseBtn = new WebButton("Collapse All");
		collapseBtn.addActionListener(ae -> {
			JTreeUtil.setTreeExpandedState(checkBoxTree, false);
		});
		topPanel.add(collapseBtn);
		pnlTests.add(topPanel);

		checkBoxTree.setPreferredSize(new Dimension(300, 150));
		GridBagConstraints gbc_checkBoxTree = new GridBagConstraints();
		gbc_checkBoxTree.insets = new Insets(0, 0, 5, 5);
		gbc_checkBoxTree.gridheight = 4;
		gbc_checkBoxTree.fill = GridBagConstraints.BOTH;
		gbc_checkBoxTree.gridx = 0;
		gbc_checkBoxTree.gridy = 1;

		JScrollPane scrollTests = new JScrollPane(checkBoxTree);
		GridBagConstraints gbc_scrollTests = new GridBagConstraints();
		gbc_scrollTests.gridwidth = 13;
		gbc_scrollTests.insets = new Insets(0, 0, 5, 5);
		gbc_scrollTests.gridheight = 4;
		gbc_scrollTests.fill = GridBagConstraints.BOTH;
		gbc_scrollTests.gridx = 0;
		gbc_scrollTests.gridy = 1;
		pnlTests.add(scrollTests, gbc_scrollTests);

		btnRun = new WebButton("Run");
		GridBagConstraints gbc_btnRun = new GridBagConstraints();
		gbc_btnRun.insets = new Insets(0, 0, 5, 5);
		gbc_btnRun.gridx = 13;
		gbc_btnRun.gridy = 4;
		pnlTests.add(btnRun, gbc_btnRun);
		btnRun.addActionListener(new RunListener());

		this.pack();
		this.center();
	}

	public FrameAddField(FieldAnalyzer fieldAnalyzer) {
		this();
		editMode = true;

		if (fieldAnalyzer != null) {
			ClassAnalyzer classAnalyzer = (ClassAnalyzer) fieldAnalyzer.getParent();
			String className = classAnalyzer.getMemberName();
			txtClassName.setText(className);
			chkClassRecursive.setSelected(classAnalyzer.isRecursive());
			chkClassCase.setSelected(classAnalyzer.isMatchCase());
			chkClassRegex.setSelected(classAnalyzer.isRegex());

			String fieldType = fieldAnalyzer.getType();
			txtFieldType.setText(fieldType);

			String fieldName = fieldAnalyzer.getMemberName();
			txtFieldName.setText(fieldName);

			List<FieldModifierAnalyzer> modifiers = fieldAnalyzer.getChildren();
			for (FieldModifierAnalyzer modifier : modifiers) {
				if (modifier instanceof PrivateFieldAnalyzer) {
					comboFieldModifier.setSelectedItem(I18N.getVal(msg.PRIVATE));
				} else if (modifier instanceof ProtectedFieldAnalyzer) {
					comboFieldModifier.setSelectedItem(I18N.getVal(msg.PROTECTED));
				} else if (modifier instanceof PublicFieldAnalyzer) {
					comboFieldModifier.setSelectedItem(I18N.getVal(msg.PUBLIC));
				} else if (modifier instanceof StaticFieldAnalyzer) {
					chkFieldStatic.setSelected(true);
				} else if (modifier instanceof FinalFieldAnalyzer) {
					chkFieldFinal.setSelected(true);
				}
			}

			if (fieldAnalyzer.isMatchCase()) {
				chkFieldMatchCase.setSelected(true);
			}
			if (fieldAnalyzer.isRegex()) {
				chkFieldRegex.setSelected(true);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (ADD_COMMAND.equals(command)) {
			// Add button clicked
			WebFrame frame = new WebFrame();
//			frame.getContentPane().add(new PanelAddField2(this));
			frame.pack();
			frame.center();
			frame.setVisible(true);

		} else if (REMOVE_COMMAND.equals(command)) {
			// Remove button clicked
			removeCurrentNode(checkBoxTree);
			checkBoxTree.removeCurrentNode();

		} else if (CLEAR_COMMAND.equals(command)) {
			// Clear button clicked.
//			clear(checkBoxTree);
			checkBoxTree.clear();
		}
	}

	/** Add child to the currently selected node. */
	public DefaultMutableTreeNode addObject(JTree tree, Object child) {
		DefaultMutableTreeNode parentNode = null;
		TreePath parentPath = tree.getSelectionPath();

		if (parentPath == null) {
			parentNode = rootNode;
		} else {
			parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
		}

		return addObject(tree, parentNode, child, true);
	}

	public DefaultMutableTreeNode addObject(JTree tree, DefaultMutableTreeNode parent, Object child) {
		return addObject(tree, parent, child, false);
	}

	public DefaultMutableTreeNode addObject(JTree tree, DefaultMutableTreeNode parent, Object child,
			boolean shouldBeVisible) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

		DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();

		if (parent == null) {
			parent = rootNode;
		}

		for (int index = 0; index < treeModel.getChildCount(parent); index++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeModel.getChild(parent, index);

			if (node.getUserObject().equals(child)) {
				return node;
			}
		}
		// It is key to invoke this on the TreeModel, and NOT
		// DefaultMutableTreeNode
		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

		// Make sure the user can see the lovely new node.
		if (shouldBeVisible) {
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		return childNode;
	}

	/** Remove all nodes except the root node. */
	public void clear(JTree tree) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		root.removeAllChildren();
		model.reload();
	}

	/** Remove the currently selected node. */
	public void removeCurrentNode(JTree tree) {

		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

		TreePath[] paths = tree.getSelectionPaths();
		if (paths != null) {
			for (TreePath path : paths) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
				if (node.getParent() != null) {
					model.removeNodeFromParent(node);
				}
			}
		}
	}

	public DefaultMutableTreeNode addToTree(Analyzer analyzer) {
		return addToTree(checkBoxTree.rootNode, analyzer);
	}

	public DefaultMutableTreeNode addToTree(DefaultMutableTreeNode root, Analyzer analyzer) {

		DefaultMutableTreeNode newNode = checkBoxTree.addObject(root, analyzer, true);

//		TreePath treePath = new TreePath(newNode.getPath());
//		checkBoxTree.setSelectionPath(treePath);

		if (analyzer instanceof CompositeAnalyzer) {
			CompositeAnalyzer<?> composite = (CompositeAnalyzer<?>) analyzer;
			List<? extends AbstractAnalyzer> children = composite.getChildren();
			for (AbstractAnalyzer child : children) {
				addToTree(newNode, child);
			}
		}
		return newNode;
	}

	private void updateTree(AbstractAnalyzer analyzer) {
		DefaultMutableTreeNode rootNode = checkBoxTree.rootNode;

		boolean isParentIn = false;
		AbstractAnalyzer parent = analyzer.getParent();
		if (parent != null) {
			TreeModel model = checkBoxTree.getModel();
			for (int index = 0; index < model.getChildCount(rootNode); index++) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) model.getChild(rootNode, index);

				Object userObject = node.getUserObject();
				AbstractAnalyzer a = (AbstractAnalyzer) userObject;
				if (a.getMemberName().equalsIgnoreCase(parent.getMemberName())) {
					rootNode = node;
					isParentIn = true;
					break;
				}
			}
			if (!isParentIn) {
				updateTree(parent);
			} else {
				boolean exists = false;
				for (int index = 0; index < model.getChildCount(rootNode); index++) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) model.getChild(rootNode, index);

					Object userObject = node.getUserObject();
					AbstractAnalyzer a = (AbstractAnalyzer) userObject;
					if (a.getMemberName().equalsIgnoreCase(analyzer.getMemberName())) {
//						JOptionPane.showMessageDialog(thisFrame, I18N.getVal(msg.MSG_FIELD_ALREADY_DEFINED));
						exists = true;
						break;
					}
				}
				if (!exists) {
					addToTree(rootNode, analyzer);
				}
			}

		} else {
			addToTree(rootNode, analyzer);
		}
	}

	private final class AddFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			DefaultTableModel model = (DefaultTableModel) tableFields.getModel();
			String className = txtClassName.getText();
			String fieldName = txtFieldName.getText();
			String fieldType = txtFieldType.getText();
			String stringValue = txtValue.getText();

			int value = 0;
			if (stringValue != null && !stringValue.equals("")) {
				value = Integer.valueOf(stringValue);
			}
			if (className == null || className.equals("")) {
				JOptionPane.showMessageDialog(thisFrame, I18N.getVal(msg.MSG_CLASS_NAME_MISSING));
			} else if (fieldType == null || fieldType.equals("")) {
				JOptionPane.showMessageDialog(thisFrame, I18N.getVal(msg.MSG_FIELD_TYPE_MISSING));
			} else if (fieldName == null || fieldName.equals("")) {
				JOptionPane.showMessageDialog(thisFrame, I18N.getVal(msg.MSG_FIELD_NAME_MISSING));
			} else {

				model.addRow(new String[] { fieldName, fieldType });
				tableFields.setModel(model);

				boolean regex = chkClassRegex.isSelected();
				boolean matchCase = chkClassCase.isSelected();
				boolean recursive = chkClassRecursive.isSelected();
				ClassAnalyzer classAnalyzer = new ClassAnalyzer(className, recursive, matchCase, regex, value);
				DefaultMutableTreeNode parentNode = checkBoxTree.addObject(classAnalyzer);

				boolean declared = false;
				boolean fieldRegex = chkFieldRegex.isSelected();
				boolean fieldMatchCase = chkFieldMatchCase.isSelected();

				FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, fieldType, declared, fieldName,
						fieldRegex, fieldMatchCase);

//				classAnalyzer.addField(fieldAnalyzer);
//				updateTree(fieldAnalyzer);
				DefaultMutableTreeNode childNode = checkBoxTree.addObject(parentNode, fieldAnalyzer);

				String visibility = (String) comboFieldModifier.getSelectedItem();
				if (visibility.equals(msg.PRIVATE.toString())) {
//					fieldAnalyzer.addPrivate();
					PrivateFieldAnalyzer privateFieldAnalyzer = new PrivateFieldAnalyzer(fieldAnalyzer);
					updateTree(privateFieldAnalyzer);
//					checkBoxTree.addObject(childNode, privateFieldAnalyzer);

				} else if (visibility.equals(msg.PROTECTED.toString())) {
//					fieldAnalyzer.addProtected();
					ProtectedFieldAnalyzer protectedFieldAnalyzer = new ProtectedFieldAnalyzer(fieldAnalyzer);
					checkBoxTree.addObject(childNode, protectedFieldAnalyzer);
				} else if (visibility.equals(msg.PUBLIC.toString())) {
//					fieldAnalyzer.addPublic();
					PublicFieldAnalyzer publicFieldAnalyzer = new PublicFieldAnalyzer(fieldAnalyzer);
					addToTree(childNode, publicFieldAnalyzer);
				}

				if (chkFieldStatic.isSelected()) {
//					fieldAnalyzer.addStatic();
					StaticFieldAnalyzer staticFieldAnalyzer = new StaticFieldAnalyzer(fieldAnalyzer);
					addToTree(childNode, staticFieldAnalyzer);
				}

				if (chkFieldFinal.isSelected()) {
//					fieldAnalyzer.addFinal();
					FinalFieldAnalyzer finalFieldAnalyzer = new FinalFieldAnalyzer(fieldAnalyzer);
					addToTree(childNode, finalFieldAnalyzer);
				}

//				updateTree(fieldAnalyzer);
//				DefaultMutableTreeNode root = checkBoxTree.rootNode;
//				addToTree(root, classAnalyzer);
//				checkBoxTree.addObject(classAnalyzer);
//				previousFrame.updateButtons();
			}
		}
	}

	private final class RemoveFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int selectedRow = tableFields.getSelectedRow();
			if (selectedRow >= 0) {
				ComponentUtils.removeRow(tableFields, selectedRow);
				// updatePreview();
				checkBoxTree.removeCurrentNode();
			}
		}
	}

	private TestRunner buildCommand(TestRunner command, DefaultMutableTreeNode node) {
		boolean checkedOrMixed = checkBoxTree.isChecked(node) || checkBoxTree.isMixed(node);
		if (checkedOrMixed) {
			AbstractAnalyzer analyzer = (AbstractAnalyzer) node.getUserObject();

			TestRunner slave = new TestRunner(analyzer);
			command.add(slave);

			TreeModel treeModel = checkBoxTree.getModel();
			int childCount = treeModel.getChildCount(node);

			if (childCount == 0) {
				return command;
			}

			CompositeAnalyzer<AbstractAnalyzer> compsiteAnalyzer = (CompositeAnalyzer<AbstractAnalyzer>) analyzer;
			for (int index = 0; index < childCount; index++) {
				DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) treeModel.getChild(node, index);
				buildCommand(slave, childNode);
			}
		}
		return command;
	}

	class RunListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			List<DefaultMutableTreeNode> nodes = checkBoxTree.getAllNodes();

			CompositeAnalyzer<AbstractAnalyzer> composite = new CompositeAnalyzer<>();
			TestRunner command = new TestRunner(composite);

			for (DefaultMutableTreeNode node : nodes) {
				TreeNode parent = node.getParent();
				if (parent != null && parent.equals(checkBoxTree.rootNode)) {
					buildCommand(command, node);
				}
			}

			Performance perform = command.execute();
			System.out.println(perform);

		}
	}

	public TreeNode getRootParent(TreeNode node) {
		TreeNode parent = node.getParent();
		if (parent == null) {
			return node;
		}
		return getRootParent(parent);
	}

	public static void main(String[] args) {

		// Its very important to call this before setting Look & Feel
		I18N.getVal(FrameTestMsg.ADD_BEHAVIOR);

		// Look and Feel
		WebLookAndFeel.install();

		FrameAddField frame = new FrameAddField();
//		frame.pack();
//		frame.center();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
