package com.esprit.tn.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.tn.inventory.models.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{

}
