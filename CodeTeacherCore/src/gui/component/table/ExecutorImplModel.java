package gui.component.table;

import java.util.Arrays;

import codeteacher.behave.ExecutorImpl;

public class ExecutorImplModel extends RowTableModel<ExecutorImpl> {
	
	private static String[] COLUMN_NAMES = { 
			"Type", "Alias" };

	public ExecutorImplModel() {
		super(Arrays.asList(COLUMN_NAMES));
		setRowClass(ExecutorImpl.class);

//		setColumnClass(2, String.class);
//		setColumnClass(3, String.class);
	}

	@Override
	public Object getValueAt(int row, int column) {
		ExecutorImpl analyzer = getRow(row);

		switch (column) {
		case 0:
			return analyzer.getType();
		case 1:
			return analyzer.getAlias();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		ExecutorImpl button = getRow(row);
//
		switch (column)
        {
//            case 0: button.setKlazz(klazz);Text((String)value); break;
            case 1: button.setAlias((String)value); break;
            case 2: button.setType((String)value); break;
//            case 2: button.setEnabled((Boolean)value); break;
//            case 3: button.setVisible((Boolean)value); break;
        }

		fireTableCellUpdated(row, column);
	}

}