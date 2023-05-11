package com.esprit.tn.orderprocessing.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.esprit.tn.orderprocessing.payload.ApiResponse;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 7544349601900106564L;

	private ApiResponse apiResponse;

	public BadRequestException(ApiResponse apiResponse) {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiResponse getApiResponse() {
		return apiResponse;
	}
}