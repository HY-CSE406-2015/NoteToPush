package com.example.notetopush;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;


public class ImageNote extends Note {

	private String note_content;
	private byte[] note_img;

	public ImageNote(Context context, int note_id){
		super();
		selectNote(note_id);
	}

	public ImageNote(Context context) {
		super(context);
	}

	public void setContent(String content) {
		
	}
	
	public void setImage(Bitmap image) {
		
	}
	
	public void insertImageNote(ContentValues note) {
		ContentValues img_content = new ContentValues();
		img_content.put("note_id", note.getAsInteger("note_id"));
		img_content.put("img", note.getAsByte("img"));
		img_content.put("content", note.getAsString("content"));
		Note.mDB.insert("IMG_NOTE", null, img_content);
	}

	public void selectNote(int note_id) {
		Cursor c = mDB.rawQuery("SELECT * FROM IMG_NOTE WHERE NOTE_ID = " + note_id, null);

		this.note_img = c.getBlob(c.getColumnIndex("img"));
		if(c.isNull(c.getColumnIndex("content"))) this.note_content = null;
		else this.note_content = c.getString(c.getColumnIndex("content"));
	}
	
	public void updateImageNote(ContentValues note, int note_id) {
		ContentValues img_content = new ContentValues();
		img_content.put("note_id", note.getAsInteger("note_id"));
		img_content.put("img", note.getAsByte("img"));
		img_content.put("content", note.getAsString("content"));
		Note.mDB.update("IMG_NOTE", img_content, "note_id = " + note_id, null);
	}

	private Bitmap byteToBitmap(byte[] byte_image){
		Bitmap bitmap_image = BitmapFactory.decodeByteArray(byte_image, 0, byte_image.length) ;
		return bitmap_image;
	}
	private byte[] bitmapToByte(Bitmap bitmap_image){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap_image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] byte_image = stream.toByteArray();
		return byte_image;
	}
}
