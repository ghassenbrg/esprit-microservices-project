package tn.esprit.notification.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tn.esprit.notification.model.Notification;
import tn.esprit.notification.model.NotificationType;
import tn.esprit.notification.payload.ApiResponse;
import tn.esprit.notification.payload.MailRequest;
import tn.esprit.notification.payload.PagedResponse;
import tn.esprit.notification.repository.NotificationRepository;
import tn.esprit.notification.utils.AppUtils;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private MailService mailService;

	@Override
	public PagedResponse<Notification> getUserNotification(Long idUser, int page, int size) {
		AppUtils.validatePageNumberAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "userId");
		Page<Notification> notifications = notificationRepository.findAllByUserId(idUser, pageable);
		List<Notification> content = notifications.getNumberOfElements() == 0 ? Collections.emptyList()
				: notifications.getContent();

		return new PagedResponse<>(content, notifications.getNumber(), notifications.getSize(),
				notifications.getTotalElements(), notifications.getTotalPages(), notifications.isLast());
	}

	@Override
	public ApiResponse notify(MailRequest mailRequest) {
		Notification notification = new Notification();
		notification.setContent(mailRequest.getContent());
		notification.setType(NotificationType.CONFIRM_PAYMENT);
		notification.setUserId(mailRequest.getUserId());
		ApiResponse apiResponse = mailService.sendMail(mailRequest);
		notificationRepository.save(notification);
		return apiResponse;
	}

}
