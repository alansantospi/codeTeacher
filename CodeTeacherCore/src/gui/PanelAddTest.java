package gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;

import codeteacher.behave.ConstructorCall;
import codeteacher.behave.ExecutorImpl;
import gui.component.table.ExecutorImplModel;
import gui.msg.FrameAddOutputMsg;
import utils.ReflectionUtils;

public class PanelAddTest extends WebPanel {

	private FrameAddOutputMsg msg = FrameAddOutputMsg.TITLE;

	private PanelAddTest thisPanel;
	private JFrame previous;

	private PanelAddAnalyzer<ExecutorImpl> pnlTestCases;

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
	public PanelAddTest(JFrame previous) {
		this.previous = previous;
		this.thisPanel = this;
		setBounds(100, 100, 661, 552);
		setBorder(new EmptyBorder(5, 5, 5, 5));

		GridBagLayout gbl_pnlTests = new GridBagLayout();
		gbl_pnlTests.columnWidths = new int[] { 625 };
		gbl_pnlTests.rowHeights = new int[] { 0, 0 };
		gbl_pnlTests.columnWeights = new double[] { 1.0 };
		gbl_pnlTests.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		thisPanel.setLayout(gbl_pnlTests);

		JPanel pnlAnalyzers = new JPanel();
		GridBagConstraints gbc_pnlAnalyzers = new GridBagConstraints();
		gbc_pnlAnalyzers.fill = GridBagConstraints.BOTH;
		gbc_pnlAnalyzers.gridx = 0;
		gbc_pnlAnalyzers.gridy = 0;
		add(pnlAnalyzers, gbc_pnlAnalyzers);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 295, 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlAnalyzers.setLayout(gbl_panel_4);

		pnlTestCases = new PanelAddAnalyzer<>(ExecutorImpl.class, new AddCommand());

//		pnlTestCases = new PanelAddAnalyzer<>(ExecutorImpl.class);
//		pnlFields.btnAdd.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				WebFrame frame = new WebFrame();
//				ClassAnalyzer classAnalyzer = createAnalyzer();
//				PanelAddField2 pnlAddField = new PanelAddField2(classAnalyzer, pnlFields);
//				frame.setContentPane(pnlAddField);
//				frame.pack();
//				frame.center();
//				frame.setVisible(true);
//			}
//		});

		GridBagConstraints gbc_panelTestCases = new GridBagConstraints();
		gbc_panelTestCases.gridwidth = 3;
		gbc_panelTestCases.insets = new Insets(0, 0, 5, 5);
		gbc_panelTestCases.gridx = 0;
		gbc_panelTestCases.gridy = 0;
		pnlAnalyzers.add(pnlTestCases, gbc_panelTestCases);

		JButton btnOk = new JButton("Ok");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 2;
		pnlTestCases.add(btnOk, gbc_btnOk);

		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createExecutor();
			}
		});

	}

	class AddCommand implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			WebFrame frame = new WebFrame();
//			JPanel contentPane = new WebPanel();

			PanelAddTestCase contentPane = new PanelAddTestCase();

			frame.setContentPane(contentPane);

			contentPane.addAction(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ExecutorImpl analyzer = contentPane.createAnalyzer();
					pnlTestCases.addAnalyzer(analyzer);
					pnlTestCases.updateButtons();
				}
			});

			frame.pack();
			frame.center();
			frame.setVisible(true);

		}
	}

	public void createExecutor() {
		ExecutorImplModel testModel = (ExecutorImplModel) pnlTestCases.getModel();
		for (int i = 0; i < testModel.getRowCount(); i++) {
			ExecutorImpl row = testModel.getRow(i);
//			String destDir = "";
//			ClassLoader loader = ReflectionUtils.getClassLoader(destDir);
			ClassLoader loader = ClassLoader.getSystemClassLoader();
			((ConstructorCall) row).setLoader(loader);
			if (row.exec()) {
				System.out.println(row.getError());
			}
		}
	}
}