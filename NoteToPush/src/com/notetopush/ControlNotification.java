package com.notetopush;	

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

public class ControlNotification {

	private static Notification.Builder init(Context context, String title, long time){
		Notification.Builder notiBuilder = new Notification.Builder(context);
		notiBuilder.setTicker(title)
		.setContentTitle(title)
		//.setWhen(time)
		.setWhen(System.currentTimeMillis())
		.setSmallIcon(R.drawable.logo);
		
		return notiBuilder;
	}

	public static void setMemoContent(Context context, int id, String title, long time, String text){
		NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification.Builder notiBuilder = init(context, title, time);
		String noti_summary = context.getResources().getString(R.string.notification_summary);
		notiBuilder.setContentText(noti_summary);
		
		Notification.BigTextStyle style = new Notification.BigTextStyle(notiBuilder);
		style.setBigContentTitle(title);
		style.bigText(text);
		notiBuilder.setStyle(style);
		
		Intent intent = new Intent(context, ControlNoteContent.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(ControlNoteContent.CONTROL_NOTE_ID, id);
		intent.putExtra(ControlNoteContent.CONTROL_NOTE_TYPE, Note.MEMO_TYPE);
		PendingIntent pending = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notiBuilder.setContentIntent(pending);

		intent.putExtra(ControlNoteContent.DELETE_NOTIFICATION, 1);
		pending = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		String delete = context.getResources().getString(R.string.delete_notification);
		notiBuilder.addAction(android.R.drawable.btn_dialog, delete, pending);
		
		Notification noti = notiBuilder.build();
		noti.flags = Notification.FLAG_NO_CLEAR;
		manager.notify(id, noti);
	}
	
	public static void setToDoContent(Context context, int id, String title, long time, ArrayList<String> contents){
		NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification.Builder notiBuilder = init(context, title, time);
		String noti_summary = context.getResources().getString(R.string.notification_summary);
		notiBuilder.setContentText(noti_summary);
		
		Notification.InboxStyle style = new Notification.InboxStyle(notiBuilder);
		style.setBigContentTitle(title);
		int size = contents.size();
		Log.i("noti",""+size);
		for(int i = 0; i<((size>3)?3:size); i++){
			style.addLine(contents.get(i));
			Log.i("noti",contents.get(i));
		}
		if(size>3){
			String sufix = context.getResources().getString(R.string.todo_summary);
			style.setSummaryText("+"+(size-3)+sufix);
		}
		notiBuilder.setStyle(style);
		
		Intent intent = new Intent(context, ControlNoteContent.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(ControlNoteContent.CONTROL_NOTE_ID, id);
		intent.putExtra(ControlNoteContent.CONTROL_NOTE_TYPE, Note.TODO_TYPE);
		PendingIntent pending = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notiBuilder.setContentIntent(pending);
		
		intent.putExtra(ControlNoteContent.DELETE_NOTIFICATION, 1);
		pending = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		String delete = context.getResources().getString(R.string.delete_notification);
		notiBuilder.addAction(android.R.drawable.btn_dialog, delete, pending);
		
		Notification noti = notiBuilder.build();
		noti.flags = Notification.FLAG_NO_CLEAR;
		manager.notify(id, noti);
	}

	public static void setImgContent(Context context, int id, String title, long time, Bitmap img, String content){
		NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification.Builder notiBuilder = init(context, title, time);
		String noti_summary = context.getResources().getString(R.string.notification_summary);
		notiBuilder.setContentText(noti_summary);
		
		Notification.BigPictureStyle style = new Notification.BigPictureStyle(notiBuilder);
		style.setBigContentTitle(title);
		style.setSummaryText(content);
		style.bigPicture(img);
		notiBuilder.setStyle(style);
		
		Intent intent = new Intent(context, ControlNoteContent.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(ControlNoteContent.CONTROL_NOTE_ID, id);
		intent.putExtra(ControlNoteContent.CONTROL_NOTE_TYPE, Note.IMG_TYPE);
		PendingIntent pending = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notiBuilder.setContentIntent(pending);
		
		intent.putExtra(ControlNoteContent.DELETE_NOTIFICATION, 1);
		pending = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		String delete = context.getResources().getString(R.string.delete_notification);
		notiBuilder.addAction(android.R.drawable.btn_dialog, delete, pending);
		
		Notification noti = notiBuilder.build();
		noti.flags = Notification.FLAG_NO_CLEAR;
		manager.notify(id, noti);
	}
	
	//Delete notification
	public static void deletePreviousNotification(int noti_id,Context cnt){
		NotificationManager nm = (NotificationManager)cnt.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(noti_id);
	}
}