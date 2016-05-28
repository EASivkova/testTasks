package ru.country.info.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "LOG")
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqLog")
    @SequenceGenerator(name = "seqLog", sequenceName = "seqLog")
    @Column(name = "ID")
	private Long id;
	
    @Column(name = "REGISTRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
	private Date registrationDate = new Date();
	
    @Column(name = "TYPE")
	private TypeRecord type;
	
    @Column(name = "ID_SESSION")
	private Long idSession;
	
    @Lob
    @Column(name = "TEXT")
    private byte[] text;
	
    @Column(name = "FROMM")
	private String from;
	
    @Column(name = "TOO")
	private String to;
	
    @Column(name = "STATUS")
	private Status status = Status.SUCCESS;
	
    @Column(name = "ERROR")
	private String descriptionError;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public TypeRecord getType() {
		return type;
	}

	public void setType(TypeRecord type) {
		this.type = type;
	}

	public Long getIdSession() {
		return idSession;
	}

	public void setIdSession(Long idSession) {
		this.idSession = idSession;
	}

	public byte[] getText() {
		return text;
	}

	public void setText(byte[] text) {
		this.text = text;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDescriptionError() {
		return descriptionError;
	}

	public void setDescriptionError(String descriptionError) {
		this.descriptionError = descriptionError;
	}

}
