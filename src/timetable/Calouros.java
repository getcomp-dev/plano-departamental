package timetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Héber
 */
@Entity
@Table(name = "Calouros")
public class Calouros implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int idCalouro;
	@Column(name = "num_vagas", unique = true, nullable = false)
	private int numVagas;
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "calouros")
	private List<Curso> cursos = new ArrayList<Curso>();

	public Calouros() {
	}

	public Calouros(int numVagas) {
		this.numVagas = numVagas;
	}

	public int getIdCalouro() {
		return idCalouro;
	}

	public void setIdCalouro(int idCalouro) {
		this.idCalouro = idCalouro;
	}

	public int getNumVagas() {
		return numVagas;
	}

	public void setNumVagas(int numVagas) {
		this.numVagas = numVagas;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}
}
