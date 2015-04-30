/*
 *	Author:      Nicolas Mattia
 *	Date:        5 avr. 2012
 */

package com.cowlabs.games.snakeitout.framework;

import android.view.KeyEvent;

public abstract class Screen {
//    protected final AndroidGame game;
//
//    public Screen(AndroidGame game) {
////        this.game = game;
//    }

    public abstract void update(float deltaTime);

    public abstract void present(float deltaTime);
    
    public abstract boolean onKeyDown(int keyCode, KeyEvent event);
}
