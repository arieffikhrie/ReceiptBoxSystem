package com.receiptboxsystem;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "ReceiptDB";
	private static final String TABLE_RECEIPTS = "receipts";
	private static final String KEY_ID = "id";
	private static final String KEY_DESC = "desc";
	private static final String KEY_LOCATION = "location";
	private static final String KEY_AMOUNT = "amount";
	private static final String KEY_DATE = "date";
	private static final String KEY_REMARK = "remark";
	private static final String KEY_IMGURL = "imgUrl";
	
	private static final String[] COLUMNS = {KEY_ID,KEY_DESC,KEY_LOCATION,KEY_AMOUNT,KEY_DATE,KEY_REMARK,KEY_IMGURL};
	
	public MySQLiteHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION); 	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_RECEIPT_TABLE = "CREATE TABLE receipts ( " +
								"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
								"desc TEXT, " +
								"location TEXT, " +
								"amount TEXT, " +
								"date TEXT, " +
								"remark TEXT, " +
								"imgUrl TEXT )";
		db.execSQL(CREATE_RECEIPT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS receipts");
		this.onCreate(db);
	}
	
	public void addReceipt(Receipt r){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues val = new ContentValues();
		val.put(KEY_DESC,r.getDesc());
		val.put(KEY_LOCATION, r.getLocation());
		val.put(KEY_AMOUNT, r.getAmount());
		val.put(KEY_DATE, r.getDate());
		val.put(KEY_REMARK, r.getRemark());
		val.put(KEY_IMGURL, r.getImgUrl());
		db.insert(TABLE_RECEIPTS,null,val);
		db.close();
	}
	
	public Receipt getReceipt(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_RECEIPTS, COLUMNS, " id = ?", new String[] { String.valueOf(id)}, null, null, null);
		if ( cursor != null)
			cursor.moveToFirst();
		Receipt r = new Receipt();
		r.setId(Integer.parseInt(cursor.getString(0)));
		r.setDesc(cursor.getString(1));
		r.setLocation(cursor.getString(2));
		r.setAmount(cursor.getString(3));
		r.setDate(cursor.getString(4));
		r.setRemark(cursor.getString(5));
		r.setImgUrl(cursor.getString(6));
		db.close();
		return r;
	}
	
	public List<Receipt> getReceipts(){
		List<Receipt> receipts = new LinkedList<Receipt>();
		String query = "SELECT * FROM " + TABLE_RECEIPTS;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(query, null);
		
		Receipt r = null;
		if ( c.moveToFirst()) {
			do {
				r = new Receipt();
				r.setId(Integer.parseInt(c.getString(0)));
				r.setDesc(c.getString(1));
				r.setLocation(c.getString(2));
				r.setAmount(c.getString(3));
				r.setDate(c.getString(4));
				r.setRemark(c.getString(5));
				r.setImgUrl(c.getString(6));
				
				receipts.add(r);
			} while ( c.moveToNext() );
		}
		db.close();
		return receipts;
	}
	
	public int updateReceipt(Receipt r){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues val = new ContentValues();
		val.put(KEY_DESC,r.getDesc());
		val.put(KEY_LOCATION, r.getLocation());
		val.put(KEY_AMOUNT, r.getAmount());
		val.put(KEY_DATE, r.getDate());
		val.put(KEY_REMARK, r.getRemark());
		val.put(KEY_IMGURL, r.getImgUrl());
		int i = db.update(TABLE_RECEIPTS, val, KEY_ID + " = ?", new String[] {String.valueOf(r.getId())});
		db.close();
		return i;
	}
	
	public void deleteReceipt(Receipt r){
		SQLiteDatabase db= this.getWritableDatabase();
		db.delete(TABLE_RECEIPTS, KEY_ID + " = ?", new String[] {String.valueOf(r.getId())});
		db.close();
	}
	
	public void deleteReceipt(int id){
		SQLiteDatabase db= this.getWritableDatabase();
		db.delete(TABLE_RECEIPTS, KEY_ID + " = ?", new String[] {String.valueOf(id)});
		db.close();
	}
}
