package com.cowlabs.games.framework.visual.graphics;

import com.cowlabs.games.snakeitout.framework.gl.Texture;
import com.cowlabs.games.snakeitout.framework.gl.TextureRegion;
import com.cowlabs.games.snakeitout.framework.math.Rectangle;

public class Picture{

	private Texture tex;
	private TextureRegion texRegion;
	private final Rectangle shape;
	
	public Picture(Texture tex, TextureRegion texRegion, Rectangle shape){
		this.shape = shape;
		this.setTex(tex);
		this.setTexRegion(texRegion);
	}
	
	public float getAspectRatio(){
		return this.texRegion.getAspectRatio();
	}
	
	// TODO remove from here
	
	public float getX(){
		return this.shape.getX();
	}
	
	public float getY(){
		return this.shape.getY();
	}
	
	public float getWidth(){
		return this.shape.getWidth();
	}
	
	public float getHeight(){
		return this.shape.getHeight();
	}
	
	public TextureRegion getTexRegion() {
		return texRegion;
	}

	public Texture getTex() {
		return tex;
	}

	public void setTex(Texture tex) {
		this.tex = tex;
	}

	public void setTexRegion(TextureRegion texRegion) {
		this.texRegion = texRegion;
	}
	
	

}
