package com.cowlabs.games.snakeitout.framework.impl;


import com.cowlabs.games.snakeitout.framework.Audio;
import com.cowlabs.games.snakeitout.framework.FileIO;
import com.cowlabs.games.snakeitout.framework.Input;
import com.cowlabs.games.snakeitout.framework.events.EventManager;

public class AndroidGameGlobal {


	public static GLGraphics glGraphics;
	public static Audio audio;
	public static EventManager evtMgr;
	public static Input input;
	public static FileIO fileIO;
	public static AndroidVibrator vibrator;
	public static int versionCode;
	public static String[] stringArray;
	public static AndroidScore scores;
	public static RessourceCache ressourceCache;

}
