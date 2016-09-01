package timetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "docente")
public class Docente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int idDocente;
	@Column(name = "codigo", unique = true, nullable = false)
	private String codigo;
	@Column(name = "nome", unique = false, nullable = false)
	private String nome;
	@Column(name = "nome_completo", unique = false, nullable = false)
	private String nomeCompleto;
	@Column(name = "creditacao_esperada", unique = false, nullable = false)
	private int creditacaoEsperada;

	@ManyToMany(mappedBy = "docente")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Turma> turma = new ArrayList<Turma>();
	

	public Docente(){
	}

	public Docente(String codigo, String nome, String nomeCompleto,	int creditacaoEsperada) {
		this.codigo = codigo;
		this.nome = nome;
		this.nomeCompleto = nomeCompleto;
		this.creditacaoEsperada = creditacaoEsperada;
	}

	public int getIdDocente() {
		return idDocente;
	}

	public void setIdDocente(int idDocente) {
		this.idDocente = idDocente;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public int getCreditacaoEsperada() {
		return creditacaoEsperada;
	}

	public void setCreditacaoEsperada(int creditacaoEsperada) {
		this.creditacaoEsperada = creditacaoEsperada;
	}

	public List<Turma> getTurma() {
		return turma;
	}

	public void setTurma(List<Turma> turma) {
		this.turma = turma;
	}
	
}
