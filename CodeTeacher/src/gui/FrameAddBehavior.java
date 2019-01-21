package gui;

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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.OutputAnalyzer;
import codeteacher.behave.BehaviorSet;
import codeteacher.behave.ConstructorCall;
import codeteacher.behave.factory.FactoryMethodCall;
import gui.component.ButtonRenderer;
import gui.component.ComponentUtils;

public class FrameAddBehavior extends JFrame {

	private static final String OUTPUT_TIP = "Double click on item to edit param";
	private static final String OUTPUT_VALUE = "Value";
	private static final String OUTPUT_TYPE = "Type";
	private static final String OUTPUT = "Output";
	private static final String DOWN = "Down";
	private static final String UP = "Up";
	private static final String CLEAR = "Clear";
	private static final String REMOVE = "Remove";
	//Consertar
	private static final String TITLE = "Add Behavior";
	private static final String TITLE_EDIT = "Edit Method";
	private static final String ADD = "Add";
	private static final String DEFAULT = "default";
	private static final String PROTECTED = "protected";
	private static final String PUBLIC = "public";
	private static final String PRIVATE = "private";
	private static final String ABSTRACT = "abstract";
	private static final String FINAL = "final";
	private static final String STATIC = "static";
	private static final String VOID = "void";
	private static final String ACCESS_MODIFIER = "Access modifier";
	private static final String RETURN_TYPE = "Return type";
	private static final String METHOD_NAME = "Method name";
	private static final String EXCEPTIONS = "Exceptions";
	private static final String PARAMS = "Params";
	private static final String PARAM_TYPE_LABEL = OUTPUT_TYPE;
	private static final String PARAM_NAME_LABEL = "Name";
	private static final String PARAM_TYPE = "java.lang.Object";
	private static final String PARAM_NAME = "param";
	private static final String CANCEL = "Cancel";
	private static final String MSG_PARAMS = OUTPUT_TIP;
	private static final String OK = "OK";
	private boolean editMode;
	private JPanel contentPane;
	private JTextField txtMethodName;
	private JTextField txtReturnType;
	private JLabel lblPreview;
	private JComboBox comboModifier;
	private JLabel lblReturnType;
	private JTable tableParams;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnClear;
	private JButton btnUp;
	private JButton btnDown;
	private JButton btnOk;
	private JCheckBox chkStatic;
	private JButton btnCancel;
	private FrameAddBehavior thisFrame;
	private JScrollPane scrollPreview;
	private JPanel pnlControlButtons;
	private JPanel pnlSignature;
	private GridBagLayout gbl_pnlControlButtons;
	private JPanel pnlOuput;
	private JCheckBox chkFinal;
	private JCheckBox chkAbstract;
	private String visibility;
	private String returnType;
	private String methodName;
	private FrameTest previousFrame;
	private String isStatic;
	private String isFinal;
	private String isAbstract;
	private JPanel panel;
	private JLabel lblClassname;
	private JTextField txtClassName;
	private JLabel lblValue;
	private JTextField txtValue;
	private JScrollPane scrollOutput;
	private JTable tableOutput;
	private JScrollPane scrollMethodParams;
	private JTable tableMethodParams;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) { 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameAddBehavior frame = new FrameAddBehavior(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param previousFrame 
	 * @wbp.parser.constructor
	 */
	public FrameAddBehavior(final FrameTest previousFrame) {
		this.previousFrame = previousFrame;
		setTitle(TITLE);
		this.thisFrame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 683, 552);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {87, 251, 30, 199, 0, 0};
		gbl_contentPane.rowHeights = new int[] {40, 85, 299, 61, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 6;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 475, 47, 52, 0};
		gbl_panel.rowHeights = new int[]{31, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblClassname = new JLabel("Class name");
		lblClassname.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblClassname = new GridBagConstraints();
		gbc_lblClassname.insets = new Insets(0, 0, 0, 5);
		gbc_lblClassname.gridx = 0;
		gbc_lblClassname.gridy = 0;
		panel.add(lblClassname, gbc_lblClassname);
		
		txtClassName = new JTextField();
		txtClassName.setText("Automovel");
		txtClassName.setColumns(10);
		GridBagConstraints gbc_txtClassName = new GridBagConstraints();
		gbc_txtClassName.insets = new Insets(0, 0, 0, 5);
		gbc_txtClassName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtClassName.gridx = 1;
		gbc_txtClassName.gridy = 0;
		panel.add(txtClassName, gbc_txtClassName);
		
		lblValue = new JLabel(OUTPUT_VALUE);
		GridBagConstraints gbc_lblValue = new GridBagConstraints();
		gbc_lblValue.insets = new Insets(0, 0, 0, 5);
		gbc_lblValue.gridx = 2;
		gbc_lblValue.gridy = 0;
		panel.add(lblValue, gbc_lblValue);
		
		txtValue = new JTextField();
		txtValue.setText("7");
		GridBagConstraints gbc_txtValue = new GridBagConstraints();
		gbc_txtValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValue.gridx = 3;
		gbc_txtValue.gridy = 0;
		panel.add(txtValue, gbc_txtValue);
		txtValue.setColumns(10);
		txtValue.getDocument().addDocumentListener(new ChangeListener());
		
		pnlSignature = new JPanel();
		pnlSignature.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlSignature = new GridBagConstraints();
		gbc_pnlSignature.gridwidth = 6;
		gbc_pnlSignature.insets = new Insets(0, 0, 5, 0);
		gbc_pnlSignature.fill = GridBagConstraints.BOTH;
		gbc_pnlSignature.gridx = 0;
		gbc_pnlSignature.gridy = 1;
		contentPane.add(pnlSignature, gbc_pnlSignature);
		GridBagLayout gbl_pnlSignature = new GridBagLayout();
		gbl_pnlSignature.columnWidths = new int[]{0, 0, 0, 0, 119, 0, 7, 0, 20, 0, 0, 0, 0, 0, 0};
		gbl_pnlSignature.rowHeights = new int[]{0, 36, 0, 0};
		gbl_pnlSignature.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlSignature.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlSignature.setLayout(gbl_pnlSignature);
		
		JLabel lblAccessModifier = new JLabel(ACCESS_MODIFIER);
		GridBagConstraints gbc_lblAccessModifier = new GridBagConstraints();
		gbc_lblAccessModifier.insets = new Insets(0, 0, 5, 5);
		gbc_lblAccessModifier.gridx = 0;
		gbc_lblAccessModifier.gridy = 0;
		pnlSignature.add(lblAccessModifier, gbc_lblAccessModifier);
		lblAccessModifier.setLabelFor(comboModifier);
		
		lblReturnType = new JLabel(RETURN_TYPE);
		GridBagConstraints gbc_lblReturnType = new GridBagConstraints();
		gbc_lblReturnType.anchor = GridBagConstraints.WEST;
		gbc_lblReturnType.insets = new Insets(0, 0, 5, 5);
		gbc_lblReturnType.gridx = 2;
		gbc_lblReturnType.gridy = 0;
		pnlSignature.add(lblReturnType, gbc_lblReturnType);
		
		lblReturnType.setLabelFor(txtReturnType);
		
		JLabel lblNewLabel_1 = new JLabel(METHOD_NAME);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 6;
		gbc_lblNewLabel_1.gridy = 0;
		pnlSignature.add(lblNewLabel_1, gbc_lblNewLabel_1);
		lblNewLabel_1.setLabelFor(txtMethodName);
		
		comboModifier = new JComboBox();
		GridBagConstraints gbc_comboModifier = new GridBagConstraints();
		gbc_comboModifier.insets = new Insets(0, 0, 5, 5);
		gbc_comboModifier.gridx = 0;
		gbc_comboModifier.gridy = 1;
		pnlSignature.add(comboModifier, gbc_comboModifier);
		comboModifier.setModel(new DefaultComboBoxModel(new String[] {PRIVATE, PUBLIC, PROTECTED, DEFAULT}));
		
		txtReturnType = new JTextField();
		txtReturnType.setColumns(10);
		GridBagConstraints gbc_txtReturnType = new GridBagConstraints();
		gbc_txtReturnType.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtReturnType.gridwidth = 4;
		gbc_txtReturnType.insets = new Insets(0, 0, 5, 5);
		gbc_txtReturnType.gridx = 2;
		gbc_txtReturnType.gridy = 1;
		pnlSignature.add(txtReturnType, gbc_txtReturnType);
		txtReturnType.setText(VOID);
		
		txtReturnType.getDocument().addDocumentListener(new ChangeListener());
		
		txtMethodName = new JTextField();
		txtMethodName.setText("setNivelCombustivel");
		GridBagConstraints gbc_txtMethodName = new GridBagConstraints();
		gbc_txtMethodName.insets = new Insets(0, 0, 5, 0);
		gbc_txtMethodName.gridwidth = 8;
		gbc_txtMethodName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMethodName.gridx = 6;
		gbc_txtMethodName.gridy = 1;
		pnlSignature.add(txtMethodName, gbc_txtMethodName);
		txtMethodName.setColumns(10);
		txtMethodName.getDocument().addDocumentListener(new ChangeListener());
		
		chkStatic = new JCheckBox("static");
		GridBagConstraints gbc_chkStatic = new GridBagConstraints();
		gbc_chkStatic.insets = new Insets(0, 0, 0, 5);
		gbc_chkStatic.gridx = 0;
		gbc_chkStatic.gridy = 2;
		pnlSignature.add(chkStatic, gbc_chkStatic);
		chkStatic.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		chkStatic.setHorizontalAlignment(SwingConstants.LEFT);
		
		chkFinal = new JCheckBox("final");
		chkFinal.setHorizontalAlignment(SwingConstants.LEFT);
		chkFinal.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GridBagConstraints gbc_chkFinal = new GridBagConstraints();
		gbc_chkFinal.insets = new Insets(0, 0, 0, 5);
		gbc_chkFinal.gridx = 2;
		gbc_chkFinal.gridy = 2;
		pnlSignature.add(chkFinal, gbc_chkFinal);
		
		chkAbstract = new JCheckBox("abstract");
		chkAbstract.setHorizontalAlignment(SwingConstants.LEFT);
		chkAbstract.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		GridBagConstraints gbc_chkAbstract = new GridBagConstraints();
		gbc_chkAbstract.anchor = GridBagConstraints.WEST;
		gbc_chkAbstract.insets = new Insets(0, 0, 0, 5);
		gbc_chkAbstract.gridx = 4;
		gbc_chkAbstract.gridy = 2;
		pnlSignature.add(chkAbstract, gbc_chkAbstract);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 6;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 2;
		contentPane.add(tabbedPane, gbc_tabbedPane);
		
		JPanel pnlParams = new JPanel();
		tabbedPane.addTab(PARAMS, null, pnlParams, null);
		JPanel pnlExceptions = new JPanel();
		tabbedPane.addTab(EXCEPTIONS, null, pnlExceptions, null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		pnlExceptions.add(scrollPane_2);
		
		GridBagLayout gbl_panel2 = new GridBagLayout();
		gbl_panel2.columnWidths = new int[] {356, 0, 100};
		gbl_panel2.rowHeights = new int[]{68, 0, 0};
		gbl_panel2.columnWeights = new double[]{1.0, 1.0, 1.0};
		gbl_panel2.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		pnlParams.setLayout(gbl_panel2);
		
		JScrollPane scrollParams = new JScrollPane();
		GridBagConstraints gbc_scrollParams = new GridBagConstraints();
		gbc_scrollParams.gridwidth = 2;
		gbc_scrollParams.fill = GridBagConstraints.BOTH;
		gbc_scrollParams.insets = new Insets(0, 0, 5, 5);
		gbc_scrollParams.gridx = 0;
		gbc_scrollParams.gridy = 0;
		pnlParams.add(scrollParams, gbc_scrollParams);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollParams.setViewportView(scrollPane_1);
		
		DefaultTableModel model = new DefaultTableModel(); 
		tableParams = new JTable(model);
		tableParams.setToolTipText(MSG_PARAMS);
		model.addColumn(PARAM_TYPE_LABEL);
		model.addColumn(PARAM_NAME_LABEL);
		model.addRow(new Object[]{"java.lang.Float","10"});
		
		tableParams.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tableParams.getTableHeader().setReorderingAllowed(false);
		scrollPane_1.setViewportView(tableParams);
		
		scrollMethodParams = new JScrollPane();
		GridBagConstraints gbc_scrollMethodParams = new GridBagConstraints();
		gbc_scrollMethodParams.gridwidth = 2;
		gbc_scrollMethodParams.insets = new Insets(0, 0, 0, 5);
		gbc_scrollMethodParams.fill = GridBagConstraints.BOTH;
		gbc_scrollMethodParams.gridx = 0;
		gbc_scrollMethodParams.gridy = 1;
		pnlParams.add(scrollMethodParams, gbc_scrollMethodParams);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollMethodParams.setViewportView(scrollPane_3);
		
		DefaultTableModel model2 = new DefaultTableModel(); 
		tableMethodParams = new JTable(model2);
		tableMethodParams.setToolTipText(MSG_PARAMS);
		model2.addColumn(PARAM_TYPE_LABEL);
		model2.addColumn(PARAM_NAME_LABEL);
		model2.addRow(new Object[]{"java.lang.Float","10"});
		
		tableMethodParams.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tableMethodParams.getTableHeader().setReorderingAllowed(false);
		scrollPane_3.setViewportView(tableMethodParams);
		
		tableMethodParams.getColumn(PARAM_TYPE_LABEL).setCellRenderer(new ButtonRenderer());
		
		scrollPreview = new JScrollPane();
		GridBagConstraints gbc_scrollPreview = new GridBagConstraints();
		gbc_scrollPreview.fill = GridBagConstraints.BOTH;
		gbc_scrollPreview.gridwidth = 6;
		gbc_scrollPreview.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPreview.gridx = 0;
		gbc_scrollPreview.gridy = 3;
		contentPane.add(scrollPreview, gbc_scrollPreview);
		
		lblPreview = new JLabel("");
		scrollPreview.setViewportView(lblPreview);
		lblPreview.setFont(new Font("Courier New", Font.BOLD, 14));
		
		JPanel pnlButtons = new JPanel();
		GridBagConstraints gbc_pnlButtons = new GridBagConstraints();
		gbc_pnlButtons.insets = new Insets(0, 0, 5, 0);
		gbc_pnlButtons.anchor = GridBagConstraints.EAST;
		gbc_pnlButtons.fill = GridBagConstraints.VERTICAL;
		gbc_pnlButtons.gridx = 2;
		gbc_pnlButtons.gridy = 0;
		pnlParams.add(pnlButtons, gbc_pnlButtons);
		GridBagLayout gbl_pnlButtons = new GridBagLayout();
		gbl_pnlButtons.columnWidths = new int[] {44};
		gbl_pnlButtons.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_pnlButtons.columnWeights = new double[]{0.0};
		gbl_pnlButtons.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlButtons.setLayout(gbl_pnlButtons);
		
		btnAdd = new JButton(ADD);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancelar.gridx = 0;
		gbc_btnCancelar.gridy = 0;
		pnlButtons.add(btnAdd, gbc_btnCancelar);
		
		btnRemove = new JButton(REMOVE);
		btnRemove.setEnabled(false);
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemove.gridx = 0;
		gbc_btnRemove.gridy = 1;
		pnlButtons.add(btnRemove, gbc_btnRemove);
		
		btnClear = new JButton(CLEAR);
		btnClear.setEnabled(false);
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClear.insets = new Insets(0, 0, 5, 0);
		gbc_btnClear.gridx = 0;
		gbc_btnClear.gridy = 2;
		pnlButtons.add(btnClear, gbc_btnClear);
		
		btnUp = new JButton(UP);
		btnUp.setEnabled(false);
		GridBagConstraints gbc_btnUp = new GridBagConstraints();
		gbc_btnUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUp.insets = new Insets(0, 0, 5, 0);
		gbc_btnUp.gridx = 0;
		gbc_btnUp.gridy = 3;
		pnlButtons.add(btnUp, gbc_btnUp);
		
		btnDown = new JButton(DOWN);
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
		tabbedPane.addTab(OUTPUT, null, pnlOuput, null);
		GridBagLayout gbl_pnlOuput = new GridBagLayout();
		gbl_pnlOuput.columnWidths = new int[]{0, 0};
		gbl_pnlOuput.rowHeights = new int[]{0, 0, 0, 0};
		gbl_pnlOuput.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnlOuput.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		pnlOuput.setLayout(gbl_pnlOuput);
		
		scrollOutput = new JScrollPane();
		GridBagConstraints gbc_scrollOutput = new GridBagConstraints();
		gbc_scrollOutput.insets = new Insets(0, 0, 5, 0);
		gbc_scrollOutput.fill = GridBagConstraints.BOTH;
		gbc_scrollOutput.gridx = 0;
		gbc_scrollOutput.gridy = 0;
		pnlOuput.add(scrollOutput, gbc_scrollOutput);
		
		DefaultTableModel dm = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				OUTPUT_TYPE, OUTPUT_VALUE
			}
		);
		tableOutput = new JTable(dm);
		dm.addRow(new Object[]{"java.lang.Float","20"});
		scrollOutput.setViewportView(tableOutput);
		tableOutput.setToolTipText(OUTPUT_TIP);
		tableOutput.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		
		pnlControlButtons = new JPanel();
		GridBagConstraints gbc_pnlControlButtons = new GridBagConstraints();
		gbc_pnlControlButtons.gridwidth = 6;
		gbc_pnlControlButtons.fill = GridBagConstraints.BOTH;
		gbc_pnlControlButtons.gridx = 0;
		gbc_pnlControlButtons.gridy = 4;
		contentPane.add(pnlControlButtons, gbc_pnlControlButtons);
//		GridBagLayout gbl_panel;
		gbl_pnlControlButtons = new GridBagLayout();
		gbl_pnlControlButtons.columnWidths = new int[] {50, 50, 50, 20, 50, 50, 50, 50, 147, 36, 67};
		gbl_pnlControlButtons.rowHeights = new int[]{0, 0};
		gbl_pnlControlButtons.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_pnlControlButtons.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlControlButtons.setLayout(gbl_pnlControlButtons);
		
		btnCancel = new JButton(CANCEL);
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 9;
		gbc_btnCancel.gridy = 0;
		pnlControlButtons.add(btnCancel, gbc_btnCancel);
		
		btnOk = new JButton(OK);
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOk.gridx = 10;
		gbc_btnOk.gridy = 0;
		pnlControlButtons.add(btnOk, gbc_btnOk);
		
		btnOk.addActionListener(new AddBehaviorListener());
		
		/* Adding listeners...*/
		
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
				if (checked){
					chkAbstract.setSelected(false);
				}
				updatePreview();
			}
		});
		
		chkFinal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean checked = chkFinal.isSelected(); 
				if (checked){
					chkAbstract.setSelected(false);
				}
				
				updatePreview();
			}
		});
		
		chkAbstract.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean checked = chkAbstract.isSelected(); 
				if (checked){
					chkFinal.setSelected(false);
					chkStatic.setSelected(false);
				}
				
				updatePreview();
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame frame = new FrameAddBehaviorParam(thisFrame);
				
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
		} );
		updateButtons();
	}

	public FrameAddBehavior(FrameTest thisFrame, OutputAnalyzer out, String klazzName, boolean editMode) {
		this(thisFrame);
		this.editMode = editMode;
		if (editMode){
			setTitle(TITLE_EDIT);
		}
		String methodName = out.getMemberName();
		txtMethodName.setText(methodName);
		txtClassName.setText(klazzName);
		int value = out.getError().getValue();
		txtValue.setText(String.valueOf(value));
		
		Set<String> modifiers = out.getModifiers();
		
		if (modifiers.contains(STATIC + " ")){
			chkStatic.setSelected(true);
		}
		
		if (modifiers.contains(ABSTRACT + " ")){
			chkAbstract.setSelected(true);
		}
		
		if (modifiers.contains(FINAL + " ")){
			chkFinal.setSelected(true);
		}
		
		if (modifiers.contains(PUBLIC)){
			comboModifier.setSelectedItem(PUBLIC);
		}
		
		if (modifiers.contains(PRIVATE)){
			comboModifier.setSelectedItem(PRIVATE);
		}
		
		if (modifiers.contains(PROTECTED)){
			comboModifier.setSelectedItem(PROTECTED);
		}
		
		txtReturnType.setText(out.getReturnType());
		
		Object[] params = out.getParams();
		for (Object param : params) {
			String paramType = param.getClass().getName();
			addParam(paramType);
		}
		
//		TestSet.reset();
//		TestSet.addTest(methodName, out);
	}

	private void updatePreview() {
		methodName = txtMethodName.getText();
		int index = comboModifier.getSelectedIndex();
		visibility = (String) comboModifier.getItemAt(index);
		isStatic = chkStatic.isSelected() ?  STATIC + " " : "";
		isFinal = chkFinal.isSelected() ? FINAL + " " : ""; 
		isAbstract = chkAbstract.isSelected() ? ABSTRACT + " " : "";
		returnType = txtReturnType.getText();
		String preview = visibility + " " + isStatic + isFinal + isAbstract + returnType + " " + methodName;
		
		String param = "";
		String params = "(";
		DefaultTableModel model = (DefaultTableModel) tableParams.getModel();
		int rowCount = tableParams.getRowCount();
		for (int row=0; row < rowCount; row++){
			if (param == null || param.isEmpty()){
				param = model.getValueAt(row, 1).toString();
			} else {
				param = param + " " +model.getValueAt(row, 1).toString();
			}
			if (row < rowCount-1) {
				params = params + ", ";
			}
			params = params + param;
		}
		params = params + ")";
		
		lblPreview.setText(preview + params);
		
		updateButtons();
	}
	
	private void updateButtons(){
		methodName = txtMethodName.getText();
		returnType = txtReturnType.getText();
		String stringValue = txtValue.getText();
		boolean hasName = methodName != null && !methodName.equals("");
		boolean hasValue = stringValue != null && !stringValue.equals("");
		boolean hasReturn = returnType != null && !returnType.equals("");
		boolean isRowSelected = tableParams.getSelectedRow() >= 0;
		
		btnAdd.setEnabled(hasName && hasReturn);
		
		boolean hasParams = tableParams.getModel().getRowCount() > 0;
		boolean hasMoreThanOne = tableParams.getModel().getRowCount() > 1;
		btnOk.setEnabled(hasValue && hasName && hasReturn);
		btnRemove.setEnabled(hasParams && isRowSelected);
		btnUp.setEnabled(hasMoreThanOne && isRowSelected);
		btnDown.setEnabled(hasMoreThanOne && isRowSelected);
		btnClear.setEnabled(hasParams);
	}
	
	public void addParam(String paramType){
		ComponentUtils.addRow(tableParams, paramType, PARAM_NAME);
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
	
	public class AddBehaviorListener implements ActionListener {

		private static final String MSG_METHOD_NOT_SET = "Method name must be set";
		private static final String MSG_CLASS_NOT_SET = "Class name must be set";
		private static final String MSG_VALUE_NOT_SET = "Value must be set";

		public void actionPerformed(ActionEvent e) {

			String methodName = txtMethodName.getText();
			String className = txtClassName.getText();
			String stringValue = txtValue.getText();
			
			if (methodName == null || methodName.equals("")){
				JOptionPane.showMessageDialog(thisFrame, MSG_METHOD_NOT_SET);
			} else if (className == null || className.equals("")){
				JOptionPane.showMessageDialog(thisFrame, MSG_CLASS_NOT_SET);
			} else if (stringValue == null || stringValue.equals("")){
				JOptionPane.showMessageDialog(thisFrame, MSG_VALUE_NOT_SET);
			} else {
				Set<String> modifiers = new HashSet<String>();
				modifiers.add(visibility);
				modifiers.add(isStatic);
				modifiers.add(isAbstract);
				modifiers.add(isFinal);
				returnType = txtReturnType.getText();

				int rowCount = tableParams.getRowCount();
				List<ConstructorCall> constructorParams = new ArrayList<ConstructorCall>();
				for (int currentRow = 0; currentRow < rowCount; currentRow++) {
					String paramType = (String) tableParams.getValueAt(currentRow, 0);
					String paramValue = (String) tableParams.getValueAt(currentRow, 1);
					
					ConstructorCall constructorCall = new ConstructorCall(paramValue);
					constructorCall.setKlazzName(paramType);
					constructorParams.add(constructorCall);
				}
				
				rowCount = tableMethodParams.getRowCount();
				List<ConstructorCall> methodParams = new ArrayList<ConstructorCall>();
				for (int currentRow = 0; currentRow < rowCount; currentRow++) {
					String paramType = (String) tableMethodParams.getValueAt(currentRow, 0);
					String paramValue = (String) tableMethodParams.getValueAt(currentRow, 1);
					
					ConstructorCall constructorCall = new ConstructorCall(paramValue);
					constructorCall.setKlazzName(paramType);
					methodParams.add(constructorCall);
				}
				
				String outputType = (String) tableOutput.getValueAt(0, 0);
				String outputValue = (String) tableOutput.getValueAt(0, 1);
					
				ConstructorCall outputCall = new ConstructorCall(outputValue);
				outputCall.setKlazzName(outputType);

				Analyzer analyzer = FactoryMethodCall.create(constructorParams, methodName, outputCall, methodParams);
				
				if (editMode){
	//				TestSet.removeByKey(className);
					Map<String, List<Analyzer>> tests = BehaviorSet.getTests();
					for (String key : tests.keySet()) {
						if (key.equals(className)){
							List<Analyzer> list = tests.get(key);
							for (Analyzer executer : list) {
								if (executer.getMemberName().equals(methodName)){
									System.out.println();
									executer = analyzer;
									break;
								}
							}
						}
					}
				}
				BehaviorSet.addTest(className, analyzer);
				
				previousFrame.updateTreeBehavior();
				previousFrame.updateButtons();
				ComponentUtils.closeFrame(thisFrame);
			}
		}
	}
}