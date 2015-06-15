package com.notetopush;

import java.io.ByteArrayOutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;


public class ImageNote extends Note {

	private String note_content;
	private byte[] note_img;
	
	public ImageNote(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setContent(String content) {
		note_content = content;
	}
	
	public void setImage(Bitmap image) {
		note_img = getBytes(image);
	}
	public void setImageNote(){
		
	}
	
	public String getContent(){
		return note_content;
	}
	public Bitmap getImage(){
		return getBitmapImage(note_img);
	}
	
	public void insertImageNote(ContentValues note) {
		ContentValues img_content = new ContentValues();
		img_content.put("note_id", note.getAsInteger("note_id"));
		img_content.put("img", note.getAsByteArray("img"));
		img_content.put("content", note.getAsString("content"));
		Note.mDB.insert("IMG_NOTE", null, img_content);
	}
	
	public void updateImageNote(ContentValues note) {
		ContentValues img_content = new ContentValues();
		img_content.put("note_id", note.getAsInteger("note_id"));
		img_content.put("img", note.getAsByteArray("img"));
		img_content.put("content", note.getAsString("content"));
		Note.mDB.update("IMG_NOTE", img_content, "note_id = " + note.getAsInteger("note_id"), null);
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
