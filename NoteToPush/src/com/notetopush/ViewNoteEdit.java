package com.notetopush;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class ViewNoteEdit{

	// Primary elements
	private Context context;
	private int type;
	private View view;
	private ViewListener listener;
	private ScrollView sub_view;
	
	// Common elements
	private EditText title;
	private CheckBox alarm_set_box;
	private Button date_btn;
	private DatePicker d_picker;
	private OnDateChangedListener d_listener;
	private Button time_btn;
	private TimePicker t_picker;
	private OnTimeChangedListener t_listener;
	
	// Memo element
	private EditText memo_content;
	
	// Todo element
	private LinearLayout todos;
	private int todo_id;
	
	// Image element
	private Bitmap image;
	private EditText image_sub;

	public ViewNoteEdit(Context context){
		this.context = context;
		//this.type = note_type;
		this.view = View.inflate(context, R.layout.activity_edit_note, null);
		setViewElement(Note.MEMO_TYPE);
	}
	
	public ViewNoteEdit(Context context, int note_type){
		this.context = context;
		this.type = note_type;
		this.view = View.inflate(context, R.layout.activity_edit_note, null);
		setViewElement(note_type);
	}
	
	public View getView() {
		return view;
	}
	
	public void setListener(ViewListener listener){
		this.listener = listener;
	}
	
	public View findViewById(int id){
		return view.findViewById(id);
	}

	private View.OnClickListener setOnClick(){
		View.OnClickListener click_listener = new View.OnClickListener() {
			public void onClick(View v) {
				if(listener!=null){
					int id = v.getId();
					if(id == R.id.ace_cancel_btn){
						listener.cancelAction();
					} else if(id == R.id.ace_confirm_btn){
						listener.confirmAction(type);
					} else if(id == R.id.ace_type_memo_btn){
						type = Note.MEMO_TYPE;
						setViewElement(Note.MEMO_TYPE);
					} else if(id == R.id.ace_type_todo_btn){
						type = Note.TODO_TYPE;
						setViewElement(Note.TODO_TYPE);
					} else if(id == R.id.ace_type_img_btn){
						type = Note.IMG_TYPE;
						setViewElement(Note.IMG_TYPE);
					} else if(id == R.id.ace_set_alarm){
						boolean check = ((CheckBox)v).isChecked();
						setAlarmCheck(check);
					} else if(id == R.id.ace_datepick_btn){
						findViewById(R.id.ace_datepicker).setVisibility(View.VISIBLE);
						findViewById(R.id.ace_timepicker).setVisibility(View.GONE);
					} else if(id == R.id.ace_timepick_btn){
						findViewById(R.id.ace_timepicker).setVisibility(View.VISIBLE);
						findViewById(R.id.ace_datepicker).setVisibility(View.GONE);
					} else if(id == R.id.ace_img_chose_btn){
						listener.choseImageAction();
					}
				}
			}
		};
		return click_listener;
	}
	
	protected void setAlarmCheck(boolean check) {
		if(check){
			this.sub_view.findViewById(R.id.ace_picker_frame).setVisibility(View.VISIBLE);
		}else{
			this.sub_view.findViewById(R.id.ace_picker_frame).setVisibility(View.GONE);
		}
	}

	private void setViewElement(int type){
		this.todo_id = 0;
		View.OnClickListener click_listener = setOnClick();
		
		Button btn_memo = (Button) findViewById(R.id.ace_type_memo_btn);
		btn_memo.setEnabled(true);
		btn_memo.setOnClickListener(click_listener);
		Button btn_todo = (Button) findViewById(R.id.ace_type_todo_btn);
		btn_todo.setEnabled(true);
		btn_todo.setOnClickListener(click_listener);
		Button btn_img = (Button) findViewById(R.id.ace_type_img_btn);
		btn_img.setEnabled(true);
		btn_img.setOnClickListener(click_listener);
		Button btn_cancel = (Button)findViewById(R.id.ace_cancel_btn);
		btn_cancel.setOnClickListener(click_listener);
		Button btn_confirm = (Button)findViewById(R.id.ace_confirm_btn);
		btn_confirm.setOnClickListener(click_listener);
		
		FrameLayout frame = (FrameLayout)findViewById(R.id.ace_note_content);
		frame.removeAllViews();
		switch(type){
		case Note.MEMO_TYPE:
			btn_memo.setEnabled(false);
			this.sub_view = (ScrollView)View.inflate(this.context, R.layout.edit_memo, null);
			
			this.memo_content = (EditText)this.sub_view.findViewById(R.id.ace_memo_content);
			
			break;
		case Note.TODO_TYPE:
			btn_todo.setEnabled(false);
			this.sub_view = (ScrollView)View.inflate(this.context, R.layout.edit_todo, null);
			
			this.todos = (LinearLayout)this.sub_view.findViewById(R.id.ace_todo_list);
			inflateToDo();
			
			break;
		case Note.IMG_TYPE:
			btn_img.setEnabled(false);
			this.sub_view = (ScrollView)View.inflate(this.context, R.layout.edit_img, null);
			
			Button chose_btn = (Button)this.sub_view.findViewById(R.id.ace_img_chose_btn);
			chose_btn.setOnClickListener(click_listener);
			this.image_sub = (EditText)this.sub_view.findViewById(R.id.ace_image_sub_content);
			
			break;
		default:
			try{
				throw new Exception();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String[] date = ControlNoteEdit.getTime(this.context, System.currentTimeMillis());
		
		this.title = (EditText)this.sub_view.findViewById(R.id.ace_title);
		CheckBox alarm_set_box = (CheckBox)this.sub_view.findViewById(R.id.ace_set_alarm);
		alarm_set_box.setChecked(false);
		alarm_set_box.setOnClickListener(click_listener);
		this.sub_view.findViewById(R.id.ace_picker_frame).setEnabled(false);
		this.date_btn = (Button)this.sub_view.findViewById(R.id.ace_datepick_btn);
		this.date_btn.setOnClickListener(click_listener);
		this.d_picker = (DatePicker)this.sub_view.findViewById(R.id.ace_datepicker);
		this.d_picker.setVisibility(View.GONE);
		this.d_listener = new OnDateChangedListener(){
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				dayChanged(year,monthOfYear,dayOfMonth);
			}
		};
		this.d_picker.init(Integer.parseInt(date[0]), Integer.parseInt(date[1])-1, Integer.parseInt(date[2]), this.d_listener);
		this.time_btn = (Button)this.sub_view.findViewById(R.id.ace_timepick_btn);
		this.time_btn.setOnClickListener(click_listener);
		this.t_picker = (TimePicker)this.sub_view.findViewById(R.id.ace_timepicker);
		this.t_picker.setVisibility(View.GONE);
		this.t_listener = new OnTimeChangedListener(){
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				timeChanged(hourOfDay, minute);
			}
		};
		this.t_picker.setOnTimeChangedListener(this.t_listener);
		this.t_picker.setCurrentHour(Integer.parseInt(date[3]));
		this.t_picker.setCurrentMinute(Integer.parseInt(date[4]));
		frame.addView(this.sub_view);
	}
	public void deleteTypeFrame(){
		findViewById(R.id.ace_type_layout).setVisibility(View.GONE);
	}
//	<EditText
//    android:id="@+id/todo_cont_1"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent"
//    android:text="input List" />
	public void inflateToDo(){
		EditText new_todo = new EditText(this.context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		new_todo.setLayoutParams(params);
		new_todo.setId(this.todo_id);
		new_todo.setOnFocusChangeListener(new OnFocusChangeListener(){
			public void onFocusChange(View v, boolean hasFocus) {
				if(v.getId() == todo_id){
					if(hasFocus){
						todo_id ++;
						inflateToDo();
					}
				}
			}
		});
		this.todos.addView(new_todo);
	}

	protected void timeChanged(int hourOfDay, int minute) {
		this.time_btn.setText(hourOfDay+":"+minute);
	}

	protected void dayChanged(int year, int monthOfYear, int dayOfMonth) {
		this.date_btn.setText(year+"."+(monthOfYear+1)+"."+dayOfMonth);
	}
	public void setImage(Bitmap image){
		ImageView image_view = (ImageView)this.sub_view.findViewById(R.id.ace_img_content);
		image_view.setImageBitmap(image);
		this.image = image;
	}
	public String getTitle(){
		return this.title.getText().toString();
	}
	public Long getAlarm(){
		if(((CheckBox)findViewById(R.id.ace_set_alarm)).isChecked()){
			String str_date=this.d_picker.getYear()+"-"+(this.d_picker.getMonth()+1)+"-"+this.d_picker.getDayOfMonth()+"-"+this.t_picker.getCurrentHour()+"-"+this.t_picker.getCurrentMinute();
			Locale locale = context.getResources().getConfiguration().locale;
			DateFormat formatter = new SimpleDateFormat("yyyy-M-d-k-m",locale);
			Date date;
			try {
				date = (Date)formatter.parse(str_date);
				return date.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			return null;
		}else{
			return null;
		}
	}
	public String getMemoContent(){
		return this.memo_content.getText().toString();
	}
	
	public Bitmap getImageContent(){
		return this.image;
	}
	public String getIamgeSubContent(){
		return this.image_sub.getText().toString();
	}
	public ArrayList<String> getTodos(){
		ArrayList<String> todos = new ArrayList<String>();
		
		int numbers = this.todos.getChildCount();
		for(int loop=0; loop<numbers;loop++){
			String strings = ((EditText)this.todos.getChildAt(loop)).getText().toString();
			if(strings.length() != 0 & strings != null) todos.add(strings);
		}
		
		return todos;
	}

	interface ViewListener{
		public void cancelAction();
		public void choseImageAction();
		public void confirmAction(int type);
	}

}