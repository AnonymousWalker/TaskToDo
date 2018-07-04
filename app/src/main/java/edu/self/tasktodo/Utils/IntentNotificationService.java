package edu.self.tasktodo.Utils;

import android.app.Application;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import edu.self.tasktodo.Main.MainActivity;
import edu.self.tasktodo.R;
import edu.self.tasktodo.Reminder.ReminderActivity;

/**
 * Created by Hoang Anh on 04-Jul-18.
 */

public class IntentNotificationService extends IntentService {
    private static final int TIME_VIBRATE = 1000;
    public static final int REQUEST_ID = 101;
    public IntentNotificationService() {
        super(IntentNotificationService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String taskId = intent.getStringExtra(AlarmUtil.ID_INTENT);
        int requestId = Long.valueOf(taskId).intValue();
        String message = intent.getStringExtra(AlarmUtil.MESSAGE_INTENT);

        Intent notificationIntent = new Intent(this, ReminderActivity.class);
        notificationIntent.putExtra(AlarmUtil.ID_INTENT, taskId);
        notificationIntent.putExtra(AlarmUtil.MESSAGE_INTENT, message);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent
                .getActivity(this, REQUEST_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "NotificationChannel")
                        .setSmallIcon(R.drawable.ic_alarm_64)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText("Todo: " + message)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        //.setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE})
                        .setContentIntent(contentIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(REQUEST_ID, builder.build());
        //startActivity(notificationIntent);
    }
}
