package tableModel;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import timetable.Curso;
import timetable.Disciplina;
import timetable.PedidosCoordenadores;
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
public class CadastroPedidosCoordenadoresTableModel extends AbstractTableModel{

	private static final int COL_BOTAO = 0;
	private static final int COL_CODIGO_CURSO = 1;
	private static final int COL_NOME_CURSO = 2;
	private static final int COL_CODIGO_DISCIPLINA = 3;
	private static final int COL_NOME_DISCIPLINA = 4;
	private static final int COL_TOTAL_VAGAS = 5;
	private static final int COL_PERIOTIZADOS = 6;
	private static final int COL_NAO_PERIOTIZADOS = 7;
	private static final int COL_OBSERVACOES = 8;
	private String[] colunas = new String[]{ "", "Código do Curso", "Nome do Curso", "Código da Disciplina", "Nome da Disciplina", "Total de Vagas", "Periotizados", "Não Periotizados", "Observações"};
	private ArrayList<Curso> linhas;
	private ArrayList<PedidosCoordenadores> pedidosCoordenadores; //o indice indica um curso
	private ArrayList<ArrayList<Disciplina>> disciplina; //o primeiro indice indica um curso
	private ArrayList<ArrayList<VagasSolicitadas>> vagasSolicitadas; //o primeiro indice indica um curso o segundo uma disciplina
	private CursoDAO cursoDAO;
	private DisciplinaDAO disciplinaDAO;
	private ArrayList<Boolean> botao;	
	
	public CadastroPedidosCoordenadoresTableModel() {	
		linhas = new ArrayList<Curso>();
		disciplina = new ArrayList<ArrayList<Disciplina> >();
		pedidosCoordenadores = new ArrayList<>();
		vagasSolicitadas = new ArrayList<>();
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
		Curso curso = linhas.get(rowIndex);
		ArrayList<String> dadosInternos = new ArrayList<String>();
		ArrayList<Color> coresInternas = new ArrayList<Color>();
		
		switch(columnIndex){
			case COL_BOTAO:
				if(botao.get(rowIndex))
					return "-";
				else
					return "+";
			case COL_CODIGO_CURSO:
				return curso.getCodigo();
			case COL_NOME_CURSO:
				return curso.getNome();
			case COL_CODIGO_DISCIPLINA:				
				if(disciplina.size()>=0 && disciplina.get(rowIndex).size()>=0 && botao.get(rowIndex)){
					for(Disciplina t: disciplina.get(rowIndex)){
						dadosInternos.add(t.getCodigo());
						coresInternas.add(Disciplina.getOrSetCoresPerfis(t.getPerfil()));
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, true);
				}
				else
					return "";
			case COL_NOME_DISCIPLINA:
				if(disciplina.size()>=0 && disciplina.get(rowIndex).size()>=0 && botao.get(rowIndex)){
					for(Disciplina t: disciplina.get(rowIndex)){
						dadosInternos.add(t.getNome());
						coresInternas.add(Disciplina.getOrSetCoresPerfis(t.getPerfil()));
					}
					return new DefaultInternalTable(dadosInternos, coresInternas, true);
				}
				else
					return "";
			case COL_TOTAL_VAGAS:
				if(disciplina.size()>=0 && disciplina.get(rowIndex).size()>=0 && botao.get(rowIndex)){
					return new TotalVagasInternalTable(vagasSolicitadas.get(rowIndex), coresInternas);
				}
				else
					return "";
			case COL_PERIOTIZADOS:
				if(disciplina.size()>=0 && disciplina.get(rowIndex).size()>=0 && botao.get(rowIndex)){
					return new VagasPeriotizadosInternalTable(vagasSolicitadas.get(rowIndex), coresInternas);
				}
				else
					return "";
			case COL_NAO_PERIOTIZADOS:
				if(disciplina.size()>=0 && disciplina.get(rowIndex).size()>=0 && botao.get(rowIndex)){
					return new VagasDesperiotizadosInternalTable(vagasSolicitadas.get(rowIndex), coresInternas);
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
		if(columnIndex == COL_BOTAO || columnIndex == COL_PERIOTIZADOS || columnIndex == COL_NAO_PERIOTIZADOS)
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public void updateDataRows(){
		linhas.clear(); 
		disciplina.clear();
		pedidosCoordenadores.clear();
		vagasSolicitadas.clear();
		
		try {
			linhas = (ArrayList<Curso>) cursoDAO.procuraTodos();
			
			ArrayList<Disciplina> auxDisc = (ArrayList<Disciplina>) disciplinaDAO.procuraTodos();
			
			pedidosCoordenadores = new ArrayList<PedidosCoordenadores>();
			disciplina = new ArrayList<ArrayList<Disciplina>>();
			vagasSolicitadas = new ArrayList<ArrayList<VagasSolicitadas>>();
			
			if(Home.getAno()!=0 && Home.getSemestre()!= 0){	
				//procura o pedido de coordenadores referente a aquele ano para aquele curso (o curso pode ser novo, então tem que passar por todos)
								
				for(int i=0;i<linhas.size();i++){
					pedidosCoordenadores.add(null);
					disciplina.add(new ArrayList<Disciplina>());
					vagasSolicitadas.add(new ArrayList<VagasSolicitadas>());
					for(PedidosCoordenadores p: linhas.get(i).getPedidosCoordenadores()){
						if(p.getAno() == Home.getAno() && p.getSemestre() == Home.getSemestre()){
							pedidosCoordenadores.set(i, p);
						}
					}
				}
				for(int i=0;i<disciplina.size();i++){					
					disciplina.set(i, (ArrayList<Disciplina>) auxDisc.clone());
					
					for(int j = 0;j<disciplina.get(i).size();j++){
						vagasSolicitadas.get(i).add(null);
						for(VagasSolicitadas vs: disciplina.get(i).get(j).getVagasSolicitadas()){
							if(pedidosCoordenadores.get(i) != null && vs.getPedidosCoordenadores().getIdPedidosCoordenadores() == pedidosCoordenadores.get(i).getIdPedidosCoordenadores()){
								vagasSolicitadas.get(i).set(j, vs);
							}
						}
						
						if(vagasSolicitadas.get(i).get(j)==null){
							disciplina.get(i).remove(j);
							vagasSolicitadas.get(i).remove(j);
							j--;
						}
					}
				}
				
				for(int i=0;i<linhas.size();i++){
					if(disciplina.get(i).size() == 0){
						linhas.remove(i);
						i--;
					}				
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		linhas.sort(new Comparator<Curso>() {
//			@Override
//			public int compare(Curso objeto1, Curso objeto2) {
//				if(objeto1.getNome().equals(objeto2.getNome())){
//					return objeto1.getCodigo().compareTo(objeto2.getCodigo());
//				}
//				return objeto1.getNome().compareTo(objeto2.getNome());
//			}
//		});
		
//		disciplina.sort(new Comparator<Disciplina>() {
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
		if(botao.get(rowIndex) && disciplina.size() > 0 && disciplina.get(rowIndex).size() > 0){
			height = height * disciplina.get(rowIndex).size();
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
