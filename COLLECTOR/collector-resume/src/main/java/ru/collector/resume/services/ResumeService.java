package ru.collector.resume.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ru.collector.resume.dao.interfaces.ResumeDao;
import ru.collector.resume.model.Resume;
import ru.collector.resume.model.ResumeDTO;

/**
 * @author sea
 */
@Component
@Scope("request")
@Path("resume")
public class ResumeService {
	
    private static final Logger LOG = Logger.getLogger(ResumeService.class);

    private static final String TIME_FORMAT = "yyyy-MM-dd";
	private ResumeDao resumeDAO;

    @GET
    @Path("list.json")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true)
    public List<ResumeDTO> list(@QueryParam("publicDate") String publicDate) {
    	List<ResumeDTO> listResumeDTO = new ArrayList<ResumeDTO>();
		try {
			Date date = new SimpleDateFormat(TIME_FORMAT).parse(publicDate);
			List<Resume> listResume = resumeDAO.listByDate(date);
			for (final Resume r : listResume) {
				listResumeDTO.add(new ResumeDTO() {
					{
						setId(r.getId());
						setProfession(r.getProfession());
						setSalary(r.getSalary());
						setEducation(r.getEducation().getTitle());
					}
				});
			}
		} catch (ParseException e) {
			LOG.error("Ошибка разбора даты при загрузке списка вакансий.", e);;
		}
		return listResumeDTO;
    }

    @GET
    @Path("publicDates.json")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true)
    public List<String> getPublicDates() {
		return resumeDAO.listPublicDates();
    }

    @GET
    @Path("getById.json")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true)
    public ResumeDTO getById(@QueryParam("idResume") String id) {
    	Resume r = resumeDAO.getById(id);
    	ResumeDTO dto = new ResumeDTO();
		dto.setId(r.getId());
		dto.setAge(r.getAge().toString());
		dto.setFio(r.getContact().getName());
		dto.setContact(r.getContact().getEmail());
		dto.setProfession(r.getProfession());
		dto.setSalary(r.getSalary());
		dto.setEducation(r.getEducation().getTitle());
		dto.setSkills(r.getBlobSkills().toString());
        return dto;
    }

    @Required
    @Autowired
    public void setResumeDAO(ResumeDao resumeDAO) {
        this.resumeDAO = resumeDAO;
    }

}
