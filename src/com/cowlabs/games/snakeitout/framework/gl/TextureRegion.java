package com.cowlabs.games.snakeitout.framework.gl;

public class TextureRegion {    
    public final float u1, v1;
    public final float u2, v2;
    private final float aspectRatio;
    
    public TextureRegion(Texture texture, float x, float y, float width, float height) {
    	this.aspectRatio = width/height;
    	
    	float tempu1 = x; float tempu2 = tempu1 + width;
    	float tempv1 = y; float tempv2 = tempv1 + height;
    	
    	this.u1 = tempu1 / texture.width;
    	this.u2 = tempu2 / texture.width;
    	this.v1 = tempv1 / texture.height;
    	this.v2 = tempv2 / texture.height;
    	
    }
    
    public float getAspectRatio(){
    	return this.aspectRatio;
    }
}
