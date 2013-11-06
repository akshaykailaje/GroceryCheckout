package com.example.grocerycheckout.models;

import java.io.Serializable;
import java.util.List;

public class ProductList implements Serializable {

	private static final long serialVersionUID = -921910654366278426L;
	private List<Product> productList;
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	
	

}
