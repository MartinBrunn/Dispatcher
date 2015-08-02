package de.devacon.graphics.exception;

/**
 * Created by @Martin@ on 16.07.2015 12:25.
 */
public class AllPointsInLineException extends Throwable {
    /**
     * Constructs a new {@code Throwable} that includes the current stack trace.
     */
    public AllPointsInLineException() {
    }

    /**
     * Constructs a new {@code Throwable} with the current stack trace and the
     * specified detail message.
     *
     * @param detailMessage the detail message for this {@code Throwable}.
     */
    public AllPointsInLineException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code Throwable} with the current stack trace, the
     * specified detail message and the specified cause.
     *
     * @param detailMessage the detail message for this {@code Throwable}.
     * @param throwable     the cause of this {@code Throwable}.
     */
    public AllPointsInLineException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code Throwable} with the current stack trace and the
     * specified cause.
     *
     * @param throwable the cause of this {@code Throwable}.
     */
    public AllPointsInLineException(Throwable throwable) {
        super(throwable);
    }
}
