package com.notetopush;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

public class ControlNoteEdit extends Activity implements ViewNoteEdit.ViewListener{

	private ViewNoteEdit view;
	private Note note;
	
	//이미지 호출시 사용할 변
	private static final String TEMP_PHOTO_FILE = "temp.jpg";       // 임시 저장파일
	private static final int REQ_CODE_PICK_IMAGE = 0;


	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.view = new ViewNoteEdit(this);
		this.view.setListener(this);
		setView();
		setContentView(this.view.getview());
	}

	private void setView(){
		
	}
	
	public void getNoteContent(){
		
	}

	public void cancelAction(){
		
	}
	
	public void confirmAction(){
		
	}

	public void selectImgAction() {
		
		
	}

	@Override
	public void choseImage() {
		Intent intent = new Intent(
				Intent.ACTION_GET_CONTENT,      // 또는 ACTION_PICK
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/*");              // 모든 이미지
		intent.putExtra("crop", "true");        // Crop기능 활성화
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());     // 임시파일 생성
		intent.putExtra("outputFormat",         // 포맷방식
				Bitmap.CompressFormat.JPEG.toString());

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
