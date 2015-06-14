

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class Note {

	final static int MEMO_TYPE = 0;
	final static int TODO_TYPE = 1;
	final static int IMG_TYPE = 2;

	// DB占쏙옙占쏙옙 占쏙옙占� 占쏙옙占쏙옙
	private static final String dbName = "notetopush.db";
	private static final String note = "NOTE";
	private static final String memo_note =	 "MEMO_NOTE";
	private static final String todo_note = "TODO_NOTE";
	private static final String img_note = "IMG_NOTE";
	public static final int dbVersion = 1;

	public static SQLiteDatabase mDB;
	private MyOpenHelper helper;

	private int note_id = 1;
	private String note_title = "test";
	private int note_type = 3;
	private Long note_alarm = Long.valueOf(4);
	private long note_write_time = 5;
	private Integer note_notification_id = 6;

	private Context context;

	public Note(Context context) {
		this.context = context;
		this.helper = new MyOpenHelper(context, dbName, null, dbVersion);
		mDB = helper.getWritableDatabase();
	}

	private class MyOpenHelper extends SQLiteOpenHelper {
		public MyOpenHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String createNote = "create table " + note + " ("
					+ "note_id integer primary key not null, " + "type integer not null, "
					+ "title text not null, " + "alarm numeric, " + "write_time numeric not null, "
					+ "noti_id integer not null"
					+ ");";
			db.execSQL(createNote);

			String createMemo = "create table " + memo_note + " ("
					+ "note_id integer primary key not null, " + "content text, "
					+ "foreign key(note_id) references note(note_id)"
					+ ")";
			db.execSQL(createMemo);

			String createToDo = "create table " + todo_note + " ("
					+ "note_id integer primary key not null, " + "todo_order integer not null, "
					+ "content text not null, " + "is_check integer not null, "
					+ "foreign key(note_id) references note(note_id)"
					+ ");";
			db.execSQL(createToDo);


			String createIMG = "create table " + img_note + " ("
					+ "note_id integer primary key not null, " + "img blob not null, "
					+ "content text, " + "foreign key(note_id) references  note(note_id)"
					+ ");";
			db.execSQL(createIMG);

			Toast.makeText(context, "DB is opened", Toast.LENGTH_LONG).show();
//			arg0.close();
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
		}
	}

	public void setNote(String title, int note_id, String note_type,
						Long note_alarm, long note_write_time, Integer note_notification_id) {

	}

	public int getId() {
		return note_id;
	}

	public String getTitle() {
		return note_title;
	}

	public int getType() {
		return note_type;
	}

	public Long getAlarmTime() {
		return note_alarm;
	}

	public long getWriteTime() {
		return note_write_time;
	}

	public Integer getNotification() {
		return note_notification_id;
	}

	public void insertNote(ContentValues note) { //push confirmEditButton, This operator calls insertNote of note when note is not null
		Cursor c = mDB.query("NOTE", null, null, null, null, null, null);
		note_id = c.getCount() + 1;

		note.put("note_id", this.getId()); //note_id �젙蹂대�� MemoNote, ToDoNote, ImageNote�뿉 �꽆寃⑥＜湲� �쐞�빐 put �빐以��떎.

		ContentValues tmp_note = new ContentValues();
		tmp_note.put("note_id", this.note_id);
		tmp_note.put("type", note.getAsInteger("type"));
		tmp_note.put("title", note.getAsString("title"));
		tmp_note.put("alarm", note.getAsLong("alarm"));
		tmp_note.put("write_time", note.getAsLong("write_time"));
		tmp_note.put("noti_id", note.getAsInteger("noti_id"));
		mDB.insert(this.note, null, tmp_note);

		if(note.getAsInteger("type") == MEMO_TYPE) {
			MemoNote memo_note = new MemoNote(this.context);
			memo_note.insertMemoNote(note);
		}

		else if(note.getAsInteger("type") == TODO_TYPE) {
			ToDoNote todo_note = new ToDoNote(this.context);
			todo_note.insertToDoList(note);
		}

		else if(note.getAsInteger("type") == IMG_TYPE) {
			ImageNote image_note = new ImageNote(this.context);
			image_note.insertImageNote(note);
		}
		note_id++;
	}

	public void updateNote(ContentValues note, int note_id) {	//when note is null calls updateNote of note. ??
		ContentValues update_note = new ContentValues();
		update_note.put("note_id", this.note_id);
		update_note.put("type", note.getAsInteger("type"));
		update_note.put("title", note.getAsString("title"));
		update_note.put("alarm", note.getAsLong("alarm"));
		update_note.put("write_time", note.getAsLong("write_time"));
		update_note.put("noti_id", note.getAsInteger("noti_id"));
		mDB.update(this.note, update_note, "note_id = " + note_id, null);

		if(note.getAsInteger("type") == MEMO_TYPE) {
			MemoNote memo_note = new MemoNote(this.context);
			memo_note.updateMemoNote(note);
		}

		else if(note.getAsInteger("type") == TODO_TYPE) {
			ToDoNote todo_note = new ToDoNote(this.context);
			todo_note.updateToDoList(note);
		}

		else if(note.getAsInteger("type") == IMG_TYPE) {
			ImageNote image_note = new ImageNote(this.context);
			image_note.updateImageNote(note);
		}
	}

	public void selectNote() {
		mDB = helper.getReadableDatabase(); // db占쏙옙체占쏙옙 占쏙옙占승댐옙. 占싻깍옙 占쏙옙占쏙옙
		Cursor c = mDB.query("note", null, null, null, null, null, null);


        /*
         * 占쏙옙 占쏙옙占쏙옙占� select * from student 占쏙옙 占싫댐옙. Cursor占쏙옙 DB占쏙옙占쏙옙占� 占쏙옙占쏙옙占싼댐옙. public Cursor
         * query (String table, String[] columns, String selection, String[]
         * selectionArgs, String groupBy, String having, String orderBy)
         */

		while (c.moveToNext()) {

			int _id = c.getInt(c.getColumnIndex("_id"));
			String name = c.getString(c.getColumnIndex("name"));
			int age = c.getInt(c.getColumnIndex("age"));
			String address = c.getString(c.getColumnIndex("address"));
			Log.i("db", "id: " + _id + ", name : " + name + ", age : " + age
					+ ", address : " + address);
		}
	}

	public void deleteNote() {

	}

	public void dropAll() {
		String dropNote = "drop table if exists " + note;
		String dropMemo = "drop table if exists " + memo_note;
		String dropTodo = "drop table if exists " + todo_note;
		String dropIMG = "drop table if exists " + img_note;

		mDB.execSQL(dropNote);
		mDB.execSQL(dropMemo);
		mDB.execSQL(dropTodo);
		mDB.execSQL(dropIMG);
	}
}
