package com.cowlabs.games.framework.visual.layout;

import com.cowlabs.games.snakeitout.framework.gl.Texture;
import com.cowlabs.games.snakeitout.framework.gl.TextureRegion;

public class Font {
	
	private final Texture texture;
    private final TextureRegion[] glyphs = new TextureRegion[96]; 
    private float aspectRatio;
    
    public Font(Texture texture, 
                int offsetX, int offsetY,
                int glyphsPerRow, int glyphWidth, int glyphHeight) {        
        this.texture = texture;
        this.aspectRatio = (float) glyphWidth / (float) glyphHeight;
        int x = offsetX;
        int y = offsetY;
        for(int i = 0; i < 96; i++) {
            glyphs[i] = new TextureRegion(texture, x, y, glyphWidth, glyphHeight);
            x += glyphWidth;
            if(x == offsetX + glyphsPerRow * glyphWidth) {
                x = offsetX;
                y += glyphHeight;
            }
        }        
    }
    
    public TextureRegion getRegion(char c){
    	if(c < 0 || c > glyphs.length - 1)
    		return glyphs[0];
    	else
    		return glyphs[c];
    }
	
    public Texture getTexture(){
    	return this.texture;
    }
    
    public float getCharAspectRatio(){
    	return this.aspectRatio;
    }
	
}
