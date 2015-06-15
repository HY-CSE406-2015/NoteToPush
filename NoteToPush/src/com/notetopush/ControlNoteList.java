package com.notetopush;


import java.util.ArrayList;
import java.util.Currency;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class ControlNoteList extends Activity {
	Note imgnote;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control_note_list);

		
		BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.test_img);
		Bitmap img = drawable.getBitmap();
		
		Button btn1 = (Button)findViewById(R.id.button1);
		Button btn2 = (Button)findViewById(R.id.button2);
		Note imgnote = new Note(this);
//		imgnote.dropAll();
		
		imgnote.insertNote(imgnote.setNote("test이미지노트", 2, System.currentTimeMillis(),
				System.currentTimeMillis(), 1, "이미지노트다", img));
		imgnote.selectNote(1);
		
		Note mMnote = new Note(this);
		mMnote.insertNote(mMnote.setNote("Test MemoNote",0,System.currentTimeMillis(),
				System.currentTimeMillis(),2,"메모노트다"));
		mMnote.selectNote(2);
	
		/*final ControlNotification ctrnoti = new ControlNotification();
		ArrayList<String> todoList = new ArrayList();

		todoList.add("Title1");
		todoList.add("Title2");
		todoList.add("Title3");

		final String[] tl = todoList.toArray(new String[todoList.size()]);


		btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ctrnoti.setTitle("제목이다", System.currentTimeMillis(), ControlNoteList.this);
				//ctrnoti.setMemoContent("내용!!");

				BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.test_img);
				Bitmap img = drawable.getBitmap();
				ctrnoti.setImgContent(img, "내용ㅇㅇㅇㅇ");

				Log.i("todolist",tl[0]);
				Log.i("todolist",tl[1]);
				Log.i("todolist",tl[2]);
				ctrnoti.setToDoContent(tl);
				ctrnoti.settingNotification(1);

			}
		});
		//Notification Check
		 */ 

		/*btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ctrnoti.deletePreviousNotification(1, ControlNoteList.this);
			}
		});*/
		//		Button btn1 = (Button)findViewById(R.id.btn_insert);
		//		Button btn2 = (Button)findViewById(R.id.btn_check);
		btn1.setOnClickListener(ocl);
		btn2.setOnClickListener(ocl);
	}

	View.OnClickListener ocl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (v.getId() == R.id.button1) {
				Toast.makeText(ControlNoteList.this, "btn1", Toast.LENGTH_SHORT).show();			
				//			note.insertNote(note);
			}

			else if (v.getId() == R.id.button2) {
				Toast.makeText(ControlNoteList.this, "btn2", Toast.LENGTH_SHORT).show();
				imgnote.dropAll();
			}

		}
	};

}



