package com.esprit.tn.orderprocessing.payload;

import java.math.BigDecimal;

public class LineItemDto {

	private Long id;
	private String productId;
	private int quantity;
	private BigDecimal productPrice;

	public Long getId() {
		return id;
	}

	public void setid(Long id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

}
