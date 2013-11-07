package com.example.grocerycheckout.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Receipt implements Serializable {

	private static final long serialVersionUID = -8181042397607538227L;
	private long transactionId;
	private Date transactionDate;
	private Cart shoppingCart;
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Cart getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(Cart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
	public static List<Receipt> getMockReceipts() {
		Calendar cal = Calendar.getInstance();
		List<Receipt> receipts = new ArrayList<Receipt>();
		List<Product> allProducts = Product.getAllProducts();
		for (int i = 0; i < 4; i++) {
			Receipt r = new Receipt();
			cal.add(Calendar.DAY_OF_MONTH, i * -2);
			r.setTransactionId(i);
			r.setTransactionDate(cal.getTime());
			
			Cart c = new Cart();
			int numberOfCartItems = new Random().nextInt(allProducts.size()) + 1;
			for (int j = 0; j < numberOfCartItems; j++) {
				CartItem ci = new CartItem();
				ci.setProduct(allProducts.get(new Random().nextInt(allProducts.size() - 1)));
				ci.setQuantity(1);
				c.addOrUpdateCartItem(ci, 1);
			}
			c.setSalesTaxPercentage(7.1d);
			r.setShoppingCart(c);
			receipts.add(r);
		}
		
		return receipts;
		
	}
}
