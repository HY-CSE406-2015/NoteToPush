package com.notetopush;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ViewNoteEdit{

	private Context context;
	private int type;
	private View view;
	private ViewListener listener;

	private ViewPager mPager;
	public EditText memo_title;
	public EditText todo_title;
	public EditText img_title;
	public EditText memo_content;
	public ImageView _image;

	public ViewNoteEdit(Context context){
		this.context = context;
		//this.type = note_type;
		this.view = View.inflate(context, R.layout.activity_edit_note, null);
		initPager();
		setOnClick();
	}
	
	public View getview() {
		return view;
	}

	
	
	public void setListener(ViewListener listener){
		this.listener = listener;
	}
	
	public View findViewById(int id){
		return view.findViewById(id);
	}
	
	public void initPager(){
		mPager = (ViewPager) findViewById (R.id.pager);
		mPager.setAdapter(new PagerAdapterClass(getApplicationContext()));
	}
	
	private Context getApplicationContext() {
		return view.getContext();
	}

	private void setCurrentInflateItem(int type){
		if(type==0){
			this.type = type;
			mPager.setCurrentItem(0);
		}else if(type==1){
			this.type = type;
			mPager.setCurrentItem(1);
		}else{
			this.type = type;
			mPager.setCurrentItem(2);
		}
	}

	
	
	private void setOnClick(){
		
		View.OnClickListener click_listener = new View.OnClickListener() {
			public void onClick(View v) {
				if(listener!=null){
					int id = v.getId();
					if(id == R.id.btn_cancel){
						listener.cancelAction();
					} else if(id == R.id.btn_confirm){
						listener.confirmAction(type);
					} else if(id == R.id.btn_chose){
						listener.choseImageAction();
					} else if(id == R.id.btn_memo){
						setCurrentInflateItem(0);
					} else if(id == R.id.btn_todo){
						setCurrentInflateItem(1);
					} else if(id == R.id.btn_img){
						setCurrentInflateItem(2);
					}
				}
			}
		};
		setViewElement(click_listener);
	}
	
	private void setViewElement(View.OnClickListener click_listener){
		Button btn_memo = (Button) findViewById(R.id.btn_memo);
		Button btn_todo = (Button) findViewById(R.id.btn_todo);
		Button btn_img = (Button) findViewById(R.id.btn_img);
		Button btn_cancel = (Button)findViewById(R.id.btn_cancel);
		Button btn_confirm = (Button)findViewById(R.id.btn_confirm);

		btn_memo.setOnClickListener(click_listener);
		btn_todo.setOnClickListener(click_listener);
		btn_img.setOnClickListener(click_listener);
		btn_cancel.setOnClickListener(click_listener);
		btn_confirm.setOnClickListener(click_listener);
	}

	private View.OnClickListener mPagerListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()){
			case R.id.btn_chose:
				listener.choseImageAction();
				break;
			}
		}
	};

	/**
	 * PagerAdapter ³ëÆ® Å¸ÀÔ º¯È¯ ÀüÈ¯ Å¬·¡½º
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
			}
			else if(position==1){
				v = mInflater.inflate(R.layout.edit_todo, null);
			}else{
				v = mInflater.inflate(R.layout.edit_img, null);
				v.findViewById(R.id.btn_chose).setOnClickListener(mPagerListener);
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

	interface ViewListener{
		public void cancelAction();
		public void choseImageAction();
		public void confirmAction(int type);
	}

}