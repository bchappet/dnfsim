package main.java.maps;

public class NoMoreDataException extends Exception {

	public NoMoreDataException() {
	}

	public NoMoreDataException(String message) {
		super(message);
	}

	public NoMoreDataException(Throwable cause) {
		super(cause);
	}

	public NoMoreDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoMoreDataException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
