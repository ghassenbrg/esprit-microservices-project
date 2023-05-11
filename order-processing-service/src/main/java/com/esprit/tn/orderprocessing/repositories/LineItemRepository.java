package com.esprit.tn.orderprocessing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.tn.orderprocessing.models.LineItem;

/**
 * 
 * @author Marwen Lahmar
 *
 */

public interface LineItemRepository extends JpaRepository<LineItem, Long> {

}
