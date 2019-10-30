package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import codeteacher.Config;
import codeteacher.analyzers.AbstractAnalyzer;
import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.CompositeAnalyzer;
import codeteacher.analyzers.OutputAnalyzer;
import codeteacher.analyzers.TestSet;
import codeteacher.analyzers.Tester;
import codeteacher.behave.BehaviorSet;
import codeteacher.result.Evaluation;
import codeteacher.result.Performance;
import gui.component.CheckBoxNode;
import gui.component.ComponentUtils;
import gui.msg.FrameTestMsg;
import gui.msg.I18N;
import gui.strategy.ArchiveStrategy;
import gui.strategy.FileStrategy;
import gui.strategy.FileType;
import gui.strategy.JarStrategy;
import gui.strategy.JavaStrategy;
import gui.strategy.OpenResourceStrategy;

@SuppressWarnings("serial")
public class FrameTest extends JFrame {

	private static final FrameTestMsg msg = FrameTestMsg.ADD_BEHAVIOR;
	
	private static final String STUDENT_DIRECTORY = I18N.getVal(msg.STUDENT_DIRECTORY);
	private JTextField txtProjectName;
	private JTextField txtStudentDir;
	private JTable resultTable;
	private FrameTest thisFrame;
	private DynamicTree dynTree;
	private DynamicTree dirTree;
	protected FrameDetailResult detailFrame;
	protected Evaluation eval;
	private DefaultMutableTreeNode output;
	private DefaultMutableTreeNode struct;
	private DefaultMutableTreeNode behave;
	private JButton btnEdit;
	private JButton btnRemove;
	private JButton btnAddBehavior;
	private JButton btnClear;
	private JButton btnAddStruct;
	private JButton btnAddOutput;
	private JButton btnClose;
	private JButton btnRun;
	private JPanel pnlProjOptions;
	private JCheckBox chkCaseSensitive;
	private JCheckBox chkRegex;
	private JCheckBox chkRecursive;

	private Project proj;
	private JPanel pnlOpenOptions;
	private JRadioButton radioJar;
	private JRadioButton radioFolder;
	private JRadioButton radioArchive;
	private JRadioButton radioJava;

	private ButtonGroup radioGroup;
	private ButtonGroup radioGroupFiletype;
	private JPanel pnlFiletype;
	private JRadioButton radioClassType;
	private JRadioButton radioJavaType;
	private JRadioButton radioJarType;
	private JTextField txtSourceFolder;
	private JLabel lblSourceFolder;
	private JPanel pnlButtons;

