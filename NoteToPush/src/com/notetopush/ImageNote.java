package com.notetopush;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class ImageNote extends Note {
	private byte[] img;
	private String content;
	
	public ImageNote(){
		
	}
	public ImageNote(int note_id){
		this.content = "일단 예비로 집어넣\n은 텍스트입니다\n여기에는 DB에서 읽어온 값을 넣\n어주세요.\n\n그리고 스크롤\n테스트합니다.";
	}
	
	public void setContent(String content){
		
	}
	public void setImage(Bitmap image){
		this.img = bitmapToByte(image);
	}
	public Bitmap getImage(){
		return byteToBitmap(this.img);
	}
	public String getContent(){
		return content;
	}
	public void insertMemoNote(){
		
	}
	public void updateMemoNote(){
		
	}
	private Bitmap byteToBitmap(byte[] byte_image){
		Bitmap bitmap_image = BitmapFactory.decodeByteArray( byte_image, 0, byte_image.length ) ;  
	    return bitmap_image;
	}
	private byte[] bitmapToByte(Bitmap bitmap_image){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();  
        bitmap_image.compress(CompressFormat.JPEG, 100, stream);  
        byte[] byte_image = stream.toByteArray();  
        return byte_image; 
	}
}
