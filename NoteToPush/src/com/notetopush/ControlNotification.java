package com.notetopush;	

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

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
		notiBuilder = new Notification.Builder(cnt);
		notiBuilder.setTicker(title)
		.setContentTitle(title)
		.setContentText(title+"내용")
		.setWhen(System.currentTimeMillis())    // 확인위해 현재시간으로
		.setSmallIcon(R.drawable.ic_launcher);
	}

	public void setMemoContent(String text){
		notiBuilder.setContentText(text);
	}

	public void settingNotification(Context cnt){
		Intent intent = new Intent(cnt, ControlNoteList.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent content = PendingIntent.getActivity(cnt, 0, intent, 0);
		notiBuilder.setContentIntent(content);
		noti = notiBuilder.build();
		nm.notify(1,noti);
	}
	public void setToDoContent(String[] contents){}
	public void setImgContent(Bitmap img, String content){}
	public void deletePreviousNotification(int noti_id){}

	

}
