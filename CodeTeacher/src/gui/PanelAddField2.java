package gui;

import static gui.msg.FrameAddOutputMsg.ACCESS_MODIFIER;
import static gui.msg.FrameAddOutputMsg.ADD;
import static gui.msg.FrameAddOutputMsg.CASE_SENSITIVE;
import static gui.msg.FrameAddOutputMsg.DEFAULT;
import static gui.msg.FrameAddOutputMsg.FIELD_NAME;
import static gui.msg.FrameAddOutputMsg.FIELD_TYPE;
import static gui.msg.FrameAddOutputMsg.FINAL;
import static gui.msg.FrameAddOutputMsg.MSG_CLASS_NAME_MISSING;
import static gui.msg.FrameAddOutputMsg.MSG_FIELD_NAME_MISSING;
import static gui.msg.FrameAddOutputMsg.MSG_FIELD_TYPE_MISSING;
import static gui.msg.FrameAddOutputMsg.PRIVATE;
import static gui.msg.FrameAddOutputMsg.PROTECTED;
import static gui.msg.FrameAddOutputMsg.PUBLIC;
import static gui.msg.FrameAddOutputMsg.RECURSIVE;
import static gui.msg.FrameAddOutputMsg.REGEX;
import static gui.msg.FrameAddOutputMsg.REMOVE;
import static gui.msg.FrameAddOutputMsg.STATIC;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;
import javax.swing.tree.DefaultMutableTreeNode;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.text.WebTextField;

import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.FinalFieldAnalyzer;
import codeteacher.analyzers.ImplementsAnalyzer;
import codeteacher.analyzers.PrivateFieldAnalyzer;
import codeteacher.analyzers.ProtectedFieldAnalyzer;
import codeteacher.analyzers.PublicFieldAnalyzer;
import codeteacher.analyzers.StaticFieldAnalyzer;
import gui.component.AutoCompleteBehaviourClassPath;
import gui.component.ComponentUtils;
import gui.msg.I18N;

public class PanelAddField2 extends WebPanel {

	private FrameTestField previous;
	private JPanel panel;

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

	private WebButton btnAddField;
	private WebButton btnRemoveField;
	private JPanel pnlClassOptions;
	private WebSpinner spinFieldType;
	private WebSpinner spinFieldName;
	private WebSpinner spinFinal;
	private WebSpinner spinStatic;
	private WebSpinner spinVisibility;
	private JButton btnOk;
	private JScrollPane scrollPreview;
	private JLabel lblPreview;
	private JLabel lblTotalValue;
	private JLabel lblTotal;
	private JPanel panel_1;
	private JButton btnCancel;

	public PanelAddField2(FrameTestField previous) {
		this.previous = previous;
		create();
	}

