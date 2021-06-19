package com.ck.dev.swipeviewdemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.ck.dev.swipeviewdemo.R;

public class HomeScreen extends AppCompatActivity {

    private LinearLayout swipeDetectorView;
    private LinearLayout moveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_screen);
        initView();
    }

    private void initView() {
        swipeDetectorView = this.findViewById(R.id.swipe_detector_view);
        moveView          = this.findViewById(R.id.move_view);

        onClick();
    }

    private void onClick() {

    }
}