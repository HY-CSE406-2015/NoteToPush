package com.example.notetopush;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class ToDoNote extends Note {

	private int note_id;
	private int todo_order;
	private String note_content;
	private boolean is_checked;
	
	public ToDoNote(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ToDoNote(Context context, int note_id, int todo_loop) {
		super();
	}
	
	public void setContent(String content, int index) {
		
	}
	
	public void addContents(String[] contents) {
		
	}
	
	public void setCheck(boolean check, int index) {
		
	}
	
	public void insertToDoList(ContentValues note) {
		ContentValues todo_content = new ContentValues();
		todo_content.put("note_id", note.getAsInteger("note_id"));
		todo_content.put("todo_order", note.getAsInteger("todo_order"));
		todo_content.put("content", note.getAsString("content"));
		todo_content.put("is_check", note.getAsInteger("is_check"));
		Note.mDB.insert("TODO_NOTE", null, todo_content);
	}
	
	public void updateToDoList(ContentValues note, int note_id) {
		ContentValues img_content = new ContentValues();
		img_content.put("note_id", note.getAsInteger("note_id"));
		img_content.put("img", note.getAsByte("img"));
		img_content.put("content", note.getAsString("content"));
		Note.mDB.update("TODO_NOTE", img_content, "note_id = " + note_id, null);
	}

	public void selectNote(int note_id) {
		Cursor c = mDB.rawQuery("SELECT * FROM TODO_NOTE WHERE NOTE_ID = " + note_id, null);

		this.todo_order = c.getInt(c.getColumnIndex("todo_order"));
		this.note_content = c.getString(c.getColumnIndex("content"));
		this.is_checked = c.getInt(c.getColumnIndex("is_check")) > 0;
	}
}
