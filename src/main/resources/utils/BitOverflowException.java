package main.resources.utils;

public class BitOverflowException extends Exception {

	public BitOverflowException() {
	}

	public BitOverflowException(String message) {
		super(message);
	}

	public BitOverflowException(Throwable cause) {
		super(cause);
	}

	public BitOverflowException(String message, Throwable cause) {
		super(message, cause);
	}

}
