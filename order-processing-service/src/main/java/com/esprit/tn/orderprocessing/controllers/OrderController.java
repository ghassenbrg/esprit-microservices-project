package com.esprit.tn.orderprocessing.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
import com.esprit.tn.orderprocessing.payload.ApiResponse;
import com.esprit.tn.orderprocessing.payload.OrderDto;
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

	private ModelMapper modelMapper = new ModelMapper();

	@GetMapping()
	public ResponseEntity<List<OrderDto>> findAll() {
		List<OrderDto> orders = orderService.getAllOrders().stream().map(this::convertOrderEntityToOrderDto)
				.collect(Collectors.toList());
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/my-orders")
	public ResponseEntity<List<OrderDto>> findAllOrdersByCustomerId(Long customerId) {
		List<OrderDto> orders = orderService.getOrdersByCustomerId(customerId).stream()
				.map(this::convertOrderEntityToOrderDto).collect(Collectors.toList());
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDto> getOrderDetails(@PathVariable("orderId") Long orderId) {
		Order order = orderService.findOrderById(orderId);
		return new ResponseEntity<>(convertOrderEntityToOrderDto(order), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<ApiResponse> submitOrder(Long customerId, @RequestBody OrderDto order) {
		orderService.createOrder(customerId, convertOrderDtoToOrderEntity(order));
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Order submitted successfully!");
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}

	@PutMapping("/{orderId}")
	public ResponseEntity<ApiResponse> updateOrder(Long customerId, @PathVariable("orderId") Long orderId,
			@RequestBody OrderDto order) {
		orderService.updateOrder(customerId, orderId, order);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Order updated successfully!");
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{orderId}/cancel")
	public ResponseEntity<ApiResponse> cancelOrder(Long customerId, @PathVariable("orderId") Long orderId) {
		orderService.cancelOrder(customerId, orderId);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Order was cancelled successfully");
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	private OrderDto convertOrderEntityToOrderDto(Order order) {
		OrderDto orderDto = modelMapper.map(order, OrderDto.class);
		return orderDto;
	}

	private Order convertOrderDtoToOrderEntity(OrderDto orderDto) {
		Order order = modelMapper.map(orderDto, Order.class);
		return order;
	}
}
