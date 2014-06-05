package com.demo.android.dummynote;
import java.sql.RowId;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DB {
	private static final String DATABASE_NAME = "notes.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_TABLE  = "notes";

	private static final String DATABASE_CREATE = 
	"create table notes ("
			+ "_id INTEGER PRIMARY KEY,"
			+ "note TEXT,"
			+ "created INTEGER"
	+");";
	
	public static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE　IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}		
		
	}
		
	private Context  mCtx =null;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;

	/* constructor */
	public DB(Context ctx){
	   this.mCtx = ctx;
	} 
	
	public DB open() throws SQLException{
		dbHelper = new DatabaseHelper(mCtx);
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NOTE = "note";
	public static final String KEY_CREATED = "created";
	
	String[] strCols = new String[]{
		KEY_ROWID,
		KEY_NOTE,
		KEY_CREATED
	};
			// get all entries
	public Cursor getAll(){
		//return  db.rawQuery("SELECT * FROM notes", null);
		return  db.query(DATABASE_TABLE,  // table
				// strCols, //which columns to reture 查詢顯示欄位方法  原方法
				new String[]{KEY_ROWID,KEY_NOTE,KEY_CREATED}, //省記憶體的方法
				null, // where clause
				null, // where arguments
				null, // group by clause
				null, // having clause		 
				null  // order-by clasue 
				);
	}
	
	// add an entry
	public long create(String  Note){
		Date now  = new Date();
		ContentValues args = new ContentValues();
		args.put(KEY_NOTE, Note);
		args.put(KEY_CREATED, now.getTime());
		return  db.insert(DATABASE_TABLE, null, args);
		
	}
	
	public boolean delete(long  rowId){
		
		
		//return  db.delete(DATABASE_TABLE, KEY_ROWID + "=" + ,null) > 0;
		return db.delete(DATABASE_TABLE, KEY_ROWID+"="+ rowId, null) > 0;
	}
}


