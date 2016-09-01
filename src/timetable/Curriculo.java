package timetable;

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
@Table(name = "curriculo")
public class Curriculo {
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int idCurriculo;
	@Column(name = "anoInicio", unique = false, nullable = false)
	private int anoInicio;
	@Column(name = "ativo", unique = false, nullable = false)
	private boolean ativo;
	@Column(name = "idCurso", unique = false, nullable = false)
	private int idCurso;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="curso_fk")
	private Curso curso;
}
