package com.notetopush;	

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.Notification.InboxStyle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

public class ControlNotification {

	public interface control_noti{
		public void setTitle(String title, long time,Context cnt);
		public void setMemoContent(String text);
		public void setToDoContent(String[] contents);
		public void setImgContent(Bitmap img, String content);
		public void settingNotification();
		public void deletePreviousNotification(int noti_id);
	}

	private Builder notiBuilder;
	private Notification noti;
	private NotificationManager nm;

	public void setTitle(String title, long time,Context cnt){
        nm = (NotificationManager)cnt.getSystemService(Context.NOTIFICATION_SERVICE);
        
        Intent intent = new Intent(cnt, ControlNoteContent.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent content = PendingIntent.getActivity(cnt, 0, intent, 0);
    
		notiBuilder = new Notification.Builder(cnt);
		notiBuilder.setTicker(title)
		.setContentTitle(title)
		.setWhen(time) 
		.setSmallIcon(R.drawable.ic_launcher);
		notiBuilder.setContentIntent(content);
		
		
	}

	public void setMemoContent(String text){
		notiBuilder.setContentText(text);
		noti = notiBuilder.build();
		
	}

	public void settingNotification(int noti_id){
		noti.flags = Notification.FLAG_NO_CLEAR;
		nm.notify(noti_id,noti);
	}
	
	public void setToDoContent(String[] contents){
//		InboxStyle ibs = new InboxStyle(notiBuilder);

		Notification.InboxStyle ibs = new Notification.InboxStyle(notiBuilder);
//		Log.i("noti","!!!");
//		Log.i("noti",contents[0]);
//		Log.i("noti",contents[1]);
//		Log.i("noti",contents[2]);
		Log.i("noti",""+contents.length);
		for(int i = 0; i<contents.length; i++){
			ibs.addLine(contents[i]);
			Log.i("noti",contents[i]);
		}
		noti = ibs.build();
	}

	public void setImgContent(Bitmap img, String content){
		notiBuilder.setContentText(content);
		noti = new Notification.BigPictureStyle(notiBuilder).bigPicture(img)
				.setBigContentTitle("큰그림타이틀")
				.setSummaryText("큰그림내용")
				.build();
	}
	//Delete noti
	public void deletePreviousNotification(int noti_id,Context cnt){
		nm = (NotificationManager)cnt.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(noti_id);
	}
	
	

	
}