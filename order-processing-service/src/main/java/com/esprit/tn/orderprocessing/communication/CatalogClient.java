package com.esprit.tn.orderprocessing.communication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.esprit.tn.orderprocessing.payload.ProductDto;

@FeignClient(value = "products", url = "${PRODUCTS_SERVICE_URL:http://localhost:3001/product-catalog/api}")
public interface CatalogClient {

	@RequestMapping(method = RequestMethod.GET, value = "/products/{productId}")
	ProductDto getProductById(@PathVariable("productId") String productId);

}