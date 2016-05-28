package ru.collector.resume;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import ru.collector.resume.dao.interfaces.ResumeDao;
import ru.collector.resume.model.JsonObject;
import ru.collector.resume.model.Resume;

public class CollectorResumes {

    private static final Logger LOG = LoggerFactory.getLogger(CollectorResumes.class);
    
    private ResumeDao resumeDao;
    private String root;
    private Long count = 0L;
    private Long loadCount = null;

    public void initialize() {
        LOG.info("Start CollectorVacancies.");
    }
    
    public void upload() {
    	LOG.debug("Начало сбора резюме.");
    	String url = root + "?sort=date&period=today&is_new_only=1&limit=100";
    	for (int page = 0; loadCount == null || loadCount < count; page++) {
        	try {
        		if (page != 0) {
        			url += "&offset=" + page*100;
        		}
        		InputStream responce = connect(url);
        		process(responce);
    		} catch (IOException e) {
    	    	LOG.error(String.format("Ошибка сбора резюме с адреса: %s", url), e);
    		}
    	}
    	LOG.debug("Сбор резюме завершен.");
    }

	// подключение к сайту
    private InputStream connect(String url) throws IOException {
       	URL dataURL = new URL(url);
        URLConnection conn = dataURL.openConnection();
        conn.setUseCaches(false);
        return conn.getInputStream();
	}
    
    public void process(InputStream responce) throws JsonParseException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	JsonObject jsonObject = null;
    	try {
//    	    mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);            
    	    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    	    mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    	    mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
    	    mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
    	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	    mapper.setPropertyNamingStrategy(new PropertyNamingStrategy.PascalCaseStrategy());
    	    jsonObject = mapper.readValue(responce, JsonObject.class);
    	} catch(Exception e){
    	    LOG.error("Error fetching or parsing JSON: " + e);
    	}
    	if (jsonObject != null) {
    	    count = jsonObject.getMetadata().getResultSet().getCount();
    	    if (loadCount == null) {
        	    loadCount = (long) jsonObject.getResumes().size();
    	    } else {
        	    loadCount += jsonObject.getResumes().size();
    	    }
    	    for (Resume r : jsonObject.getResumes()) {
    	    	r.setBlobSkills(r.getSkills().getBytes());
    	    	resumeDao.saveOrUpdate(r);
    	    }
    	}
    }
    
    public void setResumeDao(ResumeDao resumeDao) {
		this.resumeDao = resumeDao;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

}
