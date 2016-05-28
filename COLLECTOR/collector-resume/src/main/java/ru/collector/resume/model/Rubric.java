package ru.collector.resume.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "RUBRIC")
public class Rubric implements Serializable {

	private static final long serialVersionUID = 1L;

    @JsonProperty("id")
	@Id
    @Column(name = "ID")
    private String id;

    @JsonProperty("title")
    @Column(name = "TITLE")
	private String title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
