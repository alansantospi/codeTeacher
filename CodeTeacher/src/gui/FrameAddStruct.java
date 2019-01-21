package gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import codeteacher.Config;
import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.OutputAnalyzer;
import codeteacher.analyzers.TestSet;
import codeteacher.struct.StructAnalyzer;
import gui.component.ComponentUtils;
import gui.msg.FrameAddStructMsg;
import gui.msg.I18N;

public class FrameAddStruct extends JFrame {

	private static final FrameAddStructMsg msg = FrameAddStructMsg.ADD;

	private JPanel contentPane;
	private JPanel panel;
	private JTable table;
	private JTextField txtClassName;
	private FrameTest previousFrame;
	private FrameAddStruct thisFrame;
	private DefaultMutableTreeNode node;

	private JButton btnOk;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnClear;
	private JButton btnUp;
	private JButton btnDown;
	private JSeparator separator;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameAddStruct frame = new FrameAddStruct(null, null, null, null);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameAddStruct(final FrameTest previousFrame, DefaultMutableTreeNode node, String methodName,
			List<Analyzer> tests) {
		this.thisFrame = this;
		this.previousFrame = previousFrame;
		this.node = node;
		setTitle(I18N.getVal(msg.ADD_TEST));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 10, 432, 0 };
		gbl_contentPane.rowHeights = new int[] { 314, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 106, 46, 86, 0 };
		gbl_panel.rowHeights = new int[] { 20, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		lblNewLabel = new JLabel("Class");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setLabelFor(txtClassName);

		txtClassName = new JTextField();
		GridBagConstraints gbc_txtMedotogetclass = new GridBagConstraints();
		gbc_txtMedotogetclass.gridwidth = 2;
		gbc_txtMedotogetclass.insets = new Insets(0, 0, 5, 0);
		gbc_txtMedotogetclass.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMedotogetclass.gridx = 1;
		gbc_txtMedotogetclass.gridy = 0;
		panel.add(txtClassName, gbc_txtMedotogetclass);
		txtClassName.setColumns(10);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 3;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		panel.add(tabbedPane, gbc_tabbedPane);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab(I18N.getVal(msg.PARAMS), null, panel_1, null);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 356, 150, 0 };
		gbl_panel_1.rowHeights = new int[] { 1, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 0, 5);
//		table.getColumnModel().getColumn(3).setPreferredWidth(315);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		// table.getColumnModel().getColumn(3).setPreferredWidth(315);
		// GridBagConstraints gbc_table = new GridBagConstraints();
		// gbc_table.insets = new Insets(0, 0, 0, 5);
		// gbc_table.fill = GridBagConstraints.BOTH;
		// gbc_table.gridx = 0;
		// gbc_table.gridy = 0;

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_1.add(scrollPane, gbc_scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		//
		// panel_1.add(table, gbc_table);
		table.setModel(
				new DefaultTableModel(new Object[][] {}, 
						new String[] { "New column", "New column", "New column" }));
		// panel_1.add(table, gbc_table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setReorderingAllowed(false);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.VERTICAL;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 0;
		panel_1.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 88, 0 };
		gbl_panel_3.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		btnAdd = new JButton(I18N.getVal(msg.ADD));
		btnAdd.addActionListener(new AddListener());

//		panel_1.add(table, gbc_table);

		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 0;
		panel_3.add(btnAdd, gbc_btnAdd);

		btnRemove = new JButton(I18N.getVal(msg.REMOVE));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();

//				TestSet.getTests().get(arg0)
				String methodName = (String) table.getValueAt(selectedRow, 0);
//				String params = (String) table.getValueAt(selectedRow, 1);
//				String expected = (String) table.getValueAt(selectedRow, 2);

				List<Analyzer> list = TestSet.getTests().get(txtClassName.getText());
				if (list != null) {
					Analyzer toRemove = null;
					for (Analyzer executer : list) {
						String method = ((OutputAnalyzer) executer).getMemberName();
						toRemove = executer;
						if (method.equals(methodName)) {
							break;
						}
						list.remove(toRemove);
					}
				}

