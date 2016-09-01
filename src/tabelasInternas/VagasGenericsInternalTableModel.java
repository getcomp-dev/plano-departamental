package tabelasInternas;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import hibernate.GenericoDAO;
import timetable.GenericsVagas;

@SuppressWarnings("serial")
public class VagasGenericsInternalTableModel extends AbstractTableModel{
		private static final int COL_DEFAULT = 0;
		private String[] colunas = new String[]{""};
		private List<? extends GenericsVagas> linhas;
		private boolean editable;
		private int campoExibicao;
		public GenericoDAO dao;
		
		private static final int TOTAL_VAGAS = 0; 	//total de vagas
		private static final int PERIOTIZADOS = 1; 	//periotizadas ofertadas 
		private static final int PERIOTIZADOS_N = 2; 	//periotizadas não ofertadas 
		private static final int DESPERIOTIZADOS = 3; 	// desperiotizadas ofertadas 
		private static final int DESPERIOTIZADOS_N = 4;	//desperiotizadas não ofertadas
		
		public VagasGenericsInternalTableModel(List<? extends GenericsVagas> linhas, boolean editable, int campoExibicao) {		
			this.linhas  = linhas;
			this.editable = editable;
			this.dao = new GenericoDAO();
			this.campoExibicao = campoExibicao;
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
					switch (campoExibicao) {
					case TOTAL_VAGAS:
						return linhas.get(rowIndex).getTotalVagas();
					case PERIOTIZADOS:
						return linhas.get(rowIndex).getVagasPeriotOfert();
					case PERIOTIZADOS_N:
						return linhas.get(rowIndex).getVagasPeriotNaoOfert();
					case DESPERIOTIZADOS:
						return linhas.get(rowIndex).getVagasDesperiotOfert();
					case DESPERIOTIZADOS_N:
						return linhas.get(rowIndex).getVagasDesperiotNaoOfert();
					default:
						System.out.println("Coluna inválida");
					}
					return linhas.get(rowIndex).getVagasDesperiotOfert();
				default:
					System.out.println("Coluna inválida");
					return null;
			}
		}
		
		public void setValueAt(Object value, int rowIndex, int columnIndex){
			switch(columnIndex){
			case COL_DEFAULT:
				switch (campoExibicao) {
				case TOTAL_VAGAS:
					linhas.get(rowIndex).setTotalVagas(Integer.parseInt(value.toString()));
				case PERIOTIZADOS:
					linhas.get(rowIndex).setVagasPeriotOfert(Integer.parseInt(value.toString()));
					linhas.get(rowIndex).setTotalVagas(linhas.get(rowIndex).getVagasPeriotOfert() + linhas.get(rowIndex).getVagasDesperiotOfert());
				case PERIOTIZADOS_N:
					linhas.get(rowIndex).setVagasPeriotNaoOfert(Integer.parseInt(value.toString()));
				case DESPERIOTIZADOS:
					linhas.get(rowIndex).setVagasDesperiotOfert(Integer.parseInt(value.toString()));
					linhas.get(rowIndex).setTotalVagas(linhas.get(rowIndex).getVagasPeriotOfert() + linhas.get(rowIndex).getVagasDesperiotOfert());
				case DESPERIOTIZADOS_N:
					linhas.get(rowIndex).setVagasDesperiotNaoOfert(Integer.parseInt(value.toString()));
				default:
					System.out.println("Coluna inválida");
				}
				dao.salvaOuEdita(linhas.get(rowIndex));
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
			return editable;
		}
}
