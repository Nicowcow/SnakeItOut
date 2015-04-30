/*
 *	Author:      Nicolas Mattia
 *	Date:        7 avr. 2012
 */

package com.cowlabs.games.snakeitout;

import javax.microedition.khronos.opengles.GL10;

import android.view.KeyEvent;

import com.cowlabs.games.snakeitout.framework.gl.Camera2D;
import com.cowlabs.games.snakeitout.framework.gl.SpriteBatcher;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGame;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;
import com.cowlabs.games.snakeitout.framework.impl.GLScreen;
import com.cowlabs.games.snakeitout.framework.impl.ScreenFinishedEvent;
import com.cowlabs.games.snakeitout.framework.impl.TouchEvent;
import com.cowlabs.games.snakeitout.framework.math.Rectangle;
import com.cowlabs.games.snakeitout.framework.math.Vector2;

public class SettingsScreen extends GLScreen{
	
	Camera2D guiCam;
	SpriteBatcher batcher;
//	Vector2 touchPoint;
	
	Rectangle soundBounds;
	Rectangle vibrBounds;
	Rectangle backBounds;
	Rectangle dpadBounds;
	Rectangle joystickBounds;
	Rectangle accelerometerBounds;
	Rectangle plusSpeedBounds;
	Rectangle minusSpeedBounds;
	
	private int background_x, background_y, background_w, background_h;
	private int sound_x, sound_y, sound_w, sound_h;
	private int vibr_x, vibr_y, vibr_w, vibr_h;
	private int dpad_x, dpad_y, dpad_w, dpad_h;
	private int joystick_x, joystick_y, joystick_w, joystick_h;
	private int back_x, back_y, back_w, back_h;
	private int back_text_x, back_text_y, back_text_h;
	
	private int forth_x, forth_y, forth_w, forth_h;
//	private int forth_text_x, forth_text_y, forth_text_h;
	
	private int title_x, title_y, title_w, title_h;
	private int title_text_x, title_text_y, title_text_h;
	
	private int minusSpeed_x, minusSpeed_y, minusSpeed_w, minusSpeed_h;
	private int plusSpeed_x, plusSpeed_y, plusSpeed_w, plusSpeed_h;
	private int speed_text_x, speed_text_y, speed_text_w, speed_text_h;
	private int speed_number_x, speed_number_y, speed_number_w, speed_number_h;
	
	private int game_m_x, game_m_y, game_m_h;
	

	public SettingsScreen() {
//		super(game);
		batcher = new SpriteBatcher(60);
		onDimensionsChanged();
	}
	
	@Override
	public void onDimensionsChanged() {
		
		int width = AndroidGameGlobal.glGraphics.getWidth();
		int height = AndroidGameGlobal.glGraphics.getHeight();
		
		background_x = width/2; background_y = height/2;
		if(width > 800){
			background_h = (int) ((float)480/(float)800 * width); background_w = width;
		}
		else{
			background_h = height; background_w = (int) ((float)800 / (float)480 * height); 
		}
		
		
		
		back_h = (int)(background_h / 10f); back_w = 2 * back_h;
		back_x = back_w / 2; back_y = height - back_h/2;
		
		back_text_h = back_h /2;
		back_text_x = (back_w + back_w / 8)/2; back_text_y = back_y;
		
		forth_w = back_w; forth_h = back_h;
		forth_x = width - forth_w / 2; forth_y = height - forth_h /2;
		
//		forth_text_h = back_text_h;
//		forth_text_x = width - (forth_w - forth_w / 8)/2; forth_text_y = forth_y;
		
		title_w = width - ( back_w + forth_w) - width/200; title_h = back_h;
		title_x = width/2; title_y = height - title_h / 2;
		
		title_text_h = title_h / 2;
		title_text_x = title_x ; title_text_y = title_y;
		
		back_text_h = back_h /2;
		back_text_x = (back_w + back_w / 8)/2; back_text_y = back_y;
		
		sound_h = (int) (background_h / 6f); sound_w = sound_h;
		sound_x = width/2 + width/6; sound_y = height * 3 / 4;
		
		vibr_h = (int)(background_h/6f); vibr_w = vibr_h;
		vibr_x = width/2 + 2*width/6 ; vibr_y = sound_y;
		
		game_m_x = width/4; game_m_y = back_y - back_h; game_m_h = height/20;
		
		dpad_h = (height - back_h - height/200) / 3; dpad_w = 2 * dpad_h;
		dpad_x = game_m_x; dpad_y = game_m_y - game_m_h/2 - dpad_h/2 - height/20;
		
		joystick_h = dpad_h; joystick_w = dpad_w;
		joystick_x = dpad_x; joystick_y = dpad_y - dpad_h/2 - joystick_h / 2 - height/20;
		
		
		
		
		plusSpeed_h = minusSpeed_h = speed_number_h = vibr_h;
		plusSpeed_w = plusSpeed_h; minusSpeed_w = minusSpeed_h;
		speed_number_w = speed_number_h * 32 / 40;
		speed_text_h =  minusSpeed_h / 4;
		speed_text_w = 11 * 32 * speed_text_h / 40;
		int tot_w = speed_text_w + plusSpeed_w + minusSpeed_w + speed_number_w; int tot_x = 3 * width/4;
		speed_text_x = tot_x - tot_w/2 + speed_text_w / 2;
		minusSpeed_x = speed_text_x + speed_text_w/2 + minusSpeed_w/2;
		speed_number_x = tot_x + tot_w/2 - plusSpeed_w - speed_number_w/2;
		plusSpeed_x = tot_x + tot_w/2 - plusSpeed_w/2;
		speed_text_y = speed_number_y = minusSpeed_y = plusSpeed_y = height/2;

		guiCam = new Camera2D(glGraphics, width, height);
		soundBounds = new Rectangle(sound_x, sound_y, sound_w, sound_h);
		vibrBounds = new Rectangle(vibr_x, vibr_y, vibr_w, vibr_h);
		dpadBounds = new Rectangle(dpad_x, dpad_y, dpad_w, dpad_h);
		joystickBounds = new Rectangle(joystick_x, joystick_y, joystick_w, joystick_h);
		backBounds = new Rectangle(back_x, back_y, back_w, back_h);
		minusSpeedBounds = new Rectangle(minusSpeed_x, minusSpeed_y, minusSpeed_w, minusSpeed_h);
		plusSpeedBounds = new Rectangle(plusSpeed_x, plusSpeed_y, plusSpeed_w, plusSpeed_h);

	}


