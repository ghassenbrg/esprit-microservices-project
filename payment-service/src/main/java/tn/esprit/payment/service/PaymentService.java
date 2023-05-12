package tn.esprit.payment.service;

import tn.esprit.payment.model.Payment;
import tn.esprit.payment.payload.ApiResponse;
import tn.esprit.payment.payload.PagedResponse;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public interface PaymentService {

	PagedResponse<Payment> getAllPayments(int page, int size);

	Payment getPaymentById(Long id);

	Payment getPaymentByOrderId(Long orderId);

	PagedResponse<Payment> getPaymentsByUserId(Long userId, int page, int size);

	public ApiResponse createPayment(Payment payment);

}
