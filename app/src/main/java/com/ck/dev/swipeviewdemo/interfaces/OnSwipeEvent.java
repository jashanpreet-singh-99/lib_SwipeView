package com.ck.dev.swipeviewdemo.interfaces;

import android.view.View;

import com.ck.dev.swipeviewdemo.utils.SwipeType;

public interface OnSwipeEvent {

    void swipeEventDetected(View v, SwipeType swipeType);
    void swipeMovementValue(float valueX, float valueY);
    void tapEventDetected(View v, float valueX, float valueY);
    default void onDraggedValue(float valueX, float valueY){ swipeMovementValue(valueX, valueY); }

}
