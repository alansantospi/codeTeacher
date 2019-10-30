package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebEditorPane;
import com.alee.laf.text.WebTextField;

import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.FinalFieldAnalyzer;
import codeteacher.analyzers.PrivateFieldAnalyzer;
import codeteacher.analyzers.ProtectedFieldAnalyzer;
import codeteacher.analyzers.PublicFieldAnalyzer;
import codeteacher.analyzers.StaticFieldAnalyzer;
import gui.component.AutoComboBox;
import gui.component.AutoCompleteBehaviourClassPath;
import gui.component.ComponentUtils;
import gui.msg.FrameAddOutputMsg;
import gui.msg.I18N;

public class PanelAddField extends WebPanel {

	public PanelAddField() {
		create();
	}

	private FrameAddOutputMsg msg = FrameAddOutputMsg.TITLE;

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

	private void create() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 36, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 1, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		pnlClass = new WebPanel();
		pnlClass.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlClass = new GridBagConstraints();
		gbc_pnlClass.insets = new Insets(0, 0, 5, 5);
		gbc_pnlClass.gridx = 1;
		gbc_pnlClass.gridy = 0;
		this.add(pnlClass, gbc_pnlClass);
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
		GridBagConstraints gbc_pnlFields_1 = new GridBagConstraints();
		gbc_pnlFields_1.insets = new Insets(0, 0, 0, 5);
		gbc_pnlFields_1.gridx = 1;
		gbc_pnlFields_1.gridy = 4;
		this.add(pnlFields, gbc_pnlFields_1);
		// this.add(pnlFields);
		GridBagLayout gbl_pnlFields = new GridBagLayout();
		gbl_pnlFields.columnWidths = new int[] { 0, 0, 106, 0, 119, 0, 7, 0, 20, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlFields.rowHeights = new int[] { 0, 36, 31, 0, 0, 0 };
		gbl_pnlFields.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlFields.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
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

		// createAutoSuggestor(txtFieldType, thisFrame);
		// createAutoComboBox(gbc_txtFieldType);
		// createAutoComplete(txtFieldType);
		 AutoCompleteBehaviourClassPath autoComplete = new
		 AutoCompleteBehaviourClassPath(txtFieldType);

		 autoComplete.setInitialPopupSize(new Dimension(txtFieldType.getWidth()+500,
		 txtFieldType.getHeight()));

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
		gbc_chkFieldFinal.gridwidth = 2;
		gbc_chkFieldFinal.insets = new Insets(0, 0, 5, 5);
		gbc_chkFieldFinal.gridx = 2;
		gbc_chkFieldFinal.gridy = 2;
		pnlFields.add(chkFieldFinal, gbc_chkFieldFinal);

		chkFieldMatchCase = new WebCheckBox(I18N.getVal(msg.CASE_SENSITIVE));
		GridBagConstraints gbc_chkFieldMatchCase = new GridBagConstraints();
		gbc_chkFieldMatchCase.insets = new Insets(0, 0, 5, 5);
		gbc_chkFieldMatchCase.gridx = 11;
		gbc_chkFieldMatchCase.gridy = 2;
		pnlFields.add(chkFieldMatchCase, gbc_chkFieldMatchCase);

		chkFieldRegex = new WebCheckBox(I18N.getVal(msg.REGEX));
		GridBagConstraints gbc_chkFieldRegex = new GridBagConstraints();
		gbc_chkFieldRegex.insets = new Insets(0, 0, 5, 5);
		gbc_chkFieldRegex.gridx = 12;
		gbc_chkFieldRegex.gridy = 2;
		pnlFields.add(chkFieldRegex, gbc_chkFieldRegex);

		btnAddField = new WebButton(I18N.getVal(msg.ADD));
		btnAddField.addActionListener(new AddFieldListener());
		GridBagConstraints gbc_btnAddField = new GridBagConstraints();
		gbc_btnAddField.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddField.gridx = 11;
		gbc_btnAddField.gridy = 3;
		pnlFields.add(btnAddField, gbc_btnAddField);

		btnRemoveField = new WebButton(I18N.getVal(msg.REMOVE));
		GridBagConstraints gbc_btnRemoveField = new GridBagConstraints();
		gbc_btnRemoveField.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveField.gridx = 12;
		gbc_btnRemoveField.gridy = 3;
		pnlFields.add(btnRemoveField, gbc_btnRemoveField);
		btnRemoveField.addActionListener(new RemoveFieldListener());

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 15;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		pnlFields.add(scrollPane, gbc_scrollPane);

		tableFields = new WebTable((TableModel) null);
		tableFields.setToolTipText("Double click on item to edit param");
		tableFields.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { I18N.getVal(msg.FIELD_NAME), I18N.getVal(msg.FIELD_TYPE) }));
		// panel_1.add(table, gbc_table);
		tableFields.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableFields.getTableHeader().setReorderingAllowed(false);

		// tableFields.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		scrollPane.setColumnHeaderView(tableFields);
		scrollPane.setViewportView(tableFields);

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
				JOptionPane.showMessageDialog(null, I18N.getVal(msg.MSG_CLASS_NAME_MISSING));
			} else if (fieldType == null || fieldType.equals("")) {
				JOptionPane.showMessageDialog(null, I18N.getVal(msg.MSG_FIELD_TYPE_MISSING));
			} else if (fieldName == null || fieldName.equals("")) {
				JOptionPane.showMessageDialog(null, I18N.getVal(msg.MSG_FIELD_NAME_MISSING));
			} else {

				model.addRow(new String[] { fieldName, fieldType });
				tableFields.setModel(model);

				boolean regex = chkClassRegex.isSelected();
				boolean matchCase = chkClassCase.isSelected();
				boolean recursive = chkClassRecursive.isSelected();
				ClassAnalyzer classAnalyzer = new ClassAnalyzer(className, recursive, matchCase, regex, value);
//				DefaultMutableTreeNode parentNode = checkBoxTree.addObject(classAnalyzer);

				boolean declared = false;
				boolean fieldRegex = chkFieldRegex.isSelected();
				boolean fieldMatchCase = chkFieldMatchCase.isSelected();

				FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, fieldType, declared, fieldName,
						fieldRegex, fieldMatchCase);