				ComponentUtils.removeRow(table, selectedRow);
				updateButtons();
			}
		});
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemove.gridx = 0;
		gbc_btnRemove.gridy = 1;
		panel_3.add(btnRemove, gbc_btnRemove);

		btnClear = new JButton(I18N.getVal(msg.CLEAR));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ComponentUtils.clearTable(table);
				updateButtons();
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 2;
		panel_3.add(btnClear, gbc_btnNewButton_1);

		btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				ComponentUtils.moveRowUp(table, selectedRow);
			}
		});
		GridBagConstraints gbc_btnUp = new GridBagConstraints();
		gbc_btnUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUp.insets = new Insets(0, 0, 5, 0);
		gbc_btnUp.gridx = 0;
		gbc_btnUp.gridy = 3;
		panel_3.add(btnUp, gbc_btnUp);

		btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				ComponentUtils.moveRowDown(table, selectedRow);
			}
		});
		GridBagConstraints gbc_btnDown = new GridBagConstraints();
		gbc_btnDown.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDown.gridx = 0;
		gbc_btnDown.gridy = 4;
		panel_3.add(btnDown, gbc_btnDown);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Exceptions", null, panel_2, null);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 208, 30, 0 };
		gbl_panel_2.rowHeights = new int[] { 10, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 1;
		contentPane.add(separator, gbc_separator);

		btnOk = new JButton("OK");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.EAST;
		gbc_btnOk.insets = new Insets(0, 0, 5, 0);
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 4;
		contentPane.add(btnOk, gbc_btnOk);

		btnOk.addActionListener(new OkListener(previousFrame));

		if (methodName == null || methodName.equals("")) {
			txtClassName.setText(Config.CLASS_NAME);

			table.setModel(new DefaultTableModel(new Object[][] {
//					new String[] {"main", "java.lang.String[]", "esperava-se"}
//					new String[] {"testXY", "java.lang.Integer, java.lang.Integer", "esperava-se"}
					new String[] { "main", "java.lang.String[]", "esperava-se" } },
					new String[] { I18N.getVal(msg.METHOD), I18N.getVal(msg.PARAMS), I18N.getVal(msg.EXPECTED) }));
			table.setRowSelectionInterval(0, 0);
		} else {
			txtClassName.setText(methodName);
		}

		table.addFocusListener(new FocusListener() {

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

	private void updateButtons() {
		boolean hasData = table.getModel().getRowCount() > 0;
		boolean hasMoreThanOne = table.getModel().getRowCount() > 1;
		btnOk.setEnabled(hasData);
		boolean isRowSelected = table.getSelectedRow() >= 0;
		btnRemove.setEnabled(hasData && isRowSelected);
		btnUp.setEnabled(hasMoreThanOne && isRowSelected);
		btnDown.setEnabled(hasMoreThanOne && isRowSelected);
		btnClear.setEnabled(hasData);
	}

	private final class OkListener implements ActionListener {
		private final FrameTest previousFrame;

		private OkListener(FrameTest previousFrame) {
			this.previousFrame = previousFrame;
		}

		public void actionPerformed(ActionEvent event) {
			String className = txtClassName.getText();
			if (className.equals("")) {
				JOptionPane.showMessageDialog(null, msg.CLASS_NOT_SET);
			} else if (table.getRowCount() == 0) {
				JOptionPane.showMessageDialog(null, msg.PARAMS_NOT_SET);
			} else {
				int rows = table.getRowCount();
				for (int selectedRow = 0; selectedRow < rows; selectedRow++) {

					String methodName = (String) table.getValueAt(selectedRow, 0);
					String params = (String) table.getValueAt(selectedRow, 1);
					String expected = (String) table.getValueAt(selectedRow, 2);
					params = params.replaceAll("\\s", "");
					String[] split = params.trim().split(",");

					Object[] args = new Object[0];// split.length];
					Set<String> modifiers = new HashSet<String>();

//					Executer analyzer = FactoryOutputAnalyzr.create(modifiers, "void", methodName, expected, 1, args, split);

					Analyzer struct = new StructAnalyzer(className, modifiers, methodName, "void", split);
//					MethodEvaluator evaluator = new MethodEvaluator(baseClazz, struct.getMethodName(), paramTypesList.toArray(new Class<?>[0]));
//					struct.setEvaluator(evaluator);
					TestSet.addTest(className, struct);
				}
				previousFrame.updateTree(node);
				ComponentUtils.closeFrame(thisFrame);
			}

//				DynamicTree tree = previousFrame.getTree();// new DynamicTree();
//				Map<String, List<Executer>> tests = TestSet.getTests();
//				for (String key : tests.keySet()){
//					DefaultMutableTreeNode node = tree.addObject(key);
//					TreePath treePath = new TreePath(node.getPath());
//					for (Executer e : tests.get(key)){
//						tree.setSelectionPath(treePath);
//						tree.addObject(e);
////						(DefaultMutableTreeNode) (parentPath.getLastPathComponent());
//					}
//				}
//				previousFrame.getContentPane().add(tree);

		}
	}

	private final class AddListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
//				table.setModel(new DefaultTableModel(
//						new Object[][] {
//							{null, null, null, null},
//						},
//						new String[] {
////							"Nome", "Tipo", "Valor", "Saída esperada"
//						}
//					));
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.addRow(new String[] { "main", "java.lang.String[]", "esperava-se" });
			table.setModel(model);
			updateButtons();
		}
	}
}
