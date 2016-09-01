package interfaceTabelas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import estruturasAuxiliaresTabelas.BotaoTabela;
import estruturasAuxiliaresTabelas.CellEditor;
import estruturasAuxiliaresTabelas.CellRenderer;
import tableModel.PedidosCoordenadoresTableModel;
import timetable.Disciplina;

/**
 *
 * @author Héber
 */
@SuppressWarnings("serial")
public class PedidosCoordenadores extends InterfacesTabela {

	private BotaoTabela botaoExpandir;
	private CellRenderer renderer;
	private CellEditor editor;
	private ArrayList<Color> coresLinhas;
	
	public PedidosCoordenadores(){		
		super(new PedidosCoordenadoresTableModel());
		
		coresLinhas = new ArrayList<Color>();
		
		renderer = new CellRenderer();
		editor = new CellEditor();
		
		botaoExpandir = new BotaoTabela(table, PedidosCoordenadoresTableModel.getColBotao());		
		
		for(int i=1;i<table.getColumnCount(); i++){
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			table.getColumnModel().getColumn(i).setCellEditor(editor);
		}
		
		botaoExpandir.getEditButton().addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				botaoExpandir.editingStopped();
				((PedidosCoordenadoresTableModel)tableModel).alteraValorBotao(table.getSelectedRow());
				updateRow(table.getSelectedRow());
				
			}
		});
	}

	@Override
	public void updateTable() {		
		((PedidosCoordenadoresTableModel)tableModel).updateDataRows();	
		preencheCoresLinhas();	
		renderer = new CellRenderer(coresLinhas);
		for(int i=1;i<table.getColumnCount(); i++){
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		((PedidosCoordenadoresTableModel)tableModel).fireTableDataChanged();
		for(int i= 0; i<table.getRowCount(); i++){
			int height = ((PedidosCoordenadoresTableModel)tableModel).getInternalTableHeight(i);
			table.setRowHeight(i, height);
		}		
	}
	
	public void updateRow(int rowIndex){
		((PedidosCoordenadoresTableModel)tableModel).fireTableRowsUpdated(rowIndex, rowIndex);
		int height = ((PedidosCoordenadoresTableModel)tableModel).getInternalTableHeight(rowIndex);
		table.setRowHeight(rowIndex, height);
	}
	
	private void preencheCoresLinhas(){
		int numeroLinhas = table.getRowCount();
		coresLinhas.clear();
		Disciplina.resetCoresPerfis();
		for(int i=0; i<numeroLinhas;i++){
			Color cor = Disciplina.getOrSetCoresPerfis(((PedidosCoordenadoresTableModel)tableModel).getLinhas().get(i).getPerfil());
			coresLinhas.add(cor);
		}
	}
}