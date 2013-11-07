package com.example.grocerycheckout.interfaces;

import java.io.Serializable;

import com.example.grocerycheckout.models.CartItem;

public abstract class GroceryCartUpdateListener implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	abstract public void onCartItemDelete(CartItem ci);
	abstract public void onCartItemEdit(CartItem ci);
	abstract public void onView();
}
