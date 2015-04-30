package com.cowlabs.games.snakeitout.framework.impl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.PowerManager.WakeLock;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.cowlabs.games.snakeitout.Jukebox;
import com.cowlabs.games.snakeitout.R;
import com.cowlabs.games.snakeitout.Settings;
import com.cowlabs.games.snakeitout.framework.events.EventManager;

public abstract class AndroidGameApp extends Activity implements Renderer {
	enum GameAppState{
		Initializing,
		CreatingGame,
		Running,
		Paused,
		Finished,
		Idle
	}
	
	private GLSurfaceView glView;
	
	private AndroidGame game;
	private GameAppState state = GameAppState.Initializing;
	long startTime = System.nanoTime();
	private WakeLock wakeLock;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		glView = new GLSurfaceView(this);
		glView.setRenderer(this);
		setContentView(glView);
		
		try{
			AndroidGameGlobal.versionCode = this.getApplicationContext().getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;
		}catch (Exception e){
			this.finish();
		}
		
		AndroidGameGlobal.ressourceCache = new RessourceCache();
		AndroidGameGlobal.stringArray = getResources().getStringArray(R.array.loading_text);
		AndroidGameGlobal.evtMgr = new EventManager();
		AndroidGameGlobal.glGraphics = new GLGraphics(glView);
		AndroidGameGlobal.fileIO = new AndroidFileIO(getAssets());
		AndroidGameGlobal.audio = new AndroidAudio(this);
		AndroidGameGlobal.input = new AndroidInput(this, glView, 1, 1);
		AndroidGameGlobal.vibrator = new AndroidVibrator((Vibrator)getSystemService(Context.VIBRATOR_SERVICE));
		AndroidGameGlobal.scores = new AndroidScore(this);
		
		Settings.load();
		
		
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGameApp");
		
		
	}
	
	public abstract AndroidGame createGameAndStartView();  
	
	@Override
	public void onResume(){
		super.onResume();
		glView.onResume();
		wakeLock.acquire();
		Jukebox.actionHappened(Jukebox.APP_RESUMED);
	}
	
	@Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {    
        	AndroidGameGlobal.glGraphics.setGL(gl);
            this.state = GameAppState.CreatingGame;
    }
	
	@Override
    public void onSurfaceChanged(GL10 gl, int width, int height) { 
		if(game != null)
			game.onDimensionsChanged();
		
    }

    @Override
    public void onDrawFrame(GL10 gl) {    
    	if(this.state == GameAppState.CreatingGame){
    		Jukebox.actionHappened(Jukebox.APP_STARTED);
    		this.game = this.createGameAndStartView();	
    		
    		if( AndroidGameGlobal.versionCode > Settings.lastVersionCode){
    			this.game.handleNewVersion(Settings.lastVersionCode, AndroidGameGlobal.versionCode);
    			Settings.lastVersionCode = AndroidGameGlobal.versionCode;
    			Settings.save();
    		}
    		
    		this.state = GameAppState.Running;
            startTime = System.nanoTime();
    	}
    	
    	else if(this.state == GameAppState.Running) {
            AndroidGameGlobal.evtMgr.tick();    		
    		
            float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            
            game.update(deltaTime);
            game.present(deltaTime);
        }
    }   
    
    @Override 
    public void onPause() {        
            if(isFinishing())            
                state = GameAppState.Finished;
            else
                state = GameAppState.Paused;

        wakeLock.release();
        glView.onPause();  
        Jukebox.actionHappened(Jukebox.APP_PAUSED);
        
        super.onPause();
    }    
    
    @Override
    public void onStop(){
    	Jukebox.actionHappened(Jukebox.APP_STOPPED);
    	super.onStop();
    }
    
    @Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
    	if(game!= null && game.onKeyDown(keyCode, event))
    		return true;
    	else
    		return super.onKeyDown(keyCode, event);
    }  
}
