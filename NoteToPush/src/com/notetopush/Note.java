package com.notetopush;


import java.io.ByteArrayOutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import android.widget.Toast;


public class Note {

	final static int MEMO_TYPE = 0;
	final static int TODO_TYPE = 1;
	final static int IMG_TYPE = 2;

	private static final String dbName = "notetopush.db";
	private static final String note = "NOTE";
	private static final String memo_note =	 "MEMO_NOTE";
	private static final String todo_note = "TODO_NOTE";
	private static final String img_note = "IMG_NOTE";
	public static final int dbVersion = 1;

	public static SQLiteDatabase mDB;
	private MyOpenHelper helper;

	protected int note_id = 1;
	private String note_title = "test";
	private int note_type = 3;
	private Long note_alarm = Long.valueOf(4);
	private long note_write_time = 5;
	private Integer note_notification_id = 6;

	private Context context;

	public Note(Context context) {
		this.context = context;
		this.helper = new MyOpenHelper(context, dbName, null, dbVersion);
		calcNewId();
	}

	public Note(Context context, int note_id) {
		this.context = context;
		this.helper = new MyOpenHelper(context, dbName, null, dbVersion);
		
		selectNote(note_id);
	}

	protected void setNote(String title, int type, Long alarm, long write_time) {
		this.note_title = title;
		this.note_type = type;
		this.note_alarm = alarm;
		this.note_write_time = write_time;
	}
	protected void getWritableDB(){
		this.mDB =  helper.getWritableDatabase();
	}

	public int getId() {
		return note_id;
	}

	public String getTitle() {
		return note_title;
	}

	public int getType() {
		return note_type;
	}

	public Long getAlarmTime() {
		return note_alarm;
	}
 
	public long getWriteTime() {
		return note_write_time;
	}

	public Integer getNotification() {
		return note_notification_id;
	}
	
	public void selectNote(int select_note_id){
		mDB = helper.getReadableDatabase();
		Cursor c = mDB.query("NOTE", null, "note_id = " +select_note_id, null, null, null, null);

		c.moveToNext();
		if (c != null && c.getCount() != 0){
			this.note_id = c.getInt(c.getColumnIndex("note_id"));
			this.note_title = c.getString(c.getColumnIndex("title"));
			this.note_type = c.getInt(c.getColumnIndex("type"));
			if(!c.isNull(c.getColumnIndex("alarm")))
				this.note_alarm = c.getLong(c.getColumnIndex("alarm"));
			this.note_write_time = c.getLong(c.getColumnIndex("write_time"));
			this.note_notification_id = c.getInt(c.getColumnIndex("noti_id"));
			Log.i("db", "note_id: " + note_id + ", title : " + note_title + ", tpye : " + note_type
					+ ", alarm : " + note_alarm +",Write_time : "+note_write_time+", noti_id : "+note_notification_id);
		}
		else{
			Log.i("db","cursor is null");
		}
		c.close();
	}

	public void insertNote() { //push confirmEditButton, This operator calls insertNote of note when note is not null
		mDB = helper.getWritableDatabase();
		
		ContentValues note = new ContentValues();
		note.put("note_id", this.note_id);
		note.put("type", this.note_type);
		note.put("title", this.note_title);
		note.put("alarm", this.note_alarm);
		note.put("write_time", this.note_write_time);
		note.put("noti_id", this.note_id);
		long debug_insert = mDB.insert("note", null, note);
		Log.i("insertNote","debug_insert = "+debug_insert);
	}

	public void updateNote() {	//when note is null calls updateNote of note. ??
		mDB = helper.getWritableDatabase();
		
		ContentValues note = new ContentValues();
		note.put("note_id", this.note_id);
		note.put("type", this.note_type);
		note.put("title", this.note_title);
		note.put("alarm", this.note_alarm);
		note.put("write_time", this.note_write_time);
		note.put("noti_id", this.note_id);
		mDB.update("NOTE", note, "note_id = " + this.note_id, null);
	}

	public void deleteNote() {
		mDB = helper.getWritableDatabase();
		mDB.delete("NOTE", "note_id=?", new String[]{""+note_id});
	}
	private void calcNewId(){
		mDB = helper.getReadableDatabase();
		Cursor c = mDB.query("NOTE", null, null, null, null, null, note_id+" DESC");
		
		c.moveToFirst();
		if (c != null && c.getCount() != 0){
			this.note_id = c.getInt(c.getColumnIndex("note_id"))+1;
		}else this.note_id = 0;
		
		c.close();
		mDB.close();
	}
	
	private class MyOpenHelper extends SQLiteOpenHelper {
		public MyOpenHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String createNote = "create table " + note + " ("
					+ "note_id integer primary key not null, " + "type integer not null, "
					+ "title text not null, " + "alarm numeric, " + "write_time numeric not null, "
					+ "noti_id integer not null"
					+ ");";
			Log.i("DB_Create",createNote);
			db.execSQL(createNote);

			String createMemo = "create table " + memo_note + " ("
					+ "note_id integer primary key not null, " + "content text not null, "
					+ "foreign key(note_id) references note(note_id)"
					+ ")";
			Log.i("DB_Create",createMemo);
			db.execSQL(createMemo);

			String createToDo = "create table " + todo_note + " ("
					+ "note_id integer not null, " + "todo_order integer not null, "
					+ "content text not null, " + "is_check integer not null, "
					+ "primary key(note_id, todo_order), "
					+ "foreign key(note_id) references note(note_id)"
					+ ");";
			db.execSQL(createToDo);
			Log.i("DB_Create",createToDo);

			String createIMG = "create table " + img_note + " ("
					+ "note_id integer primary key not null, " + "img blob not null, "
					+ "content text, " + "foreign key(note_id) references  note(note_id)"
					+ ");";
			db.execSQL(createIMG);
			Log.i("DB_Create",createIMG);
			Toast.makeText(context, "DB is opened", Toast.LENGTH_LONG).show();
			//			arg0.close();
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
		}
	}
}