package com.example.grocerycheckout;

import com.example.grocerycheckout.fragments.ProductDetailFragment;
import com.example.grocerycheckout.models.Product;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class ProductDetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_detail);
		
		Product productToDisplay = (Product) getIntent().getSerializableExtra("productToDisplay");
		int quantity =1;
		
		
		if (productToDisplay == null) {
			productToDisplay = Product.getSampleProduct();
		} else {
			if (getIntent().hasExtra("quantity")) {
				quantity = getIntent().getExtras().getInt("quantity");
			}
			
		}
		
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction fst = manager.beginTransaction();
		fst.add(R.id.flProductDetail, ProductDetailFragment.newInstance(productToDisplay, quantity));
		fst.commit();
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_detail, menu);
		return true;
	}

}
