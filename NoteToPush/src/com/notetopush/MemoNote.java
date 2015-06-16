package com.notetopush;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class MemoNote extends Note {
	private String note_content;

	public MemoNote(Context context) {
		super(context);
	}
	public MemoNote(Context context, int note_id){
		super(context,note_id);
		selectNote(note_id);
	}
	public void setNote(String title,Long alarm, long write_time, String content) {
		super.setNote(title, Note.MEMO_TYPE, alarm, write_time);
		this.note_content = content;
	}
	
	public String getContent() {
		return note_content;		
	}
	
	public void selectNote(int note_id){
		super.selectNote(note_id);
		Cursor c = mDB.query("MEMO_NOTE", null, "note_id = " +note_id, null, null, null, null);
		
		c.moveToNext();
		if (c != null && c.getCount() != 0){
			this.note_content = c.getString(c.getColumnIndex("content"));
		}
		c.close();
		mDB.close();
	}
	public void insertNote() {
		super.insertNote();
		ContentValues memo_content = new ContentValues();
		memo_content.put("note_id", this.note_id);
		memo_content.put("content", this.note_content);
		mDB.insert("MEMO_NOTE", null, memo_content);
		mDB.close();
	}
	
	public void updateNote(){
		super.updateNote();
		ContentValues memo_content = new ContentValues();
		memo_content.put("note_id", this.note_id);
		memo_content.put("content", this.note_content);
		mDB.update("MEMO_NOTE", memo_content, "note_id=?", new String[]{""+note_id});
		mDB.close();
	}
	public void deleteNote(){
		super.deleteNote();
		mDB.delete("MEMO_NOTE", "note_id=?", new String[]{""+note_id});
		mDB.close();
	}

}