	/**
	 * Create the panel.
	 */
	public FrameTest() {
		
		thisFrame = this;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{253, 191, 112, 70, 80, 36, 55, 0};
		gridBagLayout.rowHeights = new int[]{51, 45, 39, 110, 29, 95, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel pnlDir = new JPanel();
		pnlDir.setBorder(new TitledBorder(null, STUDENT_DIRECTORY, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlDir = new GridBagConstraints();
		gbc_pnlDir.fill = GridBagConstraints.BOTH;
		gbc_pnlDir.gridwidth = 7;
		gbc_pnlDir.insets = new Insets(0, 0, 5, 0);
		gbc_pnlDir.gridx = 0;
		gbc_pnlDir.gridy = 0;
		getContentPane().add(pnlDir, gbc_pnlDir);
		GridBagLayout gbl_pnlDir = new GridBagLayout();
		gbl_pnlDir.columnWidths = new int[]{69, 246, 6, 70, 0, 0};
		gbl_pnlDir.rowHeights = new int[]{23, 0};
		gbl_pnlDir.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlDir.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlDir.setLayout(gbl_pnlDir);
		
		txtStudentDir = new JTextField();
		GridBagConstraints gbc_tfStudentDir = new GridBagConstraints();
		gbc_tfStudentDir.gridwidth = 3;
		gbc_tfStudentDir.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStudentDir.insets = new Insets(0, 0, 0, 5);
		gbc_tfStudentDir.gridx = 0;
		gbc_tfStudentDir.gridy = 0;
		pnlDir.add(txtStudentDir, gbc_tfStudentDir);
		txtStudentDir.setColumns(10);
		
		JButton btnBrowse = new JButton(I18N.getVal(msg.BROWSE));
		GridBagConstraints gbc_btnBrowseMethod = new GridBagConstraints();
		gbc_btnBrowseMethod.anchor = GridBagConstraints.NORTH;
		gbc_btnBrowseMethod.gridwidth = 2;
		gbc_btnBrowseMethod.gridx = 3;
		gbc_btnBrowseMethod.gridy = 0;
		pnlDir.add(btnBrowse, gbc_btnBrowseMethod);
		
		radioGroup = new ButtonGroup();
		radioGroupFiletype = new ButtonGroup();
		
		JPanel pnlProjName = new JPanel();
		pnlProjName.setBorder(new TitledBorder(null, I18N.getVal(msg.PROJECT_NAME), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlProjName = new GridBagConstraints();
		gbc_pnlProjName.fill = GridBagConstraints.BOTH;
		gbc_pnlProjName.gridwidth = 3;
		gbc_pnlProjName.insets = new Insets(0, 0, 5, 5);
		gbc_pnlProjName.gridx = 0;
		gbc_pnlProjName.gridy = 1;
		getContentPane().add(pnlProjName, gbc_pnlProjName);
		GridBagLayout gbl_pnlProjName = new GridBagLayout();
		gbl_pnlProjName.columnWidths = new int[]{98, 81, 140, 101, 0};
		gbl_pnlProjName.rowHeights = new int[]{23, 0, 0};
		gbl_pnlProjName.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlProjName.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pnlProjName.setLayout(gbl_pnlProjName);
		
		txtProjectName = new JTextField();
		GridBagConstraints gbc_tfProjectName = new GridBagConstraints();
		gbc_tfProjectName.insets = new Insets(0, 0, 5, 0);
		gbc_tfProjectName.gridwidth = 4;
		gbc_tfProjectName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfProjectName.gridx = 0;
		gbc_tfProjectName.gridy = 0;
		pnlProjName.add(txtProjectName, gbc_tfProjectName);
		txtProjectName.setColumns(10);
		
		txtProjectName.addFocusListener(new FocusAdapter() {
		    public void focusLost(FocusEvent e) {
//		        updateTree();
		    }
		});
		
		pnlProjOptions = new JPanel();
		pnlProjOptions.setBorder(new TitledBorder(null, I18N.getVal(msg.OPTIONS), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlProjOptions = new GridBagConstraints();
		gbc_pnlProjOptions.gridwidth = 4;
		gbc_pnlProjOptions.insets = new Insets(0, 0, 5, 0);
		gbc_pnlProjOptions.fill = GridBagConstraints.BOTH;
		gbc_pnlProjOptions.gridx = 3;
		gbc_pnlProjOptions.gridy = 1;
		getContentPane().add(pnlProjOptions, gbc_pnlProjOptions);
		
		chkRecursive = new JCheckBox(I18N.getVal(msg.RECURSIVE));
		pnlProjOptions.add(chkRecursive);
		
		chkCaseSensitive = new JCheckBox(I18N.getVal(msg.CASE_SENSITIVE));
		pnlProjOptions.add(chkCaseSensitive);
		
		chkRegex = new JCheckBox(I18N.getVal(msg.REGEX));
		pnlProjOptions.add(chkRegex);
				
		pnlFiletype = new JPanel();
		pnlFiletype.setBorder(BorderFactory.createTitledBorder(I18N.getVal(msg.FILETYPE_OPTIONS)));
		GridBagConstraints gbc_pnlFiletype = new GridBagConstraints();
		gbc_pnlFiletype.insets = new Insets(0, 0, 5, 5);
		gbc_pnlFiletype.fill = GridBagConstraints.BOTH;
		gbc_pnlFiletype.gridx = 0;
		gbc_pnlFiletype.gridy = 2;
		getContentPane().add(pnlFiletype, gbc_pnlFiletype);
		GridBagLayout gbl_pnlFiletype = new GridBagLayout();
		gbl_pnlFiletype.columnWidths = new int[]{23, 57, 49, 63, 0};
		gbl_pnlFiletype.rowHeights = new int[]{23, 0};
		gbl_pnlFiletype.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlFiletype.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlFiletype.setLayout(gbl_pnlFiletype);
		
		radioClassType = new JRadioButton(I18N.getVal(FileType.CLASS));
		radioClassType.setActionCommand(FileType.CLASS.toString());
		GridBagConstraints gbc_radioButton = new GridBagConstraints();
		gbc_radioButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_radioButton.insets = new Insets(0, 0, 0, 5);
		gbc_radioButton.gridx = 1;
		gbc_radioButton.gridy = 0;
		pnlFiletype.add(radioClassType, gbc_radioButton);
		
		radioJavaType = new JRadioButton(I18N.getVal(FileType.JAVA));
		radioJavaType.setSelected(true);
		radioJavaType.setActionCommand(FileType.JAVA.toString());
		GridBagConstraints gbc_rdbtnjava = new GridBagConstraints();
		gbc_rdbtnjava.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnjava.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnjava.gridx = 2;
		gbc_rdbtnjava.gridy = 0;
		pnlFiletype.add(radioJavaType, gbc_rdbtnjava);
		
		radioJarType = new JRadioButton(I18N.getVal(FileType.JAR));
		radioJarType.setSelected(true);
		radioJarType.setActionCommand(FileType.JAR.toString());
		GridBagConstraints gbc_rdbtnjar = new GridBagConstraints();
		gbc_rdbtnjar.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnjar.gridx = 3;
		gbc_rdbtnjar.gridy = 0;
		pnlFiletype.add(radioJarType, gbc_rdbtnjar);
		
		radioGroupFiletype.add(radioClassType);
		radioGroupFiletype.add(radioJavaType);
		radioGroupFiletype.add(radioJarType);
		
		pnlOpenOptions = new JPanel();
		GridBagConstraints gbc_pnlOpenOptions = new GridBagConstraints();
		gbc_pnlOpenOptions.gridwidth = 2;
		gbc_pnlOpenOptions.insets = new Insets(0, 0, 5, 5);
		gbc_pnlOpenOptions.gridx = 1;
		gbc_pnlOpenOptions.gridy = 2;
		getContentPane().add(pnlOpenOptions, gbc_pnlOpenOptions);
		pnlOpenOptions.setBorder(BorderFactory.createTitledBorder(I18N.getVal(msg.OPEN_OPTIONS)));
		GridBagLayout gbl_pnlOpenOptions = new GridBagLayout();
		gbl_pnlOpenOptions.columnWidths = new int[]{78, 57, 95, 0, 0};
		gbl_pnlOpenOptions.rowHeights = new int[]{23, 0};
		gbl_pnlOpenOptions.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlOpenOptions.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlOpenOptions.setLayout(gbl_pnlOpenOptions);
		
		radioFolder = new JRadioButton(I18N.getVal(msg.FOLDER));
		radioFolder.setActionCommand(msg.FOLDER.toString());
		GridBagConstraints gbc_radioFolder = new GridBagConstraints();
		gbc_radioFolder.anchor = GridBagConstraints.NORTHWEST;
		gbc_radioFolder.insets = new Insets(0, 0, 0, 5);
		gbc_radioFolder.gridx = 0;
		gbc_radioFolder.gridy = 0;
		pnlOpenOptions.add(radioFolder, gbc_radioFolder);
		
		radioJar = new JRadioButton(I18N.getVal(msg.JAR));
		radioJar.setActionCommand(msg.JAR.toString());
		radioJar.setSelected(true);
		GridBagConstraints gbc_radioJar = new GridBagConstraints();
		gbc_radioJar.insets = new Insets(0, 0, 0, 5);
		gbc_radioJar.anchor = GridBagConstraints.NORTHWEST;
		gbc_radioJar.gridx = 1;
		gbc_radioJar.gridy = 0;
		pnlOpenOptions.add(radioJar, gbc_radioJar);
		
		radioArchive = new JRadioButton(I18N.getVal(msg.ARCHIVE));
		radioArchive.setActionCommand(msg.ARCHIVE.toString());
		GridBagConstraints gbc_radioArchive = new GridBagConstraints();
		gbc_radioArchive.insets = new Insets(0, 0, 0, 5);
		gbc_radioArchive.gridx = 2;
		gbc_radioArchive.gridy = 0;
		pnlOpenOptions.add(radioArchive, gbc_radioArchive);
		
		radioJava = new JRadioButton(I18N.getVal(msg.JAVA));
		radioJava.setActionCommand(msg.JAVA.toString());
		GridBagConstraints gbc_radioJava = new GridBagConstraints();
		gbc_radioJava.gridx = 3;
		gbc_radioJava.gridy = 0;
		pnlOpenOptions.add(radioJava, gbc_radioJava);
		radioGroup.add(radioFolder);
		radioGroup.add(radioJar);
		radioGroup.add(radioArchive);
		radioGroup.add(radioJava);
		
		lblSourceFolder = new JLabel(I18N.getVal(msg.SOURCE_FOLDER));
		GridBagConstraints gbc_lblBinFolder = new GridBagConstraints();
		gbc_lblBinFolder.insets = new Insets(0, 0, 5, 5);
		gbc_lblBinFolder.anchor = GridBagConstraints.EAST;
		gbc_lblBinFolder.gridx = 3;
		gbc_lblBinFolder.gridy = 2;
		getContentPane().add(lblSourceFolder, gbc_lblBinFolder);
		
		txtSourceFolder = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 4;
		gbc_textField.gridy = 2;
		getContentPane().add(txtSourceFolder, gbc_textField);
		txtSourceFolder.setColumns(10);
		
		createDirTree();
		createTree();
		
		pnlButtons = new JPanel();
		GridBagConstraints gbc_pnlButtons = new GridBagConstraints();
		gbc_pnlButtons.gridwidth = 7;
		gbc_pnlButtons.insets = new Insets(0, 0, 5, 0);
		gbc_pnlButtons.fill = GridBagConstraints.BOTH;
		gbc_pnlButtons.gridx = 0;
		gbc_pnlButtons.gridy = 4;
		getContentPane().add(pnlButtons, gbc_pnlButtons);
		GridBagLayout gbl_pnlButtons = new GridBagLayout();
		gbl_pnlButtons.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pnlButtons.rowHeights = new int[]{0, 0};
		gbl_pnlButtons.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlButtons.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlButtons.setLayout(gbl_pnlButtons);
		
		btnAddStruct = new JButton(I18N.getVal(msg.ADD_STRUCT_TEST));
		GridBagConstraints gbc_btnAddStruct = new GridBagConstraints();
		gbc_btnAddStruct.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddStruct.gridx = 7;
		gbc_btnAddStruct.gridy = 0;
		pnlButtons.add(btnAddStruct, gbc_btnAddStruct);
		
		btnAddStruct.addActionListener(new AddStructListener());
		
		btnAddOutput = new JButton(I18N.getVal(msg.ADD_OUTPUT_TEST));
		GridBagConstraints gbc_btnAddOutput = new GridBagConstraints();
		gbc_btnAddOutput.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddOutput.gridx = 8;
		gbc_btnAddOutput.gridy = 0;
		pnlButtons.add(btnAddOutput, gbc_btnAddOutput);
		
		chkRecursive.addItemListener(new ItemListener() {
	      public void itemStateChanged(ItemEvent e) {		        
	        if (proj != null ) {
	        	proj.setRecursive(chkRecursive.isSelected());
	        }		        	
	      }
	    });
		
		chkCaseSensitive.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {		        
				if (proj != null ) {
					proj.setCaseSensitive(chkCaseSensitive.isSelected());
				}		        	
			}
		});
		
		chkRegex.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {		        
				if (proj != null ) {
					proj.setRegex(chkRegex.isSelected());
				}		        	
			}
		});
		
		btnAddOutput.addActionListener(new AddOutputActionListener());
		
		btnAddBehavior = new JButton(I18N.getVal(msg.ADD_BEHAVIOR));
		GridBagConstraints gbc_btnAddBehavior = new GridBagConstraints();
		gbc_btnAddBehavior.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddBehavior.gridx = 9;
		gbc_btnAddBehavior.gridy = 0;
		pnlButtons.add(btnAddBehavior, gbc_btnAddBehavior);
		
		btnAddBehavior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame frame = new FrameAddBehavior(thisFrame);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		
		btnRemove = new JButton(I18N.getVal(msg.REMOVE));
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemove.gridx = 10;
		gbc_btnRemove.gridy = 0;
		pnlButtons.add(btnRemove, gbc_btnRemove);
		
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath currentSelection = dynTree.tree.getSelectionPath();
				if (currentSelection != null) {
					DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection
							.getLastPathComponent());
					Object userObject = currentNode.getUserObject();
					if (userObject instanceof Analyzer) {
						TestSet.remove((Analyzer) userObject);
					}
				}
				dynTree.removeCurrentNode();
				
			}
		});
		
