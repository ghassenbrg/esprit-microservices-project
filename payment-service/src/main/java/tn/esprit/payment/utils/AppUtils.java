package tn.esprit.payment.utils;

import org.springframework.http.HttpStatus;

import tn.esprit.payment.exception.PaymentApiException;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public class AppUtils {

	public static final String DEFAULT_PAGE_NUMBER = "0";
	public static final String DEFAULT_PAGE_SIZE = "30";
	public static final int MAX_PAGE_SIZE = 30;

	public static void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new PaymentApiException(HttpStatus.BAD_REQUEST, "Page number cannot be less than zero.");
		}

		if (size < 0) {
			throw new PaymentApiException(HttpStatus.BAD_REQUEST, "Size number cannot be less than zero.");
		}

		if (size > MAX_PAGE_SIZE) {
			throw new PaymentApiException(HttpStatus.BAD_REQUEST,
					"Page size must not be greater than " + MAX_PAGE_SIZE);
		}
	}
}
