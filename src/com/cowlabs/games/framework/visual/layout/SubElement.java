package com.cowlabs.games.framework.visual.layout;

import com.cowlabs.games.snakeitout.framework.math.Rectangle;


public abstract class SubElement extends Element {
	
	private float toParentRatio;
	private float ratioToHalfParentX, ratioToHalfParentY;
	private float ratioToHalfThisX, ratioToHalfThisY;
	
	
	void setRatios(float toParent, float halfParentX, float halfParentY,
					float ownHalfX, float ownHalfY){
		this.toParentRatio = toParent;
		this.ratioToHalfParentX = halfParentX; this.ratioToHalfParentY = halfParentY;
		this.ratioToHalfThisX = ownHalfX; this.ratioToHalfThisY = ownHalfY;
		
	}
	
	void setFromParent(Rectangle parent){
		
		
		
		int newHeight = (int) (parent.getHeight() * this.toParentRatio);
		int newWidth = (int) (newHeight * this.getAspectRatio());

		
		int newX = (int) (parent.getX() 
				+ 0.5f * ratioToHalfParentX * parent.getWidth()
				+ 0.5f * ratioToHalfThisX * newWidth);
		int newY = (int) (parent.getY() 
				+ 0.5f * ratioToHalfParentY * parent.getHeight()
				+ 0.5f * ratioToHalfThisY * newHeight);	
		
		super.set(newWidth, newHeight, newX, newY);
	}
	
	

}
