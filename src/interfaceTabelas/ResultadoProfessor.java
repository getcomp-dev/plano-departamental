package interfaceTabelas;

import hibernate.TurmaDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import org.hibernate.HibernateException;

import estruturasAuxiliaresTabelas.BotaoTabela;
import estruturasAuxiliaresTabelas.CellRenderer;
import tableModel.ResultadosProfessorTableModel;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class ResultadoProfessor extends InterfacesTabela {

	private BotaoTabela botaoExpandir;
	private CellRenderer renderer;
	
	public ResultadoProfessor(){		
		super(new ResultadosProfessorTableModel(), "Gerar Relatório");
		
		botaoExpandir = new BotaoTabela(table, ResultadosProfessorTableModel.getColBotao());
		renderer = new CellRenderer();
		
		for(int i=1;i<table.getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		
		botaoExpandir.getEditButton().addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				botaoExpandir.editingStopped();
				((ResultadosProfessorTableModel)tableModel).alteraValorBotao(table.getSelectedRow());
				updateRow(table.getSelectedRow());				
			}
		});
		
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FileWriter arq = null;
				try {
					arq = new FileWriter("relatório.csv");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				BufferedWriter escrita = new BufferedWriter(arq);
				
				try {
					escrita.write("Semestre,Código da Disciplina,Disciplina,Turma,Horário,Créditos,Docente,Vagas");
					escrita.newLine();
					
					ArrayList<timetable.Turma> turma = null;
					try {
						TurmaDAO turmaDAO = new TurmaDAO();
						turma = (ArrayList<timetable.Turma>) turmaDAO .procuraTodos();
					} catch (HibernateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
						String docente = "";
						if(tur.getDocente().size() != 0){
							for(Iterator<?> it1 = tur.getDocente().iterator(); it1.hasNext();){
								timetable.Docente doc = (timetable.Docente) it1.next();
								docente = doc.getNomeCompleto();
								docente = docente + "; ";
							}
						}
						escrita.write("Semestre,"+ tur.getDisciplina().getCodigo()+ "," + 
						tur.getDisciplina().getNome() + "," + tur.getCodigo() + "," +
						"Horário," + tur.getDisciplina().getCreditos() + "," + docente + "," + "Vagas");
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

	@Override
	public void updateTable() {
		int height = 0;
		((ResultadosProfessorTableModel)tableModel).updateDataRows();
		((ResultadosProfessorTableModel)tableModel).fireTableDataChanged();
		for(int i= 0; i<table.getRowCount(); i++){
			height = ((ResultadosProfessorTableModel)tableModel).getInternalTableHeight(i);
			if(height > 0)
				table.setRowHeight(i, height);
		}
		
	}
	
	public void updateRow(int rowIndex){
		((ResultadosProfessorTableModel)tableModel).fireTableRowsUpdated(rowIndex, rowIndex);
		int height = ((ResultadosProfessorTableModel)tableModel).getInternalTableHeight(rowIndex);
		table.setRowHeight(rowIndex, height);
	}
}