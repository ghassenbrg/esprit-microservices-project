package tn.esprit.payment.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tn.esprit.payment.exception.ResourceNotFoundException;
import tn.esprit.payment.model.Payment;
import tn.esprit.payment.payload.ApiResponse;
import tn.esprit.payment.payload.MailRequest;
import tn.esprit.payment.payload.PagedResponse;
import tn.esprit.payment.repository.PaymentRepository;
import tn.esprit.payment.utils.AppUtils;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private NotificationServiceClient notificationServiceClient;

	@Override
	public PagedResponse<Payment> getAllPayments(int page, int size) {
		AppUtils.validatePageNumberAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
		Page<Payment> payments = paymentRepository.findAll(pageable);
		List<Payment> content = payments.getNumberOfElements() == 0 ? Collections.emptyList() : payments.getContent();

		return new PagedResponse<>(content, payments.getNumber(), payments.getSize(), payments.getTotalElements(),
				payments.getTotalPages(), payments.isLast());
	}

	@Override
	public Payment getPaymentById(Long id) {
		return paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
	}

	@Override
	public Payment getPaymentByOrderId(Long orderId) {
		return paymentRepository.findByOrderId(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("order Id", "id", orderId));
	}

	@Override
	public PagedResponse<Payment> getPaymentsByUserId(Long userId, int page, int size) {
		AppUtils.validatePageNumberAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
		Page<Payment> payments = paymentRepository.findAllByUserId(userId, pageable);
		List<Payment> content = payments.getNumberOfElements() == 0 ? Collections.emptyList() : payments.getContent();

		return new PagedResponse<>(content, payments.getNumber(), payments.getSize(), payments.getTotalElements(),
				payments.getTotalPages(), payments.isLast());
	}

	@Override
	public ApiResponse createPayment(Payment payment) {

		Payment savedPayment = paymentRepository.save(payment);
		MailRequest mailRequest = new MailRequest(savedPayment.getUserId(), savedPayment.getEmail(),
				"Transaction Completed Successfully", "Transaction Completed Successfully");
		notificationServiceClient.sendNotification(mailRequest);

		return new ApiResponse(Boolean.TRUE, "Transaction Completed Successfully");
	}

}
