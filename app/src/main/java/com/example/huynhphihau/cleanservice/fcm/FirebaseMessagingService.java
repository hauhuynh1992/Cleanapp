package com.example.huynhphihau.cleanservice.fcm;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.BaseApplication;
import com.example.huynhphihau.cleanservice.data.response.FCMBody;
import com.example.huynhphihau.cleanservice.util.AppLog;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by phihau on 6/7/2017.
 */


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "HAU";
    public final static String PUSH_ANNOUNCE = "com.example.huynhphihau.cleanservice.PUSH_FCM";
    public static final Intent mPushBroadcastAnnounce = new Intent(PUSH_ANNOUNCE);

    /**
     * using for push announce
     */
    private String report_id;
    private String type;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        AppLog.e(TAG, "VAFirebaseService From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage != null) {
            if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0) {
                AppLog.e(TAG, "VAFirebaseService Message data payload: " + remoteMessage.getData());
                String body = remoteMessage.getData() + "";
                // parse job object
                final Gson gson = new Gson();
                FCMBody fcmBody = null;
                try {
                    fcmBody = gson.fromJson(body, FCMBody.class);
                    AppLog.e("AAA", fcmBody.toString());
                    if (fcmBody != null) {
                        report_id = fcmBody.getReportID();
                        type = fcmBody.getType();

                        BaseApplication.getInstance().setType(type);

                        /* SEND ID, AND TYPE */
                        mPushBroadcastAnnounce.putExtra("REPORT_ID", report_id);
                        mPushBroadcastAnnounce.putExtra("TYPE", type);
                        LocalBroadcastManager.getInstance(this).sendBroadcast(mPushBroadcastAnnounce);
                    }

                } catch (JsonSyntaxException exception) {
                    AppLog.e(TAG, "Message parse: " + exception.getMessage());
                }
            }
        }

        // DISPLAY NOTIFICATIONS
        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getBody(), getString(R.string.app_full_name));
        }
    }


    /**
     * Create and show a simple notification containing the received FCM message.
     */
    private void sendNotification(String message, String title) {
        Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notifiy_sound);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);
        int num = (int) System.currentTimeMillis();

        if (notificationBuilder != null) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(num, notificationBuilder.build());
        }
    }
}