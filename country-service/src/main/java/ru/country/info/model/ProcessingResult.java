package ru.country.info.model;

import java.util.ArrayList;
import java.util.List;

import ru.gismeteo.ws.HHForecast;

public class ProcessingResult {
	
	private String responseId;

    private ValuteCursOnDate valuteCursOnDate;
    
    private List<HHForecast> listHHForecast = new ArrayList<HHForecast>();

    private transient String errorDetails = "";
    
    public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public ValuteCursOnDate getValuteCursOnDate() {
		return valuteCursOnDate;
	}

	public void setValuteCursOnDate(ValuteCursOnDate valuteCursOnDate) {
		this.valuteCursOnDate = valuteCursOnDate;
	}

	public List<HHForecast> getListHHForecast() {
		return listHHForecast;
	}

	public void setListHHForecast(List<HHForecast> listHHForecast) {
		this.listHHForecast = listHHForecast;
	}

	/**
     * Добавление текста сообщения об ошибке к текущему сообщению об ошибке с
     * переносом строки.
     * @param oneErrorText
     *        текст добавляемый к описанию ошибки
     */
    public final void addErrorDetails(final String oneErrorText) {
        if (oneErrorText != null && oneErrorText.length() != 0) {
            this.errorDetails = this.errorDetails.concat(oneErrorText + "\r\n");
        }
    }

    /**
     * @return {@link #errorDetails}
     */
    public final String getErrorDetails() {
        return errorDetails;
    }

}
