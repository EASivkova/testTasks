package ru.country.info.dao;

import java.math.BigDecimal;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ru.country.info.model.Country;
import ru.country.info.model.Log;

public class LogDaoImpl extends HibernateDaoSupport implements LogDao {

	private static final long serialVersionUID = 1L;

	public Country getCountryByName(String name) {
        return (Country) DataAccessUtils.singleResult(getHibernateTemplate().find(
                "from Country c where LOWER(c.name) like ?", name.toLowerCase()));
	}

	public String getNextIdSession() {
		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {
            public String doInHibernate(Session session) throws HibernateException {
                SQLQuery sq = session.createSQLQuery("SELECT seqSession.nextval FROM dual");
                if (sq.uniqueResult() instanceof Integer) {
                    return ((Integer) sq.uniqueResult()).toString();
                } else {
                    return ((BigDecimal) sq.uniqueResult()).toString();
                }
            }
        });
	}
	
	public void saveOrUpdate(Log log) {
        getHibernateTemplate().saveOrUpdate(log);
	}
}
