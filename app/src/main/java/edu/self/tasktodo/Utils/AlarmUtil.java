package edu.self.tasktodo.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by Hoang Anh on 04-Jul-18.
 */

public class AlarmUtil {
    private static final int REQUEST_ALARM_CODE = 101;
    public static final String ID_INTENT = "ID";
    public static final String MESSAGE_INTENT = "Message";

    public static void setAlarm(Context context, String id, String title, long timeTrigger) {
//        <--create background service-->
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, IntentNotificationService.class);
        intent.putExtra(ID_INTENT, Long.valueOf(id));
        intent.putExtra(MESSAGE_INTENT, title);
//      invoke when alarm goes off
        PendingIntent pendingIntent =
                PendingIntent.getService(context, REQUEST_ALARM_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager
                    .setExact(AlarmManager.RTC_WAKEUP, timeTrigger, pendingIntent);
        } else {
            alarmManager
                    .set(AlarmManager.RTC_WAKEUP, timeTrigger, pendingIntent);
        }
    }
}
