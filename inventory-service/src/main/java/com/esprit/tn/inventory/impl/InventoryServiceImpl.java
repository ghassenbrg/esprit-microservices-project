package com.esprit.tn.inventory.impl;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import com.esprit.tn.inventory.models.Inventory;
import com.esprit.tn.inventory.repository.InventoryRepository;
import com.esprit.tn.inventory.service.InventoryService;
import org.springframework.stereotype.Service;


@Service

public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Override
	public List<Inventory> getAllInventories() {
		return inventoryRepository.findAll();
	}

	@Override
	public Inventory getInventoryById(Long inventoryId) {
		Inventory inventory = inventoryRepository.findById(inventoryId).orElse(null);
		return inventory;
	}

	@Override
	public Inventory getInventoryByProductId(Long productId) {
		List<Inventory> allInventories = inventoryRepository.findAll();
		Inventory foundInventory = null;
		for (int i = 0; i < allInventories.size(); i++) {
			if (allInventories.get(i).getProductId() != null
					&& allInventories.get(i).getProductId().equals(productId)) {
				foundInventory = allInventories.get(i);
			}
		}
		return foundInventory;
	}

	@Override
	public Inventory createInventory(Inventory inventory) {
		if (inventory != null) {
			return inventoryRepository.save(inventory);
		}
		return null;
	}

	@Override
	public Inventory updateInventory(Long inventoryId, Inventory inventory) {
		Inventory inventoryToUpdate = getInventoryByProductId(inventoryId);
		inventoryToUpdate.setAvailableQuantity(inventory.getAvailableQuantity());
		inventoryToUpdate.setProductId(inventory.getProductId());
		inventoryToUpdate.setReservedItems(inventory.getReservedItems());
		return inventoryRepository.save(inventoryToUpdate);
	}

	@Override
	public void cancelInventory(Long inventoryId) {
		Inventory inventory = getInventoryByProductId(inventoryId);
		inventoryRepository.delete(inventory);
	}

}
