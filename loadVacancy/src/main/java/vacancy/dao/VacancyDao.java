package vacancy.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import vacancy.entities.*;
import vacancy.utils.HibernateUtil;

public class VacancyDao {

	public void addVacancy(Vacancy vacancy) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(vacancy);
        session.getTransaction().commit();
	}

	public Vacancy getVacancy(Integer idVacancy) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Vacancy result = (Vacancy) session.load(Vacancy.class, idVacancy);
        session.getTransaction().commit();
        return result;
	}

	public List<Vacancy> listVacancy() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Vacancy> result = session.createQuery("from Vacancy").list();
        for (Vacancy v : result) {
            Hibernate.initialize(v.getCity());
            Hibernate.initialize(v.getEducation());
            Hibernate.initialize(v.getEmployer());
            Hibernate.initialize(v.getEmployment());
            Hibernate.initialize(v.getShedule());
            Hibernate.initialize(v.getProfessions());
        }
        session.getTransaction().commit();
        return result;
	}

	public City getCity(String name) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<City> list = session.createQuery("from City c where c.name = ?").setParameter(0, name).list();
        if (list.size() > 0) {
        	City result = list.get(0);
	        session.getTransaction().commit();
	        return result;
        } else {
        	return null;
        }
	}

	public List<City> listCity() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<City> result = session.createQuery("from City").list();
        session.getTransaction().commit();
        return result;
	}

	public void addCity(City city) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(city);
        session.getTransaction().commit();
	}

	public Sphere getSphere(Integer idSphere) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Sphere result = (Sphere) session.load(Sphere.class, idSphere);
        session.getTransaction().commit();
        return result;
	}

	public List<Sphere> listSphere() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Sphere> result = session.createQuery("from Sphere").list();
        for (Sphere s : result) {
            Hibernate.initialize(s.getProfessions());
        }
        session.getTransaction().commit();
        return result;
	}

	public void addSphere(Sphere sphere) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(sphere);
        session.getTransaction().commit();
	}

	public Shedule getShedule(String description) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Shedule> list = session.createQuery("from Shedule s where s.description = ?").setParameter(0, description).list();
        if (list.size() > 0) {
        	Shedule result = list.get(0);
	        session.getTransaction().commit();
	        return result;
        } else {
        	return null;
        }
	}

	public List<Shedule> listShedule() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Shedule> result = session.createQuery("from Shedule").list();
        session.getTransaction().commit();
        return result;
	}

	public void addShedule(Shedule shedule) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(shedule);
        session.getTransaction().commit();
	}

	public Education getEducation(String description) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Education> list = session.createQuery("from Education e where e.description = ?").setParameter(0, description).list();
        if (list.size() > 0) {
        	Education result = list.get(0);
	        session.getTransaction().commit();
	        return result;
        } else {
        	return null;
        }
	}

	public List<Education> listEducation() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Education> result = session.createQuery("from Education").list();
        session.getTransaction().commit();
        return result;
	}

	public void addEducation(Education education) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(education);
        session.getTransaction().commit();
	}

	public Employer getEmployer(String name) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Employer> list = session.createQuery("from Employer e where e.name = ?").setParameter(0, name).list();
        if (list.size() > 0) {
        	Employer result = list.get(0);
	        session.getTransaction().commit();
	        return result;
        } else {
        	return null;
        }
	}

	public List<Employer> listEmployer() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Employer> result = session.createQuery("from Employer").list();
        session.getTransaction().commit();
        return result;
	}

	public void addEmployer(Employer employer) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(employer);
        session.getTransaction().commit();
	}

	public Employment getEmployment(String description) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Employment> list = session.createQuery("from Employment e where e.description = ?").setParameter(0, description).list();
        if (list.size() > 0) {
        	Employment result = list.get(0);
	        session.getTransaction().commit();
	        return result;
        } else {
        	return null;
        }
	}

	public List<Employment> listEmployment() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Employment> result = session.createQuery("from Employment").list();
        session.getTransaction().commit();
        return result;
	}

	public void addEmployment(Employment employment) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(employment);
        session.getTransaction().commit();
	}

	public Profession getProfession(String id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Profession> list = session.createQuery("from Profession p where p.idprofession = ?").setParameter(0, id).list();
        if (list.size() > 0) {
        	Profession result = list.get(0);
	        session.getTransaction().commit();
	        return result;
        } else {
        	return null;
        }
	}

	public List<Profession> listProfession() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Profession> result = session.createQuery("from Profession").list();
        session.getTransaction().commit();
        return result;
	}

	public void addProfession(Profession profession) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(profession);
        session.getTransaction().commit();
	}

}
