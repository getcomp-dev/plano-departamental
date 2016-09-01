package tabelasInternas;

import hibernate.GenericoDAO;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import timetable.GenericsVagas;

@SuppressWarnings("serial")
public class VagasDesperiotizadosInternalTableModel  extends AbstractTableModel{
	private static final int COL_DEFAULT = 0;
	private String[] colunas = new String[]{""};
	private List<? extends GenericsVagas> linhas;
	public GenericoDAO dao;
	
	public VagasDesperiotizadosInternalTableModel(List<? extends GenericsVagas> linhas) {		
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
				return linhas.get(rowIndex).getVagasDesperiotOfert();
			default:
				System.out.println("Coluna inválida");
				return null;
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		switch(columnIndex){
		case COL_DEFAULT:
			if(value.toString().matches("^[0-9]*$")){
				linhas.get(rowIndex).setVagasDesperiotOfert(Integer.parseInt(value.toString()));
				linhas.get(rowIndex).setTotalVagas(linhas.get(rowIndex).getVagasPeriotOfert() + linhas.get(rowIndex).getVagasDesperiotOfert());
				dao.salvaOuEdita(linhas.get(rowIndex));
			}else{
				JOptionPane.showMessageDialog(new JFrame(), "O valor inserido no campo \"Vagas Desperiotizados\" não é um número, por favor insira-o novamente", "Erro",  JOptionPane.ERROR_MESSAGE);
				fireTableCellUpdated(rowIndex, columnIndex);
			}			
			break;
		default:
			System.out.println("Coluna inválida");
		}
	}
	
	public List<? extends GenericsVagas> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<? extends GenericsVagas> linhas) {
		this.linhas = linhas;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

}
