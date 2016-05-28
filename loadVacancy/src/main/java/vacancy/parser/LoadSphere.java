package vacancy.parser;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import vacancy.dao.VacancyDao;
import vacancy.entities.City;
import vacancy.entities.Education;
import vacancy.entities.Employer;
import vacancy.entities.Employment;
import vacancy.entities.Profession;
import vacancy.entities.Shedule;
import vacancy.entities.Sphere;
import vacancy.entities.Vacancy;

public class LoadSphere implements Runnable {
	
	private static final Logger log = Logger.getLogger(LoadSphere.class);
	private Sphere sphere;
	private Set<Vacancy> listVacancy = new HashSet<Vacancy>();
	private boolean next = true;

	public LoadSphere(Sphere sphere) {
		// TODO Auto-generated constructor stub
		this.sphere = sphere;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String text = "";
		int i = 0;
		while (next) {
			try {
				text = connect("http://www.e1.ru/business/job/vacancy.search.php", "section=" + sphere.getIdsphere() + "&sex=l&search_by=1&show_for=30&search=yes&page=" + i);
				if (!text.equals("") && text != null)
					parsePageIndex(text);
				i++;
			} catch (IOException e) {
				log.debug(sphere.getIdsphere() + " " + e.toString());
				System.out.println(sphere.getIdsphere() + " " + e.toString());
				next = false;
			}
		}
		for (Vacancy vacancy : listVacancy) {
			try {
				text = connect("http://www.e1.ru/business/job/vacancy.detail.php", "id=" + vacancy.getId());
				parseVacancy(vacancy, text);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.debug("Ошибка подключения к сайту (вакансия) или загрузки данных.\n" + e.toString());
			}
		}
	}

	private void parsePageIndex(String text) {
		text = text.substring(text.indexOf("Список найденных вакансий:"));
		VacancyDao dao = new VacancyDao();
		String strDate = Calendar.getInstance().getTime().toString();
		String day = strDate.substring(8, 10);
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
		int begin = text.indexOf("<div id=");
		int start, end;
		int iter = 0;
		while((start = text.indexOf("\"/business/job/vacancy.detail.php?id=", begin)) > -1 && (end = text.indexOf(day + " " + months_ru[numMonth] + "</nobr></td>", start)) > -1) {
			String str = text.substring(start, end);
			Date dat = Date.valueOf(strDate.substring(strDate.length() - 4) + "-" + month + "-" + day);
			Vacancy vacancy = new Vacancy();
			vacancy.setDat(dat);
			int id = Integer.parseInt(str.substring(str.indexOf("id=") + 3, str.indexOf("\" class=\"big\" onclick=\"show_vacancy(")));
			vacancy.setId(id);
			if (str.indexOf("<a href='vacancy.search.php?search=yes&section=" + sphere.getIdsphere() + "&spec[]=") > -1) {
				String professions = str.substring(str.indexOf("<a href='vacancy.search.php?search=yes&section=" + sphere.getIdsphere() + "&spec[]="), str.indexOf("</td>"));
				String[] prof = professions.split(", <");
				for (String p : prof) {
					String idprofession = sphere.getIdsphere() + "_" + p.substring(p.indexOf("spec[]=") + 7, p.indexOf(" class=") - 1);
					Profession profession = dao.getProfession(idprofession);
					if (profession == null) {
						profession = new Profession();
						profession.setSphere(sphere);
						String name = p.substring(p.indexOf(">") + 1, p.indexOf("</a>"));
						profession.setName(name);
						profession.setIdprofession(idprofession);
						dao.addProfession(profession);
					}
					vacancy.getProfessions().add(profession);
				}
			} else {
				Profession profession = dao.getProfession("" + sphere.getIdsphere());
				if (profession == null) {
					profession = new Profession();
					profession.setSphere(sphere);
					profession.setIdprofession("" + sphere.getIdsphere());
					dao.addProfession(profession);
				}
				vacancy.getProfessions().add(profession);
			}
			listVacancy.add(vacancy);
			begin = end;
			iter++;
		}
		if (iter < 10) {
			next = false;
		}
	}

	// подключение к сайту
    private String connect(String url, String parametrs) throws IOException {
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
	}
    
