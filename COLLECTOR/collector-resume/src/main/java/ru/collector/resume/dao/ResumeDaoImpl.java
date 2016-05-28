package ru.collector.resume.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ru.collector.resume.dao.interfaces.ResumeDao;
import ru.collector.resume.model.Resume;

public class ResumeDaoImpl extends HibernateDaoSupport implements ResumeDao {

    private static final long serialVersionUID = 1L;

	public List<Resume> listByDate(final Date date) throws ParseException {
		// 2014-12-25T22:38:49+05:00
		String strDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate.substring(0,10) + " 00:00:00");
		Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate.substring(0,10) + " 23:59:59");
        return getHibernateTemplate().find("from Resume r where r.mod_date >= ? and r.mod_date <= ?", start, end);
	}

	public List<String> listPublicDates() {
        return getHibernateTemplate().execute(new HibernateCallback<List<String>>() {
            public List doInHibernate(final Session session) throws SQLException {
                Query query = session
                        .createSQLQuery("select distinct trunc(r.MODIFICATION_DATE) from resume r");
                return query.list();
            }
        });
	}
	
	public Resume getById(final String id) {
		return getHibernateTemplate().get(Resume.class, id);
	}

    public void saveOrUpdate(final Object t) {
        getHibernateTemplate().saveOrUpdate(t);
    }

}