	@Override
	public void update(float deltaTime) {}

	

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();

		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(Assets.background);
		batcher.drawSprite(background_x, background_y, background_w, background_h, Assets.backgroundRegion);
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(sound_x, sound_y, sound_w, sound_h, Settings.soundEnabled?Assets.soundEnabledRegion:Assets.soundDisabledRegion);
		batcher.drawSprite(vibr_x, vibr_y, vibr_w, vibr_h, Settings.vibrEnabled?Assets.vibrEnabledRegion:Assets.vibrDisabledRegion);
		batcher.drawSprite(back_x, back_y, back_w, back_h, Assets.backRegion);
		batcher.drawSprite(forth_x, forth_y, forth_w, forth_h, Assets.blueRegion);
		batcher.drawSprite(title_x, title_y, title_w, title_h, Assets.blueRegion);
		Assets.font.drawText(batcher, "settings", title_text_x, title_text_y, title_text_h,  true);
		Assets.font.drawText(batcher, "back", back_text_x, back_text_y, back_text_h, true);
		batcher.drawSprite(plusSpeed_x, plusSpeed_y, plusSpeed_w, plusSpeed_h, Assets.nextRegion);
		batcher.drawSprite(minusSpeed_x, minusSpeed_y, minusSpeed_w, minusSpeed_h, Assets.prevRegion);
		Assets.font.drawText(batcher, "rot. speed:", speed_text_x, speed_text_y, speed_text_h,true);
		Assets.font.drawText(batcher,Settings.cubeSpeed + "", speed_number_x, speed_number_y, speed_number_h, false);
		Assets.font.drawText(batcher, "game mode:", game_m_x, game_m_y,game_m_h,true);
		batcher.endBatch();
		
		batcher.beginBatch(Assets.gameInputs);
		batcher.drawSprite(dpad_x, dpad_y, dpad_w, dpad_h, Settings.gameInput == Settings.DPAD?Assets.dpadEnabledRegion:Assets.dpadDisabledRegion);
		batcher.drawSprite(joystick_x, joystick_y, joystick_w, joystick_h, Settings.gameInput == Settings.JOYSTICK?Assets.joystickEnabledRegion:Assets.joystickDisabledRegion);
		batcher.endBatch();


		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Jukebox.actionHappened(Jukebox.CLICK);
			AndroidGameGlobal.vibrator.vibrate(20);
			AndroidGameGlobal.evtMgr.queueEvent(
					new ScreenFinishedEvent(new MainMenuScreen()));
			return true;
		}
		return false;
	}

	@Override
	public void handleEvent(Object event) {
		
		if(event instanceof TouchEvent){
		
			if(((TouchEvent) event).getType() != TouchEvent.TouchType.TOUCH_UP)
				return;
			
			Vector2 touchPoint = ((TouchEvent) event).getTouchPoint();
			guiCam.touchToWorld(touchPoint);
			
			if (vibrBounds.overlap(touchPoint)) {
				Jukebox.actionHappened(Jukebox.CLICK);
				Settings.vibrEnabled = !Settings.vibrEnabled;
				Settings.save();
				AndroidGameGlobal.vibrator.vibrate(20);
			}
			else if (soundBounds.overlap(touchPoint)) {
				Settings.soundEnabled = !Settings.soundEnabled;
				Settings.save();
				Jukebox.setVolume(Settings.soundEnabled?1:0);
				Jukebox.actionHappened(Jukebox.CLICK);
				AndroidGameGlobal.vibrator.vibrate(20);
			}
			else if (backBounds.overlap(touchPoint)) {
				Jukebox.actionHappened(Jukebox.CLICK);
				AndroidGameGlobal.vibrator.vibrate(20);
				AndroidGameGlobal.evtMgr.queueEvent(
						new ScreenFinishedEvent(new MainMenuScreen()));
			}
			
			else if (dpadBounds.overlap(touchPoint)){
				Jukebox.actionHappened(Jukebox.CLICK);
				AndroidGameGlobal.vibrator.vibrate(20);
				Settings.gameInput = Settings.DPAD;
				Settings.save();
			}
			
			else if (joystickBounds.overlap(touchPoint)){
				Jukebox.actionHappened(Jukebox.CLICK);
				AndroidGameGlobal.vibrator.vibrate(20);
				Settings.gameInput = Settings.JOYSTICK;
				Settings.save();
			}
			
			else if(minusSpeedBounds.overlap(touchPoint)){
				Jukebox.actionHappened(Jukebox.CLICK);
				AndroidGameGlobal.vibrator.vibrate(20);
				if(Settings.cubeSpeed > 1){
					--Settings.cubeSpeed;
					Settings.save();
				}
			}
			
			else if(plusSpeedBounds.overlap(touchPoint)){
				Jukebox.actionHappened(Jukebox.CLICK);
				AndroidGameGlobal.vibrator.vibrate(20);
				if(Settings.cubeSpeed < 9){
					++Settings.cubeSpeed;
					Settings.save();
				}
			}
		}

		
	}

}
