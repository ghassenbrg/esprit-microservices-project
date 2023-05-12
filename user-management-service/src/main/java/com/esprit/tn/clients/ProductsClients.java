package com.esprit.tn.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "products", url = "${PRODUCTS_SERVICE_URL:http://localhost:8999/product-catalog/api}")
public interface ProductsClients {
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/products/{sellerId}")
    void deleteSellerProducts(@PathVariable("sellerId") Long sellerId);

}
