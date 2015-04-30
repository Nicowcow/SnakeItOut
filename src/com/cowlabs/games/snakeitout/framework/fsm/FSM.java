package com.cowlabs.games.snakeitout.framework.fsm;

import java.util.Vector;

//import games.mayo.events.Event;
//import games.mayo.events.EventListener;



public class FSM {//implements EventListener {
		
//	private FSMState currentState;
//	private Vector<Event> currentStateEvents = new Vector<Event>();
//	private Vector<Event> occuredEvents = new Vector<Event>();
//	
//	private boolean isRunning = false;
//	
//	public FSMState getNewStartingState() {
//		
//		this.currentState = new FSMState();
//	
//		return this.currentState;
//	}
//	
//	
//	public void setRunning(){
//		
//		updateRegisteredEvents();
//		
//		this.isRunning = true;
//	}
//	
//	public void checkStateChange(){
//		
//		FSMState newState; int i, nEvents = occuredEvents.size();
//		for(i = 0; i < nEvents; i++){
//			if((newState = this.currentState.getNextState(occuredEvents.get(i))) != null){
//				changeToState(newState);				
//				break;
//			}
//		}
//		
//	}
//	
//	private void changeToState(FSMState newState){
//		
//		this.currentState = newState;
//		
//		updateRegisteredEvents();
//	}
//	
//	private void updateRegisteredEvents(){
//		
//		// Unregister to ancient state's events
//		int i, nEvents = this.currentStateEvents.size();
//		
//		for( i = 0 ; i < nEvents ; i++){
//			this.currentStateEvents.get(i).unregisterListener(this);
//		}
//		
//		this.currentStateEvents = this.currentState.getEvents();
//		
//		// Register to new state's events
//		nEvents = this.currentStateEvents.size();
//		
//		for( i = 0 ; i < nEvents ; i++){
//			this.currentStateEvents.get(i).registerListener(this);
//		}
//	}
//	
//	public boolean isRunning(){
//		return this.isRunning;
//	}
//	
//	public void currentStateAction(){
//		this.currentState.listenersAction();
//	}
//
//	@Override
//	public void eventOccured(Event e) {
//		occuredEvents.add(e);
//	}
}

