/*
 *	Author:      Nicolas Mattia
 *	Date:        23 avr. 2012
 */

package com.cowlabs.games.snakeitout;

import javax.microedition.khronos.opengles.GL10;


import android.view.KeyEvent;

import com.cowlabs.games.snakeitout.framework.gl.Camera2D;
import com.cowlabs.games.snakeitout.framework.gl.SpriteBatcher;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;
import com.cowlabs.games.snakeitout.framework.impl.GLScreen;
import com.cowlabs.games.snakeitout.framework.impl.ScreenFinishedEvent;

public class SplashScreen extends GLScreen {
	
	
	private int center_x, center_y;
	private int image_w, image_h;
	private int background_w, background_h;
	SpriteBatcher batcher;
	Camera2D guiCam = new Camera2D(glGraphics,0,0);

	private float elapsedTime = 0;
	private float animStartTime = 0f;
	private float animFirstPeak = 0.50f;
	private float animFirstPeakTime = animStartTime + .2f;
	private float animDownPeak = 0.6f;
	private float animDownPeakTime = animStartTime + 0.4f;
	private float animFinalPeak = 1f;
	private float animFinalPeakTime=animStartTime +0.6f;
	private float animEndTime = animStartTime + 1.2f;
	private float a0 = (animFirstPeak - 0) / (animFirstPeakTime - animStartTime), b0 = 0;
	private float a1 = (animDownPeak - animFirstPeak)/(animDownPeakTime - animFirstPeakTime), b1 = animFirstPeak;
	private float a2 = (animFinalPeak - animDownPeak)/(animFinalPeakTime - animDownPeakTime), b2 = animDownPeak;
	
	public SplashScreen() {
//		super(game);
		batcher = new SpriteBatcher(10);
	}

	@Override
	public void onDimensionsChanged() {
		
		int width = AndroidGameGlobal.glGraphics.getWidth();
		int height = AndroidGameGlobal.glGraphics.getHeight();
		
		center_x = width/2; center_y = height/2;
		image_w = height * 3 / 4; image_h = image_w;
		background_h = height; background_w = 800 * background_h / 480;
		guiCam = new Camera2D(glGraphics, width, height);

	}
	
	private float alphaFromElapsedTime(float t){
		if(t >= animStartTime){
			if(t < animFirstPeakTime)
				return a0 * (t- animStartTime) + b0;
			if(t < animDownPeakTime)
				return a1 * (t- animFirstPeakTime) + b1;
			if(t < animFinalPeakTime)
				return a2 * (t - animDownPeakTime) + b2;
			return animFinalPeak;
		}
		else
			return 0f;
	}

	@Override
	public void present(float deltaTime) {
		elapsedTime += deltaTime;
		
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();
		
		gl.glColor4f(1, 1, 1, alphaFromElapsedTime(elapsedTime));
		
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.splashBackground);
		batcher.drawSprite(center_x, center_y, background_w, background_h, Assets.splashBackgroundRegion);
		batcher.endBatch();
		gl.glColor4f(1, 1, 1, 1);

		batcher.beginBatch(Assets.splashTexture);
		batcher.drawSprite(center_x, center_y, image_w, image_h, Assets.splashRegion);
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
	}

	@Override
	public void update(float deltaTime) {
		if(elapsedTime >= animEndTime){
			Jukebox.actionHappened(Jukebox.APP_STARTED);
			AndroidGameGlobal.evtMgr.queueEvent(
					new ScreenFinishedEvent(new MainMenuScreen()));
		}


	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleEvent(Object e) {
		// TODO Auto-generated method stub
		
	}

}
