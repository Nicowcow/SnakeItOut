package com.cowlabs.games.snakeitout.framework.math;

public abstract class GeoObj2 {

    private Vector2 center = new Vector2();
    
    GeoObj2(float x, float y){
    	this.center.set(x, y);
    }
    
    GeoObj2(Vector2 center){
    	this(center.x, center.y);
    }
    
    public Vector2 getCenter(){
    	return this.center;
    }
    
    public void set(float x, float y){
    	this.center.x = x;
    	this.center.y = y;
    }
        
}
