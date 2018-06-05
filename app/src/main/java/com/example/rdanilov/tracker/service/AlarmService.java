package com.example.rdanilov.tracker.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.example.rdanilov.tracker.util.Constants;

public class AlarmService {

    public static void startAlarmService(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(context, LocationService.class);
        PendingIntent pending = PendingIntent.getService(context, 0, alarmIntent, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +
                    10 * 1000, Constants.TRACK_TIME, pending);
        }
    }
}
