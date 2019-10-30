package gui.component.table;

import java.util.Arrays;

import codeteacher.analyzers.FieldAnalyzer;
import codeteacher.analyzers.ModifierAnalyzer;

public class FieldAnalyzerModel extends RowTableModel<FieldAnalyzer> {
	
	private static String[] COLUMN_NAMES = { 
			"Name", "Type", "Declared", "Match case", "Regex", "Visibility", "Value" };

	public FieldAnalyzerModel() {
		super(Arrays.asList(COLUMN_NAMES));
		setRowClass(FieldAnalyzer.class);

		setColumnClass(2, Boolean.class);
		setColumnClass(3, Boolean.class);
		setColumnClass(4, Boolean.class);
	}

	@Override
	public Object getValueAt(int row, int column) {
		FieldAnalyzer analyzer = getRow(row);

		switch (column) {
		case 0:
			return analyzer.getMemberName();
		case 1:
			return analyzer.getType();
		case 2:
			return analyzer.isDeclared();
		case 3:
			return analyzer.isMatchCase();
		case 4:
			return analyzer.isRegex();
		case 5:
			StringBuilder sb = new StringBuilder();
			for (ModifierAnalyzer a : analyzer.getChildren()) {
				sb.append(a.getModifier()+" ");
			}
			return sb.toString();
		case 6:
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