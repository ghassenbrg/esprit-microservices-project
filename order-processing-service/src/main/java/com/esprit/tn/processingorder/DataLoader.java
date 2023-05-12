package com.esprit.tn.processingorder;

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
	
	@Autowired
	private ModelMapper modelMapper = new ModelMapper();

	public DataLoader(OrderService orderService, LineItemService lineItemService) {
		this.orderService = orderService;
		this.lineItemService = lineItemService;
	}

	public void run(ApplicationArguments args) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, OrderDto.class);
		List<OrderDto> orders = mapper.readValue(new ClassPathResource("/orders-dummy-data.json").getInputStream(), type);
		orders.forEach(order -> orderService.createOrder((long) 1, convertOrderDtoToOrderEntity(order)));
	}

	private Order convertOrderDtoToOrderEntity(OrderDto orderDto) {
		Order order = modelMapper.map(orderDto, Order.class);
		return order;
	}
}
