package com.cowlabs.games.snakeitout.framework.impl;


import java.util.Vector;

import com.cowlabs.games.snakeitout.framework.MenuView;
//import com.cowlabs.games.snakeitout.framework.events.EventListener;
import android.view.KeyEvent;


public abstract class AndroidGame {
	
//	private GLScreen screen;
	private MenuView currentView;
	private Vector<View> attachedViews = new Vector<View>();
	
	public AndroidGame(){
		this.setView(this.getStartView());
//		this.registerEvents();
//		this.setScreen(this.getStartScreen());
	}
	
//	protected void registerEvents(){
//		AndroidGameGlobal.evtMgr.addListener(this, ScreenFinishedEvent.class);
//	}
	
	public void handleNewVersion(int lastVersionCode, int newVersionCode){
	}
	
	

	public void update(float deltaTime){
		for(int i = 0; i < attachedViews.size(); i++)
			attachedViews.get(i).update(deltaTime);
	}
	
	public void present(float deltaTime){
		this.currentView.present(deltaTime);
		
	}
	
	protected abstract MenuView getStartView();

	
	protected void setView(MenuView view){
		if(this.currentView != null)
			AndroidGameGlobal.evtMgr.removeListener(this.currentView);
		this.currentView = view;
		for(Class<?> type : this.currentView.getEvents())
			AndroidGameGlobal.evtMgr.addListener(currentView, type);
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event){	// TODO this goes to App layer
    	return this.currentView.onKeyDown(keyCode, event);
    }
	
	public void onDimensionsChanged(){
		this.currentView.onDimensionsChanged();
	}
	
//	@Override
//	public void handleEvent(Object event){
//		if(event instanceof ScreenFinishedEvent)
//			this.setScreen(((ScreenFinishedEvent) event).getNewScreen());
//	}
	

}
