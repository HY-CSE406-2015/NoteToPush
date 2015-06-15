package com.notetopush;

import java.util.ArrayList;

import com.cengalabs.flatui.views.FlatButton;
import com.cengalabs.flatui.views.FlatCheckBox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewNoteContent {
	// Primary global field
	private Context context;
	private int type;
	private View view;
	private ViewListener listener;
	// View element global field
	private TextView title;
	private LinearLayout sub_view;
	
	public ViewNoteContent(Context context, int note_type){
		// Prolog
		this.context = context;
		this.type = note_type;
		this.view = View.inflate(context, R.layout.activity_content_note, null);
		setOnClick();
	}
	
	private void setOnClick(){
		View.OnClickListener click_listener = new View.OnClickListener() {
			public void onClick(View v) {
				if(listener != null){
					int id = v.getId();
					if(id == R.id.content_note_delete){
						listener.deleteNoteAction();
					}else if(id == R.id.content_note_modify){
						listener.modifyNoteAction();
					}
				}
			}
		};
		setViewElement(click_listener);
	}
	private void setViewElement(View.OnClickListener click_listener){
		this.title = (TextView)findViewById(R.id.content_note_title);
		FlatButton delete_btn = (FlatButton)findViewById(R.id.content_note_delete);
		delete_btn.setOnClickListener(click_listener);
		FlatButton modify_btn = (FlatButton)findViewById(R.id.content_note_modify);
		modify_btn.setOnClickListener(click_listener);
		FrameLayout content_frame = (FrameLayout)findViewById(R.id.content_note_content);
		setContent(content_frame);
	}
	private void setContent(FrameLayout content_frame){
		switch(this.type){
		case Note.MEMO_TYPE:
			this.sub_view = (LinearLayout)View.inflate(this.context, R.layout.activity_content_memo, null);
			content_frame.addView(sub_view);
			break;
		case Note.TODO_TYPE:
			this.sub_view = (LinearLayout)View.inflate(this.context, R.layout.activity_content_todo, null);
			content_frame.addView(sub_view);
			break;
		case Note.IMG_TYPE:
			this.sub_view = (LinearLayout)View.inflate(this.context, R.layout.activity_content_img, null);
			content_frame.addView(sub_view);
			break;
		default:
			try {
				throw new Exception("com.notetopush.NoteTypeException: Exception occured missing note type match");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setMemo(String text, String[] date){
		TextView t_view = (TextView)sub_view.findViewById(R.id.acm_content_memo);
		t_view.setText(text);
		
		if(date == null){ 
			LinearLayout frame = ((LinearLayout)sub_view.findViewById(R.id.acm_alarm_frame));
			frame.setVisibility(View.GONE);
		}else{
			TextView year = (TextView)sub_view.findViewById(R.id.acm_alarm_year);
			year.setText(date[0]);
			TextView month = (TextView)sub_view.findViewById(R.id.acm_alarm_month);
			month.setText(date[1]);
			TextView day = (TextView)sub_view.findViewById(R.id.acm_alarm_day);
			day.setText(date[2]);
			TextView hour = (TextView)sub_view.findViewById(R.id.acm_alarm_hour);
			hour.setText(date[3]);
			TextView minute = (TextView)sub_view.findViewById(R.id.acm_alarm_min);
			minute.setText(date[4]);
			TextView apm = (TextView)sub_view.findViewById(R.id.acm_alarm_apm);
			apm.setText(date[5]);
		}
	}
	public void setToDo(ArrayList<String> str_list, ArrayList<Boolean> able_list,String[] date){
		if(str_list.size() != able_list.size())
			try{
				throw new Exception("com.notetopush.ToDoException: Exception occured missing match todo data!");
			}catch(Exception e){
				e.printStackTrace();
			}
		else{
			LinearLayout parent = (LinearLayout)sub_view.findViewById(R.id.act_todo);
			for(int todo_loop = 0; todo_loop<str_list.size(); todo_loop++){
				FlatCheckBox c_box = new FlatCheckBox(context);
				c_box.setId(todo_loop);
				LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

				c_box.setLayoutParams(param);
				c_box.setText(str_list.get(todo_loop));
				c_box.setGravity(0x00800003);
				c_box.setChecked(able_list.get(todo_loop));
				todoSetter(c_box,able_list.get(todo_loop));
				if(!(todo_loop==0)){
					c_box.setBackgroundResource(R.drawable.shape_border_upper);
				}
				c_box.setOnClickListener(new View.OnClickListener(){
					public void onClick(View v) {
						if(listener != null){
							FlatCheckBox cast = (FlatCheckBox)v;
							Log.d(null, "touch "+cast.getId()+" checked:"+cast.isChecked());
							todoSetter(cast, cast.isChecked());
							listener.todoCheckAction(cast.getId(), cast.isChecked());
							Log.d(null, "End touch "+cast.getId()+" checked:"+cast.isChecked());
						}else
							try{
								throw new Exception("com.notetopush.ListenerException: Exception occured missing Listener!");
							}catch(Exception e){
								e.printStackTrace();
							}
					}});
				c_box.getAttributes().setTheme(R.array.dark, c_box.getResources());
				parent.addView(c_box);
			}
		}
		if(date == null){ 
			LinearLayout frame = ((LinearLayout)sub_view.findViewById(R.id.act_alarm_frame));
			frame.setVisibility(View.GONE);
		}else{
			TextView year = (TextView)sub_view.findViewById(R.id.act_alarm_year);
			year.setText(date[0]);
			TextView month = (TextView)sub_view.findViewById(R.id.act_alarm_month);
			month.setText(date[1]);
			TextView day = (TextView)sub_view.findViewById(R.id.act_alarm_day);
			day.setText(date[2]);
			TextView hour = (TextView)sub_view.findViewById(R.id.act_alarm_hour);
			hour.setText(date[3]);
			TextView minute = (TextView)sub_view.findViewById(R.id.act_alarm_min);
			minute.setText(date[4]);
			TextView apm = (TextView)sub_view.findViewById(R.id.act_alarm_apm);
			apm.setText(date[5]);
		}
	}
	private void todoSetter(FlatCheckBox c_box, boolean enable){
		if(enable)
			c_box.setPaintFlags(c_box.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
		else
			c_box.setPaintFlags(c_box.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
	}
	public void setImage(Bitmap image,String sub_content, String[] date){
		ImageView i_view = (ImageView)sub_view.findViewById(R.id.aci_content_img);
		i_view.setImageBitmap(image);
		if(sub_content == null){
			LinearLayout frame = (LinearLayout)sub_view.findViewById(R.id.aci_sub_content_frame);
			frame.setVisibility(View.GONE);
		}else{
			TextView sub = (TextView)sub_view.findViewById(R.id.aci_sub_content);
			sub.setText(sub_content);
		}
		if(date == null){ 
			LinearLayout frame = ((LinearLayout)sub_view.findViewById(R.id.aci_alarm_frame));
			frame.setVisibility(View.GONE);
		}else{
			TextView year = (TextView)sub_view.findViewById(R.id.aci_alarm_year);
			year.setText(date[0]);
			TextView month = (TextView)sub_view.findViewById(R.id.aci_alarm_month);
			month.setText(date[1]);
			TextView day = (TextView)sub_view.findViewById(R.id.aci_alarm_day);
			day.setText(date[2]);
			TextView hour = (TextView)sub_view.findViewById(R.id.aci_alarm_hour);
			hour.setText(date[3]);
			TextView minute = (TextView)sub_view.findViewById(R.id.aci_alarm_min);
			minute.setText(date[4]);
			TextView apm = (TextView)sub_view.findViewById(R.id.aci_alarm_apm);
			apm.setText(date[5]);
		}
	}
	
	public View findViewById(int id){
		return view.findViewById(id);
	}
	public View getView(){
		return view;
	}
	public void setListener(ViewListener listener){
		this.listener = listener;
	}
	public void setTitle(String text){
		this.title.setText(text);
	}
	
	interface ViewListener{
		public void deleteNoteAction();
		public void modifyNoteAction();
		public void todoCheckAction(int order, boolean checked);
	}
}
