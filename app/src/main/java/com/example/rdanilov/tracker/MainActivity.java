package com.example.rdanilov.tracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rdanilov.tracker.controller.RestController;

import static com.example.rdanilov.tracker.util.Utils.getTestConnectionCallback;

public class MainActivity extends AppCompatActivity {

    RestController controller = new RestController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller.makeTestConnection(getTestConnectionCallback(this));
    }

}
