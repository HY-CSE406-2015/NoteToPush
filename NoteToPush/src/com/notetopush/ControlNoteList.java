package com.notetopush;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;



public class ControlNoteList extends Activity {
	
	Button noteAddbtn;
	View addedView;
	RelativeLayout scrolllayout;
	LinearLayout scrolllinear;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_note_list);
        
        noteAddbtn = (Button) findViewById(R.id.noteadd);
        
        noteAddbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				scrolllayout = (RelativeLayout)View.inflate(ControlNoteList.this, R.layout.eachnotelistlayout, null);
				scrolllinear = (LinearLayout)findViewById(R.id.scrolllinearview);
				if(scrolllinear.getChildCount()%2 == 0){
					scrolllayout.setBackgroundColor(Color.GRAY);
				}
				else{
					scrolllayout.setBackgroundColor(Color.WHITE);
				}
				scrolllinear.addView(scrolllayout);
			}
		});
    
    }
}
