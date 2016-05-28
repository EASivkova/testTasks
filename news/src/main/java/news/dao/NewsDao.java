package news.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import news.utils.HibernateUtil;
import news.entities.News;
import news.entities.Rubric;

public class NewsDao {

	public void addNews(News news) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(news);
        session.getTransaction().commit();
	}

	public News getNews(Integer idNews) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        News result = (News) session.load(News.class, idNews);
        session.getTransaction().commit();
        return result;
	}

	public List<News> listNews() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<News> result = session.createQuery("from News order by dat").list();
        for (News n : result) {
            Hibernate.initialize(n.getRubric());
        }
        session.getTransaction().commit();
        return result;
	}

	public List<News> findNews(String text) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        System.out.println(text);
        List<News> result = session.createQuery("select n from News n where fts('english', n.body, ?) = true").setParameter(0, text).list();
        System.out.println(result.size());
        for (News n : result) {
            Hibernate.initialize(n.getRubric());
        }
        session.getTransaction().commit();
        return result;
	}

	public List<News> findNews(Integer id_rubric) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<News> result = session.createQuery("from News n where n.id_rubric = ?").setParameter(0, id_rubric).list();
        for (News n : result) {
            Hibernate.initialize(n.getRubric());
        }
        session.getTransaction().commit();
        return result;
	}

	public List<News> findNews(Date dat) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<News> result = session.createQuery("from News n where n.dat = ?").setParameter(0, dat).list();
        for (News n : result) {
            Hibernate.initialize(n.getRubric());
        }
        session.getTransaction().commit();
        return result;
	}

	public List<News> findNews(Integer id_rubric, Date dat) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<News> result = session.createQuery("from News n where n.id_rubric = ? and n.dat = ?").setParameter(0, id_rubric).setParameter(1, dat).list();
        for (News n : result) {
            Hibernate.initialize(n.getRubric());
        }
        session.getTransaction().commit();
        return result;
	}

	public List<Rubric> listRubric() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Rubric> result = session.createQuery("from Rubric").list();
        for (Rubric r : result) {
            Hibernate.initialize(r.getNewsList());
        }
        session.getTransaction().commit();
        return result;
	}

	public void addRubric(Rubric rubric) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(rubric);
        session.getTransaction().commit();
	}

	public Rubric getRubric(String name) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Rubric> list = session.createQuery("from Rubric r where r.name = ?").setParameter(0, name).list();
        if (list.size() > 0) {
	        Rubric result = list.get(0);
	        Hibernate.initialize(result.getNewsList());
	        session.getTransaction().commit();
	        return result;
        } else {
        	return null;
        }
	}

}
