package hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import timetable.Calouros;
import timetable.Curso;
/*
 * gerencia as buscas no banco de dados para a classe/tabela curso
 */
public class CursoDAO extends GenericoDAO{

	public CursoDAO() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("finally")
	public Curso encontraCursoPorCodigo(String cursoCodigo) throws HibernateException, Exception {
		Curso curso = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Curso.class)
					.add(Restrictions.eq("codigo", cursoCodigo));	
			curso = (Curso) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return curso;
		}
	}
	
	@SuppressWarnings({"unchecked", "finally" })
	public List<Curso> procuraTodos() throws Exception {
		List<Curso> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Curso.class);
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public List<Calouros> encontraCalouroPorCodigoCurso(String cursoCodigo) throws HibernateException, Exception {
		List<Calouros> calouros = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Calouros.class)
					.createAlias("curso", "c")
					.add(Restrictions.eq("c.codigo", cursoCodigo));	
			calouros = (List<Calouros>) criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return calouros;
		}
	}
}
