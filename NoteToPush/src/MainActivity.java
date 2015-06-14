package com.example.notetopush;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity{

	Note note;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		deleteDatabase("notetopush.db");
		note = new Note(this);
	}

	public void onClick(View v){
		Button btn_img = (Button)findViewById(R.id.insert_img);
		Button btn_todo = (Button)findViewById(R.id.insert_todo);
		Button btn_drop = (Button)findViewById(R.id.drop_all);
		Button btn_update = (Button)findViewById(R.id.update_note);

		if (v == btn_img) {
			ContentValues test = new ContentValues();
			test.put("type", 2);
			test.put("title", "imagetest");
			test.put("alarm", 4);
			test.put("write_time", 5);
			test.put("noti_id", 6);
			test.put("img", 7);
			test.put("content", "8");
			note.insertNote(test);
			Toast.makeText(this, "btn_img", Toast.LENGTH_SHORT).show();
		}

		else if (v == btn_todo) {
			ContentValues test = new ContentValues();
			test.put("type", 1);
			test.put("title", "todotest");
			test.put("alarm", 4);
			test.put("write_time", 5);
			test.put("noti_id", 6);
			test.put("todo_order", 7);
			test.put("content", "8");
			test.put("is_check", 9);
			note.insertNote(test);
			Toast.makeText(this, "btn_todo", Toast.LENGTH_SHORT).show();
		}

		else if (v == btn_drop) {
			Toast.makeText(this, "drop finished", Toast.LENGTH_SHORT).show();
			note.dropAll();
		}

		else if (v == btn_update) {
			Toast.makeText(this, "update finished", Toast.LENGTH_SHORT).show();
			ContentValues test = new ContentValues();
			test.put("type", 2);
			test.put("title", "success");
			test.put("alarm", 4);
			test.put("write_time", 5);
			test.put("noti_id", 6);
			test.put("todo_order", 7);
			test.put("img", 10);
			test.put("content", "success");
			test.put("is_check", 9);
			note.updateNote(test, 1);
		}
	}
//	public void deleteDB(){
//		String mPath = "/data/data" + this.getPackageName() + "/databases";
//		File mFile = new File(mPath);
//		mFile.delete();
//	}
//
//	public String getPackageName(){
//		return "com.example.notetopush";
//	}
}
