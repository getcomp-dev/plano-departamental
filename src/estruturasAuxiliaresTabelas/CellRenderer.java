package estruturasAuxiliaresTabelas;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import tabelasInternas.DefaultInternalTable;
import tabelasInternas.GradeInternalTable;
import tabelasInternas.TotalVagasInternalTable;
import tabelasInternas.VagasDesperiotizadosInternalTable;
import tabelasInternas.VagasPeriotizadosInternalTable;

/*
 * classe responsável por renderizar os dados dentro da tabela.
 * útil quando há a necessidade de exibir dados que não são padrões no Jtable como por exemplo uma tabela dentro de outra tabela.
 */
@SuppressWarnings("serial")
public class CellRenderer extends DefaultTableCellRenderer{
	
	private ArrayList<Color> coresLinhas;
	
	public CellRenderer(){
		this.coresLinhas = null;
	}
	
	public CellRenderer(ArrayList<Color> coresLinhas){
		this.coresLinhas = coresLinhas;
	}
	
	/*
	 * função resposável por renderizar o dado da tabela
	 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if(coresLinhas != null){
			this.setBackground(coresLinhas.get(row));
		}
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
		if(value instanceof Date){
			SimpleDateFormat f = new SimpleDateFormat("EEEEE HH:mm");
			value = f.format(value);
		}
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

	public ArrayList<Color> getCoresLinhas() {
		return coresLinhas;
	}

	public void setCoresLinhas(ArrayList<Color> coresLinhas) {
		this.coresLinhas = coresLinhas;
	}
	
	
}