	/**
	 * @param classAnalyzer
	 * @wbp.parser.constructor
	 */
	public PanelAddField2(ClassAnalyzer classAnalyzer, JPanel panel) {
		this.panel = panel;
		create();
		txtClassName.setText(classAnalyzer.getMemberName());
		txtClassName.setEnabled(false);
		chkClassRecursive.setSelected(classAnalyzer.isRecursive());
		chkClassRecursive.setEnabled(false);
		chkClassCase.setSelected(classAnalyzer.isMatchCase());
		chkClassCase.setEnabled(false);
		chkClassRegex.setSelected(classAnalyzer.isRegex());
		chkClassRegex.setEnabled(false);
		pnlClass.setEnabled(false);

		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 3;
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 195, 45, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 23, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 5;
		gbc_btnCancel.gridy = 0;
		panel_1.add(btnCancel, gbc_btnCancel);

		btnOk = new JButton("Ok");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnOk.gridx = 6;
		gbc_btnOk.gridy = 0;
		panel_1.add(btnOk, gbc_btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String className = txtClassName.getText();
				boolean regex = chkClassRegex.isSelected();
				boolean matchCase = chkClassCase.isSelected();
				boolean recursive = chkClassRecursive.isSelected();
				Double total = Double.parseDouble(lblTotal.getText());
				int totalValue = 0;
				ClassAnalyzer classAnalyzer = new ClassAnalyzer(className, recursive, matchCase, regex, totalValue);

				boolean declared = false;
				boolean fieldRegex = chkFieldRegex.isSelected();
				boolean fieldMatchCase = chkFieldMatchCase.isSelected();
				String fieldName = txtFieldName.getText();
				String fieldType = txtFieldType.getText();

//				Integer value = (Integer) spinFieldName.getValue();

				FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, fieldType, declared, fieldName,
						fieldRegex, fieldMatchCase, total.intValue());

				String visibility = (String) comboFieldModifier.getSelectedItem();
				Double visibilityValue = (Double) spinVisibility.getValue();
				if (visibility.equals(PRIVATE.toString())) {
					PrivateFieldAnalyzer privateFieldAnalyzer = new PrivateFieldAnalyzer(fieldAnalyzer,	visibilityValue.intValue());
					fieldAnalyzer.add(privateFieldAnalyzer);
				} else if (visibility.equals(PROTECTED.toString())) {
					ProtectedFieldAnalyzer protectedFieldAnalyzer = new ProtectedFieldAnalyzer(fieldAnalyzer, visibilityValue.intValue());
					fieldAnalyzer.add(protectedFieldAnalyzer);

				} else if (visibility.equals(PUBLIC.toString())) {
					PublicFieldAnalyzer publicFieldAnalyzer = new PublicFieldAnalyzer(fieldAnalyzer, visibilityValue.intValue());
					fieldAnalyzer.add(publicFieldAnalyzer);
				}

				if (chkFieldStatic.isSelected()) {
					Double staticValue = (Double) spinStatic.getValue();
					StaticFieldAnalyzer staticFieldAnalyzer = new StaticFieldAnalyzer(fieldAnalyzer, staticValue.intValue());
					fieldAnalyzer.add(staticFieldAnalyzer);
				}

				if (chkFieldFinal.isSelected()) {
					Double finalValue = (Double) spinFinal.getValue();
					FinalFieldAnalyzer finalFieldAnalyzer = new FinalFieldAnalyzer(fieldAnalyzer, finalValue.intValue());
					fieldAnalyzer.add(finalFieldAnalyzer);
				}

				if (panel instanceof PanelAddAnalyzer) {
					((PanelAddAnalyzer<FieldAnalyzer>) panel).addAnalyzer(fieldAnalyzer);
				}
			}
		});
		updatePreview();
	}

	private void create() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 36, 366, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 1, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		pnlClass = new WebPanel();
		pnlClass.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlClass = new GridBagConstraints();
		gbc_pnlClass.insets = new Insets(0, 0, 5, 5);
		gbc_pnlClass.gridx = 1;
		gbc_pnlClass.gridy = 0;
		this.add(pnlClass, gbc_pnlClass);
		GridBagLayout gbl_pnlClass = new GridBagLayout();
		gbl_pnlClass.columnWidths = new int[] { 0, 10, 10 };
		gbl_pnlClass.rowHeights = new int[] { 35, 0, 0 };
		gbl_pnlClass.columnWeights = new double[] { 0.0, 0.0 };
		gbl_pnlClass.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		pnlClass.setLayout(gbl_pnlClass);

		lblClassname = new WebLabel("Class name");
		lblClassname.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblClassname = new GridBagConstraints();
		gbc_lblClassname.insets = new Insets(0, 0, 5, 5);
		gbc_lblClassname.gridx = 0;
		gbc_lblClassname.gridy = 0;
		pnlClass.add(lblClassname, gbc_lblClassname);

		txtClassName = new WebTextField();
		txtClassName.setColumns(10);
		GridBagConstraints gbc_txtClassName = new GridBagConstraints();
		gbc_txtClassName.insets = new Insets(0, 0, 5, 5);
		gbc_txtClassName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtClassName.gridx = 1;
		gbc_txtClassName.gridy = 0;
		pnlClass.add(txtClassName, gbc_txtClassName);

		chkClassRecursive = new WebCheckBox(gui.msg.I18N.getVal(RECURSIVE));
		GridBagConstraints gbc_chkClassRecursive = new GridBagConstraints();
		gbc_chkClassRecursive.anchor = GridBagConstraints.WEST;
		gbc_chkClassRecursive.gridx = 0;
		gbc_chkClassRecursive.gridy = 0;
