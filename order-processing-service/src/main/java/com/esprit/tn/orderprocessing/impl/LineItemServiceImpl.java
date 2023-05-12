package com.esprit.tn.orderprocessing.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esprit.tn.orderprocessing.models.LineItem;
import com.esprit.tn.orderprocessing.models.Order;
import com.esprit.tn.orderprocessing.repositories.LineItemRepository;
import com.esprit.tn.orderprocessing.services.LineItemService;
import com.esprit.tn.orderprocessing.services.OrderService;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@Service
public class LineItemServiceImpl implements LineItemService {

	@Autowired
	private LineItemRepository lineItemRepository;

	@Autowired
	private OrderService orderService;

	public List<LineItem> getAllItems() {
		return lineItemRepository.findAll();
	}

	@Override
	public List<LineItem> getItemsByOrderId(Long orderId) {
		List<LineItem> items = new ArrayList<>();
		List<LineItem> allItems = getAllItems();
		allItems.forEach(item -> {
			if (item.getOrder() != null && item.getOrder().getId() != null && item.getOrder().getId().equals(orderId)) {
				items.add(item);
			}
		});
		return items;
	}

	@Override
	public void addItemToOrder(Long orderId, LineItem lineItem) {
		Order order = orderService.findOrderById(orderId);
		// connect with catalog service to check if product exists
		if (lineItem != null && order != null && productExist(lineItem.getProductId())) {
			lineItem.setOrder(order);
		}
		lineItemRepository.save(lineItem);
	}

	@Override
	public void removeItemFromOrder(Long orderId, Long lineItemId) {
		Order order = orderService.findOrderById(orderId);
		LineItem lineItem = lineItemRepository.findById(lineItemId).orElse(null);
		if (lineItem != null && order != null) {
			lineItem.setOrder(null);
		}
		lineItemRepository.save(lineItem);
	}

    private boolean productExist(String productId) {
        // TODO;
    	return true;
    }
}
