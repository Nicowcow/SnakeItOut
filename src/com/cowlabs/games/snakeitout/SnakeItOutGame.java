package com.cowlabs.games.snakeitout;

import com.cowlabs.games.snakeitout.framework.MenuView;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGame;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;

public class SnakeItOutGame extends AndroidGame {
	
	enum GameState{
		MENU,
		WAITING_FOR_PLAYERS,
		LOADING_GAME_ENVIRONMENT,
		RUNNING
		
	}
		
	
	@Override
	public void handleNewVersion(int lastVersionCode, int newVersionCode){
		if(lastVersionCode == 0)
			AndroidGameGlobal.fileIO.copyAssetsDir("Maps", 
					AndroidGameGlobal.fileIO.getGameDirectoryPath());
	}


	@Override
	protected MenuView getStartView() {
		// TODO Auto-generated method stub
		return new MenuView();
	} 
	
//	@Override
//	public GLScreen getStartScreen(){
//		
//		return new SplashScreen();
//	}

}
