package ru.collector.resume.test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;

import ru.collector.resume.CollectorResumes;
import ru.collector.resume.dao.interfaces.ResumeDao;
import ru.collector.resume.model.Resume;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(value = "classpath:applicationTestContext.xml")
public class ParserTest {

    @Autowired
    private ResumeDao resumeDao;

    @Test
    public void parserTest() {
    	CollectorResumes collector = new CollectorResumes();
    	collector.setResumeDao(resumeDao);
    	InputStream responce = Thread.currentThread().getContextClassLoader().getResourceAsStream("exampleResponse/response");
    	try {
			collector.process(responce);
		} catch (JsonParseException e) {
			System.err.print("Ошибка парсинга JSON" + e);
		} catch (IOException e) {
			System.err.print(e);
		}
    	Resume resumeDB = resumeDao.getById("58948262");
        Assert.assertNotNull(resumeDB);
        Assert.assertEquals("Помощница по дому", resumeDB.getProfession());
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2014-12-25T22:38:49");
	    	List<Resume> resumes = resumeDao.listByDate(date);
	        Assert.assertEquals(100, resumes.size());
		} catch (ParseException e) {
			System.err.print(e);
		}
    }

	public void setResumeDao(ResumeDao resumeDao) {
		this.resumeDao = resumeDao;
	}
    
}
