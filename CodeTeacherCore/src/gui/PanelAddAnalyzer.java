package gui;

import static gui.msg.FrameAddOutputMsg.ADD;
import static gui.msg.FrameAddOutputMsg.CLEAR;
import static gui.msg.FrameAddOutputMsg.REMOVE;
import static gui.msg.FrameAddOutputMsg.UP;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;

import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.ImplementsAnalyzer;
import codeteacher.analyzers.MethodAnalyzer;
import codeteacher.analyzers.ThrowsAnalyzer;
import codeteacher.behave.ExecutorImpl;
import gui.component.table.ExecutorImplModel;
import gui.component.table.FieldAnalyzerModel;
import gui.component.table.ImplementsAnalyzerModel;
import gui.component.table.MethodAnalyzerModel;
import gui.component.table.RowTableModel;
import gui.component.table.ThrowsAnalyzerModel;
import gui.msg.FrameAddOutputMsg;
import gui.msg.I18N;

public class PanelAddAnalyzer<T extends Analyzer> extends WebPanel {

	private Class<T> type;

	private FrameAddOutputMsg msg = FrameAddOutputMsg.TITLE;

	private PanelAddAnalyzer<?> thisPanel;
	protected JButton btnAdd;
	private JButton btnRemove;
	private JButton btnClear;
	private JButton btnUp;
	private JButton btnDown;

	private JTable table;

	private JScrollPane scrollExceptions;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebFrame frame = new WebFrame();
					frame.getContentPane().add(new PanelException(null));
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
	public PanelAddAnalyzer(Class<T> type) {
		this.type = type;
		this.thisPanel = this;
		setBounds(100, 100, 742, 552);
		setBorder(new EmptyBorder(5, 5, 5, 5));

		GridBagLayout gbl_pnlExceptions = new GridBagLayout();
		gbl_pnlExceptions.columnWidths = new int[] { 356, 0, 100 };
		gbl_pnlExceptions.rowHeights = new int[] { 1, 0, 0, 0 };
		gbl_pnlExceptions.columnWeights = new double[] { 1.0, 1.0, 0.0 };
		gbl_pnlExceptions.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		thisPanel.setLayout(gbl_pnlExceptions);

		JScrollPane scrollParams = new JScrollPane();
		GridBagConstraints gbc_scrollParams = new GridBagConstraints();
		gbc_scrollParams.gridwidth = 2;
		gbc_scrollParams.fill = GridBagConstraints.BOTH;
		gbc_scrollParams.insets = new Insets(0, 0, 5, 5);
		gbc_scrollParams.gridx = 0;
		gbc_scrollParams.gridy = 1;
		thisPanel.add(scrollParams, gbc_scrollParams);

		scrollExceptions = new JScrollPane();
		scrollParams.setViewportView(scrollExceptions);

		AbstractTableModel model = createModel();
		if (model == null) {
			model = new DefaultTableModel();
			((DefaultTableModel) model).addColumn("Name");
			((DefaultTableModel) model).addColumn("Value");
		}
		table = new JTable(model);
//			tableExceptions.setToolTipText(I18N.getVal(""));

		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getTableHeader().setReorderingAllowed(false);
		scrollExceptions.setViewportView(table);

		JPanel pnlButtons = new JPanel();
		GridBagConstraints gbc_pnlButtons = new GridBagConstraints();
		gbc_pnlButtons.insets = new Insets(0, 0, 5, 0);
		gbc_pnlButtons.anchor = GridBagConstraints.EAST;
		gbc_pnlButtons.fill = GridBagConstraints.VERTICAL;
		gbc_pnlButtons.gridx = 2;
		gbc_pnlButtons.gridy = 1;
		thisPanel.add(pnlButtons, gbc_pnlButtons);
		GridBagLayout gbl_pnlButtons = new GridBagLayout();
		gbl_pnlButtons.columnWidths = new int[] { 44 };
		gbl_pnlButtons.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_pnlButtons.columnWeights = new double[] { 0.0 };
		gbl_pnlButtons.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlButtons.setLayout(gbl_pnlButtons);

		btnAdd = new JButton(I18N.getVal(ADD));
//			btnAdd.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					WebFrame frame = new WebFrame();
//					frame.setContentPane(new PanelAddException(thisPanel));
//					frame.pack();
//					frame.center();
//					frame.setVisible(true);
//				}
//			});
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

		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();

				((RowTableModel<?>) table.getModel()).removeRows(selectedRow);

				updateButtons();
			}
		});

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((RowTableModel<?>) table.getModel()).clearRows();
				updateButtons();
			}
		});

		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				((RowTableModel<?>) table.getModel()).moveRow(selectedRow, selectedRow, selectedRow - 1);

				updateButtons();
			}
		});

		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				((RowTableModel<?>) table.getModel()).moveRow(selectedRow, selectedRow, selectedRow + 1);
				updateButtons();
			}
		});

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				updateButtons();
			}
		});

	}

	public PanelAddAnalyzer(Class<T> type, ActionListener addListener) {
		this(type);
		btnAdd.addActionListener(addListener);
	}

	private AbstractTableModel createModel() {
		AbstractTableModel model = null;
		if (type.equals(FieldAnalyzer.class)) {
			model = new FieldAnalyzerModel();
		} else if (type.equals(ImplementsAnalyzer.class)) {
			model = new ImplementsAnalyzerModel();
		} else if (type.equals(MethodAnalyzer.class)) {
			model = new MethodAnalyzerModel();
		} else if (type.equals(ThrowsAnalyzer.class)) {
			model = new ThrowsAnalyzerModel();
		} else if (type.equals(ExecutorImpl.class)) {
			model = new ExecutorImplModel();
		}
		
		return model;
	}

	public void addAnalyzer(T a) {
		if (a instanceof FieldAnalyzer) {
			FieldAnalyzerModel model = (FieldAnalyzerModel) table.getModel();
			model.addRow((FieldAnalyzer) a);
			table.setModel(model);
		} else if (a instanceof ImplementsAnalyzer) {
			ImplementsAnalyzerModel model = (ImplementsAnalyzerModel) table.getModel();
			model.addRow((ImplementsAnalyzer) a);
			table.setModel(model);
		} else if (a instanceof MethodAnalyzer) {
			MethodAnalyzerModel model = (MethodAnalyzerModel) table.getModel();
			model.addRow((MethodAnalyzer) a);
			table.setModel(model);
		} else if (a instanceof ThrowsAnalyzer) {
			ThrowsAnalyzerModel model = (ThrowsAnalyzerModel) table.getModel();
			model.addRow((ThrowsAnalyzer) a);
			table.setModel(model);
		} else if (a instanceof ExecutorImpl) {
			ExecutorImplModel model = (ExecutorImplModel) table.getModel();
			model.addRow((ExecutorImpl) a);
			table.setModel(model);
		}
		
	}

	public TableModel getModel() {
		return table.getModel();
	}

	public void updateButtons() {
		boolean isRowSelected = table.getSelectedRow() >= 0;

//		btnAdd.setEnabled(hasName && hasReturn);

		boolean hasData = table.getModel().getRowCount() > 0;
		boolean hasMoreThanOne = table.getModel().getRowCount() > 1;
//		btnOk.setEnabled(hasValue && hasName && hasReturn && hasOutput);
		btnRemove.setEnabled(hasData && isRowSelected);
		btnUp.setEnabled(hasMoreThanOne && isRowSelected);
		btnDown.setEnabled(hasMoreThanOne && isRowSelected);
		btnClear.setEnabled(hasData);
	}
}
