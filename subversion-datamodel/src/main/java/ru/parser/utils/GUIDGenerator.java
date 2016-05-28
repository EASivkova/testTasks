package ru.parser.utils;

/**
 * Интерфейс генератора случайных последовательностей в формате GUID.
 * @since 1.0.0
 * @author vdm
 */
public interface GUIDGenerator {

    /**
     * Формирует GUID в стандартном представлении
     * ХХХХХХХХ-ХХХХ-ХХХХ-ХХХХ-ХХХХХХХХХХХХ.
     * @return строковое представление GUID
     */
    String generate();
}