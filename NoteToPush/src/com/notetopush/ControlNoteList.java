package com.notetopush;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ControlNoteList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control_note_list);
		Button btn1 = (Button)findViewById(R.id.button1);
		final ControlNotification ctrnoti = new ControlNotification();
		
		btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ctrnoti.setTitle("제목이다", System.currentTimeMillis(), ControlNoteList.this);
				//ctrnoti.setMemoContent("내용!!");
				BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.test_img);
				Bitmap img = drawable.getBitmap();
				ctrnoti.setImgContent(img, "내용ㅇㅇㅇㅇ");
				
				ctrnoti.settingNotification(1);
				
			}
		});
		
		Button btn2 = (Button)findViewById(R.id.button2);
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ctrnoti.deletePreviousNotification(1, ControlNoteList.this);
			}
		});
	
	
	}
	
}



