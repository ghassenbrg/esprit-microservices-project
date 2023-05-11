package com.esprit.tn.orderprocessing.services;

import java.util.List;

import com.esprit.tn.orderprocessing.models.Order;

/**
 * 
 * @author Marwen Lahmar
 *
 */

public interface OrderService {

	List<Order> getAllOrders();

	List<Order> getOrdersByCustomerId(Long customerId);

	Order findOrderById(Long orderId);

	void createOrder(Long customerId, Order order);

	void updateOrder(Long customerId, Long orderId, Order order);

	void cancelOrder(Long customerId, Long orderId);

}
