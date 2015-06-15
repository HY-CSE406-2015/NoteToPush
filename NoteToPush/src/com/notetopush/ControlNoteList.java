// ControlNoteList
package com.notetopush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ControlNoteList extends Activity implements ViewNoteList.ViewListener{

	private ViewNoteList view;
	private NoteList note_list;
	private boolean is_created;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		Log.i("ControlNoteList","Start make NoteList");
		this.note_list = new NoteList(this);
		Log.i("ControlNoteList","Start make ViewNoteList");
		this.view = new ViewNoteList(this);
		Log.i("ControlNoteList","Start make NoteList.setListener");
		this.view.setListener(this);
		Log.i("ControlNoteList","Start setView");
		setView();
		Log.i("ControlNoteList","Start setContent");
		setContentView(this.view.getView());
		is_created = false;
	}
	protected void onResume(){
		super.onResume();
		if(is_created){
			this.note_list = new NoteList(this);
			setView();
			setContentView(this.view.getView());
		}
		is_created = true;
	}

	private void setView(){
		Log.i("CtlNL",""+this.note_list.getSize());
		for(int list_loop=0; list_loop<this.note_list.getSize();list_loop++){
			Log.i("CtlNL",""+list_loop);
			view.addNoteContainer(note_list.getNoteTitles(list_loop), note_list.getNoteId(list_loop));
		}
	}
	public void addButtonAction() {
		Intent i = new Intent(ControlNoteList.this, ControlNoteEdit.class);
		startActivity(i);
	}
	public void noteClickAction(int note_id){
		Intent i = new Intent(ControlNoteList.this, ControlNoteEdit.class);
		i.putExtra(ControlNoteContent.CONTROL_NOTE_ID, note_id);
		startActivity(i);
	}
	public void deleteButtonAction(int note_id, View note){
		//삭제 확인 팝업 추가
		note_list.deleteNote(note_id);
		view.deleteNote(note);
	}

}