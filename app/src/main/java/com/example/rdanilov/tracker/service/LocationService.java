package com.example.rdanilov.tracker.service;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.example.rdanilov.tracker.controller.RestController;
import com.example.rdanilov.tracker.util.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static com.example.rdanilov.tracker.util.Utils.buildEntry;
import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

public class LocationService extends IntentService {

    FusedLocationProviderClient locationProvider;
    LocationCallback locationCallback;
    Location location;

    RestController controller = new RestController();

    public LocationService() {
        super("LocationService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        locationProvider = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null) {
                        location = locationResult.getLastLocation();
                    }
                }
            };
            LocationRequest request = new LocationRequest()
                    .setPriority(PRIORITY_HIGH_ACCURACY)
                    .setInterval(Constants.LOCATION_TIME);
            locationProvider.requestLocationUpdates(request, locationCallback, null);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("DESTROYED!");
        locationProvider.removeLocationUpdates(locationCallback);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (location == null
                && ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            locationProvider.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location loc) {
                    location = loc;
                    controller.sendEntry(buildEntry(LocationService.this, location));
                }
            });
        } else {
            controller.sendEntry(buildEntry(LocationService.this, location));
        }
    }
}