package tableModel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import hibernate.PeriodoDAO;
import timetable.Curso;
import timetable.Disciplina;
import timetable.Periodo;
import tabelasInternas.GradeInternalTable;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class GradesTableModel extends AbstractTableModel {		
	private static final int COL_BOTAO = 0;
	private static final int COL_PERIODO = 1;
	private static final int COL_GRADE = 2;
	private String[] colunas = new String[]{"", "Período", "Grade"};
	PeriodoDAO periodoDAO;
	private List<Periodo> linhas;
	private ArrayList<Boolean> botao;
	Curso curso;
	
	public GradesTableModel(Curso curso) {
		this.curso = curso;
		linhas = curso.getPeriodo();
		if(linhas == null)
			linhas = new ArrayList<Periodo>();
		botao = new ArrayList<Boolean>();
		periodoDAO = new PeriodoDAO();
		
		for(int i=0; i<linhas.size(); i++)
			botao.add(true);
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
			case COL_BOTAO:
				if(botao.get(rowIndex))
					return "-";
				else
					return "+";
			case COL_PERIODO:
				return linhas.get(rowIndex).getNumeroPeriodo();
			case COL_GRADE:
				if(botao.get(rowIndex)){
					ArrayList<Color> coresInternas = new ArrayList<Color>();
					for(Disciplina t: linhas.get(rowIndex).getDisciplina()){
						coresInternas.add(Disciplina.getOrSetCoresPerfis(t.getPerfil()));
					}
					return new GradeInternalTable(linhas.get(rowIndex), coresInternas);
				}
				return "";
			default:
				System.out.println("Coluna inválida");
				return null;
		}
	}
	
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		switch(columnIndex){
			case COL_PERIODO:
				if(value.toString().matches("^[0-9]*$")){
					int periodo = Integer.parseInt(value.toString());
					linhas.get(rowIndex).setNumeroPeriodo(periodo);
				}else{
					JOptionPane.showMessageDialog(new JFrame(), "O campo \"Periodo\" não é um número, por favor insira-o e tente novamente", "Erro",  JOptionPane.ERROR_MESSAGE);
					fireTableCellUpdated(rowIndex, columnIndex);
				}
				
				if(linhas.get(rowIndex).getNumeroPeriodo() != 0){
					linhas.get(rowIndex).setCurso(curso);
					periodoDAO.salvaOuEdita(linhas.get(rowIndex));
				}		
				break;			
		}		
	}
	
	public Periodo getPeriodo(int rowIndex){
		return linhas.get(rowIndex);
	}
	
	public void addPeriodo(Periodo periodo){
		linhas.add(periodo);
		botao.add(true);
		int ultimoIndice = getRowCount() - 1; 
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void updateCurso(int indiceLinha, Periodo periodo) { 
		linhas.set(indiceLinha, periodo); 
		fireTableRowsUpdated(indiceLinha, indiceLinha);
	}
	
	public void removeCurso(int indiceLinha) { 
		Periodo p = linhas.remove(indiceLinha); 
		fireTableRowsDeleted(indiceLinha, indiceLinha);
		periodoDAO.exclui(p);
	}

	public List<Periodo> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<Periodo> linhas) {
		this.linhas = linhas;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == COL_BOTAO || columnIndex == COL_PERIODO)
			return true;
		return false;
	}
	
	public void updateDataRows(){
		try {
			linhas = curso.getPeriodo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		botao.clear(); 
		
		for(int i=0; i<linhas.size(); i++)
			botao.add(true);
		
		linhas.sort(new Comparator<Periodo>() {
			@Override
			public int compare(Periodo objeto1, Periodo objeto2) {
				return ((Integer)objeto1.getNumeroPeriodo()).compareTo(((Integer)objeto2.getNumeroPeriodo()));
			}
		});
	}
	
	public void alteraValorBotao(int index){
		if(botao.get(index))
			botao.set(index, false);
		else
			botao.set(index, true);
	}
	
	public static int getColBotao() {
		return COL_BOTAO;
	}
	
	public int getInternalTableHeight(int rowIndex){
		int height = 16;
		if(botao.get(rowIndex) && linhas.get(rowIndex).getDisciplina().size() > 0){
			height = height * linhas.get(rowIndex).getDisciplina().size();
		}
		return height;
	}
}
