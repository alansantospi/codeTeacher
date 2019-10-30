package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.FinalFieldAnalyzer;
import codeteacher.analyzers.OutputAnalyzer;
import codeteacher.analyzers.PrivateFieldAnalyzer;
import codeteacher.analyzers.ProtectedFieldAnalyzer;
import codeteacher.analyzers.PublicFieldAnalyzer;
import codeteacher.analyzers.StaticFieldAnalyzer;
import codeteacher.analyzers.TestSet;
import gui.component.AutoComboBox;
import gui.component.AutoCompleteBehaviourClassPath;
import gui.component.AutoSuggestor;
import gui.component.Autocomplete;
import gui.component.ComponentUtils;
import gui.msg.FrameAddOutputMsg;
import gui.msg.I18N;
import utils.ClassPathUtils;
import utils.StringUtils;

public class FrameAddOutput extends JFrame {

	private FrameAddOutputMsg msg = FrameAddOutputMsg.TITLE;

	private boolean editMode;
	private FrameAddOutput thisFrame;

	private JPanel contentPane;

	private JPanel pnlControlButtons;
	private GridBagLayout gbl_pnlControlButtons;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnClear;
	private JButton btnUp;
	private JButton btnDown;
	private JButton btnOk;
	private JButton btnCancel;

	private JScrollPane scrollPreview;
	private JLabel lblPreview;
	
	private JTextField txtMethodName;
	private JTextField txtReturnType;
	private JTable tableParams;
	private JComboBox comboModifier;
	private JLabel lblReturnType;
	private JPanel pnlSignature;
	private JTextArea textOutput;
	private JCheckBox chkFinal;
	private JCheckBox chkAbstract;
	private JCheckBox chkStatic;
	private String visibility;
	private String returnType;
	private String methodName;
	private FrameTest previousFrame;
	private String isStatic;
	private String isFinal;
	private String isAbstract;
	
	/* Class */
	private JPanel pnlClass;
	private JLabel lblClassname;
	private JTextField txtClassName;
	private JLabel lblValue;
	private JTextField txtValue;
	private JCheckBox chkClassRegex;
	private JCheckBox chkClassCase;
	private JCheckBox chkMethodCase;
	private JCheckBox chkMethodRegex;
	private JCheckBox chkClassRecursive;
	
	/* Output */
	private JPanel pnlOuput;
	private JPanel pnlOutputOptions;
	private JCheckBox chkOutputRegex;
	private JCheckBox chkOutputCase;
	private JCheckBox chkTabs;
	private JCheckBox chkWhiteSpaces;
	private JPanel pnlIgnore;
	private JCheckBox chkExtraSpaces;
	private JCheckBox chkLineBreaks;
	private JPanel pnlValue;
	
	/* Fields */
	private JPanel pnlFields;
	private JLabel lblFieldModifier;
	private JLabel lblFieldType;
	private JLabel lblFieldName;
	private JComboBox comboFieldModifier;
	private JTextField txtFieldType;
	private JTextField txtFieldName;
	private JCheckBox chkFieldStatic;
	private JCheckBox chkFieldFinal;
	private JCheckBox chkFieldAbstract;
	private JCheckBox chkFieldMatchCase;
	private JCheckBox chkFieldRegex;
	private JScrollPane scrollPane;
	private JTable tableFields;
	private JButton btnAddField;
	private JButton btnRemoveField;
	
	private AutoComboBox autoComboBox;
	private JEditorPane editorPane;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameAddOutput frame = new FrameAddOutput(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param previousFrame
	 * @wbp.parser.constructor
	 */
	public FrameAddOutput(final FrameTest previousFrame) {
		this.previousFrame = previousFrame;
		setTitle(I18N.getVal(msg.TITLE));
		this.thisFrame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 683, 552);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 87, 208, 30, 199, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 64, 32, 51, 93, 61, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0, 0.0, 0.0 };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		pnlValue = new JPanel();
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

		lblValue = new JLabel("Value");
		GridBagConstraints gbc_lblValue = new GridBagConstraints();
		gbc_lblValue.insets = new Insets(0, 0, 0, 5);
		gbc_lblValue.gridx = 16;
		gbc_lblValue.gridy = 0;
		pnlValue.add(lblValue, gbc_lblValue);

		txtValue = new JTextField();
		txtValue.setColumns(10);
		GridBagConstraints gbc_txtValue = new GridBagConstraints();
		gbc_txtValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValue.gridx = 17;
		gbc_txtValue.gridy = 0;
		pnlValue.add(txtValue, gbc_txtValue);
		txtValue.getDocument().addDocumentListener(new ChangeListener());

		pnlClass = new JPanel();
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
		gbl_pnlClass.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_pnlClass.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlClass.setLayout(gbl_pnlClass);

		lblClassname = new JLabel("Class name");
		lblClassname.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblClassname = new GridBagConstraints();
		gbc_lblClassname.insets = new Insets(0, 0, 0, 5);
		gbc_lblClassname.gridx = 0;
		gbc_lblClassname.gridy = 0;
		pnlClass.add(lblClassname, gbc_lblClassname);

		txtClassName = new JTextField();
		txtClassName.setColumns(10);
		GridBagConstraints gbc_txtClassName = new GridBagConstraints();
		gbc_txtClassName.insets = new Insets(0, 0, 0, 5);
		gbc_txtClassName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtClassName.gridx = 1;
		gbc_txtClassName.gridy = 0;
		pnlClass.add(txtClassName, gbc_txtClassName);

		chkClassRecursive = new JCheckBox(I18N.getVal(msg.RECURSIVE));
		GridBagConstraints gbc_chkClassRecursive = new GridBagConstraints();
		gbc_chkClassRecursive.insets = new Insets(0, 0, 0, 5);
		gbc_chkClassRecursive.gridx = 2;
		gbc_chkClassRecursive.gridy = 0;
		pnlClass.add(chkClassRecursive, gbc_chkClassRecursive);

		chkClassCase = new JCheckBox(I18N.getVal(msg.CASE_SENSITIVE));
		GridBagConstraints gbc_chkClassMatchCase = new GridBagConstraints();
		gbc_chkClassMatchCase.insets = new Insets(0, 0, 0, 5);
		gbc_chkClassMatchCase.gridx = 4;
		gbc_chkClassMatchCase.gridy = 0;
		pnlClass.add(chkClassCase, gbc_chkClassMatchCase);

