package ru.parser;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import ru.parser.utils.Type;

@Entity
@Table(name = "RECORD")
public class Record implements DomainObject {

	/** UID для сериализации объекта. */
	private static final long serialVersionUID = 1L;
	/** Сурогатный ключ, генерится из сиквенса базы данных. */
	@Id
	@SequenceGenerator(name = "seqRec", sequenceName = "SEQ_REC", allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seqRec")
	@Column(name = "ID")
	private Long id;
	
    /** Тип аннотированного объекта: класс или метод */
    @Column(name = "TYPE")
    private Type type;
    
    /** Дата внесения изменений. */
    @Column(name = "DATE_CHANGE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeDate;

	/**
	 * Причина внесения изменений.
	 */
	@Column(name = "REASON", length = 250)
	private String reason;
	
	/**
	 * Автор изменений.
	 */
	@Column(name = "AUTHOR", length = 100)
	private String author;

	/**
	 * Имя объета к которому относятся изменения.
	 */
	@Column(name = "NAME", length = 100)
	private String name;

	/** Конструктор по умолчанию. */
	public Record() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
