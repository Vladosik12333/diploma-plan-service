package by.vb.diplomaplanservice.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
	private final HttpStatus status;

	public ApiException(final HttpStatus status, final String message) {
		super(message);
		this.status = status;
	}

	public ApiException(final HttpStatus status, final String message, final Throwable cause) {
		super(message, cause);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
}
