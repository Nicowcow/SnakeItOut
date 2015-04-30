package com.cowlabs.games.framework.visual.layout;

import com.cowlabs.games.framework.visual.graphics.Picture;
import com.cowlabs.games.snakeitout.framework.gl.Texture;
import com.cowlabs.games.snakeitout.framework.gl.TextureRegion;

public class Banner extends SubElement {

	private final Picture pic ;

	
	public Banner(Texture tex, TextureRegion texRegion){
		this.pic = new Picture(tex, texRegion, super.getShape());
		super.addPicture(pic);
	}
	
	@Override
	float getAspectRatio() {
		return pic.getAspectRatio();
	}

	public Picture getPic() {
		return this.pic;
	}

	

}
