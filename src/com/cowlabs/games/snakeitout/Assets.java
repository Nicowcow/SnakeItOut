package com.cowlabs.games.snakeitout;

import com.cowlabs.games.snakeitout.framework.gl.Font;
import com.cowlabs.games.snakeitout.framework.gl.MTLLoader;
import com.cowlabs.games.snakeitout.framework.gl.Material;
import com.cowlabs.games.snakeitout.framework.gl.ObjLoader;
import com.cowlabs.games.snakeitout.framework.gl.Texture;
import com.cowlabs.games.snakeitout.framework.gl.TextureRegion;
import com.cowlabs.games.snakeitout.framework.gl.Vertices;

public class Assets {
	
	public static Texture background;
	public static TextureRegion backgroundRegion;
	
	public static Texture gameBackground;
	public static TextureRegion gameBackgroundRegion;
	
	
	public static Font font;
	public static com.cowlabs.games.framework.visual.layout.Font fontTest;

	public static Texture items;
	public static Texture splashTexture;
	public static Texture splashBackground;
	public static TextureRegion splashRegion;
	public static TextureRegion splashBackgroundRegion;
	public static TextureRegion backRegion;
	public static TextureRegion forthRegion;
	public static TextureRegion nextRegion;
	public static TextureRegion prevRegion;
	public static TextureRegion blueRegion;
	
	public static Vertices cube8Model;
	public static Texture cube8Texture;
	
	public static Vertices snakePortionModel;
	public static Vertices wallModel;
	public static Vertices appleModel;
	public static Material appleMaterial;
	public static Vertices teleporterTopModel;
	public static Vertices teleporterExtModel;
			
	/* Main Menu Screen */
	
	public static Texture logo;
	public static TextureRegion logoRegion;
	
	public static TextureRegion playRegion;
	public static TextureRegion settingsRegion;
	
	/**/
	
	/* Game Screen */
	
	public static TextureRegion joystickRegion;
	public static TextureRegion dpadRegion;
	public static TextureRegion dpadplusRegion;
	public static TextureRegion pauseRegion;
	
	
	
	/**/
	
	/* Settings Screen */
	
	
	public static TextureRegion soundEnabledRegion;
	public static TextureRegion soundDisabledRegion;
	public static TextureRegion vibrEnabledRegion;
	public static TextureRegion vibrDisabledRegion;
	
	public static Texture gameInputs;

	public static TextureRegion joystickEnabledRegion;
	public static TextureRegion joystickDisabledRegion;
	public static TextureRegion dpadEnabledRegion;
	public static TextureRegion dpadDisabledRegion;
	public static TextureRegion accelerometerEnabledRegion;
	public static TextureRegion accelerometerDisabledRegion;
	
	/**/
	
	/* Setup Screen*/
	public static Texture mapMask8;
	public static TextureRegion mapMask8Region;
	
	
	public static void load() {
		background = new Texture("background.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 1024, 512);
		gameBackground = new Texture("background_midres.png");
		gameBackgroundRegion = new TextureRegion(gameBackground, 0, 0, 512, 256);
		logo = new Texture("logo.png");
		logoRegion = new TextureRegion(logo, 0, 0, 64, 16);
		
		items = new Texture("items.png");
		playRegion = new TextureRegion(items, 0, 0, 400, 120);
		settingsRegion = new TextureRegion(items,0,120,400,120);
		soundEnabledRegion = new TextureRegion(items, 0, 240, 100, 100);
		soundDisabledRegion = new TextureRegion(items, 100, 240, 100, 100);
		vibrEnabledRegion = new TextureRegion(items, 0, 340, 100, 100);
		vibrDisabledRegion = new TextureRegion(items, 100,340,100,100);
		backRegion = new TextureRegion(items, 0, 440, 200,100);
		forthRegion = new TextureRegion(items, 0, 540, 200, 100);
		blueRegion = new TextureRegion(items, 0, 640, 200, 100);
		nextRegion = new TextureRegion(items,100,740, 100,100);
		prevRegion = new TextureRegion(items,0,740, 100,100);

		
		joystickRegion = new TextureRegion(items, 1024 - 256, 256, 256, 256);
		dpadRegion = new TextureRegion(items, 1024 - 288, 512, 288, 288);
		dpadplusRegion = new TextureRegion(items, 1024 - 2 * 288, 512, 288, 288);
		pauseRegion = new TextureRegion(items, 0, 840, 50, 50);
		
		
		gameInputs = new Texture("gameinputs.png");
		joystickEnabledRegion = new TextureRegion(gameInputs, 0, 256, 512, 256);
		joystickDisabledRegion = new TextureRegion(gameInputs, 512, 256, 512, 256);
		dpadEnabledRegion = new TextureRegion(gameInputs, 0, 0, 512, 256);
		dpadDisabledRegion = new TextureRegion(gameInputs, 512, 0, 512, 256);
		
		splashTexture = new Texture("hdcow512.png");
		splashBackground = new Texture("splashbackground.png");
		splashRegion = new TextureRegion(splashTexture, 0, 0, 512, 512);
		splashBackgroundRegion = new TextureRegion(splashBackground, 0, 0, 800, 480);

		
		mapMask8 = new Texture("mapmask8.png");
		mapMask8Region = new TextureRegion(mapMask8, 0, 0, 128, 128);
		
		font = new Font(items, 400, 0, 16, 32, 40);
		fontTest = new com.cowlabs.games.framework.visual.layout.Font(items, 400, 0, 16, 32, 40);

		
		cube8Texture = new Texture("cube8.png", true, true);
		cube8Model = ObjLoader.load("cube8.obj");
		
		snakePortionModel = ObjLoader.load("snakeportion.obj");
		wallModel = ObjLoader.load("wall.obj");
		appleModel = ObjLoader.load("apple.obj");
		appleMaterial = MTLLoader.load("apple.mtl");
		teleporterTopModel = ObjLoader.load("teleporter_top.obj");
	}
	
	public static void reload() {
		background.load();
		items.load();
		mapMask8.load();
		cube8Texture.load();
		gameInputs.load();
		splashTexture.load();
		gameBackground.load();
		logo.load();
	}
}
