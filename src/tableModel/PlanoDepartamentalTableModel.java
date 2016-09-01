package tableModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import timetable.Docente;
import timetable.Turma;
import hibernate.DocenteDAO;
import hibernate.TurmaDAO;
import interfaces.Home;

/**
 *
 * @author Héber
 */

@SuppressWarnings("serial")
public class PlanoDepartamentalTableModel extends AbstractTableModel {		
	private static final int COL_SEMESTRE = 0;
	private static final int COL_NOME_DISCIPLINA = 1;
	private static final int COL_CODIGO_TURMA = 2;
	private static final int COL_HORARIO = 3;
	private static final int COL_CREDITOS = 4;
	private static final int COL_DOCENTE = 5;
	private static final int COL_VAGA = 6;
	private String[] colunas = new String[]{"Semestre", "Nome", "Código", "Horário", "Créditos", "Docente", "Numero de Vagas"};
	private ArrayList<Turma> linhas;
	private ArrayList<Docente> docente;
	private TurmaDAO turmaDAO;
	private DocenteDAO docenteDAO;
	JComboBox<String> comboBox;
	
	public PlanoDepartamentalTableModel() {	
		linhas = new ArrayList<Turma>();
		docente = new ArrayList<Docente>();
		turmaDAO = new TurmaDAO();
		docenteDAO = new DocenteDAO();
		comboBox = new JComboBox<String>();		
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
		Turma turma = linhas.get(rowIndex);
		
		switch(columnIndex){
			case COL_SEMESTRE:
				return "Semestre";
			case COL_NOME_DISCIPLINA:
				return turma.getDisciplina().getNome();
			case COL_CODIGO_TURMA:
				return turma.getDisciplina().getCodigo() + " - " + turma.getCodigo();
			case COL_HORARIO:
				return "Horário";
			case COL_CREDITOS:
				return turma.getDisciplina().getCreditos();
			case COL_DOCENTE:
				if(turma.getDocente().size()>=1)
					return turma.getDocente().get(0).getNomeCompleto();
				return "Não há docentes para esta turma";
			case COL_VAGA:
				return turma.getMaxVagas();
			default:
				System.out.println("Coluna inválida");
				return null;
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		Turma turma = linhas.get(rowIndex);
		
		switch(columnIndex){
		case COL_DOCENTE:
			System.out.println(value.toString());
			Docente docente = null;
			try {
				docente = docenteDAO.encontraDocentePorName(value.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(docente != null){
				turma.getDocente().clear();
				turma.getDocente().add(docente);
				turmaDAO.salvaOuEdita(turma);
			}else
				fireTableRowsUpdated(rowIndex, rowIndex);
			break;
		default:
			System.out.println("Coluna inválida");
			break;
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == COL_DOCENTE)
			return true;
		return false;
	}

	public static int getColDocente() {
		return COL_DOCENTE;
	}

	public JComboBox<String> getComboBox() {
		return comboBox;
	}
	
	public void updateDataRows(){
		try {
			linhas = (ArrayList<Turma>) turmaDAO.procuraTurmasPorAnoSemestre(Home.getAno(), Home.getSemestre());
			docente = (ArrayList<Docente>) docenteDAO.procuraTodos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		comboBox.removeAllItems();
		
		Iterator<Docente> it = docente.iterator();
		while(it.hasNext()){
			comboBox.addItem(((Docente)it.next()).getNomeCompleto());
		}
		
		linhas.sort(new Comparator<Turma>() {
			@Override
			public int compare(Turma objeto1, Turma objeto2) {
				if(objeto1.getDisciplina().getPerfil().equals(objeto2.getDisciplina().getPerfil())){
					if(objeto1.getDisciplina().getNome().equals(objeto2.getDisciplina().getNome())){
						return objeto1.getCodigo().compareTo(objeto2.getCodigo());
					}
					return objeto1.getDisciplina().getNome().compareTo(objeto2.getDisciplina().getNome());						
				}						
				return objeto1.getDisciplina().getPerfil().compareTo(objeto2.getDisciplina().getPerfil());
			}
		});
	}

	public ArrayList<Turma> getLinhas() {
		return linhas;
	}
	
	
}