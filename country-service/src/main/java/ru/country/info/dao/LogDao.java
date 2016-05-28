package ru.country.info.dao;

import java.io.Serializable;

import ru.country.info.model.Country;
import ru.country.info.model.Log;

public interface LogDao extends Serializable {

	Country getCountryByName(String name);

	String getNextIdSession();

	void saveOrUpdate(Log log);

}
