package tabelasInternas;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import estruturasAuxiliaresTabelas.CellRenderer;
import timetable.GenericsVagas;

@SuppressWarnings("serial")
public class VagasPeriotizadosInternalTable extends JPanel{
	private JTable table;
	private AbstractTableModel tableModel;
	
	public VagasPeriotizadosInternalTable(List<? extends GenericsVagas> linhas, ArrayList<Color> coresLinhas){
		super(new GridLayout(1, 0));
		
		tableModel = new VagasPeriotizadosInternalTableModel(linhas);
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
