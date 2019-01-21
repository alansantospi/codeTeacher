package gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utils.ClassPathUtils;

public class FrameAddBehaviorParam extends JFrame {

	private static final String BTN_OK = "OK";
	private static final String CHK_ARRAY = "Array";
	private static final String LBL_MATCHING_ITEMS = "Matching items";
	private static final String LBL_PARAM_TYPE = "Param type";
	private static final String ADD_PARAM = "Add param";
	private static final String BTN_CANCEL = "Cancel";
	private static final String ARRAY = "[]";
	private JPanel contentPane;
	private JTextField textParam;
	private JPanel panel;
	private JButton btnOk;
	private JButton btnCancel;
	private JScrollPane scrollPane;
	private JList paramList;
	private FrameAddBehaviorParam thisFrame;
	private FrameAddBehavior previousFrame;
	private JCheckBox chkArray;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameAddBehaviorParam frame = new FrameAddBehaviorParam(null);
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
	public FrameAddBehaviorParam(FrameAddBehavior previousFrame) {
		this.thisFrame = this;
		this.previousFrame = previousFrame;
		setTitle(ADD_PARAM);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 49, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel(LBL_PARAM_TYPE);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		textParam = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		contentPane.add(textParam, gbc_textField);
		textParam.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel(LBL_MATCHING_ITEMS);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		paramList = new JList();
		scrollPane.setViewportView(paramList);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 10;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		chkArray = new JCheckBox(CHK_ARRAY);
		GridBagConstraints gbc_chkArray = new GridBagConstraints();
		gbc_chkArray.insets = new Insets(0, 0, 5, 5);
		gbc_chkArray.gridx = 0;
		gbc_chkArray.gridy = 0;
		panel.add(chkArray, gbc_chkArray);
		
		btnCancel = new JButton(BTN_CANCEL);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridwidth = 3;
		gbc_btnCancel.gridx = 14;
		gbc_btnCancel.gridy = 1;
		panel.add(btnCancel, gbc_btnCancel);
		
		btnOk = new JButton(BTN_OK);
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.fill = GridBagConstraints.BOTH;
		gbc_btnOk.gridx = 17;
		gbc_btnOk.gridy = 1;
		panel.add(btnOk, gbc_btnOk);
		
		//Actions
		btnOk.addActionListener(new AddParamListener());
		textParam.getDocument().addDocumentListener(new ChangeListener());
		paramList.addListSelectionListener(new ParamListSelectionListener());
		paramList.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent evt) {
		        if (evt.getClickCount() == 2) {
		        	addParam();
		        }
		    }
		});
		updateButtons();
	}
	
	public void updateParamList(){
		String pattern = textParam.getText();
		DefaultListModel<String> listModel = new DefaultListModel<String>();

		if (!pattern.equals("")){
			List<String> match = ClassPathUtils.match(pattern);
			for (String value : match) {
				listModel.addElement(value);
			}
		}
		paramList.setModel(listModel);
		updateButtons();
	}
	
	private void addParam() {
		String paramType = (String) paramList.getSelectedValue();
		
		if (chkArray.isSelected()){
			paramType = paramType + ARRAY;
		}
		previousFrame.addParam(paramType);
//		ComponentUtils.closeFrame(thisFrame);
		thisFrame.dispose();
	}
	
	private void updateButtons(){
		btnOk.setEnabled(paramList.getSelectedIndex()>-1);
	}
	
	public class ChangeListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			updateParamList();			
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			updateParamList();	
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			updateParamList();	
		}
	}
	
	public class AddParamListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			addParam();
		}		
	}
	
	public class ParamListSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			updateButtons();			
		}		
	}
}