		btnEdit = new JButton(I18N.getVal(msg.EDIT));
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.insets = new Insets(0, 0, 0, 5);
		gbc_btnEdit.gridx = 11;
		gbc_btnEdit.gridy = 0;
		pnlButtons.add(btnEdit, gbc_btnEdit);
		
		btnEdit.addActionListener(new EditListener());
		
		btnClear = new JButton(I18N.getVal(msg.CLEAR));
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.insets = new Insets(0, 0, 0, 5);
		gbc_btnClear.gridx = 12;
		gbc_btnClear.gridy = 0;
		pnlButtons.add(btnClear, gbc_btnClear);
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TestSet.reset();
				BehaviorSet.reset();
				if (eval != null ) eval.reset();
				dynTree.clear();
				thisFrame.repaint();
			}
		});
		
		btnRun = new JButton(I18N.getVal(msg.RUN));
		GridBagConstraints gbc_btnRun = new GridBagConstraints();
		gbc_btnRun.insets = new Insets(0, 0, 0, 5);
		gbc_btnRun.gridx = 13;
		gbc_btnRun.gridy = 0;
		pnlButtons.add(btnRun, gbc_btnRun);
		
		btnRun.addActionListener(new RunListener());
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 5;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		resultTable = new JTable();
		scrollPane.setViewportView(resultTable);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//		table.setModel(new DefaultTableModel(
		//			new Object[][] {
		//				{null, null, null, null},
		//				{null, null, null, null},
		//				{null, null, null, null},
		//				{null, null, null, null},
		//			},
		//			new String[] {
		//				"Aluno", "Tipo", "Valor", "Descri\u00E7\u00E3o"
		//			}
		//		));
		resultTable.setCellSelectionEnabled(true);
		resultTable.setColumnSelectionAllowed(true);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu(I18N.getVal(msg.FILE));
		menuBar.add(menuFile);
		
		JMenuItem menuNew = new JMenuItem(I18N.getVal(msg.NEW));
		menuFile.add(menuNew);
		
		JMenuItem menuOpen = new JMenuItem(I18N.getVal(msg.OPEN));
		menuFile.add(menuOpen);
		
		JMenuItem menuSave = new JMenuItem(I18N.getVal(msg.SAVE));
		menuFile.add(menuSave);
		
		JSeparator separator = new JSeparator();
		menuFile.add(separator);
		
		JMenuItem menuClose = new JMenuItem(I18N.getVal(msg.CLOSE));
		menuFile.add(menuClose);
		
		btnClose = new JButton(I18N.getVal(msg.CLOSE));
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.anchor = GridBagConstraints.SOUTH;
		gbc_btnClose.gridx = 6;
		gbc_btnClose.gridy = 6;
		getContentPane().add(btnClose, gbc_btnClose);
		
		// Adding listeners...
		menuNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TestSet.reset();
				updateTreeOutput();
			}
		});
		
		menuOpen.addActionListener(new OpenFileListener());
		menuSave.addActionListener(new SaveFileListener());
		menuClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ComponentUtils.closeFrame(thisFrame);
			}
		});
		
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new BrowseAction(I18N.getVal(msg.STUDENT_DIR), new ImageIcon("action.gif"));
			}
		});
		
		dynTree.tree.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		    	if (me.getClickCount() == 1) { 
		        	updateButtons();
		        }
		    	if (me.getClickCount() == 2) { 
		    		showEditFrame();
			    }
		    }
		});
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ComponentUtils.closeFrame(thisFrame);
			}
		});
		
		resultTable.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		        JTable table =(JTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table.rowAtPoint(p);
		        
		        if (me.getClickCount() == 2) {
		            String student = (String) table.getModel().getValueAt(row, 0);
		        	Performance perform = eval.getPerforMap().get(student);
		        	
		        	detailFrame = new FrameDetailResult(thisFrame, perform, eval);
		        	detailFrame.setTitle(I18N.getVal(msg.DETAIL_RESULT));
		        	detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        	
//		        	detailFrame.setSize(600, 450);
		        	detailFrame.setLocationRelativeTo(null);
		        	detailFrame.setVisible(true);
		        }
		    }
		});
		updateButtons();
		eval = new Evaluation();
	}

	public FrameTest(Project proj) {
		this();
		this.proj = proj;
		Config cfg = proj.getCfg();
		
		String workingPath = cfg.getStudentDir();
		File selFile = new File(workingPath);
		selectFile(selFile);
		
		txtProjectName.setText(cfg.getProjectName());
		txtStudentDir.setText(cfg.getStudentDir());
		
		chkRegex.setSelected(proj.isRegex());
		chkRecursive.setSelected(proj.isRecursive());
		chkCaseSensitive.setSelected(proj.isCaseSensitive());
		
		OpenResourceStrategy strategy = proj.getStrategy();
		if (strategy instanceof JarStrategy) {
			radioJar.setSelected(true);
		} else if (strategy instanceof JavaStrategy) {
			radioJava.setSelected(true);
		} else if (strategy instanceof FileStrategy) {
			radioFolder.setSelected(true);
		} else if (strategy instanceof ArchiveStrategy) {
			radioArchive.setSelected(true);
		}
		
		TestSet.reset();
		TestSet.getTests().putAll(proj.getTests());
		updateTreeOutput();
		updateButtons();
	}

	public void updateButtons() {
		boolean empty = dynTree.tree.getRowCount() > 0;
		btnClear.setEnabled(empty);
		btnRemove.setEnabled(empty);
		
		TreePath currentSelection = dynTree.tree.getSelectionPath();
		if (currentSelection != null) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection
					.getLastPathComponent());
			Object userObject = currentNode.getUserObject();
			boolean editable = userObject instanceof OutputAnalyzer;
			btnEdit.setEnabled(editable);
		} else {
			btnEdit.setEnabled(false);
		}		
	}
	
	protected void createDirTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(null);
		dirTree = new DynamicTree(root, true);
		
		GridBagConstraints gbc_dirtree = new GridBagConstraints();
		gbc_dirtree.insets = new Insets(0, 0, 5, 5);
		gbc_dirtree.fill = GridBagConstraints.BOTH;
		gbc_dirtree.gridx = 0;
		gbc_dirtree.gridy = 3;
		gbc_dirtree.gridwidth = 2;
		
		getContentPane().add(dirTree, gbc_dirtree);
	}
	
	protected void createTree() {
//		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(txtProjectName.getText());
		dynTree = new DynamicTree(new DefaultMutableTreeNode(I18N.getVal(msg.TESTS)));
		output = new DefaultMutableTreeNode("Output");
		struct = new DefaultMutableTreeNode("Struct");
		behave = new DefaultMutableTreeNode("Behave");
		 
		output = dynTree.addObject(output);
		struct = dynTree.addObject(struct);
		behave = dynTree.addObject(behave);
		
//		dynTree.tree.setModel(new DefaultTreeModel(
//			new DefaultMutableTreeNode(TESTS) {
//				{
//				}
//			}
//		));
		GridBagConstraints gbc_tree = new GridBagConstraints();
		gbc_tree.insets = new Insets(0, 0, 5, 0);
		gbc_tree.fill = GridBagConstraints.BOTH;
		gbc_tree.gridx = 2;
		gbc_tree.gridy = 3;
		gbc_tree.gridwidth = 5;
		
		getContentPane().add(dynTree, gbc_tree);
	}

	public DynamicTree getTree(){
		return dynTree;
	}
	
	protected void update() {
		thisFrame.setVisible(false);
		thisFrame.setVisible(true);
	}

