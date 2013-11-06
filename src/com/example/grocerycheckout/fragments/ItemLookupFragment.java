package com.example.grocerycheckout.fragments;


import java.util.List;

import com.example.grocerycheckout.LookupResultsItemAdapter;
import com.example.grocerycheckout.R;
import com.example.grocerycheckout.models.Product;
import com.example.grocerycheckout.models.ProductList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

public class ItemLookupFragment extends Fragment {

	private EditText etItemLookup;
	private ListView lvItemResult;
	private List<Product> products;
	private LookupResultsItemAdapter lookupResultAdapter;
	
	public static ItemLookupFragment newInstance(ProductList productList) {
		ItemLookupFragment fragment = new ItemLookupFragment();
		
		Bundle args = new Bundle();
		args.putSerializable("productList", productList);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragments_lookup_item, container, false);
		
		etItemLookup = (EditText) v.findViewById(R.id.etItemLookup);		
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if (getArguments() == null || !getArguments().containsKey("productList")) {
			return;
		}
		
		products = ((ProductList) getArguments().getSerializable("productList")).getProductList();
		lookupResultAdapter = new LookupResultsItemAdapter(getActivity(), products);
		etItemLookup.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				lookupResultAdapter.getFilter().filter(s);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
