package tabelasInternas;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
/**
*
* @author Héber
*/

//tabela interna com apenas uma coluna
@SuppressWarnings("serial")
public class DefaultInternalTableModel extends AbstractTableModel{
	private static final int COL_DEFAULT = 0;
	private String[] colunas = new String[]{""};
	private ArrayList<String> linhas;
	private boolean editable;
	
	public DefaultInternalTableModel(ArrayList<String> linhas) {		
		this.linhas  = linhas;
		editable = false;
	}
	
	public DefaultInternalTableModel(ArrayList<String> linhas, boolean editable) {		
		this.linhas  = linhas;
		this.editable = editable;
	}

	@Override
	public int getColumnCount() {
		return this.colunas.length;
	}

	@Override
	public int getRowCount() {
		return this.linhas.size();
	}
	
	@Override
	public String getColumnName(int columnIndex) { 
		return colunas[columnIndex]; 
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		switch(columnIndex){
			case COL_DEFAULT:
				return linhas.get(rowIndex);
			default:
				System.out.println("Coluna inválida");
				return null;
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		switch(columnIndex){
		case COL_DEFAULT:
			linhas.set(rowIndex, value.toString());
		}
	}
	
	public ArrayList<String> getLinhas() {
		return linhas;
	}

	public void setLinhas(ArrayList<String> linhas) {
		this.linhas = linhas;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return editable;
	}
}
