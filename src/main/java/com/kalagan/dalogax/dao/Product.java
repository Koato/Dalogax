package com.kalagan.dalogax.dao;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Min(value = 1)
	private String id;
	
	@Min(value = 1)
	private String name;
	
	private Double price;
	
	private Boolean availability;
}
