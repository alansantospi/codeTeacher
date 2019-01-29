package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.commons.io.FileUtils;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;

import codeteacher.Config;
import codeteacher.analyzers.AbstractAnalyzer;
import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.CompositeAnalyzer;
import codeteacher.analyzers.TestSet;
import codeteacher.analyzers.Tester;
import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import codeteacher.result.Evaluation;
import codeteacher.result.Performance;
import gui.component.ComponentUtils;
import gui.component.jtreefiltering.AnalyzerTreeRenderer;
import gui.component.jtreefiltering.example.JTreeUtil;
import gui.component.jtreefiltering.example.TreeFilterDecorator;
import gui.msg.FrameTestMsg;
import gui.msg.I18N;
import gui.strategy.ArchiveStrategy;
import gui.strategy.FileStrategy;
import gui.strategy.FileType;
import gui.strategy.JarStrategy;
import gui.strategy.JavaStrategy;
import gui.strategy.OpenResourceStrategy;
import utils.ReflectionUtils;
import utils.UnzipUtils;

@SuppressWarnings("serial")
public class FrameTestField extends WebFrame {

	private static final FrameTestMsg msg = FrameTestMsg.ADD_BEHAVIOR;

//	private static final String STUDENT_DIRECTORY = I18N.getVal(msg.STUDENT_DIRECTORY);
	private JTextField txtProjectName;
	private JTextField txtStudentDir;
	private JTable resultTable;
	private FrameTestField thisFrame;
	private DynamicTree dirTree;
	protected FrameDetailResult detailFrame;
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

	/* new features */
	private static String ADD_COMMAND = "add";
	private static String REMOVE_COMMAND = "remove";
	private static String CLEAR_COMMAND = "clear";

	private DynamicWebCheckBoxTree checkBoxTree;
	private DefaultMutableTreeNode rootNode;

	private WebPanel pnlTestButtons;
	private JPanel pnlResult;
	private JPanel panel_3;
	private JPanel pnlFiles;

	/**
	 * Create the panel.
	 */
	public FrameTestField() {

		thisFrame = this;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 441, 375, 0 };
		gridBagLayout.rowHeights = new int[] { 36, 51, 53, 105, 31, -179 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 1.0 };
		getContentPane().setLayout(gridBagLayout);

		JPanel pnlDir = new JPanel();
//		pnlDir.setBorder(new TitledBorder(null, STUDENT_DIRECTORY, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlDir = new GridBagConstraints();
		gbc_pnlDir.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlDir.gridwidth = 2;
		gbc_pnlDir.insets = new Insets(0, 0, 5, 5);
		gbc_pnlDir.gridx = 0;
		gbc_pnlDir.gridy = 0;
		getContentPane().add(pnlDir, gbc_pnlDir);
		GridBagLayout gbl_pnlDir = new GridBagLayout();
		gbl_pnlDir.columnWidths = new int[] { 69, 246, 6, 70, 0, 0 };
		gbl_pnlDir.rowHeights = new int[] { 23, 0 };
		gbl_pnlDir.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlDir.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlDir.setLayout(gbl_pnlDir);

		txtStudentDir = new JTextField();
		GridBagConstraints gbc_tfStudentDir = new GridBagConstraints();
		gbc_tfStudentDir.gridwidth = 2;
		gbc_tfStudentDir.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStudentDir.insets = new Insets(0, 0, 0, 5);
		gbc_tfStudentDir.gridx = 0;
		gbc_tfStudentDir.gridy = 0;
		pnlDir.add(txtStudentDir, gbc_tfStudentDir);
		txtStudentDir.setColumns(10);

