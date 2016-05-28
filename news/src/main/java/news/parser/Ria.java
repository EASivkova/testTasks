package news.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndPerson;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import news.dao.NewsDao;
import news.entities.News;
import news.entities.Rubric;

import org.apache.log4j.Logger;

public class Ria {
	private static final Logger log = Logger.getLogger(Ria.class);

	public Ria() {
        try {
			printRSSContent(parseFeed("http://ria.ru/export/rss2/incidents/index.xml"));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			log.debug(e.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			log.debug(e.toString());
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			log.debug(e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.debug(e.toString());
		}
	}
	
    public SyndFeed parseFeed(String url) throws IllegalArgumentException, MalformedURLException, FeedException, IOException {
        return new SyndFeedInput().build(new XmlReader(new URL(url)));
    }

    public void printRSSContent(SyndFeed feed) {
    	NewsDao dao = new NewsDao();
    	String rubricName = "Происшествие";
        for (Object object : feed.getEntries()) {
            SyndEntry entry = (SyndEntry) object;
            for (Object contobj : entry.getCategories()) {
            	String tmp = contobj.toString();
            	rubricName = tmp.substring(tmp.lastIndexOf("=") + 1, tmp.length() - 1);
            }
        	Rubric rubric = dao.getRubric(rubricName);
        	if (rubric == null) {
        		rubric = new Rubric();
        		rubric.setIdRubric(dao.listRubric().size() + 1);
        		rubric.setName(rubricName);
        		dao.addRubric(rubric);
        	}
            News news = new News();
            news.setRubric(rubric);
            news.setHeader(entry.getTitle());
            String str = "" + entry.getLink();
            str = str.substring(str.indexOf("/", 18), str.indexOf(".html"));
            Date date = Date.valueOf(str.substring(1, 5) + "-" + str.substring(5, 7) + "-" + str.substring(7, 9));
            news.setDat(date);
            news.setIdNews(Integer.parseInt(str.substring(str.length() - 9)));
            for (Object contobj : entry.getContents()) {
                SyndContent content = (SyndContent) contobj;
            }
            SyndContent content = entry.getDescription();
            if (content != null) {
                news.setBody(content.getValue());
            }
            dao.addNews(news);
        }
    }

}
