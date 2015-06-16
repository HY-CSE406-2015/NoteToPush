package com.notetopush;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

public class ControlNoteEdit extends Activity implements ViewNoteEdit.ViewListener{

	private ViewNoteEdit view;
	private Note note;

	private int note_type;
	
	//이미지 호출시 사용할 변수
	private static final String TEMP_PHOTO_FILE = "temp.jpg";       // 이미지 임시 저장파일
	private static final int REQ_CODE_PICK_IMAGE = 0;				// REQ_CODE_PICK_IMAGE == requestCode

	private String datetime = "";
	private long timestamp;

	public static final String NOTE_ID = "com.notetopush.ControlNoteEdit.NOTE_ID";

	private static int note_id = 0;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		note_type = 0;
		
		this.view = new ViewNoteEdit(this, note_type);
		this.view.setListener(this);
		setContentView(this.view.getview());
		setView();
		getDateTime();
	}

	private void setView(){
		String title = "더미 메모 노트";
		String text = "더메 컨텐트";
		int type = note_type;
		
		switch(type){
		case Note.MEMO_TYPE:
			//String text = ((MemoNote)this.note).getContent();
			this.view.setMemo(title,text);
			break;
		case Note.TODO_TYPE:
			ArrayList<String> str_list = ((ToDoNote)this.note).getContents();
			ArrayList<Boolean> able_list = ((ToDoNote)this.note).getChecks();
			Log.d("ToDo draw", "Number of elem is "+str_list.size());
			this.view.setToDo(title, str_list, able_list);
			break;
		case Note.IMG_TYPE:
			//ImageNote sub = ((ImageNote)this.note);
			Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.test_big_img);
			this.view.setImage(title, image);
			break;
		default:
			try {
				throw new Exception("com.notetopush.NoteTypeException: Exception occured missing note type match");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void getNoteContent(){
		int type = note_type;
		switch(type){
		case Note.MEMO_TYPE:
			break;
			
		case Note.TODO_TYPE:
			break;
			
		case Note.IMG_TYPE:
			break;
		}
	}

	public void cancelAction(){
		finish();
	}

	public void confirmAction(){
		getNoteContent();
		//update();
	}

	public long getDateTime(){
		
		DatePicker dp = (DatePicker)findViewById(R.id.datepicker);
		TimePicker tp = (TimePicker)findViewById(R.id.timepicker);
		String year = String.valueOf(dp.getYear());
		String day = String.valueOf(dp.getDayOfMonth());
		String month = String.valueOf(dp.getMonth()+1);
		String hour = String.valueOf(tp.getCurrentHour());
		String minutes = String.valueOf(tp.getCurrentMinute());
		
		if((dp.getMonth()+1) <= 9){
			month = "0"+month;
		}
		if(dp.getDayOfMonth()<9){
			day = "0"+day;
		}
		if(tp.getCurrentHour()<=9){
			hour = "0"+hour;
		}
		if(tp.getCurrentMinute()<=9){
			minutes = "0"+minutes;
		}

		datetime = ""+year+month+day+hour+minutes; //yyyyMMddhhmm

		timestamp = Long.parseLong(datetime);
		Log.d("DateTime", datetime + " timetable is "+ timestamp);
		return timestamp;
	}



	//image 선택 호출
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

	/** 임시 저장 파일의 경로를 반환 */
	private Uri getTempUri() {
		return Uri.fromFile(getTempFile());
	}

	/** 외장메모리에 임시 이미지 파일을 생성하여 그 파일의 경로를 반환  */
	private File getTempFile() {
		if (isSDCARDMOUNTED()) {
			File f = new File(Environment.getExternalStorageDirectory(), // 외장메모리 경로
					TEMP_PHOTO_FILE);
			try {
				f.createNewFile();      // 외장메모리에 temp.jpg 파일 생성
			} catch (IOException e) {
			}

			return f;
		} else
			return null;
	}

	/** SD카드가 마운트 되어 있는지 확인 */
	private boolean isSDCARDMOUNTED() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED))
			return true;

		return false;
	}

	/** 다시 액티비티로 복귀하였을때 이미지를 셋팅 */
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

					view._image = (ImageView) findViewById(R.id.img_content);
					view._image.setImageBitmap(selectedImage); 
					// temp.jpg파일을 이미지뷰에 씌운다.
				}
			}
			break;
		}
	}

}
