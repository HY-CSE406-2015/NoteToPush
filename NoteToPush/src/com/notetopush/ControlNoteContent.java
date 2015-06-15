package com.notetopush;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

public class ControlNoteContent extends Activity implements ViewNoteContent.ViewListener {
	public static final String CONTROL_NOTE_ID = "com.notetopush.ControlNoteContent.CONTROL_NOTE_ID";
	public static final String CONTROL_NOTE_TYPE = "com.notetopush.ControlNoteContent.CONTROL_NOTE_TYPE";
	private ViewNoteContent view;
	private Note note;
	private int note_id;
	private int note_type;
	private boolean is_created;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.note_id = getIntent().getIntExtra(CONTROL_NOTE_ID, -1);
		this.note_type = getIntent().getIntExtra(CONTROL_NOTE_TYPE, -1);
		getNoteContent(this.note_id);
		this.view = new ViewNoteContent(this, this.note.getType());
		this.view.setListener(this);
		setView();
		setContentView(this.view.getView());
		is_created = false;
	}
	protected void onResume(){
		super.onResume();
		if(is_created){
			getNoteContent(this.note_id);
			setView();
			setContentView(this.view.getView());
		}
		is_created = true;
	}
	private void setView(){
		this.view.setTitle(this.note.getTitle());
		String[] date;
		Long alarm = this.note.getAlarmTime();
		if(alarm == null) date = null;
		else{
			date = new String[6];
			Calendar c = Calendar.getInstance();
		    c.setTimeInMillis(alarm);
		    Date d = c.getTime();
		    
		    Locale locale = getResources().getConfiguration().locale;
		    
		    date[0] = (new SimpleDateFormat("yyyy",locale)).format(d);
		    date[1] = (new SimpleDateFormat("MMM",locale)).format(d);
		    date[2] = (new SimpleDateFormat("dd",locale)).format(d);
		    date[3] = (new SimpleDateFormat("hh",locale)).format(d);
		    date[4] = (new SimpleDateFormat("mm",locale)).format(d);
		    date[5] = (new SimpleDateFormat("aa",locale)).format(d);
		}
		
		int type = this.note_type;
		Log.d("NoteType", ""+type);
		switch(type){
		case Note.MEMO_TYPE:
			String text = ((MemoNote)this.note).getContent();
			this.view.setMemo(text, date);
			break;
		case Note.TODO_TYPE:
			ArrayList<String> str_list = ((ToDoNote)this.note).getContents();
			ArrayList<Boolean> able_list = ((ToDoNote)this.note).getChecks();
			Log.d("ToDo draw", "Number of elem is "+str_list.size());
			this.view.setToDo(str_list, able_list, date);
			break;
		case Note.IMG_TYPE:
			ImageNote sub = ((ImageNote)this.note);
			this.view.setImage(sub.getImage(), sub.getSubContent(), date);
			break;
		default:
			try {
				throw new Exception("com.notetopush.NoteTypeException: Exception occured missing note type match");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void getNoteContent(int note_id){
		switch(this.note_type){
		case Note.MEMO_TYPE:
			this.note = new MemoNote(this,note_id);
			break;
		case Note.IMG_TYPE:
			this.note = new ImageNote(this,note_id);
			break;
		case Note.TODO_TYPE:
			this.note = new ToDoNote(this, note_id);
			break;
		default:
			try{
				throw new Exception("com.notetopush.NoteTypeException: Exception occured missing note type match");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void deleteNoteAction() {
		this.note.deleteNote();
		finish();
	}

	public void modifyNoteAction() {
		Intent i = new Intent(ControlNoteContent.this,ControlNoteEdit.class);
		i.putExtra(ControlNoteEdit.NOTE_ID, this.note_id);
		startActivity(i);
	}

	public void todoCheckAction(int order, boolean checked) {
		((ToDoNote)this.note).setChecked(order,checked);
	}

}
