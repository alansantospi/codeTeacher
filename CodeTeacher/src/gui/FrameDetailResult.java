package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import codeteacher.err.Action;
import codeteacher.err.Error;
import codeteacher.err.ErrorFixer;
import codeteacher.result.Evaluation;
import codeteacher.result.Performance;
import gui.component.ComponentUtils;

public class FrameDetailResult extends JFrame {

	private static final String DESCRIPTION = "Description";// "Descri\u00E7\u00E3o";
	private static final String VALUE = "Value";
	private static final String TYPE = "Type";
	private static final String SCORE = "Score: ";
	private static final String CANCEL = "Cancel";
	private static final String OK = "OK";
	private JPanel contentPane;
	private JTable table;
	private FrameDetailResult thisFrame;
	private Evaluation eval;
	private FrameTest parentFrame;

	/**
	 * Create the frame.
	 * 
	 * @param perform
	 */
	public FrameDetailResult(FrameTest parentFrame, Performance perform, Evaluation eval) {
		this.parentFrame = parentFrame;
		thisFrame = this;
		thisFrame.setEval(eval);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 742, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 67, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblAluno = new JLabel(perform.getStudent());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		contentPane.add(lblAluno, gbc_lblNewLabel);

		JLabel lblNota = new JLabel(SCORE);
		lblNota.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNota.setText(SCORE + " " + perform.getHitsPercentage());
		GridBagConstraints gbc_lblNota = new GridBagConstraints();
		gbc_lblNota.insets = new Insets(0, 0, 5, 0);
		gbc_lblNota.gridx = 2;
		gbc_lblNota.gridy = 1;
		contentPane.add(lblNota, gbc_lblNota);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		contentPane.add(scrollPane, gbc_scrollPane);

		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
//		scrollPane.setRowHeaderView(table);
		scrollPane.setViewportView(table);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.SOUTHEAST;
		gbc_panel.gridwidth = 3;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 4;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 149, 54, 101, 61, 53, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 23, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JButton btnCancel = new JButton(CANCEL);
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.NORTH;
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 11;
		gbc_btnCancel.gridy = 0;
		panel.add(btnCancel, gbc_btnCancel);

		JButton btnOk = new JButton(OK);
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.NORTH;
		gbc_btnOk.gridx = 12;
		gbc_btnOk.gridy = 0;
		panel.add(btnOk, gbc_btnOk);

		populateTable(perform);

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ComponentUtils.closeFrame(thisFrame);
			}
		});

		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ComponentUtils.closeFrame(thisFrame);
			}
		});

//		String student = perform.getStudent();
//		EvaluationReturn eval2 = thisFrame.getEval();
//		ErrorFixer fix = eval2.getQuickFixes().get(student);
		final JPopupMenu popup = new JPopupMenu();
//		if (fix != null) {
//			for (Action act : fix.getActions()) {
//
//				JMenuItem menuItem = new JMenuItem(act.toString());
//				menuItem.addActionListener(new ActionListener() {
//
//					public void actionPerformed(ActionEvent e) {
//						act.doAction();
//					}
//				});
//				popup.add(menuItem);
//			}
//		}

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

			private void showPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		Map<Error, ErrorFixer> fixers = perform.getErrorFixers();
		for (ErrorFixer x : fixers.values()) {
			for (Action act : x.getActions()) {

				JMenuItem menuItem = new JMenuItem(act.toString());
				menuItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						Performance p = act.doAct();
						parentFrame.addPerformance(p);
						// TODO remover o menu quando resolver o fix
						// TODO só efetivar as ações quando clicar em ok
					}
				});
				popup.add(menuItem);
			}
		}
	}

	private void populateTable(Performance perform) {

		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { TYPE, VALUE, DESCRIPTION }));
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		for (Error e : perform.getErrors()) {
			String desc = e.getDescription();
			String value = String.valueOf(e.getValue());
			Object msg = e.getMessage();
			
			if (perform.getErrorFixers().containsKey(e)) {
				ErrorFixer fix = perform.getErrorFixers().get(e);
				msg = fix;
			}
			
			model.addRow(new Object[] { desc, value, msg });
		}
		table.getTableHeader().setResizingAllowed(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(10);
		table.getColumnModel().getColumn(2).setCellRenderer(createRenderer());
		table.getColumnModel().getColumn(2).setPreferredWidth(550);
	}

	public Evaluation getEval() {
		return eval;
	}

	public void setEval(Evaluation eval) {
		this.eval = eval;
	}

	private static TableCellRenderer createRenderer() {
		return new DefaultTableCellRenderer() {
//			{   //puts the icon after the text
//				super.setHorizontalTextPosition(SwingConstants.LEFT);
//			}

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				ImageIcon icon = null;
				if (value instanceof ErrorFixer) {
					icon = new ImageIcon("images/bulb-icon.png");
					value = ((ErrorFixer) value).getError().getMessage();
				}
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setIcon(icon); 
				return this;
			}
		};
	}
}
