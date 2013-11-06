package com.example.grocerycheckout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.grocerycheckout.models.Product;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class LookupResultsItemAdapter extends ArrayAdapter<Product> implements Filterable {

	private static final DecimalFormat df = new DecimalFormat("#.##");
	private Context context;
	
	public LookupResultsItemAdapter(Context context, List<Product> products) {
		
		super(context, 0, products);
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Product p = getItem(position);
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.result_item, null);
		}
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context , ProductDetailActivity.class);
				i.putExtra("productToDisplay", p);
				((FragmentActivity) context).startActivityForResult(i, 1);
			}
		});
		ImageView ivResultImage = (ImageView) view.findViewById(R.id.ivResultImage);
		TextView tvResultItemName = (TextView) view.findViewById(R.id.tvResultItemName);
		TextView tvResultItemPrice = (TextView) view.findViewById(R.id.tvResultPrice);
		ImageLoader.getInstance().displayImage(p.getImageUrl(), ivResultImage);
		tvResultItemName.setText(p.getName());
		tvResultItemPrice.setText("$ " + df.format(p.getPrice()));
		return view;
	}

	@Override
	public Filter getFilter() {
		
		return new Filter() {
			
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				List<Product> products = (List<Product>) results.values; 
				LookupResultsItemAdapter.this.clear();
				LookupResultsItemAdapter.this.addAll(products);
				LookupResultsItemAdapter.this.notifyDataSetChanged();
			}
			
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();
				List<Product> products;
				if (constraint == null || constraint.length() == 0) { 
					products = Product.getAllProducts();
					
				} else {
					products = Product.getProductsByNamePattern(constraint.toString());
				}
				
				results.values = products;
				results.count = products.size();
				return results;
			}
		};
	}
}
