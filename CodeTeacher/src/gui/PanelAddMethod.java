package gui;

import static gui.msg.FrameAddOutputMsg.ABSTRACT;
import static gui.msg.FrameAddOutputMsg.ACCESS_MODIFIER;
import static gui.msg.FrameAddOutputMsg.ADD;
import static gui.msg.FrameAddOutputMsg.CASE_SENSITIVE;
import static gui.msg.FrameAddOutputMsg.CLEAR;
import static gui.msg.FrameAddOutputMsg.DEFAULT;
import static gui.msg.FrameAddOutputMsg.EXCEPTIONS;
import static gui.msg.FrameAddOutputMsg.FIELD_NAME;
import static gui.msg.FrameAddOutputMsg.FIELD_TYPE;
import static gui.msg.FrameAddOutputMsg.FINAL;
import static gui.msg.FrameAddOutputMsg.MSG_CLASS_NAME_MISSING;
import static gui.msg.FrameAddOutputMsg.MSG_FIELD_NAME_MISSING;
import static gui.msg.FrameAddOutputMsg.MSG_FIELD_TYPE_MISSING;
import static gui.msg.FrameAddOutputMsg.MSG_PARAMS;
import static gui.msg.FrameAddOutputMsg.PARAMS;
import static gui.msg.FrameAddOutputMsg.PARAM_NAME_LABEL;
import static gui.msg.FrameAddOutputMsg.PARAM_TYPE_LABEL;
import static gui.msg.FrameAddOutputMsg.PRIVATE;
import static gui.msg.FrameAddOutputMsg.PROTECTED;
import static gui.msg.FrameAddOutputMsg.PUBLIC;
import static gui.msg.FrameAddOutputMsg.REGEX;
import static gui.msg.FrameAddOutputMsg.REMOVE;
import static gui.msg.FrameAddOutputMsg.STATIC;
import static gui.msg.FrameAddOutputMsg.UP;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.io.output.ThresholdingOutputStream;

import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.text.WebTextField;

import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.MethodAnalyzer;
import codeteacher.analyzers.OutputAnalyzer;
import codeteacher.analyzers.PrivateMethodAnalyzer;
import codeteacher.analyzers.ProtectedMethodAnalyzer;
import codeteacher.analyzers.PublicMethodAnalyzer;
import gui.component.AutoComboBox;
import gui.component.AutoCompleteBehaviourClassPath;
import gui.component.Autocomplete;
import gui.component.ComponentUtils;
import gui.msg.FrameAddOutputMsg;
import gui.msg.I18N;
import utils.ClassPathUtils;

public class PanelAddMethod extends WebPanel {

	private FrameAddOutputMsg msg = FrameAddOutputMsg.TITLE;

	private boolean editMode;
	private PanelAddMethod thisPanel;
	private MethodAnalyzer methodAnalyzer;

//	private JPanel contentPane;

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
	private JTextArea textOutput;
	private JCheckBox chkFinal;
	private JCheckBox chkAbstract;
	private JCheckBox chkStatic;
	private String visibility;
	private String returnType;
	private String methodName;
	private FrameTestField previous;
	private String isStatic;
	private String isFinal;
	private String isAbstract;

	/* Class */
	private JPanel pnlClass;
	private JLabel lblClassName;
	private JTextField txtClassName;
	private JLabel lblValue;
	private JTextField txtValue;
	private JCheckBox chkClassRegex;
	private JCheckBox chkClassCase;
	private JCheckBox chkMethodCase;
	private JCheckBox chkMethodRegex;
	private JCheckBox chkClassRecursive;

	/* Method */
	private WebButton btnAddMethod;
	private WebButton btnRemoveMethod;
	private WebSpinner spinMethodType;
	private WebSpinner spinMethodName;
	private WebSpinner spinFinal;
	private WebSpinner spinStatic;
	private WebSpinner spinVisibility;

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

	private AutoComboBox autoComboBox;
	private JEditorPane editorPane;

	private WebPanel pnlMethods;

	private WebLabel lblMethodModifier;

	private WebLabel lblMethodType;

	private WebLabel lblMethodName;

	private WebCheckBox chkMatchCase;

