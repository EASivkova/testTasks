package ru.collector.resume.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.collector.resume.dao.interfaces.ResumeDao;
import ru.collector.resume.model.Resume;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(value = "classpath:applicationTestContext.xml")
public class ResumeDAOTest {
 
    @Autowired
    private ResumeDao resumeDao;
    private Date date;
    
    @Before
    @Test
    public void init() throws ParseException {
    	date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2014-12-25T22:38:49");
    }
 
    @Test
    public void getResumeTest() throws ParseException {
    	Resume resume = new Resume();
    	resume.setId("58948262");
    	resume.setProfession("Программист");
    	resume.setMod_date(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2014-12-15T22:38:49"));
    	resume.setSalary("не указана");
    	resumeDao.saveOrUpdate(resume);
    	Resume resumeDB = resumeDao.getById(resume.getId());
        Assert.assertEquals("Программист", resumeDB.getProfession());
    }

    @Test
    public void listTest() throws ParseException {
    	Resume resume = new Resume();
    	resume.setId("58948262");
    	resume.setProfession("Программист");
    	resume.setMod_date(date);
    	resume.setSalary("не указана");
    	resumeDao.saveOrUpdate(resume);
    	Resume resume2 = new Resume();
    	resume2.setId("58948263");
    	resume2.setProfession("Аналитик");
    	resume2.setMod_date(date);
    	resumeDao.saveOrUpdate(resume2);
        List<Resume> resumes = resumeDao.listByDate(date);
        Assert.assertNotNull(resumes);
        Assert.assertEquals(2, resumes.size());
    }
 
    @Test
    public void listDatesTest() throws ParseException {
    	Resume resume = new Resume();
    	resume.setId("58948262");
    	resume.setMod_date(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2014-12-25T22:38:49"));
    	resumeDao.saveOrUpdate(resume);
    	Resume resume2 = new Resume();
    	resume2.setId("58948263");
    	resume2.setMod_date(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2014-12-25T23:38:49"));
    	resumeDao.saveOrUpdate(resume2);
    	Resume resume3 = new Resume();
    	resume3.setId("58948264");
    	resume3.setMod_date(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2014-12-26T22:00:49"));
    	resumeDao.saveOrUpdate(resume3);
        List<String> dates = resumeDao.listPublicDates();
        Assert.assertNotNull(dates);
        Assert.assertEquals(2, dates.size());
    }
 
}
