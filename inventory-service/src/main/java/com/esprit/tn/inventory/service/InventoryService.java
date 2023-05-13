package com.esprit.tn.inventory.service;

import java.util.List;

import com.esprit.tn.inventory.models.Inventory;


public interface InventoryService {
	
	List<Inventory> getAllInventories();
	
	Inventory getInventoryById(Long inventoryId);

	Inventory getInventoryByProductId(Long productId);

	Inventory createInventory(Inventory inventory);

	Inventory updateInventory(Long inventoryId, Inventory inventory);

	void cancelInventory(Long inventoryId);

}
