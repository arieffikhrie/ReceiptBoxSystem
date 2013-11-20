package com.receiptboxsystem;

import com.example.receiptboxsystem.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

public class ReceiptActivity extends Activity {
	public final static String RECEIPT_ID = "com.receiptboxsystem.RECEIPT_ID";
	private MySQLiteHelper db;
	private String receiptId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receipt);
		// Show the Up button in the action bar.
		setupActionBar();
		Intent intent = getIntent();
		receiptId = intent.getStringExtra(ViewActivity.RECEIPT_ID);
		db = new MySQLiteHelper(this);
		Receipt r = db.getReceipt(Integer.parseInt(receiptId));
		
		ImageView imgPreview = (ImageView) findViewById(R.id.imageView1 );
		TextView descText = (TextView) findViewById(R.id.desc_view_text);
		TextView locationText = (TextView) findViewById(R.id.location_view_text);
		TextView amountText = (TextView) findViewById(R.id.amount_view_text);
		TextView dateText = (TextView) findViewById(R.id.date_view_text);
		TextView remarkText = (TextView) findViewById(R.id.remark_view_text);
		descText.setText(r.getDesc());
		locationText.setText(r.getLocation());
		amountText.setText(r.getAmount());
		dateText.setText(r.getDate());
		remarkText.setText(r.getRemark());
		
		final Bitmap bitmap = BitmapFactory.decodeFile(r.getImgUrl());
        imgPreview.setImageBitmap(bitmap);
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
	protected void onResume(){
		super.onResume();
		Receipt r = db.getReceipt(Integer.parseInt(receiptId));
		
		ImageView imgPreview = (ImageView) findViewById(R.id.imageView1 );
		TextView descText = (TextView) findViewById(R.id.desc_view_text);
		TextView locationText = (TextView) findViewById(R.id.location_view_text);
		TextView amountText = (TextView) findViewById(R.id.amount_view_text);
		TextView dateText = (TextView) findViewById(R.id.date_view_text);
		TextView remarkText = (TextView) findViewById(R.id.remark_view_text);
		descText.setText(r.getDesc());
		locationText.setText(r.getLocation());
		amountText.setText(r.getAmount());
		dateText.setText(r.getDate());
		remarkText.setText(r.getRemark());
		
		final Bitmap bitmap = BitmapFactory.decodeFile(r.getImgUrl());
        imgPreview.setImageBitmap(bitmap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.receipt, menu);
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
	
	public void editReceipt(View view){
		Intent intent = new Intent(this,EditReceiptActivity.class);
		intent.putExtra(RECEIPT_ID, receiptId);
		startActivity(intent);
	}
	public void deleteReceipt(View view){
		 //Put up the Yes/No message box
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder
    	.setTitle("Delete receipt")
    	.setMessage("Are you sure?")
    	.setIcon(android.R.drawable.ic_dialog_alert)
    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int which) {	
    	    	db.deleteReceipt(Integer.parseInt(receiptId));
    	    }
    	})
    	.setNegativeButton("No", null)						//Do nothing on no
    	.setOnDismissListener(new OnDismissListener() {
	        @Override
	        public void onDismiss(DialogInterface dialog) {
	            finish();
	        }
	    })
    	.show();
	}
}
