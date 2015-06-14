package com.notetopush;

public class MemoNote extends Note {
	private int note_id;
	private String note_content;
	
	public MemoNote(){
		
	}
	public MemoNote(int note_id){
		this.note_id = note_id;
		this.note_content = "일단 예비로 집어넣\n은 텍스트입니다\n여기에는 DB에서 읽어온 값을 넣\n어주세요.";
	}
	
	public void setContent(String content){
		
	}
	public String getContent(){
		return note_content;
	}
	public void insertMemoNote(){
		
	}
	public void updateMemoNote(){
		
	}

}
