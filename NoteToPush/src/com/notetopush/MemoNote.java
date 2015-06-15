package com.notetopush;

import android.content.ContentValues;
import android.content.Context;


public class MemoNote extends Note {
	private String note_content;

	public MemoNote(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setContent(String content) {
		note_content = content;
	}
	
	public String getContent() {
		return note_content;		
	}
	
	public void insertMemoNote(ContentValues note) {
		ContentValues memo_content = new ContentValues();
		//	+ "note_id integer primary key not null, " + "content text, "
		memo_content.put("note_id", note.getAsInteger("note_id"));
		memo_content.put("content", note.getAsString("content"));
		Note.mDB.insert("MEMO_NOTE", null, memo_content);
	}
	
	public void updateMemoNote(ContentValues note) {
		
	}

}
