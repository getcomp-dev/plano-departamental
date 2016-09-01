package interfaceTabelas;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import estruturasAuxiliaresTabelas.TableMouseListener;
import hibernate.CursoDAO;
import interfaces.LayoutConstraints;
import tableModel.CursoTableModel;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class Curso extends InterfacesTabela implements ActionListener{
	
	private JButton botaoExibirGrades;
	private JPopupMenu popupMenu;
    private JMenuItem menuRemoverItem;
    
	public Curso() {
		super(new CursoTableModel(), "Inserir Novo Curso");
		
		botaoExibirGrades = new JButton("Exibir Grades");
		painelBotao.add(botaoExibirGrades);
		
		popupMenu = new JPopupMenu();
        menuRemoverItem = new JMenuItem("Remover Curso");
        
        menuRemoverItem.addActionListener(this);
        
        popupMenu.add(menuRemoverItem);
        
        table.setComponentPopupMenu(popupMenu);
        
        table.addMouseListener(new TableMouseListener(table));
		
        // seta a posição do botão exibir grades
 		LayoutConstraints.setConstraints(constraints, 1, 1, 1, 1, 1, 1);
 		constraints.insets = new Insets(0, 0, 0, 190);
 		constraints.fill = GridBagConstraints.RELATIVE;
 		constraints.anchor = GridBagConstraints.EAST;
 		btnGridBag.setConstraints(botaoExibirGrades, constraints);
 		
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timetable.Curso curso = new timetable.Curso();
				((CursoTableModel)tableModel).addCurso(curso);
			}
		});
		
		botaoExibirGrades.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CursoDAO cursoDAO = new CursoDAO();
				List<timetable.Curso> lista = null;
				try {
					lista = cursoDAO.procuraTodos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				String[] curso = new String[lista.size()];
				
				for(int i=0;i<lista.size();i++){
					curso[i] = lista.get(i).getNome();
				}
				
				JFrame frameAno = new JFrame("Exibir Grade");
				Object resposta = JOptionPane.showInputDialog(frameAno, 
			        "Selecione o Curso",
			        "Exibir",
			        JOptionPane.QUESTION_MESSAGE,
			        null, 
			        curso, 
			        curso[0]);
				
			    if(resposta == null)
					return;
			    
			    JFrame frame = new JFrame("Grade do Curso " + resposta.toString());
			    Grades grade = null;
			    for(timetable.Curso c: lista){
			    	if(c.getNome().equals(resposta.toString())){
			    		grade = new Grades(c);
			    	}
			    }
			    
			    if(grade != null){
			    	frame.setLayout(new BorderLayout());
			    	frame.add(grade.getPainel(), BorderLayout.CENTER);
			    
			    	frame.pack();
			    	frame.setExtendedState( frame.getExtendedState()|JFrame.MAXIMIZED_BOTH ); //maximiza a janela
			    	frame.setVisible(true);
			    }
			    
			}
		});
	}
	
	public void updateTable() {
		((CursoTableModel)tableModel).updateDataRows();
		((CursoTableModel)tableModel).fireTableDataChanged();
	}
	
	public void actionPerformed(ActionEvent event) {
        JMenuItem menu = (JMenuItem) event.getSource();
        if (menu == menuRemoverItem) {
            ((CursoTableModel)tableModel).removeCurso(table.getSelectedRow());  
        }
    }
}
