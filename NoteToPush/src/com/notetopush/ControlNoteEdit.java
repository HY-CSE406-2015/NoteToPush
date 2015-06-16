package com.notetopush;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

public class ControlNoteEdit extends Activity {
	public static final String NOTE_ID = "com.notetopush.ControlNoteEdit.NOTE_ID";
	
	private static int note_id = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(note_id%3 == 0){
        	MemoNote note = new MemoNote(this);
        	
    		String note_title = "더미 메모 노트 "+note_id;
    		Long note_alarm = System.currentTimeMillis();
    		long note_write_time = System.currentTimeMillis();
    		
    		String note_content = "메모 노트 테스트 입니다.";
    		for(int loop=0;loop<note_id/3;loop++) note_content +="\n메모 노트 테스트 입니다.";
    		
    		note.setNote(note_title, note_alarm, note_write_time, note_content);
    		note.insertNote();
    		
    		int noti_id = note.getId();
    		ControlNotification.setMemoContent(this, noti_id, note_title, note_alarm, note_content);
    		
    		Log.d("Generate Note", "Type: Memo, Title: "+note_title);
    		
        }else if(note_id%3 == 1){
        	ToDoNote note = new ToDoNote(this);
        	
        	ArrayList<String> contents = new ArrayList<String>();
        	ArrayList<Boolean> is_checks = new ArrayList<Boolean>();
        	for(int todo_loop = 0; todo_loop<note_id; todo_loop++){
        		Log.d("ToDo Insert",""+note_id);
        		contents.add("todtodotodo" + todo_loop);
            	is_checks.add((note_id%2 == 0)?true:false);
        	}
        	String note_title = "더미 ToDo 노트 "+note_id;
    		Long note_alarm = System.currentTimeMillis();
    		long note_write_time = System.currentTimeMillis();
    		
    		note.setNote(note_title, note_alarm, note_write_time, contents, is_checks);
    		note.insertNote();
    		
    		int noti_id = note.getId();
    		ControlNotification.setToDoContent(this, noti_id, note_title, note_alarm, contents);
    		
    		Log.d("Generate Note", "Type: ToDo, Title: "+note_title);
        }else{
        	ImageNote note = new ImageNote(this);
        	
        	String note_title = "더미 이미지 노트 "+note_id;
    		Long note_alarm = System.currentTimeMillis();
    		long note_write_time = System.currentTimeMillis();
    		
    		Bitmap img = BitmapFactory.decodeResource(this.getResources(),R.drawable.test_big_img);
    		String content = "이미지 보조 텍스트 "+note_id;
    		
    		note.setNote(note_title, note_alarm, note_write_time, img, content);
    		note.insertNote();
    		
    		int noti_id = note.getId();
    		ControlNotification.setImgContent(this, noti_id, note_title, note_alarm, img, content);
    		
    		Log.d("Generate Note", "Type: Image, Title: "+note_title);
        }
        
        note_id++;
        finish();
    }
}