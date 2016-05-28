package news.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import news.bo.NewsBo;
import news.entities.News;
import news.entities.Rubric;
import news.parsers.E1;
import news.parsers.Ria;

import org.apache.log4j.Logger;

public class NewsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(NewsBean.class);
	
	NewsBo newsBo;
	public NewsBo getNewsBo() {
		return newsBo;
	}
	public void setNewsBo(NewsBo newsBo) {
		this.newsBo = newsBo;
	}
	
	private int id_rubric;
	
	public int getId_rubric() {
		return id_rubric;
	}
	public void setId_rubric(int id_rubric) {
		this.id_rubric = id_rubric;
	}

	private Date date;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	private String strDate;
	
	public String getStrDate() {
		return strDate;
	}
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}
	
	private String strSearch;
	
	public String getStrSearch() {
		return strSearch;
	}
	public void setStrSearch(String strSearch) {
		this.strSearch = strSearch;
	}
	
	public String load() {
		E1 e1 = new E1();
		for (News news : e1.getListNews()) {
			log.debug(news.getHeader());
			newsBo.addNews(news);
		}
		Ria ria = new Ria();
		for (News news : ria.getListNews()) {
			log.debug(news.getHeader());
			newsBo.addNews(news);
		}
		return "";
	}

	public List<News> getNewsList() {
		List<News> newsList;
		if (getStrSearch() != null && !getStrSearch().trim().equals("")) {
			String str = getStrSearch();
			str = str.replaceAll(".", " ");
			str = str.replaceAll(",", " ");
			str = str.replaceAll("-", " ");
			str = str.replaceAll("!", " ");
			str = str.replaceAll("?", " ");
			str = str.replaceAll("(", " ");
			str = str.replaceAll(")", " ");
			str = str.replaceAll(":", " ");
			str = str.replaceAll(";", " ");
			String[] temp = getStrSearch().split(" ");
			String text = "";
			for (String s : temp) {
				text += s + " & ";
			}
			text = text.substring(0, text.length() - 3);
			newsList = newsBo.findNews(text); 
		} else {
			if (getDate() != null || getId_rubric() != 0) {
				newsList = newsBo.findNews(getId_rubric(), getDate());
			} else {
				if (getId_rubric() != 0) {
					newsList = newsBo.findNews(getId_rubric());
				} else {
					if (getDate() != null) {
						newsList = newsBo.findNews(getDate());
					} else {
						newsList = newsBo.listNews();
					}
				}
			}
		}
		return newsList;
	}
	
    public void rubricChanged(ValueChangeEvent event) {        
    	log.debug((String) event.getNewValue());
    	if (null != event.getNewValue()) {            
    		setId_rubric(Integer.parseInt((String) event.getNewValue()));
    	}    
    }

	public List<Rubric> getListRubric() {
		List<Rubric> listRubric;
		log.debug(newsBo.getNews(1).getHeader());
		listRubric = newsBo.listRubric();
		return listRubric;
	}
	
	public void clearForm() {
		setId_rubric(0);
		setStrDate("");
		setDate(null);
	}
	
}
