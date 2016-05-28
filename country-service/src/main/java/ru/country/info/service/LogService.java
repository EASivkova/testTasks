package ru.country.info.service;

import ru.country.info.model.Country;
import ru.country.info.model.Log;

public interface LogService {

	Country getCountryByName(String name);

	Long getNextIdSession();

	void saveOrUpdate(Log log);

}
