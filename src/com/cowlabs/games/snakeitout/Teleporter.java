/*
 *	Author:      Nicolas Mattia
 *	Date:        12 avr. 2012
 */

package com.cowlabs.games.snakeitout;

import com.cowlabs.games.snakeitout.framework.math.CubePoint;

public class Teleporter {
	public CubePoint position;
	public Teleporter twin;
	public float[] floatColor;
	public int color;
	
	public Teleporter(CubePoint position, int color){
		this.position = new CubePoint(1,1,1);
		this.position.row = position.row;
		this.position.col = position.col;
		this.position.face = position.face;
		this.color = color;
		this.floatColor = colorIntToFloatArray(color);
	}
	
	private float[] colorIntToFloatArray(int color){
		float[] floatArray = new float[4];
		int alpha = ((color & 0xff000000)>>> 6*4);
		int red = ((color & 0x00ff0000)>>> 4*4);
		int green = ((color & 0x0000ff00)>>> 2*4);
		int blue = (color & 0x000000ff);
		floatArray[0] = red/255f;
		floatArray[1] = green/255f;
		floatArray[2] = blue/255f;
		floatArray[3] = alpha/255f;
		return floatArray;
	}
}
