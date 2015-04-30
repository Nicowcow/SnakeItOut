/*
 *	Author:      Nicolas Mattia
 *	Date:        5 avr. 2012
 */

package com.cowlabs.games.snakeitout.framework.impl;

import java.util.List;

import com.cowlabs.games.snakeitout.framework.Input;

import android.content.Context;
import android.view.View;

public class AndroidInput implements Input {    
    AccelerometerHandler accelHandler;
    KeyboardHandler keyHandler;
    TouchHandler touchHandler;

    public AndroidInput(Context context, View view, float scaleX, float scaleY) {
        accelHandler = new AccelerometerHandler(context);
        keyHandler = new KeyboardHandler(view);               
        touchHandler = new MultiTouchHandler(view, scaleX, scaleY);        
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return keyHandler.isKeyPressed(keyCode);
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }

    @Override
    public float getAccelX() {
        return accelHandler.getAccelX();
    }

    @Override
    public float getAccelY() {
        return accelHandler.getAccelY();
    }

    @Override
    public float getAccelZ() {
        return accelHandler.getAccelZ();
    }

//    @Override
//    public List<TouchEvent> getTouchEvents() {
//        return touchHandler.getTouchEvents();
//    }
    
    public void queueTouchEvents() {
    	
    }
    
    @Override
    public List<KeyEvent> getKeyEvents() {
        return keyHandler.getKeyEvents();
    }
}