//	protected void updateTree() {
//		createTree();
//		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(txtProjectName.getText());
//		DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
//		tree.setModel(treeModel);
//		update();
//	}
	
	public void updateTree(DefaultMutableTreeNode rootNode){
		Map<String, List<Analyzer>> tests = TestSet.getTests();
		for (String key : tests.keySet()){
			DefaultMutableTreeNode newNode = dynTree.addObject(rootNode, key, true);
			TreePath treePath = new TreePath(newNode.getPath());
			for (Analyzer e : tests.get(key)){
				dynTree.setSelectionPath(treePath);
				dynTree.addObject(newNode, e, true);
//				(DefaultMutableTreeNode) (parentPath.getLastPathComponent());
			}
		}
	}

	private void addToTree(DefaultMutableTreeNode root, Analyzer a) {
		
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(
				 new CheckBoxNode(a, true));
		
//		DefaultMutableTreeNode childNode = dynTree.addObject(root, a, true);
		root.add(node);
		
		TreePath treePath = new TreePath(node.getPath());
		dynTree.setSelectionPath(treePath);
		
		if (a instanceof CompositeAnalyzer) {
			CompositeAnalyzer<?> c = (CompositeAnalyzer<?>) a;
			List<? extends AbstractAnalyzer> children = c.getChildren();
			for(AbstractAnalyzer child : children) {
				addToTree(node, child);
			}
		}
	}
	
	public void updateTree() {
		DefaultMutableTreeNode rootNode = dynTree.rootNode;
		
		DefaultMutableTreeNode fields = new DefaultMutableTreeNode("Fields");
		 
		fields = dynTree.addObject(rootNode, fields, true);
		
//		Map<String, List<CompositeAnalyzer>> tests = TestSet.getFieldTests();
//		
//		for (String klazzName : tests.keySet()){
////			DefaultMutableTreeNode node = dynTree.addObject(fields, klazzName, true);
//			
//			for (CompositeAnalyzer<?> a : tests.get(klazzName)){
//				addToTree(fields, a);
//			}
//		}		
	}
	
	public void updateTreeOutput(){
		
		Map<String, List<Analyzer>> tests = TestSet.getTests();
		//output = new DefaultMutableTreeNode("Output");
		for (String klazzName : tests.keySet()){
			//adding parent node
			DefaultMutableTreeNode node = dynTree.addObject(output, klazzName, true);
			TreePath treePath = new TreePath(node.getPath());
			DefaultTreeModel treeModel = new DefaultTreeModel(output);
			
			for (Analyzer e : tests.get(klazzName)){
				OutputAnalyzer oa = (OutputAnalyzer) e;
				dynTree.setSelectionPath(treePath);
				for (int index = 0; index < treeModel.getChildCount(node); index++){
					DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) treeModel.getChild(node, index);
					OutputAnalyzer analyzr = (OutputAnalyzer) childNode.getUserObject();
					if (analyzr.getMethodNameWithParams().equals(oa.getMethodNameWithParams())){
						TreePath childTreePath = new TreePath(childNode.getPath());
						dynTree.setSelectionPath(childTreePath);
						dynTree.removeCurrentNode();
					}
				} 
				dynTree.addObject(node, e, true);
				
				//adding child node
				//dynTree.addObject(node, newChild, true);
//				(DefaultMutableTreeNode) (parentPath.getLastPathComponent());
			}
		}
