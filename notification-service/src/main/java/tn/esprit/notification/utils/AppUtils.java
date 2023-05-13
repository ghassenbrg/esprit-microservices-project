package tn.esprit.notification.utils;

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
			throw new RuntimeException("Page number cannot be less than zero.");
		}

		if (size < 0) {
			throw new RuntimeException("Size number cannot be less than zero.");
		}

		if (size > MAX_PAGE_SIZE) {
			throw new RuntimeException("Page size must not be greater than " + MAX_PAGE_SIZE);
		}
	}
}
