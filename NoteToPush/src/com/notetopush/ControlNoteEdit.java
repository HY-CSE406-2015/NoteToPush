package com.notetopush;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ControlNoteEdit extends Activity implements OnClickListener{
	
	private ViewPager mPager;
	private Button btn_memo;
	private Button btn_todo;
	private Button btn_img;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_note);
		
		setLayout();
		
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
	}
	
	private View.OnClickListener mPagerListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			String text = ((Button)v).getText().toString();
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
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
