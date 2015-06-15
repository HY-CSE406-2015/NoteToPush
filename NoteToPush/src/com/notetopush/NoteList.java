package com.notetopush;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NoteList {

	final static int MEMO_TYPE = 0;
	final static int TODO_TYPE = 1;
	final static int IMG_TYPE = 2;


	private static final String dbName = "notetopush.db";
	private static final int dbVersion = 1;
	public static SQLiteDatabase mDB;
	private DatabaseHelper mDBHelper;
	private Context mCtx;

	private static final String note = "NOTE";
	private static final String memo_note =	 "MEMO_NOTE";
	private static final String todo_note = "TODO_NOTE";
	private static final String img_note = "IMG_NOTE";
	private ArrayList<String> noteTitle;
	private ArrayList<Integer> note_id;
	private ArrayList<Integer> note_noti_id;


	public NoteList(Context context){
		this.mCtx = context;
		this.mDBHelper = new DatabaseHelper(context, dbName, null, dbVersion);
		//mDBHelper = new DatabaseHelper(mCtx, dbName, null, dbVersion);
		mDB = mDBHelper.getWritableDatabase();
		dropAll();
		mDBHelper.onCreate(mDB);
		for (int i = 1; i<4; i++){
			ContentValues ctnValue = new ContentValues();
			ctnValue.put("note_id", i);
			ctnValue.put("type",1);
			ctnValue.put("alarm",System.currentTimeMillis());
			ctnValue.put("write_time",System.currentTimeMillis());
			ctnValue.put("title", "title"+i);
			ctnValue.put("noti_id", i);
			mDB.insert("note",null,ctnValue);
		}
		//mDB.execSQL("INSERT INTO note VALUES (null, '컬럼명', '값');");

		Log.i("NoteList","Start selectNote");
		selectNote();
		Log.i("NoteList","End selectNote");
//		Log.i("NoteList","title"+getNoteTitles(0));
//		Log.i("NoteList","title"+getNoteTitles(1));
	}


	private class DatabaseHelper extends SQLiteOpenHelper{

		// 생성자
		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		// 최초 DB를 만들때 한번만 호출된다.
		@Override
		public void onCreate(SQLiteDatabase db) {
			String createNote = "create table " + note + " ("
					+ "note_id integer primary key not null, " + "type integer not null, "
					+ "title text not null, " + "alarm numeric, " + "write_time numeric not null, "
					+ "noti_id integer not null"
					+ ");";
			db.execSQL(createNote);

			String createMemo = "create table " + memo_note + " ("
					+ "note_id integer primary key not null, " + "content text, "
					+ "foreign key(note_id) references note(note_id)"
					+ ")";
			db.execSQL(createMemo);

			String createToDo = "create table " + todo_note + " ("
					+ "note_id integer primary key not null, " + "todo_order integer not null, "
					+ "content text not null, " + "is_check integer not null, "
					+ "foreign key(note_id) references note(note_id)"
					+ ");";
			db.execSQL(createToDo);


			String createIMG = "create table " + img_note + " ("
					+ "note_id integer primary key not null, " + "img blob not null, "
					+ "content text, " + "foreign key(note_id) references  note(note_id)"
					+ ");";
			db.execSQL(createIMG);

		}

		// 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			String createNote = "create table " + note + " ("
					+ "note_id integer primary key not null, " + "type integer not null, "
					+ "title text not null, " + "alarm numeric, " + "write_time numeric not null, "
					+ "noti_id integer not null"
					+ ");";
			db.execSQL(createNote);

			String createMemo = "create table " + memo_note + " ("
					+ "note_id integer primary key not null, " + "content text, "
					+ "foreign key(note_id) references note(note_id)"
					+ ")";
			db.execSQL(createMemo);

			String createToDo = "create table " + todo_note + " ("
					+ "note_id integer primary key not null, " + "todo_order integer not null, "
					+ "content text not null, " + "is_check integer not null, "
					+ "foreign key(note_id) references note(note_id)"
					+ ");";
			db.execSQL(createToDo);


			String createIMG = "create table " + img_note + " ("
					+ "note_id integer primary key not null, " + "img blob not null, "
					+ "content text, " + "foreign key(note_id) references  note(note_id)"
					+ ");";
			db.execSQL(createIMG);
			onCreate(db);
		}
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

	public NoteList open() throws SQLException{
		mDBHelper = new DatabaseHelper(mCtx, dbName, null, dbVersion);
		mDB = mDBHelper.getWritableDatabase();
		return this;
	}

	public void close(){
		mDB.close();
	}

	public void selectNote() {
		mDB = mDBHelper.getReadableDatabase();
		Cursor c = mDB.query("note", null, null, null, null, null, null);
		noteTitle = new ArrayList<String>();
		note_id = new ArrayList<Integer>();
		note_noti_id = new ArrayList<Integer>();

		while (c.moveToNext()) {
			int _id = c.getInt(c.getColumnIndex("note_id"));
			String title = c.getString(c.getColumnIndex("title"));
			int noti_id = c.getInt(c.getColumnIndex("noti_id"));
			Log.i("db", "note_id: " + _id + ", title : " + title + ", noti id : " + noti_id);
			noteTitle.add(title);
			note_id.add(_id);
			note_noti_id.add(noti_id);
		}
		c.close();
	}

	public String getNoteTitles(int index){
		return noteTitle.get(index);
	}

	public int getNoteId(int index){
		return note_id.get(index);
	}
	public int getNote_Noti_Id(int index){
		return note_noti_id.get(index);
	}

	public int getSize(){
		return noteTitle.size();
	}

	public void deleteNote(int note_id){
		mDB = mDBHelper.getWritableDatabase();
		//id로 지워지는거 맞지?
		String deleteNote = "delete from " + note +" where id = '"+ note_id +"'";
		String deleteMemoNote = "delete from " + memo_note +" where id = '"+ note_id +"'";
		String deleteImgNote = "delete from " + img_note +" where id = '"+ note_id +"'";
		String deleteTodoNote = "delete from " + todo_note +" where id = '"+ note_id +"'";
		
		mDB.execSQL(deleteNote);
		mDB.execSQL(deleteMemoNote);
		mDB.execSQL(deleteImgNote);
		mDB.execSQL(deleteTodoNote);
	}
}