package com.receiptboxsystem;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.receiptboxsystem.R;
import com.example.receiptboxsystem.R.layout;
import com.example.receiptboxsystem.R.menu;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.MediaStore;

public class EditReceiptActivity extends Activity {
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String IMAGE_DIRECTORY_NAME = "Receipt Image";
    private Uri fileUri; // file url to store image/video
    private ImageView imgPreview;
	private int year;
	private int month;
	private int day;
	private EditText et;
	private Calendar c;
	private String receiptId;
	private MySQLiteHelper db;
	private Receipt r;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_receipt);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		receiptId = intent.getStringExtra(ReceiptActivity.RECEIPT_ID);
		db = new MySQLiteHelper(this);		
		r = db.getReceipt(Integer.parseInt(receiptId));
		
		imgPreview = (ImageView) findViewById(R.id.imageView1 );
		EditText descText = (EditText) findViewById(R.id.desc_text);
		EditText locationText = (EditText) findViewById(R.id.location_text);
		EditText amountText = (EditText) findViewById(R.id.amount_text);
		EditText remarkText = (EditText) findViewById(R.id.remark_text);
		et = (EditText) findViewById(R.id.date_text);
		
		
		descText.setText(r.getDesc());
		locationText.setText(r.getLocation());
		amountText.setText(r.getAmount());
		et.setText(r.getDate());
		remarkText.setText(r.getRemark());
		fileUri = fileUri.fromFile(new File(r.getImgUrl()));
		
		BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
		final Bitmap bitmap = BitmapFactory.decodeFile(r.getImgUrl(),options);
        imgPreview.setImageBitmap(bitmap);
				
		final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
			@Override
	    	public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
		        c.set(Calendar.YEAR, year);
		        c.set(Calendar.MONTH, monthOfYear);
		        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		        updateLabel();
		    }
		};
		et.setOnClickListener(new OnClickListener(){
			@Override
		    public void onClick(View v) {
		        new DatePickerDialog(EditReceiptActivity.this, date, c.get(Calendar.YEAR), c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
		        
		    }
		} );
		
		imgPreview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				captureImage();
			}
		});
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
		getMenuInflater().inflate(R.menu.edit_receipt, menu);
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
	
	private void updateLabel() {
		year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);
 
        // Show current date
         
        et.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year));
	}
	private void captureImage() {
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	 
	    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
	 
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	 
	    // start the image capture Intent
	    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}
	
	/**
	 * Creating file uri to store image/video
	 */
	public Uri getOutputMediaFileUri(int type) {
	    return Uri.fromFile(getOutputMediaFile(type));
	}
	
	/*
	 * returning image / video
	 */
	private static File getOutputMediaFile(int type) {
	 
	    // External sdcard location
	    File mediaStorageDir = new File(
	            Environment
	                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
	            IMAGE_DIRECTORY_NAME);
	 
	    // Create the storage directory if it does not exist
	    if (!mediaStorageDir.exists()) {
	        if (!mediaStorageDir.mkdirs()) {
	            Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
	                    + IMAGE_DIRECTORY_NAME + " directory");
	            return null;
	        }
	    }
	 
	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
	            Locale.getDefault()).format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator
	                + "IMG_" + timeStamp + ".jpg");
	    } else {
	        return null;
	    }
	 
	    return mediaFile;
	}
	/**
	 * Receiving activity result method will be called after closing the camera
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // if the result is capturing Image
	    if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // successfully captured the image
	            // display it in image view
	            previewCapturedImage();
	            File file = new File(r.getImgUrl());
	            file.delete();
	        } else if (resultCode == RESULT_CANCELED) {
	            // user cancelled Image capture
	            Toast.makeText(getApplicationContext(),
	                    "User cancelled image capture", Toast.LENGTH_SHORT)
	                    .show();
	        } else {
	            // failed to capture image
	            Toast.makeText(getApplicationContext(),
	                    "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
	                    .show();
	        }
	    }
	}

	/*
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {
            // hide video preview 
            imgPreview.setVisibility(View.VISIBLE);
 
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
 
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 2;
 
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
 
            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    
    public void cancelStore(View view){
		this.finish();
	}

	public void saveStore(View view){
		EditText desc_text = (EditText) findViewById(R.id.desc_text);
		EditText location_text = (EditText) findViewById(R.id.location_text);
		EditText amount_text = (EditText) findViewById(R.id.amount_text);
		EditText date_text = (EditText) findViewById(R.id.date_text);
		EditText remark_text = (EditText) findViewById(R.id.remark_text);
		String desc = desc_text.getText().toString();
		String location = location_text.getText().toString();
		String amount = amount_text.getText().toString();
		String date = date_text.getText().toString();
		String remark = remark_text.getText().toString();
		String pictureUrl = "No picture";
		if ( fileUri != null ){
			pictureUrl = fileUri.getPath();
		}
		if ( desc.equals("")){
			Toast.makeText(getApplicationContext(), "Please input description field", Toast.LENGTH_SHORT).show();
			return;
		} else if ( location.equals("")){
			Toast.makeText(getApplicationContext(), "Please input location field", Toast.LENGTH_SHORT).show();
			return;
		} else if ( amount.equals("")){
			Toast.makeText(getApplicationContext(), "Please input amount field", Toast.LENGTH_SHORT).show();
			return;
		} else if ( date.equals("")){
			Toast.makeText(getApplicationContext(), "Please input date field", Toast.LENGTH_SHORT).show();
			return;
		} else if ( pictureUrl.equals("No picture")){
			Toast.makeText(getApplicationContext(), "There is no receipt captured", Toast.LENGTH_SHORT).show();
			return;
		} else {
			r.setDesc(desc);
			r.setLocation(location);
			r.setAmount(amount);
			r.setDate(date);
			r.setRemark(remark);
			r.setImgUrl(pictureUrl);
			db.updateReceipt(r);
			this.finish();
		}
		return;
	}
}
