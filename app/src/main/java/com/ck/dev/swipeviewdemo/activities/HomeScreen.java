package com.ck.dev.swipeviewdemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ck.dev.swipeviewdemo.R;
import com.ck.dev.swipeviewdemo.interfaces.OnSwipeEvent;
import com.ck.dev.swipeviewdemo.utils.SwipeDetector;
import com.ck.dev.swipeviewdemo.utils.SwipeType;

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
        new SwipeDetector(swipeDetectorView).setOnSwipeListener(new OnSwipeEvent() {
            @Override
            public void swipeEventDetected(View v, SwipeType swipeType) {
                switch(swipeType) {
                    case BOTTOM_TO_TOP:
                        break;
                    case TOP_TO_BOTTOM:
                        break;
                }
            }

            @Override
            public void swipeMovementValue(float valueX, float valueY) {
                if (valueX <= 0)
                    return;

                float change = swipeDetectorView.getHeight() - valueY;
                moveView.setTranslationY(change);
            }

            @Override
            public void tapEventDetected(View v, float valueX, float valueY) {

            }
        });
    }
}