/*
 *	Author:      Nicolas Mattia
 *	Date:        12 avr. 2012
 */

package com.cowlabs.games.snakeitout;

import java.util.ArrayList;
import java.util.List;


import com.cowlabs.games.snakeitout.framework.math.CubeMovingPoint;

public class Snake {
	
	private static final int ALIVE = 0;
	private static final int DYING = 1;
	private static final int DEAD = 2;
	public final List<SnakePortion> body = new ArrayList<SnakePortion>();
	public final float deltaTimeMove;
	public float timeSinceLastMove = 0f;
	public final int speed;
	private int state = ALIVE;
	
	public CubeMovingPoint newHeadPosition = new CubeMovingPoint(1, 1, 1, 1);
	
	public Snake(CubeMovingPoint startPosition, int startLength, int speed){
		body.add(new SnakePortion(startPosition));
		body.add(new SnakePortion(new CubeMovingPoint(startPosition.row+1,startPosition.col, startPosition.face, 0)));
		body.add(new SnakePortion(new CubeMovingPoint(startPosition.row+2,startPosition.col, startPosition.face, 0)));
		body.add(new SnakePortion(new CubeMovingPoint(startPosition.row+3,startPosition.col, startPosition.face, 0)));
		this.speed = speed;
		deltaTimeMove = 2/(float)speed;
	}

	public boolean checkForMove(float deltaTime){
		
		if((timeSinceLastMove += deltaTime) >= deltaTimeMove){
			newHeadPosition.copy(body.get(0).position);
			newHeadPosition.move(1);
			return true;
		}
		
		return false;
	}
	
	public void addPortion(){
		CubeMovingPoint newPortionPosition = new CubeMovingPoint(1,1,1,1);
		newPortionPosition.copy(body.get(body.size() - 1).position);
		body.add(new SnakePortion(newPortionPosition));
	}
	
	
	
	public void moveToNewHeadPosition(){
		int len = body.size();

		SnakePortion lastPortion = body.remove(len-1);
		lastPortion.position.copy(newHeadPosition);
		body.add(0, lastPortion);
		timeSinceLastMove = 0;
	}
	
	public boolean isAlive(){
		return state == ALIVE;
	}
	
	public boolean isDying(){
		return state == DYING;
	}
	
	public boolean isDead(){
		return state == DEAD;
	}
	
	public void kill(){
		if(state == ALIVE)
			this.state = DYING;
	}
	
	public void setDead(){
		this.state = DEAD;
	}
}
