package com.esprit.tn.inventory.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.esprit.tn.inventory.models.Inventory;
import com.esprit.tn.inventory.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;

	@GetMapping
	public ResponseEntity<List<Inventory>> getAllInventories() {
		List<Inventory> inventories = inventoryService.getAllInventories().stream().collect(Collectors.toList());
		return new ResponseEntity<>(inventories, HttpStatus.OK);
	}

	@GetMapping("/{inventoryId}")
	public ResponseEntity<Inventory> getInventoryById(@PathVariable Long inventoryId) {
		Inventory inventory = inventoryService.getInventoryById(inventoryId);
		return new ResponseEntity<>(inventory, HttpStatus.OK);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long productId) {
		Inventory inventory = inventoryService.getInventoryByProductId(productId);
		return new ResponseEntity<>(inventory, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
		inventory = inventoryService.createInventory(inventory);
		return new ResponseEntity<>(inventory, HttpStatus.OK);
	}

	@PutMapping("/{inventoryId}")
	public ResponseEntity<Inventory> updateInventory(@PathVariable Long inventoryId, @RequestBody Inventory inventory) {
		inventory = inventoryService.updateInventory(inventoryId, inventory);
		return new ResponseEntity<>(inventory, HttpStatus.OK);
	}

	@DeleteMapping("/{inventoryId}")
	public ResponseEntity<Inventory> deleteInventory(@PathVariable Long inventoryId) {
		inventoryService.cancelInventory(inventoryId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
