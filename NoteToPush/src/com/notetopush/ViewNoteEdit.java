package com.notetopush;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ViewNoteEdit{

	private Context context;
	private int type;
	private View view;
	private ViewListener listener;

	public EditText memo_content;
	public ImageView _image;

	private LinearLayout sub_view;

	public ViewNoteEdit(Context context, int note_type){
		this.context = context;
		this.type = note_type;
		this.view = View.inflate(context, R.layout.activity_edit_note, null);
		setOnClick();
	}

	public View getview() {
		return view;
	}

	public void setListener(ViewListener listener){
		this.listener = listener;
	}

	public void setMemo(String title, String text){
		EditText memo_title = (EditText) findViewById(R.id.memo_title);
		memo_title.setText(title);
		//date
		
		//content
		EditText memo_content = (EditText) findViewById(R.id.memo_content);
		memo_content.setText(text);
	}

	public void setToDo(String title, ArrayList<String> str_list, ArrayList<Boolean> able_list){
		EditText todo_title = (EditText) findViewById(R.id.todo_title);
		todo_title.setText(title);

		//date

		//content
	}

	public void setImage(String title, Bitmap image){
		EditText img_title = (EditText) findViewById(R.id.img_title);
		img_title.setText(title);

		//date

		//content
		
		ImageView i_view = (ImageView)sub_view.findViewById(R.id.img_content);
		i_view.setImageBitmap(image);
	}

	public View findViewById(int id){
		return view.findViewById(id);
	}

	private void setOnClick(){
		View.OnClickListener click_listener = new View.OnClickListener() {
			public void onClick(View v) {
				if(listener!=null){
					int id = v.getId();
					if(id == R.id.btn_cancel){
						listener.cancelAction();
					} else if(id == R.id.btn_confirm){
						listener.confirmAction();
					}
				}
			}
		};
		setViewElement(click_listener);
	}

	private void setViewElement(View.OnClickListener click_listener){
		Button btn_cancel = (Button)findViewById(R.id.btn_cancel);
		Button btn_confirm = (Button)findViewById(R.id.btn_confirm);

		btn_cancel.setOnClickListener(click_listener);
		btn_confirm.setOnClickListener(click_listener);

		FrameLayout content_frame = (FrameLayout)findViewById(R.id.edit_note_content);
		setContent(content_frame);
	}

	public void setContent(FrameLayout content_frame){

		switch(this.type){
		case Note.MEMO_TYPE:
			this.sub_view = (LinearLayout)View.inflate(this.context, R.layout.edit_memo, null);
			content_frame.addView(sub_view);
			break;
		case Note.TODO_TYPE:
			this.sub_view = (LinearLayout)View.inflate(this.context, R.layout.edit_todo, null);
			content_frame.addView(sub_view);
			break;
		case Note.IMG_TYPE:
			this.sub_view = (LinearLayout)View.inflate(this.context, R.layout.edit_img, null);
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

	//뷰 리스너 함수
	interface ViewListener{
		public void cancelAction();
		public long getDateTime();
		public void choseImageAction();
		public void confirmAction();
	}

}
