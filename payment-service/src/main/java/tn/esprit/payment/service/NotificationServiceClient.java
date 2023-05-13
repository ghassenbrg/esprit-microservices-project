package tn.esprit.payment.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tn.esprit.payment.payload.MailRequest;

@FeignClient(name = "notification-service", url = "${NOTIFICATION_SERVICE_URL:http://localhost:8085}") // Use your notification-service url
public interface NotificationServiceClient {
    @PostMapping("/notification/send")
    void sendNotification(@RequestBody MailRequest mailRequest);
}