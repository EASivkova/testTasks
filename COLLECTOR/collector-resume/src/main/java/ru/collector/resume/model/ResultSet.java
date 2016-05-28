package ru.collector.resume.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultSet {
	
    @JsonProperty("count")
	private Long count;
	
    @JsonProperty("limit")
	private Long limit;
	
    @JsonProperty("offset")
	private Long offset;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

}
