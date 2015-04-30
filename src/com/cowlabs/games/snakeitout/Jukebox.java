/*
 *	Author:      Nicolas Mattia
 *	Date:        23 avr. 2012
 */

package com.cowlabs.games.snakeitout;

import java.util.ArrayList;
import java.util.List;

import com.cowlabs.games.snakeitout.framework.Music;
import com.cowlabs.games.snakeitout.framework.Sound;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;

public class Jukebox {
	
	private static float elapsedTime = 0;
	private static float songPlayTime = 0;
	
	/* HYPER STATES */
	public final static int APP_PAUSED = 18;
	public final static int APP_RESUMED = 19;
	public final static int APP_STOPPED = 20;
	public final static int APP_STARTED = 26;
	/***/
	
	/* SUPER STATES */
	public final static int GAME_AUDIO = 1;
	public final static int MENU_AUDIO = 0;
	/***/
	
	/* STATES */
	private final static int NULL = -1, A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6;
	/***/
	
	/* ACTIONS */
	public final static int APPLE_ATE = 10;
	public final static int SNAKE_DIES = 11;
	public final static int SNAKE_TELEPORTS = 12;
	public final static int CLICK = 13;
	
	public final static int GAME_SCREEN_READY = 14;
	public final static int SETTINGS_SCREEN_READY = 15;
	public final static int MAIN_MENU_SCREEN_READY = 16;
	public final static int SETUP_SCREEN_READY = 17;	
	public final static int LOADING_SCREEN_READY = 27;
	
	public final static int GAME_STARTED = 21;
	public final static int GAME_PAUSE = 22;
	public final static int GAME_RESUME = 23;
	public final static int GAME_QUIT = 25;
	public final static int GAME_OVER = 28;
	
	public final static int ON_COMPLETION = 24;
	/***/
	
	
	private static final List<Sound> loadedSounds = new ArrayList<Sound>();
	private static final List<Music> loadedMusics = new ArrayList<Music>();
	
	private static int superState = NULL;
	private static int state = NULL;
	private static int loopState = NULL;
	
	private static Music menuLoop = null;
	private static Music introLoop = null;
	private static Music loopBattery0 = null;
	private static Music loopBattery1 = null;
	private static Music loopBattery2 = null;
	private static Music waiting1 = null;
	private static Music waiting2 = null;
	private static Music granFinal = null;
	
	public static Sound clickSound = null;
	public static Sound destroyedSound = null;
	public static Sound appleAteSound = null;
	public static Sound teleportSound = null;
	
	
	private static float volume = Settings.soundEnabled?0.7f:0f;
	
	
	public static void setGame(){
		volume = Settings.soundEnabled?0.7f:0f;
	}
	
	public static void reload(){
		if(superState != GAME_AUDIO)
			loadMenuAudio();
		else
			loadGameAudio();
	}
	
	public static int getState(){
		return state;
	}
	
	public static void actionHappened(int action){
		if(action== CLICK)
			clickSound.play(volume);
		else if(action == APP_STARTED)
			setSuperState(MENU_AUDIO);
		else if(action == APP_PAUSED)
			pauseAll();
		else if(action == APP_STOPPED)
			disposeAll();
		else if(action == APP_RESUMED)
			resume();
		else if(superState == MENU_AUDIO)
			menuAction(action);
		else
			gameAction(action);
	}
	
	public static void resetSuperState(){
		Jukebox.superState = MENU_AUDIO;
	}
	
	private static void menuAction(int action){
		switch(action){
		case MAIN_MENU_SCREEN_READY:
		case SETTINGS_SCREEN_READY:
		case SETUP_SCREEN_READY:
			menuLoop.play();
			break;
			
		case LOADING_SCREEN_READY:
			setSuperState(GAME_AUDIO);
			
		}
	}
	