	private WebSpinner spinAbstract;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebFrame frame = new WebFrame();
					frame.getContentPane().add(new PanelAddMethod(null));
					frame.pack();
					frame.center();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the panel.
	 * 
	 * @param previousFrame
	 * @wbp.parser.constructor
	 */
	public PanelAddMethod(final FrameTestField previousFrame) {
		this.previous = previousFrame;
		this.thisPanel = this;
		setBounds(100, 100, 742, 552);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowHeights = new int[] { 0, 0, 221, 0, 0 };
		setLayout(gbl_contentPane);

		pnlValue = new JPanel();
		GridBagConstraints gbc_Value = new GridBagConstraints();
		gbc_Value.anchor = GridBagConstraints.EAST;
		gbc_Value.gridwidth = 6;
		gbc_Value.insets = new Insets(0, 0, 5, 0);
		gbc_Value.fill = GridBagConstraints.VERTICAL;
		gbc_Value.gridx = 0;
		gbc_Value.gridy = 0;
		add(pnlValue, gbc_Value);
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
		add(pnlClass, gbc_pnlClass);
		GridBagLayout gbl_pnlClass = new GridBagLayout();
		gbl_pnlClass.columnWidths = new int[] { 0, 295, 0, 0, 47, 52, 0 };
		gbl_pnlClass.rowHeights = new int[] { 63, 0 };
		gbl_pnlClass.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_pnlClass.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlClass.setLayout(gbl_pnlClass);

		lblClassName = new JLabel("Class name");
		lblClassName.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblClassname = new GridBagConstraints();
		gbc_lblClassname.insets = new Insets(0, 0, 0, 5);
		gbc_lblClassname.gridx = 0;
		gbc_lblClassname.gridy = 0;
		pnlClass.add(lblClassName, gbc_lblClassname);

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

		pnlMethods = new WebPanel();
		pnlMethods.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlMethods_1 = new GridBagConstraints();
		gbc_pnlMethods_1.gridwidth = 2;
		gbc_pnlMethods_1.insets = new Insets(0, 0, 5, 5);
		gbc_pnlMethods_1.gridx = 1;
		gbc_pnlMethods_1.gridy = 2;
//		this.add(pnlMethods, gbc_pnlMethods_1);
		// this.add(pnlMethods);
		GridBagLayout gbl_pnlMethods = new GridBagLayout();
		gbl_pnlMethods.columnWidths = new int[] { 0, 0, 106, 0, 119, 0, 7, 0, 20, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlMethods.rowHeights = new int[] { 0, 36, 31, 0, 0, 0, 0, 0, 0 };
		gbl_pnlMethods.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlMethods.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		pnlMethods.setLayout(gbl_pnlMethods);

		lblMethodModifier = new WebLabel(I18N.getVal(ACCESS_MODIFIER));
		GridBagConstraints gbc_lblMethodModifier = new GridBagConstraints();
		gbc_lblMethodModifier.anchor = GridBagConstraints.WEST;
		gbc_lblMethodModifier.insets = new Insets(0, 0, 5, 5);
		gbc_lblMethodModifier.gridx = 1;
		gbc_lblMethodModifier.gridy = 0;
		pnlMethods.add(lblMethodModifier, gbc_lblMethodModifier);

		comboModifier = new WebComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 3;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 0;
		comboModifier.setModel(new DefaultComboBoxModel(new String[] { I18N.getVal(PRIVATE), I18N.getVal(PUBLIC),
				I18N.getVal(PROTECTED), I18N.getVal(DEFAULT) }));
		pnlMethods.add(comboModifier, gbc_comboBox);

		spinVisibility = new WebSpinner();
		formatSpinner(spinVisibility);

		GridBagConstraints gbc_spinVisibility = new GridBagConstraints();
		gbc_spinVisibility.gridwidth = 2;
		gbc_spinVisibility.insets = new Insets(0, 0, 5, 5);
		gbc_spinVisibility.gridx = 5;
		gbc_spinVisibility.gridy = 0;
		pnlMethods.add(spinVisibility, gbc_spinVisibility);

		chkStatic = new WebCheckBox(I18N.getVal(STATIC));
		chkStatic.setHorizontalAlignment(SwingConstants.LEFT);
//		chkMethodStatic.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GridBagConstraints gbc_chkMethodStatic = new GridBagConstraints();
		gbc_chkMethodStatic.anchor = GridBagConstraints.WEST;
		gbc_chkMethodStatic.insets = new Insets(0, 0, 5, 5);
		gbc_chkMethodStatic.gridx = 4;
		gbc_chkMethodStatic.gridy = 1;
		pnlMethods.add(chkStatic, gbc_chkMethodStatic);

		spinStatic = new WebSpinner();
		formatSpinner(spinStatic);
		GridBagConstraints gbc_spinStatic = new GridBagConstraints();
		gbc_spinStatic.gridwidth = 2;
		gbc_spinStatic.insets = new Insets(0, 0, 5, 5);
		gbc_spinStatic.gridx = 5;
		gbc_spinStatic.gridy = 1;
		pnlMethods.add(spinStatic, gbc_spinStatic);

		chkFinal = new WebCheckBox(I18N.getVal(FINAL));
		chkFinal.setHorizontalAlignment(SwingConstants.LEFT);
//		chkMethodFinal.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GridBagConstraints gbc_chkMethodFinal = new GridBagConstraints();
		gbc_chkMethodFinal.anchor = GridBagConstraints.WEST;
		gbc_chkMethodFinal.insets = new Insets(0, 0, 5, 5);
		gbc_chkMethodFinal.gridx = 4;
		gbc_chkMethodFinal.gridy = 2;
		pnlMethods.add(chkFinal, gbc_chkMethodFinal);

		spinFinal = new WebSpinner();
		formatSpinner(spinFinal);
		GridBagConstraints gbc_spinFinal = new GridBagConstraints();
		gbc_spinFinal.gridwidth = 2;
		gbc_spinFinal.insets = new Insets(0, 0, 5, 5);
		gbc_spinFinal.gridx = 5;
		gbc_spinFinal.gridy = 2;
		pnlMethods.add(spinFinal, gbc_spinFinal);

		chkAbstract = new WebCheckBox(I18N.getVal(ABSTRACT));
		chkAbstract.setHorizontalAlignment(SwingConstants.LEFT);
//		chkMethodFinal.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GridBagConstraints gbc_chkAbstract = new GridBagConstraints();
		gbc_chkAbstract.anchor = GridBagConstraints.WEST;
		gbc_chkAbstract.insets = new Insets(0, 0, 5, 5);
		gbc_chkAbstract.gridx = 4;
		gbc_chkAbstract.gridy = 3;
		pnlMethods.add(chkAbstract, gbc_chkAbstract);

		spinAbstract = new WebSpinner();
		formatSpinner(spinAbstract);
		GridBagConstraints gbc_spinAbstract = new GridBagConstraints();
		gbc_spinAbstract.gridwidth = 2;
		gbc_spinAbstract.insets = new Insets(0, 0, 5, 5);
		gbc_spinAbstract.gridx = 5;
		gbc_spinAbstract.gridy = 3;
		pnlMethods.add(spinAbstract, gbc_spinAbstract);

		lblMethodType = new WebLabel(I18N.getVal(FIELD_TYPE));
		GridBagConstraints gbc_lblMethodType = new GridBagConstraints();
		gbc_lblMethodType.anchor = GridBagConstraints.WEST;
		gbc_lblMethodType.insets = new Insets(0, 0, 5, 5);
		gbc_lblMethodType.gridx = 1;
		gbc_lblMethodType.gridy = 4;
		pnlMethods.add(lblMethodType, gbc_lblMethodType);

		GridBagConstraints gbc_txtMethodType = new GridBagConstraints();
		gbc_txtMethodType.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMethodType.gridwidth = 3;
		gbc_txtMethodType.insets = new Insets(0, 0, 5, 5);
		gbc_txtMethodType.gridx = 2;
		gbc_txtMethodType.gridy = 4;

		txtReturnType = new WebTextField();
		txtReturnType.setColumns(10);
		pnlMethods.add(txtReturnType, gbc_txtMethodType);

		spinMethodType = new WebSpinner();
		formatSpinner(spinMethodType);
		GridBagConstraints gbc_spinMethodType = new GridBagConstraints();
		gbc_spinMethodType.gridwidth = 2;
		gbc_spinMethodType.insets = new Insets(0, 0, 5, 5);
		gbc_spinMethodType.gridx = 5;
		gbc_spinMethodType.gridy = 4;
		pnlMethods.add(spinMethodType, gbc_spinMethodType);

		lblMethodName = new WebLabel(I18N.getVal(FIELD_NAME));
		GridBagConstraints gbc_lblMethodName = new GridBagConstraints();
		gbc_lblMethodName.anchor = GridBagConstraints.WEST;
		gbc_lblMethodName.insets = new Insets(0, 0, 5, 5);
		gbc_lblMethodName.gridx = 1;
		gbc_lblMethodName.gridy = 5;
		pnlMethods.add(lblMethodName, gbc_lblMethodName);

		// createAutoSuggestor(txtMethodType, thisFrame);
		// createAutoComboBox(gbc_txtMethodType);
		// createAutoComplete(txtMethodType);
		AutoCompleteBehaviourClassPath autoComplete = new AutoCompleteBehaviourClassPath(txtReturnType);

		autoComplete.setInitialPopupSize(new Dimension(txtReturnType.getWidth() + 500, txtReturnType.getHeight()));

		txtMethodName = new WebTextField();
		txtMethodName.setColumns(10);
		GridBagConstraints gbc_txtMethodName = new GridBagConstraints();
		gbc_txtMethodName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMethodName.gridwidth = 3;
		gbc_txtMethodName.insets = new Insets(0, 0, 5, 5);
		gbc_txtMethodName.gridx = 2;
		gbc_txtMethodName.gridy = 5;
		pnlMethods.add(txtMethodName, gbc_txtMethodName);

		spinMethodName = new WebSpinner();
		formatSpinner(spinMethodName);
		GridBagConstraints gbc_spinMethodName = new GridBagConstraints();
		gbc_spinMethodName.gridwidth = 2;
		gbc_spinMethodName.insets = new Insets(0, 0, 5, 5);
		gbc_spinMethodName.gridx = 5;
		gbc_spinMethodName.gridy = 5;

		pnlMethods.add(spinMethodName, gbc_spinMethodName);

		chkMatchCase = new WebCheckBox(I18N.getVal(CASE_SENSITIVE));
		GridBagConstraints gbc_chkMethodMatchCase = new GridBagConstraints();
		gbc_chkMethodMatchCase.insets = new Insets(0, 0, 5, 5);
		gbc_chkMethodMatchCase.gridx = 2;
		gbc_chkMethodMatchCase.gridy = 5;
		pnlMethods.add(chkMatchCase, gbc_chkMethodMatchCase);

		chkMethodRegex = new WebCheckBox(I18N.getVal(REGEX));
		GridBagConstraints gbc_chkMethodRegex = new GridBagConstraints();
		gbc_chkMethodRegex.insets = new Insets(0, 0, 5, 5);
		gbc_chkMethodRegex.gridx = 3;
		gbc_chkMethodRegex.gridy = 5;
		pnlMethods.add(chkMethodRegex, gbc_chkMethodRegex);

		btnAddMethod = new WebButton(I18N.getVal(ADD));
		btnAddMethod.addActionListener(new AddMethodListener());

		chkMethodCase = new JCheckBox(I18N.getVal(msg.CASE_SENSITIVE));
		GridBagConstraints gbc_chkCaseMethod = new GridBagConstraints();
		gbc_chkCaseMethod.insets = new Insets(0, 0, 5, 5);
		gbc_chkCaseMethod.gridx = 2;
		gbc_chkCaseMethod.gridy = 6;
		pnlMethods.add(chkMethodCase, gbc_chkCaseMethod);

		chkMethodRegex = new JCheckBox(I18N.getVal(msg.REGEX));
		GridBagConstraints gbc_chkRegexMethod = new GridBagConstraints();
		gbc_chkRegexMethod.insets = new Insets(0, 0, 5, 5);
		gbc_chkRegexMethod.gridx = 3;
		gbc_chkRegexMethod.gridy = 6;
		pnlMethods.add(chkMethodRegex, gbc_chkRegexMethod);
		GridBagConstraints gbc_btnAddMethod = new GridBagConstraints();
		gbc_btnAddMethod.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddMethod.gridx = 5;
		gbc_btnAddMethod.gridy = 6;
		pnlMethods.add(btnAddMethod, gbc_btnAddMethod);

		btnRemoveMethod = new WebButton(I18N.getVal(REMOVE));
		GridBagConstraints gbc_btnRemoveMethod = new GridBagConstraints();
		gbc_btnRemoveMethod.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveMethod.gridx = 6;
		gbc_btnRemoveMethod.gridy = 6;
		pnlMethods.add(btnRemoveMethod, gbc_btnRemoveMethod);
//		btnRemoveMethod.addActionListener(new RemoveMethodListener());

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 6;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 2;
		add(tabbedPane, gbc_tabbedPane);

		tabbedPane.addTab("Methods", null, pnlMethods, null);
		JPanel pnlParams = new JPanel();
		tabbedPane.addTab(I18N.getVal(PARAMS), null, pnlParams, null);
		
		JPanel pnlExceptions = new PanelException();
		tabbedPane.addTab(I18N.getVal(EXCEPTIONS), null, pnlExceptions, null);

		JScrollPane scrollPane_2 = new JScrollPane();
		pnlExceptions.add(scrollPane_2);

		GridBagLayout gbl_panel2 = new GridBagLayout();
		gbl_panel2.columnWidths = new int[] { 356, 0, 100 };
		gbl_panel2.rowHeights = new int[] { 1, 0 };
		gbl_panel2.columnWeights = new double[] { 1.0, 1.0, 0.0 };
		gbl_panel2.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
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
		tableParams.setToolTipText(I18N.getVal(MSG_PARAMS));
		model.addColumn(I18N.getVal(PARAM_TYPE_LABEL));
		model.addColumn(I18N.getVal(PARAM_NAME_LABEL));

		tableParams.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tableParams.getTableHeader().setReorderingAllowed(false);
		scrollPane_1.setViewportView(tableParams);

		scrollPreview = new JScrollPane();
		GridBagConstraints gbc_scrollPreview = new GridBagConstraints();
		gbc_scrollPreview.fill = GridBagConstraints.BOTH;
		gbc_scrollPreview.gridwidth = 6;
		gbc_scrollPreview.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPreview.gridx = 0;
		gbc_scrollPreview.gridy = 3;
		add(scrollPreview, gbc_scrollPreview);

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

		btnAdd = new JButton(I18N.getVal(ADD));
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancelar.gridx = 0;
		gbc_btnCancelar.gridy = 0;
		pnlButtons.add(btnAdd, gbc_btnCancelar);

		btnRemove = new JButton(I18N.getVal(REMOVE));
		btnRemove.setEnabled(false);
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemove.gridx = 0;
		gbc_btnRemove.gridy = 1;
		pnlButtons.add(btnRemove, gbc_btnRemove);

		btnClear = new JButton(I18N.getVal(CLEAR));
		btnClear.setEnabled(false);
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClear.insets = new Insets(0, 0, 5, 0);
		gbc_btnClear.gridx = 0;
		gbc_btnClear.gridy = 2;
		pnlButtons.add(btnClear, gbc_btnClear);

		btnUp = new JButton(I18N.getVal(UP));
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
		gbl_pnlOuput.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
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

		textOutput.getDocument().addDocumentListener(new ChangeListener());
		chkOutputRegex.addActionListener(new UpdateOutputOptions());
		chkOutputCase.addActionListener(new UpdateOutputOptions());
		chkWhiteSpaces.addActionListener(new UpdateOutputOptions());
		chkExtraSpaces.addActionListener(new UpdateOutputOptions());
		chkLineBreaks.addActionListener(new UpdateOutputOptions());
		chkTabs.addActionListener(new UpdateOutputOptions());

//		chkMethodRegex.addActionListener(new UpdateMethodOptions());
//		chkMethodCase.addActionListener(new UpdateMethodOptions());
//		chkClassRegex.addActionListener(new UpdateClassOptions());
//		chkClassCase.addActionListener(new UpdateClassOptions());

		pnlControlButtons = new JPanel();
		GridBagConstraints gbc_pnlControlButtons = new GridBagConstraints();
		gbc_pnlControlButtons.gridwidth = 6;
		gbc_pnlControlButtons.fill = GridBagConstraints.BOTH;
		gbc_pnlControlButtons.gridx = 0;
		gbc_pnlControlButtons.gridy = 4;
		add(pnlControlButtons, gbc_pnlControlButtons);
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
				JFrame frame = new WebFrame();
				frame.setContentPane(new PanelAddParam(thisPanel));
				frame.pack();
				((WebFrame) frame).center();
				frame.setBounds(100, 100, 610, 452);
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
//				ComponentUtils.closeFrame(thisFrame);
			}
		});

		tableParams.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
