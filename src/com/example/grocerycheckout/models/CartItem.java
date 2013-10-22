package com.example.grocerycheckout.models;

/**
 * Represents an item in the shopping cart
 * @author akailaje
 *
 */
public class CartItem {

	private Product product;
	private int quantity = 0;
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		if (quantity >= 0) {
			this.quantity = quantity;
		}
	}
	
	public void increaseQuantity(int by) {
		if (by <= 0) {
			return;
		}
		this.quantity += by;
	}
	
	public void decreaseQuantity(int by) {
		if (by <= 0) {
			return;
		}
		
		if (this.quantity >= by) {
			this.quantity -= by;
		}
	}
	
	/**
	 * Utility function to get the list price of the item in the cart
	 * @return
	 */
	public double getListPrice() {
		if (product == null) {
			return 0.0d;
		}
		
		return (getQuantity() * getProduct().getPrice());
	}
	
}
