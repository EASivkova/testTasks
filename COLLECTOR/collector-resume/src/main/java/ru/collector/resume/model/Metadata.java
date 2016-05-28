package ru.collector.resume.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {

    @JsonProperty("resultset")
	private ResultSet resultSet;
	
	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

}
