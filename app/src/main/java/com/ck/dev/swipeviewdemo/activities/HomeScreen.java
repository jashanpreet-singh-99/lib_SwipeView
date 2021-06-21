package com.ck.dev.swipeviewdemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ck.dev.swipeviewdemo.R;
import com.ck.dev.swipeviewdemo.interfaces.OnSwipeEvent;
import com.ck.dev.swipeviewdemo.utils.Config;
import com.ck.dev.swipeviewdemo.utils.SwipeDetector;
import com.ck.dev.swipeviewdemo.utils.SwipeType;

public class HomeScreen extends AppCompatActivity {

    private LinearLayout swipeDetectorView;
    private LinearLayout moveView;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_screen);
        initView();
    }

    private void initView() {
        swipeDetectorView = this.findViewById(R.id.swipe_detector_view);
        moveView          = this.findViewById(R.id.move_view);

        btn1              = this.findViewById(R.id.btn_1);
        btn2              = this.findViewById(R.id.btn_2);
        btn3              = this.findViewById(R.id.btn_3);
        btn4              = this.findViewById(R.id.btn_4);

        onClick();
    }

    private void onClick() {
        new SwipeDetector(swipeDetectorView).setOnSwipeListener(new OnSwipeEvent() {
            @Override
            public void swipeEventDetected(View v, SwipeType swipeType) {
                switch(swipeType) {
                    case BOTTOM_TO_TOP:
                        Config.LOG(Config.TAG_HOME_SCREEN, "Bottom to top", false);
                        break;
                    case TOP_TO_BOTTOM:
                        Config.LOG(Config.TAG_HOME_SCREEN, "Top to bottom", false);
                        break;
                }
            }

            @Override
            public void swipeMovementValue(float valueX, float valueY) {
                if (valueX <= 10)
                    return;

                float change = swipeDetectorView.getHeight() - valueY;
                moveView.setTranslationY(change);
            }

            @Override
            public void tapEventDetected(View v, float valueX, float valueY) {

            }
        });

        Runnable btnLongPress = () -> Config.LOG(Config.TAG_HOME_SCREEN, "Long pressed.", false);

        new SwipeDetector(btn1, btnLongPress).setOnSwipeListener(new OnSwipeEvent() {
            @Override
            public void swipeEventDetected(View v, SwipeType swipeType) {

            }

            @Override
            public void swipeMovementValue(float valueX, float valueY) {

            }

            @Override
            public void tapEventDetected(View v, float valueX, float valueY) {

            }
        });

        new SwipeDetector(btn2, btnLongPress).setOnSwipeListener(new OnSwipeEvent() {
            @Override
            public void swipeEventDetected(View v, SwipeType swipeType) {

            }

            @Override
            public void swipeMovementValue(float valueX, float valueY) {

            }

            @Override
            public void tapEventDetected(View v, float valueX, float valueY) {

            }
        });

        new SwipeDetector(btn3, btnLongPress).setOnSwipeListener(new OnSwipeEvent() {
            @Override
            public void swipeEventDetected(View v, SwipeType swipeType) {

            }

            @Override
            public void swipeMovementValue(float valueX, float valueY) {

            }

            @Override
            public void tapEventDetected(View v, float valueX, float valueY) {

            }
        });

        new SwipeDetector(btn4, btnLongPress).setOnSwipeListener(new OnSwipeEvent() {
            @Override
            public void swipeEventDetected(View v, SwipeType swipeType) {

            }

            @Override
            public void swipeMovementValue(float valueX, float valueY) {

            }

            @Override
            public void tapEventDetected(View v, float valueX, float valueY) {

            }
        });
    }
}