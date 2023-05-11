package com.esprit.tn.orderprocessing.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.tn.orderprocessing.models.Order;
import com.esprit.tn.orderprocessing.services.OrderService;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping()
	public ResponseEntity<List<Order>> findAll() {
		List<Order> orders = orderService.getAllOrders().stream().collect(Collectors.toList());
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/my-orders")
	public ResponseEntity<List<Order>> findAllOrdersByCustomerId(Long customerId) {
		List<Order> orders = orderService.getOrdersByCustomerId(customerId).stream()
				.collect(Collectors.toList());
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderDetails(@PathVariable("orderId") Long orderId) {
		Order order = orderService.findOrderById(orderId);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<Order> submitOrder(Long customerId, @RequestBody Order order) {
		orderService.createOrder(customerId, order);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@PutMapping("/{orderId}")
	public ResponseEntity<Order> updateOrder(Long customerId, @PathVariable("orderId") Long orderId,
			@RequestBody Order order) {
		orderService.updateOrder(customerId, orderId, order);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@DeleteMapping("/{orderId}/cancel")
	public ResponseEntity<Order> cancelOrder(Long customerId, @PathVariable("orderId") Long orderId) {
		orderService.cancelOrder(customerId, orderId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
