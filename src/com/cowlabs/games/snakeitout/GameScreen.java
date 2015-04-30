/*
 *	Author:      Nicolas Mattia
 *	Date:        12 avr. 2012
 */

package com.cowlabs.games.snakeitout;


import javax.microedition.khronos.opengles.GL10;


import android.view.KeyEvent;

import com.cowlabs.games.snakeitout.World.WorldListener;
import com.cowlabs.games.snakeitout.framework.Score;
//import com.cowlabs.games.snakeitout.framework.Input.TouchEvent;
import com.cowlabs.games.snakeitout.framework.gl.Camera2D;
import com.cowlabs.games.snakeitout.framework.gl.SpriteBatcher;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;
import com.cowlabs.games.snakeitout.framework.impl.GLScreen;
import com.cowlabs.games.snakeitout.framework.math.Circle;
import com.cowlabs.games.snakeitout.framework.math.CubeMovingPoint;
import com.cowlabs.games.snakeitout.framework.math.Quaternion;
import com.cowlabs.games.snakeitout.framework.math.Rectangle;
import com.cowlabs.games.snakeitout.framework.math.Vector2;

public class GameScreen extends GLScreen {
	
	static final int GAME_WAITING_FOR_PLAYER = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_WAIT_FOR_THE_BREAK = 3;
	static final int GAME_OVER = 4;
	
	int state;
	
	Score score;
	
	Camera2D guiCam;
	Vector2 touchPoint;
	SpriteBatcher batcher;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	
	Circle dpadplusBounds;
	Rectangle[] dpadBounds = new Rectangle[4];
	Circle joystickBounds;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	Rectangle setupBounds;
	Rectangle againBounds;
	Rectangle centerBounds;
	
	
	private int background_x, background_y, background_w, background_h;
	private int dpad_x, dpad_y, dpad_w, dpad_h;
	private int dpadplus_x, dpadplus_y, dpadplus_w, dpadplus_h;
	private int joystick_x, joystick_y, joystick_w, joystick_h;
	private int pause_x, pause_y, pause_w, pause_h;

	
	private int resume_x, resume_y, resume_w, resume_h;
	private int resume_text_x, resume_text_y, resume_text_h;
	private int quit_x, quit_y, quit_w, quit_h;
	private int quit_text_x, quit_text_y, quit_text_h;
	
	private int ready_text_x, ready_text_y, ready_text_h;
	private int touch_text_x, touch_text_y, touch_text_h;
	
	private int score_x, score_y, score_h;
	
//	private int joystickPointer = -1;
	
	private int center_x,center_y;
	private int wait_for_text_h;
	
	private int game_over_x, game_over_y, game_over_w, game_over_h;
	private int game_over_info_x, game_over_info_y, game_over_info_h;
	private int game_over_again_x, game_over_again_y, game_over_again_w, game_over_again_h;
	private int game_over_setup_x, game_over_setup_y, game_over_setup_w, game_over_setup_h;
	private int game_over_again_t_x, game_over_again_t_y, game_over_again_t_h;
	private int game_over_setup_t_x, game_over_setup_t_y, game_over_setup_t_h;
	private int game_over_text_x, game_over_text_y, game_over_text_h;
	private int game_over_score_x, game_over_score_y, game_over_score_h;
	
	
	private float timeSinceOver;
//	private float time;

	
	String scoreString;
//	FPSCounter fpsCounter;

