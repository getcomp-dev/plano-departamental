package timetable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
@Table(name = "horario")
public class Horario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	
	@Column(name = "dia", unique = false, nullable = false)
	private Timestamp dia;
	
	@ManyToMany(mappedBy = "horario")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Turma> turma = new ArrayList<Turma>();
	
	public Horario() {
		super();
	}

	public Horario(Date dia) {
		super();
		this.dia = new Timestamp(dia.getTime());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDia() {
		return new Date(dia.getTime());
	}

	public void setDia(Date dia) {
		this.dia = new Timestamp(dia.getTime());
	}

	public List<Turma> getTurma() {
		return turma;
	}

	public void setTurma(List<Turma> turma) {
		this.turma = turma;
	}
	
	
}
