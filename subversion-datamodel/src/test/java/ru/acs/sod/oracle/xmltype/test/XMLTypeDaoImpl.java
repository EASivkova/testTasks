package ru.acs.sod.oracle.xmltype.test;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class XMLTypeDaoImpl extends HibernateDaoSupport implements XMLTypeDao {

    public Long addXMLTable(XMLTable table) {
        return  (Long) getHibernateTemplate().save(table);
    }

    public XMLTable getXMLTableById(Long id) {
        return getHibernateTemplate().get(XMLTable.class, id);
    }
}
