package com.cowlabs.games.framework.process;

public abstract class Process {
	
	private boolean dead = false;
	private Process next;

	public abstract void onUpdate(float deltaTime);
	
	public void kill(){
		this.dead = true;
	}
	
	public boolean isDead(){
		return this.dead;
	}
	
	public void setNext(Process next){
		if(this.next != null)
			throw new RuntimeException("Next process was already set!");
		// TODO
	}
	
	public Process getNext(){
		return this.next;
	}
	
}


