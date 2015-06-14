package com.notetopush;

public class ToDoNote extends Note {
	private int note_id;
	private int todo_order;
	private String content;
	private boolean is_checked;
	
	public ToDoNote(){
		
	}
	public ToDoNote(int note_id, int order){
		this.note_id = note_id;
		this.todo_order = order;
		this.content = "ToDo 요소입니다.\n"+order+"번째 todo";
		this.is_checked = false;
		if(order == 2) this.is_checked = true;
	}
	
	public void setContent(String content){
		
	}
	public void setChecked(boolean able){ is_checked = able; }
	public int getOrder(){ return this.todo_order; }
	public String getContent(){ return this.content; }
	public boolean isChecked(){ return this.is_checked; }
	
	public void insertMemoNote(){
		
	}
	public void updateMemoNote(){
		
	}
}
