package news.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import news.dao.NewsDao;
import news.entities.News;
import news.entities.Rubric;

public class NewsBoImpl implements NewsBo, Serializable {

	private static final long serialVersionUID = 1L;
	NewsDao newsDao;
	 
	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}
 
	@Override
	public void addNews(News news) {
		// TODO Auto-generated method stub
		newsDao.addNews(news);
	}

	@Override
	public News getNews(Integer idNews) {
		// TODO Auto-generated method stub
		return newsDao.getNews(idNews);
	}

	@Override
	public List<News> listNews() {
		// TODO Auto-generated method stub
		return newsDao.listNews();
	}

	@Override
	public List<News> findNews(String text) {
		// TODO Auto-generated method stub
		return newsDao.findNews(text);
	}

	@Override
	public List<News> findNews(Integer id_rubric) {
		// TODO Auto-generated method stub
		return newsDao.findNews(id_rubric);
	}

	@Override
	public List<News> findNews(Date dat) {
		// TODO Auto-generated method stub
		return newsDao.findNews(dat);
	}

	@Override
	public List<News> findNews(Integer id_rubric, Date dat) {
		// TODO Auto-generated method stub
		return newsDao.findNews(id_rubric, dat);
	}

	@Override
	public List<Rubric> listRubric() {
		// TODO Auto-generated method stub
		return newsDao.listRubric();
	}

}