//		this.getContentPane().add(dynTree);
	}

	protected void updateTreeBehavior(){
		
		Map<String, List<Analyzer>> tests = BehaviorSet.getTests();
		for (String key : tests.keySet()){
			DefaultMutableTreeNode node = dynTree.addObject(behave, key, true);
			TreePath treePath = new TreePath(node.getPath());
			for (Analyzer e : tests.get(key)){
				dynTree.setSelectionPath(treePath);
				dynTree.addObject(node, e, true);
//				(DefaultMutableTreeNode) (parentPath.getLastPathComponent());
			}
		}
		this.getContentPane().add(dynTree);
	}
	
//	protected void updateTreeStruct(){
//		Map<String, List<Executer>> structs = StructSet.getStructs();
//		for (String key : structs.keySet()){
//			DefaultMutableTreeNode node = dynTree.addObject(struct, key, true);
//			TreePath treePath = new TreePath(node.getPath());
//			for (Executer e : structs.get(key)){
//				dynTree.setSelectionPath(treePath);
//				dynTree.addObject(node, e, true);
////				(DefaultMutableTreeNode) (parentPath.getLastPathComponent());
//			}
//		}
//		this.getContentPane().add(dynTree);
//	}

	public void addListener(AbstractButton button, ActionListener listener){
		button.addActionListener(listener);
	}
	
	private final class AddStructListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
