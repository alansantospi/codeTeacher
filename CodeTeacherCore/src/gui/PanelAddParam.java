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

import gui.component.ComponentUtils;
import utils.ClassPathUtils;

public class PanelAddParam extends JPanel {

	private static final String ARRAY = "[]";
	private JPanel contentPane;
	private JTextField textParam;
	private JPanel panel;
	private JButton btnOk;
	private JButton btnCancel;
	private JScrollPane scrollPane;
	private JList paramList;
	private PanelAddParam thisFrame;
	private PanelAddMethod previousFrame;
	private JCheckBox chkArray;
	private JLabel lblEnclosingType;
	private JPanel pnlEnclosingType;
	private JCheckBox chkCollection;
	private JPanel panel_1;
	private JCheckBox chkByte;
	private JCheckBox chkShort;
	private JCheckBox chkInt;
	private JCheckBox chkLong;
	private JCheckBox chkFloat;
	private JCheckBox chkDouble;
	private JCheckBox chkBoolean;
	private JCheckBox chkChar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.setContentPane(new PanelAddParam());
//					frame.pack();
					frame.setBounds(100, 100, 610, 452);
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
	
	public PanelAddParam(PanelAddMethod previousFrame) {
		this();
		this.previousFrame = previousFrame;
	}
	
	public PanelAddParam() {
		this.contentPane = this;
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblEnclosingType = new JLabel("Enclosing type");
		GridBagConstraints gbc_lblEnclosingType = new GridBagConstraints();
		gbc_lblEnclosingType.anchor = GridBagConstraints.WEST;
		gbc_lblEnclosingType.insets = new Insets(0, 0, 5, 0);
		gbc_lblEnclosingType.gridx = 0;
		gbc_lblEnclosingType.gridy = 0;
		contentPane.add(lblEnclosingType, gbc_lblEnclosingType);
		
		pnlEnclosingType = new JPanel();
		GridBagConstraints gbc_pnlEnclosingType = new GridBagConstraints();
		gbc_pnlEnclosingType.fill = GridBagConstraints.BOTH;
		gbc_pnlEnclosingType.insets = new Insets(0, 0, 5, 0);
		gbc_pnlEnclosingType.gridx = 0;
		gbc_pnlEnclosingType.gridy = 1;
		contentPane.add(pnlEnclosingType, gbc_pnlEnclosingType);
		GridBagLayout gbl_pnlEnclosingType = new GridBagLayout();
		gbl_pnlEnclosingType.columnWidths = new int[]{53, 0, 0};
		gbl_pnlEnclosingType.rowHeights = new int[]{23, 0};
		gbl_pnlEnclosingType.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlEnclosingType.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlEnclosingType.setLayout(gbl_pnlEnclosingType);
		
		chkArray = new JCheckBox("Array");
		GridBagConstraints gbc_chkArray = new GridBagConstraints();
		gbc_chkArray.insets = new Insets(0, 0, 0, 5);
		gbc_chkArray.anchor = GridBagConstraints.WEST;
		gbc_chkArray.gridx = 0;
		gbc_chkArray.gridy = 0;
		pnlEnclosingType.add(chkArray, gbc_chkArray);
		
		chkCollection = new JCheckBox("Collection");
		GridBagConstraints gbc_chkCollection = new GridBagConstraints();
		gbc_chkCollection.gridx = 1;
		gbc_chkCollection.gridy = 0;
		pnlEnclosingType.add(chkCollection, gbc_chkCollection);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		contentPane.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{53, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{23, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		chkByte = new JCheckBox("byte");
		GridBagConstraints gbc_chkByte = new GridBagConstraints();
		gbc_chkByte.insets = new Insets(0, 0, 0, 5);
		gbc_chkByte.gridx = 0;
		gbc_chkByte.gridy = 0;
		panel_1.add(chkByte, gbc_chkByte);
		
		chkShort = new JCheckBox("short");
		GridBagConstraints gbc_chkShort = new GridBagConstraints();
		gbc_chkShort.insets = new Insets(0, 0, 0, 5);
		gbc_chkShort.gridx = 1;
		gbc_chkShort.gridy = 0;
		panel_1.add(chkShort, gbc_chkShort);
		
		chkInt = new JCheckBox("int");
		GridBagConstraints gbc_chkInt = new GridBagConstraints();
		gbc_chkInt.insets = new Insets(0, 0, 0, 5);
		gbc_chkInt.gridx = 2;
		gbc_chkInt.gridy = 0;
		panel_1.add(chkInt, gbc_chkInt);
		
		chkLong = new JCheckBox("long");
		GridBagConstraints gbc_chkLong = new GridBagConstraints();
		gbc_chkLong.insets = new Insets(0, 0, 0, 5);
		gbc_chkLong.gridx = 3;
		gbc_chkLong.gridy = 0;
		panel_1.add(chkLong, gbc_chkLong);
		
		chkFloat = new JCheckBox("float");
		GridBagConstraints gbc_chkFloat = new GridBagConstraints();
		gbc_chkFloat.insets = new Insets(0, 0, 0, 5);
		gbc_chkFloat.gridx = 4;
		gbc_chkFloat.gridy = 0;
		panel_1.add(chkFloat, gbc_chkFloat);
		
		chkDouble = new JCheckBox("double");
		GridBagConstraints gbc_chkDouble = new GridBagConstraints();
		gbc_chkDouble.insets = new Insets(0, 0, 0, 5);
		gbc_chkDouble.gridx = 5;
		gbc_chkDouble.gridy = 0;
		panel_1.add(chkDouble, gbc_chkDouble);
		
		chkBoolean = new JCheckBox("boolean");
		GridBagConstraints gbc_chkBoolean = new GridBagConstraints();
		gbc_chkBoolean.insets = new Insets(0, 0, 0, 5);
		gbc_chkBoolean.gridx = 6;
		gbc_chkBoolean.gridy = 0;
		panel_1.add(chkBoolean, gbc_chkBoolean);
		
		chkChar = new JCheckBox("char");
		GridBagConstraints gbc_chkChar = new GridBagConstraints();
		gbc_chkChar.gridx = 7;
		gbc_chkChar.gridy = 0;
		panel_1.add(chkChar, gbc_chkChar);
		
		JLabel lblNewLabel = new JLabel("Param type");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		textParam = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 4;
		contentPane.add(textParam, gbc_textField);
		textParam.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Matching items");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 5;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 6;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		paramList = new JList();
		scrollPane.setViewportView(paramList);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 7;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
//				ComponentUtils.closeFrame(thisFrame);
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridwidth = 3;
		gbc_btnCancel.gridx = 14;
		gbc_btnCancel.gridy = 1;
		panel.add(btnCancel, gbc_btnCancel);
		
		btnOk = new JButton("OK");
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
//		thisFrame.dispose();
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
