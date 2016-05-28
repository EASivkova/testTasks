package ru.parser.utils;

import java.util.UUID;

/**
 * Генератор случайных последовательностей в формате GUID.
 * @since 1.0.0
 * @author vdm
 */
public class GUIDGeneratorImpl implements GUIDGenerator {

    /**
     * Формирует GUID в стандартном представлении
     * ХХХХХХХХ-ХХХХ-ХХХХ-ХХХХ-ХХХХХХХХХХХХ.
     * @return строковое представление GUID
     */
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
