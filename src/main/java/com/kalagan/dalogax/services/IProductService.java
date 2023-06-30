package com.kalagan.dalogax.services;

import java.util.List;
import java.util.Optional;

import com.kalagan.dalogax.dao.ProductDetail;
import com.kalagan.dalogax.dao.SimilarProduct;

public interface IProductService {

	List<SimilarProduct> getListProductSimilarId(String productId);
	
	Optional<ProductDetail> getProductSimilar(String productId);
}
