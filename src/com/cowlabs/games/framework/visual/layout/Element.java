package com.cowlabs.games.framework.visual.layout;

import java.util.Vector;

import com.cowlabs.games.framework.visual.graphics.Displayer;
import com.cowlabs.games.framework.visual.graphics.Picture;
import com.cowlabs.games.snakeitout.framework.math.Rectangle;

abstract class Element {
	
	private final Rectangle shape = new Rectangle();
	
	private Vector<SubElement> subElements = new Vector<SubElement>();
	private Vector<Picture> pics = new Vector<Picture>();
	
	public void hangOnRightExt(SubElement e){
		this.hang(e, 1, 1 , 0, 1, 0);
	}
	
	public void hangOnLeftExt(SubElement e){
		this.hang(e, 1, -1, 0, -1, 0);
	}
	
	public void hang(SubElement e, float ratio, float halfX, float halfY){
		this.hang(e, ratio, halfX, halfY, 0, 0);
	}
	
	public void hang(SubElement e, float ratio, float halfParentX, float halfParentY,
						float ownHalfX, float ownHalfY){
		subElements.add(e);
		e.setRatios(ratio, halfParentX, halfParentY, ownHalfX, ownHalfY);
		e.setFromParent(this.shape);
	}
	
	public void addPicture(Picture pic){
		this.pics.add(pic);
	}
	
	public Vector<Picture> getPics(){
		return this.pics;
	}
	
	public Vector<SubElement> getSubElements(){
		return this.subElements;
	}
	
	abstract float getAspectRatio();
	
	void set(int width, int height, int x, int y){
		this.shape.set(x, y, width, height);
	}
	
	public Rectangle getShape(){
		return this.shape;
	}
	
	void loadDisplayer(Displayer disp){
		for(int i = 0; i < this.pics.size(); i++){
			disp.register(this.pics.get(i));
		}
		
		for(int i = 0; i < subElements.size(); i++){
			subElements.get(i).loadDisplayer(disp);
		}
	}
	


}
