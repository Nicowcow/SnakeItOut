/* *	Author:      Nicolas Mattia
 *	Date:        12 avr. 2012
 */

package com.cowlabs.games.snakeitout;

import javax.microedition.khronos.opengles.GL10;

import com.cowlabs.games.snakeitout.framework.gl.Camera2D;
import com.cowlabs.games.snakeitout.framework.gl.SpriteBatcher;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;
import com.cowlabs.games.snakeitout.framework.impl.GLScreen;
import com.cowlabs.games.snakeitout.framework.impl.ScreenFinishedEvent;

public class LoadingScreen extends GLScreen {
	
	World world;
	private Map map;
	
	private static int NOTHING = 0;
	private static int CREATING_WORLD = 1;
	private static int SETTING_SCREEN = 2;
	private static int FINISHED = 3;
	
	
	private float elapsedTime = 0;
	private float duration = 4f;
	private int state;
	private int speed;
	
	private int center_x; private int text_y; private int text_h;
	private int center_y; private int square_w; private int square_x; private int square_offset;
	
	SpriteBatcher batcher;
	Camera2D guiCam = new Camera2D(glGraphics,0,0);
	
	private String text;
	
	public LoadingScreen(Map map, int speed) {
//		super(game);
		this.map = map;
		this.speed=speed;
		state = NOTHING;
		
		String[] stringArray = AndroidGameGlobal.stringArray;
		text = stringArray[(int)(Math.random()*stringArray.length)];
		
		
		batcher = new SpriteBatcher(64);

		
		Jukebox.actionHappened(Jukebox.LOADING_SCREEN_READY);
		onDimensionsChanged();
	}

	@Override
	public void onDimensionsChanged() {
		
		int width = AndroidGameGlobal.glGraphics.getWidth();
		int height = AndroidGameGlobal.glGraphics.getHeight();
		
		center_x = width/2; center_y = height/2;
		text_h = height/30; text_y = height/10 + text_h/2;
		
		square_w = width / 20; square_offset = square_w / 4;
		square_x = width/2 - 5 * ( square_w + square_offset);
		
		guiCam = new Camera2D(glGraphics, width, height);
		
	}
	
	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		guiCam.setViewportAndMatrices();

		
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		Assets.font.drawText(batcher, "" + text, center_x, text_y,text_h, true);
		float x = 0;
		for(int i = 0; i < elapsedTime/duration * 11f; i++){
			batcher.drawSprite(square_x + x, center_y, square_w, square_w, Assets.blueRegion);
			x+= square_w + square_offset;
		}
		
		batcher.endBatch();
		
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}

	@Override
	public void update(float deltaTime) {
//		AndroidGameGlobal.input.getTouchEvents(); // Vide le buffer
		elapsedTime+= deltaTime;
		if(state == NOTHING && elapsedTime >= duration / 5f){
			state = CREATING_WORLD;
			return;
		}
		if(state == CREATING_WORLD){
			world = new World(map,speed);
			state = SETTING_SCREEN;
			return;
		}
		if(elapsedTime >= duration && state == SETTING_SCREEN){
			AndroidGameGlobal.evtMgr.queueEvent(
					new ScreenFinishedEvent(new GameScreen(world)));
//			AndroidGameGlobal.evtMgr.removeListener(this);
			state = FINISHED;
			return;
		}
		if(state == FINISHED){
			return;
		}
	
		

	}

	@Override
	public void handleEvent(Object e) {
		// TODO Auto-generated method stub
		
	}

}
