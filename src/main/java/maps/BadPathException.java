package main.java.maps;

public class BadPathException extends Exception {

	public BadPathException() {
	}

	public BadPathException(String message) {
		super(message);
	}

	public BadPathException(Throwable cause) {
		super(cause);
	}

	public BadPathException(String message, Throwable cause) {
		super(message, cause);
	}

}
