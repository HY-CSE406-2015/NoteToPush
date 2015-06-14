import com.example.notetopush.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity{
//	Note note = new Note(this);

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClick(View v){
		Button btn1 = (Button)findViewById(R.id.btn_insert);
		Button btn2 = (Button)findViewById(R.id.btn_check);

		if (v == btn1) {
			Toast.makeText(this, "btn1", Toast.LENGTH_SHORT).show();			
			//			note.insertNote(note);
		}

		else if (v == btn2) {
			Toast.makeText(this, "btn2", Toast.LENGTH_SHORT).show();
		}
	}
}
