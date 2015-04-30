package com.cowlabs.games.framework.visual.graphics;

import java.util.Vector;

import com.cowlabs.games.snakeitout.framework.gl.SpriteBatcher;


public class Displayer {
	
	private SpriteBatcher batcher;
	private final Vector<Picture> pics = new Vector<Picture>();
	
	public Displayer(){	}
		
	public void register(Picture pic){
		this.pics.add(pic);
	}
	
	public void load(){
		this.batcher = new SpriteBatcher(pics.size());
	}
	
	public void renderPics(){
		if(batcher == null)
			return;
		
		for(Picture pic: this.pics){
			batcher.beginBatch(pic.getTex());
			batcher.drawSprite(pic.getX(), pic.getY(), pic.getWidth(), pic.getHeight(), pic.getTexRegion());
			batcher.endBatch();
		}
	}

}
