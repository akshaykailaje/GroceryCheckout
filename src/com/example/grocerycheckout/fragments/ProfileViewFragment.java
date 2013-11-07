package com.example.grocerycheckout.fragments;

import java.util.ArrayList;
import java.util.List;

import com.example.grocerycheckout.R;
import com.example.grocerycheckout.ReceiptsAdapter;
import com.example.grocerycheckout.models.Receipt;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

public class ProfileViewFragment extends Fragment {

	private ImageView ivProfilePic;
	private ListView lvReceipts;
	private List<Receipt> receipts;
	private ReceiptsAdapter receiptsAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragments_profile_view, container, false);
		ivProfilePic = (ImageView) v.findViewById(R.id.ivProfilePic);
		lvReceipts = (ListView) v.findViewById(R.id.lvReceipts);
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d("DEBUG", "Loading profile pic");
		ImageLoader.getInstance().displayImage("http://cdn.uproxx.com/wp-content/uploads/2013/02/tobias-headshot.png", ivProfilePic);
		receipts = Receipt.getMockReceipts();
		receiptsAdapter = new ReceiptsAdapter(getActivity(), receipts);
		lvReceipts.setAdapter(receiptsAdapter);
	}
}
