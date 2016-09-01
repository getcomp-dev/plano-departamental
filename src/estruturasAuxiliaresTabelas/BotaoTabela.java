package estruturasAuxiliaresTabelas;

import interfaces.LayoutConstraints;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author H�ber
 * classe responsável por criar e renderizar um botão dentro de uma tabela
 */
@SuppressWarnings("serial")
public class BotaoTabela extends AbstractCellEditor implements	TableCellRenderer, TableCellEditor, ActionListener {

	JTable table;
	JButton renderButton; //bot�o de exibi��o
	JButton editButton; //bot�o que realiza a a��o
	String text;

	public BotaoTabela(JTable table, int column) {
		super();
		this.table = table;
		
		renderButton = new JButton();
		editButton = new JButton();
		
		editButton.setFocusPainted(false);
		editButton.addActionListener(this);

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer(this);
		columnModel.getColumn(column).setCellEditor(this);

	}
	
	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
	
	public JButton getEditButton() {
		return editButton;
	}

	public void setEditButton(JButton editButton) {
		this.editButton = editButton;
	}
	
	public void editingStopped(){
		fireEditingStopped();
	}
	
	/*
	 * função resposável por renderizar o botão dentro da tabela
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (hasFocus) {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		} else if (isSelected) {
			renderButton.setForeground(table.getSelectionForeground());
			renderButton.setBackground(table.getSelectionBackground());
		} else {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		}

		renderButton.setText((value == null) ? "" : value.toString());
		
		//cria um novo painel para o controle do tamanho do bot�o
		JPanel painelBotao = new JPanel();
		GridBagLayout btnGridBag = new GridBagLayout();
		painelBotao.setLayout(btnGridBag);
		//adiciona este bot�o ao painel
		painelBotao.add(renderButton);
		
		// seta a posi��o e tamanho do bot�o dentro da tabela
		GridBagConstraints constraints = new GridBagConstraints();
		LayoutConstraints.setConstraints(constraints, 1, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.fill = GridBagConstraints.CENTER;
		constraints.anchor = GridBagConstraints.CENTER;
		renderButton.setPreferredSize(new Dimension(50, 20));
		renderButton.setMaximumSize(new Dimension(50,20));
		renderButton.setMinimumSize(new Dimension(50,20));
		btnGridBag.setConstraints(renderButton, constraints);
		
		return painelBotao;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		text = (value == null) ? "" : value.toString();
		editButton.setText(text);
		return editButton;
	}

	public Object getCellEditorValue() {
		return text;
	}

	public void actionPerformed(ActionEvent e) {
		//n�o utilizado		
	}

	public JButton getRenderButton() {
		return renderButton;
	}
}
