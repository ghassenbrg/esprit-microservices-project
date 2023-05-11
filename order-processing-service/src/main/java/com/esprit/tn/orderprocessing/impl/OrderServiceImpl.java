package com.esprit.tn.orderprocessing.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esprit.tn.orderprocessing.exceptions.BadRequestException;
import com.esprit.tn.orderprocessing.models.Order;
import com.esprit.tn.orderprocessing.payload.ApiResponse;
import com.esprit.tn.orderprocessing.payload.OrderDto;
import com.esprit.tn.orderprocessing.repositories.OrderRepository;
import com.esprit.tn.orderprocessing.services.OrderService;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
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
		Order order = orderRepository.findById(orderId).orElse(null);
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
		orderRepository.save(order);
	}

	@Override
	public void updateOrder(Long customerId, Long orderId, OrderDto orderInput) {
		Order order = findOrderById(orderId);
		order.setCustomerId(orderInput.getCustomerId());
		order.setOrderDate(orderInput.getOrderDate());
		order.setPaymentId(orderInput.getPaymentId());
		order.setStatus(orderInput.getStatus());
		orderRepository.save(order);
	}

	@Override
	public void cancelOrder(Long customerId, Long orderId) {
		Order order = findOrderById(orderId);
		if (order.getCustomerId() == null || !order.getCustomerId().equals(customerId)) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to cancel this order");
			throw new BadRequestException(apiResponse);
		}
		orderRepository.delete(order);
	}

}
