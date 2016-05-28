package ru.acs.sod.model.oracle;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.sql.OPAQUE;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import com.ibm.websphere.rsadapter.WSCallHelper;
import com.ibm.ws.rsadapter.jdbc.WSJdbcPreparedStatement;
import com.ibm.ws.rsadapter.jdbc.WSJdbcResultSet;

public class OracleXMLType implements UserType, Serializable {

    private static final Logger LOG = Logger.getLogger(OracleXMLType.class);

    private static final long serialVersionUID = 1L;
    private static final Class<?> returnedClass = byte[].class;
    private static final int[] SQL_TYPES = new int[] {
        XMLType._SQL_TYPECODE
    };

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }
        byte[] copyArray = new byte[((byte[]) value).length];
        System.arraycopy(value, 0, copyArray, 0, copyArray.length);
        return copyArray;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (byte[]) value;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        return Arrays.equals((byte[]) x, (byte[]) y);
    }

    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public boolean isMutable() {
        return true;
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        XMLType xmlType = null;
        try {
            String value = null;
            if (rs instanceof WSJdbcResultSet) {
                OPAQUE op = (OPAQUE) rs.getObject(names[0]);
                if (op != null) {
                    xmlType = XMLType.createXML(op);
                    value = xmlType.getStringVal();
                    LOG.debug("message from driver = " + value);
                }
            } else if (rs instanceof OracleResultSet) {
                OracleResultSet ors = (OracleResultSet) rs;
                OPAQUE op = ors.getOPAQUE(names[0]);
                if (op != null) {
                    xmlType = XMLType.createXML(op);
                    value = xmlType.getStringVal();
                }
            } else {
                value = rs.getString(names[0]);
            }
            byte[] data = null;
            if (value != null) {
                data = value.getBytes("windows-1251");
            }
            return data;
        } catch (UnsupportedEncodingException e) {
            throw new HibernateException("Unsupported encoding", e);
        } finally {
            if (null != xmlType) {
                xmlType.close();
            }
        }
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        try {
            if (st instanceof WSJdbcPreparedStatement) {
                XMLType xmlType = (XMLType) (WSCallHelper.jdbcPass(XMLType.class, "createXML", new Object[] {
                        st.getConnection(), value == null ? "" : new String((byte[]) value, "windows-1251")
                }, new Class[] {
                        Connection.class, String.class
                }, new int[] {
                        WSCallHelper.CONNECTION, WSCallHelper.IGNORE
                }));
                st.setObject(index, xmlType);
            } else if (st instanceof OraclePreparedStatement) {
                XMLType xmlType = null;
                if (value == null) {
                    xmlType = XMLType.createXML(st.getConnection(), "");
                } else {
                    xmlType = XMLType.createXML(st.getConnection(), new String((byte[]) value, "windows-1251"));
                }
                st.setObject(index, xmlType);
            } else {
                if (value != null) {
                    try {
                        st.setString(index, new String((byte[]) value, "windows-1251"));
                    } catch (UnsupportedEncodingException e) {
                        throw new HibernateException("Unsupported Encoding during save to ABO_SOD", e);
                    }
                } else {
                    st.setNull(index, XMLType._SQL_TYPECODE);
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new HibernateException("Unsupported Encoding during save to ABO_SOD", e);
        }
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }

    public Class<?> returnedClass() {
        return returnedClass;
    }

    public int[] sqlTypes() {
        return SQL_TYPES;
    }
}
