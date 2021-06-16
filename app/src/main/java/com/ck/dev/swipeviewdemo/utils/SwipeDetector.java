package com.ck.dev.swipeviewdemo.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;

import com.ck.dev.swipeviewdemo.interfaces.OnSwipeEvent;


public class SwipeDetector implements View.OnTouchListener {

    private final View parent;
    private short MIN_DISTANCE = 50;

    private OnSwipeEvent swipeEventListener;

    private final Handler handler;
    private Runnable onLongPressedEvent;

    public SwipeDetector(View parent) {
        this.parent = parent;
        this.parent.setOnTouchListener(this);
        this.handler = null;
    } // SwipeDetector

    public SwipeDetector(View parent, Runnable onLongPressedEvent) {
        this.parent = parent;
        this.parent.setOnTouchListener(this);
        this.handler = new Handler(Looper.getMainLooper());
        this.onLongPressedEvent = onLongPressedEvent;
    } // SwipeDetector

    public void setOnSwipeListener(OnSwipeEvent listener) {
        try {
            swipeEventListener = listener;
        } catch (ClassCastException e) {
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "setOnSwipeListener : " + e.getMessage(), false);
        }
    } // setOnSwipeListener

    public void onTapEventDetected(float valueX, float valueY) {
        if (swipeEventListener != null) {
            swipeEventListener.tapEventDetected(parent, valueX, valueY);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onTapEventDetected : OnSwipeEvent is null.", false);
    } // onTapEventDetected

    public void onSwipeMovementValue(float valueX, float valueY) {
        if (swipeEventListener != null) {
            swipeEventListener.swipeMovementValue(valueX, valueY);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onSwipeMovementValue : OnSwipeEvent is null.", false);
    } // onSwipeMovementValue

    public void onRightToLeftSwipe() {
        if (swipeEventListener != null) {
            swipeEventListener.swipeEventDetected(parent, SwipeType.RIGHT_TO_LEFT);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onRightToLeftSwipe : OnSwipeEvent is null.", false);
    } // onRightToLeftSwipe

    public void onLeftToRightSwipe() {
        if (swipeEventListener != null) {
            swipeEventListener.swipeEventDetected(parent, SwipeType.LEFT_TO_RIGHT);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onLeftToRightSwipe : OnSwipeEvent is null.", false);
    } // onLeftToRightSwipe

    public void onTopToBottomSwipe() {
        if (swipeEventListener != null) {
            swipeEventListener.swipeEventDetected(parent, SwipeType.TOP_TO_BOTTOM);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onTopToBottomSwipe : OnSwipeEvent is null.", false);
    } // onTopToBottomSwipe

    public void onBottomToTopSwipe() {
        if (swipeEventListener != null) {
            swipeEventListener.swipeEventDetected(parent, SwipeType.BOTTOM_TO_TOP);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onBottomToTopSwipe : OnSwipeEvent is null.", false);
    } // onBottomToTopSwipe

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    } // onTouch

    public short getMinDistance() {
        return MIN_DISTANCE;
    } // getMinDistance

    public void setMinDistance(short MIN_DISTANCE) {
        this.MIN_DISTANCE = MIN_DISTANCE;
    } // setMinDistance

} // SwipeDetector
