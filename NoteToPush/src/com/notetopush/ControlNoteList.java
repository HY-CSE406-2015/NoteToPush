package com.notetopush;

import java.util.Currency;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
				ctrnoti.setMemoContent("내용!!");
				ctrnoti.settingNotification(ControlNoteList.this);
				
			}
		});
	
	
	}
	
}



