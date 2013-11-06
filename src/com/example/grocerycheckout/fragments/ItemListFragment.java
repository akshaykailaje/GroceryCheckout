package com.example.grocerycheckout.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.grocerycheckout.CartItemsAdapter;
import com.example.grocerycheckout.R;
import com.example.grocerycheckout.ShowCartActivity;
import com.example.grocerycheckout.interfaces.GroceryCartUpdateListener;
import com.example.grocerycheckout.models.Cart;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.google.zxing.integration.android.IntentIntegrator;

public class ItemListFragment extends Fragment {

	private Cart shoppingCart;
	private CartItemsAdapter cartItemsAdapter;
	// private ListView lvCartItems;
	// private RelativeLayout rlEmptyCart;
	private Button btnScan;
	private GroceryCartUpdateListener listener;
	
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
		listener = ((ShowCartActivity) getActivity()).getCartUpdateListener();
		cartItemsAdapter = new CartItemsAdapter(getActivity(), shoppingCart.getCartItems(), listener);
		SwipeListView slvCartItems = (SwipeListView) getView().findViewById(R.id.lvCartItems);
		slvCartItems.setAdapter(cartItemsAdapter);
		slvCartItems.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		
		if (shoppingCart.getCartItems().size() > 0) {
			showList();
		}
	}
	
	public void showList() {
		RelativeLayout rlEmptyCart = (RelativeLayout) getView().findViewById(R.id.rlEmptyCart);
		SwipeListView slvCartItems = (SwipeListView) getView().findViewById(R.id.lvCartItems);
		slvCartItems.setVisibility(View.VISIBLE);
		rlEmptyCart.setVisibility(View.INVISIBLE);
	}
	
	public void hideList() {
		RelativeLayout rlEmptyCart = (RelativeLayout) getView().findViewById(R.id.rlEmptyCart);
		SwipeListView slvCartItems = (SwipeListView) getView().findViewById(R.id.lvCartItems);
		slvCartItems.setVisibility(View.INVISIBLE);
		rlEmptyCart.setVisibility(View.VISIBLE);
	}
	
	
	
	public Cart getShoppingCart() {
		return shoppingCart;
	}
	
	public CartItemsAdapter getCartItemsAdapter() {
		return cartItemsAdapter;
	}
}