		JButton btnBrowse = new JButton(I18N.getVal(msg.BROWSE));
		GridBagConstraints gbc_btnBrowseMethod = new GridBagConstraints();
		gbc_btnBrowseMethod.insets = new Insets(0, 0, 0, 5);
		gbc_btnBrowseMethod.anchor = GridBagConstraints.NORTH;
		gbc_btnBrowseMethod.gridwidth = 2;
		gbc_btnBrowseMethod.gridx = 2;
		gbc_btnBrowseMethod.gridy = 0;
		pnlDir.add(btnBrowse, gbc_btnBrowseMethod);

		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new BrowseAction(I18N.getVal(msg.STUDENT_DIR), new ImageIcon("action.gif"));
			}
		});

		radioGroup = new ButtonGroup();
		radioGroupFiletype = new ButtonGroup();

		JPanel pnlProjName = new JPanel();
		pnlProjName.setBorder(new TitledBorder(null, I18N.getVal(msg.PROJECT_NAME), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlProjName = new GridBagConstraints();
		gbc_pnlProjName.fill = GridBagConstraints.BOTH;
		gbc_pnlProjName.insets = new Insets(0, 0, 5, 5);
		gbc_pnlProjName.gridx = 0;
		gbc_pnlProjName.gridy = 1;
		getContentPane().add(pnlProjName, gbc_pnlProjName);
		GridBagLayout gbl_pnlProjName = new GridBagLayout();
		gbl_pnlProjName.columnWidths = new int[] { 98, 81, 140, 101, 0 };
		gbl_pnlProjName.rowHeights = new int[] { 23, 0, 0 };
		gbl_pnlProjName.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_pnlProjName.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		pnlProjName.setLayout(gbl_pnlProjName);

		txtProjectName = new JTextField();
		GridBagConstraints gbc_tfProjectName = new GridBagConstraints();
		gbc_tfProjectName.insets = new Insets(0, 0, 5, 0);
		gbc_tfProjectName.gridwidth = 2;
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
		pnlProjOptions.setBorder(
				new TitledBorder(null, I18N.getVal(msg.OPTIONS), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlProjOptions = new GridBagConstraints();
		gbc_pnlProjOptions.insets = new Insets(0, 0, 5, 0);
		gbc_pnlProjOptions.fill = GridBagConstraints.BOTH;
		gbc_pnlProjOptions.gridx = 1;
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
		gbl_pnlFiletype.columnWidths = new int[] { 23, 57, 49, 63, 0 };
		gbl_pnlFiletype.rowHeights = new int[] { 23, 0 };
		gbl_pnlFiletype.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlFiletype.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
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
		gbc_pnlOpenOptions.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlOpenOptions.insets = new Insets(0, 0, 5, 5);
		gbc_pnlOpenOptions.gridx = 1;
		gbc_pnlOpenOptions.gridy = 2;
		getContentPane().add(pnlOpenOptions, gbc_pnlOpenOptions);
		pnlOpenOptions.setBorder(BorderFactory.createTitledBorder(I18N.getVal(msg.OPEN_OPTIONS)));
		GridBagLayout gbl_pnlOpenOptions = new GridBagLayout();
		gbl_pnlOpenOptions.columnWidths = new int[] { 78, 57, 95, 0, 0 };
		gbl_pnlOpenOptions.rowHeights = new int[] { 23, 0 };
		gbl_pnlOpenOptions.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlOpenOptions.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
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

		createDirTree();

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

		pnlResult = new JPanel();
		GridBagConstraints gbc_pnlResult = new GridBagConstraints();
		gbc_pnlResult.insets = new Insets(0, 0, 0, 5);
		gbc_pnlResult.fill = GridBagConstraints.BOTH;
		gbc_pnlResult.gridwidth = 2;
		gbc_pnlResult.gridx = 0;
		gbc_pnlResult.gridy = 4;
		getContentPane().add(pnlResult, gbc_pnlResult);
		GridBagLayout gbl_pnlResult = new GridBagLayout();
		gbl_pnlResult.columnWidths = new int[] { 253, 191, 112, 70, 80, 0, 0, 0, 0, 0 };
		gbl_pnlResult.rowHeights = new int[] { 95, 0 };
		gbl_pnlResult.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlResult.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlResult.setLayout(gbl_pnlResult);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 9;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		pnlResult.add(scrollPane, gbc_scrollPane);

		resultTable = new JTable();
		scrollPane.setViewportView(resultTable);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// table.setModel(new DefaultTableModel(
		// new Object[][] {
		// {null, null, null, null},
		// {null, null, null, null},
		// {null, null, null, null},
		// {null, null, null, null},
		// },
		// new String[] {
		// "Aluno", "Tipo", "Valor", "Descri\u00E7\u00E3o"
		// }
		// ));
		resultTable.setCellSelectionEnabled(true);
		resultTable.setColumnSelectionAllowed(true);

		resultTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable table = (JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);

				if (me.getClickCount() == 2) {
					String student = (String) table.getModel().getValueAt(row, 0);
//					Performance perform = eval.getPerforMap().get(student);

//					detailFrame = new FrameDetailResult(thisFrame, perform, eval);
//					detailFrame.setTitle(I18N.getVal(msg.DETAIL_RESULT));
//					detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

					// detailFrame.setSize(600, 450);
					detailFrame.setLocationRelativeTo(null);
					detailFrame.setVisible(true);
				}
			}
		});

		// Adding listeners...
		menuNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TestSet.reset();
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

	public FrameTestField(Project proj) {
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
	}

	protected void createDirTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(null);

		panel_3 = new WebPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 2;
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 3;
		getContentPane().add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 365, 426 };
		gbl_panel_3.rowHeights = new int[] { 110, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, 0.0 };
		gbl_panel_3.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		pnlFiles = new JPanel();
		GridBagConstraints gbc_pnlFiles = new GridBagConstraints();
		gbc_pnlFiles.fill = GridBagConstraints.BOTH;
		gbc_pnlFiles.insets = new Insets(0, 0, 0, 5);
		gbc_pnlFiles.gridx = 0;
		gbc_pnlFiles.gridy = 0;
		panel_3.add(pnlFiles, gbc_pnlFiles);
		GridBagLayout gbl_pnlFiles = new GridBagLayout();
		gbl_pnlFiles.columnWidths = new int[] { 365, 0 };
		gbl_pnlFiles.rowHeights = new int[] { 110, 0 };
		gbl_pnlFiles.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_pnlFiles.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlFiles.setLayout(gbl_pnlFiles);
		dirTree = new DynamicTree(root, true);
		GridBagConstraints gbc_dirTree = new GridBagConstraints();
		gbc_dirTree.fill = GridBagConstraints.BOTH;
		gbc_dirTree.gridx = 0;
		gbc_dirTree.gridy = 0;
		pnlFiles.add(dirTree, gbc_dirTree);

		WebPanel pnlTests = new WebPanel();
		GridBagConstraints gbc_pnlTests = new GridBagConstraints();
		gbc_pnlTests.fill = GridBagConstraints.BOTH;
		gbc_pnlTests.insets = new Insets(0, 0, 0, 7);
		gbc_pnlTests.gridx = 1;
		gbc_pnlTests.gridy = 0;
		panel_3.add(pnlTests, gbc_pnlTests);
		GridBagLayout gbl_pnlTests = new GridBagLayout();
		gbl_pnlTests.columnWidths = new int[] { 391 };
		gbl_pnlTests.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0 };
		gbl_pnlTests.columnWeights = new double[] { 1.0 };
