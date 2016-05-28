package vacancy.parser;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import vacancy.dao.VacancyDao;
import vacancy.entities.*;

public class E1 {

	private static final Logger log = Logger.getLogger(E1.class);
	
	Set<Vacancy> listVacancy = new HashSet<Vacancy>();
	
	public E1() {
		String text = "";
		try {
			text = connect("http://www.e1.ru/business/job/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.debug("Ошибка подключения к сайту или загрузки данных.\n" + e.toString());
		}
		if (!text.equals("") && text != null) {
			Set<Sphere> listSphere = parseSphere(text);
			for (Sphere sphere : listSphere) {
				ExecutorService exec = Executors.newCachedThreadPool();
				exec.execute(new LoadSphere(sphere));
			    exec.shutdown();
			 
/*				int i = 0;
				while (i < 100) {
					ExecutorService exec = Executors.newCachedThreadPool();
					exec.execute(new LoadPage(sphere, parametrs));
				    exec.shutdown();
					i++;
				} */

/*				text = "";
				// http://www.e1.ru/business/job/vacancy.search.php?search=yes&amp;section=15
				// http://www.e1.ru/business/job/vacancy.search.php?section=23&sex=l&search_by=1&show_for=30&search=yes&page=0
				// http://www.e1.ru/business/job/vacancy.search.php?section=23&sex=l&search_by=1&show_for=30&search=yes&page=1
				// http://www.e1.ru/business/job/vacancy.search.php?section=23&sex=l&search_by=1&show_for=30&search=yes&page=2
				int i = 0;
				boolean next = true;
				while (next) {
					try {
						text = connect("http://www.e1.ru/business/job/vacancy.search.php", "section=" + sphere.getIdsphere() + "&sex=l&search_by=1&show_for=30&search=yes&page=" + i);
						if (!text.equals("") && text != null)
							parsePageIndex(text, sphere);
						i++;
					} catch (IOException e) {
						next = false;
					}
				} */
			}
/*			for (Vacancy vacancy : listVacancy) {
				try {
					text = connect("http://www.e1.ru/business/job/vacancy.detail.php", "id=" + vacancy.getId());
					parseVacancy(vacancy, text);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.debug("Ошибка подключения к сайту (вакансия) или загрузки данных.\n" + e.toString());
				}
			} */
		}
	}

	// подключение к сайту
    private String connect(String url) throws IOException {
    	String text = "";
        	URL dataURL = new URL(url);
            URLConnection conn = dataURL.openConnection();
            conn.setUseCaches(false);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "Cp1251"));
            String line;
            while((line = in.readLine()) != null)
            	text += line+"\n";
            return text;
	}
    
	// подключение к сайту
/*    private String connect(String url, String parametrs) throws IOException {
        	URL dataURL = new URL(url);
            URLConnection conn = dataURL.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(100);
            PrintWriter out = new PrintWriter(byteStream,true);
            out.print(parametrs);
            out.flush();
            conn.setRequestProperty("Content-Length", String.valueOf(byteStream.size()));
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            byteStream.writeTo(conn.getOutputStream());
        	String text = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "Cp1251"));
            String line;
            while((line=in.readLine())!=null)
            	text += line+"\n";
            return text;
	} */
    
    // составление списка отраслей (гиперссылки)
    private Set<Sphere> parseSphere(String text) {
    	VacancyDao dao = new VacancyDao();
    	Set<Sphere> listSphere = new HashSet<Sphere>();
		String regex = Pattern.quote("<a href=\"http://www.e1.ru/business/job/vacancy.search.php?search=yes&amp;section=") + "(.+)" + Pattern.quote("</a>");
		Matcher m = Pattern.compile(regex).matcher(text);
		while (m.find()) {
			String str = m.group();
			int idSphere = Integer.parseInt(str.substring(str.indexOf("section=") + 8, str.indexOf("\">")));
			Sphere sphere = dao.getSphere(idSphere);
			if (sphere == null) {
				sphere = new Sphere();
				sphere.setIdsphere(idSphere);
				String name;
				if (str.indexOf("<strong>") > -1)
					name = str.substring(str.indexOf("\">") + 10, str.indexOf("</"));
				else
					name = str.substring(str.indexOf("\">") + 2, str.indexOf("</"));
				sphere.setName(name);
				dao.addSphere(sphere);
			}
			listSphere.add(sphere);
		}
		m.reset();
    	return listSphere;
    }
    
    // разбор страницы со списком вакансий (составление списка id вакансий)
/*    private void parsePageIndex(String text, Sphere sphere) {
    	VacancyDao dao = new VacancyDao();
		String strDate = Calendar.getInstance().getTime().toString();
		String day = strDate.substring(0, 2);
		String month = "";
		String[] months_en = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		int numMonth = 0;
		for (int i = 0; i < months_en.length; i++) {
			String m = strDate.substring(4, 7);
			if (months_en[i].equals(m)) {
				month = ((i < 9)?"0":"") + (i + 1);
				numMonth = i;
			}
		}
		String[] months_ru = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
		String regex = Pattern.quote("href=\"/business/job/vacancy.detail.php?id=") + "(.+)" + Pattern.quote(day + " " + months_ru[numMonth] + "</nobr></td>");
		Date dat = Date.valueOf(strDate.substring(6, 10) + "-" + month + "-" + day);
		Matcher m = Pattern.compile(regex).matcher(text);
		while (m.find()) {
			String str = m.group();
			Vacancy vacancy = new Vacancy();
			vacancy.setDat(dat);
			int id = Integer.parseInt(str.substring(str.indexOf("id=") + 3, str.indexOf("\" class=\"big\" onclick=\"show_vacancy(")));
			vacancy.setId(id);
			String professions = str.substring(str.indexOf("<a href='vacancy.search.php?search=yes&section=" + sphere.getIdsphere() + "&spec[]="), str.indexOf("</td>"));
			String[] prof = professions.split(", ");
			for (String p : prof) {
				Profession profession = new Profession();
				profession.setSphere(sphere);
				String name = p.substring(p.indexOf(">") + 1, p.indexOf("</a>"));
				profession.setName(name);
				int idprofession = Integer.parseInt(p.substring(p.indexOf("spec[]=") + 7, p.indexOf(" class=") - 1));
				profession.setIdprofession(idprofession);
				dao.addProfession(profession);
				vacancy.getProfessions().add(profession);
			}
			listVacancy.add(vacancy);
		}
		m.reset();
    } */
    
    // разбор страницы с описанием вакансии
