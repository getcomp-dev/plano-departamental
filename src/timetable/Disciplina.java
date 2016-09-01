package timetable;

import hibernate.DisciplinaDAO;

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
@Table(name = "disciplina")
public class Disciplina implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int idDisciplina;
	@Column(name = "codigo", unique = true, nullable = false)
	private String codigo;
	@Column(name = "credito", unique = false, nullable = true)
	private int creditos;
	@Column(name = "nome", unique = false, nullable = true)
	private String nome;
	@Column(name = "perfil", unique = false, nullable = true)
	private String perfil;

	@OneToMany(mappedBy = "disciplina")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Turma> turma = new ArrayList<Turma>();
	
	@OneToMany(mappedBy = "disciplina")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VagasAtendidas> vagasAtendidas = new ArrayList<VagasAtendidas>();
	
	@OneToMany(mappedBy = "disciplina")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VagasSolicitadas> vagasSolicitadas = new ArrayList<VagasSolicitadas>();
	
	@ManyToMany(mappedBy = "disciplina")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Periodo> periodo = new ArrayList<Periodo>();
	
	static HashMap<String, Color> coresPerfis = new HashMap<String, Color>();

	public Disciplina() {
	}

	public Disciplina(String codigo, int creditos, String nome, String perfil) {
		this.codigo = codigo;
		this.creditos = creditos;
		this.nome = nome;
		this.perfil = perfil;
	}	
	
	public Disciplina(String codigo, int creditos, String nome, String perfil, List<VagasSolicitadas> vagasSolicitadas) {
		super();
		this.codigo = codigo;
		this.creditos = creditos;
		this.nome = nome;
		this.perfil = perfil;
		this.vagasSolicitadas = vagasSolicitadas;
	}

	public int getIdDisciplina() {
		return idDisciplina;
	}

	public void setIdDisciplina(int idDisciplina) {
		this.idDisciplina = idDisciplina;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getCreditos() {
		return creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public List<VagasAtendidas> getVagasAtendidas() {
		return vagasAtendidas;
	}

	public void setVagasAtendidas(List<VagasAtendidas> vagasAtendidas) {
		this.vagasAtendidas = vagasAtendidas;
	}

	public List<VagasSolicitadas> getVagasSolicitadas() {
		return vagasSolicitadas;
	}

	public void setVagasSolicitadas(List<VagasSolicitadas> vagasSolicitadas) {
		this.vagasSolicitadas = vagasSolicitadas;
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
	
	public static void resetCoresPerfis(){
		DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
		List<Disciplina> disciplina = null;
		try {
			disciplina = disciplinaDAO.procuraTodos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Disciplina d: disciplina){
			getOrSetCoresPerfis(d.getPerfil());
		}
	}
}