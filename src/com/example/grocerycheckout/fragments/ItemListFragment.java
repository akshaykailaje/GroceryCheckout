package com.example.grocerycheckout.fragments;

import com.example.grocerycheckout.CartItemsAdapter;
import com.example.grocerycheckout.R;
import com.example.grocerycheckout.models.Cart;
import com.google.zxing.integration.android.IntentIntegrator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ItemListFragment extends Fragment {

	private Cart shoppingCart;
	private CartItemsAdapter cartItemsAdapter;
	// private ListView lvCartItems;
	// private RelativeLayout rlEmptyCart;
	private Button btnScan;
	
	public static ItemListFragment newInstance(Cart shoppingCart) {
		ItemListFragment fragment = new ItemListFragment();
		
		Bundle args = new Bundle();
		args.putSerializable("shoppingCart", shoppingCart);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragments_item_list, container, false);
		// rlEmptyCart = (RelativeLayout) v.findViewById(R.id.rlEmptyCart);
		// lvCartItems = (ListView) v.findViewById(R.id.lvCartItems);
		btnScan = (Button) v.findViewById(R.id.btnScan);
		btnScan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("DEBUG", "Hello");
		    	IntentIntegrator integrator = new IntentIntegrator(getActivity());
		    	integrator.initiateScan();
				
			}
		});
		
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (getArguments() == null || !getArguments().containsKey("shoppingCart")) {
			return;
		}
		
		shoppingCart = (Cart) getArguments().getSerializable("shoppingCart");
		cartItemsAdapter = new CartItemsAdapter(getActivity(), shoppingCart.getCartItems());
		ListView lvCartItems = (ListView) getView().findViewById(R.id.lvCartItems);
		lvCartItems.setAdapter(cartItemsAdapter);
		
		if (shoppingCart.getCartItems().size() > 0) {
			showList();
		}
	}
	
	public void showList() {
		RelativeLayout rlEmptyCart = (RelativeLayout) getView().findViewById(R.id.rlEmptyCart);
		ListView lvCartItems = (ListView) getView().findViewById(R.id.lvCartItems);
		lvCartItems.setVisibility(View.VISIBLE);
		rlEmptyCart.setVisibility(View.INVISIBLE);
	}
	
	public void hideList() {
		RelativeLayout rlEmptyCart = (RelativeLayout) getView().findViewById(R.id.rlEmptyCart);
		ListView lvCartItems = (ListView) getView().findViewById(R.id.lvCartItems);
		lvCartItems.setVisibility(View.INVISIBLE);
		rlEmptyCart.setVisibility(View.VISIBLE);
	}
	
	public Cart getShoppingCart() {
		return shoppingCart;
	}
	
	public CartItemsAdapter getCartItemsAdapter() {
		return cartItemsAdapter;
	}
}
