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

@Entity
@Table(name = "vagas_atendidas")
public class VagasAtendidas implements GenericsVagas{
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int idVagasSolicitadas;
	
	@Column(name = "total_vagas", unique = false, nullable = false)
	private int totalVagas;	
	@Column(name = "vagas_periotizados_ofert", unique = false, nullable = false)
	private int vagasPeriotOfert;	
	@Column(name = "vagas_periotizados_nao_ofert", unique = false, nullable = false)
	private int vagasPeriotNaoOfert;
	@Column(name = "vagas_desperiotizados_ofert", unique = false, nullable = false)
	private int vagasDesperiotOfert;	
	@Column(name = "vagas_desperiotizados_nao_ofert", unique = false, nullable = false)
	private int vagasDesperiotNaoOfert;
	@Column(name = "descricao", unique = false, nullable = false)
	private String descricao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="disciplina_fk")
	private Disciplina disciplina;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="pedidos_coordenadores_fk")
	private PedidosCoordenadores pedidosCoordenadores;

	public VagasAtendidas() {
		super();
	}

	public VagasAtendidas(Disciplina disciplina, PedidosCoordenadores pedidosCoordenadores) {
		super();
		this.totalVagas = 0;
		this.vagasPeriotOfert = 0;
		this.vagasPeriotNaoOfert = 0;
		this.vagasDesperiotOfert = 0;
		this.vagasDesperiotNaoOfert = 0;
		this.descricao = "Descrição";
		this.disciplina = disciplina;
		this.pedidosCoordenadores = pedidosCoordenadores;
	}

	public int getTotalVagas() {
		return totalVagas;
	}

	public void setTotalVagas(int totalVagas) {
		this.totalVagas = totalVagas;
	}

	public int getVagasPeriotOfert() {
		return vagasPeriotOfert;
	}

	public void setVagasPeriotOfert(int vagasPeriotOfert) {
		this.vagasPeriotOfert = vagasPeriotOfert;
	}

	public int getVagasPeriotNaoOfert() {
		return vagasPeriotNaoOfert;
	}

	public void setVagasPeriotNaoOfert(int vagasPeriotNaoOfert) {
		this.vagasPeriotNaoOfert = vagasPeriotNaoOfert;
	}

	public int getVagasDesperiotOfert() {
		return vagasDesperiotOfert;
	}

	public void setVagasDesperiotOfert(int vagasDesperiotOfert) {
		this.vagasDesperiotOfert = vagasDesperiotOfert;
	}

	public int getVagasDesperiotNaoOfert() {
		return vagasDesperiotNaoOfert;
	}

	public void setVagasDesperiotNaoOfert(int vagasDesperiotNaoOfert) {
		this.vagasDesperiotNaoOfert = vagasDesperiotNaoOfert;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public PedidosCoordenadores getPedidosCoordenadores() {
		return pedidosCoordenadores;
	}

	public void setPedidosCoordenadores(PedidosCoordenadores pedidosCoordenadores) {
		this.pedidosCoordenadores = pedidosCoordenadores;
	}
}