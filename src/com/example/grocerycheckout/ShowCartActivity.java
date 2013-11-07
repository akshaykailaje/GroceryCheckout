package com.example.grocerycheckout;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.example.grocerycheckout.fragments.CartTotalFragment;
import com.example.grocerycheckout.fragments.ItemListFragment;
import com.example.grocerycheckout.fragments.ItemLookupFragment;
import com.example.grocerycheckout.fragments.ProfileViewFragment;
import com.example.grocerycheckout.interfaces.GroceryCartUpdateListener;
import com.example.grocerycheckout.models.Cart;
import com.example.grocerycheckout.models.CartItem;
import com.example.grocerycheckout.models.Product;
import com.example.grocerycheckout.models.ProductList;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ShowCartActivity extends FragmentActivity implements TabListener {

	private static List<String> productList = new ArrayList<String>();
	private ItemListFragment itemListFragment;
	private CartTotalFragment cartTotalFragment;
	private ItemLookupFragment itemLookupFragment;
	private ProfileViewFragment profileViewFragment;
	private GroceryCartUpdateListener listener;
	
	
	static {
		productList.add("http://thumbs.ebaystatic.com/m/m-Nv9ane8yiab7e9NnAKqZw/96.jpg,Hershey's Chocolate Bar,0.99,80");
		productList.add("http://www.vitacost.com/Images/Products/50/Head-And-Shoulders/Head-And-Shoulders-Dandruff-Shampoo-Dry-Scalp-Care-with-Almond-Oil-037000012146.jpg,Head and Shoulders Shampoo,21.99,100");
		productList.add("http://img3.targetimg3.com/wcsstore/TargetSAS//img/p/14/76/14766952_201309121230_50x50.jpg,Fiber One Cereal,4.99,20");
		productList.add("http://img1.targetimg1.com/wcsstore/TargetSAS//img/p/12/99/12992473_201307191112_50x50.jpg,Kellogg's Nutri Grain,5.99,30");
		productList.add("http://c.shld.net/rpx/i/s/pi/mp/14226/1893841702?src=http%3A%2F%2Fwww.farm-home.com%2Fimages%2Fjb76%2F69051076c.jpg&d=fce6dcfe03dc8fdb55aed3cb15d7c4daf630b91a,Mini Chips Ahoy Chocolate Chip Cookies,0.99,50");
		productList.add("http://img2.targetimg2.com/wcsstore/TargetSAS//img/p/13/58/13587184_201308161222_50x50.jpg,Tropicana Orange Juice,5.99,10");
		productList.add("http://img1.targetimg1.com/wcsstore/TargetSAS//img/p/12/96/12965659_50x50.jpg,Bread,3.99,45");
		productList.add("http://i01.i.aliimg.com/wsphoto/v0/1123663111_4/Free-Shipping-Strawberry-Seeds-6-Color-6-Pack-Each-Pack-50-Seeds-Total-300-Strawberry-Foloer.jpg_50x50.jpg,Organic Strawberries,5,10");
		productList.add("http://img2.targetimg2.com/wcsstore/TargetSAS//img/p/12/94/12945730_201307221156_50x50.jpg,Ritz Crackers,4.99,50");
		productList.add("http://cdn2.bigcommerce.com/server300/ac7db/products/83045/images/10180/965_nescof100g__85175.1303164876.120.120.png,Nescafe Instant Coffee,13.99,60");
		productList.add("http://ratetea.com/images/tea/105.jpg,Lipton Tea,9.99,60");
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_cart);
		
		ActiveAndroid.beginTransaction();
        try {
        	for (int i = 0; i < productList.size(); i++) {
        		String[] splitLine = productList.get(i).split(",");
        		Product product = new Product();
        		product.setProductId(i + 1);
        		product.setBarCode(100000000L * (i + 1));
        		product.setImageUrl(splitLine[0]);
        		product.setName(splitLine[1]);
        		product.setPrice(Double.valueOf(splitLine[2]));
        		product.setInventoryTotal(Long.valueOf(splitLine[3]));
        		// Log.d("DEBUG", product.toString());
        		product.save();
        	}

        	ActiveAndroid.setTransactionSuccessful();
        } finally {
        	ActiveAndroid.endTransaction();
        }
        
        Cart shoppingCart = new Cart();
        shoppingCart.setSalesTaxPercentage(7.1d);
        ProductList list = new ProductList();
        list.setProductList(Product.getAllProducts());
        itemListFragment = ItemListFragment.newInstance(shoppingCart);
        cartTotalFragment = CartTotalFragment.newInstance(shoppingCart);
        itemLookupFragment = ItemLookupFragment.newInstance(list);
        profileViewFragment = new ProfileViewFragment();
        
		setupNavigationTabs();
		Log.d("DEBUG", "Done create");
	}
	
	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				
		Tab tabCart = actionBar.newTab()
						//.setText("Cart")
						.setTag("CartTab")
						.setIcon(R.drawable.ic_launcher)
						.setTabListener(this);
		
		Tab tabProfile = actionBar.newTab()
							//.setText("Profile")
							.setTag("ProfileTab")
							.setIcon(R.drawable.ic_profile)
							.setTabListener(this);
		
		Tab tabLookup = actionBar.newTab()
							//.setText("Lookup Item")
							.setTag("LookupTab")
							.setIcon(R.drawable.ic_lookup)
							.setTabListener(this);
		
		actionBar.addTab(tabCart);
		actionBar.addTab(tabProfile);
		actionBar.addTab(tabLookup);
		
		actionBar.selectTab(tabCart);
	}
	
	public GroceryCartUpdateListener getCartUpdateListener() {
		return new GroceryCartUpdateListener() {

			@Override
			public void onCartItemDelete(CartItem ci) {
				// TODO Auto-generated method stub
				cartTotalFragment.updateTotal();
			}

			@Override
			public void onCartItemEdit(CartItem ci) {
				// TODO Auto-generated method stub
	    		Intent i = new Intent(ShowCartActivity.this, ProductDetailActivity.class);
	    		i.putExtra("productToDisplay", ci.getProduct());
	    		i.putExtra("quantity", ci.getQuantity());
	    		startActivityForResult(i, 2);
			}
			
			public void onView(CartItem ci) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" +
			ci.getProduct().getName()));
				startActivity(browserIntent);
			}

		};
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_cart, menu);
		return true;
	}
	
	public void onItemAddClick(MenuItem mi) {
    	Log.d("DEBUG", "Hello");
    	IntentIntegrator integrator = new IntentIntegrator(this);
    	integrator.initiateScan();
    }
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	
    	IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
    	if (scanResult != null && scanResult.getContents() != null) {
    		Log.d("DEBUG", "Returned from barcode scanner");
    		// scan result
    		Log.d("DEBUG", "scan result = " + scanResult.getContents());
    		// Toast.makeText(ShowCartActivity.this, "scan result = " + scanResult.getContents(), Toast.LENGTH_LONG).show();
    		Intent i = new Intent(ShowCartActivity.this, ProductDetailActivity.class);
    		startActivityForResult(i, 1);
    		
    	} else if (requestCode == 1 || requestCode == 2) { // add item
    		
    		if (resultCode == RESULT_OK) {
    			CartItem ci = (CartItem) intent.getSerializableExtra("cartItem");
    			Toast.makeText(ShowCartActivity.this, ci.getProduct().getName()+" added to the cart", Toast.LENGTH_LONG).show();
    			
    			
    			itemListFragment.getShoppingCart().addOrUpdateCartItem(ci, requestCode);
    			itemListFragment.getCartItemsAdapter().notifyDataSetChanged();
    			
    			cartTotalFragment.updateTotal();
    			itemListFragment.showList();
    			getActionBar().selectTab(getActionBar().getTabAt(0));
    			
    		} else if (resultCode == RESULT_CANCELED) {
    			Toast.makeText(ShowCartActivity.this, "No item added", Toast.LENGTH_LONG).show();
    		}
    		
    	} else {
    		Log.d("DEBUG", "scan result is null");
    	}
    	
    }

	@Override
	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		
		FragmentManager manager = getSupportFragmentManager();
		
		if (tab.getTag().equals("CartTab")) {
			
			FragmentTransaction fst = manager.beginTransaction();
			Fragment cartFragment = manager.findFragmentByTag("cartItemsFragment");
			Cart shoppingCart;
			if (cartFragment == null) {
				shoppingCart = new Cart();
				shoppingCart.setSalesTaxPercentage(7.1d);
			} else {
				shoppingCart = ((ItemListFragment) cartFragment).getShoppingCart();
				if (shoppingCart != null) {
					Log.d("DEBUG", "On Tab select, shopping cart="+shoppingCart.toString());
					fst.remove(manager.findFragmentByTag("cartTotalFragment"));
					fst.remove(cartFragment);
				} else {
					shoppingCart = new Cart();
					shoppingCart.setSalesTaxPercentage(7.1d);
				}
			}
			
			fst.addToBackStack(null);
			fst.add(R.id.llCart, itemListFragment, "cartItemsFragment");
			fst.add(R.id.llCart, cartTotalFragment, "cartTotalFragment");
			fst.commitAllowingStateLoss();
			
		} else if (tab.getTag().equals("LookupTab")) {
			Log.d("DEBUG", "Lookup tab selected ");
			FragmentTransaction fst = manager.beginTransaction();
			
			Fragment lookupFragment = manager.findFragmentByTag("itemLookupFragment");
			if (lookupFragment != null) {
				fst.remove(lookupFragment);
			}
			fst.addToBackStack(null);
			fst.replace(R.id.llCart, itemLookupFragment, "itemLookupFragment");
			fst.commitAllowingStateLoss();
		} else if (tab.getTag().equals("ProfileTab")) {
			Log.d("DEBUG", "Profile tab selected ");
			FragmentTransaction fst = manager.beginTransaction();
			
			Fragment profileViewFragment = manager.findFragmentByTag("profileViewFragment");
			if (profileViewFragment != null) {
				fst.remove(profileViewFragment);
			}
			fst.addToBackStack(null);
			fst.replace(R.id.llCart, this.profileViewFragment, "profileViewFragment");
			fst.commitAllowingStateLoss();
		}
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {

		FragmentManager manager = getSupportFragmentManager();
		if (tab.getTag().equals("LookupTab")) {
			FragmentTransaction fst = manager.beginTransaction();
			Fragment lookupFragment = manager.findFragmentByTag("itemLookupFragment");
			if (lookupFragment != null) {
				fst.remove(lookupFragment);
			}
			
			fst.commitAllowingStateLoss();
		} else if (tab.getTag().equals("CartTab")) {
			FragmentTransaction fst = manager.beginTransaction();
			fst.remove(manager.findFragmentByTag("cartItemsFragment"));
			fst.remove(manager.findFragmentByTag("cartTotalFragment"));
			fst.commitAllowingStateLoss();
		} else if (tab.getTag().equals("ProfileTab")) {
			FragmentTransaction fst = manager.beginTransaction();
			fst.remove(manager.findFragmentByTag("profileViewFragment"));
			fst.commitAllowingStateLoss();
		}
		
	}

}
