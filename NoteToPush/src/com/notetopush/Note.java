package com.notetopush;

import java.util.ArrayList;

import android.content.ContentValues;

public class Note {
	public static final int MEMO_TYPE = 0;
	public static final int TODO_TYPE = 1;
	public static final int IMG_TYPE = 2;
	
	private Note sub_note;
	
	private String note_title;
	private int note_id;
	private int note_type;
	private Long note_alarm;
	private long note_write_time;
	private Integer note_notification_id;
	
	private ArrayList<ToDoNote> todos;

	public Note(int note_id){
		this.note_id = note_id;
		this.note_title = "이미지 노트 테스트으";
		this.note_type = IMG_TYPE;
		this.note_alarm = System.currentTimeMillis();
		this.note_write_time = System.currentTimeMillis();
		this.note_notification_id = null;
		
		switch(this.note_type){
		case MEMO_TYPE:
			this.sub_note = new MemoNote(this.note_id);
			break;
		case TODO_TYPE:
			todos = new ArrayList<ToDoNote>();
			for(int todo_loop = 0; todo_loop < todoSize(); todo_loop++)
				todos.add(new ToDoNote(this.note_id,todo_loop));
			break;
		case IMG_TYPE:
			this.sub_note = new ImageNote(this.note_id);
			break;
		default:
		}
		
	}
	public Note(){
		
	}
	
	public int todoSize(){
		return 5;
	}
	public ToDoNote getToDo(int order){
		return todos.get(order);
	}
	
	public void setNote(String title, int note_id, String note_type, Long note_alarm, long note_write_time, Integer note_notification_id){
		
	}
	
	public String getTitle(){
		return note_title;
	}
	public int getType(){
		return note_type;
	}
	public Long getAlarmTime(){
		return note_alarm;
	}
	public Integer getNotification(){
		return note_notification_id;
	}
	public Note getSubNote(){
		return sub_note;
	}
	
	public void insertNote(ContentValues note){
		
	}
	public void updateNote(ContentValues note, int note_id){
		
	}
	public void deleteNote(){
		
	}
}
