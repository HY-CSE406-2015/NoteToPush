package com.notetopush;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class ControlNoteEdit extends Activity implements ViewNoteEdit.ViewListener {
	public static final String NOTE_ID = "com.notetopush.ControlNoteEdit.NOTE_ID";
	public static final String NOTE_TYPE = "com.notetopush.ControlNoteEdit.NOTE_TYPE";
	private static final String TEMP_PHOTO_FILE = "temp.jpg";       // 이미지 임시 저장파일
	private static final int REQ_CODE_PICK_IMAGE = 0;				// REQ_CODE_PICK_IMAGE == requestCode
	
//	private static int note_id = 0;
	private Note note;
	private int note_id;
	private int note_type;
	private boolean is_modify;
	private ViewNoteEdit view;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        int intent_check = getIntent().getIntExtra(NOTE_ID, -1);
        if(intent_check != -1){
        	this.note_id = intent_check;
        	this.note_type = getIntent().getIntExtra(NOTE_TYPE, -1);
        	this.is_modify = true;
        	switch(this.note_type){
        	case Note.MEMO_TYPE:
        		this.note = new MemoNote(this, intent_check);
        		break;
        	case Note.TODO_TYPE:
        		this.note = new ToDoNote(this, intent_check);
        		break;
        	case Note.IMG_TYPE:
        		this.note = new ImageNote(this, intent_check);
        		break;
    		default:
    			try{
    				throw new Exception();
    			}catch(Exception e){
    				e.printStackTrace();
    			}
        	}
        }else this.is_modify = false;
        
        this.view = new ViewNoteEdit(this);
        this.view.setListener(this);
        setView();
        setContentView(this.view.getView());
        
//        if(note_id%3 == 0){
//        	MemoNote note = new MemoNote(this);
//        	
//    		String note_title = "더미 메모 노트 "+note_id;
//    		Long note_alarm = System.currentTimeMillis();
//    		long note_write_time = System.currentTimeMillis();
//    		
//    		String note_content = "메모 노트 테스트 입니다.";
//    		for(int loop=0;loop<note_id/3;loop++) note_content +="\n메모 노트 테스트 입니다.";
//    		
//    		note.setNote(note_title, note_alarm, note_write_time, note_content);
//    		note.insertNote();
//    		
//    		int noti_id = note.getId();
//    		ControlNotification.setMemoContent(this, noti_id, note_title, note_alarm, note_content);
//    		
//    		Log.d("Generate Note", "Type: Memo, Title: "+note_title);
//    		
//        }else if(note_id%3 == 1){
//        	ToDoNote note = new ToDoNote(this);
//        	
//        	ArrayList<String> contents = new ArrayList<String>();
//        	ArrayList<Boolean> is_checks = new ArrayList<Boolean>();
//        	for(int todo_loop = 0; todo_loop<note_id; todo_loop++){
//        		Log.d("ToDo Insert",""+note_id);
//        		contents.add("todtodotodo" + todo_loop);
//            	is_checks.add((note_id%2 == 0)?true:false);
//        	}
//        	String note_title = "더미 ToDo 노트 "+note_id;
//    		Long note_alarm = System.currentTimeMillis();
//    		long note_write_time = System.currentTimeMillis();
//    		
//    		note.setNote(note_title, note_alarm, note_write_time, contents, is_checks);
//    		note.insertNote();
//    		
//    		int noti_id = note.getId();
//    		ControlNotification.setToDoContent(this, noti_id, note_title, note_alarm, contents);
//    		
//    		Log.d("Generate Note", "Type: ToDo, Title: "+note_title);
//        }else{
//        	ImageNote note = new ImageNote(this);
//        	
//        	String note_title = "더미 이미지 노트 "+note_id;
//    		Long note_alarm = System.currentTimeMillis();
//    		long note_write_time = System.currentTimeMillis();
//    		
//    		Bitmap img = BitmapFactory.decodeResource(this.getResources(),R.drawable.test_big_img);
//    		String content = "이미지 보조 텍스트 "+note_id;
//    		
//    		note.setNote(note_title, note_alarm, note_write_time, img, content);
//    		note.insertNote();
//    		
//    		int noti_id = note.getId();
//    		ControlNotification.setImgContent(this, noti_id, note_title, note_alarm, img, content);
//    		
//    		Log.d("Generate Note", "Type: Image, Title: "+note_title);
//        }
//        
//        note_id++;
//        finish();
    }
    private void setView(){
    	
    }
    static public String[] getTime(Context context, long miltime){
    	String[] date = new String[5];
		Calendar c = Calendar.getInstance();
	    c.setTimeInMillis(miltime);
	    Date d = c.getTime();
	    
	    Locale locale = context.getResources().getConfiguration().locale;
	    
	    date[0] = (new SimpleDateFormat("yyyy",locale)).format(d);
	    date[1] = (new SimpleDateFormat("M",locale)).format(d);
	    date[2] = (new SimpleDateFormat("d",locale)).format(d);
	    date[3] = (new SimpleDateFormat("k",locale)).format(d);
	    date[4] = (new SimpleDateFormat("m",locale)).format(d);
	    
	    return date;
    }

	@Override
	public void cancelAction() {
		finish();
	}

	@Override
	public void choseImageAction() {
		Intent intent = new Intent(
				Intent.ACTION_GET_CONTENT,      // 또는 ACTION_PICK
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/*");              // 모든 이미지
		intent.putExtra("crop", "true");        // Crop기능 활성화
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());     // 임시파일 생성
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()); // 포맷방식

		startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
	}
	private Uri getTempUri() {
		return Uri.fromFile(getTempFile());
	}
	private File getTempFile() {
		if (isSDCARDMOUNTED()) {
			File f = new File(Environment.getExternalStorageDirectory(), // 외장메모리 경로
					TEMP_PHOTO_FILE);
			try {
				f.createNewFile();      // 외장메모리에 temp.jpg 파일 생성
			} catch (IOException e) {
				e.printStackTrace();
			}

			return f;
		} else
			return null;
	}
	private boolean isSDCARDMOUNTED() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED))
			return true;

		return false;
	}
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageData) {
		super.onActivityResult(requestCode, resultCode, imageData);

		switch (requestCode) {
		case REQ_CODE_PICK_IMAGE:
			if (resultCode == RESULT_OK) {
				if (imageData != null) {
					String filePath = Environment.getExternalStorageDirectory()
							+ "/temp.jpg";

					System.out.println("path" + filePath); // logCat으로 경로확인.

					Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
					// temp.jpg파일을 Bitmap으로 디코딩한다.

					view.setImage(selectedImage); 
					// temp.jpg파일을 이미지뷰에 씌운다.
				}
			}
			break;
		}
	}

	@Override
	public void confirmAction(int type) {
		String note_content = null;
		ArrayList<String> todos = null;
		ArrayList<Boolean> checks = null;
		Bitmap image_content = null;
		String image_sub = null;
		
		String title = this.view.getTitle();
		Long alarm = this.view.getAlarm();
		long write_time = System.currentTimeMillis();
		switch(type){
		case Note.MEMO_TYPE:
			note_content = this.view.getMemoContent();
			break;
		case Note.TODO_TYPE:
			todos = this.view.getTodos();
			checks = new ArrayList<Boolean>();
			for(int loop=0; loop<todos.size(); loop++)
				checks.add(false);
			break;
		case Note.IMG_TYPE:
			image_content = this.view.getImageContent();
			image_sub = this.view.getIamgeSubContent();
			break;
		default:
			try{
				throw new Exception();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(is_modify){
    		switch(type){
    		case Note.MEMO_TYPE:
    			((MemoNote)this.note).setNote(title, alarm, write_time, note_content);
    			((MemoNote)this.note).updateNote();
    			break;
    		case Note.TODO_TYPE:
    			break;
    		case Note.IMG_TYPE:
    			((ImageNote)this.note).setNote(title, alarm, write_time, image_content, image_sub);
    			((ImageNote)this.note).updateNote();
    			break;
			default:
				try{
					throw new Exception();
				}catch(Exception e){
					e.printStackTrace();
				}
    		}
		}else{
			switch(type){
    		case Note.MEMO_TYPE:
    			this.note = new MemoNote(this);
    			((MemoNote)this.note).setNote(title, alarm, write_time, note_content);
    			((MemoNote)this.note).insertNote();
        		int memo_id = note.getId();
        		if(alarm == null){ Log.d("deb", "is null"); alarm = System.currentTimeMillis();}
        		ControlNotification.setMemoContent(this, memo_id, title, alarm, note_content);
    			break;
    		case Note.TODO_TYPE:
    			this.note = new ToDoNote(this);
    			((ToDoNote)this.note).setNote(title, alarm, write_time, todos, checks);
    			((ToDoNote)this.note).insertNote();
    			int todo_id = note.getId();
    			if(alarm == null) alarm = System.currentTimeMillis();
    			ControlNotification.setToDoContent(this, todo_id, title, alarm, todos);
    			this.note = new ToDoNote(this);
    			break;
    		case Note.IMG_TYPE:
    			this.note = new ImageNote(this);
    			((ImageNote)this.note).setNote(title, alarm, write_time, image_content, image_sub);
    			((ImageNote)this.note).insertNote();
    			int img_id = note.getId();
    			if(alarm == null) alarm = System.currentTimeMillis();
        		ControlNotification.setImgContent(this, img_id, title, alarm, image_content, image_sub);
    			break;
			default:
				try{
					throw new Exception();
				}catch(Exception e){
					e.printStackTrace();
				}
    		}
		}
		finish();
	}
}