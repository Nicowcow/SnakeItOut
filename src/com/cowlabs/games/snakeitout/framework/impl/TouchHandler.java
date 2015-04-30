/*
 *	Author:      Nicolas Mattia
 *	Date:        5 avr. 2012
 */

package com.cowlabs.games.snakeitout.framework.impl;

//import java.util.List;


import android.view.View.OnTouchListener;

public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);
    
    public int getTouchX(int pointer);
    
    public int getTouchY(int pointer);
    
//    public List<TouchEvent> getTouchEvents();
}
