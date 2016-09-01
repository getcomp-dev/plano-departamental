package interfaceTabelas;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import estruturasAuxiliaresTabelas.BotaoTabela;
import estruturasAuxiliaresTabelas.CellEditor;
import estruturasAuxiliaresTabelas.CellRenderer;
import estruturasAuxiliaresTabelas.TableMouseListener;
import hibernate.DisciplinaDAO;
import hibernate.PeriodoDAO;
import interfaces.LayoutConstraints;
import tableModel.GradesTableModel;
import tableModel.PedidosCoordenadoresTableModel;
import timetable.Curso;
import timetable.Disciplina;
import timetable.Periodo;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class Grades extends InterfacesTabela implements ActionListener{
	
	private BotaoTabela botaoExpandir;
	private JButton inserirDisciplina;
	private JButton removerDisciplina;
	private CellRenderer renderer;
	private CellEditor editor;
	private JPopupMenu popupMenu;
    private JMenuItem menuRemoverItem;
    
	public Grades(Curso curso){
		super(new GradesTableModel(curso), "Inserir Novo Periodo");
		
		popupMenu = new JPopupMenu();
        menuRemoverItem = new JMenuItem("Remover Periodo");   
        menuRemoverItem.addActionListener(this);        
        popupMenu.add(menuRemoverItem);        
        table.setComponentPopupMenu(popupMenu);        
        table.addMouseListener(new TableMouseListener(table));
                
        renderer = new CellRenderer();
		editor = new CellEditor();
        
		inserirDisciplina = new JButton("Inserir Disciplina");
		painelBotao.add(inserirDisciplina);
		removerDisciplina = new JButton("Remover Disciplina");
		painelBotao.add(removerDisciplina);
        botaoExpandir = new BotaoTabela(table, PedidosCoordenadoresTableModel.getColBotao());
		
     // seta a posição do botão inserirDisciplina
  		LayoutConstraints.setConstraints(constraints, 1, 1, 1, 1, 1, 1);
  		constraints.insets = new Insets(0, 0, 0, 335);
  		constraints.fill = GridBagConstraints.RELATIVE;
  		constraints.anchor = GridBagConstraints.EAST;
  		btnGridBag.setConstraints(inserirDisciplina, constraints);
  		
  	// seta a posição do botão inserirDisciplina
  		LayoutConstraints.setConstraints(constraints, 1, 1, 1, 1, 1, 1);
  		constraints.insets = new Insets(0, 0, 0, 190);
  		constraints.fill = GridBagConstraints.RELATIVE;
  		constraints.anchor = GridBagConstraints.EAST;
  		btnGridBag.setConstraints(removerDisciplina, constraints);
    
		for(int i=2;i<table.getColumnCount(); i++){
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			table.getColumnModel().getColumn(i).setCellEditor(editor);
		}
		
		botaoExpandir.getEditButton().addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				botaoExpandir.editingStopped();
				((GradesTableModel)tableModel).alteraValorBotao(table.getSelectedRow());
				updateRow(table.getSelectedRow());
				
			}
		});
		
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timetable.Periodo periodo = new timetable.Periodo();
				((GradesTableModel)tableModel).addPeriodo(periodo);;
			}
		});
		
		
		inserirDisciplina.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
				PeriodoDAO periodoDAO = new PeriodoDAO();
				List<timetable.Disciplina> disciplina = null;
				List<Periodo> periodo = null;
				try {
					disciplina = disciplinaDAO.procuraTodos();
					periodo = ((GradesTableModel)tableModel).getLinhas();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(disciplina == null){
					JOptionPane.showMessageDialog(new JFrame(), "Não existe Disciplinas cadastradas no banco", "Erro",  JOptionPane.ERROR_MESSAGE);
				}
				else if(periodo == null){
					JOptionPane.showMessageDialog(new JFrame(), "Não existe Periodos cadastradas no banco para este curso", "Erro",  JOptionPane.ERROR_MESSAGE);
				}
				else{
					
					Integer[] periodo_ = new Integer[periodo.size()];
					
					for(int i=0;i<periodo.size();i++){
						periodo_[i] = periodo.get(i).getNumeroPeriodo();
					}
					
					JFrame frameAno = new JFrame("Inserir Disciplina");
					Object resposta = JOptionPane.showInputDialog(frameAno, 
				        "Escolha o Periodo",
				        "Inserir Disciplina",
				        JOptionPane.QUESTION_MESSAGE, 
				        null, 
				        periodo_, 
				        periodo_[0]);
					
				    if(resposta == null)
						return;
					int respostaPeriodo = Integer.parseInt(resposta.toString());
					
					disciplina.sort(new Comparator<Disciplina>() {
						@Override
						public int compare(Disciplina objeto1, Disciplina objeto2) {
							return objeto1.getNome().compareTo(objeto2.getNome());
						}
					});
					String[] disciplina_ = new String[disciplina.size()];
				    for(int i=0; i<disciplina.size();i++){
				    	disciplina_[i] = disciplina.get(i).getNome();
			    	}
				    
				    resposta = JOptionPane.showInputDialog(frameAno, 
					        "Escolha o semestre",
					        "Importar Turma",
					        JOptionPane.QUESTION_MESSAGE, 
					        null, 
					        disciplina_, 
					        disciplina_[0]);
				    
				    String respostaDisciplina = "";
				    if(resposta == null)
				    	return;
				    
				    respostaDisciplina = resposta.toString();
				    
				    try {
				    	for(Periodo p: periodo){
				    		if(p.getNumeroPeriodo() == respostaPeriodo){
				    			if(!periodoDAO.procuraPorNomeDisc(p.getIdPeriodo(), respostaDisciplina)){
				    				for(timetable.Disciplina d: disciplina){
				    					if(d.getNome().equals(respostaDisciplina)){
				    						p.getDisciplina().add(d);
				    						break;
				    					}
				    				}
				    			}

							    periodoDAO.salvaOuEdita(p);
							    break;
				    		}
				    		
				    	}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    updateTable();
				}
			}
		});
		
		removerDisciplina.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
				PeriodoDAO periodoDAO = new PeriodoDAO();
				List<Periodo> periodo = null;
				try {
					periodo = ((GradesTableModel)tableModel).getLinhas();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				if(periodo == null){
					JOptionPane.showMessageDialog(new JFrame(), "Não existe Periodos cadastradas no banco para este curso", "Erro",  JOptionPane.ERROR_MESSAGE);
				}
				else{
					
					Integer[] periodo_ = new Integer[periodo.size()];
					
					for(int i=0;i<periodo.size();i++){
						periodo_[i] = periodo.get(i).getNumeroPeriodo();
					}
					
					JFrame frameAno = new JFrame("Inserir Disciplina");
					Object resposta = JOptionPane.showInputDialog(frameAno, 
				        "Escolha o Periodo",
				        "Inserir Disciplina",
				        JOptionPane.QUESTION_MESSAGE, 
				        null, 
				        periodo_, 
				        periodo_[0]);
					
				    if(resposta == null)
						return;
					int respostaPeriodo = Integer.parseInt(resposta.toString());
					respostaPeriodo --;
					
					if(periodo.get(respostaPeriodo).getDisciplina().size() == 0){
						JOptionPane.showMessageDialog(new JFrame(), "Não existe Disciplinas cadastradas no banco", "Erro",  JOptionPane.ERROR_MESSAGE);
					}else{
						String[] disciplina_ = new String[periodo.get(respostaPeriodo).getDisciplina().size()];
					    for(int i=0; i<periodo.get(respostaPeriodo).getDisciplina().size();i++){
					    	disciplina_[i] = periodo.get(respostaPeriodo).getDisciplina().get(i).getNome();
				    	}
					    
					    resposta = JOptionPane.showInputDialog(frameAno, 
						        "Escolha o semestre",
						        "Importar Turma",
						        JOptionPane.QUESTION_MESSAGE, 
						        null, 
						        disciplina_, 
						        disciplina_[0]);
					    
					    String respostaDisciplina = "";
					    if(resposta == null)
					    	return;
					    
					    respostaDisciplina = resposta.toString();
					    
					    try {
					    	for(Disciplina d: periodo.get(respostaPeriodo).getDisciplina()){
					    		if(d.getNome().equals(respostaDisciplina)){
					    			periodo.get(respostaPeriodo).getDisciplina().remove(d);
					    			break;
					    		}
					    	}
						    periodoDAO.salvaOuEdita(periodo.get(respostaPeriodo));
			    		} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					    
					    updateTable();
						
					}
				}
			}
		});
		
		updateTable();
	}
	
	public void updateTable() {
		((GradesTableModel)tableModel).updateDataRows();
		
		renderer = new CellRenderer();
		for(int i=1;i<table.getColumnCount(); i++){
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		
		((GradesTableModel)tableModel).fireTableDataChanged();
		
		for(int i= 0; i<table.getRowCount(); i++){
			int height = ((GradesTableModel)tableModel).getInternalTableHeight(i);
			table.setRowHeight(i, height);
		}
		
		table.getColumnModel().getColumn(GradesTableModel.getColBotao()).setMaxWidth(400);
	}
	
	public void actionPerformed(ActionEvent event) {
        JMenuItem menu = (JMenuItem) event.getSource();
        if (menu == menuRemoverItem) {
            ((GradesTableModel)tableModel).removeCurso(table.getSelectedRow());
        }
    }
	
	public void updateRow(int rowIndex){
		((GradesTableModel)tableModel).fireTableRowsUpdated(rowIndex, rowIndex);
		int height = ((GradesTableModel)tableModel).getInternalTableHeight(rowIndex);
		table.setRowHeight(rowIndex, height);
	}
}
