package tn.esprit.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.payment.model.Payment;
import tn.esprit.payment.payload.ApiResponse;
import tn.esprit.payment.payload.PagedResponse;
import tn.esprit.payment.payload.PaymentRequest;
import tn.esprit.payment.service.PaymentService;
import tn.esprit.payment.utils.AppUtils;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@GetMapping
	public PagedResponse<Payment> getAllPayments(
			@RequestParam(name = "page", required = false, defaultValue = AppUtils.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppUtils.DEFAULT_PAGE_SIZE) Integer size) {
		return paymentService.getAllPayments(page, size);
	}

	@GetMapping("/{id}")
	public Payment getPaymentById(@PathVariable(name = "id") Long id) {
		return paymentService.getPaymentById(id);
	}

	@GetMapping("/order-id/{id}")
	public Payment getPaymentByOrderId(@PathVariable(name = "id") Long id) {
		return paymentService.getPaymentByOrderId(id);
	}

	@GetMapping("/user-id/{id}")
	public PagedResponse<Payment> getPaymentsByUserId(@PathVariable(name = "id") Long id,
			@RequestParam(name = "page", required = false, defaultValue = AppUtils.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppUtils.DEFAULT_PAGE_SIZE) Integer size) {
		return paymentService.getPaymentsByUserId(id, page, size);
	}

	@PostMapping
	public ResponseEntity<ApiResponse> createPayment(PaymentRequest paymentRequest) {
		ApiResponse response = paymentService.createPayment(paymentRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
