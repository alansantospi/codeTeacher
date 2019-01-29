package gui;

import static gui.msg.FrameAddOutputMsg.CASE_SENSITIVE;
import static gui.msg.FrameAddOutputMsg.REGEX;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.alee.laf.spinner.WebSpinner;

import codeteacher.analyzers.MethodAnalyzer;
import codeteacher.analyzers.ThrowsAnalyzer;
import gui.msg.I18N;

public class PanelAddException extends JPanel {

	private JLabel lblName;
	private JTextField txtName;
	private JCheckBox chkMatchCase;
	private JCheckBox chkRegex;
	private JLabel lblValue;
	private WebSpinner spinValue;
	private JButton btnOk;
	
	private PanelException previous;

	public PanelAddException(PanelException previous) {
		this.previous = previous;
		this.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_pnlClass = new GridBagLayout();
		gbl_pnlClass.columnWidths = new int[] { 139, 295, 0, 0, 47, 52, 0 };
		gbl_pnlClass.rowHeights = new int[] { 63, 0, 0, 0 };
		gbl_pnlClass.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_pnlClass.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_pnlClass);

		lblName = new JLabel("Exception");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblClassname = new GridBagConstraints();
		gbc_lblClassname.insets = new Insets(0, 0, 5, 5);
		gbc_lblClassname.gridx = 0;
		gbc_lblClassname.gridy = 0;
		this.add(lblName, gbc_lblClassname);

		txtName = new JTextField();
		txtName.setColumns(10);
		GridBagConstraints gbc_txtClassName = new GridBagConstraints();
		gbc_txtClassName.insets = new Insets(0, 0, 5, 5);
		gbc_txtClassName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtClassName.gridx = 1;
		gbc_txtClassName.gridy = 0;
		this.add(txtName, gbc_txtClassName);

		chkMatchCase = new JCheckBox(I18N.getVal(CASE_SENSITIVE));
		GridBagConstraints gbc_chkClassMatchCase = new GridBagConstraints();
		gbc_chkClassMatchCase.insets = new Insets(0, 0, 5, 5);
		gbc_chkClassMatchCase.gridx = 2;
		gbc_chkClassMatchCase.gridy = 0;
		this.add(chkMatchCase, gbc_chkClassMatchCase);

		chkRegex = new JCheckBox(I18N.getVal(REGEX));
		GridBagConstraints gbc_chkClassRegex = new GridBagConstraints();
		gbc_chkClassRegex.insets = new Insets(0, 0, 5, 5);
		gbc_chkClassRegex.gridx = 3;
		gbc_chkClassRegex.gridy = 0;
		this.add(chkRegex, gbc_chkClassRegex);

		lblValue = new JLabel("Value");
		GridBagConstraints gbc_lblValue = new GridBagConstraints();
		gbc_lblValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblValue.gridx = 0;
		gbc_lblValue.gridy = 1;
		this.add(lblValue, gbc_lblValue);

		spinValue = new WebSpinner();
		GridBagConstraints gbc_txtValue = new GridBagConstraints();
		gbc_txtValue.insets = new Insets(0, 0, 5, 5);
		gbc_txtValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValue.gridx = 1;
		gbc_txtValue.gridy = 1;
		this.add(spinValue, gbc_txtValue);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean matchCase = chkMatchCase.isSelected();
				boolean regex = chkRegex.isSelected();
				Object value = spinValue.getValue();
				String name = txtName.getText();
				
				previous.addException(name, matchCase, regex, (Integer) value);
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 3;
		gbc_btnOk.gridy = 2;
		add(btnOk, gbc_btnOk);
	}
}
