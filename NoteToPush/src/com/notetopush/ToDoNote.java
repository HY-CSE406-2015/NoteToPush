package com.notetopush;


import android.content.ContentValues;
import android.content.Context;


public class ToDoNote extends Note {
	
	public ToDoNote(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private String note_content;
	private int todo_order;
	private int is_check;
	
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
	
	public void updateToDoList(ContentValues note) {
		ContentValues img_content = new ContentValues();
		img_content.put("note_id", note.getAsInteger("note_id"));
		img_content.put("img", note.getAsByte("img"));
		img_content.put("content", note.getAsString("content"));
		Note.mDB.update("TODO_NOTE", img_content, "note_id = " + note.getAsInteger("note_id"), null);
	}



}
