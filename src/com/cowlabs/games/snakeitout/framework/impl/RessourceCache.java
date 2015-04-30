package com.cowlabs.games.snakeitout.framework.impl;


import java.util.HashMap;

import com.cowlabs.games.snakeitout.framework.Ressource;
import com.cowlabs.games.snakeitout.framework.Ressource.RessourceType;

public class RessourceCache {
	
	
	
	private HashMap<RessourceType, HashMap<String, Ressource>> ressources = 
			new HashMap<RessourceType, HashMap<String, Ressource>>();
	

	public Ressource getRessource(RessourceType type, String name){
		return ressources.get(type).get(name);
	}

	public void addRessource(RessourceType type, String name, Ressource newRessource){
		HashMap<String, Ressource> typeRessources = ressources.get(type);
		if(typeRessources == null)
			typeRessources = new HashMap<String, Ressource>();
		
		Ressource ressource = typeRessources.get(name);
		if(ressource == null)
			typeRessources.put(name, newRessource);
	}


}
