package ru.acs.sod.model.exceptions;

/**
 * Класс обертка для ошибок возникающих при ошибках обработки ТК.
 * @since 1.0.0
 * @author vdm
 */
public class TechEnvProcessorException extends Exception {

    /** UID для сериализации объекта. */
    private static final long serialVersionUID = 1L;

    /** Конструктор по умолчанию. */
    public TechEnvProcessorException() {
        super();
    }

    /**
     * Конструктор.
     * @param message
     *        текст сообщения об ошибке
     */
    public TechEnvProcessorException(final String message) {
        super(message);
    }

    /**
     * Конструктор.
     * @param cause
     *        причина ошибки
     */
    public TechEnvProcessorException(final Throwable cause) {
        super(cause);
    }

    /**
     * Конструктор.
     * @param message
     *        текст сообщения об ошибке
     * @param cause
     *        причина ошибки
     */
    public TechEnvProcessorException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
