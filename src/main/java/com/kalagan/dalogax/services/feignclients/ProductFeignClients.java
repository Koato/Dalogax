package com.kalagan.dalogax.services.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "mocks", url = "http://localhost:3001")
@RequestMapping(value = "/product")
public interface ProductFeignClients {

	@PostMapping(value = "/{productId}")
	public ResponseEntity<?> getProductSimilar(@PathVariable String productId);

	@GetMapping(value = "/{productId}/similarids")
	public ResponseEntity<?> getListProductSimilarId(@PathVariable String productId);
}
