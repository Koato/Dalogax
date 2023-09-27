package com.kalagan.dalogax.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.kalagan.dalogax.dao.ProductDetail;
import com.kalagan.dalogax.dao.SimilarProduct;
import com.kalagan.dalogax.services.IProductService;
import com.kalagan.dalogax.services.feignclients.ProductFeignClients;

@Service("iProductService")
public class ProductImpl implements IProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ProductFeignClients productFeignClients;

	@SuppressWarnings("unchecked")
	@Override
	public List<SimilarProduct> getListProductSimilarId(String productId) {
		try {
			ResponseEntity<?> resultado = productFeignClients.getListProductSimilarId(productId);
			LOGGER.info("status: {}", resultado.getStatusCode());
			if (resultado.getStatusCode() == HttpStatus.OK) {
				LOGGER.info("resultado: {}", resultado.getBody());
				return (List<SimilarProduct>) resultado.getBody();
			}
		} catch (HttpClientErrorException.NotFound e) {
			LOGGER.error("The requested resource was not found.");
		} catch (Exception e) {
			LOGGER.error("The resource does not respond as expected.");
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public Optional<ProductDetail> getProductSimilar(String productId) {
		StringBuilder url = new StringBuilder("http://localhost:3001/product/");
		url.append(productId.trim());
		LOGGER.info("url: {}", url);
		try {
			ResponseEntity<?> resultado = restTemplate.getForEntity(url.toString(), ProductDetail.class);
			LOGGER.info("status: {}", resultado.getStatusCode());
			if (resultado.getStatusCode() == HttpStatus.OK) {
				LOGGER.info("resultado: {}", resultado.getBody());
				return Optional.of((ProductDetail) resultado.getBody());
			}
		} catch (HttpClientErrorException.NotFound e) {
			LOGGER.error("The requested resource was not found.");
		} catch (Exception e) {
			LOGGER.error("The resource does not respond as expected.");
		}
		return Optional.empty();
	}

}