//				classAnalyzer.addField(fieldAnalyzer);
//				updateTree(fieldAnalyzer);
//				DefaultMutableTreeNode childNode = checkBoxTree.addObject(parentNode, fieldAnalyzer);

				String visibility = (String) comboFieldModifier.getSelectedItem();
				if (visibility.equals(msg.PRIVATE.toString())) {
//					fieldAnalyzer.addPrivate();
					PrivateFieldAnalyzer privateFieldAnalyzer = new PrivateFieldAnalyzer(fieldAnalyzer);
//					updateTree(privateFieldAnalyzer);
//					checkBoxTree.addObject(childNode, privateFieldAnalyzer);

				} else if (visibility.equals(msg.PROTECTED.toString())) {
//					fieldAnalyzer.addProtected();
					ProtectedFieldAnalyzer protectedFieldAnalyzer = new ProtectedFieldAnalyzer(fieldAnalyzer);
//					checkBoxTree.addObject(childNode, protectedFieldAnalyzer);
				} else if (visibility.equals(msg.PUBLIC.toString())) {
//					fieldAnalyzer.addPublic();
					PublicFieldAnalyzer publicFieldAnalyzer = new PublicFieldAnalyzer(fieldAnalyzer);
//					addToTree(childNode, publicFieldAnalyzer);
				}

				if (chkFieldStatic.isSelected()) {
//					fieldAnalyzer.addStatic();
					StaticFieldAnalyzer staticFieldAnalyzer = new StaticFieldAnalyzer(fieldAnalyzer);
//					addToTree(childNode, staticFieldAnalyzer);
				}

				if (chkFieldFinal.isSelected()) {
//					fieldAnalyzer.addFinal();
					FinalFieldAnalyzer finalFieldAnalyzer = new FinalFieldAnalyzer(fieldAnalyzer);
//					addToTree(childNode, finalFieldAnalyzer);
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
//				checkBoxTree.removeCurrentNode();
			}
		}
	}

	public static void main(String[] args) {
		WebFrame frame = new WebFrame();
		frame.getContentPane().add(new PanelAddField());
		frame.pack();
		frame.center();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
