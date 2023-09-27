package com.kalagan.dalogax.services.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
		StringBuilder url = new StringBuilder("http://localhost:3001/");
		url.append("product/");
		url.append(productId.trim());
		url.append("/similarids");
		LOGGER.info("url: {}", url);
		List<SimilarProduct> resultado = restTemplate.getForObject(url.toString(), List.class);
		LOGGER.info("resultado: {}", resultado.toString());
		return resultado;
	}

	@Override
	public Optional<ProductDetail> getProductSimilar(String productId) {
		StringBuilder url = new StringBuilder("http://localhost:3001/");
		url.append("product/");
		url.append(productId.trim());
		LOGGER.info("url: {}", url);
		ProductDetail resultado = restTemplate.getForObject(url.toString(), ProductDetail.class);
		LOGGER.info("resultado: {}", resultado);
		return Optional.ofNullable(resultado);
	}

}