//		gbl_pnlTests.columnWidths = new int[] { 213, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
//		gbl_pnlTests.rowHeights = new int[] { 23, 0, 0, 0, 0, 0, 0 };
//		gbl_pnlTests.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
//				0.0, 0.0, 0.0, Double.MIN_VALUE };
//		gbl_pnlTests.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlTests.setLayout(gbl_pnlTests);

		rootNode = new DefaultMutableTreeNode("Fields");
		checkBoxTree = new DynamicWebCheckBoxTree(rootNode);
		GridBagConstraints gbc_checkBoxTree = new GridBagConstraints();
		gbc_checkBoxTree.insets = new Insets(0, 0, 5, 5);
		gbc_checkBoxTree.gridx = 0;
		gbc_checkBoxTree.gridy = 1;
//		pnlTests.add(checkBoxTree, gbc_checkBoxTree);
//		checkBoxTree.setVisibleRowCount(7);
		checkBoxTree.setAutoscrolls(true);

		JTreeUtil.setTreeExpandedState(checkBoxTree, true);
		TreeFilterDecorator filterDecorator = TreeFilterDecorator.decorate(checkBoxTree, AnalyzerMatcher.create());

		checkBoxTree.setCellRenderer(new AnalyzerTreeRenderer(() -> filterDecorator.getFilterField().getText()));

		checkBoxTree.setPreferredSize(new Dimension(300, 150));

		WebScrollPane scrollTests = new WebScrollPane(checkBoxTree);
		GridBagConstraints gbc_scrollTests = new GridBagConstraints();
		gbc_scrollTests.insets = new Insets(0, 0, 5, 0);
		gbc_scrollTests.gridheight = 3;
		gbc_scrollTests.fill = GridBagConstraints.BOTH;
		gbc_scrollTests.gridx = 0;
		gbc_scrollTests.gridy = 0;
		pnlTests.add(scrollTests, gbc_scrollTests);

		JPanel topPanel = new JPanel();
		scrollTests.setColumnHeaderView(topPanel);
		topPanel.add(filterDecorator.getFilterField());
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.columnWidths = new int[] { 226, 68, 72, 0 };
		gbl_topPanel.rowHeights = new int[] { 24, 0 };
		gbl_topPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_topPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		topPanel.setLayout(gbl_topPanel);
		WebButton collapseBtn = new WebButton("Collapse All");
		collapseBtn.addActionListener(ae -> {
			JTreeUtil.setTreeExpandedState(checkBoxTree, false);
		});
		WebButton expandBtn = new WebButton("Expand All");
		expandBtn.addActionListener(ae -> {
			JTreeUtil.setTreeExpandedState(checkBoxTree, true);
		});
		GridBagConstraints gbc_expandBtn = new GridBagConstraints();
		gbc_expandBtn.anchor = GridBagConstraints.NORTHWEST;
		gbc_expandBtn.insets = new Insets(0, 0, 0, 5);
		gbc_expandBtn.gridx = 1;
		gbc_expandBtn.gridy = 0;
		topPanel.add(expandBtn, gbc_expandBtn);
		GridBagConstraints gbc_collapseBtn = new GridBagConstraints();
		gbc_collapseBtn.anchor = GridBagConstraints.NORTHWEST;
		gbc_collapseBtn.gridx = 2;
		gbc_collapseBtn.gridy = 0;
		topPanel.add(collapseBtn, gbc_collapseBtn);

		WebButton addButton = new WebButton("Add");
		addButton.setActionCommand(ADD_COMMAND);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WebFrame frame = new FrameAddType(thisFrame);