		chkClassRegex = new JCheckBox(I18N.getVal(msg.REGEX));
		GridBagConstraints gbc_chkClassRegex = new GridBagConstraints();
		gbc_chkClassRegex.gridx = 5;
		gbc_chkClassRegex.gridy = 0;
		pnlClass.add(chkClassRegex, gbc_chkClassRegex);

		pnlSignature = new JPanel();
		pnlSignature.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlSignature = new GridBagConstraints();
		gbc_pnlSignature.gridwidth = 6;
		gbc_pnlSignature.insets = new Insets(0, 0, 5, 0);
		gbc_pnlSignature.fill = GridBagConstraints.BOTH;
		gbc_pnlSignature.gridx = 0;
		gbc_pnlSignature.gridy = 2;
		contentPane.add(pnlSignature, gbc_pnlSignature);
		GridBagLayout gbl_pnlSignature = new GridBagLayout();
		gbl_pnlSignature.columnWidths = new int[] { 0, 0, 0, 0, 119, 0, 7, 0, 20, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlSignature.rowHeights = new int[] { 0, 36, 31, 0 };
		gbl_pnlSignature.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlSignature.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlSignature.setLayout(gbl_pnlSignature);

		JLabel lblAccessModifier = new JLabel(I18N.getVal(msg.ACCESS_MODIFIER));
		GridBagConstraints gbc_lblAccessModifier = new GridBagConstraints();
		gbc_lblAccessModifier.gridwidth = 2;
		gbc_lblAccessModifier.insets = new Insets(0, 0, 5, 5);
		gbc_lblAccessModifier.gridx = 0;
		gbc_lblAccessModifier.gridy = 0;
		pnlSignature.add(lblAccessModifier, gbc_lblAccessModifier);
		lblAccessModifier.setLabelFor(comboModifier);

		lblReturnType = new JLabel(I18N.getVal(msg.RETURN_TYPE));
		GridBagConstraints gbc_lblReturnType = new GridBagConstraints();
		gbc_lblReturnType.anchor = GridBagConstraints.WEST;
		gbc_lblReturnType.insets = new Insets(0, 0, 5, 5);
		gbc_lblReturnType.gridx = 2;
		gbc_lblReturnType.gridy = 0;
		pnlSignature.add(lblReturnType, gbc_lblReturnType);

		lblReturnType.setLabelFor(txtReturnType);

		JLabel lblMethodName = new JLabel(I18N.getVal(msg.METHOD_NAME));
		GridBagConstraints gbc_lblMethodName = new GridBagConstraints();
		gbc_lblMethodName.anchor = GridBagConstraints.WEST;
		gbc_lblMethodName.insets = new Insets(0, 0, 5, 5);
		gbc_lblMethodName.gridx = 8;
		gbc_lblMethodName.gridy = 0;
		pnlSignature.add(lblMethodName, gbc_lblMethodName);
		lblMethodName.setLabelFor(txtMethodName);

		comboModifier = new JComboBox();
		GridBagConstraints gbc_comboModifier = new GridBagConstraints();
		gbc_comboModifier.insets = new Insets(0, 0, 5, 5);
		gbc_comboModifier.gridx = 0;
		gbc_comboModifier.gridy = 1;
		pnlSignature.add(comboModifier, gbc_comboModifier);
		comboModifier.setModel(new DefaultComboBoxModel(new String[] { I18N.getVal(msg.PRIVATE),
				I18N.getVal(msg.PUBLIC), I18N.getVal(msg.PROTECTED), I18N.getVal(msg.DEFAULT) }));

		txtReturnType = new JTextField();
		txtReturnType.setColumns(10);
		GridBagConstraints gbc_txtReturnType = new GridBagConstraints();
		gbc_txtReturnType.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtReturnType.gridwidth = 6;
		gbc_txtReturnType.insets = new Insets(0, 0, 5, 5);
		gbc_txtReturnType.gridx = 2;
		gbc_txtReturnType.gridy = 1;
		pnlSignature.add(txtReturnType, gbc_txtReturnType);
//		txtReturnType.setText(MsgHandler.getMsg(msg.VOID));

		txtReturnType.getDocument().addDocumentListener(new ChangeListener());

		txtMethodName = new JTextField();
		GridBagConstraints gbc_txtMethodName = new GridBagConstraints();
		gbc_txtMethodName.insets = new Insets(0, 0, 5, 0);
		gbc_txtMethodName.gridwidth = 6;
		gbc_txtMethodName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMethodName.gridx = 8;
		gbc_txtMethodName.gridy = 1;
		pnlSignature.add(txtMethodName, gbc_txtMethodName);
		txtMethodName.setColumns(10);
		txtMethodName.getDocument().addDocumentListener(new ChangeListener());

		chkStatic = new JCheckBox(I18N.getVal(msg.STATIC));
		GridBagConstraints gbc_chkStatic = new GridBagConstraints();
		gbc_chkStatic.insets = new Insets(0, 0, 0, 5);
		gbc_chkStatic.gridx = 0;
		gbc_chkStatic.gridy = 2;
		pnlSignature.add(chkStatic, gbc_chkStatic);
		chkStatic.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		chkStatic.setHorizontalAlignment(SwingConstants.LEFT);

		chkFinal = new JCheckBox(I18N.getVal(msg.FINAL));
		chkFinal.setHorizontalAlignment(SwingConstants.LEFT);
		chkFinal.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GridBagConstraints gbc_chkFinal = new GridBagConstraints();
		gbc_chkFinal.insets = new Insets(0, 0, 0, 5);
		gbc_chkFinal.gridx = 2;
		gbc_chkFinal.gridy = 2;
		pnlSignature.add(chkFinal, gbc_chkFinal);

		chkAbstract = new JCheckBox(I18N.getVal(msg.ABSTRACT));
		chkAbstract.setHorizontalAlignment(SwingConstants.LEFT);
		chkAbstract.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GridBagConstraints gbc_chkAbstract = new GridBagConstraints();
		gbc_chkAbstract.anchor = GridBagConstraints.WEST;
		gbc_chkAbstract.insets = new Insets(0, 0, 0, 5);
		gbc_chkAbstract.gridx = 4;
		gbc_chkAbstract.gridy = 2;
		pnlSignature.add(chkAbstract, gbc_chkAbstract);

		chkMethodCase = new JCheckBox(I18N.getVal(msg.CASE_SENSITIVE));
		GridBagConstraints gbc_chkCaseMethod = new GridBagConstraints();
		gbc_chkCaseMethod.insets = new Insets(0, 0, 0, 5);
		gbc_chkCaseMethod.gridx = 11;
		gbc_chkCaseMethod.gridy = 2;
		pnlSignature.add(chkMethodCase, gbc_chkCaseMethod);

		chkMethodRegex = new JCheckBox(I18N.getVal(msg.REGEX));
		GridBagConstraints gbc_chkRegexMethod = new GridBagConstraints();
		gbc_chkRegexMethod.insets = new Insets(0, 0, 0, 5);
		gbc_chkRegexMethod.gridx = 13;
		gbc_chkRegexMethod.gridy = 2;
		pnlSignature.add(chkMethodRegex, gbc_chkRegexMethod);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 6;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 3;
		contentPane.add(tabbedPane, gbc_tabbedPane);

		JPanel pnlParams = new JPanel();
		tabbedPane.addTab(I18N.getVal(msg.PARAMS), null, pnlParams, null);
		JPanel pnlExceptions = new JPanel();
		tabbedPane.addTab(I18N.getVal(msg.EXCEPTIONS), null, pnlExceptions, null);

		JScrollPane scrollPane_2 = new JScrollPane();
		pnlExceptions.add(scrollPane_2);

		GridBagLayout gbl_panel2 = new GridBagLayout();
		gbl_panel2.columnWidths = new int[] { 356, 0, 100 };
		gbl_panel2.rowHeights = new int[] { 1, 0 };
		gbl_panel2.columnWeights = new double[] { 1.0, 1.0, 0.0 };
		gbl_panel2.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlParams.setLayout(gbl_panel2);

		JScrollPane scrollParams = new JScrollPane();
		GridBagConstraints gbc_scrollParams = new GridBagConstraints();
		gbc_scrollParams.gridwidth = 2;
		gbc_scrollParams.fill = GridBagConstraints.BOTH;
		gbc_scrollParams.insets = new Insets(0, 0, 0, 5);
		gbc_scrollParams.gridx = 0;
		gbc_scrollParams.gridy = 0;
		pnlParams.add(scrollParams, gbc_scrollParams);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollParams.setViewportView(scrollPane_1);

		DefaultTableModel model = new DefaultTableModel();
		tableParams = new JTable(model);
		tableParams.setToolTipText(I18N.getVal(msg.MSG_PARAMS));
		model.addColumn(I18N.getVal(msg.PARAM_TYPE_LABEL));
		model.addColumn(I18N.getVal(msg.PARAM_NAME_LABEL));

		tableParams.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tableParams.getTableHeader().setReorderingAllowed(false);
		scrollPane_1.setViewportView(tableParams);

		scrollPreview = new JScrollPane();
		GridBagConstraints gbc_scrollPreview = new GridBagConstraints();
		gbc_scrollPreview.fill = GridBagConstraints.BOTH;
		gbc_scrollPreview.gridwidth = 6;
		gbc_scrollPreview.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPreview.gridx = 0;
		gbc_scrollPreview.gridy = 4;
		contentPane.add(scrollPreview, gbc_scrollPreview);

		lblPreview = new JLabel("");
		scrollPreview.setViewportView(lblPreview);
		lblPreview.setFont(new Font("Courier New", Font.BOLD, 14));

		JPanel pnlButtons = new JPanel();
		GridBagConstraints gbc_pnlButtons = new GridBagConstraints();
		gbc_pnlButtons.anchor = GridBagConstraints.EAST;
		gbc_pnlButtons.fill = GridBagConstraints.VERTICAL;
		gbc_pnlButtons.gridx = 2;
		gbc_pnlButtons.gridy = 0;
		pnlParams.add(pnlButtons, gbc_pnlButtons);
		GridBagLayout gbl_pnlButtons = new GridBagLayout();
		gbl_pnlButtons.columnWidths = new int[] { 44 };
		gbl_pnlButtons.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_pnlButtons.columnWeights = new double[] { 0.0 };
		gbl_pnlButtons.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlButtons.setLayout(gbl_pnlButtons);

		btnAdd = new JButton(I18N.getVal(msg.ADD));
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancelar.gridx = 0;
		gbc_btnCancelar.gridy = 0;
		pnlButtons.add(btnAdd, gbc_btnCancelar);

		btnRemove = new JButton(I18N.getVal(msg.REMOVE));
		btnRemove.setEnabled(false);
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemove.gridx = 0;
		gbc_btnRemove.gridy = 1;
		pnlButtons.add(btnRemove, gbc_btnRemove);

		btnClear = new JButton(I18N.getVal(msg.CLEAR));
		btnClear.setEnabled(false);
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClear.insets = new Insets(0, 0, 5, 0);
		gbc_btnClear.gridx = 0;
		gbc_btnClear.gridy = 2;
		pnlButtons.add(btnClear, gbc_btnClear);

		btnUp = new JButton(I18N.getVal(msg.UP));
		btnUp.setEnabled(false);
		GridBagConstraints gbc_btnUp = new GridBagConstraints();
		gbc_btnUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUp.insets = new Insets(0, 0, 5, 0);
		gbc_btnUp.gridx = 0;
		gbc_btnUp.gridy = 3;
		pnlButtons.add(btnUp, gbc_btnUp);

		btnDown = new JButton(I18N.getVal(msg.DOWN));
		btnDown.setEnabled(false);
		GridBagConstraints gbc_btnDown = new GridBagConstraints();
		gbc_btnDown.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDown.gridx = 0;
		gbc_btnDown.gridy = 4;
		pnlButtons.add(btnDown, gbc_btnDown);
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 2;
		gbc_btnCancelar.gridy = 6;

		pnlOuput = new JPanel();
		tabbedPane.addTab(I18N.getVal(msg.OUTPUT), null, pnlOuput, null);
		GridBagLayout gbl_pnlOuput = new GridBagLayout();
		gbl_pnlOuput.columnWidths = new int[] { 507, 209 };
		gbl_pnlOuput.rowHeights = new int[] { 263, 0 };
		gbl_pnlOuput.columnWeights = new double[] { 0.0, 1.0 };
		gbl_pnlOuput.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlOuput.setLayout(gbl_pnlOuput);

		textOutput = new JTextArea();
		GridBagConstraints gbc_textOutput = new GridBagConstraints();
		gbc_textOutput.insets = new Insets(0, 0, 0, 5);
		gbc_textOutput.fill = GridBagConstraints.BOTH;
		gbc_textOutput.gridx = 0;
		gbc_textOutput.gridy = 0;
		pnlOuput.add(textOutput, gbc_textOutput);

		pnlOutputOptions = new JPanel();
		GridBagConstraints gbc_pnlOutputOptions = new GridBagConstraints();
		gbc_pnlOutputOptions.fill = GridBagConstraints.BOTH;
		gbc_pnlOutputOptions.gridx = 1;
		gbc_pnlOutputOptions.gridy = 0;
		pnlOuput.add(pnlOutputOptions, gbc_pnlOutputOptions);
		GridBagLayout gbl_pnlOutputOptions = new GridBagLayout();
		gbl_pnlOutputOptions.columnWidths = new int[] { 99, 0 };
		gbl_pnlOutputOptions.rowHeights = new int[] { 23, 23, 158, 0 };
		gbl_pnlOutputOptions.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_pnlOutputOptions.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlOutputOptions.setLayout(gbl_pnlOutputOptions);

		chkOutputRegex = new JCheckBox(I18N.getVal(msg.REGEX));
		GridBagConstraints gbc_chkRegexOutput = new GridBagConstraints();
		gbc_chkRegexOutput.anchor = GridBagConstraints.NORTHWEST;
		gbc_chkRegexOutput.insets = new Insets(0, 0, 5, 0);
		gbc_chkRegexOutput.gridx = 0;
		gbc_chkRegexOutput.gridy = 0;
		pnlOutputOptions.add(chkOutputRegex, gbc_chkRegexOutput);

		chkOutputCase = new JCheckBox(I18N.getVal(msg.CASE_SENSITIVE));
		GridBagConstraints gbc_chkOutputCase = new GridBagConstraints();
		gbc_chkOutputCase.anchor = GridBagConstraints.NORTH;
		gbc_chkOutputCase.insets = new Insets(0, 0, 5, 0);
		gbc_chkOutputCase.gridx = 0;
		gbc_chkOutputCase.gridy = 1;
		pnlOutputOptions.add(chkOutputCase, gbc_chkOutputCase);

		pnlIgnore = new JPanel();
		pnlIgnore.setBorder(
				new TitledBorder(null, I18N.getVal(msg.IGNORE), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlIgnore = new GridBagConstraints();
		gbc_pnlIgnore.anchor = GridBagConstraints.NORTHWEST;
		gbc_pnlIgnore.gridx = 0;
		gbc_pnlIgnore.gridy = 2;
		pnlOutputOptions.add(pnlIgnore, gbc_pnlIgnore);
		GridBagLayout gbl_pnlIgnore = new GridBagLayout();
		gbl_pnlIgnore.columnWidths = new int[] { 57, 0 };
		gbl_pnlIgnore.rowHeights = new int[] { 0, 0, 0, 23, 0 };
		gbl_pnlIgnore.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_pnlIgnore.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlIgnore.setLayout(gbl_pnlIgnore);

		chkWhiteSpaces = new JCheckBox(I18N.getVal(msg.WHITESPACES));
		GridBagConstraints gbc_chkWhiteSpaces = new GridBagConstraints();
		gbc_chkWhiteSpaces.insets = new Insets(0, 0, 5, 0);
		gbc_chkWhiteSpaces.gridx = 0;
		gbc_chkWhiteSpaces.gridy = 0;
		pnlIgnore.add(chkWhiteSpaces, gbc_chkWhiteSpaces);

		chkLineBreaks = new JCheckBox(I18N.getVal(msg.LINE_BREAKS));
		GridBagConstraints gbc_chkLineBreaks = new GridBagConstraints();
		gbc_chkLineBreaks.anchor = GridBagConstraints.NORTHWEST;
		gbc_chkLineBreaks.insets = new Insets(0, 0, 5, 0);
		gbc_chkLineBreaks.gridx = 0;
		gbc_chkLineBreaks.gridy = 1;
		pnlIgnore.add(chkLineBreaks, gbc_chkLineBreaks);

		chkTabs = new JCheckBox(I18N.getVal(msg.TABS));
		GridBagConstraints gbc_chkTabs = new GridBagConstraints();
		gbc_chkTabs.insets = new Insets(0, 0, 5, 0);
		gbc_chkTabs.anchor = GridBagConstraints.NORTHWEST;
		gbc_chkTabs.gridx = 0;
		gbc_chkTabs.gridy = 2;
		pnlIgnore.add(chkTabs, gbc_chkTabs);

		chkExtraSpaces = new JCheckBox(I18N.getVal(msg.EXTRA_SPACES));
		GridBagConstraints gbc_chkExtraSpaces = new GridBagConstraints();
		gbc_chkExtraSpaces.anchor = GridBagConstraints.NORTHWEST;
		gbc_chkExtraSpaces.gridx = 0;
		gbc_chkExtraSpaces.gridy = 3;
		pnlIgnore.add(chkExtraSpaces, gbc_chkExtraSpaces);

		pnlFields = new JPanel();
		pnlFields.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.addTab(I18N.getVal(msg.FIELDS), null, pnlFields, null);
		GridBagLayout gbl_pnlFields = new GridBagLayout();
		gbl_pnlFields.columnWidths = new int[] { 0, 0, 0, 0, 119, 0, 7, 0, 20, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlFields.rowHeights = new int[] { 0, 36, 31, 0, 0 };
		gbl_pnlFields.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlFields.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		pnlFields.setLayout(gbl_pnlFields);

		lblFieldModifier = new JLabel(I18N.getVal(msg.ACCESS_MODIFIER));
		GridBagConstraints gbc_lblFieldModifier = new GridBagConstraints();
		gbc_lblFieldModifier.anchor = GridBagConstraints.WEST;
		gbc_lblFieldModifier.gridwidth = 2;
		gbc_lblFieldModifier.insets = new Insets(0, 0, 5, 5);
		gbc_lblFieldModifier.gridx = 0;
		gbc_lblFieldModifier.gridy = 0;
		pnlFields.add(lblFieldModifier, gbc_lblFieldModifier);

		lblFieldType = new JLabel(I18N.getVal(msg.FIELD_TYPE));
		GridBagConstraints gbc_lblFieldType = new GridBagConstraints();
		gbc_lblFieldType.anchor = GridBagConstraints.WEST;
		gbc_lblFieldType.insets = new Insets(0, 0, 5, 5);
		gbc_lblFieldType.gridx = 1;
		gbc_lblFieldType.gridy = 0;
		pnlFields.add(lblFieldType, gbc_lblFieldType);

		lblFieldName = new JLabel(I18N.getVal(msg.FIELD_NAME));
		GridBagConstraints gbc_lblFieldName = new GridBagConstraints();
		gbc_lblFieldName.anchor = GridBagConstraints.WEST;
		gbc_lblFieldName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFieldName.gridx = 7;
		gbc_lblFieldName.gridy = 0;
		pnlFields.add(lblFieldName, gbc_lblFieldName);

		comboFieldModifier = new JComboBox();
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

		txtFieldType = new JTextField();
		txtFieldType.setColumns(10);
		pnlFields.add(txtFieldType, gbc_txtFieldType);

//		createAutoSuggestor(txtFieldType, thisFrame);
//		createAutoComboBox(gbc_txtFieldType);
//		createAutoComplete(txtFieldType);
		AutoCompleteBehaviourClassPath autoComplete = new AutoCompleteBehaviourClassPath(txtFieldType);
		
//		autoComplete.setInitialPopupSize(new Dimension(txtFieldType.getWidth()+500, txtFieldType.getHeight()));
		
		txtFieldName = new JTextField();
		txtFieldName.setColumns(10);
		GridBagConstraints gbc_txtFieldName = new GridBagConstraints();
		gbc_txtFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldName.gridwidth = 8;
		gbc_txtFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_txtFieldName.gridx = 7;
		gbc_txtFieldName.gridy = 1;
		pnlFields.add(txtFieldName, gbc_txtFieldName);

		chkFieldStatic = new JCheckBox(I18N.getVal(msg.STATIC));
		chkFieldStatic.setHorizontalAlignment(SwingConstants.LEFT);
		chkFieldStatic.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GridBagConstraints gbc_chkFieldStatic = new GridBagConstraints();
		gbc_chkFieldStatic.insets = new Insets(0, 0, 5, 5);
		gbc_chkFieldStatic.gridx = 1;
		gbc_chkFieldStatic.gridy = 2;
		pnlFields.add(chkFieldStatic, gbc_chkFieldStatic);

		chkFieldAbstract = new JCheckBox(I18N.getVal(msg.ABSTRACT));
		chkFieldAbstract.setHorizontalAlignment(SwingConstants.LEFT);
		chkFieldAbstract.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GridBagConstraints gbc_chkFieldAbstract = new GridBagConstraints();
		gbc_chkFieldAbstract.anchor = GridBagConstraints.WEST;
		gbc_chkFieldAbstract.insets = new Insets(0, 0, 5, 5);
		gbc_chkFieldAbstract.gridx = 2;
		gbc_chkFieldAbstract.gridy = 2;
		pnlFields.add(chkFieldAbstract, gbc_chkFieldAbstract);

		chkFieldFinal = new JCheckBox(I18N.getVal(msg.FINAL));
		chkFieldFinal.setHorizontalAlignment(SwingConstants.LEFT);
		chkFieldFinal.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GridBagConstraints gbc_chkFieldFinal = new GridBagConstraints();
		gbc_chkFieldFinal.insets = new Insets(0, 0, 5, 5);
		gbc_chkFieldFinal.gridx = 3;
		gbc_chkFieldFinal.gridy = 2;
		pnlFields.add(chkFieldFinal, gbc_chkFieldFinal);

		btnAddField = new JButton(I18N.getVal(msg.ADD));
		btnAddField.addActionListener(new AddFieldListener());
		GridBagConstraints gbc_btnAddField = new GridBagConstraints();
		gbc_btnAddField.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddField.gridx = 4;
		gbc_btnAddField.gridy = 2;
		pnlFields.add(btnAddField, gbc_btnAddField);

		btnRemoveField = new JButton(I18N.getVal(msg.REMOVE));
		GridBagConstraints gbc_btnRemoveField = new GridBagConstraints();
		gbc_btnRemoveField.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveField.gridx = 7;
		gbc_btnRemoveField.gridy = 2;
		pnlFields.add(btnRemoveField, gbc_btnRemoveField);
		btnRemoveField.addActionListener(new RemoveFieldListener());

		chkFieldMatchCase = new JCheckBox(I18N.getVal(msg.CASE_SENSITIVE));
		GridBagConstraints gbc_chkFieldMatchCase = new GridBagConstraints();
		gbc_chkFieldMatchCase.insets = new Insets(0, 0, 5, 5);
		gbc_chkFieldMatchCase.gridx = 13;
		gbc_chkFieldMatchCase.gridy = 2;
		pnlFields.add(chkFieldMatchCase, gbc_chkFieldMatchCase);

		chkFieldRegex = new JCheckBox(I18N.getVal(msg.REGEX));
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

		tableFields = new JTable((TableModel) null);
		tableFields.setToolTipText("Double click on item to edit param");
		tableFields.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { I18N.getVal(msg.FIELD_NAME), I18N.getVal(msg.FIELD_TYPE) }));
		// panel_1.add(table, gbc_table);
		tableFields.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableFields.getTableHeader().setReorderingAllowed(false);

//		tableFields.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		scrollPane.setColumnHeaderView(tableFields);
		scrollPane.setViewportView(tableFields);

		textOutput.getDocument().addDocumentListener(new ChangeListener());
		chkOutputRegex.addActionListener(new UpdateOutputOptions());
		chkOutputCase.addActionListener(new UpdateOutputOptions());
		chkWhiteSpaces.addActionListener(new UpdateOutputOptions());
		chkExtraSpaces.addActionListener(new UpdateOutputOptions());
		chkLineBreaks.addActionListener(new UpdateOutputOptions());
		chkTabs.addActionListener(new UpdateOutputOptions());

		chkMethodRegex.addActionListener(new UpdateMethodOptions());
		chkMethodCase.addActionListener(new UpdateMethodOptions());
		chkClassRegex.addActionListener(new UpdateClassOptions());
		chkClassCase.addActionListener(new UpdateClassOptions());

		pnlControlButtons = new JPanel();
		GridBagConstraints gbc_pnlControlButtons = new GridBagConstraints();
		gbc_pnlControlButtons.gridwidth = 6;
		gbc_pnlControlButtons.fill = GridBagConstraints.BOTH;
		gbc_pnlControlButtons.gridx = 0;
		gbc_pnlControlButtons.gridy = 5;
		contentPane.add(pnlControlButtons, gbc_pnlControlButtons);
//		GridBagLayout gbl_panel;
		gbl_pnlControlButtons = new GridBagLayout();
		gbl_pnlControlButtons.columnWidths = new int[] { 50, 50, 50, 20, 50, 50, 50, 50, 147, 36, 67 };
		gbl_pnlControlButtons.rowHeights = new int[] { 0, 0 };
		gbl_pnlControlButtons.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		gbl_pnlControlButtons.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlControlButtons.setLayout(gbl_pnlControlButtons);

		btnCancel = new JButton(I18N.getVal(msg.CANCEL));
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 9;
		gbc_btnCancel.gridy = 0;
		pnlControlButtons.add(btnCancel, gbc_btnCancel);

		btnOk = new JButton(I18N.getVal(msg.OK));
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOk.gridx = 10;
		gbc_btnOk.gridy = 0;
		pnlControlButtons.add(btnOk, gbc_btnOk);

		btnOk.addActionListener(new AddOutputListener());

		/* Adding listeners... */

		comboModifier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updatePreview();
			}
		});

		chkStatic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean checked = chkStatic.isSelected();
				if (checked) {
					chkAbstract.setSelected(false);
				}
				updatePreview();
			}
		});

		chkFinal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean checked = chkFinal.isSelected();
				if (checked) {
					chkAbstract.setSelected(false);
				}
				updatePreview();
			}
		});

		chkAbstract.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean checked = chkAbstract.isSelected();
				if (checked) {
					chkFinal.setSelected(false);
					chkStatic.setSelected(false);
				}

				updatePreview();
			}
		});

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame frame = new FrameAddParam(thisFrame);

				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});

		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableParams.getSelectedRow();
				ComponentUtils.removeRow(tableParams, selectedRow);
				updatePreview();
			}
		});

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ComponentUtils.clearTable(tableParams);
				updatePreview();
			}
		});

		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableParams.getSelectedRow();
				ComponentUtils.moveRowUp(tableParams, selectedRow);
				updatePreview();
			}
		});

		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableParams.getSelectedRow();
				ComponentUtils.moveRowDown(tableParams, selectedRow);
				updatePreview();
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ComponentUtils.closeFrame(thisFrame);
			}
		});

		tableParams.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				updatePreview();
			}
		});

		tableParams.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				updateButtons();
			}

			@Override
			public void focusGained(FocusEvent e) {
				updateButtons();
			}
		});
		updateButtons();
	}

	private void createAutoComplete(JTextField txtFieldType) {
		// Without this, cursor always leaves text field
		txtFieldType.setFocusTraversalKeysEnabled(false);

		Autocomplete autoComplete = new Autocomplete(txtFieldType, new ArrayList<String>());
		txtFieldType.getDocument().addDocumentListener(autoComplete);

		// Maps the tab key to the commit action, which finishes the autocomplete
		// when given a suggestion
		txtFieldType.getInputMap().put(KeyStroke.getKeyStroke("TAB"), Autocomplete.COMMIT_ACTION);
		txtFieldType.getActionMap().put(Autocomplete.COMMIT_ACTION, autoComplete.new CommitAction());
	}

	private void createAutoComboBox(GridBagConstraints gbc_txtFieldType) {
		autoComboBox = new AutoComboBox();
		String[] itemArray = {};
		autoComboBox.setKeyWord(itemArray);
		autoComboBox.getDocument().addDocumentListener(new AutoComboChangeListener());
		pnlFields.add(autoComboBox, gbc_txtFieldType);
	}

	private void createAutoSuggestor(JTextField txtField, Window mainWindow) {

		AutoSuggestor auto = new AutoSuggestor(txtField, mainWindow, null, Color.WHITE.brighter(), Color.BLUE,
				Color.RED, 0.75f) {
			@Override
			public boolean wordTyped(String typedWord) {
				if (typedWord != null && typedWord.length() <= 3) {
					return false;
				}

				// create list for dictionary this in your case might be done via calling a
				// method which queries db and returns results as arraylist
				ArrayList<String> words = new ArrayList<>();
				List<String> wordList = ClassPathUtils.match(typedWord);
				words.addAll(wordList);

				setDictionary(words);

				return super.wordTyped(typedWord);// now call super to check for any matches against newest dictionary
			}
		};
	}

	public void updateParamList() {
		String pattern = autoComboBox.getText();
		List<String> listModel = new ArrayList<String>();

		if (!pattern.equals("")) {
			List<String> match = ClassPathUtils.match(pattern);
			for (String value : match) {
				listModel.add(value);
			}
		}
		autoComboBox.setKeyWord(listModel.toArray(new String[0]));
	}

	public FrameAddOutput(FrameTest thisFrame, OutputAnalyzer out, String klazzName, boolean editMode) {
		this(thisFrame);
		this.editMode = editMode;
		if (editMode) {
			setTitle(I18N.getVal(msg.TITLE_EDIT));
		}
		String methodName = out.getMemberName();
		txtMethodName.setText(methodName);
		chkClassRegex.setSelected(out.isRegex());

		txtClassName.setText(klazzName);
		int value = out.getError().getValue();
		boolean klazzCaseSensitive = out.isKlazzCaseSensitive();
		boolean klazzRecursive = out.isKlazzRecursive();
		boolean klazzRegex = out.isKlazzRegex();
		chkClassRegex.setSelected(klazzRegex);
		chkClassRecursive.setSelected(klazzRecursive);
		chkClassCase.setSelected(klazzCaseSensitive);

		txtValue.setText(String.valueOf(value));

		Set<String> modifiers = out.getModifiers();

		if (modifiers.contains(I18N.getVal(msg.STATIC))) {
			chkStatic.setSelected(true);
		}

		if (modifiers.contains(I18N.getVal(msg.ABSTRACT))) {
			chkAbstract.setSelected(true);
		}

		if (modifiers.contains(I18N.getVal(msg.FINAL))) {
			chkFinal.setSelected(true);
		}

		if (modifiers.contains(I18N.getVal(msg.PUBLIC))) {
			comboModifier.setSelectedItem(I18N.getVal(msg.PUBLIC));
		}

		if (modifiers.contains(I18N.getVal(msg.PRIVATE))) {
			comboModifier.setSelectedItem(I18N.getVal(msg.PRIVATE));
		}

		if (modifiers.contains(I18N.getVal(msg.PROTECTED))) {
			comboModifier.setSelectedItem(I18N.getVal(msg.PROTECTED));
		}
		returnType = out.getReturnType();
		// TODO extrair o tipo de retorno caso seja um array ou collection
		// Class<?> returnKlazz = ReflectionUtils.loadClass(returnType);

		txtReturnType.setText(returnType);

		Object[] params = out.getParams();
		for (Object param : params) {
			Class<? extends Object> paramKlazz = param.getClass();
			// TODO extrair o tipo do parâmetro caso seja um collection
			if (paramKlazz.isArray()) {
				paramKlazz = paramKlazz.getComponentType();
			}
			String paramType = paramKlazz.getName() + "[]";
			addParam(paramType);
		}

		textOutput.setText(out.getExpected());

		boolean outputRegex = out.isOutputRegex();
		boolean outputCaseSensitive = out.isOutputCaseSensitive();
		boolean ignoreWhiteSpaces = out.isIgnoreWhiteSpaces();
		boolean ignoreLinebreaks = out.isIgnoreLinebreaks();
		boolean ignoreTabs = out.isIgnoreTabs();
		boolean ignoreExtraSpaces = out.isIgnoreExtraSpaces();

		chkOutputRegex.setSelected(outputRegex);
		chkOutputCase.setSelected(outputCaseSensitive);
		chkWhiteSpaces.setSelected(ignoreWhiteSpaces);
		chkLineBreaks.setSelected(ignoreLinebreaks);
		chkTabs.setSelected(ignoreTabs);
		chkExtraSpaces.setSelected(ignoreExtraSpaces);

//		TestSet.reset();
//		TestSet.addTest(methodName, out);
	}

	private void updatePreview() {
		methodName = txtMethodName.getText();
		int index = comboModifier.getSelectedIndex();
		visibility = (String) comboModifier.getItemAt(index);
		isStatic = chkStatic.isSelected() ? I18N.getVal(msg.STATIC) + " " : "";
		isFinal = chkFinal.isSelected() ? I18N.getVal(msg.FINAL) + " " : "";
		isAbstract = chkAbstract.isSelected() ? I18N.getVal(msg.ABSTRACT) + " " : "";
		returnType = txtReturnType.getText();
		String preview = visibility + " " + isStatic + isFinal + isAbstract + returnType + " " + methodName;

		String param = "";
		String params = "(";
		DefaultTableModel model = (DefaultTableModel) tableParams.getModel();
		for (int row = 0; row < tableParams.getRowCount(); row++) {
			param = model.getValueAt(row, 0).toString();
			if (param != null && !param.isEmpty()) {
				param = param + " " + model.getValueAt(row, 1).toString();
				if (row != 0) {
					params = params + ", ";
				}
				params = params + param;
			}
		}
		params = params + ")";

		lblPreview.setText(preview + params);

		updateButtons();
	}

	private void updateOutputOptions(Object source) {
		if (source == chkOutputRegex && chkOutputRegex.isSelected()) {
			chkOutputCase.setSelected(false);
			chkWhiteSpaces.setSelected(false);
			chkExtraSpaces.setSelected(false);
			chkLineBreaks.setSelected(false);
			chkTabs.setSelected(false);
		} else if (source == chkOutputCase && chkOutputCase.isSelected()) {
			chkOutputRegex.setSelected(false);
		} else if (source == chkWhiteSpaces && chkWhiteSpaces.isSelected()) {
			chkOutputRegex.setSelected(false);
		} else if (source == chkExtraSpaces && chkExtraSpaces.isSelected()) {
			chkOutputRegex.setSelected(false);
		} else if (source == chkLineBreaks && chkLineBreaks.isSelected()) {
			chkOutputRegex.setSelected(false);
		} else if (source == chkTabs && chkTabs.isSelected()) {
			chkOutputRegex.setSelected(false);
		}

	}

	private void updateClassOptions(Object source) {
		if (source == chkClassRegex && chkClassRegex.isSelected()) {
			chkClassCase.setSelected(false);
		} else if (source == chkClassCase && chkClassCase.isSelected()) {
			chkClassRegex.setSelected(false);
		}
	}

	private void updateMethodOptions(Object source) {
		if (source == chkMethodRegex && chkMethodRegex.isSelected()) {
			chkMethodCase.setSelected(false);
		} else if (source == chkMethodCase && chkMethodCase.isSelected()) {
			chkMethodRegex.setSelected(false);
		}
	}

	private void updateButtons() {
		methodName = txtMethodName.getText();
		returnType = txtReturnType.getText();
		String stringValue = txtValue.getText();
		boolean hasName = methodName != null && !methodName.equals("");
		boolean hasValue = stringValue != null && !stringValue.equals("");
		boolean hasReturn = returnType != null && !returnType.equals("");
		boolean isRowSelected = tableParams.getSelectedRow() >= 0;
		boolean hasOutput = !textOutput.getText().isEmpty();

		btnAdd.setEnabled(hasName && hasReturn);

		boolean hasParams = tableParams.getModel().getRowCount() > 0;
		boolean hasMoreThanOne = tableParams.getModel().getRowCount() > 1;
		btnOk.setEnabled(hasValue && hasName && hasReturn && hasOutput);
		btnRemove.setEnabled(hasParams && isRowSelected);
		btnUp.setEnabled(hasMoreThanOne && isRowSelected);
		btnDown.setEnabled(hasMoreThanOne && isRowSelected);
		btnClear.setEnabled(hasParams);
	}

	public void addParam(String paramType) {
		ComponentUtils.addRow(tableParams, paramType, true, I18N.getVal(msg.PARAM_NAME));
		updatePreview();
	}

	public class ChangeListener implements DocumentListener {

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

	public class AutoComboChangeListener implements DocumentListener {

		public void removeUpdate(DocumentEvent e) {
			updateParamList();
		}

		public void insertUpdate(DocumentEvent e) {
			updateParamList();
		}

		public void changedUpdate(DocumentEvent e) {
			updateParamList();
		}
	}

	class UpdateClassOptions implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			updateClassOptions(source);
		}
	}

	class UpdateMethodOptions implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			updateMethodOptions(source);
		}
	}

	class UpdateOutputOptions implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			updateOutputOptions(source);
		}
	}

	public class AddOutputListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			String methodName = txtMethodName.getText();
			String className = txtClassName.getText();
			String stringValue = txtValue.getText();

			if (methodName == null || methodName.equals("")) {
				JOptionPane.showMessageDialog(thisFrame, I18N.getVal(msg.MSG_METHOD_NAME_MISSING));
			} else if (className == null || className.equals("")) {
				JOptionPane.showMessageDialog(thisFrame, I18N.getVal(msg.MSG_CLASS_NAME_MISSING));
			} else if (stringValue == null || stringValue.equals("")) {
				JOptionPane.showMessageDialog(thisFrame, I18N.getVal(msg.MSG_VALUE_MISSING));
			} else {
				int rowCount = tableParams.getRowCount();
				List<String> params = new ArrayList<String>();
				for (int currentRow = 0; currentRow < rowCount; currentRow++) {
					String param = (String) tableParams.getValueAt(currentRow, 0);

					params.add(param);
				}

				Set<String> modifiers = new HashSet<String>();
				modifiers.add(visibility);
				modifiers.add(isStatic.trim());
				modifiers.add(isAbstract.trim());
				modifiers.add(isFinal.trim());
				returnType = txtReturnType.getText();
				String[] paramArray = params.toArray(new String[0]);
				// Struct struct = new Struct(className, modifiers, methodName, returnType,
				// paramArray);
				// StructSet.addStruct(className, struct);
				// previousFrame.updateTreeStruct();
				String expected = textOutput.getText();
				int value = Integer.valueOf(stringValue);
				Object[] args = new Object[0];
				boolean regex = chkClassRegex.isSelected();

				// MethodAnalyzer methodAnalyzer = new MethodAnalyzer(clazz, methodName,
				// parameterTypes)

				OutputAnalyzer analyzer = FactoryOutputAnalyzr.create(modifiers, returnType, methodName, regex,
						expected, value, args, paramArray);
				boolean klazzRecursive = chkClassRecursive.isSelected();
				boolean outputRegex = chkOutputRegex.isSelected();
				boolean outputCase = chkOutputCase.isSelected();
				boolean ignoreWhiteSpaces = chkWhiteSpaces.isSelected();
				boolean ignoreExtraSpaces = chkExtraSpaces.isSelected();
				boolean ignoreLinebreaks = chkLineBreaks.isSelected();
				boolean ignoreTabs = chkTabs.isSelected();

				analyzer.setKlazzRecursive(klazzRecursive);
				analyzer.setOutputRegex(outputRegex);
				analyzer.setOutputCaseSensitive(outputCase);
				analyzer.setIgnoreWhiteSpaces(ignoreWhiteSpaces);
				analyzer.setIgnoreLinebreaks(ignoreLinebreaks);
				analyzer.setIgnoreTabs(ignoreTabs);
				analyzer.setIgnoreExtraSpaces(ignoreExtraSpaces);

				if (editMode) {
					// TestSet.removeByKey(className);
					Map<String, List<Analyzer>> tests = TestSet.getTests();
					for (String key : tests.keySet()) {
						if (key.equals(className)) {
							List<Analyzer> list = tests.get(key);
							for (Analyzer analzer : list) {
								OutputAnalyzer outputAnalyzer = (OutputAnalyzer) analzer;
								if (outputAnalyzer.getMethodNameWithParams()
										.equals(analyzer.getMethodNameWithParams())) {
									TestSet.remove(analzer);
									TestSet.addTest(key, analyzer);
									previousFrame.updateTreeOutput();
									break;
								}
							}
						}
					}
				} else {
					TestSet.addTest(className, analyzer);
				}

				previousFrame.updateTreeOutput();
				previousFrame.updateButtons();
				ComponentUtils.closeFrame(thisFrame);
			}
		}
	}

	private final class AddFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
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
				
				boolean declared = false;
				boolean fieldRegex = chkFieldRegex.isSelected();
				boolean fieldMatchCase = chkFieldMatchCase.isSelected();
				
				
				FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(classAnalyzer, fieldType, declared, fieldName, fieldRegex, fieldMatchCase);
				
				classAnalyzer.addField(fieldAnalyzer);
				
				String visibility = (String) comboFieldModifier.getSelectedItem();
				if (visibility.equals(msg.PRIVATE.toString())) {
					fieldAnalyzer.addPrivate();
				} else if (visibility.equals(msg.PROTECTED.toString())) {
//					fieldAnalyzer.add(new ProtectedFieldAnalyzer(clazz, declared, fieldName, fieldRegex, fieldMatchCase));
				} else if (visibility.equals(msg.PUBLIC.toString())) {
//					fieldAnalyzer.add(new PublicFieldAnalyzer(clazz, declared, fieldName, fieldRegex, fieldMatchCase));
				}
				
				if (chkFieldStatic.isSelected()) {
//					fieldAnalyzer.add(new StaticFieldAnalyzer(clazz, declared, fieldName, fieldRegex, fieldMatchCase));
				}
				
				if (chkFieldFinal.isSelected()) {
//					fieldAnalyzer.add(new FinalFieldAnalyzer(clazz, declared, fieldName, fieldRegex, fieldMatchCase));
				}
				
				// updateButtons();
