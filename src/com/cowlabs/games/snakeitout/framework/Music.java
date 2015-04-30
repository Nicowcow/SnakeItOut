package com.cowlabs.games.snakeitout.framework;

public interface Music {
	
	public void play();

    public void stop();

    public void pause();
    
    public void prepare();

    public void setLooping(boolean looping);

    public void setVolume(float volume);

    public boolean isPlaying();

    public boolean isStopped();

    public boolean isLooping();

    public void dispose();
    
    public float getPlayTime();
    
    public void backToBeginning();

}
