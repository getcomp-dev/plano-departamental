package tableModel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetable.Disciplina;
import timetable.Docente;
import timetable.Turma;
import hibernate.DocenteDAO;
import interfaces.Home;
import tabelasInternas.DefaultInternalTable;

/**
 *
 * @author Héber
 */

@SuppressWarnings("serial")
public class ResultadosProfessorTableModel extends AbstractTableModel {		
	private static final int COL_BOTAO = 0;
	private static final int COL_CODIGO_PROFESSOR = 1;
	private static final int COL_NOME_PROFESSOR = 2;
	private static final int COL_DISCIPLINA = 3;
	private static final int COL_CODIGO_DISCIPLINA = 4;
	private static final int COL_CREDITOS_DISCIPLINA = 5;
	private static final int COL_CREDITOS_PROFESSOR = 6;
	
	private String[] colunas = new String[]{"", "Código do Professor", "Nome do Professor", "Disicplinas", "Código da Disciplinas", "Creditos da Disciplina", "Créditos: Atuais / Esperados"};
	private ArrayList<Docente> linhas;
	private DocenteDAO docenteDAO;
	private ArrayList<Boolean> botao;	
	
	public ResultadosProfessorTableModel() {	
		linhas = new ArrayList<Docente>();
		botao = new ArrayList<Boolean>();
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
		ArrayList<String> dadosInternos = new ArrayList<String>();
		ArrayList<Color> coresInternas = new ArrayList<Color>();
		List<Turma> turmas = docente.getTurma();
		
		switch(columnIndex){
			case COL_BOTAO:
				if(botao.get(rowIndex))
					return "-";
				else
					return "+";
			case COL_CODIGO_PROFESSOR:
				return docente.getCodigo();
			case COL_NOME_PROFESSOR:
				return docente.getNomeCompleto();
			case COL_DISCIPLINA:				
				if(turmas.size()>=0 && botao.get(rowIndex)){
					for(Turma t: turmas){
						if(t.getAno() == Home.getAno() && t.getSemestre() == Home.getSemestre()){
							dadosInternos.add(t.getDisciplina().getNome());
							coresInternas.add(Disciplina.getOrSetCoresPerfis(t.getDisciplina().getPerfil()));
						}
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, true);
				}
				else
					return "";
			case COL_CODIGO_DISCIPLINA:
				if(turmas.size()>=0 && botao.get(rowIndex)){
					for(Turma t: turmas){
						if(t.getAno() == Home.getAno() && t.getSemestre() == Home.getSemestre()){
							dadosInternos.add(t.getDisciplina().getCodigo() + " - " + t.getCodigo());
							coresInternas.add(Disciplina.getOrSetCoresPerfis(t.getDisciplina().getPerfil()));
						}
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, true);
				}
				else
					return "";
			case COL_CREDITOS_DISCIPLINA:
				if(turmas.size()>=0 && botao.get(rowIndex)){
					for(Turma t: turmas){
						if(t.getAno() == Home.getAno() && t.getSemestre() == Home.getSemestre()){
							dadosInternos.add(Integer.toString(t.getDisciplina().getCreditos()));
							coresInternas.add(Disciplina.getOrSetCoresPerfis(t.getDisciplina().getPerfil()));
						}
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, true);
				}
				else
					return "";
			case COL_CREDITOS_PROFESSOR:
				int creditosTotais = 0;
				try {
					creditosTotais = docenteDAO.somatorioCreditosPorCodigo(docente.getCodigo(), Home.getAno(), Home.getSemestre());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return  creditosTotais + " / " + docente.getCreditacaoEsperada();
			default:
				System.out.println("Coluna inválida");
				return null;
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex){}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == COL_BOTAO)
			return true;
		return false;
	}

	public void updateDataRows(){
		linhas.clear();
		try {
			linhas = (ArrayList<Docente>) docenteDAO.procuraTodos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		linhas.sort(new Comparator<Docente>() {
			@Override
			public int compare(Docente objeto1, Docente objeto2) {
				if(objeto1.getNomeCompleto().equals(objeto2.getNomeCompleto())){
					return objeto1.getCodigo().compareTo(objeto2.getCodigo());
				}
				return objeto1.getNomeCompleto().compareTo(objeto2.getNomeCompleto());
			}
		});	
		
		botao.clear(); 
		
		for(int i=0; i<linhas.size(); i++)
			botao.add(true);
	}

	public static int getColDisciplina() {
		return COL_DISCIPLINA;
	}

	public static int getColCodigoDisciplina() {
		return COL_CODIGO_DISCIPLINA;
	}

	public static int getColCreditosDisciplina() {
		return COL_CREDITOS_DISCIPLINA;
	}
	
	public static int getColBotao() {
		return COL_BOTAO;
	}

	public int getInternalTableHeight(int rowIndex){
		int height = 0;
		if(botao.get(rowIndex) && linhas.get(rowIndex).getTurma().size() > 0){
			for(Turma t: linhas.get(rowIndex).getTurma()){
				if(t.getAno() == Home.getAno() && t.getSemestre() == Home.getSemestre())
					height += 16;
			}
			
		}
		return height;
	}
	
	public void alteraValorBotao(int index){
		if(botao.get(index))
			botao.set(index, false);
		else
			botao.set(index, true);
	}
}