/*
 *	Author:      Nicolas Mattia
 *	Date:        10 avr. 2012
 */

package com.cowlabs.games.snakeitout.framework.impl;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.cowlabs.games.snakeitout.framework.PNGDecoder;

public class AndroidPNGDecoder implements PNGDecoder {
	
	private Bitmap bitmap;
	
	@Override
	public boolean setFile(String fileName) {
		try {
			if(bitmap != null)
				bitmap.recycle();
//			bitmap = BitmapFactory.decodeStream(fileIO.readFile(fileName));
			bitmap = BitmapFactory.decodeStream(AndroidGameGlobal.fileIO.readAsset(fileName));

		} catch (IOException e) {
			return false;
		}
		
		return true;
	}

	@Override
	public int getColor(int x, int y) {
		return (bitmap.getPixel(x, y));
	}

	@Override
	public int getHeight() {
		return bitmap.getHeight();
	}

	@Override
	public int getWidth() {
		return bitmap.getWidth();
	}

	@Override
	public int readBinary(int x0, int xLength, int y) {
		int value = 0;
		
		for(int i = 0; i< xLength; i++){
			value |= (((bitmap.getPixel(x0 + i, y)==Color.BLACK)?1:0)<<(xLength-i - 1));
		}
		
		
		return value;
	}

	

	

}
