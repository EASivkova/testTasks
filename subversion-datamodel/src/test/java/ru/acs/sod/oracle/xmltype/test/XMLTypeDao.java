package ru.acs.sod.oracle.xmltype.test;

public interface XMLTypeDao {
    Long addXMLTable(XMLTable table);

    XMLTable getXMLTableById(Long id);
}
