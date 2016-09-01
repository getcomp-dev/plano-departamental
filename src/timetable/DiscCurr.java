package timetable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Héber
 */

@Entity
@Table(name = "disccurr")
public class DiscCurr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	@Column(name = "periodo", unique = false, nullable = false)
	private int periodo;
	@Column(name = "carater", unique = false, nullable = false)
	private String carater;
	@Column(name = "idDisciplina", unique = false, nullable = false)
	private int idDisciplina;
	@Column(name = "idCurriculo", unique = false, nullable = false)
	private int idCurriculo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="disciplina_fk")
	private Disciplina disciplina;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="curriculo_fk")
	private Curriculo curriculo;

}
