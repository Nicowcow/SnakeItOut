package com.cowlabs.games.snakeitout.framework.impl;


// TODO this class shouldn't exist, everything handled in the view

public class ScreenFinishedEvent {

	private GLScreen newScreen;
	
	public ScreenFinishedEvent(GLScreen newScreen){
		this.newScreen = newScreen;
	}


	public GLScreen getNewScreen(){
		return this.newScreen;
	}
	

}
