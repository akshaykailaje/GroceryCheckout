package com.example.grocerycheckout.fragments;


import java.text.DecimalFormat;

import com.example.grocerycheckout.R;
import com.example.grocerycheckout.models.CartItem;
import com.example.grocerycheckout.models.Product;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

public class ProductDetailFragment extends Fragment {

	private Product productToDisplay;
	private static final DecimalFormat df = new DecimalFormat("#.##");
	private NumberPicker npQuantity;
	private TextView tvListPrice;
	private TextView tvTotalPrice;
	private TextView tvProductDescription;
	private ImageView ivProductImage;
	private Button btnCancel;
	private Button btnAddItem;
	
	public static ProductDetailFragment newInstance (Product productToDisplay) {
		ProductDetailFragment fragment = new ProductDetailFragment();
		
		Bundle args = new Bundle();
		args.putSerializable("productToDisplay", productToDisplay);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragments_product_detail, container, false);
		ivProductImage = (ImageView) v.findViewById(R.id.ivProductImage);
		npQuantity = (NumberPicker) v.findViewById(R.id.npQuantity);
		npQuantity.setMaxValue(100);
		npQuantity.setMinValue(1);
		tvProductDescription = (TextView) v.findViewById(R.id.tvProductDescription);
		tvListPrice = (TextView) v.findViewById(R.id.tvListPrice);
		tvTotalPrice = (TextView) v.findViewById(R.id.tvTotalPrice);
		btnCancel = (Button) v.findViewById(R.id.btnCancel);
		btnAddItem = (Button) v.findViewById(R.id.btnAddItem);
		
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ImageLoader.getInstance().displayImage(productToDisplay.getImageUrl(), ivProductImage);
		
		tvProductDescription.setText(productToDisplay.getName());
		
		tvListPrice.setText("$ " + df.format(productToDisplay.getPrice()));
		
		tvTotalPrice.setText(Html.fromHtml("<b>$ " + df.format(productToDisplay.getPrice() * npQuantity.getValue()) + "</b>"));
		
		npQuantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				// calculate the total quantity
				tvTotalPrice.setText(Html.fromHtml("<b>$ " + (df.format(newVal * productToDisplay.getPrice())) + "</b>"));
			}
		});
		
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent cancelResult = new Intent();
				getActivity().setResult(FragmentActivity.RESULT_CANCELED, cancelResult);
				getActivity().finish();
			}
		});
		
		btnAddItem.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent addItemResult = new Intent();
				CartItem ci = new CartItem();
				ci.setProduct(productToDisplay);
				ci.setQuantity(npQuantity.getValue());
				addItemResult.putExtra("cartItem", ci);
				getActivity().setResult(FragmentActivity.RESULT_OK, addItemResult);
				getActivity().finish();
			}
		});
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getArguments() == null || !getArguments().containsKey("productToDisplay")) {
			return;
		}
		productToDisplay = (Product) getArguments().getSerializable("productToDisplay");
		Log.d("DEBUG", "arguments product="+productToDisplay.toString());
	}
	
}
