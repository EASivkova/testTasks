package ru.parser;

import java.io.Serializable;

/**
 * Интерфейс нужен для объединения всех сущности в множество, у которого можно
 * получить идентификатор.
 * @author Сергей Петунин
 *         Created by IntelliJ IDEA by psa 14.07.2011 18:27:23
 */
public interface DomainObject extends Serializable {
    /**
     * Метод возвращает идентификатор объекта, в большинстве своем, это числовые
     * сурогатные ключи.
     * @return сурогатный ключ объекта
     */
    Long getId();
}
