package timetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "turma")
public class Turma implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	@Column(name = "codigo", unique = true, nullable = false)
	private String codigo;
	@Column(name = "turno", unique = false, nullable = false)
	private String turno;
	@Column(name = "max_vagas", unique = false, nullable = false)
	private int maxVagas;
	@Column(name = "ano", unique = false, nullable = false)
	private int ano;
	@Column(name = "semestre", unique = false, nullable = false)
	private int semestre;
	@Column(name = "horario_fixo", unique = false, nullable = false)
	private boolean horarioFixo;


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "disciplina_fk", nullable = false, unique = false)
	private Disciplina disciplina;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sala_fk", nullable = false, unique = false)
	private Sala sala;

	@ManyToMany()
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "creditoministrado", joinColumns = { @JoinColumn(name = "turma_fk", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "docente_fk", nullable = false, updatable = false) })
	private List<Docente> docente = new ArrayList<Docente>();
	
	@ManyToMany()
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "turma_horario", joinColumns = { @JoinColumn(name = "turma_fk", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "horario_fk", nullable = false, updatable = false) })
	private List<Horario> horario = new ArrayList<Horario>();
	
	public Turma() {
		this.semestre = 0;
		this.ano = 0;
	}

	public Turma(String codigo, String turno, int maxVagas,	Disciplina disciplina, Sala sala, Docente docente, int ano, int semestre, boolean horarioFixo) {
		this.codigo = codigo;
		this.turno = turno;
		this.maxVagas = maxVagas;
		this.disciplina = disciplina;
		this.ano = ano;
		this.semestre = semestre;
		this.horarioFixo = horarioFixo;
		if(sala != null)
			this.sala = sala;
		if(docente != null)
			this.docente.add(docente);
	}
	
	public void setHorarioFixo(boolean horarioFixo) {
		this.horarioFixo = horarioFixo;
	}
	
	public boolean isHorarioFixo() {
		return horarioFixo;
	}

	public void setHorario(List<Horario> horario) {
		this.horario = horario;
	}

	public List<Horario> getHorario() {
		return horario;
	}

	public int getIdTurma() {
		return id;
	}

	public void setIdTurma(int idTurma) {
		this.id = idTurma;
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

	public int getMaxVagas() {
		return maxVagas;
	}

	public void setMaxVagas(int maxVagas) {
		this.maxVagas = maxVagas;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public List<Docente> getDocente() {
		return docente;
	}

	public void setDocente(List<Docente> docente) {
		this.docente = docente;
	}

}
