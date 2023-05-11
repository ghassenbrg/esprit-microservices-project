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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.esprit.tn.orderprocessing.models.LineItem;
import com.esprit.tn.orderprocessing.models.Order;
import com.esprit.tn.orderprocessing.services.LineItemService;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@RestController
@RequestMapping("/items")
public class LineItemController {

	@Autowired
	private LineItemService lineItemService;

	@GetMapping("/{orderId}")
	public ResponseEntity<List<LineItem>> findItemsByOrderId(Long orderId) {
		List<LineItem> items = lineItemService.getItemsByOrderId(orderId).stream()
				.collect(Collectors.toList());
		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@PostMapping("/{orderId}")
	public ResponseEntity<LineItem> addItem(Long orderId, @RequestBody LineItem lineItem) {
		lineItemService.addItemToOrder(orderId, lineItem);
		return new ResponseEntity<>(lineItem, HttpStatus.CREATED);
	}

	@DeleteMapping("/{orderId}/cancel/{lineItemId}")
	public ResponseEntity<Order>removeItem(@PathVariable("orderId") Long orderId, @PathVariable("lineItemId") Long lineItemId) {
		lineItemService.removeItemFromOrder(orderId, lineItemId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