//		pnlClassOptions.add(chkClassRecursive, gbc_chkClassRecursive);

		chkClassCase = new WebCheckBox(I18N.getVal(CASE_SENSITIVE));
		GridBagConstraints gbc_chkClassMatchCase = new GridBagConstraints();
		gbc_chkClassMatchCase.anchor = GridBagConstraints.WEST;
		gbc_chkClassMatchCase.gridx = 1;
		gbc_chkClassMatchCase.gridy = 0;
//		pnlClassOptions.add(chkClassCase, gbc_chkClassMatchCase);

		chkClassRegex = new WebCheckBox(I18N.getVal(REGEX));
		GridBagConstraints gbc_chkClassRegex = new GridBagConstraints();
		gbc_chkClassRegex.anchor = GridBagConstraints.WEST;
		gbc_chkClassRegex.gridx = 2;
		gbc_chkClassRegex.gridy = 0;
//		pnlClassOptions.add(chkClassRegex, gbc_chkClassRegex);

		pnlClassOptions = new GroupPanel(chkClassRecursive, chkClassCase, chkClassRegex);
		GridBagConstraints gbc_pnlClassOptions = new GridBagConstraints();
		gbc_pnlClassOptions.fill = GridBagConstraints.BOTH;
		gbc_pnlClassOptions.insets = new Insets(0, 0, 0, 5);
		gbc_pnlClassOptions.gridx = 1;
		gbc_pnlClassOptions.gridy = 1;
//		pnlClass.add(pnlClassOptions, gbc_pnlClassOptions);
		GridBagLayout gbl_pnlClassOptions = new GridBagLayout();
		gbl_pnlClassOptions.columnWidths = new int[] { 295, 0 };
		gbl_pnlClassOptions.rowHeights = new int[] { 0, 0 };
		gbl_pnlClassOptions.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_pnlClassOptions.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlClassOptions.setLayout(gbl_pnlClassOptions);

		pnlClass.add(pnlClassOptions, gbc_pnlClassOptions);

		pnlFields = new WebPanel();
		pnlFields.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlFields_1 = new GridBagConstraints();
		gbc_pnlFields_1.insets = new Insets(0, 0, 5, 5);
		gbc_pnlFields_1.gridx = 1;
		gbc_pnlFields_1.gridy = 1;
		this.add(pnlFields, gbc_pnlFields_1);
		// this.add(pnlFields);
		GridBagLayout gbl_pnlFields = new GridBagLayout();
		gbl_pnlFields.columnWidths = new int[] { 0, 0, 106, 0, 119, 0, 7, 0, 20, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlFields.rowHeights = new int[] { 0, 36, 31, 0, 0, 0, 0, 0, 0 };
		gbl_pnlFields.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlFields.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		pnlFields.setLayout(gbl_pnlFields);

		lblFieldModifier = new WebLabel(I18N.getVal(ACCESS_MODIFIER));
		GridBagConstraints gbc_lblFieldModifier = new GridBagConstraints();
		gbc_lblFieldModifier.anchor = GridBagConstraints.WEST;
		gbc_lblFieldModifier.insets = new Insets(0, 0, 5, 5);
		gbc_lblFieldModifier.gridx = 1;
		gbc_lblFieldModifier.gridy = 0;
		pnlFields.add(lblFieldModifier, gbc_lblFieldModifier);

		comboFieldModifier = new WebComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 0;
		comboFieldModifier.setModel(new DefaultComboBoxModel(new String[] { I18N.getVal(PRIVATE), I18N.getVal(PUBLIC),
				I18N.getVal(PROTECTED), I18N.getVal(DEFAULT) }));
		comboFieldModifier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updatePreview();
			}
		});

		pnlFields.add(comboFieldModifier, gbc_comboBox);

		spinVisibility = ComponentUtils.createSpinner(spinVisibility);
		spinVisibility.addChangeListener(new ValueUpdateListener());

		GridBagConstraints gbc_spinVisibility = new GridBagConstraints();
		gbc_spinVisibility.gridwidth = 2;
		gbc_spinVisibility.insets = new Insets(0, 0, 5, 5);
		gbc_spinVisibility.gridx = 5;
		gbc_spinVisibility.gridy = 0;
		pnlFields.add(spinVisibility, gbc_spinVisibility);

		chkFieldStatic = new WebCheckBox(I18N.getVal(STATIC));
		chkFieldStatic.setHorizontalAlignment(SwingConstants.LEFT);
