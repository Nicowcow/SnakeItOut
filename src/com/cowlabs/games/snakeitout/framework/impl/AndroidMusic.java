package com.cowlabs.games.snakeitout.framework.impl;

import java.io.IOException;

import com.cowlabs.games.snakeitout.framework.Music;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;

public class AndroidMusic implements Music, OnCompletionListener, OnPreparedListener {
    MediaPlayer mediaPlayer;
    private float playTime;
    boolean isPrepared = false;
    
    OnCompletionListener listener;

    public AndroidMusic(AssetFileDescriptor assetDescriptor) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
                    assetDescriptor.getStartOffset(),
                    assetDescriptor.getLength());
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(this);
            playTime = mediaPlayer.getDuration()/1000f;
        } catch (Exception e) {
            throw new RuntimeException("Couldn't load music");
        }
    }

    @Override
    public void dispose() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public boolean isStopped() {
        return !isPrepared;
    }

    @Override
    public void pause() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    public void play() {
        if (mediaPlayer.isPlaying())
            return;

        try {
            synchronized (this) {
                if (!isPrepared)
                    mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLooping(boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }

    @Override
    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }
    
    @Override
    public void prepare(){
    	if(!isPrepared){
    		mediaPlayer.prepareAsync();
    	}
    	else
    		this.onPrepared(mediaPlayer);
    }

    @Override
    public void stop() {
        mediaPlayer.stop();
        synchronized (this) {
            isPrepared = false;
        }
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
    }

	@Override
	public void onPrepared(MediaPlayer mp) {
		synchronized(this){
			isPrepared = true;
		}
		
	}
	
	@Override
	public float getPlayTime(){
		return playTime;
	}

	@Override
	public void backToBeginning() {
		mediaPlayer.seekTo(0);
		
	}
}