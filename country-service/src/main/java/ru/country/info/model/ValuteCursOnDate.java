package ru.country.info.model;

public class ValuteCursOnDate {
	
	// Название валюты
	private String Vname;
	// Номинал
	private String Vnom;
	// Курс
	private String Vcurs;
	// Цифровой код валюты
	private String Vcode;
	// Символьный код валюты
	private String VchCode;
	
	public String getVname() {
		return Vname;
	}
	public void setVname(String vname) {
		Vname = vname;
	}
	public String getVnom() {
		return Vnom;
	}
	public void setVnom(String vnom) {
		Vnom = vnom;
	}
	public String getVcurs() {
		return Vcurs;
	}
	public void setVcurs(String vcurs) {
		Vcurs = vcurs;
	}
	public String getVcode() {
		return Vcode;
	}
	public void setVcode(String vcode) {
		Vcode = vcode;
	}
	public String getVchCode() {
		return VchCode;
	}
	public void setVchCode(String vchCode) {
		VchCode = vchCode;
	} 

}