//		chkFieldStatic.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GridBagConstraints gbc_chkFieldStatic = new GridBagConstraints();
		gbc_chkFieldStatic.anchor = GridBagConstraints.WEST;
		gbc_chkFieldStatic.insets = new Insets(0, 0, 5, 5);
		gbc_chkFieldStatic.gridx = 2;
		gbc_chkFieldStatic.gridy = 1;
		chkFieldStatic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updatePreview();
				boolean selected = chkFieldStatic.isSelected();
				spinStatic.setEnabled(selected);
				if (!selected) {
					spinStatic.setValue(0d);
				}

			}
		});

		pnlFields.add(chkFieldStatic, gbc_chkFieldStatic);

		spinStatic = ComponentUtils.createSpinner(spinStatic);
		GridBagConstraints gbc_spinStatic = new GridBagConstraints();
		gbc_spinStatic.gridwidth = 2;
		gbc_spinStatic.insets = new Insets(0, 0, 5, 5);
		gbc_spinStatic.gridx = 5;
		gbc_spinStatic.gridy = 1;
		spinStatic.addChangeListener(new ValueUpdateListener());
		pnlFields.add(spinStatic, gbc_spinStatic);

		chkFieldFinal = new WebCheckBox(I18N.getVal(FINAL));
		chkFieldFinal.setHorizontalAlignment(SwingConstants.LEFT);
