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

	// DB占쏙옙占쏙옙 占쏙옙占� 占쏙옙占쏙옙
	private static final String dbName = "notetopush.db";
	private static final String note = "NOTE";
	private static final String memo_note =	 "MEMO_NOTE";
	private static final String todo_note = "TODO_NOTE";
	private static final String img_note = "IMG_NOTE";
	public static final int dbVersion = 1;

	public static SQLiteDatabase mDB;
	private MyOpenHelper helper;

	private int note_id = 1;
	private String note_title = "test";
	private int note_type = 3;
	private Long note_alarm = Long.valueOf(4);
	private long note_write_time = 5;
	private Integer note_notification_id = 6;

	private Context context;

	public Note(Context context) {
		this.context = context;
		this.helper = new MyOpenHelper(context, dbName, null, dbVersion);
		mDB = helper.getWritableDatabase();
//		dropAll();
//		helper.onCreate(mDB);
//		for (int i = 1; i<4; i++){
//			ContentValues ctnValue = new ContentValues();
//			ctnValue.put("note_id", i);
//			ctnValue.put("type",1);
//			ctnValue.put("alarm",System.currentTimeMillis());
//			ctnValue.put("write_time",System.currentTimeMillis());
//			ctnValue.put("title", "title"+i);
//			ctnValue.put("noti_id", i);
//			mDB.insert("note",null,ctnValue);
//			Log.i("db_test_insert", "note_id: " + i + ", title : " + "title"+i + ", tpye : " + 1
//					+ ", alarm : " + System.currentTimeMillis() +",Write_time : "+System.currentTimeMillis()+""
//					+ ", noti_id : "+i);
//
//		}
//		selectNote(1);

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
					+ "note_id integer primary key not null, " + "content text, "
					+ "foreign key(note_id) references note(note_id)"
					+ ")";
			Log.i("DB_Create",createMemo);
			db.execSQL(createMemo);

			String createToDo = "create table " + todo_note + " ("
					+ "note_id integer primary key not null, " + "todo_order integer not null, "
					+ "content text not null, " + "is_check integer not null, "
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

	public ContentValues setNote(String title, int type,
			Long alarm, long write_time, int notification_id,String content1) {
		ContentValues baseNote = new ContentValues();
		
		note_title = title;
		note_type = type;
		note_alarm = alarm;
		note_write_time = write_time;
		note_notification_id = notification_id;
		
		baseNote.put("type", type);
		baseNote.put("title", title);
		baseNote.put("alarm", alarm);
		baseNote.put("write_time", write_time);
		baseNote.put("noti_id", notification_id);

		if(type == MEMO_TYPE) {
			baseNote.put("content",(String)content1);
		}
		else {
			Log.i("insertError","MEMO ERROR");
		}
		return baseNote;
	}

	public ContentValues setNote(String title, int type,
			Long alarm, long write_time, int notification_id,String content,int order,int checked) {
		ContentValues baseNote = new ContentValues();
		
		note_title = title;
		note_type = type;
		note_alarm = alarm;
		note_write_time = write_time;
		note_notification_id = notification_id;
		
		baseNote.put("type", type);
		baseNote.put("title", title);
		baseNote.put("alarm", alarm);
		baseNote.put("write_time", write_time);
		baseNote.put("noti_id", notification_id);

		if(type == TODO_TYPE) {
			baseNote.put("content",content);
			baseNote.put("todo_order",order);
			baseNote.put("is_check",checked);
//			todo_content.put("todo_order", note.getAsInteger("todo_order"));
//			todo_content.put("content", note.getAsString("content"));
//			todo_content.put("is_check", note.getAsInteger("is_check"));
		}	else {
			Log.i("insertError","MEMO ERROR");
		}
		return baseNote;
	}

	public ContentValues setNote(String title, int type,
			Long alarm, long write_time, int notification_id,String content,Bitmap img) {
		ContentValues baseNote = new ContentValues();
		
		note_title = title;
		note_type = type;
		note_alarm = alarm;
		note_write_time = write_time;
		note_notification_id = notification_id;
		
		baseNote.put("type", type);
		baseNote.put("title", title);
		baseNote.put("alarm", alarm);
		baseNote.put("write_time", write_time);
		baseNote.put("noti_id", notification_id);

		if(type == IMG_TYPE) {
			baseNote.put("content",content);
			baseNote.put("img", getBytes(img));
//			img_content.put("img", note.getAsByte("img"));
//			img_content.put("content", note.getAsString("content"));

		}	else {
			Log.i("insertError","MEMO ERROR");
		}
		return baseNote;
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
	
    private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
 
	public long getWriteTime() {
		return note_write_time;
	}

	public Integer getNotification() {
		return note_notification_id;
	}

	public void insertNote(ContentValues note) { //push confirmEditButton, This operator calls insertNote of note when note is not null
		//mDB = helper.getWritableDatabase();
		Cursor c = mDB.query("note", null, null, null, null, null, null);
		note_id = c.getCount() + 1;
		
		note.put("note_id", this.getId()); //note_id �젙蹂대�� MemoNote, ToDoNote, ImageNote�뿉 �꽆寃⑥＜湲� �쐞�빐 put �빐以��떎.
		
		Log.i("insertNote","cursor count = "+c.getCount());
		Log.i("insertNote","id = "+note_id);
		
		ContentValues tmp_note = new ContentValues();
		tmp_note.put("note_id", this.note_id);
		tmp_note.put("type", note.getAsInteger("type"));
		tmp_note.put("title", note.getAsString("title"));
		tmp_note.put("alarm", note.getAsLong("alarm"));
		tmp_note.put("write_time", note.getAsLong("write_time"));
		tmp_note.put("noti_id", note.getAsInteger("noti_id"));
		Log.i("insertNote", "this.note = "+this.note);
		long debug_insert = mDB.insert("note", null, tmp_note);
		Log.i("insertNote","debug_insert = "+debug_insert);
		//mDB.insert("note",null,tmp_note);

		Log.i("insertNote", "note_id: " + this.note_id + ", title : " + tmp_note.getAsString("title") 
				+ ", type : " +tmp_note.getAsInteger("type") + ", alarm : " +tmp_note.getAsLong("alarm") 
				+",Write_time : "+tmp_note.getAsLong("write_time")+", noti_id : "+note.getAsInteger("noti_id"));
	
		if(note.getAsInteger("type") == MEMO_TYPE) {
			MemoNote memo_note = new MemoNote(this.context);
			memo_note.insertMemoNote(note);
		}

		else if(note.getAsInteger("type") == TODO_TYPE) {
			ToDoNote todo_note = new ToDoNote(this.context);
			todo_note.insertToDoList(note);
		}

		else if(note.getAsInteger("type") == IMG_TYPE) {
			ImageNote image_note = new ImageNote(this.context);
			image_note.insertImageNote(note);
		}
		//note_id++;

	}

	public void updateNote(ContentValues note, int note_id) {	//when note is null calls updateNote of note. ??
		ContentValues update_note = new ContentValues();
		update_note.put("note_id", this.note_id);
		update_note.put("type", note.getAsInteger("type"));
		update_note.put("title", note.getAsString("title"));
		update_note.put("alarm", note.getAsLong("alarm"));
		update_note.put("write_time", note.getAsLong("write_time"));
		update_note.put("noti_id", note.getAsInteger("noti_id"));
		mDB.update(this.note, update_note, "note_id = " + note_id, null);

		if(note.getAsInteger("type") == MEMO_TYPE) {
			MemoNote memo_note = new MemoNote(this.context);
			memo_note.updateMemoNote(note);
		}

		else if(note.getAsInteger("type") == TODO_TYPE) {
			ToDoNote todo_note = new ToDoNote(this.context);
			todo_note.updateToDoList(note);
		}

		else if(note.getAsInteger("type") == IMG_TYPE) {
			ImageNote image_note = new ImageNote(this.context);
			image_note.updateImageNote(note);
		}
	}

	public void selectNote(int select_note_id) {
		//mDB = helper.getReadableDatabase(); // db占쏙옙체占쏙옙 占쏙옙占승댐옙. 占싻깍옙 占쏙옙占쏙옙
		Cursor c = mDB.query(this.note, null, "note_id = " +select_note_id, null, null, null, null);

		Log.i("selectNote","note_id = "+select_note_id);
		Log.i("selectNote","getCount = "+c.getCount());
		/*
		 * 占쏙옙 占쏙옙占쏙옙占� select * from student 占쏙옙 占싫댐옙. Cursor占쏙옙 DB占쏙옙占쏙옙占� 占쏙옙占쏙옙占싼댐옙. public Cursor
		 * query (String table, String[] columns, String selection, String[]
		 * selectionArgs, String groupBy, String having, String orderBy)
		 */
		c.moveToNext();
		if (c != null && c.getCount() != 0){
			note_id = c.getInt(c.getColumnIndex("note_id"));
			note_title = c.getString(c.getColumnIndex("title"));
			note_type = c.getInt(c.getColumnIndex("type"));
			note_alarm = c.getLong(c.getColumnIndex("alarm"));
			note_write_time = c.getLong(c.getColumnIndex("write_time"));
			note_notification_id = c.getInt(c.getColumnIndex("noti_id"));
			Log.i("db", "note_id: " + note_id + ", title : " + note_title + ", tpye : " + note_type
					+ ", alarm : " + note_alarm +",Write_time : "+note_write_time+", noti_id : "+note_notification_id);
		}
		else{
			Log.i("db","cursor is null");
		}
	}

	public void deleteNote() {


	}

	public void dropAll() {
		String dropNote = "drop table if exists " + note;
		String dropMemo = "drop table if exists " + memo_note;
		String dropTodo = "drop table if exists " + todo_note;
		String dropIMG = "drop table if exists " + img_note;

		mDB.execSQL(dropNote);
		mDB.execSQL(dropMemo);
		mDB.execSQL(dropTodo);
		mDB.execSQL(dropIMG);
	}
}