//				DefaultMutableTreeNode node = (DefaultMutableTreeNode) 
//				dynTree.getLastSelectedPathComponent(); 
			
			DefaultMutableTreeNode node = output;
			
			JFrame frame = null;
//				if (node == null || node.getParent() == null){
				frame = new FrameAddStruct(thisFrame, node, null, null);
//				} else {
//					node = (node.getChildCount() == 0) ? (DefaultMutableTreeNode) node.getParent() : node;
//					frame = new FrameAddTest(thisFrame, node.toString(), null);
//				}
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
	}
	private final class EditListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			showEditFrame();
		}
	}

	private void showEditFrame() {
		TreePath currentSelection = dynTree.tree.getSelectionPath();
		if (currentSelection != null) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection
					.getLastPathComponent());
			Object userObject = currentNode.getUserObject();
			if (userObject instanceof OutputAnalyzer){
				OutputAnalyzer executer = (OutputAnalyzer) userObject;
				TreeNode parent = currentNode.getParent();
				JFrame frame = new FrameAddOutput(thisFrame, executer, parent.toString(), true);
				
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		}
	}
	
	class SaveFileListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		    		I18N.getVal(msg.FILE_TYPE), I18N.getVal(msg.EXTENSION));
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showSaveDialog(thisFrame);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	String filePath = chooser.getSelectedFile().getAbsolutePath();
		    	FileOutputStream fout;
				try {
					fout = new FileOutputStream(filePath);
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					Config cfg = Tester.getConfig();
					Map<String, List<Analyzer>> tests = TestSet.getTests();
					boolean regex = chkRegex.isSelected();
					boolean caseSensitive = chkCaseSensitive.isSelected();
					boolean recursive = chkRecursive.isSelected();
					Project p = new Project(cfg, tests, caseSensitive, regex, recursive);
					oos.writeObject(tests);
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, I18N.getVal(msg.FILE_NOT_FOUND));
					e1.printStackTrace();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, I18N.getVal(msg.IO_EXCEPTION));
					e1.printStackTrace();
				}
		    }
		}
	}
	
	private void selectFile(File selFile) {
		if (selFile != null) {
			txtStudentDir.setText(selFile.getPath());
			DefaultMutableTreeNode root = new DefaultMutableTreeNode(selFile.getName());
			TreeModel model = new DefaultTreeModel(root);
			dirTree.setModel(model);
			dirTree.addChildren(root, selFile);
		}
	}
	
	class BrowseAction extends AbstractAction {
		public BrowseAction(String text, Icon icon) {
			super(text, icon);
			String filename = File.separator+"tmp";
			JFileChooser fc = new JFileChooser(new File(filename));
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			JFrame frame = new JFrame();
			// Show open dialog; this method does not return until the dialog is 

			fc.showOpenDialog(frame);
			File selFile = fc.getSelectedFile();
			selectFile(selFile);
		}

		public void actionPerformed(ActionEvent e) {
//			System.out.println("Action [" + e.getActionCommand()
//					+ "] performed!");
		}
		
		void addChildren(DefaultMutableTreeNode parent, File parentFile) {
//			File parentFile = (File) parent.getUserObject();
			File[] children = parentFile.listFiles();
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					DefaultMutableTreeNode child = new DefaultMutableTreeNode(children);
					parent.add(child);
					if (children[i].isDirectory())
						addChildren(child, children[i]);
				}
			}
		}
	}
	
	class OpenFileListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		    		I18N.getVal(msg.FILTER_MSG), I18N.getVal(msg.EXTENSION));
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(thisFrame);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	String filePath = chooser.getSelectedFile().getAbsolutePath();
				try(
			      InputStream file = new FileInputStream(filePath);
			      InputStream buffer = new BufferedInputStream(file);
			      ObjectInput input = new ObjectInputStream(buffer);
			    ){
					Map<String, List<Analyzer>> recovered = (Map<String, List<Analyzer>>) input.readObject();
					TestSet.reset();
					TestSet.getTests().putAll(recovered);
					updateTreeOutput();
					updateButtons();
			    }
			    catch(ClassNotFoundException ex){
			    	System.out.println("Cannot perform input. Class not found." + ex);
			    }
			    catch(IOException ex){
			    	System.out.println("Cannot perform input." + ex);
			    }
		    }
		}
	}
	
	class AddOutputActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			JFrame frame = new FrameAddOutput(thisFrame);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			
