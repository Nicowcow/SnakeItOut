/*
 *	Author:      Nicolas Mattia
 *	Date:        8 avr. 2012
 */

package com.cowlabs.games.snakeitout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;


public class Settings {
	
	public final static String folder = ".snakeitout";
	public final static String file = ".settings";
	
	public static final int DPAD = 0;
	public static final int JOYSTICK = 1;
	public static final int ACCELEROMETER = 2;

	public static int lastVersionCode = 0;
	public static int nbLaunch = 0;
	public static boolean soundEnabled = true;
	public static boolean vibrEnabled = true;
	public static int gameInput = DPAD;
	public static int cubeSpeed = 5;
	public static int snakeSpeed = 5;
	


	public static void load() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(AndroidGameGlobal.fileIO.readFile(file)));
			
			lastVersionCode = Integer.parseInt(in.readLine());
			nbLaunch = Integer.parseInt(in.readLine());
			soundEnabled = Boolean.parseBoolean(in.readLine());
			vibrEnabled = Boolean.parseBoolean(in.readLine());
			gameInput = Integer.parseInt(in.readLine());
			cubeSpeed = Integer.parseInt(in.readLine());
			snakeSpeed = Integer.parseInt(in.readLine());
			
		} catch (IOException e) {
			// :( It's ok we have defaults
		} catch (NumberFormatException e) {
			// :/ It's ok, defaults save our day
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

	public static void save() {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					AndroidGameGlobal.fileIO.writeFile(file)));
			out.write(Integer.toString(lastVersionCode));
			out.write("\n");
			out.write(Integer.toString(nbLaunch));
			out.write("\n");
			out.write(Boolean.toString(soundEnabled));
			out.write("\n");
			out.write(Boolean.toString(vibrEnabled));
			out.write("\n");
			out.write(Integer.toString(gameInput));
			out.write("\n");
			out.write(Integer.toString(cubeSpeed));
			out.write("\n");
			out.write(Integer.toString(snakeSpeed));
			out.write("\n");
			
		} catch (IOException e) {
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}
}

