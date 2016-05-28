package ru.acs.sod.model.hsqldb;

import oracle.xdb.XMLType;

import org.hibernate.dialect.HSQLDialect;

public class HSQLUserDialect extends HSQLDialect {

    public HSQLUserDialect() {
        super();
        registerHibernateType(XMLType._SQL_TYPECODE, "varchar($l)");
        registerColumnType(XMLType._SQL_TYPECODE, "varchar($l)");
    }
}
