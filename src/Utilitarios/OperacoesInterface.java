package Utilitarios;

//import java.awt.Component;
//import java.awt.Dimension;
//
//import javax.swing.JTable;
//import javax.swing.table.DefaultTableColumnModel;
//import javax.swing.table.TableCellRenderer;
//import javax.swing.table.TableColumn;

public abstract class OperacoesInterface {
//
//	//dimensiona as colunas da tabela de acordo com o maior texto, e o tamanho total da tabela
//	public static void dimensionaTabela(JTable tabela) { 
//        packColumns(tabela, 2);  
//        packRows(tabela, 0);  
//        tabela.setRowHeight(20);  
//        tabela.setIntercellSpacing(new Dimension(2, 2));
//	}  
//  
//	private static int getPreferredRowHeight(JTable table, int rowIndex, int margin) {  
//        // pega o tamanho padrão de todas as linhas
//        int height = table.getRowHeight();  
//  
//        // determina a maior celula na linha
//        for (int c = 0; c < table.getColumnCount(); c++) {  
//            TableCellRenderer renderer = table.getCellRenderer(rowIndex, c);  
//            Component comp = table.prepareRenderer(renderer, rowIndex, c);  
//            int h = comp.getPreferredSize().height + 2 * margin;  
//            height = Math.max(height, h);  
//        }  
//        return height;  
//    }  
//  
//    // o tamanho da maior celular é setado de acordo com a maior
//    private static void packRows(JTable table, int margin) {  
//        packRows(table, 0, table.getRowCount(), margin);  
//    }  
//  
//    // seta o tamanho preferencial para todas as linhas
//    private static void packRows(JTable table, int start, int end, int margin) {  
//        for (int r = 0; r < table.getRowCount(); r++) {  
//            // pega o tamanho preferencial
//            int h = getPreferredRowHeight(table, r, margin);  
//  
//            // seta o comprimento com o tamanho preferencial
//            if (table.getRowHeight(r) != h) {  
//                table.setRowHeight(r, h);  
//            }  
//        }  
//    }  
//  
//    private static void packColumns(JTable table, int margin) {  
//        for (int c = 0; c < table.getColumnCount(); c++) {  
//            packColumn(table, c, 2);  
//        }  
//    }  
//  
//    private static void packColumn(JTable table, int vColIndex, int margin) {  
//    	InterfaceTableModel model = (InterfaceTableModel) table.getModel();  
//        DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();  
//        TableColumn col = colModel.getColumn(vColIndex);  
//        int width = 0;  
//  
//        // pega o comprimento do texto de cabeçalho
//        TableCellRenderer renderer = col.getHeaderRenderer();  
//        if (renderer == null) {  
//            renderer = table.getTableHeader().getDefaultRenderer();  
//        }  
//        Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, -1, 0);  
//        width = comp.getPreferredSize().width;  
//  
//        // pega o maior tamanho da coluna
//        for (int r = 0; r < table.getRowCount(); r++) {  
//            renderer = table.getCellRenderer(r, vColIndex);  
//            comp = renderer.getTableCellRendererComponent(table, table  
//                    .getValueAt(r, vColIndex), false, false, r, vColIndex);  
//            width = Math.max(width, comp.getPreferredSize().width);  
//        }  
//  
//        // adiciona margem
//        width += 2 * margin;  
//  
//        // seta o tamanho
//        col.setPreferredWidth(width);  
//    } 
//
}
