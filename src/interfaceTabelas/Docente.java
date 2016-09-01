package interfaceTabelas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import estruturasAuxiliaresTabelas.TableMouseListener;
import tableModel.DocenteTableModel;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class Docente extends InterfacesTabela implements ActionListener{

	private JPopupMenu popupMenu;
    private JMenuItem menuRemoverItem;
    
	public Docente(){		
		super(new DocenteTableModel(), "Inserir Docente");
		
		popupMenu = new JPopupMenu();
        menuRemoverItem = new JMenuItem("Remover Docente");
        
        menuRemoverItem.addActionListener(this);
        
        popupMenu.add(menuRemoverItem);
        
        table.setComponentPopupMenu(popupMenu);
        
        table.addMouseListener(new TableMouseListener(table));
		
		// gera os acontecimentos ao se clicar no botão inserir
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timetable.Docente docente = new timetable.Docente();
				((DocenteTableModel)tableModel).addDocente(docente);
			}
		});
	}
	
	public void actionPerformed(ActionEvent event) {
        JMenuItem menu = (JMenuItem) event.getSource();
        if (menu == menuRemoverItem) {
            ((DocenteTableModel)tableModel).removeDocente(table.getSelectedRow());  
        }
    }
	
	public void updateTable() {
		((DocenteTableModel)tableModel).updateDataRows();
		((DocenteTableModel)tableModel).fireTableDataChanged();		
	}
}