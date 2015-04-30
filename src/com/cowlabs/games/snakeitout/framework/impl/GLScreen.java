/*
 *	Author:      Nicolas Mattia
 *	Date:        5 avr. 2012
 */

package com.cowlabs.games.snakeitout.framework.impl;

import android.view.KeyEvent;
import com.cowlabs.games.snakeitout.framework.Screen;
import com.cowlabs.games.snakeitout.framework.events.EventListener;

public abstract class GLScreen extends Screen implements EventListener {
    protected final GLGraphics glGraphics;
//    protected final AndroidGame glGameApp;
    
//    public static int width = 800; 
//    public static int height = 480;
    
    public GLScreen() {
//        super(game);
//        glGameApp = game;
        glGraphics = AndroidGameGlobal.glGraphics;
    }
    
    public abstract void onDimensionsChanged();

    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
