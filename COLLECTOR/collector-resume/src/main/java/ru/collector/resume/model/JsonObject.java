package ru.collector.resume.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonObject {

	@JsonProperty("metadata")
	private Metadata metadata;

	@JsonProperty("resumes")
	private List<Resume> resumes;

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public List<Resume> getResumes() {
		return resumes;
	}

	public void setResumes(List<Resume> resumes) {
		this.resumes = resumes;
	}
	
}