/*    private void parseVacancy(Vacancy vacancy, String text) {
		log.debug(vacancy.getId());
    	VacancyDao dao = new VacancyDao();
    	text = text.substring(text.indexOf("Вакансия:"), text.indexOf("Все вакансии этого работодателя"));
		String post = text.substring(text.indexOf("<span class=\"big\">") + 18, text.indexOf("</span>"));
		vacancy.setPost(post);
		String descEmployment = text.substring(text.indexOf("Занятость:"), text.indexOf("</tr>", text.indexOf("Занятость:")));
		descEmployment = descEmployment.substring(descEmployment.indexOf("top") + 5, descEmployment.indexOf("</td>", descEmployment.indexOf("top")));
		Employment employment = dao.getEmployment(descEmployment);
		if (employment == null) {
			employment = new Employment();
			employment.setIdemployment(dao.listEmployment().size() + 1);
			employment.setDescription(descEmployment);
			dao.addEmployment(employment);
		}
		vacancy.setEmployment(employment);
		String nameCity = text.substring(text.indexOf("Город:"), text.indexOf("</tr>", text.indexOf("Город:")));
		nameCity = nameCity.substring(nameCity.indexOf("top") + 5, nameCity.indexOf("</td>", nameCity.indexOf("top")));
		City city = dao.getCity(nameCity);
		if (city == null) {
			city = new City();
			city.setIdcity(dao.listCity().size() + 1);
			city.setName(nameCity);
			dao.addCity(city);
		}
		vacancy.setCity(city);
		String descShedule = text.substring(text.indexOf("График работы:"), text.indexOf("</tr>", text.indexOf("График работы:")));
		descShedule = descShedule.substring(descShedule.indexOf("top") + 5, descShedule.indexOf("</td>", descShedule.indexOf("top")));
		Shedule shedule = dao.getShedule(descShedule);
		if (shedule == null) {
			shedule = new Shedule();
			shedule.setIdshedule(dao.listShedule().size() + 1);
			shedule.setDescription(descShedule);
			dao.addShedule(shedule);
		}
		vacancy.setShedule(shedule);
		String descEducation = text.substring(text.indexOf("Образование:"), text.indexOf("</tr>", text.indexOf("Образование:")));
		descEducation = descEducation.substring(descEducation.indexOf("top") + 5, descEducation.indexOf("</td>", descEducation.indexOf("top")));
		Education education = dao.getEducation(descEducation);
		if (education == null) {
			education = new Education();
			education.setIdeducation(dao.listShedule().size() + 1);
			education.setDescription(descEducation);
			dao.addEducation(education);
		}
		vacancy.setEducation(education);
		String experience = text.substring(text.indexOf("Опыт работы:"), text.indexOf("</tr>", text.indexOf("Опыт работы:")));
		experience = experience.substring(experience.indexOf("top") + 5, experience.indexOf("</td>", experience.indexOf("top")));
		vacancy.setExperience(experience);
		String desires = text.substring(text.indexOf("<td colspan=\"2\" valign=\"top\">"), text.indexOf("</td>", text.indexOf("<td colspan=\"2\" valign=\"top\">")));
		desires = text.substring(text.indexOf(">") + 1);
		vacancy.setDesires(desires);
		String nameEmployer = text.substring(text.indexOf("Работодатель:"), text.indexOf("</tr>", text.indexOf("Работодатель:")));
		nameEmployer = nameEmployer.substring(nameEmployer.indexOf("top") + 5, nameEmployer.indexOf("</td>", nameEmployer.indexOf("top")));
		Employer employer = dao.getEmployer(nameEmployer);
		if (employer == null) {
			employer = new Employer();
			employer.setIdemployer(dao.listEmployer().size() + 1);
			employer.setName(nameEmployer);
			String contact = text.substring(text.indexOf("Контактная информация:"), text.indexOf("</tr>", text.indexOf("Контактная информация:")));
			String telephone = contact.substring(contact.indexOf("Телефон:"), contact.indexOf("</td>"));
			contact = contact.substring(contact.indexOf("<br>") + 4, contact.indexOf("<strong>"));
			employer.setContact(contact);
			telephone = contact.substring(contact.indexOf("</strong>") + 10);
			employer.setTelephone(telephone.trim());
			String type = text.substring(text.indexOf("Вакансия предоставлена:"), text.indexOf("</tr>", text.indexOf("Вакансия предоставлена:")));
			type = type.substring(type.indexOf("top") + 5, type.indexOf("</td>", type.indexOf("top")));
			employer.setType(type);
			employer.setName(nameEmployer);
			dao.addEmployer(employer);
		}
		vacancy.setEmployer(employer);
    	dao.addVacancy(vacancy);
    } */
    
}
