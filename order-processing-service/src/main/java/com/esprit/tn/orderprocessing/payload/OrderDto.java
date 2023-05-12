package com.esprit.tn.orderprocessing.payload;

import java.util.ArrayList;
import java.util.List;

public class OrderDto {

	private Long id;
	private Long customerId;
	private String status = "NEW";
	private String orderDate;
	private String paymentId;
	private List<LineItemDto> items = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public List<LineItemDto> getItems() {
		return items;
	}

	public void setItems(List<LineItemDto> items) {
		this.items = items;
	}

}
