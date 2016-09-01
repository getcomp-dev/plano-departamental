package estruturasAuxiliaresTabelas;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
/*
 * responsável por detectar a posição do click do mouse
 */
public class TableMouseListener extends MouseAdapter {
    
   private JTable table;
    
   public TableMouseListener(JTable table) {
       this.table = table;
   }
   /*
    * detecta a linha que houve o click do mouse
    * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
    */
   @Override
   public void mousePressed(MouseEvent event) {
       Point point = event.getPoint();
       int currentRow = table.rowAtPoint(point);
       if(currentRow >0 && currentRow <= table.getRowCount())
    	   table.setRowSelectionInterval(currentRow, currentRow);
   }
}
