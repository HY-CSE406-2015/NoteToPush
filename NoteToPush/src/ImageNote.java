
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;


public class ImageNote extends Note {

	private String note_content;
	private byte note_img;
	
	public ImageNote(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
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
	
	public void updateImageNote(ContentValues note) {
		ContentValues img_content = new ContentValues();
		img_content.put("note_id", note.getAsInteger("note_id"));
		img_content.put("img", note.getAsByte("img"));
		img_content.put("content", note.getAsString("content"));
		Note.mDB.update("IMG_NOTE", img_content, "note_id = " + note.getAsInteger("note_id"), null);
	}


}
