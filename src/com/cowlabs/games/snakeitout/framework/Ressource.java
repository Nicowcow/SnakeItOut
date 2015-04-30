package com.cowlabs.games.snakeitout.framework;

public interface Ressource {
	
	public enum RessourceType{
		TEXTURE,
	};

	public void load();
	
	public void dispose();

}
