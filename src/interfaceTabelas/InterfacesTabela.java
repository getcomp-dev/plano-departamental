package interfaceTabelas;


import hibernate.GenericoDAO;
import interfaces.LayoutConstraints;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
/*
 * Interface padrão para qualquer interface com tabela
 * possui todo o layout pronto para a tal.
 */
@SuppressWarnings("serial")
public abstract class InterfacesTabela extends JPanel{

	protected JPanel painel; // painel principal
	protected JScrollPane scroll; // componente para se adicionar a tabela
	protected JButton botaoPadrao; // salva os dados alterados da tabela
	protected JPanel painelBotao;
	protected GridBagLayout gridBag;
	protected GridBagConstraints constraints;
	protected GridBagLayout btnGridBag;
	protected GenericoDAO genericoDAO;
	protected JTable table;
	protected AbstractTableModel tableModel;

	public InterfacesTabela(AbstractTableModel tableModel, String textoBotao) {
		super(new GridLayout(1, 0));
		// inicializa as vari�veis
		this.tableModel = tableModel;
		this.painel = new JPanel();
		this.table = new JTable(tableModel);
		this.painelBotao = new JPanel();
		this.botaoPadrao = new JButton(textoBotao);
		this.genericoDAO = new GenericoDAO();
		this.gridBag = new GridBagLayout();
		this.constraints = new GridBagConstraints();
		this.btnGridBag = new GridBagLayout();
		
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);	
		scroll = new JScrollPane(table);		
		
		// monta a janela principal		
		painel.setLayout(gridBag);	
		painelBotao.setLayout(btnGridBag);		
		
		scroll.getViewport().setBorder(null);
		scroll.setSize(450, 450);

		painelBotao.add(botaoPadrao);
		painel.add(scroll);
		painel.add(painelBotao);		

		// seta a posi��o do painel onde se localiza as tabelas
		LayoutConstraints.setConstraints(constraints, 0, 0, 100, 100, 100, 100);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		gridBag.setConstraints(painel, constraints);

		// seta o tamanho da tabela
		LayoutConstraints.setConstraints(constraints, 0, 0, 1, 1, 100, 100);
		constraints.insets = new Insets(30, 20, 20, 20);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		gridBag.setConstraints(scroll, constraints);
		
		// seta a posi��o do painel onde se localiza os bot�es
		LayoutConstraints.setConstraints(constraints, 0, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(2, 2, 20, 2);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.SOUTH;
		gridBag.setConstraints(painelBotao, constraints);
		
		// seta a posi��o do bot�o salvar
		LayoutConstraints.setConstraints(constraints, 1, 1, 1, 1, 1, 1);
		constraints.insets = new Insets(0, 0, 0, 40);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.EAST;
		btnGridBag.setConstraints(botaoPadrao, constraints);

	}
	
	public InterfacesTabela(AbstractTableModel tableModel) {
		super(new GridLayout(1, 0));
		// inicializa as vari�veis
		this.tableModel = tableModel;
		this.painel = new JPanel();
		this.table = new JTable(tableModel);
		this.genericoDAO = new GenericoDAO();
		this.gridBag = new GridBagLayout();
		this.constraints = new GridBagConstraints();
		this.btnGridBag = new GridBagLayout();
		
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);	
		scroll = new JScrollPane(table);		
		
		// monta a janela principal		
		painel.setLayout(gridBag);	
		
		scroll.getViewport().setBorder(null);
		scroll.setSize(450, 450);

		painel.add(scroll);	

		// seta a posi��o do painel onde se localiza as tabelas
		LayoutConstraints.setConstraints(constraints, 0, 0, 100, 100, 100, 100);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		gridBag.setConstraints(painel, constraints);

		// seta o tamanho da tabela
		LayoutConstraints.setConstraints(constraints, 0, 0, 1, 1, 100, 100);
		constraints.insets = new Insets(30, 20, 20, 20);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		gridBag.setConstraints(scroll, constraints);

	}

	public JPanel getPainel() {
		return painel;
	}

	public abstract void updateTable();
	
}
