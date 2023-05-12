package tn.esprit.payment.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.payment.model.Payment;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	Optional<Payment> findByOrderId(Long orderId);

	Page<Payment> findAllByUserId(Long userId, Pageable pageable);
}