//				updatePreview();
			}
		});

		tableParams.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
//				updateButtons();
			}

			@Override
			public void focusGained(FocusEvent e) {
//				updateButtons();
			}
		});
//		updateButtons();
	}

	private WebSpinner formatSpinner(WebSpinner webSpinner) {
		webSpinner.setModel(new SpinnerNumberModel(0, 0, 100, 0.1));
		webSpinner.setEditor(new WebSpinner.NumberEditor(webSpinner, "##.##"));
		JFormattedTextField txt = ((JSpinner.NumberEditor) webSpinner.getEditor()).getTextField();

		NumberFormatter formatter = (NumberFormatter) txt.getFormatter();
		DecimalFormat decimalFormat = new DecimalFormat("0.0");
		formatter.setFormat(decimalFormat);

		((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

		return webSpinner;
	}

	private void createAutoComplete(JTextField txtMethodType) {
		// Without this, cursor always leaves text Method
		txtMethodType.setFocusTraversalKeysEnabled(false);

		Autocomplete autoComplete = new Autocomplete(txtMethodType, new ArrayList<String>());
		txtMethodType.getDocument().addDocumentListener(autoComplete);

		// Maps the tab key to the commit action, which finishes the autocomplete
		// when given a suggestion
		txtMethodType.getInputMap().put(KeyStroke.getKeyStroke("TAB"), Autocomplete.COMMIT_ACTION);
		txtMethodType.getActionMap().put(Autocomplete.COMMIT_ACTION, autoComplete.new CommitAction());
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

	public PanelAddMethod(FrameTest thisFrame, OutputAnalyzer out, String klazzName, boolean editMode) {
//		this(thisFrame);
		this.editMode = editMode;
//		if (editMode) {
//			setTitle(I18N.getVal(msg.TITLE_EDIT));
//		}
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
		ComponentUtils.addRow(tableParams, paramType, I18N.getVal(msg.PARAM_NAME));
		updatePreview();
	}
	
	public MethodAnalyzer getMethodAnalyzer() {
		return methodAnalyzer;
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

	class AddMethodListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String className = txtClassName.getText();
			String methodName = txtMethodName.getText();
			String methodType = txtReturnType.getText();
//			String stringValue = txtValue.getText();

//			if (stringValue != null && !stringValue.equals("")) {
//				value = Integer.valueOf(stringValue);
//			}
			if (className == null || className.equals("")) {
				JOptionPane.showMessageDialog(null, I18N.getVal(MSG_CLASS_NAME_MISSING));
			} else if (methodType == null || methodType.equals("")) {
				JOptionPane.showMessageDialog(null, I18N.getVal(MSG_FIELD_TYPE_MISSING));
			} else if (methodName == null || methodName.equals("")) {
				JOptionPane.showMessageDialog(null, I18N.getVal(MSG_FIELD_NAME_MISSING));
			} else {
				boolean regex = chkClassRegex.isSelected();
				boolean matchCase = chkClassCase.isSelected();
				boolean recursive = chkClassRecursive.isSelected();
				int value = 0;
				ClassAnalyzer classAnalyzer = new ClassAnalyzer(className, recursive, matchCase, regex, value);
				DefaultMutableTreeNode parentNode = previous.addToTree(classAnalyzer);

				boolean declared = false;
				boolean methodRegex = chkMethodRegex.isSelected();
				boolean methodCase = chkMethodCase.isSelected();
				int methodValue = spinMethodName.getRound();

				String parameterTypes = "";
				methodAnalyzer = new MethodAnalyzer(classAnalyzer, declared, methodType, methodName,
						methodCase, methodRegex, methodValue, parameterTypes);

				DefaultMutableTreeNode childNode = previous.addToTree(parentNode, methodAnalyzer);

				String visibility = (String) comboModifier.getSelectedItem();
				int visibilityValue = spinVisibility.getRound();
				if (visibility.equals(PRIVATE.toString())) {
					PrivateMethodAnalyzer privateMethodAnalyzer = new PrivateMethodAnalyzer(methodAnalyzer,
							visibilityValue);
					previous.addToTree(childNode, privateMethodAnalyzer);
				} else if (visibility.equals(PROTECTED.toString())) {
					ProtectedMethodAnalyzer protectedMethodAnalyzer = new ProtectedMethodAnalyzer(methodAnalyzer,
							visibilityValue);
					previous.addToTree(childNode, protectedMethodAnalyzer);

				} else if (visibility.equals(PUBLIC.toString())) {
					PublicMethodAnalyzer publicMethodAnalyzer = new PublicMethodAnalyzer(methodAnalyzer,
							visibilityValue);
					previous.addToTree(childNode, publicMethodAnalyzer);
				}
			}
		}
	}
}
