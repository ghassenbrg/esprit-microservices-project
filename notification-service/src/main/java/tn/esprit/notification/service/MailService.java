package tn.esprit.notification.service;

import tn.esprit.notification.payload.ApiResponse;
import tn.esprit.notification.payload.MailRequest;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public interface MailService {

	ApiResponse sendMail(MailRequest mailRequest);
}
