package com.example.grocerycheckout.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Shopping cart which contains a list of items
 * @author akailaje
 *
 */
public class Cart {

	private List<CartItem> cartItems = new ArrayList<CartItem>();
	private double salesTaxPercentage;
	
	/**
	 * Get a list of cart items in the cart
	 * @return
	 */
	public List<CartItem> getCartItems() {
		return cartItems;
	}
	
	/**
	 * Add a cart item to the cart
	 * @param cartItem
	 */
	public void addCartItem(CartItem cartItem) {
		this.cartItems.add(cartItem);
	}
	
	/**
	 * Remove a cart item from the cart
	 * @param cartItem
	 * @return
	 */
	public boolean removeCartItem(CartItem cartItem) {
		return this.cartItems.remove(cartItem);
	}
	
	/**
	 * Remove a cart item from the cart
	 * @param position
	 * @return
	 */
	public boolean removeCartItem(int position) {
		this.cartItems.remove(position);
		return true;
	}
	
	/**
	 * Get a cart item from the list
	 * @param position
	 * @return
	 */
	public CartItem getCartItem(int position) {
		return this.cartItems.get(position);
	}
	
	/**
	 * Get the Sales tax percentage
	 * @return
	 */
	public double getSalesTaxPercentage() {
		return salesTaxPercentage;
	}
	
	/**
	 * Set the sales tax percentage
	 * @param salesTaxPercentage
	 */
	public void setSalesTaxPercentage(double salesTaxPercentage) {
		this.salesTaxPercentage = salesTaxPercentage;
	}
	
	/**
	 * Gets the tax amount. i.e. list price * tax %
	 * @return
	 */
	public double getTaxAmount() {	
		return ((getSalesTaxPercentage() / 100) * getListPrice()); 
	}
	
	/**
	 * Gets the sum of the list price of all the items in the cart 
	 * @return
	 */
	public double getListPrice() {
		double listPrice= 0.0d;
		for(CartItem item : this.cartItems) {
			listPrice += item.getListPrice();
		}
		return listPrice;
	}
	
	
	
	
}
