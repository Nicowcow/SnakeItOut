package com.cowlabs.games.snakeitout.framework.impl;

import com.cowlabs.games.snakeitout.framework.math.Vector2;

public class TouchEvent {
	
	public enum TouchType{
		TOUCH_UP,
		TOUCH_DOWN,
		TOUCH_MOVE
	}

	private final int pointerId;
	private final Vector2 touchPoint;
	private final TouchType type;
	
	public TouchEvent(TouchType type, int pointerId, Vector2 touchPoint){
		this.type = type;
		this.pointerId = pointerId;
		this.touchPoint = touchPoint;
	}
	
	public int getPointerId(){
		return this.pointerId;
	}
	
	public Vector2 getTouchPoint(){
		return this.touchPoint;
	}
	
	public TouchType getType(){
		return this.type;
	}
}