//		chkFieldFinal.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GridBagConstraints gbc_chkFieldFinal = new GridBagConstraints();
		gbc_chkFieldFinal.anchor = GridBagConstraints.WEST;
		gbc_chkFieldFinal.gridwidth = 2;
		gbc_chkFieldFinal.insets = new Insets(0, 0, 5, 5);
		gbc_chkFieldFinal.gridx = 2;
		gbc_chkFieldFinal.gridy = 2;

		chkFieldFinal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updatePreview();
				boolean selected = chkFieldFinal.isSelected();
				spinFinal.setEnabled(selected);
				if (!selected) {
					spinFinal.setValue(0d);
				}
			}
		});
		pnlFields.add(chkFieldFinal, gbc_chkFieldFinal);

		spinFinal = ComponentUtils.createSpinner(spinFinal);
		GridBagConstraints gbc_spinFinal = new GridBagConstraints();
		gbc_spinFinal.gridwidth = 2;
		gbc_spinFinal.insets = new Insets(0, 0, 5, 5);
		gbc_spinFinal.gridx = 5;
		gbc_spinFinal.gridy = 2;
		spinFinal.addChangeListener(new ValueUpdateListener());
		pnlFields.add(spinFinal, gbc_spinFinal);

		lblFieldType = new WebLabel(I18N.getVal(FIELD_TYPE));
		GridBagConstraints gbc_lblFieldType = new GridBagConstraints();
		gbc_lblFieldType.anchor = GridBagConstraints.WEST;
		gbc_lblFieldType.insets = new Insets(0, 0, 5, 5);
		gbc_lblFieldType.gridx = 1;
		gbc_lblFieldType.gridy = 3;
		pnlFields.add(lblFieldType, gbc_lblFieldType);

		GridBagConstraints gbc_txtFieldType = new GridBagConstraints();
		gbc_txtFieldType.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldType.gridwidth = 3;
		gbc_txtFieldType.insets = new Insets(0, 0, 5, 5);
		gbc_txtFieldType.gridx = 2;
		gbc_txtFieldType.gridy = 3;

		txtFieldType = new WebTextField();
		txtFieldType.setColumns(10);
		txtFieldType.getDocument().addDocumentListener(new UpdatePreviewListener());
		pnlFields.add(txtFieldType, gbc_txtFieldType);

		spinFieldType = ComponentUtils.createSpinner(spinFieldType);
		GridBagConstraints gbc_spinFieldType = new GridBagConstraints();
		gbc_spinFieldType.gridwidth = 2;
		gbc_spinFieldType.insets = new Insets(0, 0, 5, 5);
		gbc_spinFieldType.gridx = 5;
		gbc_spinFieldType.gridy = 3;
		spinFieldType.addChangeListener(new ValueUpdateListener());
		pnlFields.add(spinFieldType, gbc_spinFieldType);

		lblFieldName = new WebLabel(I18N.getVal(FIELD_NAME));
		GridBagConstraints gbc_lblFieldName = new GridBagConstraints();
		gbc_lblFieldName.anchor = GridBagConstraints.WEST;
		gbc_lblFieldName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFieldName.gridx = 1;
		gbc_lblFieldName.gridy = 4;
		pnlFields.add(lblFieldName, gbc_lblFieldName);

		// createAutoSuggestor(txtFieldType, thisFrame);
		// createAutoComboBox(gbc_txtFieldType);
		// createAutoComplete(txtFieldType);
		AutoCompleteBehaviourClassPath autoComplete = new AutoCompleteBehaviourClassPath(txtFieldType);

		autoComplete.setInitialPopupSize(new Dimension(txtFieldType.getWidth() + 500, txtFieldType.getHeight()));

		txtFieldName = new WebTextField();
		txtFieldName.setColumns(10);
		GridBagConstraints gbc_txtFieldName = new GridBagConstraints();
		gbc_txtFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldName.gridwidth = 3;
		gbc_txtFieldName.insets = new Insets(0, 0, 5, 5);
		gbc_txtFieldName.gridx = 2;
		gbc_txtFieldName.gridy = 4;
		txtFieldName.getDocument().addDocumentListener(new UpdatePreviewListener());
		pnlFields.add(txtFieldName, gbc_txtFieldName);

		spinFieldName = ComponentUtils.createSpinner(spinFieldName);
		GridBagConstraints gbc_spinFieldName = new GridBagConstraints();
		gbc_spinFieldName.gridwidth = 2;
		gbc_spinFieldName.insets = new Insets(0, 0, 5, 5);
		gbc_spinFieldName.gridx = 5;
		gbc_spinFieldName.gridy = 4;
		spinFieldName.addChangeListener(new ValueUpdateListener());

		pnlFields.add(spinFieldName, gbc_spinFieldName);

		chkFieldMatchCase = new WebCheckBox(I18N.getVal(CASE_SENSITIVE));
		GridBagConstraints gbc_chkFieldMatchCase = new GridBagConstraints();
		gbc_chkFieldMatchCase.insets = new Insets(0, 0, 5, 5);
		gbc_chkFieldMatchCase.gridx = 2;
		gbc_chkFieldMatchCase.gridy = 5;
		pnlFields.add(chkFieldMatchCase, gbc_chkFieldMatchCase);

		chkFieldRegex = new WebCheckBox(I18N.getVal(REGEX));
		GridBagConstraints gbc_chkFieldRegex = new GridBagConstraints();
		gbc_chkFieldRegex.insets = new Insets(0, 0, 5, 5);
		gbc_chkFieldRegex.gridx = 3;
		gbc_chkFieldRegex.gridy = 5;
		pnlFields.add(chkFieldRegex, gbc_chkFieldRegex);

		btnAddField = new WebButton(I18N.getVal(ADD));
		btnAddField.addActionListener(new AddFieldListener());

		lblTotalValue = new JLabel("Total value");
		GridBagConstraints gbc_lblTotalValue = new GridBagConstraints();
		gbc_lblTotalValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalValue.gridx = 5;
		gbc_lblTotalValue.gridy = 5;
		pnlFields.add(lblTotalValue, gbc_lblTotalValue);

		lblTotal = new JLabel("0");
		GridBagConstraints gbc_lblTotal = new GridBagConstraints();
		gbc_lblTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotal.gridx = 6;
		gbc_lblTotal.gridy = 5;
		pnlFields.add(lblTotal, gbc_lblTotal);
		GridBagConstraints gbc_btnAddField = new GridBagConstraints();
		gbc_btnAddField.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddField.gridx = 5;
		gbc_btnAddField.gridy = 6;
		pnlFields.add(btnAddField, gbc_btnAddField);

		btnRemoveField = new WebButton(I18N.getVal(REMOVE));
		GridBagConstraints gbc_btnRemoveField = new GridBagConstraints();
		gbc_btnRemoveField.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveField.gridx = 6;
		gbc_btnRemoveField.gridy = 6;
		pnlFields.add(btnRemoveField, gbc_btnRemoveField);

		scrollPreview = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		add(scrollPreview, gbc_scrollPane);

		lblPreview = new JLabel("");
		scrollPreview.setViewportView(lblPreview);
		lblPreview.setFont(new Font("Courier New", Font.BOLD, 14));

		btnRemoveField.addActionListener(new RemoveFieldListener());

		spinFinal.setEnabled(chkFieldFinal.isSelected());
		spinStatic.setEnabled(chkFieldStatic.isSelected());
	}

	private final class AddFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			String className = txtClassName.getText();
			String fieldName = txtFieldName.getText();
			String fieldType = txtFieldType.getText();
