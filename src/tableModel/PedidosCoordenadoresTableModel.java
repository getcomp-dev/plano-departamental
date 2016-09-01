package tableModel;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import timetable.Curso;
import timetable.Disciplina;
import timetable.PedidosCoordenadores;
import timetable.VagasAtendidas;
import timetable.VagasSolicitadas;
import hibernate.CursoDAO;
import hibernate.DisciplinaDAO;
import interfaces.Home;
import tabelasInternas.DefaultInternalTable;
import tabelasInternas.TotalVagasInternalTable;
import tabelasInternas.VagasDesperiotizadosInternalTable;
import tabelasInternas.VagasPeriotizadosInternalTable;

/**
*
* @author Héber
*/

@SuppressWarnings("serial")
public class PedidosCoordenadoresTableModel extends AbstractTableModel{

	private static final int COL_BOTAO = 0;
	private static final int COL_CODIGO_DISCIPLINA = 1;
	private static final int COL_NOME_DISCIPLINA = 2;	
	private static final int COL_CODIGO_CURSO = 3;
	private static final int COL_NOME_CURSO = 4;
	private static final int COL_TOTAL_VAGAS_SOLICITADAS = 5;
	private static final int COL_TOTAL_VAGAS_ATENDIDAS = 6;
	private static final int COL_PERIOTIZADOS_SOLICITADOS = 7;
	private static final int COL_PERIOTIZADOS_ATENDIDOS = 8;
	private static final int COL_NAO_PERIOTIZADOS_SOLICITADOS = 9;
	private static final int COL_NAO_PERIOTIZADOS_ATENDIDOS = 10;	
	private static final int COL_OBSERVACOES = 11;
	private String[] colunas = new String[]{ "", "Código da Disciplina", "Nome da Disciplina", "Código do Curso", "Nome do Curso", "Total de Vagas Solicitadas", "Total de Vagas Atendidas", "Periotizados Solicitados", "Periotizados Atendidos", "Não Periotizados Solicitado", "Não Periotizados Atendidos", "Observações"};
	private ArrayList<Disciplina> linhas;
	private ArrayList<ArrayList<Curso>> curso;
	private ArrayList<PedidosCoordenadores> pedidosCoordenadores; //o indice indica um curso
	private ArrayList<ArrayList<VagasSolicitadas>> vagasSolicitadas; //o primeiro indice indica um disciplina o segundo uma curso
	private ArrayList<ArrayList<VagasAtendidas>> vagasAtendidas; //o primeiro indice indica um disciplina o segundo uma curso
	private DisciplinaDAO disciplinaDAO;
	private CursoDAO cursoDAO;
	private ArrayList<Boolean> botao;	
	
