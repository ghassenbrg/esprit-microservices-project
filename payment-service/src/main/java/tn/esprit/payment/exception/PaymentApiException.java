package tn.esprit.payment.exception;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public class PaymentApiException extends RuntimeException {

	private static final long serialVersionUID = 2621803627739497588L;

	private final HttpStatus status;
	private final String message;

	public PaymentApiException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public PaymentApiException(HttpStatus status, String message, Throwable exception) {
		super(exception);
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}
