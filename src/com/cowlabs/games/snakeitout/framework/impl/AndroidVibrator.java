/*
 *	Author:      Nicolas Mattia
 *	Date:        11 avr. 2012
 */

package com.cowlabs.games.snakeitout.framework.impl;

import com.cowlabs.games.snakeitout.Settings;

import android.os.Vibrator;

public class AndroidVibrator {
	
	private Vibrator vibrator;
	private boolean hasVibrator = true;
	
	public AndroidVibrator(Vibrator vibrator){
		this.vibrator = vibrator;
	}
	
	public void vibrate(int mTime){
		if(Settings.vibrEnabled && hasVibrator){
			vibrator.vibrate(mTime);
		}
		
	}
}
