package com.cowlabs.games.snakeitout;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.cowlabs.games.snakeitout.framework.impl.AndroidGame;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGameApp;

public class SnakeItOut extends AndroidGameApp {
	boolean firstTimeCreate = true;	
	
	@Override
	public AndroidGame createGameAndStartView() {
		return new SnakeItOutGame();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if (firstTimeCreate) {		
			
			Map.loadMaps();
			
			Assets.load();
			firstTimeCreate = false;
		} else {
			Assets.reload();
			Map.reload();
			Jukebox.reload();
		}
	}                                 
}
