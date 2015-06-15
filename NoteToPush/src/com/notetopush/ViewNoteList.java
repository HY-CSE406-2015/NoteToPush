// ViewNoteList
package com.notetopush;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
  public void addNoteContainer(String title, int note_id){
	Log.i("addNoteCon","1");
    View note = View.inflate(this.context, R.layout.eachnotelistlayout, null);
//    
//	EditText noteId = (EditText)note.findViewById(R.id.editText1);
//    noteId.setText(title);
    Log.i("addNoteCon","2");
    TextView note_title = (TextView)note.findViewById(R.id.notetitle);
    Log.i("addNoteCon","2.1 //"+note_title.getId() );
    note_title.setText(title);
    Log.i("addNoteCon","2.2");
    //note_title.setTag(0, new Integer(note_id));
    note_title.setTag(R.id.notetitle,note_id);
    Log.i("addNoteCon","3");
    Button delete_btn = (Button)note.findViewById(R.id.deletebtn);
    //delete_btn.setTag(0, new Integer(note_id));
    delete_btn.setTag(R.id.notetitle,note_id);
    //delete_btn.setTag(1, note);
    delete_btn.setTag(R.id.deletebtn,note);
    Log.i("addNoteCon","4");

    View.OnClickListener note_listener = new View.OnClickListener() {
      public void onClick(View v) {
        int id = v.getId();
        int note_id = (Integer) v.getTag(R.id.notetitle);
        if(id == R.id.notetitle){
          //listen_controller.noteClickAction(note_id);
          Log.i("Click","Title = "+note_id);
        }else if(id == R.id.deletebtn){
          //listen_controller.deleteButtonAction(note_id, (View)v.getTag(1));
          //listen_controller.deleteButtonAction(note_id, (View)v.getTag(R.id.deletebtn));
          Log.i("Click","Delete Title = "+note_id);
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
    public void noteClickAction(int note_id);
    public void deleteButtonAction(int note_id, View note);
  }
}