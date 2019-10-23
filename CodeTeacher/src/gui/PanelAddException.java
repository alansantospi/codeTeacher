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

import com.alee.laf.panel.WebPanel;
import com.alee.laf.spinner.WebSpinner;

import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.ImplementsAnalyzer;
import codeteacher.analyzers.ThrowsAnalyzer;
import gui.component.ComponentUtils;
import gui.msg.I18N;

public class PanelAddException extends JPanel {

	private JLabel lblName;
	private JTextField txtName;
	private JCheckBox chkMatchCase;
	private JCheckBox chkRegex;
	private JLabel lblValue;
	private WebSpinner spinValue;
	private JButton btnOk;

	private WebPanel previous;
	private JButton btnCancel;

	public PanelAddException(WebPanel previous) {
		this.previous = previous;
		this.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_pnlClass = new GridBagLayout();
		gbl_pnlClass.columnWidths = new int[] { 59, 0, 124, 0, 0, 0, 0 };
		gbl_pnlClass.rowHeights = new int[] { 37, 0, 0, 0 };
		gbl_pnlClass.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlClass.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_pnlClass);

		lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblClassname = new GridBagConstraints();
		gbc_lblClassname.insets = new Insets(0, 0, 5, 5);
		gbc_lblClassname.gridx = 0;
		gbc_lblClassname.gridy = 0;
		this.add(lblName, gbc_lblClassname);

		txtName = new JTextField();
		txtName.setColumns(10);
		GridBagConstraints gbc_txtClassName = new GridBagConstraints();
		gbc_txtClassName.gridwidth = 3;
		gbc_txtClassName.insets = new Insets(0, 0, 5, 5);
		gbc_txtClassName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtClassName.gridx = 1;
		gbc_txtClassName.gridy = 0;
		this.add(txtName, gbc_txtClassName);

		lblValue = new JLabel("Value");
		GridBagConstraints gbc_lblValue = new GridBagConstraints();
		gbc_lblValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblValue.gridx = 4;
		gbc_lblValue.gridy = 0;
		this.add(lblValue, gbc_lblValue);

		spinValue = ComponentUtils.createSpinner(spinValue);
//		spinValue.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(1)));
		GridBagConstraints gbc_txtValue = new GridBagConstraints();
		gbc_txtValue.insets = new Insets(0, 0, 5, 0);
		gbc_txtValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValue.gridx = 5;
		gbc_txtValue.gridy = 0;
		this.add(spinValue, gbc_txtValue);

		chkMatchCase = new JCheckBox(I18N.getVal(CASE_SENSITIVE));
		GridBagConstraints gbc_chkClassMatchCase = new GridBagConstraints();
		gbc_chkClassMatchCase.insets = new Insets(0, 0, 5, 5);
		gbc_chkClassMatchCase.gridx = 1;
		gbc_chkClassMatchCase.gridy = 1;
		this.add(chkMatchCase, gbc_chkClassMatchCase);

		chkRegex = new JCheckBox(I18N.getVal(REGEX));
		GridBagConstraints gbc_chkClassRegex = new GridBagConstraints();
		gbc_chkClassRegex.insets = new Insets(0, 0, 5, 5);
		gbc_chkClassRegex.gridx = 2;
		gbc_chkClassRegex.gridy = 1;
		this.add(chkRegex, gbc_chkClassRegex);

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
	}
	
	public ImplementsAnalyzer createAnalyzer() {
		boolean matchCase = chkMatchCase.isSelected();
		boolean regex = chkRegex.isSelected();
		String name = txtName.getText();
		Double value = (Double) spinValue.getValue();

		ImplementsAnalyzer implementsAnalyzer = new ImplementsAnalyzer(null, name, value.intValue());
		return implementsAnalyzer;
	}

	public ThrowsAnalyzer createThrowsAnalyzer() {
		boolean matchCase = chkMatchCase.isSelected();
		boolean regex = chkRegex.isSelected();
		String name = txtName.getText();
		Double value = (Double) spinValue.getValue();

		ThrowsAnalyzer throwsAnalyzer = new ThrowsAnalyzer(null, name, matchCase, regex, value.intValue());
		return throwsAnalyzer;
	}

	
	public void addAction(ActionListener actionListener) {
		btnOk.addActionListener(actionListener);		
	}
}
