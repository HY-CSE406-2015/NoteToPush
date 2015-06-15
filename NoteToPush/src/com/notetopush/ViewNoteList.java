package com.notetopush;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewNoteList{

  private Context context;
  private View view;
  private ViewListener listen_controller;
  private LinearLayout container;

  public ViewNoteList(Context context){
    this.context = context;
    this.view = View.inflate(context, R.layout.activity_control_note_list,null);
    setViewElement();
  }
  private void setViewElement(){
    container = (LinearLayout)findViewById(R.id.container);
    Button noteAddbtn = (Button)findViewById(R.id.noteadd);
    noteAddbtn.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        listen_controller.addButtonAction();
      }
    });
  }
  public View findViewById(int id){
    return view.findViewById(id);
  }
  public View getView(){
    return view;
  }
  public void setListener(ViewListener listener){
    this.listen_controller = listener;
  }
  public void clearNoteContainer(){
	  this.container.removeAllViews();
  }
  public void addNoteContainer(String title, int note_id, int note_type){
	Log.i("addNoteCon","1");
    View note = View.inflate(this.context, R.layout.eachnotelistlayout, null);

    Log.i("addNoteCon","2");
    TextView note_title = (TextView)note.findViewById(R.id.notetitle);
    note_title.setText(title);
    note_title.setTag(R.id.notetitle,note_id);
    note_title.setTag(R.id.note_type,note_type);
    
    Button delete_btn = (Button)note.findViewById(R.id.deletebtn);
    delete_btn.setTag(R.id.notetitle,note_id);
    delete_btn.setTag(R.id.deletebtn,note);
    delete_btn.setTag(R.id.note_type,note_type);

    View.OnClickListener note_listener = new View.OnClickListener() {
      public void onClick(View v) {
        int id = v.getId();
        int note_id = (Integer) v.getTag(R.id.notetitle);
        if(id == R.id.notetitle){
          listen_controller.noteClickAction(note_id, (Integer)v.getTag(R.id.note_type));
        }else if(id == R.id.deletebtn){
          listen_controller.deleteButtonAction(note_id, (Integer)v.getTag(R.id.note_type), (View)v.getTag(R.id.deletebtn));
        }
      }
    };
    note_title.setOnClickListener(note_listener);
    delete_btn.setOnClickListener(note_listener);
    
    Log.i("addNoteCon","5");
    this.container.addView(note);
    Log.i("addNoteCon","6");
  }
  public void deleteNote(View note){
    this.container.removeView(note);
  }

  interface ViewListener{
    public void addButtonAction();
    public void noteClickAction(int note_id, int note_type);
    public void deleteButtonAction(int note_id, int note_type, View note);
  }
}