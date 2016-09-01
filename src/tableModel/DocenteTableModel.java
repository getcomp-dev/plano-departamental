package tableModel;

import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import hibernate.DocenteDAO;
import timetable.Disciplina;
import timetable.Docente;

@SuppressWarnings("serial")
public class DocenteTableModel extends AbstractTableModel /* extends AbstractTableModel */{
	private static final int COL_CODIGO = 0;
	private static final int COL_NOME_COMPLETO = 1;
	private static final int COL_CREDITACAO_ESPERADA = 2;
	private String[] colunas = new String[]{"C�digo", "Nome Completo", "Credita��o Esperada"};
	private ArrayList<Docente> linhas;
	private DocenteDAO docenteDAO;
	
	public DocenteTableModel() {		
		linhas = new ArrayList<Docente>();
		docenteDAO = new DocenteDAO();
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
		Docente docente = linhas.get(rowIndex);
		
		switch(columnIndex){
			case COL_CODIGO:
				return docente.getCodigo();
			case COL_NOME_COMPLETO:
				return docente.getNomeCompleto();
			case COL_CREDITACAO_ESPERADA:
				return docente.getCreditacaoEsperada();
			default:
				System.out.println("Coluna inv�lida");
				return null;
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		Docente docente = linhas.get(rowIndex);
		
		switch(columnIndex){
			case COL_CODIGO:
				Boolean existeCodigo = false;
				for(Docente d: linhas){
					if(d.getCodigo() != null && d.getCodigo().equals(value.toString())){
						existeCodigo = true;
					}
				}
				if(existeCodigo){
					JOptionPane.showMessageDialog(new JFrame(), "O valor inserido no campo \"C�digo\" j� existe, por favor insira-o novamente", "Erro",  JOptionPane.ERROR_MESSAGE);
					fireTableCellUpdated(rowIndex, columnIndex);
				}else
					docente.setCodigo(value.toString());
				break;
			case COL_NOME_COMPLETO:
				if(value.toString().indexOf(" ") == -1){
					JOptionPane.showMessageDialog(new JFrame(), "Insira nome e sobrenome do prefessor no campo \"Nome Completo\"", "Erro",  JOptionPane.ERROR_MESSAGE);
					fireTableCellUpdated(rowIndex, columnIndex);
				}else{
					docente.setNomeCompleto(value.toString());
					docente.setNome(value.toString().substring(0, value.toString().indexOf(" ")));
				}
				break;
			case COL_CREDITACAO_ESPERADA:
				if(value.toString().matches("^[0-9]*$")){
					int creditacaoEsperada = Integer.parseInt(value.toString());
					docente.setCreditacaoEsperada(creditacaoEsperada);
				}else{
					JOptionPane.showMessageDialog(new JFrame(), "O valor inserido no campo \"Credita��o esperada\" n�o � um n�mero, por favor insira-o novamente", "Erro",  JOptionPane.ERROR_MESSAGE);
					fireTableCellUpdated(rowIndex, columnIndex);
				}
				break;
			default:
				System.out.println("Coluna inv�lida");
		}
		
		if(docente.getCodigo() != null && docente.getNome() != null && docente.getNomeCompleto() != null && docente.getCreditacaoEsperada() != 0)
			docenteDAO.salvaOuEdita(docente);
	}
	
	public Docente getDocente(int rowIndex){
		return linhas.get(rowIndex);
	}
	
	public void addDocente(Docente docente){
		linhas.add(docente);
		int ultimoIndice = getRowCount() - 1; 
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void updateDocente(int indiceLinha, Docente docente) { 
		linhas.set(indiceLinha, docente); 
		fireTableRowsUpdated(indiceLinha, indiceLinha); 
	}
	
	public void removeDocente(int indiceLinha) { 
		Docente d = linhas.remove(indiceLinha); 
		fireTableRowsDeleted(indiceLinha, indiceLinha);
		docenteDAO.exclui(d);
	}

	public ArrayList<Docente> getLinhas() {
		return linhas;
	}

	public void setLinhas(ArrayList<Docente> linhas) {
		this.linhas = linhas;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
	
	public void updateDataRows(){
		try {
			linhas = (ArrayList<Docente>) docenteDAO.procuraTodos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		linhas.sort(new Comparator<Docente>() {
			@Override
			public int compare(Docente objeto1, Docente objeto2) {
				return objeto1.getNomeCompleto().compareTo(objeto2.getNomeCompleto());
			}
		});
	}
}