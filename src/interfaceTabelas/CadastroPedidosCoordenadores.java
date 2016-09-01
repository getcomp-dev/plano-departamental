package interfaceTabelas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import estruturasAuxiliaresTabelas.BotaoTabela;
import estruturasAuxiliaresTabelas.CellEditor;
import estruturasAuxiliaresTabelas.CellRenderer;
import tableModel.CadastroPedidosCoordenadoresTableModel;

/**
 * 
 * @author H�ber
 */
@SuppressWarnings("serial")
public class CadastroPedidosCoordenadores extends InterfacesTabela {

	private BotaoTabela botaoExpandir;
	private CellRenderer renderer;
	private CellEditor editor;
	
	public CadastroPedidosCoordenadores(){		
		super(new CadastroPedidosCoordenadoresTableModel());
		
		botaoExpandir = new BotaoTabela(table, CadastroPedidosCoordenadoresTableModel.getColBotao());
		renderer = new CellRenderer();
		editor = new CellEditor();
		
		for(int i=1;i<table.getColumnCount(); i++){
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			table.getColumnModel().getColumn(i).setCellEditor(editor);
		}
		
		botaoExpandir.getEditButton().addActionListener(new ActionListener(){	
			@Override
			public void actionPerformed(ActionEvent e) {
				botaoExpandir.editingStopped();
				((CadastroPedidosCoordenadoresTableModel)tableModel).alteraValorBotao(table.getSelectedRow());
				updateRow(table.getSelectedRow());
				
			}
		});
	}

	@Override
	public void updateTable() {
		((CadastroPedidosCoordenadoresTableModel)tableModel).updateDataRows();
		((CadastroPedidosCoordenadoresTableModel)tableModel).fireTableDataChanged();
		for(int i= 0; i<table.getRowCount(); i++){
			int height = ((CadastroPedidosCoordenadoresTableModel)tableModel).getInternalTableHeight(i);
			table.setRowHeight(i, height);
		}		
	}
	
	public void updateRow(int rowIndex){
		((CadastroPedidosCoordenadoresTableModel)tableModel).fireTableRowsUpdated(rowIndex, rowIndex);
		int height = ((CadastroPedidosCoordenadoresTableModel)tableModel).getInternalTableHeight(rowIndex);
		table.setRowHeight(rowIndex, height);
	}
}