package news.parsers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import news.util.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import news.dao.NewsDao;
import news.dao.NewsDaoImpl;
import news.entities.*;

public class E1 {

	private static final Logger log = Logger.getLogger(E1.class);
	private List<News> listNews = new ArrayList<News>();
	
	public List<News> getListNews() {
		return listNews;
	}

	public E1() {
		String textHTML = connect("http://www.e1.ru/news");
		if (!textHTML.equals("")) {
			parse(textHTML);
		}
	}
	
    private String connect(String url) {
        String text = "";
        try {
        	URL dataURL = new URL(url);
//            String proxyHost = "10.76.0.123";
//            Integer proxyPort = 3128;
//            URLConnection conn = dataURL.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)));
            URLConnection conn = dataURL.openConnection();
            conn.setUseCaches(false);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = in.readLine()) != null)
            	text += line + "\n";
        } catch(Exception e){
        	log.error("Ошибка загрузки новостей с E1.\n" + e.toString());
        	text = "";
        }
        return text;
	}
	
	private void parse(String textHTML) {
		String regex = Pattern.quote("<nobr><a class=") + ".+" + Pattern.quote("<strong>") + ".+" + Pattern.quote("</strong></a></nobr>,&nbsp;&nbsp;");
		Matcher m = Pattern.compile(regex).matcher(textHTML);
		while (m.find()) {
			String str = m.group();
			if (str.indexOf("Дайджест E1.RU") < 0) {
			String url = "http://www.e1.ru" + str.substring(str.indexOf("href=") + 6, str.indexOf("><strong>") - 1);
			int id_rubric = Integer.parseInt(str.substring(str.indexOf("id") + 2, str.indexOf(".html"))) + 1000;
			String rubricName = str.substring(str.indexOf("<strong>") + 8, str.indexOf("</strong>"));
			Rubric rubric = new Rubric();
			rubric.setIdRubric(id_rubric);
			rubric.setName(rubricName);
			String textRubric = connect(url);
			if (!textRubric.equals("") && textRubric != null && textRubric.lastIndexOf("\">" + rubricName + "<") > -1) {
				int beginTextRubric = textRubric.lastIndexOf("\">" + rubricName + "<");
				int endTextRubric = textRubric.indexOf("<noindex><script type=", beginTextRubric);
				textRubric = textRubric.substring(beginTextRubric, endTextRubric);
				int beg = 0;
				for (int i = 0; textRubric.indexOf("<a title=", beg) > -1; i++) {
					String strNews;
					try {
						strNews = textRubric.substring(textRubric.indexOf("<a title=", beg), textRubric.indexOf(".<br />", textRubric.indexOf("<a title=", beg))) + ".";
					} catch(Exception e) {
						strNews = textRubric.substring(textRubric.indexOf("<a title=", beg), textRubric.indexOf("<br />", textRubric.indexOf("<a title=", beg)));
					}
					String header = strNews.substring(10, strNews.indexOf("href=") - 2);
					String dateNews = strNews.substring(strNews.indexOf("small_gray") + 12, strNews.indexOf("</span>"));
					Date date = Date.valueOf(dateNews.substring(6, 10) + "-" + dateNews.substring(3, 5) + "-" + dateNews.substring(0, 2));
					String urlNews = "http://www.e1.ru" + strNews.substring(strNews.indexOf("href=") + 6, strNews.indexOf("><") - 1);
					int id_news = Integer.parseInt(urlNews.substring(urlNews.indexOf("id-") + 3, urlNews.indexOf("-section")));
					String textNews = connect(urlNews);
					textNews = textNews.substring(textNews.indexOf("<div class=") + 18);
					textNews = textNews.substring(0, textNews.indexOf("</div>"));
					String body = textNews;
					News news = new News();
					news.setBody(body);
					news.setDat(date);
					news.setHeader(header);
					news.setIdNews(id_news);
					news.setRubric(rubric);
					listNews.add(news);
			        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			        session.beginTransaction();
			        Long result = (Long) session.save(news);
			        session.getTransaction().commit();

					beg = textRubric.indexOf("<br />", textRubric.indexOf("<a title=", beg));
				}
				
/*				String regexNews = Pattern.quote("<a title=") + ".+" + Pattern.quote("href=") + ".+" + Pattern.quote("<br />");
				Matcher mNews = Pattern.compile(regexNews).matcher(textRubric);
				while (mNews.find()) {
					log.debug(mNews.group());
					String strNews = mNews.group();
					String header = strNews.substring(10, strNews.indexOf("href=") - 2);
					log.debug(header);
					String urlNews = "http://www.e1.ru" + strNews.substring(strNews.indexOf("href=") + 6, strNews.indexOf("><") - 1);
					int id_news = Integer.parseInt(urlNews.substring(urlNews.indexOf("id-") + 3, urlNews.indexOf("-section")));
					String textNews = connect(urlNews);
					log.debug(textNews);
					textNews = textNews.substring(textNews.indexOf("<div class="));
					textNews = textNews.substring(0, textNews.indexOf("</div>"));
					String body = textNews.substring(textNews.indexOf("/>"));
					String dateNews = strNews.substring(strNews.indexOf("small_gray") + 12, strNews.indexOf("</span>"));
					log.debug(dateNews);
					Date date = Date.valueOf(dateNews.substring(6, 10) + "." + dateNews.substring(3, 5) + "." + dateNews.substring(0, 2));
					News news = new News(id_news, date, rubric, body, header);
					log.debug(date.toString());
				}
				mNews.reset(); */
			}
			}
		}
		m.reset();
		log.debug(listNews.size());
	}
}
