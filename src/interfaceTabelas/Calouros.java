package interfaceTabelas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import estruturasAuxiliaresTabelas.TableMouseListener;
import tableModel.CalourosTableModel;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class Calouros extends InterfacesTabela implements ActionListener{

	private JPopupMenu popupMenu;
    private JMenuItem menuRemoverItem;
    
	public Calouros(){		
		super(new CalourosTableModel(), "Inserir Calouro");		
		
		popupMenu = new JPopupMenu();
        menuRemoverItem = new JMenuItem("Remover Calouro");
        
        menuRemoverItem.addActionListener(this);
        
        popupMenu.add(menuRemoverItem);
        
        table.setComponentPopupMenu(popupMenu);
        
        table.addMouseListener(new TableMouseListener(table));
        
		// gera os acontecimentos ao se clicar no botão inserir
		botaoPadrao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timetable.Calouros calouro = new timetable.Calouros();
				((CalourosTableModel)tableModel).addCalouro(calouro);
			}
		});
	}
	
	public void actionPerformed(ActionEvent event) {
        JMenuItem menu = (JMenuItem) event.getSource();
        if (menu == menuRemoverItem) {
            ((CalourosTableModel)tableModel).removeCalouro(table.getSelectedRow());  
        }
    }
	
	public void updateTable() {
		((CalourosTableModel)tableModel).updateDataRows();
		((CalourosTableModel)tableModel).fireTableDataChanged();
	}
}
