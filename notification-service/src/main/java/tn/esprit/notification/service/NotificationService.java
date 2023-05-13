package tn.esprit.notification.service;

import tn.esprit.notification.model.Notification;
import tn.esprit.notification.payload.ApiResponse;
import tn.esprit.notification.payload.MailRequest;
import tn.esprit.notification.payload.PagedResponse;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public interface NotificationService {

	PagedResponse<Notification> getUserNotification(Long idUser, int page, int size);

	ApiResponse notify(MailRequest mailRequest);

}