//			String stringValue = txtValue.getText();

			int value = 0;
//			if (stringValue != null && !stringValue.equals("")) {
//				value = Integer.valueOf(stringValue);
//			}
			if (className == null || className.equals("")) {
				JOptionPane.showMessageDialog(null, I18N.getVal(MSG_CLASS_NAME_MISSING));
			} else if (fieldType == null || fieldType.equals("")) {
				JOptionPane.showMessageDialog(null, I18N.getVal(MSG_FIELD_TYPE_MISSING));
			} else if (fieldName == null || fieldName.equals("")) {
				JOptionPane.showMessageDialog(null, I18N.getVal(MSG_FIELD_NAME_MISSING));
			} else {

				boolean regex = chkClassRegex.isSelected();
				boolean matchCase = chkClassCase.isSelected();
				boolean recursive = chkClassRecursive.isSelected();
				ClassAnalyzer classAnalyzer = new ClassAnalyzer(className, recursive, matchCase, regex, value);
				DefaultMutableTreeNode parentNode = previous.addToTree(classAnalyzer);
//				checkBoxTree.addObject(classAnalyzer);

				boolean declared = false;
				boolean fieldRegex = chkFieldRegex.isSelected();
				boolean fieldMatchCase = chkFieldMatchCase.isSelected();

				FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, fieldType, declared, fieldName,
						fieldRegex, fieldMatchCase, (Integer) spinFieldName.getValue());

				DefaultMutableTreeNode childNode = previous.addToTree(parentNode, fieldAnalyzer);
