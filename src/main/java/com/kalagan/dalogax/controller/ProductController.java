package com.kalagan.dalogax.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalagan.dalogax.dao.ProductDetail;
import com.kalagan.dalogax.dao.SimilarProduct;
import com.kalagan.dalogax.services.IProductService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(value = "/product")
@CrossOrigin(origins = "*")
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private IProductService productService;

	@CircuitBreaker(name = "listCB", fallbackMethod = "fallBackGetListProducts")
	@GetMapping(value = "/{productId}/similar")
	public ResponseEntity<?> getListProductSimilarId(@PathVariable(required = true) String productId) {
		LOGGER.info("productId: {}", productId);
		List<SimilarProduct> similarProducts = productService.getListProductSimilarId(productId);
		LOGGER.info("similarProducts: {}", similarProducts);
		if (!ObjectUtils.isEmpty(similarProducts)) {
			return ResponseEntity.ok(similarProducts);
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<?> fallBackGetListProducts(RuntimeException e) {
		Map<String, String> map = new HashMap<>();
		map.put("message", "It was not possible to list all products with similar ids");
		return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
	}

	@CircuitBreaker(name = "similarCB", fallbackMethod = "fallBackGetSimilar")
	@GetMapping(value = "/{productId}")
	public ResponseEntity<?> getProductSimilar(@PathVariable(required = true) String productId) {
		LOGGER.info("productId: {}", productId);
		Optional<ProductDetail> optional = productService.getProductSimilar(productId);
		LOGGER.info("ProductDetail: {}", optional);
		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<?> fallBackGetSimilar(RuntimeException e) {
		Map<String, String> map = new HashMap<>();
		map.put("message", "A similar product could not be found");
		return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
	}
}
