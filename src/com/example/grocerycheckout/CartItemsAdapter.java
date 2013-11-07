package com.example.grocerycheckout;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.grocerycheckout.interfaces.GroceryCartUpdateListener;
import com.example.grocerycheckout.models.CartItem;

public class CartItemsAdapter extends ArrayAdapter<CartItem> {

	private GroceryCartUpdateListener listener;
	private static final DecimalFormat df = new DecimalFormat("#.##");
	public CartItemsAdapter(Context context, List<CartItem> cartItems, GroceryCartUpdateListener listener) {
		super(context, R.layout.cart_item, cartItems);
		// TODO Auto-generated constructor stub
		this.listener = listener;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final CartItem ci= this.getItem(position);
		ViewHolder holder;
		// TODO Auto-generated method stub
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			//LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.cart_item, parent, false);
			holder = new ViewHolder();
			holder.tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
			holder.tvProductName = (TextView) view.findViewById(R.id.tvProductName);
			holder.tvCartItemPrice = (TextView) view.findViewById(R.id.tvCartItemPrice);
			holder.btnDelete = (Button) view.findViewById(R.id.btnDelete);
			holder.btnEdit = (Button) view.findViewById(R.id.btnEdit);
			holder.btnView = (Button) view.findViewById(R.id.btnView);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.tvCartItemPrice.setText("$ " + df.format(ci.getProduct().getPrice()));
		holder.tvProductName.setText(ci.getProduct().getName());
		holder.tvQuantity.setText(String.valueOf(ci.getQuantity()));
		holder.btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				remove(ci);
				listener.onCartItemDelete(ci);
			}
			
		});
		
		holder.btnEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener.onCartItemEdit(ci);
			}
			
		});
		
		holder.btnView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener.onView(ci);
			}
			
		});
		
		return view;
	}
	
	static class ViewHolder {
        TextView tvCartItemPrice;
        TextView tvQuantity;
        TextView tvProductName;
        Button btnDelete;
        Button btnEdit;
        Button btnView;
    }
}
