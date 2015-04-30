/*
 *	Author:      Nicolas Mattia
 *	Date:        8 avr. 2012
 */

package com.cowlabs.games.snakeitout;

import javax.microedition.khronos.opengles.GL10;

import android.view.KeyEvent;

import com.cowlabs.games.snakeitout.framework.gl.Camera2D;
import com.cowlabs.games.snakeitout.framework.gl.SpriteBatcher;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;
import com.cowlabs.games.snakeitout.framework.impl.GLScreen;
import com.cowlabs.games.snakeitout.framework.impl.ScreenFinishedEvent;
import com.cowlabs.games.snakeitout.framework.math.Rectangle;
import com.cowlabs.games.snakeitout.framework.math.Vector2;
import com.cowlabs.games.snakeitout.framework.Score;

public class SetupScreen extends GLScreen {
	
	
	Camera2D guiCam;
	SpriteBatcher batcher;
	Vector2 touchPoint;
	
	private static Score scores ;
	
	Rectangle backBounds;
	Rectangle forthBounds;
	Rectangle scrollBounds;
	Rectangle[] mapBounds;
	Rectangle plusSpeedBounds;
	Rectangle minusSpeedBounds;
	
	private int touch_start_y;
	
		
	private int background_x, background_y, background_w, background_h;
	
	private int back_x, back_y, back_w, back_h;
	private int back_text_x, back_text_y, back_text_h;
	
	private int forth_x, forth_y, forth_w, forth_h;
	private int forth_text_x, forth_text_y, forth_text_h;
	
	private int title_x, title_y, title_w, title_h;
	private int title_text_x, title_text_y, title_text_h;
	
	private int map_preview_offset, map_preview_w, map_preview_h;
	private int chosen_map_preview_x, chosen_map_preview_y, chosen_map_preview_w, chosen_map_preview_h;
	private int chosen_map_preview_border_w, chosen_map_preview_border_h;
	private int last_map_preview_y;
	private int[] map_preview_x, map_preview_y;
	private int map_preview_y_min, map_preview_y_max;
	
	private int scroll_x, scroll_y, scroll_w, scroll_h;
	
	private int new_y;
	
	private int map_name_x, map_name_y, map_name_h;
	private int map_size_x, map_size_y, map_size_h;
	private int map_highscore_x, map_highscore_y, map_highscore_h;
	
	private int minusSpeed_x, minusSpeed_y, minusSpeed_w, minusSpeed_h;
	private int plusSpeed_x, plusSpeed_y, plusSpeed_w, plusSpeed_h;
	private int speed_text_x, speed_text_y, speed_text_w, speed_text_h;
	private int speed_number_x, speed_number_y, speed_number_w, speed_number_h;
	
	private Map chosen_map = Map.maps[0];
	private int chosen_map_highscore = 41;