	public GameScreen(final World world) {
//		super(game);
		
		score = AndroidGameGlobal.scores;
		
		state = GAME_WAITING_FOR_PLAYER;
		batcher = new SpriteBatcher(100);
		touchPoint = new Vector2();
		onDimensionsChanged();
		
		this.world = world;
		worldListener = new WorldListener(){

			@Override
			public void appleAte() {
				Jukebox.actionHappened(Jukebox.APPLE_ATE);
			}
			@Override
			public void snakeExplodes() {
				Jukebox.actionHappened(Jukebox.SNAKE_DIES);
			}
			@Override
			public void teleport(int face) {
				world.rotationQuaternion.startSlerp(getQuatByFace(face), 0.3f);
				Jukebox.actionHappened(Jukebox.SNAKE_TELEPORTS);
			}
			
			@Override
			public void gameOver() {
				state = GAME_OVER;
				timeSinceOver = 0;
				Jukebox.actionHappened(Jukebox.GAME_OVER);
				score.saveScore(world.score, world.getMap().mapName);
				
			}
		};
		
		world.setWorldListener(worldListener);
		renderer = new WorldRenderer(glGraphics);
//		fpsCounter = new FPSCounter();
		Jukebox.actionHappened(Jukebox.GAME_SCREEN_READY);
	}
	
	public Quaternion getQuatByFace(int face){
		Quaternion newQuat = Quaternion.getIdentity();
		switch(face){
		case CubeMovingPoint.TOP:
			newQuat.rotate((float) (Math.PI/2),0);
			break;
		case CubeMovingPoint.BACK:
			newQuat.rotate((float) Math.PI,0);
			break;
		case CubeMovingPoint.BOTTOM:
			newQuat.rotate((float) (-Math.PI/2),0);
			break;
		case CubeMovingPoint.RIGHT:
			newQuat.rotate(0,-(float)Math.PI/2);
			break;
		case CubeMovingPoint.LEFT:
			newQuat.rotate(0,(float)Math.PI/2);
			break;
		}
		
		return newQuat;
	}
	
	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		guiCam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		batcher.beginBatch(Assets.gameBackground);
		batcher.drawSprite(background_x, background_y, background_w, background_h, Assets.gameBackgroundRegion);
		batcher.endBatch();
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
		
		renderer.render(world, deltaTime);
		
