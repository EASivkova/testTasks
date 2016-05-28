package ru.country.info.xml;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;

import ru.country.info.model.ProcessingResult;

public interface WSXMLProcessor {

    /**
     * Формирует ответ на запрос отправки сообщения опираясь на исходные данные
     * и данные полученные в результати обработки запроса.
     * @param result
     *        результат обработки данных
     * @return
     *         XML ноду в виде {@link Element}, содержащую сформированные ответ
     *         для клиента
     * @throws ParserConfigurationException 
     * 			Ошибка инициализации XML обработчика
     */
    Element createResponse(final ProcessingResult result);
    
}
