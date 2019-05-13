package gui.component;

import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.NumberFormatter;

import com.alee.laf.spinner.WebSpinner;

public class ComponentUtils {

	public static void clearTable(final JTable table) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		while(model.getRowCount() > 0){
		    model.removeRow(0);
		}
//		model.getDataVector().removeAllElements();
//	   for (int i = 0; i < table.getRowCount(); i++) {
//	      for(int j = 0; j < table.getColumnCount(); j++) {
//	          table.setValueAt("", i, j);
//	      }
//	   }
	}
	
	public static void removeRow(final JTable table, int rowToRemove){
		((DefaultTableModel) table.getModel()).removeRow(rowToRemove);
	}
	
	public static void moveRowDown(final JTable table, int rowToRemove){
		((DefaultTableModel) table.getModel()).moveRow(rowToRemove, rowToRemove, ++rowToRemove);
		table.setRowSelectionInterval(rowToRemove, rowToRemove);
	}

	public static void moveRowUp(final JTable table, int rowToRemove){
		((DefaultTableModel) table.getModel()).moveRow(rowToRemove, rowToRemove, --rowToRemove);
		table.setRowSelectionInterval(rowToRemove, rowToRemove);
	}
	
	public static void addRow(final JTable table, String paramType, boolean countRows, String paramName){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (countRows) {
			int rowCount = model.getRowCount()+1;
			paramName = paramName + String.valueOf(rowCount);
		}
		model.addRow(new String[] {paramType, paramName});
		table.setModel(model);
	}
	
	public static void closeFrame(JFrame frame){
		frame.dispose();
	}

	public static WebSpinner createSpinner(WebSpinner webSpinner) {
		webSpinner = new WebSpinner(); 
		webSpinner.setModel(new SpinnerNumberModel(0, 0, 100, 0.1));
		webSpinner.setEditor(new WebSpinner.NumberEditor(webSpinner, "##.##"));
		JFormattedTextField txt = ((JSpinner.NumberEditor) webSpinner.getEditor()).getTextField();
		
		NumberFormatter formatter = (NumberFormatter) txt.getFormatter(); 
		DecimalFormat decimalFormat = new DecimalFormat("0.0"); 
		formatter.setFormat(decimalFormat); 
		
		((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
		
		return webSpinner;
	}
}
