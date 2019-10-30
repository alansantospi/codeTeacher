package gui.component.table;

import java.util.Arrays;

import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.ImplementsAnalyzer;
import codeteacher.analyzers.ThrowsAnalyzer;

public class ThrowsAnalyzerModel extends RowTableModel<ThrowsAnalyzer> {
	
	private static String[] COLUMN_NAMES = { 
			"Name", "Match case", "Regex", "Value" };

	public ThrowsAnalyzerModel() {
		super(Arrays.asList(COLUMN_NAMES));
		setRowClass(FieldAnalyzer.class);

		setColumnClass(1, Boolean.class);
		setColumnClass(2, Boolean.class);
	}

	@Override
	public Object getValueAt(int row, int column) {
		ThrowsAnalyzer analyzer = getRow(row);

		switch (column) {
		case 0:
			return analyzer.getMemberName();
		case 1:
			return analyzer.isMatchCase();
		case 2:
			return analyzer.isRegex();
		case 3:
			return analyzer.getTotalValue();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
//		FieldAnalyzer button = getRow(row);
//
//		switch (column)
//        {
//            case 0: button.setKlazz(klazz);Text((String)value); break;
//            case 1: button.setToolTipText((String)value); break;
//            case 2: button.setEnabled((Boolean)value); break;
//            case 3: button.setVisible((Boolean)value); break;
//        }
//
//		fireTableCellUpdated(row, column);
	}

}