package main.resources.utils;

/**
 * Created by bchappet on 10/17/14.
 */
public class BadMatrixDimensionException extends Exception {

    public BadMatrixDimensionException() {
    }

    public BadMatrixDimensionException(String message) {
        super(message);
    }

    public BadMatrixDimensionException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadMatrixDimensionException(Throwable cause) {
        super(cause);
    }

    public BadMatrixDimensionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
