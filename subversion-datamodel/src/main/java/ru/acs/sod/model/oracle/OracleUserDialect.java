package ru.acs.sod.model.oracle;

import oracle.xdb.XMLType;

import org.hibernate.dialect.Oracle10gDialect;

/**
 * Расширение диалекта оракла, для регистрации типа данных xmltype в понимании
 * hibernate.
 * @author vdm
 * @since 1.0.3
 */
public class OracleUserDialect extends Oracle10gDialect {

    public OracleUserDialect() {
        super();
        registerHibernateType(XMLType._SQL_TYPECODE, "xmltype");
        registerColumnType(XMLType._SQL_TYPECODE, "xmltype");
    }
}
