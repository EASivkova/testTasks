package news.desctopLoad;

import org.apache.log4j.Logger;

import news.beans.NewsBean;
import news.bo.NewsBo;
import news.entities.News;
import news.parsers.E1;
import news.parsers.Ria;
import news.parsers.Test;

public class LoadNews {

	private static final Logger log = Logger.getLogger(NewsBean.class);
	/**
	 * @param args
	 */
	
	static NewsBo newsBo;
	public NewsBo getNewsBo() {
		return newsBo;
	}
	public void setNewsBo(NewsBo newsBo) {
		this.newsBo = newsBo;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
/*		Ria ria = new Ria();
		for (News news : ria.getListNews())
			log.debug(news.getHeader());
		E1 e1 = new E1();
		for (News news : e1.getListNews())
			log.debug(news.getHeader()); */
		Test t = new Test();
	}

}
