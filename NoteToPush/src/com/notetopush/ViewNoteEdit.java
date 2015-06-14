package com.notetopush;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ViewNoteEdit extends Activity implements OnClickListener{

	private ViewPager mPager;
	private Button btn_memo;
	private Button btn_todo;
	private Button btn_img;
	public EditText memo_title;
	public EditText todo_title;
	public EditText img_title;
	public EditText memo_content;
	private ImageView _image;

	private static final String TEMP_PHOTO_FILE = "temp.jpg";       // 임시 저장파일
	private static final int REQ_CODE_PICK_IMAGE = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_note);

		setLayout();


		memo_title = (EditText) findViewById(R.id.memo_title);
		todo_title = (EditText) findViewById(R.id.todo_title);
		img_title = (EditText) findViewById(R.id.img_title);
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(new PagerAdapterClass(getApplicationContext()));

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_memo:
			setCurrentInflateItem(0);
			break;
		case R.id.btn_todo:
			setCurrentInflateItem(1);
			break;
		case R.id.btn_img:
			setCurrentInflateItem(2);
			break;

		}
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

					_image = (ImageView) findViewById(R.id.img_content);
					_image.setImageBitmap(selectedImage); 
					// temp.jpg파일을 이미지뷰에 씌운다.
				}
			}
			break;
		}
	}

	private void setCurrentInflateItem(int type){
		if(type==0){
			mPager.setCurrentItem(0);
		}else if(type==1){
			mPager.setCurrentItem(1);
		}else{
			mPager.setCurrentItem(2);
		}
	}

	private void setLayout(){
		btn_memo = (Button) findViewById(R.id.btn_memo);
		btn_todo = (Button) findViewById(R.id.btn_todo);
		btn_img = (Button) findViewById(R.id.btn_img);

		btn_memo.setOnClickListener(this);
		btn_todo.setOnClickListener(this);
		btn_img.setOnClickListener(this);
		//bt_chose.setOnClickListener(this);
	}

	private View.OnClickListener mPagerListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			//			String text = ((Button)v).getText().toString();
			//			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
			switch (v.getId()){
			case R.id.bt_chose:
				Intent intent = new Intent(
						Intent.ACTION_GET_CONTENT,      // 또는 ACTION_PICK
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				intent.setType("image/*");              // 모든 이미지
				intent.putExtra("crop", "true");        // Crop기능 활성화
				intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());     // 임시파일 생성
				intent.putExtra("outputFormat",         // 포맷방식
						Bitmap.CompressFormat.JPEG.toString());

				startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
				// REQ_CODE_PICK_IMAGE == requestCode
				break;
			}
		}
	};

	/**
	 * PagerAdapter 
	 */
	private class PagerAdapterClass extends PagerAdapter{

		private LayoutInflater mInflater;

		public PagerAdapterClass(Context c){
			super();
			mInflater = LayoutInflater.from(c);
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public Object instantiateItem(View pager, int position) {
			View v = null;
			if(position==0){
				v = mInflater.inflate(R.layout.edit_memo, null);
				//v.findViewById(R.id.).setOnClickListener(mPagerListener);
			}
			else if(position==1){
				v = mInflater.inflate(R.layout.edit_todo, null);
				//v.findViewById(R.id.btn_click_2).setOnClickListener(mPagerListener);
			}else{
				v = mInflater.inflate(R.layout.edit_img, null);
				//v.findViewById(R.id.btn_click_3).setOnClickListener(mPagerListener);
				v.findViewById(R.id.bt_chose).setOnClickListener(mPagerListener);
			}

			((ViewPager)pager).addView(v, 0);

			return v; 
		}

		@Override
		public void destroyItem(View pager, int position, Object view) {	
			((ViewPager)pager).removeView((View)view);
		}

		@Override
		public boolean isViewFromObject(View pager, Object obj) {
			return pager == obj; 
		}

		@Override public void restoreState(Parcelable arg0, ClassLoader arg1) {}
		@Override public Parcelable saveState() { return null; }
		@Override public void startUpdate(View arg0) {}
		@Override public void finishUpdate(View arg0) {}
	}
}
