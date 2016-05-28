package ru.collector.resume.dao.interfaces;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import ru.collector.resume.model.Resume;

public interface ResumeDao extends Serializable {
	
	List<Resume> listByDate(Date date) throws ParseException;
	
	List<String> listPublicDates();
	
	Resume getById(String id);

    void saveOrUpdate(Object t);

}
