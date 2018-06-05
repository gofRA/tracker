package com.example.rdanilov.tracker.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.rdanilov.tracker.util.Constants;

import java.util.Date;

public class EntryIntentService extends IntentService {

    public EntryIntentService() {
        super("entryService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        System.out.println(new Date() + " - I'm running");
        Intent i = new Intent();
        i.setAction(Constants.LOCATION);
        sendBroadcast(i);
    }
}
