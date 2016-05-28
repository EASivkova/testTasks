package ru.acs.sod.oracle.xmltype.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;

@Entity(name = "XML_DATA_TABLE")
public class XMLTable {

	@Id
	@SequenceGenerator(name = "seqDataBlock", sequenceName = "SEQ_DATA_BLOCK")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seqDataBlock")
	@Column(name = "ID")
	private Long id;

	@Lob
	@Column(name = "XML_DATA")
	@Type(type = "ru.acs.sod.model.oracle.OracleXMLType")
	private byte[] xmlData;

	public XMLTable() {
		super();
	}

	public XMLTable(byte[] xmlData) {
		this.xmlData = xmlData;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getXmlData() {
		return xmlData;
	}

	public void setXmlData(byte[] xmlData) {
		this.xmlData = xmlData;
	}
}
