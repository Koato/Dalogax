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

@Service("iProductService")
public class ProductImpl implements IProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<SimilarProduct> getListProductSimilarId(String productId) {
		StringBuilder url = new StringBuilder("http://localhost:3001/product/");
		url.append(productId.trim());
		url.append("/similarids");
		LOGGER.info("url: {}", url);
		try {
			ResponseEntity<?> resultado = restTemplate.getForEntity(url.toString(), List.class);
			LOGGER.info("status: {}", resultado.getStatusCode());
			if (resultado.getStatusCode() == HttpStatus.OK) {
				LOGGER.info("resultado: {}", resultado.getBody());
				return (List<SimilarProduct>) resultado.getBody();
			}
		} catch (HttpClientErrorException.NotFound e) {
			LOGGER.error("El recurso solicitado no fue encontrado.");
		} catch (Exception e) {
			LOGGER.error("El recurso no responde segun lo esperado.");
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
			LOGGER.error("El recurso solicitado no fue encontrado.");
		} catch (Exception e) {
			LOGGER.error("El recurso no responde segun lo esperado.");
		}
		return Optional.empty();
	}

}