//			String klazzName = "MetodoGetClass";
//			boolean regex = chkRegex.isSelected();
//			Set<String> mod = new HashSet<String>();
//			mod.add("public");
//			mod.add("static");
//			String returnType = "void";
//			String methodName = "main";
//			String expected = "KKK";
//			int value = 10;
//			Object obj = Array.newInstance(String.class, 0);
//			Object[] args = {obj};
//			String[] paramArray = new String[0];
//			OutputAnalyzer executer = FactoryOutputAnalyzr.create(mod, returnType , methodName, regex, expected , value, args, paramArray);
//			
//			JFrame frame = new FrameAddOutput(thisFrame, executer, klazzName, true);
//			
//			frame.setLocationRelativeTo(null);
//			frame.setVisible(true);
			
		}
	}
	
	class RunListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String studentDir = txtStudentDir.getText();
			if (studentDir.equals("")){
				JOptionPane.showMessageDialog(thisFrame, I18N.getVal(msg.MSG_STUDENT_FOLDER));
			} else if (!new File(studentDir).isDirectory()) {
				JOptionPane.showMessageDialog(thisFrame, I18N.getVal(msg.MSG_FOLDER_NOT_EXISTS));
			} else if (txtProjectName.getText().equals("")){
				JOptionPane.showMessageDialog(thisFrame, I18N.getVal(msg.MSG_PROJECT_NAME));
			} else {
				Config cfg = createConfig();
				Tester.setConfig(cfg);
				proj.setCfg(cfg);
				
				boolean regex = chkRegex.isSelected();
				boolean caseSensitive = chkCaseSensitive.isSelected();
				boolean recursive = chkRecursive.isSelected();
				proj.setCaseSensitive(caseSensitive);
				proj.setRegex(regex);
				proj.setRecursive(recursive);
				
				//TODO Valorar os pontos
				Config.setValues();
				
				Map<String, List<Analyzer>> tests = TestSet.getTests();
				proj.setTests(tests);
				
//				TestSetAtividade01.setUp();
//				EvaluationReturn eval = Tester.evaluate(TestSetAtividade01.getTests());
				
				String selection = radioGroup.getSelection().getActionCommand();
				String fileType = radioGroupFiletype.getSelection().getActionCommand();
				
				OpenResourceStrategy strategy = new JarStrategy();
				if (selection.equals(msg.FOLDER.toString())) {
					strategy = new FileStrategy();
				} else if (selection.equals(msg.ARCHIVE.toString())) {
					strategy = new ArchiveStrategy();
				} else if (selection.equals(msg.JAVA.toString())) {
					strategy = new JavaStrategy(eval);
					strategy.setFileType(fileType);					
				}
				proj.setStrategy(strategy);
				eval = strategy.eval(proj);
				
//				eval = Tester.evaluate(BehaviorSet.getTests());
//				eval = Tester.evaluate(StructSet.getStructs());
				
				resultTable.setModel(new DefaultTableModel(
						new Object[][] {
//							{null, null, null, null},
//							{null, null, null, null},
//							{null, null, null, null},
//							{null, null, null, null},
						},
						new String[] {
							I18N.getVal(msg.ALUNO), I18N.getVal(msg.ERROS), I18N.getVal(msg.NOTA)
						}
					));
				DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
				
				Map<String, Performance> perforMap = eval.getPerforMap();
				Set<String> keySet = perforMap.keySet();
				ArrayList<String> arrayList = new ArrayList<String>(keySet);
				java.util.Collections.sort(arrayList);
				for (String key : arrayList){
					Performance perform = perforMap.get(key);
					String student = perform.getStudent();
					int totalErrors = perform.getTotalErrors();
					float hitsPercentage = perform.getHitsPercentage()/10;
					model.addRow(new String[] {student, String.valueOf(totalErrors), String.valueOf(hitsPercentage)});
//					for (Error e : perform.getErrors()){
//						String desc = e.getDescription();
//						String msg = e.getMessage();
//						String value = String.valueOf(e.getValue());
//						model.addRow(new String[] {student, desc, value, msg});
//		
//					}
				}
				resultTable.getTableHeader().setResizingAllowed(true);
				resultTable.getColumnModel().getColumn(0).setPreferredWidth(474);
				resultTable.getColumnModel().getColumn(1).setPreferredWidth(50);
				resultTable.getColumnModel().getColumn(2).setPreferredWidth(50);
//				table.getColumnModel().getColumn(3).setPreferredWidth(239);
			}
		}

		private Config createConfig() {
			String studentDir = txtStudentDir.getText();
			studentDir.replaceAll("/", File.separator);
			studentDir.concat(File.separator);
			
			String projectName = txtProjectName.getText();
			Config cfg = new Config(studentDir, projectName);
			return cfg;
		}
	}
	
	public void addPerformance(Performance p) {
		eval.getPerforMap().put(p.getStudent(), p);
	}
}


