package com.esprit.tn.orderprocessing.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.esprit.tn.orderprocessing.exceptions.BadRequestException;
import com.esprit.tn.orderprocessing.models.Order;
import com.esprit.tn.orderprocessing.payload.ApiResponse;
import com.esprit.tn.orderprocessing.repositories.OrderProcessingRepository;
import com.esprit.tn.orderprocessing.services.OrderProcessingService;

/**
 * 
 * @author Marwen Lahmar
 *
 */

public class OrderProcessingServiceImpl implements OrderProcessingService {

	@Autowired
	private OrderProcessingRepository orderProcessingRepository;

	@Override
	public List<Order> getAllOrders() {
		return orderProcessingRepository.findAll();
	}

	@Override
	public List<Order> getOrdersByCustomerId(Long customerId) {
		List<Order> orders = new ArrayList<>();
		List<Order> allOrders = getAllOrders();
		allOrders.forEach(order -> {
			if (order.getCustomerId() != null && order.getCustomerId().equals(customerId)) {
				orders.add(order);
			}
		});
		return orders;
	}

	@Override
	public Order findOrderById(Long orderId) {
		Order order = orderProcessingRepository.findById(orderId).orElse(null);
		if (order == null) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Order does not exist");
			throw new BadRequestException(apiResponse);
		}
		return order;
	}

	@Override
	public void createOrder(Long customerId, Order order) {
		if (order != null) {
			order.setCustomerId(customerId);
		}
		orderProcessingRepository.save(order);
	}

	@Override
	public void updateOrder(Long customerId, Long orderId, Order orderInput) {
		Order order = findOrderById(orderId);
		order.setCustomerId(orderInput.getCustomerId());
		order.setItems(orderInput.getItems());
		order.setOrderDate(orderInput.getOrderDate());
		order.setPaymentId(orderInput.getPaymentId());
		order.setStatus(orderInput.getStatus());
		orderProcessingRepository.save(order);
	}

	@Override
	public void cancelOrder(Long customerId, Long orderId) {
		Order order = findOrderById(orderId);
		if (order.getCustomerId() == null || !order.getCustomerId().equals(customerId)) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to cancel this order");
			throw new BadRequestException(apiResponse);
		}
		orderProcessingRepository.delete(order);
	}

}