	public SetupScreen() {
//		super(game);
		
		scores = AndroidGameGlobal.scores;
		chosen_map_highscore = scores.readScore(0, chosen_map.mapName);
		batcher = new SpriteBatcher(100);
		touchPoint = new Vector2();
		onDimensionsChanged();
		Jukebox.actionHappened(Jukebox.SETUP_SCREEN_READY);
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
		
		forth_text_h = back_text_h;
		forth_text_x = width - (forth_w - forth_w / 8)/2; forth_text_y = forth_y;
		
		title_w = width - ( back_w + forth_w) - width/200; title_h = back_h;
		title_x = width/2; title_y = height - title_h / 2;
		
		title_text_h = title_h / 2;
		title_text_x = title_x ; title_text_y = title_y;
		
		scroll_w = width/2;
		
		map_preview_w = map_preview_h = 2*scroll_w /5;
		map_preview_offset = (scroll_w - 2 * map_preview_w)/2;
		map_preview_x = new int[Map.maps.length]; map_preview_y = new int[Map.maps.length];
		last_map_preview_y = height - title_h - map_preview_offset - map_preview_h/2;
		map_preview_x[0] = width - map_preview_offset - map_preview_offset/2 - 2 * map_preview_w + map_preview_w / 2; map_preview_y[0] = last_map_preview_y;
		
		map_preview_y_min = map_preview_y[0]; map_preview_y_max = map_preview_y[0] + (Map.maps.length - 3)/2 * (map_preview_h + map_preview_offset);
		scroll_h = (height - title_h - map_preview_offset);
		scroll_x = 3 * width / 4; scroll_y = (height - title_h)/2;
		
		
		chosen_map_preview_x = width/4; chosen_map_preview_h = 2*height/5; 
		chosen_map_preview_y = height - title_h - map_preview_offset/2 - chosen_map_preview_h / 2; chosen_map_preview_w = chosen_map_preview_h;
		chosen_map_preview_border_w = chosen_map_preview_border_h = chosen_map_preview_w + map_preview_offset/4;
		
		map_name_x = width/4; map_name_y = chosen_map_preview_y - chosen_map_preview_h / 2 - map_preview_offset; map_name_h = title_text_h;
		map_size_x = map_name_x; map_size_y = map_name_y - width/20; map_size_h = map_name_h;
		
		
		plusSpeed_h = minusSpeed_h = speed_number_h = 2*map_name_h;
		plusSpeed_w = plusSpeed_h; minusSpeed_w = minusSpeed_h;
		speed_number_w = speed_number_h * 32 / 40;
		speed_text_h =  map_name_h ;
		speed_text_w = 7 * 32 * speed_text_h / 40;
		int tot_w = speed_text_w + plusSpeed_w + minusSpeed_w + speed_number_w; int tot_x = width/4;
		speed_text_x = tot_x - tot_w/2 + speed_text_w / 2;
		minusSpeed_x = speed_text_x + speed_text_w/2 + minusSpeed_w/2;
		speed_number_x = tot_x + tot_w/2 - plusSpeed_w - speed_number_w/2;
		plusSpeed_x = tot_x + tot_w/2 - plusSpeed_w/2;
		speed_text_y = speed_number_y = minusSpeed_y = plusSpeed_y = map_size_y - map_size_h - plusSpeed_h/2;
		
		map_highscore_x = map_size_x; map_highscore_y = speed_text_y - width/20; map_highscore_h = map_size_h;

		
		guiCam = new Camera2D(glGraphics, width, height);
		backBounds = new Rectangle(back_x, back_y, back_w, back_h);
		forthBounds = new Rectangle(forth_x, forth_y, forth_w, forth_h);
		scrollBounds = new Rectangle(scroll_x, scroll_y, scroll_w, scroll_h);
		mapBounds = new Rectangle[Map.maps.length];
		mapBounds[0] = new Rectangle(map_preview_x[0], map_preview_y[0], map_preview_w, map_preview_h);
		minusSpeedBounds = new Rectangle(minusSpeed_x, minusSpeed_y, minusSpeed_w, minusSpeed_h);
		plusSpeedBounds = new Rectangle(plusSpeed_x, plusSpeed_y, plusSpeed_w, plusSpeed_h);
		
		
		for(int i = 1; i < mapBounds.length; i++){
			mapBounds[i] = new Rectangle(0, 0, map_preview_w, map_preview_h);
		}
		
		updateMapPreviewRelative();

	}
	
	
	
	@Override
	public void update(float deltaTime) {
//		List<TouchEvent> events = AndroidGameGlobal.input.getTouchEvents();
//		int len = events.size();
//		for (int i = 0; i < len; i++) {
//			TouchEvent event = events.get(i);
//			
//			guiCam.touchToWorld(touchPoint.set(event.x, event.y));
//			
//			if(event.type == TouchEvent.TOUCH_DOWN){
//				touch_start_y = event.y;
//				continue;
//			}
//
//			if (event.type == TouchEvent.TOUCH_UP){
//				last_map_preview_y = map_preview_y[0];
//				if(Math.abs(event.y - touch_start_y) < AndroidGameGlobal.glGraphics.getHeight()/10){
//					if (backBounds.overlap(touchPoint)) {
//						Jukebox.actionHappened(Jukebox.CLICK);
//						AndroidGameGlobal.vibrator.vibrate(20);
//						game.setScreen(new MainMenuScreen(game));
//					}
//					if(forthBounds.overlap(touchPoint)){
//						Jukebox.actionHappened(Jukebox.CLICK);
//						AndroidGameGlobal.vibrator.vibrate(20);
//						game.setScreen(new LoadingScreen(game, chosen_map, Settings.snakeSpeed));
//						continue;
//					}
//					
//					if(minusSpeedBounds.overlap(touchPoint)){
//						Jukebox.actionHappened(Jukebox.CLICK);
//						AndroidGameGlobal.vibrator.vibrate(20);
//						if(Settings.snakeSpeed > 1){
//							--Settings.snakeSpeed;
//							Settings.save();
//						}
//						continue;
//					}
//					
//					if(plusSpeedBounds.overlap(touchPoint)){
//						Jukebox.actionHappened(Jukebox.CLICK);
//						AndroidGameGlobal.vibrator.vibrate(20);
//						if(Settings.snakeSpeed < 9){
//							++Settings.snakeSpeed;
//							Settings.save();
//						}
//						continue;
//					}
//					
//					for(int j = 0; j< Map.maps.length; j++){
//						if(mapBounds[j].overlap(touchPoint)){
//							Jukebox.actionHappened(Jukebox.CLICK);
//							AndroidGameGlobal.vibrator.vibrate(20);
//							chosen_map = Map.maps[j];
//							chosen_map_highscore = scores.readScore(0, chosen_map.mapName);
//						}
//					}
//				
//				}
//				
//				continue;
//			}
//			
//			if(event.type == TouchEvent.TOUCH_DRAGGED){
//				if (scrollBounds.overlap(touchPoint)){
//					new_y = last_map_preview_y - ( event.y - touch_start_y);
//					if(new_y >= map_preview_y_max)
//						map_preview_y[0] = map_preview_y_max ;
//					else if(new_y <= map_preview_y_min)
//						map_preview_y[0] = map_preview_y_min;
//					else
//						map_preview_y[0] = new_y;
//						
//					
//					updateMapPreviewRelative();
//				}
//			}
//		}

	}
	
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
		batcher.drawSprite(scroll_x, scroll_y, scroll_w, scroll_h, Assets.blueRegion);
		batcher.drawSprite(chosen_map_preview_x, chosen_map_preview_y, chosen_map_preview_border_w, chosen_map_preview_border_h, Assets.blueRegion);
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
		

