package tabelasInternas;

import timetable.GenericsVagas;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import estruturasAuxiliaresTabelas.CellRenderer;

@SuppressWarnings("serial")
public class DefaultInternalTable extends JPanel{
	
	private JTable table;
	private AbstractTableModel tableModel;
	
	//boolean cores = flag para indicar se haverá cor nas tabelas internas
	public DefaultInternalTable(ArrayList<String> linhas, ArrayList<Color> coresLinhas, boolean cores){
		super(new GridLayout(1, 0));
		
		tableModel = new DefaultInternalTableModel(linhas);
		table  = new JTable(tableModel);
		
		if(cores){		
			table.getColumnModel().getColumn(0).setCellRenderer(new CellRenderer(coresLinhas));
		}
	}
	
	public DefaultInternalTable(ArrayList<String> linhas, ArrayList<Color> coresLinhas, boolean cores, boolean editable){
		super(new GridLayout(1, 0));
		
		tableModel = new DefaultInternalTableModel(linhas, editable);
		table  = new JTable(tableModel);
		
		if(cores){		
			table.getColumnModel().getColumn(0).setCellRenderer(new CellRenderer(coresLinhas));
		}
	}
	
	public DefaultInternalTable(List<? extends GenericsVagas> linhas, ArrayList<Color> coresLinhas, boolean editable, int campoExibicao){
		super(new GridLayout(1, 0));
		
		tableModel = new VagasGenericsInternalTableModel(linhas, editable, campoExibicao);
		table  = new JTable(tableModel);
		
		if(!coresLinhas.isEmpty()){		
			table.getColumnModel().getColumn(0).setCellRenderer(new CellRenderer(coresLinhas));
		}
	}

	public JTable getTable() {
		return table;
	}

	public AbstractTableModel getTableModel() {
		return tableModel;
	}
}
