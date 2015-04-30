/*
 *	Author:      Nicolas Mattia
 *	Date:        10 avr. 2012
 */

package com.cowlabs.games.snakeitout.framework;


public interface PNGDecoder {
	
	public boolean setFile(String fileName);
	public int getHeight();
	public int getWidth();
	public int getColor(int x, int y);
	public int readBinary(int x0, int xLength, int y);
}
