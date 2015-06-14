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

public class ControlNoteContent extends Activity implements ViewNoteContent.ViewListener {
	public static final String CONTROL_NOTE_ID = "com.notetopush.ControlNoteContent.CONTROL_NOTE_ID"; 
	private ViewNoteContent view;
	private Note note;
	private int note_id;
	private boolean is_created;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.note_id = getIntent().getIntExtra(CONTROL_NOTE_ID, -1);
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
		
		int type = this.note.getType();
		switch(type){
		case Note.MEMO_TYPE:
			String text = ((MemoNote)this.note.getSubNote()).getContent();
			this.view.setMemo(text, date);
			break;
		case Note.TODO_TYPE:
			ArrayList<String> str_list = new ArrayList<String>();
			ArrayList<Boolean> able_list = new ArrayList<Boolean>();
			for(int todo_loop=0; todo_loop < this.note.todoSize(); todo_loop++){
				str_list.add(this.note.getToDo(todo_loop).getContent());
				able_list.add(this.note.getToDo(todo_loop).isChecked());
			}
			this.view.setToDo(str_list, able_list, date);
			break;
		case Note.IMG_TYPE:
			ImageNote sub = ((ImageNote)this.note.getSubNote());
			sub.setImage(BitmapFactory.decodeResource(this.getResources(),R.drawable.test_big_img));
			this.view.setImage(sub.getImage(), sub.getContent(), date);
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
		this.note = new Note(note_id);
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
		this.note.getToDo(order).setChecked(checked);
	}

}
