package timetable;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "Curso")
public class Curso implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int idCurso;
	@Column(name = "nome", unique = true, nullable = false)
	private String nome;
	@Column(name = "codigo", unique = true, nullable = false)
	private String codigo;
	@Column(name = "turno", unique = false, nullable = false)
	private String turno;

//	@ManyToMany(fetch = FetchType.EAGER)
	@ManyToMany()
	@JoinTable(name = "calouros_curso", joinColumns = { @JoinColumn(name = "curso_fk", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "calouros_fk", nullable = false, updatable = false) })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Calouros> calouros = new ArrayList<Calouros>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "curso")
	private List<Curso> curso = new ArrayList<Curso>();
	
	@OneToMany(mappedBy = "curso")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<PedidosCoordenadores> pedidosCoordenadores = new ArrayList<PedidosCoordenadores>();
	
	@OneToMany(mappedBy = "curso")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Periodo> periodo = new ArrayList<Periodo>();
	
	public List<PedidosCoordenadores> getPedidosCoordenadores() {
		return pedidosCoordenadores;
	}

	public void setPedidosCoordenadores(
			List<PedidosCoordenadores> pedidosCoordenadores) {
		this.pedidosCoordenadores = pedidosCoordenadores;
	}

	static final int NUMERO_CALOUROS = 2;
	static HashMap<String, Color> coresPerfis = new HashMap<String, Color>();
	
	
	public Curso(String nome, String codigo, String turno, Calouros calourosPrimSem, Calouros calourosSegSem) {
		this.nome = nome;
		this.codigo = codigo;
		this.turno = turno;
		this.calouros.add(calourosPrimSem);
		this.calouros.add(calourosSegSem);
	}

	public Curso() {
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public List<Calouros> getCalouros() {
		return calouros;
	}

	public void setCalouros(List<Calouros> calouros) {
		this.calouros = calouros;
	}	
	
	public static int getNumeroCalouros() {
		return NUMERO_CALOUROS;
	}
	
	public List<Periodo> getPeriodo() {
		return periodo;
	}

	public void setPeriodo(List<Periodo> periodo) {
		this.periodo = periodo;
	}

	public static Color getOrSetCoresPerfis(String key) {
		if(coresPerfis.containsKey(key))
			return coresPerfis.get(key);
		else{
			Random rnd = new Random();
			int cont = 0;
			while(true){
				cont++;
				int r = rnd.nextInt(156) + 100;
				int g = rnd.nextInt(156) + 100;
				int b = rnd.nextInt(156) + 100;
				Color cor = new Color(r, g, b);
				if(!coresPerfis.containsValue(cor)){
					coresPerfis.put(key, cor);
					break;
				}else if(cont > 16581375){
					break;
				}		
			}
			
			return coresPerfis.get(key);
		}
	}
}
