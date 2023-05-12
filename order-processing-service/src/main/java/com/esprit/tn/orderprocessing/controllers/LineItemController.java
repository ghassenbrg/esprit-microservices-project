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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.esprit.tn.orderprocessing.models.LineItem;
import com.esprit.tn.orderprocessing.payload.ApiResponse;
import com.esprit.tn.orderprocessing.payload.LineItemDto;
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

	private ModelMapper modelMapper = new ModelMapper();

	@GetMapping("/{orderId}")
	public ResponseEntity<List<LineItemDto>> findItemsByOrderId(Long orderId) {
		List<LineItemDto> items = lineItemService.getItemsByOrderId(orderId).stream()
				.map(this::convertLineItemEntityToLineItemDto).collect(Collectors.toList());
		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@PostMapping("/{orderId}")
	public ResponseEntity<ApiResponse> addItem(@PathVariable("orderId") Long orderId, @RequestBody LineItemDto lineItem) {
		lineItemService.addItemToOrder(orderId, convertLineItemDtoToLineItemEntity(lineItem));
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Item added successfully!");
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/{orderId}/cancel/{lineItemId}")
	public ResponseEntity<ApiResponse> removeItem(@PathVariable("orderId") Long orderId,
			@PathVariable("lineItemId") Long lineItemId) {
		lineItemService.removeItemFromOrder(orderId, lineItemId);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Item removed successfully!");
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	private LineItemDto convertLineItemEntityToLineItemDto(LineItem lineItem) {
		LineItemDto lineItemDto = modelMapper.map(lineItem, LineItemDto.class);
		return lineItemDto;
	}

	private LineItem convertLineItemDtoToLineItemEntity(LineItemDto lineItemDto) {
		LineItem lineItem = modelMapper.map(lineItemDto, LineItem.class);
		return lineItem;
	}
}
