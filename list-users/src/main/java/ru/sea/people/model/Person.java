package ru.sea.people.model;
import javax.persistence.Column;
import javax.persistence.Entity;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "PERSON")
public class Person implements Serializable {
	
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seqPerson", sequenceName = "SEQ_PERSON")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPerson")
    @Column(name = "ID")
	private Long id;
	
    @Column(name = "FIO")
	private String fio;
	
    @Column(name = "LOGIN")
	private String login;
	
    @Column(name = "PASSWORD")
	private String password;

	public Person() {
	}

	public Person(Long id, String fio, String login, String password) {
		this.id = id;
		this.fio = fio;
		this.login = login;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return new StringBuilder().append("ID:").append(id.longValue()).append(",")
				.append("LOGIN:").append(login).append(",")
				.append("FIO:").append(fio).toString();
	}
}
