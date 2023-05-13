package tn.esprit.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.notification.payload.ApiResponse;
import tn.esprit.notification.payload.MailRequest;
import tn.esprit.notification.service.NotificationService;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@RestController
@RequestMapping("/notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@PostMapping(value = "/send")
	public ResponseEntity<ApiResponse> sendTestReport(@RequestBody MailRequest mailRequest) {
		ApiResponse response = notificationService.notify(mailRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
