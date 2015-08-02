package de.devacon.exception;

/**
 * Created by @Martin@ on 15.07.2015 16:48.
 */
public class NotImplementedException extends Exception {
    /**
     * Constructs a new {@code Exception} that includes the current stack trace.
     */
    public NotImplementedException() {
        super();
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace and the
     * specified detail message.
     *
     * @param detailMessage the detail message for this exception.
     */
    public NotImplementedException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace, the
     * specified detail message and the specified cause.
     *
     * @param detailMessage the detail message for this exception.
     * @param throwable
     */
    public NotImplementedException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace and the
     * specified cause.
     *
     * @param throwable the cause of this exception.
     */
    public NotImplementedException(Throwable throwable) {
        super(throwable);
    }
}
