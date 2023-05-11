package com.esprit.tn.orderprocessing.services;

import java.util.List;
import com.esprit.tn.orderprocessing.models.LineItem;

/**
 * 
 * @author Marwen Lahmar
 *
 */

public interface LineItemService {

	List<LineItem> getItemsByOrderId(Long orderId);

	void addItemToOrder(Long orderId, LineItem lineItem);

	void removeItemFromOrder(Long orderId, Long lineItemId);

}
