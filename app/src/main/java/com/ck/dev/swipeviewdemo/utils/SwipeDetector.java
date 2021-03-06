package com.ck.dev.swipeviewdemo.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.ck.dev.swipeviewdemo.interfaces.OnSwipeEvent;

public class SwipeDetector implements View.OnTouchListener {

    private final View parent;
    private short MIN_DISTANCE = 50;
    private short LONG_PRESS_SENSITIVITY = 100;

    private int DRAG_EVENT_DELAY = 1000;

    private float downX;
    private float downY;

    private boolean draggable;
    private boolean onLongPress;
    private boolean onLongPressEventRunning;

    private boolean DRAG_MODE;
    private boolean DRAG_SHIT;

    private int DRAG_LIMIT = 25;

    private OnSwipeEvent swipeEventListener;

    private Handler handler = null;

    public SwipeDetector(View parent) {
        this.parent = parent;
        this.parent.setOnTouchListener(this);
    } // SwipeDetector

    public SwipeDetector(View parent, boolean onLongPress) {
        this.parent = parent;
        this.parent.setOnTouchListener(this);
        this.onLongPress = onLongPress;
        if (onLongPress)
            this.handler = new Handler(Looper.getMainLooper());
    } // SwipeDetector

    public SwipeDetector(View parent, boolean onLongPress, boolean draggable) {
        this.parent = parent;
        this.parent.setOnTouchListener(this);
        this.onLongPress = onLongPress;
        this.draggable = draggable;
        if (onLongPress || draggable)
            this.handler = new Handler(Looper.getMainLooper());
    } // SwipeDetector

    public void setOnSwipeListener(OnSwipeEvent listener) {
        try {
            swipeEventListener = listener;
        } catch (ClassCastException e) {
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "setOnSwipeListener : " + e.getMessage(), true);
        }
    } // setOnSwipeListener

    private void onTapEventDetected(float valueX, float valueY) {
        if (swipeEventListener != null) {
            swipeEventListener.tapEventDetected(parent, valueX, valueY);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onTapEventDetected : OnSwipeEvent is null.", true);
    } // onTapEventDetected

    private void onLongPressedEvent() {
        if (swipeEventListener != null) {
            swipeEventListener.onLongPress();
            onLongPressEventRunning = true;
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onLongPressedEvent : OnSwipeEvent is null.", true);
    } // onLongPressedEvent

    private void onLongPressEventCancel() {
        if (swipeEventListener != null) {
            swipeEventListener.onLongPressEventCancel();
            onLongPressEventRunning = false;
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onLongPressEventCancel : OnSwipeEvent is null.", true);
    } // onLongPressEventCancel

    private void onSwipeMovementValue(float valueX, float valueY) {
        if (swipeEventListener != null) {
            swipeEventListener.swipeMovementValue(valueX, valueY);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onSwipeMovementValue : OnSwipeEvent is null.", true);
    } // onSwipeMovementValue

    private void onDragEventStart(){
        if (onLongPressEventRunning)
            onLongPressEventCancel();
        Config.LOG(Config.TAG_SWIPE_DETECTOR, "Run : Drag enabled.", false);
        if (swipeEventListener != null) {
            swipeEventListener.onDragEventStart();
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onDragEventStart : OnSwipeEvent is null.", true);
    } // onDragEventStart

    private void onDraggedValue(float valueX, float valueY){
        if (swipeEventListener != null) {
            swipeEventListener.onDraggedValue(valueX, valueY);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onDraggedValue : OnSwipeEvent is null.", true);
    } // onDraggedValue

    private void onDragEventEnd(){
        if (swipeEventListener != null) {
            swipeEventListener.onDragEventEnd();
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onDragEventEnd : OnSwipeEvent is null.", true);
    } // onDragEventEnd

    private void onRightToLeftSwipe() {
        if (swipeEventListener != null) {
            swipeEventListener.swipeEventDetected(parent, SwipeType.RIGHT_TO_LEFT);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onRightToLeftSwipe : OnSwipeEvent is null.", true);
    } // onRightToLeftSwipe

    private void onLeftToRightSwipe() {
        if (swipeEventListener != null) {
            swipeEventListener.swipeEventDetected(parent, SwipeType.LEFT_TO_RIGHT);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onLeftToRightSwipe : OnSwipeEvent is null.", true);
    } // onLeftToRightSwipe

    private void onTopToBottomSwipe() {
        if (swipeEventListener != null) {
            swipeEventListener.swipeEventDetected(parent, SwipeType.TOP_TO_BOTTOM);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onTopToBottomSwipe : OnSwipeEvent is null.", true);
    } // onTopToBottomSwipe

    private void onBottomToTopSwipe() {
        if (swipeEventListener != null) {
            swipeEventListener.swipeEventDetected(parent, SwipeType.BOTTOM_TO_TOP);
        } else
            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onBottomToTopSwipe : OnSwipeEvent is null.", true);
    } // onBottomToTopSwipe

    private final Runnable callDragEvent = new Runnable() {
        @Override
        public void run() {
            if (onLongPress) {
                onLongPressedEvent();
            }
            DRAG_MODE = true;
            DRAG_SHIT = false;
        }
    }; // callDragEvent

    private final Runnable onLongPressedEvent = this::onLongPressedEvent; // onLongPressedEvent

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                if (handler != null) {
                    if (draggable) {
                        handler.postDelayed(callDragEvent, DRAG_EVENT_DELAY);
                        Config.LOG(Config.TAG_SWIPE_DETECTOR, "Drag called.", false);
                    } else {
                        handler.postDelayed(onLongPressedEvent, ViewConfiguration.getLongPressTimeout());
                    }
                }
                return true;
            }
            case MotionEvent.ACTION_MOVE :
                float dX = downX - event.getX();
                float dY = downY - event.getY();

                if (DRAG_MODE) {
                    if (dX >= DRAG_LIMIT || dX <= -DRAG_LIMIT || dY >= DRAG_LIMIT || dY <= -DRAG_LIMIT) {
                        if (!DRAG_SHIT) {
                            DRAG_SHIT = true;
                            onDragEventStart();
                        }
                    }
                    if (DRAG_SHIT) {
                        onDraggedValue(dX, dY);
                        break;
                    }
                }
                if (!onLongPressEventRunning) {
                    onSwipeMovementValue(dX, dY);
                    if (Math.abs(dX) > LONG_PRESS_SENSITIVITY || Math.abs(dY) > LONG_PRESS_SENSITIVITY) {
                        if (handler != null) {
                            handler.removeCallbacks(onLongPressedEvent);
                            if (draggable) {
                                handler.removeCallbacks(callDragEvent);
                                DRAG_SHIT = false;
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP: {
                if (handler != null) {
                    if (draggable) {
                        handler.removeCallbacks(callDragEvent);
                        DRAG_SHIT = false;
                        if (DRAG_MODE) {
                            Config.LOG(Config.TAG_SWIPE_DETECTOR, "onTouch : Drag disabled.", false);
                            DRAG_MODE = false;
                            onDragEventEnd();
                            return true;
                        }
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

    public int getDragEventDelay() {
        return DRAG_EVENT_DELAY;
    } // getDragEventDelay

    public int getDragLimit() {
        return DRAG_LIMIT;
    } // getDragLimit

    public void setDragEventDelay(int DRAG_EVENT_DELAY) {
        this.DRAG_EVENT_DELAY = DRAG_EVENT_DELAY;
    } // setDragEventDelay

    public void setDragLimit(int DRAG_LIMIT) {
        this.DRAG_LIMIT = DRAG_LIMIT;
    } // setDragLimit

} // SwipeDetector