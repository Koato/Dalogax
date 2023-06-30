package com.kalagan.dalogax.controller;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kalagan.dalogax.dao.ProductDetail;
import com.kalagan.dalogax.dao.SimilarProduct;
import com.kalagan.dalogax.services.IProductService;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private IProductService productService;

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
}
