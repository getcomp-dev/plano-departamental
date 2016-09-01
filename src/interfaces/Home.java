package interfaces;

import hibernate.CursoDAO;
import hibernate.DisciplinaDAO;
import hibernate.PedidosCoordenadoresDAO;
import hibernate.VagasAtendidasDAO;
import hibernate.VagasSolicitadasDAO;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import timetable.Curso;
import timetable.Disciplina;
import timetable.PedidosCoordenadores;
import timetable.VagasAtendidas;
import timetable.VagasSolicitadas;
/*
 * interface inicial do programa
 */
@SuppressWarnings("serial")
public class Home extends JPanel{
	private JPanel painel; // painel principal
	private JButton botaoIncluir; // salva os dados alterados da tabela
	private JComboBox<String> comboBoxSemestre;
	private JComboBox<String> comboBoxAno;
	private GridBagLayout gridBag;
	private GridBagConstraints constraints;
	private PedidosCoordenadoresDAO coordenadoresDAO;
	private VagasAtendidasDAO atendidasDAO;
	private VagasSolicitadasDAO solicitadasDAO;
	private CursoDAO cursoDAO;
	private DisciplinaDAO disciplinaDAO;
	private List<PedidosCoordenadores> coordenadores;
	private static int semestre = 0;
	private static int ano = 0;

	public Home(JTabbedPane abas) {
		super(new GridLayout(1, 0));
		// inicializa as vari�veis
		this.painel = new JPanel();
		this.botaoIncluir = new JButton("Incluir / Pesquisar Semestre");
		this.gridBag = new GridBagLayout();
		this.constraints = new GridBagConstraints();
		this.coordenadoresDAO = new PedidosCoordenadoresDAO();
		this.atendidasDAO = new VagasAtendidasDAO();
		this.solicitadasDAO = new VagasSolicitadasDAO();
		this.cursoDAO = new CursoDAO();
		this.disciplinaDAO = new DisciplinaDAO();
		
		try {
			coordenadores = this.coordenadoresDAO.procuraTodos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//monta o combobox
		String[] semestresComboBox = {"1", "3"};
		String[] anoComboBox = {""};
		
		ArrayList<Integer> ano = new ArrayList<Integer>();
		if(coordenadores != null){						
			for(PedidosCoordenadores c: coordenadores){
				if(ano.contains(c.getAno()) == false){
					ano.add(c.getAno());
				}
			}
			anoComboBox = new String[ano.size()];
			for(int i = 0; i< ano.size();i++){
				anoComboBox[i] = ano.get(i).toString();
			}
		}
		
		comboBoxAno = new JComboBox<String>(anoComboBox);
		comboBoxAno.setEditable(true);
		
		comboBoxSemestre = new JComboBox<String>(semestresComboBox);
		
		
		
//		comboBoxSemestre.addActionListener((ActionListener) this);
		
		// monta a janela principal		
		painel.setLayout(gridBag);
		painel.add(botaoIncluir);
		painel.add(comboBoxSemestre);
		painel.add(comboBoxAno);
		
		// seta a posi��o do painel
		LayoutConstraints.setConstraints(constraints, 0, 0, 100, 100, 100, 100);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		gridBag.setConstraints(painel, constraints);

		// seta a posi��o do painel onde se localiza o bot�o
		LayoutConstraints.setConstraints(constraints, 0, 0, 1, 1, 1, 1);
		constraints.insets = new Insets(0, 280, 0, 0);
		constraints.fill = GridBagConstraints.RELATIVE;
		constraints.anchor = GridBagConstraints.CENTER;
		gridBag.setConstraints(botaoIncluir, constraints);
		
		// seta a posi��o do painel onde se localiza o combobox
		LayoutConstraints.setConstraints(constraints, 0, 0, 1, 1, 1, 1);
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.fill = GridBagConstraints.RELATIVE;
		constraints.anchor = GridBagConstraints.CENTER;
		gridBag.setConstraints(comboBoxSemestre, constraints);
		
		// seta a posi��o do painel onde se localiza o combobox
		LayoutConstraints.setConstraints(constraints, 0, 0, 1, 1, 1, 1);
		constraints.insets = new Insets(0, 0, 0, 200);
		constraints.fill = GridBagConstraints.RELATIVE;
		constraints.anchor = GridBagConstraints.CENTER;
		gridBag.setConstraints(comboBoxAno, constraints);
		
		botaoIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if(comboBoxAno.getSelectedItem() != ""){
					int anoAnterior = Home.ano;
					int semestreAnterior = Home.semestre;
					
					Home.ano = Integer.parseInt((String) comboBoxAno.getSelectedItem());
					Home.semestre = Integer.parseInt((String) comboBoxSemestre.getSelectedItem());
					
					boolean existeSemestreAno = false; 
					for(PedidosCoordenadores pc: coordenadores){
						if(pc.getAno() == Home.ano && pc.getSemestre() == Home.semestre){
							existeSemestreAno = true;
							break;
						}
					}
					if(existeSemestreAno == false){
						int resposta = JOptionPane.showConfirmDialog(new JFrame(), "Este semestre n�o existe, deseja incluir um novo?");
						if(resposta == JOptionPane.YES_OPTION){	
							List<Curso> curso = null;
							List<Disciplina> disciplinas = null;
							try {
								disciplinas = disciplinaDAO.procuraTodos();
								curso = cursoDAO.procuraTodos();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							if(curso != null && disciplinas != null){
								for(Curso c: curso){
									PedidosCoordenadores pedidosCoordenadores = new PedidosCoordenadores(Home.semestre,	Home.ano, new Date(System.currentTimeMillis()),	"coordenador", c);
									coordenadoresDAO.salvaOuEdita(pedidosCoordenadores);
									for(Disciplina d: disciplinas){
										VagasAtendidas atendidas = new VagasAtendidas(d, pedidosCoordenadores);
										atendidasDAO.salvaOuEdita(atendidas);
										VagasSolicitadas solicitadas = new VagasSolicitadas(d, pedidosCoordenadores);
										solicitadasDAO.salvaOuEdita(solicitadas);
									}
								}
							}
							
							existeSemestreAno = true;
						}else{
							Home.ano = anoAnterior;
							Home.semestre = semestreAnterior;
							
							InterfacePrincipal.desabilitaAba(1);
							InterfacePrincipal.desabilitaAba(2);
							InterfacePrincipal.desabilitaAba(3);
							InterfacePrincipal.desabilitaAba(4);
							InterfacePrincipal.desabilitaAba(5);
						}
					}
						
					if(existeSemestreAno){
						InterfacePrincipal.habilitaAba(1);
						InterfacePrincipal.habilitaAba(2);
						InterfacePrincipal.habilitaAba(3);
						InterfacePrincipal.habilitaAba(4);
						InterfacePrincipal.habilitaAba(5);
					}
				}
			}
		});
	}

	public JPanel getPainel() {
		return painel;
	}

	public static int getSemestre() {
		return semestre;
	}

	public static void setSemestre(int semestre) {
		Home.semestre = semestre;
	}

	public static int getAno() {
		return ano;
	}

	public static void setAno(int ano) {
		Home.ano = ano;
	}
}
