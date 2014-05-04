package com.linkedlunchbuddy;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandler {

	public static final String FBID = "fbid";
	public static final String EMAIL = "email"; // Primary Key for backend, used to check if email should be requested
	public static final String FIRSTNAME = "firstname";
	public static final String LASTNAME = "lastname";
	public static final String GENDER = "gender";
	public static final String LUNCHDATESTATUS = "lunchdatestatus";
	public static final String TABLE_NAME = "mytable";
	public static final String DATABASE_NAME = "mydatabase";
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_CREATE = "create table mytable (fbid text not null, email text not null, firstname text not null, lastname text not null, gender text not null, lunchdatestatus text not null);";
	

	DatabaseHelper dbHelper;
	Context ctx;
	SQLiteDatabase db;

	public DataHandler(Context ctx) {
		this.ctx = ctx;
		dbHelper = new DatabaseHelper(ctx);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context ctx) {
			super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
			db.execSQL(DATABASE_CREATE);
			} catch (RuntimeException ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS mytable");
			onCreate(db);

		}  
	}

	public DataHandler open() {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long insertUser(String fbid, String email, String firstname, String lastname, String gender, String lunchdatestatus) {
		// Do not insert if user already exists
		if (!containsUserWithEmail(email)) {
			ContentValues content = new ContentValues();
			content.put(FBID, fbid);
			content.put(EMAIL, email);
			content.put(FIRSTNAME, firstname);
			content.put(LASTNAME, lastname);
			content.put(GENDER, gender);
			content.put(LUNCHDATESTATUS, lunchdatestatus);
			return db.insert(TABLE_NAME, null, content);
		}
		return 0;
	}
	
	public long changeName(String firstname, String lastname) {
		ContentValues content = new ContentValues();
		Cursor cursor = this.allUsers();
		cursor.moveToFirst();
		content.put(FBID, cursor.getString(0));
		content.put(EMAIL, cursor.getString(1));
		content.put(FIRSTNAME, firstname);
		content.put(LASTNAME, lastname);
		content.put(GENDER, cursor.getString(4));
		content.put(LUNCHDATESTATUS, cursor.getString(5));
		cursor.close();
		db.delete(TABLE_NAME, null, null);
		return db.insert(TABLE_NAME, null, content);
	}

	public boolean containsUserWithEmail(String email) {
		Cursor cursor = this.allUsers();
		if (cursor.moveToFirst()) {
			do {
				if (cursor.getString(1).equals(email)) {
					return true;
				}
			} while (cursor.moveToNext());
		}
		return false;
	}
	
	// Called in RequestSubmitFragment to update LUNCHDATESTATUS
	public long updateLunchDateStatus(String lunchDateStatus) {
		ContentValues content = new ContentValues();
		Cursor cursor = this.allUsers();
		cursor.moveToFirst();
		content.put(FBID, cursor.getString(0));
		content.put(EMAIL, cursor.getString(1));
		content.put(FIRSTNAME, cursor.getString(2));
		content.put(LASTNAME, cursor.getString(3));
		content.put(GENDER, cursor.getString(4));
		content.put(LUNCHDATESTATUS, lunchDateStatus);
		cursor.close();
		db.delete(TABLE_NAME, null, null);
		return db.insert(TABLE_NAME, null, content);
	}
	

	public Cursor allUsers() {
		return db.query(TABLE_NAME, new String[] {FBID, EMAIL,FIRSTNAME,LASTNAME,GENDER,LUNCHDATESTATUS}, null, null, null, null, null);
	}

}
