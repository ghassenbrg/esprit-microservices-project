package com.esprit.tn.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

    @FeignClient(value = "quantity", url = "${PRODUCTS_SERVICE_URL:http://localhost:8999/product-catalog/api}")
public interface QuantityClient {

	@RequestMapping(method = RequestMethod.DELETE, value = "/products/{sellerId}")
    void deleteSellerProducts(@PathVariable("sellerId") Long sellerId);

}