		switch (state) {
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_OVER:
			presentGameOver(deltaTime);
			break;
			
		case GAME_WAITING_FOR_PLAYER:
			presentWaitingForPlayer();
			break;
			
		case GAME_WAIT_FOR_THE_BREAK:
			presentWaitForTheBreak();
			break;
		}
		
//		fpsCounter.logFrame();
	}
	
	@Override
	public void update(float deltaTime) {

		switch (state) {
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_OVER:
			updateGameOver(deltaTime);
			break;
			
		case GAME_WAITING_FOR_PLAYER:
			updateWaitingForPlayer();
			break;
			
		case GAME_WAIT_FOR_THE_BREAK:
			updateWaitForTheBreak();
		}
		
		Jukebox.notifyDeltaTime(deltaTime);
	}
	
	private void updatePaused() {
//		List<TouchEvent> events = AndroidGameGlobal.input.getTouchEvents();
//		int len = events.size();
//		for (int i = 0; i < len; i++) {
//			TouchEvent event = events.get(i);
//			if (event.type != TouchEvent.TOUCH_UP)
//				continue;
//
//			guiCam.touchToWorld(touchPoint.set(event.x, event.y));
//			if (resumeBounds.overlap(touchPoint)) {
//				Jukebox.actionHappened(Jukebox.CLICK);
//				Jukebox.actionHappened(Jukebox.GAME_RESUME);
//				AndroidGameGlobal.vibrator.vibrate(20);
//				state = GAME_WAIT_FOR_THE_BREAK;
//				continue;
//			}
//			if(quitBounds.overlap(touchPoint)){
//				Jukebox.actionHappened(Jukebox.CLICK);
//				AndroidGameGlobal.vibrator.vibrate(20);
//				Jukebox.actionHappened(Jukebox.GAME_QUIT);
//				game.setScreen(new SetupScreen(game));
//			}
//
//			
//		}
	}
	
	private void updateRunning(float deltaTime) {
//		List<TouchEvent> events = AndroidGameGlobal.input.getTouchEvents();
//		int len = events.size();
//		for (int i = 0; i < len; i++) {
//			TouchEvent event = events.get(i);
//			guiCam.touchToWorld(touchPoint.set(event.x, event.y));
//			
//			switch(event.type){
//			case TouchEvent.TOUCH_DOWN:
//				switch(Settings.gameInput){
//				case Settings.ACCELEROMETER:
//					break;
//					
//				case Settings.DPAD:
//					if(!testDpadPlus() && !testDpad() && centerBounds.overlap(touchPoint)){
//						world.rotationQuaternion.startSlerp(this.getQuatByFace(world.snake.body.get(0).position.face), 0.3f);
//					}
//					break;
//					
//				case Settings.JOYSTICK:
//					if(joystickPointer == -1 && joystickBounds.overlap(touchPoint))
//						joystickPointer = event.pointer;					
//					else {
//						if(!testDpadPlus() && centerBounds.overlap(touchPoint)){
//							world.rotationQuaternion.startSlerp(this.getQuatByFace(world.snake.body.get(0).position.face), 0.3f);
//						}
//					}
//					break;
//				}
//				
//			case TouchEvent.TOUCH_DRAGGED:
//				if(Settings.gameInput == Settings.JOYSTICK && joystickPointer == event.pointer && joystickBounds.overlap(touchPoint)){
//					world.rotationQuaternion.setDeltaAngles(Settings.cubeSpeed * (touchPoint.y - joystick_y)/joystickBounds.radius,
//																Settings.cubeSpeed * (- touchPoint.x + joystick_x)/joystickBounds.radius);
//				}
//				
//				break;
//				
//			case TouchEvent.TOUCH_UP:
//				if(Settings.gameInput == Settings.JOYSTICK && event.pointer == joystickPointer){
//					joystickPointer = -1;
//					world.rotationQuaternion.setDeltaAngles(0, 0);
//					continue;
//				}
//				if(pauseBounds.overlap(touchPoint)){
//					AndroidGameGlobal.vibrator.vibrate(20);
//					state = GAME_PAUSED;
//					Jukebox.actionHappened(Jukebox.GAME_PAUSE);
//					continue;
//				}
//				break;
//			}	
//		}

		world.update(deltaTime);
	}
	
//	private boolean testDpad(){
//		if(dpadBounds[CubeMovingPoint.NORTH].overlap(touchPoint)){
//			Quaternion destQuat = Quaternion.getClone(world.rotationQuaternion);
//			destQuat.rotate((float) (Math.PI/2), 0);
//			world.rotationQuaternion.startSlerp(destQuat, 0.5f);
//			AndroidGameGlobal.vibrator.vibrate(10);
//			return true;
//		} else if(dpadBounds[CubeMovingPoint.SOUTH].overlap(touchPoint)){
//			Quaternion destQuat = Quaternion.getClone(world.rotationQuaternion);
//			destQuat.rotate(-(float) (Math.PI/2), 0);
//			world.rotationQuaternion.startSlerp(destQuat, 0.5f);
//			AndroidGameGlobal.vibrator.vibrate(10);
//			return true;
//		} else if(dpadBounds[CubeMovingPoint.EAST].overlap(touchPoint)){
//			Quaternion destQuat = Quaternion.getClone(world.rotationQuaternion);
//			destQuat.rotate(0,-(float) (Math.PI/2));
//			world.rotationQuaternion.startSlerp(destQuat, 0.5f);
//			AndroidGameGlobal.vibrator.vibrate(10);
//			return true;
//		} else if(dpadBounds[CubeMovingPoint.WEST].overlap(touchPoint)){
//			Quaternion destQuat = Quaternion.getClone(world.rotationQuaternion);
//			destQuat.rotate(0,(float) (Math.PI/2));
//			world.rotationQuaternion.startSlerp(destQuat, 0.5f);
//			AndroidGameGlobal.vibrator.vibrate(10);
//			return true;
//		}
//		
//		return false;
//	}
	
