package com.cowlabs.games.framework.visual.layout;


public class Text extends SubElement{
	
	public Text(Font font, String text) {		
		this.initChars(font, text);
		
	}
	
	private void initChars(Font font, String text){
		int i;
		for(i = 0; i < text.length(); i++){
//			super.;
		}
	}
	
	
	@Override
	float getAspectRatio(){
		
		return 0f;
	}
}