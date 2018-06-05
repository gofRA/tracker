package com.example.rdanilov.tracker.util;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.BatteryManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.rdanilov.tracker.model.Entry;
import com.example.rdanilov.tracker.model.StringWrapper;
import com.example.rdanilov.tracker.service.AlarmService;
import com.example.rdanilov.tracker.service.LocationService;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Build.SERIAL;
import static android.widget.Toast.makeText;

public class Utils {

    public static Callback<StringWrapper> getTestConnectionCallback(final AppCompatActivity context) {
        return new Callback<StringWrapper>() {
            @Override
            public void onResponse(@NonNull Call<StringWrapper> call, @NonNull Response<StringWrapper> response) {
                StringWrapper result = response.body();
                if (result != null) {
                    makeText(context, result.getValue(), Toast.LENGTH_LONG).show();
                    AlarmService.startAlarmService(context);
                    if (!isServiceRunning(context, LocationService.class)) {
                        context.startService(new Intent(context, LocationService.class));
                    }
                    context.finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<StringWrapper> call, @NonNull Throwable t) {
                makeText(context, "Что-то пошло не так", Toast.LENGTH_LONG).show();
                t.printStackTrace();
                context.finish();
            }
        };
    }

    private static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }


    public static Entry buildEntry(Context context, Location location) {
        Entry result = new Entry();
        String serial;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                throw new SecurityException("No permission for SERIAL");
            }
            serial = Build.getSerial();
        } else {
            serial = SERIAL;
        }

        if (location != null) {
            result.setLatitude(location.getLatitude());
            result.setLongitude(location.getLongitude());
            result.setEntryTime(new Date(location.getTime()));
        }

        result.setBattery(getBatteryPercentage(context));
        result.setSerial(serial);
        return result;
    }

    private static int getBatteryPercentage(Context context) {

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;

        return (int) (batteryPct * 100);
    }
}
