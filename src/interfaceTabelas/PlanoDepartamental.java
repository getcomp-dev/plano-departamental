package interfaceTabelas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.DefaultCellEditor;

import org.hibernate.HibernateException;

import estruturasAuxiliaresTabelas.CellRenderer;
import hibernate.DisciplinaDAO;
import hibernate.DocenteDAO;
import hibernate.TurmaDAO;
import tableModel.PlanoDepartamentalTableModel;
import timetable.Disciplina;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class PlanoDepartamental extends InterfacesTabela {
	
	private CellRenderer renderer;
	private ArrayList<Color> coresLinhas;
	
	public PlanoDepartamental(){		
		super(new PlanoDepartamentalTableModel(), "Gerar Grade");
		
		table.getColumnModel().getColumn(PlanoDepartamentalTableModel.getColDocente()).setCellEditor(new DefaultCellEditor(((PlanoDepartamentalTableModel) tableModel).getComboBox()));
		
		coresLinhas = new ArrayList<Color>();
		renderer = new CellRenderer();		
		
		for(int i=0;i<table.getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FileWriter arq = null;
				try {
					arq = new FileWriter("dados.tbd");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				BufferedWriter escrita = new BufferedWriter(arq);
				
				try {
					ArrayList<timetable.Docente> docente = null;
					try {
						DocenteDAO docenteDAO = new DocenteDAO();
						docente = (ArrayList<timetable.Docente>) docenteDAO.procuraTodos();
					} catch (HibernateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					ArrayList<timetable.Disciplina> disciplina = null;
					try {
						DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
						disciplina = (ArrayList<timetable.Disciplina>) disciplinaDAO.procuraTodos();
					} catch (HibernateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					ArrayList<timetable.Turma> turma = null;
					try {
						TurmaDAO turmaDAO = new TurmaDAO();
						turma = (ArrayList<timetable.Turma>) turmaDAO.procuraTodos();
					} catch (HibernateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					escrita.write(docente.size()  + "\n");
					escrita.write(disciplina.size()  + "\n");
					escrita.write(turma.size()  + "\n");
					escrita.write("Docente:");
					escrita.newLine();
					escrita.write("id, código, nome completo, creditação esperada");
					escrita.newLine();
					
					for(Iterator<?> it = docente.iterator(); it.hasNext();){
						timetable.Docente doc = ((timetable.Docente)it.next());
						escrita.write(doc.getIdDocente() + " " + doc.getCodigo() + " " + doc.getNome() + " " + doc.getCreditacaoEsperada());
						escrita.newLine();
					}
					
					escrita.newLine();
					
					escrita.write("Disciplina:");
					escrita.newLine();
					escrita.write("id, código, crédito, perfil");
					escrita.newLine();					
					
					for(Iterator<?> it = disciplina.iterator(); it.hasNext();){
						timetable.Disciplina disc = ((timetable.Disciplina)it.next());
						escrita.write(disc.getIdDisciplina() + " " + disc.getCodigo() + " " + disc.getCreditos() + " " + disc.getPerfil());
						escrita.newLine();
					}					

					escrita.newLine();
					
					escrita.write("Turma:");
					escrita.newLine();
					escrita.write("id, código, turno, id disciplina");
					escrita.newLine();
					
					
					
					turma.sort(new Comparator<timetable.Turma>() {
						public int compare(timetable.Turma o1, timetable.Turma o2) {
							if(o1.getDisciplina().getPerfil().equals(o2.getDisciplina().getPerfil())){
								if(o1.getDisciplina().getNome().equals(o2.getDisciplina().getNome())){
									return o1.getCodigo().compareTo(o2.getCodigo());
								}
								return o1.getDisciplina().getNome().compareTo(o2.getDisciplina().getNome());						
							}						
							return o1.getDisciplina().getPerfil().compareTo(o2.getDisciplina().getPerfil());
						}
					});
					
					for(Iterator<?> it = turma.iterator(); it.hasNext();){
						timetable.Turma tur = ((timetable.Turma)it.next());
						escrita.write(tur.getIdTurma() + " " + tur.getCodigo() + " " + tur.getTurno() + " " + tur.getDisciplina().getIdDisciplina());
						escrita.newLine();
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
				try {
					escrita.close();
					arq.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				

			}
		});
	}
	
	public void updateTable() {		
		((PlanoDepartamentalTableModel)tableModel).updateDataRows();
		table.getColumnModel().getColumn(PlanoDepartamentalTableModel.getColDocente()).setCellEditor(new DefaultCellEditor(((PlanoDepartamentalTableModel) tableModel).getComboBox()));
		preencheCoresLinhas();
		((PlanoDepartamentalTableModel)tableModel).fireTableDataChanged();
	}
	
	private void preencheCoresLinhas(){
		int numeroLinhas = table.getRowCount();
		coresLinhas.clear();
		Disciplina.resetCoresPerfis();
		for(int i=0; i<numeroLinhas;i++){
			Color cor = Disciplina.getOrSetCoresPerfis(((PlanoDepartamentalTableModel)tableModel).getLinhas().get(i).getDisciplina().getPerfil());
			coresLinhas.add(cor);
		}
		renderer.setCoresLinhas(coresLinhas);
	}
}