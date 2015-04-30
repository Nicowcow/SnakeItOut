package com.cowlabs.games.framework.visual.layout;

import com.cowlabs.games.framework.visual.graphics.Displayer;
import com.cowlabs.games.framework.visual.graphics.Picture;
import com.cowlabs.games.snakeitout.framework.gl.Texture;
import com.cowlabs.games.snakeitout.framework.gl.TextureRegion;


public class Screen extends Element {
	
	private final Picture background;
	private final Displayer displayer = new Displayer();
	
	public Screen(Texture tex, TextureRegion texRegion){
		this.background = new Picture(tex, texRegion, super.getShape());
		super.addPicture(background);
	}
	
	public void set(int screenWidth, int screenHeight){
		
		int width, height;
		
		float physicalAR = (float) screenWidth/ (float) screenHeight;
		float picAR = this.getAspectRatio();
				
		if(physicalAR > picAR){
			height =  (int) (screenWidth/picAR);
			width = screenWidth;
		} else {
			height = screenHeight;
			width = (int) ( physicalAR * screenHeight);
		}
		
		super.set(width, height, screenWidth/2, screenHeight/2);
		
		
	}
	
	@Override
	float getAspectRatio(){
		return this.background.getAspectRatio();
	}

	public Picture getPic() {
		return this.background;
	}
	
	public void loadDisplayer(){
		this.loadDisplayer(this.displayer);
		this.displayer.load();
	}
	
	public void present(){
		this.displayer.renderPics();
	}
	
	
	
	
}
