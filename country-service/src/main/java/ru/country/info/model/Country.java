package ru.country.info.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CITY")
public class Country implements Serializable{

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
	private Long id;
	
    @Column(name = "COUNTRY")
	private String name;
	
    @Column(name = "CAPITAL")
	private String capital;
	
    @Column(name = "CURRENCY")
	private String currency;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