//				classAnalyzer.addField(fieldAnalyzer);
//				updateTree(fieldAnalyzer);
//				DefaultMutableTreeNode childNode = checkBoxTree.addObject(parentNode, fieldAnalyzer);

				String visibility = (String) comboFieldModifier.getSelectedItem();
				int visibilityValue = (Integer) spinVisibility.getValue();
				if (visibility.equals(PRIVATE.toString())) {
					PrivateFieldAnalyzer privateFieldAnalyzer = new PrivateFieldAnalyzer(fieldAnalyzer,
							visibilityValue);
					previous.addToTree(childNode, privateFieldAnalyzer);
				} else if (visibility.equals(PROTECTED.toString())) {
					ProtectedFieldAnalyzer protectedFieldAnalyzer = new ProtectedFieldAnalyzer(fieldAnalyzer,
							visibilityValue);
					previous.addToTree(childNode, protectedFieldAnalyzer);

				} else if (visibility.equals(PUBLIC.toString())) {
					PublicFieldAnalyzer publicFieldAnalyzer = new PublicFieldAnalyzer(fieldAnalyzer, visibilityValue);
					previous.addToTree(childNode, publicFieldAnalyzer);
				}

				if (chkFieldStatic.isSelected()) {
					int staticValue = (Integer) spinVisibility.getValue();
					StaticFieldAnalyzer staticFieldAnalyzer = new StaticFieldAnalyzer(fieldAnalyzer, staticValue);
					previous.addToTree(childNode, staticFieldAnalyzer);
				}

				if (chkFieldFinal.isSelected()) {
					int finalValue = (Integer) spinVisibility.getValue();
					FinalFieldAnalyzer finalFieldAnalyzer = new FinalFieldAnalyzer(fieldAnalyzer, finalValue);
					previous.addToTree(childNode, finalFieldAnalyzer);
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
//			int selectedRow = tableFields.getSelectedRow();
//			if (selectedRow >= 0) {
//				ComponentUtils.removeRow(tableFields, selectedRow);
			// updatePreview();
//				checkBoxTree.removeCurrentNode();
//			}
		}
	}

	private void updatePreview() {
		String fieldName = txtFieldName.getText();
		String fieldType = txtFieldType.getText();
		int index = comboFieldModifier.getSelectedIndex();
		String visibility = (String) comboFieldModifier.getItemAt(index);
		String isStatic = chkFieldStatic.isSelected() ? I18N.getVal(STATIC) + " " : "";
		String isFinal = chkFieldFinal.isSelected() ? I18N.getVal(FINAL) + " " : "";
		String preview = visibility + " " + isStatic + isFinal + fieldType + " " + fieldName;

		lblPreview.setText(preview);

//		updateButtons();
	}

	private void updateValue() {
		Double nameValue = (Double) spinFieldName.getValue();
		Double typeValue = (Double) spinFieldType.getValue();
		Double finalValue = (Double) spinFinal.getValue();
		Double staticValue = (Double) spinStatic.getValue();
		Double visValue = (Double) spinVisibility.getValue();

		Double totalValue = nameValue + typeValue + finalValue + staticValue + visValue;

		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ROOT);
		symbols.setGroupingSeparator('.');

		NumberFormat formatter = new DecimalFormat("#0.00", symbols);
		System.out.println(formatter.format(totalValue));

		lblTotal.setText(formatter.format(totalValue));
	}

	public static void main(String[] args) {

		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ROOT);
//	        symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator('.');

		NumberFormat formatter = new DecimalFormat("#0.00", symbols);
		float a = 5;
		double x = 0.222543853489;
		System.out.println(formatter.format(a));

//		WebFrame frame = new WebFrame();
//
//		frame.getContentPane().add(new PanelAddField2(null));
//		frame.pack();
//		frame.center();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
	}

	public class UpdatePreviewListener implements DocumentListener {

		public void removeUpdate(DocumentEvent e) {
			updatePreview();
		}

		public void insertUpdate(DocumentEvent e) {
			updatePreview();
		}

		public void changedUpdate(DocumentEvent e) {
			updatePreview();
		}
	}

	public class ValueUpdateListener implements javax.swing.event.ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			updateValue();
		}
	}

}
