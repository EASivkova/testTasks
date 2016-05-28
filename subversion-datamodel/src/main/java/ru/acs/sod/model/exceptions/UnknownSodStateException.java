package ru.acs.sod.model.exceptions;

public class UnknownSodStateException extends Exception {
    
    /** UID для сериализации объекта. */
    private static final long serialVersionUID = 1L;

    /** Конструктор по умолчанию. */
    public UnknownSodStateException() {
        super();
    }

    /**
     * Конструктор.
     * @param message
     *        текст сообщения об ошибке
     */
    public UnknownSodStateException(final String message) {
        super(message);
    }

    /**
     * Конструктор.
     * @param cause
     *        причина ошибки
     */
    public UnknownSodStateException(final Throwable cause) {
        super(cause);
    }

    /**
     * Конструктор.
     * @param message
     *        текст сообщения об ошибке
     * @param cause
     *        причина ошибки
     */
    public UnknownSodStateException(final String message, final Throwable cause) {
        super(message, cause);
    }
	
}
