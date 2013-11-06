package com.example.grocerycheckout;

import java.text.DecimalFormat;
import java.util.List;

import com.example.grocerycheckout.models.Product;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LookupResultsItemAdapter extends ArrayAdapter<Product> {

	private static final DecimalFormat df = new DecimalFormat("#.##");
	public LookupResultsItemAdapter(Context context, List<Product> products) {
		super(context, android.R.layout.simple_selectable_list_item, products);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Product p = getItem(position);
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.result_item, null);
		}
		Log.d("DEBUG", "lookup item adapter, position="+position+", product="+p.toString());
		ImageView ivResultImage = (ImageView) view.findViewById(R.id.ivResultImage);
		TextView tvResultItemName = (TextView) view.findViewById(R.id.tvResultItemName);
		TextView tvResultItemPrice = (TextView) view.findViewById(R.id.tvResultPrice);
		ImageLoader.getInstance().displayImage(p.getImageUrl(), ivResultImage);
		tvResultItemName.setText(p.getName());
		tvResultItemPrice.setText("$ " + df.format(p.getPrice()));
		return view;
	}

}
