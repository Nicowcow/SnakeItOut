package com.cowlabs.games.snakeitout.framework.math;

public class Circle extends GeoObj2{
	
    public float radius;

    public Circle(float x, float y, float radius) {
    	super(x,y);
        this.radius = radius;
    }
    
    public boolean overlap(Circle circle){
    	double centersDistance = this.getCenter().dist(circle.getCenter());
    	return centersDistance <= this.radius + circle.radius;
    	
    }
    
    public boolean overlap(Vector2 point){
    	return this.getCenter().dist(point) <= this.radius;
    }
}
