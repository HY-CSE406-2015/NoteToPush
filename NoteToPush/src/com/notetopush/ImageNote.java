package com.notetopush;

import java.io.ByteArrayOutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;


public class ImageNote extends Note {

	private String note_content;
	private byte[] note_img;
	
	public ImageNote(Context context) {
		super(context);
	}
	public ImageNote(Context context, int note_id){
		super(context,note_id);
		selectNote(note_id);
	}
	public void setNote(String title,Long alarm, long write_time, Bitmap image, String sub_content) {
		super.setNote(title, Note.IMG_TYPE, alarm, write_time);
		this.note_img = getBytes(image);
		this.note_content = sub_content;
	}
	
	public String getSubContent() {
		return this.note_content;		
	}
	public Bitmap getImage(){
		return getBitmapImage(this.note_img);
	}
	
	public void selectNote(int note_id){
		super.selectNote(note_id);
		Cursor c = mDB.query("IMG_NOTE", null, "note_id = " +note_id, null, null, null, null);
		
		c.moveToNext();
		if (c != null && c.getCount() != 0){
			this.note_img = c.getBlob(c.getColumnIndex("img"));
			if(!c.isNull(c.getColumnIndex("content")))
				this.note_content = c.getString(c.getColumnIndex("content"));
		}
		c.close();
		mDB.close();
	}
	public void insertNote() {
		super.insertNote();
		ContentValues img_content = new ContentValues();
		img_content.put("note_id", this.note_id);
		img_content.put("img", this.note_img);
		img_content.put("content", this.note_content);
		mDB.insert("IMG_NOTE", null, img_content);
		mDB.close();
	}
	
	public void updateNote(){
		super.updateNote();
		ContentValues img_content = new ContentValues();
		img_content.put("note_id", this.note_id);
		img_content.put("img", this.note_img);
		img_content.put("content", this.note_content);
		mDB.update("IMG_NOTE", img_content, "note_id=?", new String[]{""+note_id});
		mDB.close();
	}
	public void deleteNote(){
		super.deleteNote();
		mDB.delete("IMG_NOTE", "note_id=?", new String[]{""+note_id});
		mDB.close();
	}

    private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    private Bitmap getBitmapImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}