/*
 *	Author:      Nicolas Mattia
 *	Date:        12 avr. 2012
 */

package com.cowlabs.games.snakeitout;

import com.cowlabs.games.snakeitout.framework.math.CubePoint;

public class Apple {
	public CubePoint position;
	public float dephasage;
	public Apple(CubePoint position){
		this.position = new CubePoint(1,1,1);
		this.position.row = position.row;
		this.position.col = position.col;
		this.position.face = position.face;
	}
	
}
