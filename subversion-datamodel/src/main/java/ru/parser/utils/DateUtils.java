package ru.parser.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.acs.sod.model.exceptions.TechEnvProcessorException;

/**
 * Класс по преобразования даты/времени. Используется для преобразоваия в
 * строкове представлнеие по маске и обратно.<br/>
 * Маска использует стандартный шаблон:<br/>
 * yyyy - год<br/>
 * MM - месяц<br/>
 * dd - день<br/>
 * HH - час<br/>
 * mm - минута<br/>
 * ss - секунда<br/>
 * одинарные кавычки для добавления и парсинга строковых символов не имеющих
 * отношения к дате.
 * @since 1.0.1
 * @author vdm
 */
public final class DateUtils {

    /** Стандартная маска полного времени. */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    /** Маска только даты. */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /** Конструктор по умолчанию. */
    private DateUtils() {
        super();
    }

    /**
     * Метод получает из полной даты/времени, только дату.
     * @param currentDate
     *        дата/время
     * @return дату/время с обрезанной частью времени
     */
    public static Date getOnlyDate(final Date currentDate) {
        if (currentDate == null) {
            throw new IllegalArgumentException("currentData == null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * Метод преобразует по маске дату в строковое представление.
     * @param date
     *        дата/время для преобразования
     * @param pattern
     *        маска преобразования
     * @return строковое представление даты/времени
     */
    public static String getStringDate(final Date date, final String pattern) {
        if (date == null || pattern == null) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
        //formatter.setTimeZone(TimeZone.getTimeZone("GMT+0000"));
        return formatter.format(date);
    }

    /**
     * Метод преобразует строку в дату/время по маске.
     * @param date
     *        строка содержащая дату/время для преобразования
     * @param pattern
     *        маска для преобразования строки в дату/время
     * @return дату полученную из строки
     * @throws TechEnvProcessorException
     *         в случае несоответствия строкового представления с маской
     *         преобразования
     */
    public static Date getDateFromString(final String date, final String pattern)
            throws TechEnvProcessorException {
        if (date == null || date.length() == 0) {
            return null;
        }
        try {
            DateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
            //formatter.setTimeZone(TimeZone.getTimeZone("GMT+0000"));
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new TechEnvProcessorException(e);
        }
    }
}