		for(int i = 0; i<Map.maps.length; i++){
			
			batcher.beginBatch(Map.maps[i].preview);
			batcher.drawSprite(map_preview_x[i], map_preview_y[i], map_preview_w, map_preview_h, Map.maps[i].previewRegion);
			if(Map.maps[i] == chosen_map){
				batcher.drawSprite(chosen_map_preview_x, chosen_map_preview_y, chosen_map_preview_w, chosen_map_preview_h, chosen_map.previewRegion);
			}
			batcher.endBatch();
			
			
			
		}
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.mapMask8);
		for(int i = 0; i<Map.maps.length; i++)
			batcher.drawSprite(map_preview_x[i], map_preview_y[i], map_preview_w, map_preview_h, Assets.mapMask8Region);
		batcher.drawSprite(chosen_map_preview_x, chosen_map_preview_y, chosen_map_preview_w, chosen_map_preview_h, Assets.mapMask8Region);
		batcher.endBatch();
		
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(back_x, back_y, back_w, back_h, Assets.backRegion);
		batcher.drawSprite(forth_x, forth_y, forth_w, forth_h, Assets.forthRegion);
		batcher.drawSprite(title_x, title_y, title_w, title_h, Assets.blueRegion);
		Assets.font.drawText(batcher, "setup", title_text_x, title_text_y, title_text_h,  true);
		Assets.font.drawText(batcher, "back", back_text_x, back_text_y, back_text_h, true);
		Assets.font.drawText(batcher, "go!", forth_text_x, forth_text_y, forth_text_h, true);
		Assets.font.drawText(batcher, "name : " + chosen_map.mapName, map_name_x, map_name_y, map_name_h, true);
		Assets.font.drawText(batcher, "size : " + chosen_map.size + "x" + chosen_map.size + "x" + chosen_map.size, map_size_x, map_size_y, map_size_h, true);
		Assets.font.drawText(batcher, "highscore : " + chosen_map_highscore, map_highscore_x, map_highscore_y, map_highscore_h,true);
		batcher.drawSprite(plusSpeed_x, plusSpeed_y, plusSpeed_w, plusSpeed_h, Assets.nextRegion);
		batcher.drawSprite(minusSpeed_x, minusSpeed_y, minusSpeed_w, minusSpeed_h, Assets.prevRegion);
		Assets.font.drawText(batcher, "speed :", speed_text_x, speed_text_y, speed_text_h,true);
		Assets.font.drawText(batcher,Settings.snakeSpeed + "", speed_number_x, speed_number_y, speed_number_h, false);
		batcher.endBatch();


		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}
	
	
	private void updateMapPreviewRelative(){
		
		mapBounds[0].getCenter().set(map_preview_x[0], map_preview_y[0]);
		
		for(int i =1; i < map_preview_x.length; i++){
			map_preview_x[i] = map_preview_x[0] + (map_preview_offset + map_preview_w)*(i%2);
			map_preview_y[i] = map_preview_y[0] - (map_preview_offset + map_preview_h)*(i/2);
			
			mapBounds[i].getCenter().set(map_preview_x[i], map_preview_y[i]);
			
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Jukebox.actionHappened(Jukebox.CLICK);
			AndroidGameGlobal.vibrator.vibrate(20);
			AndroidGameGlobal.evtMgr.queueEvent(
					new ScreenFinishedEvent(new MainMenuScreen()));
//			AndroidGameGlobal.evtMgr.removeListener(this);
			return true;
		}
		return false;
	}

	@Override
	public void handleEvent(Object e) {
		// TODO Auto-generated method stub
		
	}

	

}