	private static void gameAction(int action){
		switch(action){
		case SNAKE_TELEPORTS:
			teleportSound.play(volume);
			break;
			
		case APPLE_ATE:
			appleAteSound.play(volume);
			break;
			
		case SNAKE_DIES:
			destroyedSound.play(volume);
		}
		switch(state){
		case NULL:
			if(action == GAME_SCREEN_READY){
				state = A;
				introLoop.play();
				loopBattery0.prepare();
			}
			break;
		case A:
			if(action == GAME_STARTED){
				introLoop.stop();
				notifyLoop();
				waiting1.prepare();
				state = B;
			}
			break;
			
		case B:
			if(action == GAME_PAUSE){
				waiting1.play();
				songPlayTime = waiting1.getPlayTime();
				pauseLoop();
				state = D;
			}
			else if(action == ON_COMPLETION)
				notifyLoop();
			else if(action == GAME_OVER){
				granFinal.play();
				pauseLoop();
				state = D;
			}
			break;
			
		case C:
			if(action == GAME_RESUME){
				waiting2.prepare();
				state = G;
			}
			else if(action == ON_COMPLETION){
				pauseLoop();
				songPlayTime = waiting1.getPlayTime();
				waiting1.play();
				state = D;
			}
			
			
			break;
			
		case D:
			if(action == GAME_RESUME){
				waiting2.prepare();
				state = E;
			}
			else if(action == GAME_QUIT){
				setSuperState(MENU_AUDIO);
			}
			
			break;
			
			
		case E:
			if(action == ON_COMPLETION){
				waiting1.stop();
				songPlayTime = waiting2.getPlayTime();
				waiting2.play();
				state = F;
			}
			break;
			
		case F:
			if(action == ON_COMPLETION){
				waiting2.stop();
				resumeLoop();
				state = B;
			}
			break;
			
		case G:
			if(action == ON_COMPLETION){
				loopBattery0.stop();
				songPlayTime = waiting2.getPlayTime();
				waiting2.play();
				state = F;
			}
			break;
		}
	}
	
	private static void pauseLoop(){
		switch(loopState){
		case A:
			loopBattery0.stop();	
			break;
		
		case B:
			loopBattery1.stop();
			break;
			
		case D:
			loopBattery2.stop();
			
		}
		
		loopBattery1.prepare();
		loopState = C;
		
	}
	
	private static void resumeLoop(){
		loopBattery1.play();
		songPlayTime = loopBattery1.getPlayTime();
		loopBattery2.prepare();
		loopState = B;
	}
	
	
	private static void notifyLoop(){
		switch(loopState){
		case NULL:
			loopBattery0.play();
			songPlayTime = loopBattery0.getPlayTime();
			loopBattery1.prepare();
			loopState = A;
			break;
			
		case A:
			loopBattery0.stop();
			loopBattery1.play();
			songPlayTime = loopBattery1.getPlayTime();
			loopBattery2.prepare();
			loopState = B;
			break;
			
		case B:
			loopBattery1.stop();
			loopBattery2.play();
			songPlayTime = loopBattery2.getPlayTime();
			loopState = D;
			break;
			
		case D:
			loopBattery2.backToBeginning();
			break;
		}
		
	}
	
	private static void setSuperState(int superState){
		if(superState != Jukebox.superState){
			state = NULL;
			disposeAll();
			songPlayTime = elapsedTime = 0;
			Jukebox.state = NULL;
			Jukebox.loopState = NULL;
			switch(superState){
			case MENU_AUDIO:
				loadMenuAudio();
				break;
				
			case GAME_AUDIO:
				loadGameAudio();
				break;
			}
			Jukebox.superState = superState;
		}
	}
	
	private static void loadMenuAudio(){
		
		clickSound = AndroidGameGlobal.audio.newSound("Sounds/click.ogg");

		
		menuLoop = AndroidGameGlobal.audio.newMusic("Music/loopbattery1.ogg");
		menuLoop.setLooping(true);
		menuLoop.setVolume(volume);
		
		loadedSounds.add(clickSound);
		loadedMusics.add(menuLoop);
	}
	
