package ru.country.info.xml;

public enum XMLField {

    COUNTRY_REQUEST("CountryRequest"),
    COUNTRY_RESPONCE("CountryResponse"),
    BODY("body"),
    NAME("name"),
    ID("id"),
    COUNTRY_INFO("countryInfo"),
    COURSES("courses"),
    COURSES_VNAME("vname"),
    COURSES_VNOM("vnom"),
    COURSES_VCURS("vcurs"),
    COURSES_VCODE("vcode"),
    COURSES_VCHCODE("vchcode"),
    WEATHER("weather"),
    FORECAST("forecast"),
    FORECAST_TIME("time"),
    FORECAST_TOD("tod"),
    FORECAST_T("t"),
    FORECAST_P("p"),
    FORECAST_CL("cl"),
    FORECAST_PRC("prc"),
    FORECAST_PRCT("prct"),
    FORECAST_DD("dd"),
    FORECAST_FF("ff"),
    FORECAST_ST("st"),
    FORECAST_HUMIDITY("humidity"),
    ERROR_TEXT("errorText");

    /** Имя тега ноды. */
    private String name;

    /**
     * Конструктор значения с указанием имени тэга ноды.
     * @param fieldName
     *        имя тэга ноды
     */
    XMLField(final String fieldName) {
        this.name = fieldName;
    }

    /**
     * Получение имени тэга из значения перечисления.
     * @return имя тэга из значения перечисления
     */
    public String getName() {
        return name;
    }
}
