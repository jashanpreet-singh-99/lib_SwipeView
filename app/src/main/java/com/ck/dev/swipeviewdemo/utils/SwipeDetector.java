package com.ck.dev.swipeviewdemo.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.core.view.ViewConfigurationCompat;

import com.ck.dev.swipeviewdemo.interfaces.OnSwipeEvent;

public class SwipeDetector implements View.OnTouchListener {

    private final View parent;
    private short MIN_DISTANCE = 50;
    private short LONG_PRESS_SENSITIVITY = 100;

    private float downX;
    private float downY;

    private boolean draggable;

    private boolean DRAG_MODE;

    private OnSwipeEvent swipeEventListener;

    private final Handler handler;
    private Runnable onLongPressedEvent;

    public SwipeDetector(View parent) {
        this.parent = parent;
        this.parent.setOnTouchListener(this);
        this.handler = null;
    } // SwipeDetector

    public SwipeDetector(View parent, boolean draggable) {
        this.parent = parent;
        this.parent.setOnTouchListener(this);
        this.handler = new Handler(Looper.getMainLooper());
        this.draggable = draggable;
    } // SwipeDetector

    public SwipeDetector(View parent, Runnable onLongPressedEvent) {
        this.parent = parent;
        this.parent.setOnTouchListener(this);
        this.handler = new Handler(Looper.getMainLooper());
        this.onLongPressedEvent = onLongPressedEvent;
    } // SwipeDetector

    public SwipeDetector(View parent, Runnable onLongPressedEvent, boolean draggable) {
        this.parent = parent;
        this.parent.setOnTouchListener(this);
        this.handler = new Handler(Looper.getMainLooper());
        this.onLongPressedEvent = onLongPressedEvent;
        this.draggable = draggable;
    } // SwipeDetector

    public void setOnSwipeListener(OnSwipeEvent listener) {
        try {
            swipeEventListener = listener;
        } catch (ClassCastException e) {
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "setOnSwipeListener : " + e.getMessage(), true);
        }
    } // setOnSwipeListener

    public void onTapEventDetected(float valueX, float valueY) {
        if (swipeEventListener != null) {
            swipeEventListener.tapEventDetected(parent, valueX, valueY);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onTapEventDetected : OnSwipeEvent is null.", true);
    } // onTapEventDetected

    public void onSwipeMovementValue(float valueX, float valueY) {
        if (swipeEventListener != null) {
            swipeEventListener.swipeMovementValue(valueX, valueY);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onSwipeMovementValue : OnSwipeEvent is null.", true);
    } // onSwipeMovementValue

    public void onDraggedValue(float valueX, float valueY){
        if (swipeEventListener != null) {
            swipeEventListener.onDraggedValue(valueX, valueY);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onDraggedValue : OnSwipeEvent is null.", true);
    } // onDraggedValue

    public void onRightToLeftSwipe() {
        if (swipeEventListener != null) {
            swipeEventListener.swipeEventDetected(parent, SwipeType.RIGHT_TO_LEFT);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onRightToLeftSwipe : OnSwipeEvent is null.", true);
    } // onRightToLeftSwipe

    public void onLeftToRightSwipe() {
        if (swipeEventListener != null) {
            swipeEventListener.swipeEventDetected(parent, SwipeType.LEFT_TO_RIGHT);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onLeftToRightSwipe : OnSwipeEvent is null.", true);
    } // onLeftToRightSwipe

    public void onTopToBottomSwipe() {
        if (swipeEventListener != null) {
            swipeEventListener.swipeEventDetected(parent, SwipeType.TOP_TO_BOTTOM);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onTopToBottomSwipe : OnSwipeEvent is null.", true);
    } // onTopToBottomSwipe

    public void onBottomToTopSwipe() {
        if (swipeEventListener != null) {
            swipeEventListener.swipeEventDetected(parent, SwipeType.BOTTOM_TO_TOP);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onBottomToTopSwipe : OnSwipeEvent is null.", true);
    } // onBottomToTopSwipe

    Runnable callDragEvent = new Runnable() {
        @Override
        public void run() {
            DRAG_MODE = true;
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "Run : Drag enabled.", false);
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                if (handler != null) {
                    if (draggable) {
                        handler.postDelayed(callDragEvent, ViewConfiguration.getLongPressTimeout());
                        Config.LOG(Config.TAG_SWIPE_DETECTOR, "Drag started.", false);
                    } else {
                        handler.postDelayed(onLongPressedEvent, ViewConfiguration.getLongPressTimeout());
                    }
                }
                return true;
            }
            case MotionEvent.ACTION_MOVE :
                float dX = downX - event.getX();
                float dY = downY - event.getY();

                if (DRAG_MODE)
                    onDraggedValue(dX, dY);
                else {
                    onSwipeMovementValue(dX, dY);
                    if (Math.abs(dX) > LONG_PRESS_SENSITIVITY || Math.abs(dY) > LONG_PRESS_SENSITIVITY) {
                        if (handler != null) {
                            handler.removeCallbacks(onLongPressedEvent);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP: {
                if (handler != null) {
                    if (draggable && DRAG_MODE) {
                        handler.removeCallbacks(callDragEvent);
                        Config.LOG(Config.TAG_SWIPE_DETECTOR, "onTouch : Drag disabled.", false);
                        DRAG_MODE = false;
                    } else
                        handler.removeCallbacks(onLongPressedEvent);
                }
                float upX = event.getX();
                float upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                //HORIZONTAL SCROLL
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        // left or right
                        if (deltaX < 0) {
                            this.onLeftToRightSwipe();
                            return true;
                        } else if (deltaX > 0) {
                            this.onRightToLeftSwipe();
                            return true;
                        }
                    } else {
                        this.onTapEventDetected(upX, upY);
                        return true;
                    }
                } else { //VERTICAL SCROLL
                    if (Math.abs(deltaY) > MIN_DISTANCE) {
                        // top or down
                        if (deltaY < 0) {
                            this.onTopToBottomSwipe();
                            return true;
                        } else if (deltaY > 0) {
                            this.onBottomToTopSwipe();
                            return true;
                        }
                    } else {
                        this.onTapEventDetected(upX, upY);
                        return true;
                    }
                } //VERTICAL SCROLL
                return true;
            }
        }
        return false;
    } // onTouch

    public short getMinDistance() {
        return MIN_DISTANCE;
    } // getMinDistance

    public void setMinDistance(short MIN_DISTANCE) {
        this.MIN_DISTANCE = MIN_DISTANCE;
    } // setMinDistance

    public short getLongPressSensitivity() {
        return LONG_PRESS_SENSITIVITY;
    } // getLongPressSensitivity

    public void setLongPressSensitivity(short LONG_PRESS_SENSITIVITY) {
        this.LONG_PRESS_SENSITIVITY = LONG_PRESS_SENSITIVITY;
    } // setLongPressSensitivity

} // SwipeDetector
