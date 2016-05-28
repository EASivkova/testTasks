package news.dao;

import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import news.entities.News;
import news.entities.Rubric;

public class NewsDaoImpl extends HibernateDaoSupport implements NewsDao {

	@Override
	public void addNews(News news) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(news);
	}

	@Override
	public News getNews(Integer idNews) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().load(News.class, idNews);
	}

	@Override
	public List<News> listNews() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from news");
	}

	@Override
	public List<News> findNews(String text) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from news, to_tsquery(?) q where fts @@ q", text);
	}

	@Override
	public List<News> findNews(Integer id_rubric) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from news n where n.id_rubric = ?", id_rubric);
	}

	@Override
	public List<News> findNews(Date dat) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from news n where n.dat = ?", dat);
	}

	@Override
	public List<News> findNews(Integer id_rubric, Date dat) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from news n where n.id_rubric = ? and n.dat = ?", id_rubric, dat);
	}

	@Override
	public List<Rubric> listRubric() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from rubric");
	}

	@Override
	public void addRubric(Rubric rubric) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(rubric);
	}

}
