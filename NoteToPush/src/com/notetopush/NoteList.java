package com.notetopush;

import java.util.ArrayList;

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
        selectNote();
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
			int _id = c.getInt(c.getColumnIndex("id"));
			String title = c.getString(c.getColumnIndex("title"));
			int noti_id = c.getInt(c.getColumnIndex("noti_id"));
			Log.i("db", "id: " + _id + ", name : " + title + ", noti id : " + noti_id);
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
	public ArrayList<Integer> getNote_Noti_Id(){
		return note_noti_id;
	}
	
	public int getSize(){
		return noteTitle.size();
	}
	
	public void deleteNote(int note_id){
		
	}
}
