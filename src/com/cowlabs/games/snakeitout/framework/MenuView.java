package com.cowlabs.games.snakeitout.framework;

import java.util.Vector;

import android.view.KeyEvent;

import com.cowlabs.games.snakeitout.MainMenuScreen;
import com.cowlabs.games.snakeitout.framework.events.EventListener;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;
import com.cowlabs.games.snakeitout.framework.impl.GLScreen;
import com.cowlabs.games.snakeitout.framework.impl.ScreenFinishedEvent;
import com.cowlabs.games.snakeitout.framework.impl.TouchEvent;

public class MenuView  extends HumanView implements EventListener {

	private GLScreen currentScreen;

	public MenuView(){
		this.setScreen(new MainMenuScreen());
	}
	
	@Override
	public void present(float deltaTime) {
		this.currentScreen.present(deltaTime);
		
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		this.currentScreen.update(deltaTime);
		
	}
	
	protected void setScreen(GLScreen newScreen) {
		if(this.currentScreen != null)
			AndroidGameGlobal.evtMgr.removeListener(this.currentScreen);
		
        if (newScreen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.currentScreen = newScreen;
        AndroidGameGlobal.evtMgr.addListener(this.currentScreen, TouchEvent.class);

    }
	
	public void onDimensionsChanged(){
		this.currentScreen.onDimensionsChanged();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return this.currentScreen.onKeyDown(keyCode, event);
	}
	
	public Vector<Class<?>> getEvents(){
		Vector<Class<?>> eventTypes = new Vector<Class<?>>();
		eventTypes.add(ScreenFinishedEvent.class);
		return eventTypes;
	}
	
	@Override
	public void handleEvent(Object event){
		if(event instanceof ScreenFinishedEvent)
			this.setScreen(((ScreenFinishedEvent) event).getNewScreen());
	}
}
