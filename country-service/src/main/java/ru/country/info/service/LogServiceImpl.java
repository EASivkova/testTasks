package ru.country.info.service;

import ru.country.info.dao.LogDao;
import ru.country.info.model.Country;
import ru.country.info.model.Log;

public class LogServiceImpl implements LogService {
	
	private LogDao dao;

	public Country getCountryByName(String name) {
		return dao.getCountryByName(name);
	}

	public Long getNextIdSession() {
		return Long.valueOf(dao.getNextIdSession());
	}
	
	public void saveOrUpdate(Log log) {
		dao.saveOrUpdate(log);
	}
	
	public void setDao(LogDao dao) {
		this.dao = dao;
	}

}
