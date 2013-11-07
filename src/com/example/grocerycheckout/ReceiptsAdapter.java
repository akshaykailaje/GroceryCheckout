package com.example.grocerycheckout;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.grocerycheckout.models.Receipt;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ReceiptsAdapter extends ArrayAdapter<Receipt> {

	private static final DecimalFormat df = new DecimalFormat("#.##");
	private static DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	public ReceiptsAdapter(Context context, List<Receipt> receipts) {
		super(context, 0, receipts);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.receipt_item, null);
		}
		TextView tvTransactionDate = (TextView) v.findViewById(R.id.tvTransactionDate);
		TextView tvTransactionAmount = (TextView) v.findViewById(R.id.tvTransactionAmount);
		
		Receipt receipt = getItem(position);
		
		tvTransactionDate.setText(Html.fromHtml("<b>" + dateFormat.format(receipt.getTransactionDate()) + "<b>"));
		tvTransactionAmount.setText("$ " + df.format(receipt.getShoppingCart().getListPrice() + receipt.getShoppingCart().getTaxAmount()));
		return v;
	}

}
