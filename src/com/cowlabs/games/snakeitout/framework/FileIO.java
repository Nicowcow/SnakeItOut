package com.cowlabs.games.snakeitout.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileIO {
	public InputStream readAsset(String fileName) throws IOException;

    public InputStream readFile(String fileName) throws IOException;

    public OutputStream writeFile(String fileName) throws IOException;
    
	public String getGameDirectoryPath();

	public String[] listGameDirectory();
	
	public String[] listAssetsDir(String dir);
	
	public void copyAssetsDir(String srcDir, String desDir);

}
