package com.example.grocerycheckout.models;

import java.io.Serializable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column.ForeignKeyAction;

/**
 * Represents an item in the shopping cart
 * @author akailaje
 *
 */
@Table(name="CartItems")
public class CartItem extends Model implements Serializable {

	private static final long serialVersionUID = -2471177175707956487L;
	
	@Column(name="Products",onDelete=ForeignKeyAction.CASCADE,onUpdate=ForeignKeyAction.CASCADE)
	private Product product;
	@Column(name="quantity")
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
	
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		} else if (this == other) {
			return true;
		}
		
		if (getProduct() == null) {
			return false;
		}
		
		if (other instanceof CartItem) {
			CartItem otherCi = (CartItem) other;
			return otherCi.getProduct().equals(getProduct());
		}
		return false;
	}
	
}
