package estruturasAuxiliaresTabelas;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import tabelasInternas.DefaultInternalTable;
import tabelasInternas.GradeInternalTable;
import tabelasInternas.TotalVagasInternalTable;
import tabelasInternas.VagasDesperiotizadosInternalTable;
import tabelasInternas.VagasPeriotizadosInternalTable;

/**
 * Editor de celulas da tabela
 */
@SuppressWarnings("serial")
public class CellEditor extends AbstractCellEditor implements TableCellEditor {

	JComponent component = new JTextField();

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex) {
		if(value instanceof DefaultInternalTable){
			DefaultInternalTable c = (DefaultInternalTable) value;
			return c.getTable();
		}
		if(value instanceof TotalVagasInternalTable){
			TotalVagasInternalTable c = (TotalVagasInternalTable) value;
			return c.getTable();
		}
		if(value instanceof VagasPeriotizadosInternalTable){
			VagasPeriotizadosInternalTable c = (VagasPeriotizadosInternalTable) value;
			return c.getTable();
		}
		if(value instanceof VagasDesperiotizadosInternalTable){
			VagasDesperiotizadosInternalTable c = (VagasDesperiotizadosInternalTable) value;
			return c.getTable();
		}
		if(value instanceof GradeInternalTable){
			GradeInternalTable c = (GradeInternalTable) value;
			return c.getTable();
		}
		
		((JTextField) component).setText((String) value);
		return component;
	}

	@Override
	public Object getCellEditorValue() {
		return ((JTextField) component).getText();
	}
}