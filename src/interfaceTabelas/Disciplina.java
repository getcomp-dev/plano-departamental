package interfaceTabelas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import estruturasAuxiliaresTabelas.TableMouseListener;
import tableModel.DisciplinaTableModel;

/**
*
* @author Héber
*/
@SuppressWarnings("serial")
public class Disciplina extends InterfacesTabela implements ActionListener {
	
	private JPopupMenu popupMenu;
    private JMenuItem menuRemoverItem;
    
	public Disciplina() {
		super(new DisciplinaTableModel(), "Inserir Disciplina");
		
		popupMenu = new JPopupMenu();
        menuRemoverItem = new JMenuItem("Remover Disciplina");
        
        menuRemoverItem.addActionListener(this);
        
        popupMenu.add(menuRemoverItem);
        
        table.setComponentPopupMenu(popupMenu);
        
        table.addMouseListener(new TableMouseListener(table));
		
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timetable.Disciplina disciplina = new timetable.Disciplina();
				((DisciplinaTableModel)tableModel).addDisciplina(disciplina);
			}
		});
	}
	
	public void actionPerformed(ActionEvent event) {
        JMenuItem menu = (JMenuItem) event.getSource();
        if (menu == menuRemoverItem) {
            ((DisciplinaTableModel)tableModel).removeDisciplina(table.getSelectedRow());  
        }
    }
	
	@Override
	public void updateTable() {
		((DisciplinaTableModel)tableModel).updateDataRows();
		((DisciplinaTableModel)tableModel).fireTableDataChanged();		
	}
	
	
}
