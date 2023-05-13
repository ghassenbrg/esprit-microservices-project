package tn.esprit.notification.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.notification.model.Notification;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	Page<Notification> findAllByUserId(Long userId, Pageable pageable);
}