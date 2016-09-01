package hibernate;

import java.net.URL;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * responsável pela configuração e conexão do banco de dados.
 * @author H�ber
 */
public class HibernateUtil {

	// private static SessionFactory sessionFactory = buildSessionFactory();
	private static SessionFactory sessionFactory;
	@SuppressWarnings("unused")
	private static ServiceRegistry serviceRegistry;
	private static Transaction transaction;
	private static Session session;

	private static void buildSessionFactory() {
		try {
			URL config = HibernateUtil.class.getResource("hibernate.cfg.xml");
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration configuration = new Configuration()
					.configure(config);
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties())
					.applySetting("hibernate.connection.driver_class", "org.postgresql.Driver")
					.applySetting("hibernate.connection.url", "jdbc:postgresql://localhost:5433/TimetableBD")
					.applySetting("hibernate.connection.username", "postgres")
					.applySetting("hibernate.connection.password", "root")
					.applySetting("dialect", "org.hibernate.dialect.PostgreSQLDialect");
			sessionFactory = configuration.buildSessionFactory(builder.build());
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	protected static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	protected static Session getInstance() throws Exception {
		if (sessionFactory == null) {
			buildSessionFactory();
		}
		return sessionFactory.openSession();
	}

	/**
	 * Retorna todos os registros da tabela (classe) informada entre begin e
	 * end. Caso begin e end sejam negativos retorna todos os dados.
	 *
	 * @param objClass
	 * @param begin
	 * @param end
	 * @return List<?>
	 */
	@SuppressWarnings({ "finally", "rawtypes" })
	protected static List<?> findAll(Class objClass, int begin, int end) {
		List<?> lista = null;
		Query query = null;
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			query = session.createQuery("From " + objClass.getName());
			if (begin >= 0 && end >= 0) {
				query.setFirstResult(begin);
				query.setMaxResults(end - begin);
			}
			lista = (List<?>) query.list();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
		} finally {
			session.close();
			return lista;
		}
	}

	/**
	 * Retorna todos os objetos da tabela
	 *
	 * @param objClass
	 * @param id
	 * @return Object
	 */
	@SuppressWarnings({ "finally", "rawtypes" })
	protected static Object find(Class objClass, int id) {
		Object objGet = null;
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			objGet = session.get(objClass, id);
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
		} finally {
			session.close();
			return objGet;
		}
	}

	/**
	 * Atualiza ou salva um objeto passado por par�metro retornando {@code true}
	 * caso tenha sucesso e {@code false} caso n�o atualize.
	 *
	 * @param obj
	 * @return boolean
	 * @exception HibernateException
	 *                em caso de erro de transa��o, executando um
	 *                {@code rollback} nas modifica��es.
	 */
	@SuppressWarnings("finally")
	protected static boolean saveOrUpdate(Object obj) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			session.saveOrUpdate(obj);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	/**
	 * Salva um objeto passado por par�metro retornando {@code true} caso tenha
	 * sucesso e {@code false} caso n�o atualize.
	 *
	 * @param obj
	 * @return boolean
	 * @exception HibernateException
	 *                em caso de erro de transa��o, executando um
	 *                {@code rollback} nas modifica��es.
	 */
	@SuppressWarnings("finally")
	protected static boolean save(Object obj) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			session.save(obj);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	/**
	 * Salva um objeto passado por par�metro retornando {@code true} caso tenha
	 * sucesso e {@code false} caso n�o atualize.
	 *
	 * @param obj
	 * @return boolean
	 * @exception HibernateException
	 *                em caso de erro de transa��o, executando um
	 *                {@code rollback} nas modifica��es.
	 */
	@SuppressWarnings("finally")
	protected static boolean saveList(List<?> objects) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			for (Object obj : objects) {
				session.save(obj);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	/**
	 * Atualiza o objeto passado por par�metro retornando {@code true} caso
	 * tenha sucesso e {@code false} caso n�o atualize.
	 *
	 * @param obj
	 * @return boolean
	 * @exception HibernateException
	 *                em caso de erro de transa��o, executando um
	 *                {@code rollback} nas modifica��es.
	 */
	@SuppressWarnings("finally")
	protected static boolean update(Object obj) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			session.update(obj);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	/**
	 * Exclui o objeto passado por par�metro
	 *
	 * @param obj
	 * @return boolean
	 * @exception HibernateException
	 *                em caso de erro de transa��o, executando um
	 *                {@code rollback} nas modifica��es.
	 */
	@SuppressWarnings("finally")
	protected static boolean delete(Object obj) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			session.delete(obj);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	/**
	 * Exclui o v�rios objetos passados por par�metro
	 *
	 * @param objects
	 * @return boolean
	 * @exception HibernateException
	 *                em caso de erro de transa��o, executando um
	 *                {@code rollback} nas modifica��es.
	 */
	@SuppressWarnings("finally")
	protected static boolean deleteList(List<?> objects) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			for (Object obj : objects) {
				session.delete(obj);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}
}
