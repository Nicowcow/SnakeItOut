package com.cowlabs.games.snakeitout.framework;

import com.cowlabs.games.framework.process.ProcessManager;
import com.cowlabs.games.snakeitout.framework.impl.View;

public abstract class HumanView extends View{
	
	protected ProcessManager processMgr;
	protected int viewID;
	protected int actorID;
	

	public abstract void present(float deltaTime);
	
}


