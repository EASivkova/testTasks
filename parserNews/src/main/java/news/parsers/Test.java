package news.parsers;

import java.sql.Date;

import org.hibernate.Session;

import news.entities.News;
import news.entities.Rubric;
import news.util.HibernateUtil;

public class Test {

	public Test() {
		Rubric rubric = new Rubric();
		rubric.setIdRubric(1);
		rubric.setName("sfgsdfg");
		
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(rubric);
        session.getTransaction().commit();
		
		News news = new News();
		news.setBody("sdgfsdfgмы ыва ывапч sdxc");
		Date date = Date.valueOf("2013-04-08");
		news.setDat(date);
		news.setHeader("fsbs adf");
		news.setIdNews(1);
		news.setRubric(rubric);

        session.beginTransaction();
        session.saveOrUpdate(news);
        session.getTransaction().commit();
	}
	
	
}
