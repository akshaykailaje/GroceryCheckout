package com.example.grocerycheckout.models;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Represents a product that can be purchased
 * @author akailaje
 *
 */
@Table(name="Products")
public class Product extends Model implements Serializable {

	private static final long serialVersionUID = 923520426711269933L;
	@Column(name="productId", unique=true, onUniqueConflict=ConflictAction.IGNORE,notNull=true)
	private long productId;
	@Column(name="barCode", unique=true, onUniqueConflict=ConflictAction.IGNORE,notNull=true)
	private long barCode;
	@Column(name="price")
	private double price;
	@Column(name="name")
	private String name;
	@Column(name="imageUrl")
	private String imageUrl;
	@Column(name="inventoryTotal")
	private long inventoryTotal;
	
	public long getProductId() {
		return productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public long getBarCode() {
		return barCode;
	}
	
	public void setBarCode(long barCode) {
		this.barCode = barCode;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public static Product getSampleProduct() {
		List<Product> products = getAllProducts();
		int randomIndex = Math.abs(new Random().nextInt() % products.size());
		return products.get(randomIndex);
	}
	
	public static List<Product> getAllProducts() {
		List<Product> products = new Select()
		.from(Product.class)
		.orderBy("productId")
		.execute();
		Log.d("DEBUG", "total product count in db="+products.size());
		return products;
	}
	
	public static List<Product> getProductsByNamePattern(String namePattern) {
		List<Product> products = new Select()
									.from(Product.class)
									.orderBy("name")
									.where("name LIKE '%"+namePattern+"%'")
									.execute();
		Log.d("DEBUG", "total product count after filter ("+namePattern+") ="+products.size());
		return products;
	}

	public long getInventoryTotal() {
		return inventoryTotal;
	}

	public void setInventoryTotal(long inventoryTotal) {
		this.inventoryTotal = inventoryTotal;
	}
	
	@Override
	public String toString() {
		return String.format("{ productId=%d, barCode=%d, name=%s, price=%.2f, inventoryTotal=%d, imageUrl=%s }", getProductId(), getBarCode(), getName(), getPrice(), getInventoryTotal(), getImageUrl());
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		} else if (this == other) {
			return true;
		}
		
		if (other instanceof Product) {
			Product otherProduct = (Product) other;
			return otherProduct.getProductId() == getProductId();
		}
		
		return false;
	}
}
