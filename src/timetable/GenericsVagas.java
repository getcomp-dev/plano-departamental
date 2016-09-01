package timetable;

/*
 * interface genérica para controlar quais classes herdam desta classe
 */
public interface GenericsVagas {
	public int getTotalVagas();

	public void setTotalVagas(int totalVagas);

	public int getVagasPeriotOfert();

	public void setVagasPeriotOfert(int vagasPeriotOfert);

	public int getVagasPeriotNaoOfert();

	public void setVagasPeriotNaoOfert(int vagasPeriotNaoOfert);

	public int getVagasDesperiotOfert();

	public void setVagasDesperiotOfert(int vagasDesperiotOfert);

	public int getVagasDesperiotNaoOfert();

	public void setVagasDesperiotNaoOfert(int vagasDesperiotNaoOfert);

	public String getDescricao();

	public void setDescricao(String descricao);
}