	public PedidosCoordenadoresTableModel() {	
		linhas = new ArrayList<Disciplina>();
		curso = new ArrayList<ArrayList<Curso>>();
		pedidosCoordenadores = new ArrayList<>();
		vagasSolicitadas = new ArrayList<>();
		vagasAtendidas = new ArrayList<>();
		botao = new ArrayList<Boolean>();
		cursoDAO = new CursoDAO();
		disciplinaDAO = new DisciplinaDAO();
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
		Disciplina disciplina = linhas.get(rowIndex);
		ArrayList<String> dadosInternos = new ArrayList<String>();
		ArrayList<Color> coresInternas = new ArrayList<Color>();
		
		switch(columnIndex){
			case COL_BOTAO:
				if(botao.get(rowIndex))
					return "-";
				else
					return "+";
			case COL_CODIGO_DISCIPLINA:
				return disciplina.getCodigo();
			case COL_NOME_DISCIPLINA:
				return disciplina.getNome();
			case COL_CODIGO_CURSO:				
				if(curso.size()>=0 && curso.get(rowIndex).size()>=0 && botao.get(rowIndex)){
					for(Curso c: curso.get(rowIndex)){
						dadosInternos.add(c.getCodigo());
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, false);
				}
				else
					return "";
			case COL_NOME_CURSO:
				if(curso.size()>=0 && curso.get(rowIndex).size()>=0 && botao.get(rowIndex)){
					for(Curso c: curso.get(rowIndex)){
						dadosInternos.add(c.getNome());
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, false);
				}
				else
					return "";
				
			case COL_TOTAL_VAGAS_SOLICITADAS:
				if(curso.size()>0 && curso.get(rowIndex).size()>0 && botao.get(rowIndex)){
					return new TotalVagasInternalTable(vagasSolicitadas.get(rowIndex), coresInternas);
				}
				else
					return "";
				
			case COL_TOTAL_VAGAS_ATENDIDAS:
				if(curso.size()>0 && curso.get(rowIndex).size()>0 && botao.get(rowIndex)){
					return new TotalVagasInternalTable(vagasAtendidas.get(rowIndex), coresInternas);
				}
				else
					return "";
			
			case COL_PERIOTIZADOS_SOLICITADOS:
				if(curso.size()>=0 && curso.get(rowIndex).size()>=0 && botao.get(rowIndex)){
					return new VagasPeriotizadosInternalTable(vagasSolicitadas.get(rowIndex), coresInternas);
				}
				else
					return "";
			
			case COL_PERIOTIZADOS_ATENDIDOS:
				if(curso.size()>=0 && curso.get(rowIndex).size()>=0 && botao.get(rowIndex)){
					return new VagasPeriotizadosInternalTable(vagasAtendidas.get(rowIndex), coresInternas);
				}
				else
					return "";
				
			case COL_NAO_PERIOTIZADOS_SOLICITADOS:
				if(curso.size()>=0 && curso.get(rowIndex).size()>=0 && botao.get(rowIndex)){
					return new VagasDesperiotizadosInternalTable(vagasSolicitadas.get(rowIndex), coresInternas);
				}
				else
					return "";
			
			case COL_NAO_PERIOTIZADOS_ATENDIDOS:
				if(curso.size()>=0 && curso.get(rowIndex).size()>=0 && botao.get(rowIndex)){
					return new VagasDesperiotizadosInternalTable(vagasAtendidas.get(rowIndex), coresInternas);
				}
				else
					return "";
			case COL_OBSERVACOES:
				return "";
				
			default:
				System.out.println("Coluna inválida");
				return null;
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == COL_BOTAO || columnIndex == COL_PERIOTIZADOS_ATENDIDOS || columnIndex == COL_NAO_PERIOTIZADOS_ATENDIDOS)
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public void updateDataRows(){
		linhas.clear(); 
		curso.clear();
		pedidosCoordenadores.clear();
		vagasSolicitadas.clear();		
		vagasAtendidas.clear();
		
		try {
			linhas = (ArrayList<Disciplina>) disciplinaDAO.procuraTodos();			
			ArrayList<Curso> auxCurso = (ArrayList<Curso>) cursoDAO.procuraTodos();
			
			pedidosCoordenadores = new ArrayList<PedidosCoordenadores>();
			curso = new ArrayList<ArrayList<Curso>>();
			vagasSolicitadas = new ArrayList<ArrayList<VagasSolicitadas>>();
			vagasAtendidas = new ArrayList<ArrayList<VagasAtendidas>>();
			
			//inicializa as variaveis
			for(int i=0;i<linhas.size();i++){
				curso.add((ArrayList<Curso>) auxCurso.clone());
				vagasSolicitadas.add(new ArrayList<VagasSolicitadas>());
				vagasAtendidas.add(new ArrayList<VagasAtendidas>());
				for(int j=0;j<auxCurso.size();j++){
					vagasSolicitadas.get(i).add(null);
					vagasAtendidas.get(i).add(null);
				}
			}
			
			for(int i=0;i<auxCurso.size();i++){
				pedidosCoordenadores.add(null);
			}
			
			if(Home.getAno()!=0 && Home.getSemestre()!= 0){	
				//procura o pedido de coordenadores referente a aquele ano para aquele curso (o curso pode ser novo, então tem que passar por todos)
								
				for(int i=0;i<auxCurso.size();i++){
					for(PedidosCoordenadores p: auxCurso.get(i).getPedidosCoordenadores()){
						if(p.getAno() == Home.getAno() && p.getSemestre() == Home.getSemestre()){
							pedidosCoordenadores.set(i, p);
						}
					}
				}
				for(int i=0;i<linhas.size();i++){
					for(int j=0;j<curso.get(i).size();j++){
						for(VagasSolicitadas vs: linhas.get(i).getVagasSolicitadas()){
							if(pedidosCoordenadores.get(j) != null && vs.getPedidosCoordenadores().getIdPedidosCoordenadores() == pedidosCoordenadores.get(j).getIdPedidosCoordenadores()){
								vagasSolicitadas.get(i).set(j, vs);
							}
						}
						
						for(VagasAtendidas va: linhas.get(i).getVagasAtendidas()){
							if(pedidosCoordenadores.get(j) != null && va.getPedidosCoordenadores().getIdPedidosCoordenadores() == pedidosCoordenadores.get(j).getIdPedidosCoordenadores()){
								vagasAtendidas.get(i).set(j, va);
							}
						}
						
						if(vagasSolicitadas.get(i).get(j) == null){
							curso.get(i).remove(j);
							vagasSolicitadas.get(i).remove(j);
							vagasAtendidas.get(i).remove(j);
							j--;
						}						
					}
					
				}
				
				for(int i=0;i<linhas.size();i++){
					if(curso.get(i).isEmpty()){
						linhas.remove(i);
						i--;
					}				
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		curso.sort(new Comparator<Curso>() {
//			@Override
//			public int compare(Curso objeto1, Curso objeto2) {
//				if(objeto1.getNome().equals(objeto2.getNome())){
//					return objeto1.getCodigo().compareTo(objeto2.getCodigo());
//				}
//				return objeto1.getNome().compareTo(objeto2.getNome());
//			}
//		});
//		
//		linhas.sort(new Comparator<Disciplina>() {
//			@Override
//			public int compare(Disciplina objeto1, Disciplina objeto2) {
//				if(objeto1.getNome().equals(objeto2.getNome())){
//					return objeto1.getCodigo().compareTo(objeto2.getCodigo());
//				}
//				return objeto1.getNome().compareTo(objeto2.getNome());
//			}
//		});	
		
		botao.clear(); 
		
		for(int i=0; i<linhas.size(); i++)
			botao.add(true);
	}

	public static int getColBotao() {
		return COL_BOTAO;
	}

	public int getInternalTableHeight(int rowIndex){
		int height = 16;
		if(botao.get(rowIndex) && curso.get(rowIndex).size() > 0){
			height = height * curso.get(rowIndex).size();
		}
		return height;
	}
	
	public void alteraValorBotao(int index){
		if(botao.get(index))
			botao.set(index, false);
		else
			botao.set(index, true);
	}

	public ArrayList<Disciplina> getLinhas() {
		return linhas;
	}
}