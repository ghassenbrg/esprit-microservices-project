package com.esprit.tn.orderprocessing.payload;

import java.util.ArrayList;
import java.util.List;

public class ProductDto {

	private Long productId;
	private Long sellerId;
	private String name;
	private String description;
	private List<String> category;
	private List<String> images;
	private List<LineItemDto> items = new ArrayList<>();

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<LineItemDto> getItems() {
		return items;
	}

	public void setItems(List<LineItemDto> items) {
		this.items = items;
	}

}