    // разбор страницы с описанием вакансии
    private void parseVacancy(Vacancy vacancy, String text) {
    	VacancyDao dao = new VacancyDao();
    	text = text.substring(text.indexOf("Вакансия:"), text.indexOf("<TD vAlign=top align=left width=18 background=/images/lines/_line.gif>"));
		String post = text.substring(text.indexOf("<span class=\"big\">") + 18, text.indexOf("</span>"));
		vacancy.setPost(post);
		String descEmployment = text.substring(text.indexOf("Занятость:"), text.indexOf("</tr>", text.indexOf("Занятость:")));
		descEmployment = descEmployment.substring(descEmployment.indexOf("top") + 6, descEmployment.indexOf("</td>", descEmployment.indexOf("top")));
		Employment employment = dao.getEmployment(descEmployment.trim());
		if (employment == null) {
			employment = new Employment();
			employment.setIdemployment(dao.listEmployment().size() + 1);
			employment.setDescription(descEmployment.trim());
			dao.addEmployment(employment);
		}
		vacancy.setEmployment(employment);
		String nameCity = text.substring(text.indexOf("Город:"), text.indexOf("</tr>", text.indexOf("Город:")));
		nameCity = nameCity.substring(nameCity.indexOf("top") + 5, nameCity.indexOf("</td>", nameCity.indexOf("top")));
		City city = dao.getCity(nameCity.trim());
		if (city == null) {
			city = new City();
			city.setIdcity(dao.listCity().size() + 1);
			city.setName(nameCity.trim());
			dao.addCity(city);
		}
		vacancy.setCity(city);
		String descShedule = text.substring(text.indexOf("График работы:"), text.indexOf("</tr>", text.indexOf("График работы:")));
		descShedule = descShedule.substring(descShedule.indexOf("top") + 6, descShedule.indexOf("</td>", descShedule.indexOf("top")));
		Shedule shedule = dao.getShedule(descShedule.trim());
		if (shedule == null) {
			shedule = new Shedule();
			shedule.setIdshedule(dao.listShedule().size() + 1);
			shedule.setDescription(descShedule.trim());
			dao.addShedule(shedule);
		}
		vacancy.setShedule(shedule);
		String descEducation = text.substring(text.indexOf("Образование:"), text.indexOf("</tr>", text.indexOf("Образование:")));
		descEducation = descEducation.substring(descEducation.indexOf("top") + 6, descEducation.indexOf("</td>", descEducation.indexOf("top")));
		Education education = dao.getEducation(descEducation.trim());
		if (education == null) {
			education = new Education();
			education.setIdeducation(dao.listShedule().size() + 1);
			education.setDescription(descEducation.trim());
			dao.addEducation(education);
		}
		vacancy.setEducation(education);
		String experience = text.substring(text.indexOf("Опыт работы:"), text.indexOf("</tr>", text.indexOf("Опыт работы:")));
		experience = experience.substring(experience.indexOf("top") + 6, experience.indexOf("</td>", experience.indexOf("top")));
		vacancy.setExperience(experience.trim());
		String desires = text.substring(text.indexOf("<td colspan=\"2\" valign=\"top\">"), text.indexOf("</t", text.indexOf("<td colspan=\"2\" valign=\"top\">")));
		desires = desires.substring(desires.indexOf(">") + 1);
		vacancy.setDesires(desires.trim());
		String nameEmployer = text.substring(text.indexOf("Работодатель:"), text.indexOf("</tr>", text.indexOf("Работодатель:")));
		nameEmployer = nameEmployer.substring(nameEmployer.indexOf("top") + 6, nameEmployer.indexOf("</td>", nameEmployer.indexOf("top")));
		Employer employer = dao.getEmployer(nameEmployer.trim());
		if (employer == null) {
			employer = new Employer();
			employer.setIdemployer(dao.listEmployer().size() + 1);
			employer.setName(nameEmployer.trim());
			String contact = text.substring(text.indexOf("Контактная информация:"), text.indexOf("</t", text.indexOf("Контактная информация:")));
			String telephone = contact.substring(contact.indexOf("Телефон:"));
			if (telephone.indexOf("<br") > -1)
				telephone = telephone.substring(telephone.indexOf("</strong>") + 10, telephone.indexOf("<br"));
			else
				telephone = telephone.substring(contact.indexOf("</strong>") + 10);
			employer.setTelephone(telephone.trim());
			contact = contact.substring(contact.indexOf("<br"), contact.indexOf(","));
			contact = contact.substring(contact.indexOf(">") + 1);
			employer.setContact(contact.trim());
			String type = text.substring(text.indexOf("Вакансия предоставлена:"), text.indexOf("</tr>", text.indexOf("Вакансия предоставлена:")));
			type = type.substring(type.indexOf("top") + 6, type.indexOf("</td>", type.indexOf("top")));
			if (type.indexOf("предоставлена:") > -1)
				type = type.substring(type.indexOf(":") + 1);
			employer.setType(type.trim());
			employer.setName(nameEmployer.trim());
			dao.addEmployer(employer);
		}
		vacancy.setEmployer(employer);
    	dao.addVacancy(vacancy);
    }
    
}
