package com.ck.dev.swipeviewdemo.interfaces;

import android.view.View;

import com.ck.dev.swipeviewdemo.utils.Config;
import com.ck.dev.swipeviewdemo.utils.SwipeType;

public interface OnSwipeEvent {
    void swipeEventDetected(View v, SwipeType swipeType);

    default void swipeMovementValue(float valueX, float valueY) {
        Config.LOG(Config.TAG_SWIPE_DETECTOR, "SwipeMovementValue : valueX: " + valueX + " valueY: " + valueY, false);
    } // swipeMovementValue

    default void tapEventDetected(View v, float valueX, float valueY) {
        Config.LOG(Config.TAG_SWIPE_DETECTOR, "TapEventDetected " + v.getId() + " : valueX: " + valueX + " valueY: " + valueY, false);
    } // tapEventDetected

    default void onLongPress() {
        Config.LOG(Config.TAG_SWIPE_DETECTOR, "OnLongPress.", false);
    } // onLongPress

    default void onLongPressEventCancel() {
        Config.LOG(Config.TAG_SWIPE_DETECTOR, "onLongPressEventCancel.", false);
    } // onLongPressEventCancel

    default void onDragEventStart() {
        Config.LOG(Config.TAG_SWIPE_DETECTOR, "onDragEventStart.", false);
    } // onDragEventStart

    default void onDragEventEnd() {
        Config.LOG(Config.TAG_SWIPE_DETECTOR, "onDragEventEnd ", false);
    } // onDragEventEnd

    default void onDraggedValue(float valueX, float valueY){
        swipeMovementValue(valueX, valueY);
    } // onDraggedValue
}