//	private boolean testDpadPlus(){
//		if(dpadplusBounds.overlap(touchPoint)){
//			world.newSnakeDirection((float) Math.atan2(touchPoint.y - dpadplus_y, touchPoint.x - dpadplus_x));
//			AndroidGameGlobal.vibrator.vibrate(10);
//			return true;
//		}
//		return false;
//	}
	
	private void updateGameOver(float deltaTime){
		world.rotationQuaternion.setDeltaAngles(0.5f, 0.5f);
		world.update(deltaTime);
//		
//		List<TouchEvent> events = AndroidGameGlobal.input.getTouchEvents();
//		int len = events.size();
//		for (int i = 0; i < len; i++) {
//			TouchEvent event = events.get(i);
//			if (event.type != TouchEvent.TOUCH_UP)
//				continue;
//
//			guiCam.touchToWorld(touchPoint.set(event.x, event.y));
//			if (againBounds.overlap(touchPoint) && timeSinceOver >= 1f) {
//				Jukebox.actionHappened(Jukebox.CLICK);
//				AndroidGameGlobal.vibrator.vibrate(20);
//				Jukebox.resetSuperState();
//				game.setScreen(new LoadingScreen(game, world.getMap(), Settings.snakeSpeed));
//				continue;
//			}
//			if(setupBounds.overlap(touchPoint) && timeSinceOver >= 1f){
//				Jukebox.actionHappened(Jukebox.CLICK);
//				AndroidGameGlobal.vibrator.vibrate(20);
//				Jukebox.actionHappened(Jukebox.GAME_QUIT);
//				game.setScreen(new SetupScreen(game));
//			}

			
//		}

	}
	
	private void updateWaitingForPlayer(){
//		List<TouchEvent> events = AndroidGameGlobal.input.getTouchEvents();
//		int len = events.size();
//		for (int i = 0; i < len; i++) {
//			TouchEvent event = events.get(i);
//			if (event.type == TouchEvent.TOUCH_UP){
//				state = GAME_RUNNING;
//				Jukebox.actionHappened(Jukebox.GAME_STARTED);
//			}
//		}
	}
	
	private void updateWaitForTheBreak(){
		if(Jukebox.getState() == 1)
			this.state = GAME_RUNNING;
	}

	private void presentWaitForTheBreak(){
		GL10 gl = glGraphics.getGL();
		guiCam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.items);
		Assets.font.drawText(batcher, "wait for the break...", center_x, center_y,wait_for_text_h,true );
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
		
	}
	
	private void presentRunning(){
		GL10 gl = glGraphics.getGL();
		guiCam.setViewportAndMatrices();
		
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		if(Settings.gameInput != Settings.ACCELEROMETER){
			batcher.drawSprite(dpadplus_x, dpadplus_y, dpadplus_w, dpadplus_h, Assets.dpadplusRegion);
			if(Settings.gameInput == Settings.JOYSTICK)
				batcher.drawSprite(joystick_x, joystick_y, joystick_w, joystick_h, Assets.joystickRegion);
			else
				batcher.drawSprite(dpad_x, dpad_y, dpad_w, dpad_h, Assets.dpadRegion);
		}
		Assets.font.drawText(batcher, " score : " + world.score, score_x, score_y, score_h, false);
		batcher.drawSprite(pause_x, pause_y, pause_w, pause_h, Assets.pauseRegion);
		batcher.endBatch();
		
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}
	private void presentPaused(){
		
		GL10 gl = glGraphics.getGL();
		guiCam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(resume_x, resume_y, resume_w, resume_h, Assets.playRegion);
		batcher.drawSprite(quit_x, quit_y, quit_w, quit_h, Assets.playRegion);
		Assets.font.drawText(batcher, "resume", resume_text_x, resume_text_y, resume_text_h, true);
		Assets.font.drawText(batcher, "quit", quit_text_x, quit_text_y, quit_text_h, true);
		Assets.font.drawText(batcher, " score : " + world.score, score_x, score_y, score_h, false);
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}
	private void presentGameOver(float deltaTime){
		
		timeSinceOver += deltaTime;
		GL10 gl = glGraphics.getGL();
		guiCam.setViewportAndMatrices();
		
		

		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(game_over_info_x,game_over_info_y,game_over_width(timeSinceOver), game_over_info_h,Assets.blueRegion);
		batcher.endBatch();
		
		gl.glColor4f(1, 1, 1, game_over_alpha(timeSinceOver));
		batcher.beginBatch(Assets.items);
		
		Assets.font.drawText(batcher, " score : " + world.score,game_over_score_x, game_over_score_y, game_over_score_h, true);
		Assets.font.drawText(batcher, "game over", game_over_text_x, game_over_text_y,game_over_text_h,true);
		batcher.drawSprite(game_over_again_x,game_over_again_y,game_over_again_w, game_over_again_h,Assets.blueRegion);
		batcher.drawSprite(game_over_setup_x,game_over_setup_y,game_over_setup_w, game_over_setup_h,Assets.blueRegion);
		Assets.font.drawText(batcher, "setup", game_over_setup_t_x, game_over_setup_t_y,game_over_setup_t_h,true);
		Assets.font.drawText(batcher, "again", game_over_again_t_x, game_over_again_t_y,game_over_again_t_h,true);

		batcher.endBatch();
		gl.glColor4f(1, 1, 1, 1);
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
	}
	
	private float game_over_width(float timeSinceOver){
		if(timeSinceOver <= .5f){
			return timeSinceOver/(0.5f) * game_over_w;
		}
		else
			return game_over_w;
	}
	
	private float game_over_alpha(float timeSinceOver){
		if(timeSinceOver >= 0.6f && timeSinceOver <= 1.5f){
			return (timeSinceOver-0.6f) / 0.9f ;
		}
		else if (timeSinceOver < 0.6f){
			return 0f;
		}
		else
			return 1f;
	}
	private void presentWaitingForPlayer(){
		GL10 gl = glGraphics.getGL();
		guiCam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(background_x, background_y, background_w, background_h, Assets.blueRegion);
		Assets.font.drawText(batcher, "ready?", ready_text_x, ready_text_y, ready_text_h, true);
		Assets.font.drawText(batcher, "touch to begin", touch_text_x, touch_text_y, touch_text_h, true);
		
		batcher.endBatch();
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
		
		/* WAITINGS */
		
		ready_text_x = width/2; ready_text_h = height/10; ready_text_y = height/2 + ready_text_h;
		touch_text_x = width/2; touch_text_h = height/15; touch_text_y = height/2 - touch_text_h;
		
		/* RUNNING */
		dpad_y =  height/4; dpad_x =  dpad_y;
		dpad_w = dpad_h = 5*height/12;
		dpadplus_x = width - dpad_y; dpadplus_y = dpad_y;
		dpadplus_w = dpad_w; dpadplus_h = dpad_h;
		
		pause_w = pause_h = height / 10;
		pause_x = width - pause_w; pause_y = height - pause_h;
		pauseBounds = new Rectangle(pause_x, pause_y, pause_w, pause_h);

		
		score_x = 0; score_y = pause_y; score_h = pause_h * 3 /4;		
		dpadBounds[CubeMovingPoint.NORTH] = new Rectangle(dpad_x+ dpad_w / 3,dpad_y + 2*dpad_h/3, dpad_w/3, dpad_h/3 );
		dpadBounds[CubeMovingPoint.SOUTH] = new Rectangle(dpad_x+ dpad_w / 3,dpad_y, dpad_w/3, dpad_h/3 );
		dpadBounds[CubeMovingPoint.EAST] = new Rectangle(dpad_x+ 2*dpad_w / 3,dpad_y + dpad_h/3, dpad_w/3, dpad_h/3 );
		dpadBounds[CubeMovingPoint.WEST] = new Rectangle(dpad_x,dpad_y + dpad_h/3, dpad_w/3, dpad_h/3 );
		
		dpadplusBounds = new Circle(dpadplus_x, dpadplus_y,1.1f* dpadplus_w/2f);
		
		joystick_y = height/4; joystick_x = joystick_y;
		joystick_w = joystick_h = height/2;
		
		joystickBounds = new Circle(joystick_x, joystick_y, joystick_w/2);
		
		/* GAME OVER */
		int game_over_offset = height/100;
		game_over_x = width/2; game_over_y = height/2; game_over_w = 9 * width / 10; game_over_h = 9 * height / 10;
		game_over_info_x = game_over_x; game_over_info_h = 4 * game_over_h / 5; game_over_info_y = game_over_y + game_over_h/2 - game_over_info_h/2;
		game_over_text_x = width/2; game_over_text_h = height/8; game_over_text_y = height/2 + game_over_h / 4;
		game_over_score_x = width/2; game_over_score_y = game_over_text_y - game_over_text_h / 2 - height/10; game_over_score_h = score_h;
		game_over_setup_w = game_over_again_w = (game_over_w - game_over_offset)/2; game_over_setup_x = game_over_x + game_over_w/2 - game_over_setup_w / 2;
		game_over_again_x = game_over_x - game_over_w/2 + game_over_again_w/2; game_over_again_h = game_over_setup_h = game_over_h - game_over_info_h - game_over_offset/2;
		game_over_again_y = game_over_setup_y = game_over_y - game_over_h/2 + game_over_again_h/2;
		game_over_again_t_x = game_over_again_x; game_over_again_t_y = game_over_again_y; game_over_again_t_h = game_over_again_h / 3;
		game_over_setup_t_x = game_over_setup_x; game_over_setup_t_y = game_over_setup_y; game_over_setup_t_h = game_over_setup_h / 3;
		
		againBounds = new Rectangle(game_over_again_x, game_over_again_y, game_over_again_w, game_over_again_h);
		setupBounds = new Rectangle(game_over_setup_x , game_over_setup_y, game_over_setup_w, game_over_setup_h);
		centerBounds = new Rectangle(width/3, height/3,width/3, height/3);

		/* PAUSED */
		
		resume_h = (int) (background_h / 6f); resume_w = (int) (resume_h * (400f/120f));
		resume_x = width/2; resume_y = height/2 + resume_h/2;	
		
		resume_text_h = resume_h / 2; 
		resume_text_x = resume_x ; resume_text_y = resume_y;
		
		quit_w = resume_w; quit_h = resume_h; quit_x = resume_x;
		quit_y = (int) (resume_y - resume_h / 2f - background_h / 10f - quit_h / 2f);
		
		quit_text_h = 3 * resume_text_h / 4; quit_text_x = quit_x; quit_text_y = quit_y;
		
		resumeBounds = new Rectangle(resume_x, resume_y, resume_w, resume_h);
		quitBounds = new Rectangle(quit_x, quit_y, quit_w, quit_h);
		
		center_x = width/2; center_y = height/2;
		wait_for_text_h = height/15;
		
		guiCam = new Camera2D(glGraphics, width, height);
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU){
			if(state == GAME_RUNNING){
				AndroidGameGlobal.vibrator.vibrate(20);
				state = GAME_PAUSED;
				Jukebox.actionHappened(Jukebox.GAME_PAUSE);
				return true;
			}
			else if(state == GAME_PAUSED){
				Jukebox.actionHappened(Jukebox.CLICK);
				Jukebox.actionHappened(Jukebox.GAME_RESUME);
				AndroidGameGlobal.vibrator.vibrate(20);
				state = GAME_WAIT_FOR_THE_BREAK;
				return true;
			}
			
		}
		return false;
	}

	@Override
	public void handleEvent(Object e) {
		// TODO Auto-generated method stub
		
	}

	

}
