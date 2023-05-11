package com.esprit.tn.orderprocessing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.esprit.tn.orderprocessing.models.Order;

/**
 * 
 * @author Marwen Lahmar
 *
 */

public interface OrderProcessingRepository extends JpaRepository<Order, Long> {

}
