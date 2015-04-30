package com.cowlabs.games.snakeitout.framework.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.os.Environment;

import com.cowlabs.games.snakeitout.Settings;
import com.cowlabs.games.snakeitout.framework.FileIO;

public class AndroidFileIO implements FileIO {
	AssetManager assets;
	String gameDirectoryPath;
	File gameDirectory;
	
	public AndroidFileIO(AssetManager assets){
		this.assets = assets;
		gameDirectory = new File(Environment.getExternalStorageDirectory(), Settings.folder);
		gameDirectory.mkdir();
		this.gameDirectoryPath = gameDirectory.getAbsolutePath() + File.separator;
		
	}

	public String getGameDirectoryPath(){
		return gameDirectory.getAbsolutePath();
	}
	
	@Override
	public String[] listAssetsDir(String dir){
		String[] filesInDir = null;
		try {
			filesInDir = assets.list(dir);
		} catch (IOException e) {}
		
		return filesInDir;
	}
	
	@Override
	public InputStream readAsset(String fileName) throws IOException {
		return assets.open(fileName);
	}

	@Override
	public InputStream readFile(String fileName) throws IOException {
		return new FileInputStream(gameDirectoryPath + fileName);
	}

	@Override
	public OutputStream writeFile(String fileName) throws IOException {
		return new FileOutputStream(gameDirectoryPath + fileName);
	}
	
	@Override
	public String[] listGameDirectory(){
		return gameDirectory.list();
	}

	@Override
	public void copyAssetsDir(String srcDir, String desDir) {
		
		String[] files = listAssetsDir(srcDir);
		
		for(String file : files){
			InputStream in = null;
	        OutputStream out = null;
	        try {
	          in = readAsset(srcDir + "/" + file);
	          out = new FileOutputStream(desDir + "/" + file);
	          copyFile(in, out);
	          in.close();
	          in = null;
	          out.flush();
	          out.close();
	          out = null;
	        } catch(Exception e) {
	        	e.printStackTrace();
	        }       
		}
		
		
	}
	
	private void copyFile(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    int read;
	    while((read = in.read(buffer)) != -1){
	      out.write(buffer, 0, read);
	    }
	}

}
