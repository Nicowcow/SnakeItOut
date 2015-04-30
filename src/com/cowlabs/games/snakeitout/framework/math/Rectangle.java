package com.cowlabs.games.snakeitout.framework.math;

public class Rectangle extends GeoObj2{
    private float halfWidth, halfHeight;
    private float width, height;
    private float minX, minY, maxX, maxY;
    
    public Rectangle(){
    	this(0f, 0f, 1f,1f);
    }
    
    public Rectangle(float x, float y, float width, float height) {
        this(new Vector2(x,y), width, height);
    }
    
    public Rectangle(Vector2 center, float width, float height){
    	super(center);
    	this.updateInner(width, height);
    	
    }
    
    private void updateInner(float width, float height){
    	
    	this.width = width;
    	this.height = height;
    	
    	this.halfWidth = width/2;
    	this.halfHeight = height/2;
    	
    	this.minX = this.getCenter().x - halfWidth;
    	this.minY = this.getCenter().y - halfHeight;
    	this.maxX = this.getCenter().x + halfWidth;
    	this.maxY = this.getCenter().y + halfHeight;
    }
    
    public float getX(){
    	return this.getCenter().x;
    }
    
    public float getY(){
    	return this.getCenter().y;
    }
    
    public float getWidth(){
    	return this.width;
    }
    
    public float getHeight(){
    	return this.height;
    }
    
    public void set(float x, float y, float width, float height){
    	super.set(x, y);
    	
    	updateInner(width, height);
    	
    }

    public boolean overlap(Vector2 point){
    	
    	return this.minX <= point.x && this.minY <= point.y &&
    			this.maxX >= point.x && this.maxY >= point.y;
    	
    }
}
