package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import codeteacher.behave.ConstructorCall;
import codeteacher.behave.ExecutorImpl;
import gui.component.AutoCompleteBehaviourClassPath;

public class PanelAddTestCase extends JPanel {

	private JLabel lblType;
	private JTextField txtType;
	private JButton btnOk;
	private JButton btnCancel;
	private JLabel lblAlias;
	private JTextField txtAlias;

	public PanelAddTestCase() {
		this.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_pnlClass = new GridBagLayout();
		gbl_pnlClass.columnWidths = new int[] { 59, 0, 124, 0, 0, 0, 0 };
		gbl_pnlClass.rowHeights = new int[] { 37, 0, 0, 0 };
		gbl_pnlClass.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlClass.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_pnlClass);

		lblType = new JLabel("Type");
		lblType.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblClassname = new GridBagConstraints();
		gbc_lblClassname.insets = new Insets(0, 0, 5, 5);
		gbc_lblClassname.gridx = 0;
		gbc_lblClassname.gridy = 0;
		this.add(lblType, gbc_lblClassname);

		txtType = new JTextField();
		txtType.setColumns(10);
		GridBagConstraints gbc_txtClassName = new GridBagConstraints();
		gbc_txtClassName.gridwidth = 3;
		gbc_txtClassName.insets = new Insets(0, 0, 5, 5);
		gbc_txtClassName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtClassName.gridx = 1;
		gbc_txtClassName.gridy = 0;
		this.add(txtType, gbc_txtClassName);
		
		lblAlias = new JLabel("Alias");
		lblAlias.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblAlias = new GridBagConstraints();
		gbc_lblAlias.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlias.gridx = 4;
		gbc_lblAlias.gridy = 0;
		this.add(lblAlias, gbc_lblAlias);

		txtAlias = new JTextField();
		GridBagConstraints gbc_txtAlias = new GridBagConstraints();
		gbc_txtAlias.gridwidth = 3;
		gbc_txtAlias.insets = new Insets(0, 0, 5, 5);
		gbc_txtAlias.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAlias.gridx = 5;
		gbc_txtAlias.gridy = 0;
		this.add(txtAlias, gbc_txtAlias);

		btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 4;
		gbc_btnCancel.gridy = 2;
		add(btnCancel, gbc_btnCancel);
		
		btnOk = new JButton("Ok");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.gridx = 5;
		gbc_btnOk.gridy = 2;
		add(btnOk, gbc_btnOk);
		
//		btnOk.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				ImplementsAnalyzer analyzer = createAnalyzer();
//				if (previous instanceof PanelAddAnalyzer) {
//					((PanelAddAnalyzer<ImplementsAnalyzer>) previous).addAnalyzer(analyzer);
//				}
//			}
//		});
		
		txtType.setColumns(10);
		AutoCompleteBehaviourClassPath autoComplete = new AutoCompleteBehaviourClassPath(txtType);

		autoComplete.setInitialPopupSize(new Dimension(txtType.getWidth() + 500, txtType.getHeight()));
	}
	
	public void addAction(ActionListener actionListener) {
		btnOk.addActionListener(actionListener);		
	}

	public ExecutorImpl createAnalyzer() {
		String alias = txtAlias.getText();
		String type = txtType.getText();
		
		return new ConstructorCall(alias, type);
	}
}
