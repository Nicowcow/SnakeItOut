package com.cowlabs.games.snakeitout.framework.gl;

import com.cowlabs.games.snakeitout.framework.math.Numbers;

public class Font {
    public final Texture texture;
    public final int glyphWidth;
    public final int glyphHeight;
    public final float aspectRatio;
    public final TextureRegion[] glyphs = new TextureRegion[96];   
    
    public Font(Texture texture, 
                int offsetX, int offsetY,
                int glyphsPerRow, int glyphWidth, int glyphHeight) {        
        this.texture = texture;
        this.glyphWidth = glyphWidth;
        this.glyphHeight = glyphHeight;
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
    
    public void drawText(SpriteBatcher batcher, String text, float x, float y, int size, boolean centered){
    	int len = text.length();
    	
    	int height = size;
    	int width = (int) (aspectRatio * height);
    	if(centered){
    		x-= len * width / 2;
    		if(!Numbers.isOdd(len))
    			x+= width / 2;
    	}
        for(int i = 0; i < len; i++) {
            int c = text.charAt(i) - ' ';
            if(c < 0 || c > glyphs.length - 1) 
                continue;
            
            TextureRegion glyph = glyphs[c];
            batcher.drawSprite(x, y, width, height, glyph);
            x += width;
        }
    }
    
    public void drawText(SpriteBatcher batcher, String text, float x, float y) {
        drawText(batcher, text, x, y, glyphHeight, false);
    }
}