	private static void loadGameAudio(){
		
		
		clickSound = AndroidGameGlobal.audio.newSound("Sounds/click.ogg");
		teleportSound = AndroidGameGlobal.audio.newSound("Sounds/teleport.ogg");
		appleAteSound = AndroidGameGlobal.audio.newSound("Sounds/appleate.ogg");
		destroyedSound = AndroidGameGlobal.audio.newSound("Sounds/destroyed.ogg");
		
		loadedSounds.add(clickSound);
		loadedSounds.add(teleportSound);
		loadedSounds.add(appleAteSound);
		loadedSounds.add(destroyedSound);
		
		introLoop = AndroidGameGlobal.audio.newMusic("Music/introloop.ogg");
		introLoop.setLooping(true);
		introLoop.setVolume(volume);
		
		loopBattery0 = AndroidGameGlobal.audio.newMusic("Music/loopbattery0.ogg");
		loopBattery0.setLooping(true);
		loopBattery0.setVolume(volume);
		
		loopBattery1 = AndroidGameGlobal.audio.newMusic("Music/loopbattery1.ogg");
		loopBattery1.setLooping(true);
		loopBattery1.setVolume(volume);
		
		loopBattery2 = AndroidGameGlobal.audio.newMusic("Music/loopbattery2.ogg");
		loopBattery2.setLooping(true);
		loopBattery2.setVolume(volume);
		
		granFinal = AndroidGameGlobal.audio.newMusic("Music/granfinal.ogg");
		granFinal.setLooping(false);
		granFinal.setVolume(volume);
		
		
		
		waiting1 = AndroidGameGlobal.audio.newMusic("Music/waiting1.ogg");
		waiting1.setLooping(true);
		waiting1.setVolume(0.5f);
		
		waiting2 = AndroidGameGlobal.audio.newMusic("Music/waiting2.ogg");
		waiting2.setLooping(true);
		waiting2.setVolume(0.5f);
		
		loadedMusics.add(introLoop);
		loadedMusics.add(loopBattery0);
		loadedMusics.add(loopBattery1);
		loadedMusics.add(loopBattery2);
		loadedMusics.add(waiting1);
		loadedMusics.add(waiting2);
		loadedMusics.add(granFinal);
	}
	
	private static void resume(){
		int len = loadedMusics.size();
		for(int i = 0; i<len; i++){
			Music music;
			if((music = loadedMusics.get(i))!= null && !music.isStopped())
					music.play();
		}
	}
	
	private static void pauseAll(){
		int len = loadedMusics.size();
		for(int i = 0; i < len; i++){
			Music music= loadedMusics.get(i);
			if(music.isPlaying())
				music.pause();
		}
	}
	
	public static void setVolume(float volume){
		int len = loadedMusics.size();
		for(int i = 0; i<len; i++){
			Music music;
			if((music = loadedMusics.get(i))!= null){
				music.setVolume(volume);
			}
		}
		Jukebox.volume = volume;
	}

	private static void disposeAll(){
		int len = loadedSounds.size();
		for(int i = 0; i < len; i++){
			Sound sound;
			if((sound = loadedSounds.get(i)) != null){
				sound.dispose();
				sound = null;
				loadedSounds.remove(i);
				--len;
				--i;
			}
		}
		len = loadedMusics.size();
		for(int i = 0; i<len; i++){
			Music music;
			if((music = loadedMusics.get(i))!= null){
				if(music.isPlaying())
					music.stop();
				music.dispose();
				music = null;
				loadedMusics.remove(i);
				--len;
				--i;
			}
		}
	}
	
	public static void notifyDeltaTime(float deltaTime){
		if((elapsedTime += deltaTime) >= songPlayTime){
			Jukebox.actionHappened(ON_COMPLETION);
			elapsedTime = 0;
		}
	}
	
	public static int getSuperState(){
		return Jukebox.superState;
	}
}
