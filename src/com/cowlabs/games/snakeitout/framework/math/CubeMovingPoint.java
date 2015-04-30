/*
 *	Author:      Nicolas Mattia
 *	Date:        13 avr. 2012
 */

package com.cowlabs.games.snakeitout.framework.math;

public class CubeMovingPoint extends CubePoint {
	
	public int direction;
	
	private int new_row;
	private int new_col;

	public CubeMovingPoint(int face, int row, int col, int direction) {
		super(face, row, col);
		this.direction = direction;
	}
	
	public void move(int delta){
		switch(direction){
		case NORTH:
			this.row -= delta;
			break;
			
		case SOUTH:
			this.row += delta;
			break;
			
		case EAST: 
			this.col += delta;
			break;
			
		case WEST:
			this.col -= delta;
			break;
		}
		
		checkFace();
	}
	
	public void copy(CubeMovingPoint newPoint){
		super.copy(newPoint);
		this.direction = newPoint.direction;
	}
	
	private void checkFace(){
		
		if(row > size)
			rowBiggerThanSize();
		else if(row < 1)
			rowSmallerThanOne();
		else if(col > size)
			colBiggerThanSize();
		else if(col < 1)
			colSmallerThanOne();
		
		if(row > size || row < 1 || col > size || col < 1)
			checkFace();
		
	}
	
	private void rowBiggerThanSize(){
		switch(this.face){
		case FRONT:
			this.face = BOTTOM;
			this.row -= size;
			break;
			
		case BACK:
			this.face = TOP;
			this.row -= size;
			break;
			
		case RIGHT:
			this.face = BOTTOM;
			new_col = 2*size  + 1 - this.row;
			this.row = this.col;
			this.col = new_col;
			this.direction = WEST;
			break;
			
		case LEFT:
			this.face = BOTTOM;
			new_col = this.row - size;
			this.row = size + 1 - this.col;
			this.col = new_col;
			this.direction = EAST;
			break;
			
		case TOP:
			this.face = FRONT;
			this.row -= size;
			break;
			
		case BOTTOM:
			this.face = BACK;
			this.row -= size;
		}
		
		
	}
	
	private void rowSmallerThanOne(){
		switch(this.face){
		case FRONT:
			this.face = TOP;
			this.row += size;
			break;
		
		case BACK:
			this.face = BOTTOM;
			this.row += size;
			break;
			
		case RIGHT:
			this.face = TOP;
			new_col = this.row + size;
			this.row = size - this.col  + 1;
			this.col = new_col;
			this.direction = WEST;
			break;
			
		case LEFT:
			this.face = TOP;
			new_col = - this.row + 1;
			this.row = this.col;
			this.col = new_col;
			this.direction = EAST;
			break;
			
		case TOP:
			this.face = BACK;
			this.row += size;
			break;
			
		case BOTTOM:
			this.face = FRONT;
			this.row += size;
			
		}
	}
	
	private void colBiggerThanSize(){
		
		switch(this.face){
		case FRONT:
			this.face = RIGHT;
			this.col -= size;
			this.direction = EAST;
			break;
			
		case BACK:
			this.face = RIGHT;
			this.col = 2*size - (this.col) + 1;
			this.row = size  + 1 - this.row;
			this.direction = WEST;
			break;
			
		case RIGHT:
			this.face = BACK;
			this.col = 2*size - this.col + 1;
			this.row = size + 1 - this.row;
			this.direction = WEST;
			break;
			
		case LEFT:
			this.face = FRONT;
			this.col -= size;
			break;
			
		case TOP:
			this.face = RIGHT;
			new_row = this.col - size;
			this.col = size - this.row + 1;
			this.row = new_row;
			this.direction = SOUTH;
			break;
			
		case BOTTOM:
			this.face = RIGHT;
			new_row = 2*size + 1 - this.col;
			this.col = this.row;
			this.row = new_row;
			this.direction = NORTH;
			break;
		}

		
	}
	
	private void colSmallerThanOne(){
		switch(face){
		case FRONT:
			this.face = LEFT;
			this.col += size;
			break;
		
		case BACK:
			this.face = LEFT;
			this.row = size  + 1 - this.row;
			this.col = - this.col + 1;
			this.direction = EAST;
			break;
			
		case RIGHT:
			this.face = FRONT;
			this.col += size;
			break;
			
		case LEFT:
			this.face = BACK;
			this.row = size + 1 - this.row;
			this.col = - this.col + 1;
			this.direction = EAST;
			break;
			
		case TOP:
			this.face = LEFT;
			new_row = - this.col + 1;
			this.col = this.row;
			this.row = new_row;
			this.direction = SOUTH;
			break;
			
		case BOTTOM:
			this.face = LEFT;
			new_row = size  + this.col ;
			this.col = size - this.row + 1;
			this.row = new_row;
			this.direction = NORTH;
			
		}
	}
	
	

}
