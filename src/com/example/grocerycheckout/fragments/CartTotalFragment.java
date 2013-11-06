package com.example.grocerycheckout.fragments;

import java.text.DecimalFormat;

import com.example.grocerycheckout.R;
import com.example.grocerycheckout.models.Cart;
import com.example.grocerycheckout.models.Product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CartTotalFragment extends Fragment {
	
	private TextView tvTotal;
	private TextView tvTaxText;
	private TextView tvTax;
	private TextView tvCartTotal;
	private Button btnCheckout;
	private Cart shoppingCart;
	
	private static final DecimalFormat df = new DecimalFormat("#.##");
	
	public static CartTotalFragment newInstance(Cart shoppingCart) {
		CartTotalFragment fragment = new CartTotalFragment();
		
		Bundle args = new Bundle();
		args.putSerializable("shoppingCart", shoppingCart);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragments_cart_total, container, false);
		
		tvTotal = (TextView) v.findViewById(R.id.tvTotal);
		tvTaxText = (TextView) v.findViewById(R.id.tvTaxText);
		tvTax = (TextView) v.findViewById(R.id.tvTax);
		tvCartTotal = (TextView) v.findViewById(R.id.tvCartTotal);
		btnCheckout = (Button) v.findViewById(R.id.btnCheckout);
		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getArguments() == null || !getArguments().containsKey("shoppingCart")) {
			return;
		}
		shoppingCart = (Cart) getArguments().getSerializable("shoppingCart");
		Log.d("DEBUG", "shopping cart="+shoppingCart.toString());
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		updateTotal();
	}
	
	public Cart getShoppingCart() {
		return shoppingCart;
	}
	
	public void updateTotal() {
		
		if (shoppingCart == null) {
			return;
		}
		
		tvTotal.setText("$ " + df.format(shoppingCart.getListPrice()));
		tvTaxText.setText("Tax@" + df.format(shoppingCart.getSalesTaxPercentage()) + " %");
		tvTax.setText("$ " + df.format(shoppingCart.getTaxAmount()));
		tvCartTotal.setText("$ " + df.format(shoppingCart.getListPrice() + shoppingCart.getTaxAmount()));
	}

}
