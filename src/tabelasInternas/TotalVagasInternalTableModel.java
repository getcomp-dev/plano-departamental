package tabelasInternas;

import hibernate.GenericoDAO;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetable.GenericsVagas;

@SuppressWarnings("serial")
public class TotalVagasInternalTableModel extends AbstractTableModel{
	private static final int COL_DEFAULT = 0;
	private String[] colunas = new String[]{""};
	private List<? extends GenericsVagas> linhas;
	public GenericoDAO dao;
	
	public TotalVagasInternalTableModel(List<? extends GenericsVagas> linhas) {		
		this.linhas  = linhas;
		this.dao = new GenericoDAO();
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
				return linhas.get(rowIndex).getTotalVagas();
			default:
				System.out.println("Coluna inválida");
				return null;
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		
	}
	
	public List<? extends GenericsVagas> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<? extends GenericsVagas> linhas) {
		this.linhas = linhas;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
