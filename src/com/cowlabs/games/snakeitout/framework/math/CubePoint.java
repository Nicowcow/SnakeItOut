/*
 *	Author:      Nicolas Mattia
 *	Date:        13 avr. 2012
 */

package com.cowlabs.games.snakeitout.framework.math;

public class CubePoint {
	
	public final static int FRONT = 0;
	public final static int BACK = 1;
	public final static int RIGHT = 2;
	public final static int LEFT = 3;
	public final static int TOP = 4;
	public final static int BOTTOM = 5;
	
	public final static int NORTH = 0;
	public final static int SOUTH = 2;
	public final static int EAST = 1;
	public final static int WEST = 3;
	
	public static int size;
	
	public int face;
	public int row;
	public int col;
	
	public CubePoint(int face, int row, int col){
		this.face = face;
		this.row = row;
		this.col = col;
	}
	
	public void copy(CubePoint newPoint){
		this.face = newPoint.face;
		this.row = newPoint.row;
		this.col = newPoint.col;
		
	}
	
	
	public boolean isAt(CubePoint point){
		if(this.row == point.row && this.col == point.col && this.face == point.face)
			return true;
		
		return false;
	}

}
