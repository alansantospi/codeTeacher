package gui.component;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
	
	public static void addRow(final JTable table, String paramType, String paramName){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount()+1;
		model.addRow(new String[] {paramType, paramName + String.valueOf(rowCount)});
		table.setModel(model);
	}
	
	public static void closeFrame(JFrame frame){
		frame.dispose();
	}

}
