/*
 *	Author:      Nicolas Mattia
 *	Date:        14 avr. 2012
 */

package com.cowlabs.games.snakeitout;

import com.cowlabs.games.snakeitout.framework.math.CubeMovingPoint;

public class SnakePortion {
	public CubeMovingPoint position;
	
	public SnakePortion(CubeMovingPoint position){
		this.position = new CubeMovingPoint(0, 0, 0, 0);
		this.position.copy(position);
	}
}
