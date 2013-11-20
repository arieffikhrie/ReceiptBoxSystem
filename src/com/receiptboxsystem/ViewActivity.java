package com.receiptboxsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.receiptboxsystem.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class ViewActivity extends Activity {
	public final static String RECEIPT_ID = "com.receiptboxsystem.RECEIPT_ID";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		// Show the Up button in the action bar.
		setupActionBar();
		MySQLiteHelper db = new MySQLiteHelper(this);
		List<Receipt> receipts = db.getReceipts();
		ListView lv = (ListView) findViewById(R.id.listView1);
		
		ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String, String> >();
		HashMap<String, String> itemMap;
		
		for ( int i = 0 ; i < receipts.size(); i++ ){
			 itemMap = new HashMap<String, String>();
			 itemMap.put("id", String.valueOf(receipts.get(i).getId()));
			 itemMap.put("desc", receipts.get(i).getDesc());
			 itemList.add(itemMap);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(this,itemList,R.layout.view_row,new String[]{"id","desc"},new int[]{ R.id.row_id, R.id.row_desc});
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				LinearLayout current = (LinearLayout) view;
				TextView currentId = (TextView) current.getChildAt(0);
				//Toast.makeText(getApplicationContext(), currentId.getText().toString(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplication(),ReceiptActivity.class);
				intent.putExtra(RECEIPT_ID, currentId.getText().toString());
				startActivity(intent);
			}
			
		});
	}
	
	@Override
	 protected void onResume() {
		super.onResume();
		MySQLiteHelper db = new MySQLiteHelper(this);
		List<Receipt> receipts = db.getReceipts();
		ListView lv = (ListView) findViewById(R.id.listView1);
		
		ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String, String> >();
		HashMap<String, String> itemMap;
		
		for ( int i = 0 ; i < receipts.size(); i++ ){
			 itemMap = new HashMap<String, String>();
			 itemMap.put("id", String.valueOf(receipts.get(i).getId()));
		 itemMap.put("desc", receipts.get(i).getDesc());
			 itemList.add(itemMap);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(this,itemList,R.layout.view_row,new String[]{"id","desc"},new int[]{ R.id.row_id, R.id.row_desc});
		lv.setAdapter(adapter);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
