package com.esprit.tn.orderprocessing;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.esprit.tn.orderprocessing.models.LineItem;
import com.esprit.tn.orderprocessing.models.Order;
import com.esprit.tn.orderprocessing.payload.OrderDto;
import com.esprit.tn.orderprocessing.repositories.OrderRepository;
import com.esprit.tn.orderprocessing.services.LineItemService;
import com.esprit.tn.orderprocessing.services.OrderService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

@Component

public class DataLoader implements ApplicationRunner {

	@Autowired
	private OrderService orderService;

	@Autowired
	private LineItemService lineItemService;

	public DataLoader(OrderService orderService, LineItemService lineItemService) {
		this.orderService = orderService;
		this.lineItemService = lineItemService;
	}

	public void run(ApplicationArguments args) throws StreamReadException, DatabindException, IOException {
		// mock orders data
		ObjectMapper mapper = new ObjectMapper();
		CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, Order.class);
		List<Order> orders = mapper.readValue(new ClassPathResource("/orders-dummy-data.json").getInputStream(), type);
		orders.forEach(order -> orderService.createOrder((long) 1, order));

		// mock items data
		CollectionType typeItem = mapper.getTypeFactory().constructCollectionType(List.class, LineItem.class);
		List<LineItem> lineItems = mapper.readValue(new ClassPathResource("/line-items-dummy-data.json").getInputStream(),
				typeItem);

		for (int i = 1; i < 6; i++) {
			lineItemService.addItemToOrder((long) 1, lineItems.get(i));
		}
		for (int j = 6; j < 11; j++) {
			lineItemService.addItemToOrder((long) 2, lineItems.get(j));
		}
		for (int k = 11; k < 16; k++) {
			lineItemService.addItemToOrder((long) 3, lineItems.get(k));
		}
		for (int l = 16; l < 20; l++) {
			lineItemService.addItemToOrder((long) 4, lineItems.get(l));
		}
	}

}