//				Map<String, List<Analyzer>> tests = TestSet.getTests();
//				List<Analyzer> list = tests.get(className);
//				
//				if (list == null ) {
//					list = new ArrayList<Analyzer>();
//				}
//				
//				list.add(fieldAnalyzer);
				
//				for (String key : tests.keySet()) {
//					if (key.equals(className)) {
//						List<Analyzer> list = tests.get(key);
//						for (Analyzer analzer : list) {
//							outputAnalyzer outputAnalyzer = (OutputAnalyzer) analzer;
//							if (outputAnalyzer.getMethodNameWithParams()
//									.equals(analyzer.getMethodNameWithParams())) {
//								TestSet.remove(analzer);
//								TestSet.addTest(key, analyzer);
//								previousFrame.updateTreeOutput();
//								break;
//							}
//						}
//				}
				TestSet.addFieldTest(className, classAnalyzer);
				 
				previousFrame.updateTree();
//				previousFrame.updateButtons();
			}
		}
	}

	private final class RemoveFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int selectedRow = tableFields.getSelectedRow();
			if (selectedRow >= 0) {
				ComponentUtils.removeRow(tableFields, selectedRow);
				// updatePreview();
			}
		}
	}

	public class AddStructListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			String fieldName = txtFieldName.getText();
			String fieldType = txtFieldType.getText();

			if (StringUtils.isEmpty(fieldName)) {
				JOptionPane.showMessageDialog(thisFrame, I18N.getVal(msg.MSG_FIELD_TYPE_MISSING));
			} else if (StringUtils.isEmpty(fieldType)) {
				JOptionPane.showMessageDialog(thisFrame, I18N.getVal(msg.MSG_FIELD_NAME_MISSING));
			} else {
				int rowCount = tableFields.getRowCount();
				List<String> fields = new ArrayList<String>();
				for (int currentRow = 0; currentRow < rowCount; currentRow++) {
					String field = (String) tableFields.getValueAt(currentRow, 0);

					fields.add(field);
				}

				Set<String> modifiers = new HashSet<String>();
				modifiers.add(visibility);
				modifiers.add(isStatic.trim());
				modifiers.add(isAbstract.trim());
				modifiers.add(isFinal.trim());

				boolean regex = chkFieldRegex.isSelected();

				// MethodAnalyzer methodAnalyzer = new MethodAnalyzer(clazz, methodName,
				// parameterTypes)
//				FieldAnalyzer analyzer = new FieldAnalyzer(clazz, declared, name, regex, matchCase);

			}
		}
	}
}
