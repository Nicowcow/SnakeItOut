package com.cowlabs.games.snakeitout;


import javax.microedition.khronos.opengles.GL10;

import com.cowlabs.games.framework.visual.layout.Banner;
import com.cowlabs.games.framework.visual.layout.Button;
import com.cowlabs.games.framework.visual.layout.Screen;
import com.cowlabs.games.snakeitout.framework.gl.Camera2D;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;
import com.cowlabs.games.snakeitout.framework.impl.GLScreen;
import com.cowlabs.games.snakeitout.framework.impl.ScreenFinishedEvent;
import com.cowlabs.games.snakeitout.framework.impl.TouchEvent;
import com.cowlabs.games.snakeitout.framework.math.Vector2;

public class MainMenuScreen extends GLScreen {
		
	Camera2D guiCam;
	
	private Screen background = new Screen(Assets.background, Assets.backgroundRegion);
	
	private Banner logo = new Banner(Assets.logo, Assets.logoRegion);
	private Button play = new Button("gamescreen play",Assets.items, Assets.playRegion);
	private Button settings = new Button("gamescreen settings", Assets.items, Assets.settingsRegion);
	
	public MainMenuScreen() {
//		super(game);
		
		onDimensionsChanged();
		Jukebox.actionHappened(Jukebox.MAIN_MENU_SCREEN_READY);
	}
	
	
	@Override
	public void onDimensionsChanged() {	
		
		int width = AndroidGameGlobal.glGraphics.getWidth();
		int height = AndroidGameGlobal.glGraphics.getHeight();
		
		background.set(width, height);
		
			background.hang(logo, 0.25f, 0, 0.5f);
		
			background.hang(play, 0.2f, 0, -0.2f);
			
			background.hang(settings, 0.2f, 0, -0.7f);
			
		background.loadDisplayer();

		guiCam = new Camera2D(glGraphics, width, height);
	}
	
	@Override
	public void update(float deltaTime) {
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();

		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		background.present();

		gl.glDisable(GL10.GL_TEXTURE_2D);
	}


	@Override
	public void handleEvent(Object event) {
		if(event instanceof TouchEvent){
			if(((TouchEvent) event).getType() != TouchEvent.TouchType.TOUCH_UP)
				return ;
			
			Vector2 touchPoint = ((TouchEvent) event).getTouchPoint();
			guiCam.touchToWorld(touchPoint);
	
			if (play.isTouching(touchPoint)) {				
				Jukebox.actionHappened(Jukebox.CLICK);
				AndroidGameGlobal.vibrator.vibrate(20);
				AndroidGameGlobal.evtMgr.queueEvent(
						new ScreenFinishedEvent(new SetupScreen()));	
			}
			if (settings.isTouching(touchPoint)) {
				Jukebox.actionHappened(Jukebox.CLICK);
				AndroidGameGlobal.vibrator.vibrate(20);
				AndroidGameGlobal.evtMgr.queueEvent(
						new ScreenFinishedEvent(new SettingsScreen()));
			}
		}
		
	}
}

