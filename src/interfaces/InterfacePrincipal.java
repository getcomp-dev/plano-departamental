package interfaces;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import interfaceTabelas.CadastroPedidosCoordenadores;
import interfaceTabelas.Calouros;
import interfaceTabelas.Curso;
import interfaceTabelas.Disciplina;
import interfaceTabelas.Docente;
import interfaceTabelas.PedidosCoordenadores;
import interfaceTabelas.PlanoDepartamental;
import interfaceTabelas.ResultadoProfessor;
import interfaceTabelas.Turma;

/**
 * controla todas as interfaces do programa
 * responsável por inserir e excluir interfaces
 * @author H�ber
 */
public class InterfacePrincipal {
	private static final int ABA_HOME = 0;
	private static final int ABA_PLANO_DEPARTAMENTAL = 1;
	private static final int ABA_RESULTADO = 2;
	private static final int ABA_CADASTRO_PEDIDOS = 3;
	private static final int ABA_PEDIDO_COORDENADORES = 4;
	private static final int ABA_TURMAS = 5;
	private static final int ABA_DOCENTE = 6;
	private static final int ABA_DISCIPLINA = 7;	
	private static final int ABA_CURSOS = 8;
	private static final int ABA_CALOUROS = 9;
	
	private JFrame janelaPrincipal;
	private static JTabbedPane abas;
	
	private Curso janelaCurso;
	private Calouros janelaCalouros;
	private ResultadoProfessor janelaResultados;
	private Docente janelaDocente;
	private Disciplina janelaDisciplina;
	private Turma janelaTurma;
	private PlanoDepartamental janelaPlanoDepartamental;
	private CadastroPedidosCoordenadores janelaCadastro;
	private PedidosCoordenadores janelaPedidosCoordenadores;
	private Home janelaHome;

	public void preparaJanela() {		
		janelaCurso = new Curso();
		janelaCalouros = new Calouros();
		janelaResultados = new ResultadoProfessor();
		janelaDocente = new Docente();
		janelaDisciplina = new Disciplina();
		janelaTurma = new Turma();
		janelaPlanoDepartamental = new PlanoDepartamental();
		janelaCadastro = new CadastroPedidosCoordenadores();
		janelaPedidosCoordenadores = new PedidosCoordenadores();
		janelaHome = new Home(abas);
		
		
		janelaPrincipal = new JFrame("Gerador de Grade");		
		abas = new JTabbedPane();
		
		abas.addTab("Home", janelaHome.getPainel());
		abas.addTab("Plano Departamental", janelaPlanoDepartamental.getPainel());
		abas.addTab("Resultado", janelaResultados.getPainel());
		abas.addTab("Cadastro de Pedidos", janelaCadastro.getPainel());
		abas.addTab("Pedidos Coordenadores", janelaPedidosCoordenadores.getPainel());
		abas.addTab("Turmas", janelaTurma.getPainel());
		abas.addTab("Docente", janelaDocente.getPainel());
		abas.addTab("Disciplinas", janelaDisciplina.getPainel());		
		abas.addTab("Cursos", janelaCurso.getPainel());
		abas.addTab("Calouros", janelaCalouros.getPainel());
		
		janelaPrincipal.add(abas);
		janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		abas.setEnabledAt(ABA_PLANO_DEPARTAMENTAL, false);
		abas.setEnabledAt(ABA_RESULTADO, false);
		abas.setEnabledAt(ABA_CADASTRO_PEDIDOS, false);
		abas.setEnabledAt(ABA_PEDIDO_COORDENADORES, false);
		abas.setEnabledAt(ABA_TURMAS, false);
		
		abas.addChangeListener(new javax.swing.event.ChangeListener() {  
            public void stateChanged(javax.swing.event.ChangeEvent e) {   
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_HOME)){}
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_PLANO_DEPARTAMENTAL))
            		janelaPlanoDepartamental.updateTable();
                            	
            	if(abas.getSelectedComponent() == abas.getComponent(ABA_RESULTADO))
            		janelaResultados.updateTable();
            	
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_CADASTRO_PEDIDOS))
            		janelaCadastro.updateTable();
            	
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_PEDIDO_COORDENADORES))
            		janelaPedidosCoordenadores.updateTable();
            	
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_TURMAS))
            		janelaTurma.updateTable();
            	
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_DOCENTE))
            		janelaDocente.updateTable();
            	
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_DISCIPLINA))
            		janelaDisciplina.updateTable();
            	
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_CURSOS))
            		janelaCurso.updateTable();
                           	
            	if (abas.getSelectedComponent() == abas.getComponent(ABA_CALOUROS))
            		janelaCalouros.updateTable();
            }  
        });
	}

	// outros metodos prepara...

	public void mostraJanela() {
		janelaPrincipal.pack();
		janelaPrincipal.setExtendedState( janelaPrincipal.getExtendedState()|JFrame.MAXIMIZED_BOTH ); //maximiza a janela
		janelaPrincipal.setVisible(true);
	}
	
	public static void habilitaAba(int numeroAba){
		if(0 < numeroAba && numeroAba < abas.getTabCount())
			if(abas.isEnabledAt(numeroAba) == false)
				abas.setEnabledAt(numeroAba, true);
	}
	public static void desabilitaAba(int numeroAba){
		if(0 < numeroAba && numeroAba < abas.getTabCount())
			if(abas.isEnabledAt(numeroAba))
				abas.setEnabledAt(numeroAba, false);
	}
}
