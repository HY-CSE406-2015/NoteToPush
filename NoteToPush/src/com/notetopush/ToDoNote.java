package com.notetopush;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class ToDoNote extends Note {

	private ArrayList<String> note_contents;
	private ArrayList<Boolean> is_checks;
	
	public ToDoNote(Context context) {
		super(context);
	}
	public ToDoNote(Context context, int note_id){
		super(context,note_id);
		selectNote(note_id);
	}
	public void setNote(String title,Long alarm, long write_time, ArrayList<String> contents, ArrayList<Boolean> checks) {
		super.setNote(title, Note.MEMO_TYPE, alarm, write_time);
		this.note_contents = contents;
		this.is_checks = checks;
	}
	
	public ArrayList<String> getContents() {
		return this.note_contents;		
	}
	public ArrayList<Boolean> getChecks(){
		return this.is_checks;
	}
	
	public void selectNote(int note_id){
		super.selectNote(note_id);
		Cursor c = mDB.query("TODO_NOTE", null, "note_id = " +note_id, null, null, null, null);
		
		this.note_contents = new ArrayList<String>();
		this.is_checks = new ArrayList<Boolean>();
		
		while(c.moveToNext()){
			this.note_contents.add(c.getString(c.getColumnIndex("content")));
			this.is_checks.add((c.getInt(c.getColumnIndex("is_check"))==0)?false:true);
		}
		c.close();
		mDB.close();
	}
	public void insertNote() {
		super.insertNote();
		for(int insert_loop = 0; insert_loop<this.note_contents.size(); insert_loop++){
			ContentValues todo_content = new ContentValues();
			todo_content.put("note_id", this.note_id);
			todo_content.put("todo_order", insert_loop);
			todo_content.put("content", this.note_contents.get(insert_loop));
			todo_content.put("is_check", (this.is_checks.get(insert_loop))?1:0);
			mDB.insert("TODO_NOTE", null, todo_content);
		}
		mDB.close();
	}
	
	public void updateNote(){
		super.updateNote();
		for(int update_loop = 0; update_loop<this.note_contents.size(); update_loop++){
			ContentValues todo_content = new ContentValues();
			todo_content.put("note_id", this.note_id);
			todo_content.put("todo_order", update_loop);
			todo_content.put("content", this.note_contents.get(update_loop));
			todo_content.put("is_check", (this.is_checks.get(update_loop))?1:0);
			mDB.update("TODO_NOTE", todo_content, "note_id=? AND todo_order=?", new String[]{""+note_id, ""+update_loop});
		}
		mDB.close();
	}
	public void deleteNote(){
		super.deleteNote();
		mDB.delete("MEMO_NOTE", "note_id=?", new String[]{""+note_id});
		mDB.close();
	}
	public void setChecked(int order, boolean checked) {
		this.is_checks.set(order, checked);
		ContentValues todo_content = new ContentValues();
		todo_content.put("note_id", this.note_id);
		todo_content.put("todo_order", order);
		todo_content.put("is_check", (checked)?1:0);
		mDB.update("TODO_NOTE", todo_content, "note_id=? AND todo_order=?", new String[]{""+note_id, ""+order});
		mDB.close();
	}
}