//				frame.getContentPane().add(new PanelAddField2(thisFrame));
				frame.pack();
				frame.center();
				frame.setVisible(true);
			}
		});
		WebButton removeButton = new WebButton("Remove");
		removeButton.setActionCommand(REMOVE_COMMAND);
		// removeButton.addActionListener(this);

		WebButton clearButton = new WebButton("Clear");
		clearButton.setActionCommand(CLEAR_COMMAND);
		// clearButton.addActionListener(this);

		pnlTestButtons = new WebPanel(new GridLayout(0, 3));
		GridLayout gridLayout = (GridLayout) pnlTestButtons.getLayout();
		gridLayout.setColumns(4);
		pnlTestButtons.add(addButton);
		pnlTestButtons.add(removeButton);
		pnlTestButtons.add(clearButton);
		GridBagConstraints gbc_TestButtons = new GridBagConstraints();
		gbc_TestButtons.insets = new Insets(0, 0, 5, 0);
		gbc_TestButtons.anchor = GridBagConstraints.SOUTH;
		gbc_TestButtons.gridx = 0;
		gbc_TestButtons.gridy = 3;
		pnlTests.add(pnlTestButtons, gbc_TestButtons);

		btnRun = new WebButton("Run");
		pnlTestButtons.add(btnRun);
		btnRun.addActionListener(new RunListener());
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

	public void addListener(AbstractButton button, ActionListener listener) {
		button.addActionListener(listener);
	}

	class SaveFileListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(I18N.getVal(msg.FILE_TYPE),
					I18N.getVal(msg.EXTENSION));
			chooser.setFileFilter(filter);
			int returnVal = chooser.showSaveDialog(thisFrame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
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
			JTree tree = dirTree.tree;
			tree.expandPath(new TreePath(((DefaultTreeModel) tree.getModel()).getRoot()));
		}
	}

	class BrowseAction extends AbstractAction {
		public BrowseAction(String text, Icon icon) {
			super(text, icon);
			String filename = File.separator + "tmp";
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
			FileNameExtensionFilter filter = new FileNameExtensionFilter(I18N.getVal(msg.FILTER_MSG),
					I18N.getVal(msg.EXTENSION));
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(thisFrame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String filePath = chooser.getSelectedFile().getAbsolutePath();
				try (InputStream file = new FileInputStream(filePath);
						InputStream buffer = new BufferedInputStream(file);
						ObjectInput input = new ObjectInputStream(buffer);) {
					Map<String, List<Analyzer>> recovered = (Map<String, List<Analyzer>>) input.readObject();
					TestSet.reset();
					TestSet.getTests().putAll(recovered);
				} catch (ClassNotFoundException ex) {
					System.out.println("Cannot perform input. Class not found." + ex);
				} catch (IOException ex) {
					System.out.println("Cannot perform input." + ex);
				}
			}
		}
	}

	public Evaluation eval(TestRunner runner) {
		Evaluation eval = new Evaluation();
		List<Analyzer> tests = runner.getTests();
		String studentDir = txtStudentDir.getText();
		File[] files = new File(studentDir).listFiles();

		for (File studentFolder : files) {
			String folderName = studentFolder.getName();

			if (studentFolder.isDirectory()) {
				String projectName = txtProjectName.getText();
				String jarName = projectName + ".jar";
				String jarPath = studentFolder + Config.SEPARATOR + projectName;

				Collection<File> jarFiles = FileUtils.listFiles(new File(studentFolder + ""), new String[] { "jar" },
						true);

				boolean match = false;
				String fileName = "";

				/* Loading from jar file... */
				for (File f : jarFiles) {
					fileName = f.getName();
					jarPath = studentFolder + Config.SEPARATOR + fileName;
					if (chkRegex.isSelected()) {
						Pattern pattern = Pattern.compile(jarName);
						Matcher m = pattern.matcher(fileName);
						match = m.find();
						if (match) {
							break;
						}
					} else if (chkCaseSensitive.isSelected()) {
						match = fileName.equals(jarName);
					} else {
						match = fileName.equalsIgnoreCase(jarName);
					}
				}

				if (match) {
					String destDir = studentFolder + Config.SEPARATOR
							+ fileName.substring(0, fileName.lastIndexOf('.'));

					try {
						UnzipUtils.unzip(jarPath, destDir);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ClassLoader loader = ReflectionUtils.getClassLoader(destDir);

					eval.addStudent(folderName);

					for (Analyzer a : tests) {
						if (a instanceof ClassAnalyzer) {
							((ClassAnalyzer) a).setLoader(loader);
						}
					}

					Performance perform = runner.execute();
					perform.setStudent(folderName);
					eval.mergePerformance(perform);

				} else {
					ErrorType errorType = ErrorType.JAR_NOT_FOUND;
					Error error = new Error(errorType, errorType.getMessage(jarName));
					eval.addError(folderName, error);
				}
			}
		}
//		calcPerformance();

		return eval;
	}

	private void fillResultTable(Evaluation eval) {
		resultTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { I18N.getVal(msg.ALUNO), I18N.getVal(msg.ERROS), I18N.getVal(msg.NOTA) }));
		DefaultTableModel model = (DefaultTableModel) resultTable.getModel();

		Map<String, Performance> perforMap = eval.getPerforMap();
		Set<String> keySet = perforMap.keySet();
		ArrayList<String> arrayList = new ArrayList<String>(keySet);
		java.util.Collections.sort(arrayList);
		for (String key : arrayList) {
			Performance perform = perforMap.get(key);
			String student = perform.getStudent();
			int totalErrors = perform.getTotalErrors();
			float hitsPercentage = perform.getHitsPercentage() / 10;
			model.addRow(new String[] { student, String.valueOf(totalErrors), String.valueOf(hitsPercentage) });
		}
		resultTable.getTableHeader().setResizingAllowed(true);
		resultTable.getColumnModel().getColumn(0).setPreferredWidth(474);
		resultTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		resultTable.getColumnModel().getColumn(2).setPreferredWidth(50);
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

			Evaluation eval = eval(command);
			for (String key : eval.getPerforMap().keySet()) {
				Performance perform = eval.getPerforMap().get(key);
				System.out.println(perform);
			}
			fillResultTable(eval);
		}
	}

	public static void main(String[] args) {

		// Its very important to call this before setting Look & Feel
		I18N.getVal(FrameTestMsg.ADD_BEHAVIOR);

		// Look and Feel
		WebLookAndFeel.install();

		FrameTestField frame = new FrameTestField();
		frame.pack();
		frame.center();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
