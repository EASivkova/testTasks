package ru.collector.resume.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class Contact implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("name")
    @Column(name = "NAME")
	private String name;
    
    @JsonProperty("email")
    @Column(name = "EMAIL")
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
