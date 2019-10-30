package gui;

import static gui.msg.FrameAddOutputMsg.ADD;
import static gui.msg.FrameAddOutputMsg.CLEAR;
import static gui.msg.FrameAddOutputMsg.REMOVE;
import static gui.msg.FrameAddOutputMsg.UP;

import java.awt.Container;
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

import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;

import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.MethodAnalyzer;
import codeteacher.analyzers.ThrowsAnalyzer;
import gui.component.ComponentUtils;
import gui.component.table.FieldAnalyzerModel;
import gui.msg.FrameAddOutputMsg;
import gui.msg.I18N;

public class PanelException extends WebPanel {

	private FrameAddOutputMsg msg = FrameAddOutputMsg.TITLE;

	private boolean editMode;
	private PanelException thisPanel;
	private PanelAddMethod owner;
	private FrameTestField previous;

	
	private JPanel pnlControlButtons;
	private GridBagLayout gbl_pnlControlButtons;
	protected JButton btnAdd;
	private JButton btnRemove;
	private JButton btnClear;
	private JButton btnUp;
	private JButton btnDown;
	protected JButton btnOk;
	private JButton btnCancel;

	private JTable tableExceptions;

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
	public PanelException(AbstractTableModel model) {
		this.thisPanel = this;
		setBounds(100, 100, 742, 552);
		setBorder(new EmptyBorder(5, 5, 5, 5));
//		JPanel pnlExceptions = new JPanel();

		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 0;
//		thisPanel.add(scrollPane_2, gbc_scrollPane_2);

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

		if (model == null){
			model = new DefaultTableModel();
			((DefaultTableModel) model).addColumn("Name");
			((DefaultTableModel) model).addColumn("Value");
		}
		tableExceptions = new JTable(model);
//		tableExceptions.setToolTipText(I18N.getVal(""));

		tableExceptions.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tableExceptions.getTableHeader().setReorderingAllowed(false);
		scrollExceptions.setViewportView(tableExceptions);

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
//		btnAdd.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				WebFrame frame = new WebFrame();
//				frame.setContentPane(new PanelAddException(thisPanel));
//				frame.pack();
//				frame.center();
//				frame.setVisible(true);
//			}
//		});
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
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			private MethodAnalyzer methodAnalyzer;

			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel model = (DefaultTableModel) tableExceptions.getModel();
				for (int row = 0; row < tableExceptions.getRowCount(); row++) {
					String name = model.getValueAt(row, 0).toString();
					String matchCase = model.getValueAt(row, 1).toString();
					String regex = model.getValueAt(row, 2).toString();
					String value = model.getValueAt(row, 3).toString();
					
					methodAnalyzer = owner.getMethodAnalyzer();
					if (methodAnalyzer != null) {
						ThrowsAnalyzer throwsAnalyzer = new ThrowsAnalyzer(methodAnalyzer, name, false, false, Integer.valueOf(value));
						Container parent = getParent().getParent();
					}
				}
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.gridx = 2;
		gbc_btnOk.gridy = 2;
		add(btnOk, gbc_btnOk);
	}

	public void addException(String name, boolean matchCase, boolean regex, Double value) {
		ComponentUtils.addRow(tableExceptions, name, false, String.valueOf(value));
		ThrowsAnalyzer throwsAnalyzer = new ThrowsAnalyzer(null, name, matchCase, regex, value.intValue());
	}
	
	public void addAnalyzer(FieldAnalyzer a) {
		FieldAnalyzerModel model = (FieldAnalyzerModel) tableExceptions.getModel();
		model.addRow(a);
		tableExceptions.setModel(model);
	}
	
	public FieldAnalyzerModel getModel() {
		return (FieldAnalyzerModel) tableExceptions.getModel();
	}
}
