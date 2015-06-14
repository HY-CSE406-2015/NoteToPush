package com.notetopush;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class ControlNoteList extends Activity {

	Button noteAddbtn;
	Button noteDeletebtn;
	LinearLayout container;
	TextView noteTitle;
	String title;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_control_note_list);

		noteAddbtn = (Button) findViewById(R.id.noteadd);
		container = (LinearLayout) findViewById(R.id.container);
		
		noteAddbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LayoutInflater layoutInflater = 
						(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View eachNoteList = layoutInflater.inflate(R.layout.eachnotelistlayout,null);
				noteTitle = (TextView)eachNoteList.findViewById(R.id.notetitle);
				title = "note" + container.getChildCount(); //나중에 디비에서 받아와서 제목으로 하기
				
				noteDeletebtn = (Button)eachNoteList.findViewById(R.id.deletebtn);
				noteDeletebtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						((LinearLayout)eachNoteList.getParent()).removeView(eachNoteList);
					}
				});
				
				noteTitle.setText(title);
				container.addView(eachNoteList);
			}
		});

	}
}
