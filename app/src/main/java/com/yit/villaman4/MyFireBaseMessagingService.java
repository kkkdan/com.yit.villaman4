package com.yit.villaman4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class MyFireBaseMessagingService extends FirebaseMessagingService {
    Bitmap bigPicture;

    private static final String __TAG__ = "Firebase♥♥:";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        if (remoteMessage.getData() == null) {

            return;
        }

        //포그라운드 일때 직접 메시지 처리

        // String imgUrl = remoteMessage.getData().get("imgUrl");

//        String imgUrl = "http://yes-it.kr/images/home/home.png";

//        //이미지 온라인 링크를 가져와 비트맵으로 바꾼다.
//
//        try {
//            URL url = new URL(imgUrl);
//            bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Map<String, String> pushDataMap = remoteMessage.getData();
        sendNotification(pushDataMap);

        // sendNotification222222(remoteMessage.getData().get("title"), remoteMessage.getData().get("content"), bigPicture);

    }


    private void sendNotification(Map<String, String> dataMap) {


        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final String CHANNEL_ID = "채널ID";

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(dataMap.get("title"))
                .setContentText(dataMap.get("msg"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{1000, 1000})
                .setLights(Color.WHITE,1500,1500)
                .setContentIntent(contentIntent);

        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(0 /* ID of notification */, nBuilder.build());


    }










    private void sendNotification222222(String title, String content, Bitmap bigPicture) {
        if (title == null) {
            title = "기본 제목";
        }
        Log.d("#####", title);
        //Log.d("#####", content);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        final String CHANNEL_ID = "채널ID";
        NotificationManager mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);

        builder.setLargeIcon (bigPicture);

        builder.setContentText(content);
        builder.setContentTitle(title);
        builder.setSound(defaultSoundUri);
        builder.setVibrate(new long[]{500, 500});

        mManager.notify(0, builder.build());
    }

}
