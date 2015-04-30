/*
 *	Author:      Nicolas Mattia
 *	Date:        9 avr. 2012
 */

package com.cowlabs.games.snakeitout;




import android.graphics.Color;

import com.cowlabs.games.snakeitout.framework.gl.Texture;
import com.cowlabs.games.snakeitout.framework.gl.TextureRegion;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;
import com.cowlabs.games.snakeitout.framework.impl.AndroidPNGDecoder;
import com.cowlabs.games.snakeitout.framework.math.CubeMovingPoint;
import com.cowlabs.games.snakeitout.framework.math.CubePoint;


public class Map {
	
	
	
	
	// STATIC
	public static Map[] maps;
	private static AndroidPNGDecoder decoder = new AndroidPNGDecoder();
		
	public static void loadMaps(){
		String[] filesInDirectory = AndroidGameGlobal.fileIO.listAssetsDir("Maps");

		
		int nbMap = 0;
		Map[] tmpMaps = new Map[filesInDirectory.length];
		
		for(int i = 0; i < filesInDirectory.length; i++){
			if((tmpMaps[i] = loadMap(filesInDirectory[i])) != null)
				nbMap ++;
		}
		
		maps = new Map[nbMap];
		
		int counter = 0;
		for(int i = 0; i < tmpMaps.length; i++){
			if(tmpMaps[i] != null){
				maps[counter] = tmpMaps[i];
				counter++;
			}
		}		
	}
	
	public static void reload(){
		for(Map map: maps){
			map.preview.load();
		}
	}
	
	private static Map loadMap(String fileName){
		if(!fileName.contains(".snk"))
			return null;
		
		
		if(!decoder.setFile("Maps/"+fileName))
			return null;
		
		int height = decoder.getHeight(), width = decoder.getWidth();
		if((height != (24 + 8) && height != 48) || height != width) // 24 + 8, 24 is the actual size, + 8 to match a power of 2
			return null;

		int size = (height-8) / 3;
		int startFace = decoder.readBinary(0, 3, 2*size);
		int nbPortion = decoder.readBinary(3, 3, 2*size);
		int startOrient = decoder.readBinary(6, 2, 2*size);
		int nbApples = decoder.readBinary(0, 8, 2*size + 1);
		
		if(startFace > CubePoint.BOTTOM)
			return null;
		CubeMovingPoint startPosition;
		if((startPosition = getStartingPosition(size, startFace, startOrient)) == null)
			return null;

		return new Map(fileName, size, startPosition, nbPortion, nbApples);
	}
	
	private static CubeMovingPoint getStartingPosition(int size, int face, int orient){
		
		for(int row = 1; row <= size; row ++){
			for(int col = 1; col <= size; col ++){
				if(decoder.getColor( 2 * size + col - 1, row-1) == Color.BLACK){
					return new CubeMovingPoint(face, row, col, orient);
				}
			}
		}
		
		
		return null;
	}
	
	
	// MAP
	public String mapName, mapFile;
	public int size;
	public int nbPortion, nbApples;
	public Texture preview;
	public TextureRegion previewRegion;
	public CubeMovingPoint startPosition;
	
	public Map(String mapFile, int size, CubeMovingPoint startPosition, int nbPortion, int nbApples){
		preview = new Texture(mapFile, false, false);
		previewRegion = new TextureRegion(preview, size, size, size, size);
		
		this.mapFile = mapFile;
		this.mapName = mapFile.substring(0, mapFile.length()-4);
		this.size = size;
		this.startPosition = startPosition;
		this.nbPortion = nbPortion;
		this.nbApples = nbApples;
		
		
	}
	
	public int getElement(int face, int row, int col){
		decoder.setFile("Maps/" + mapFile);
		switch(face){
		case CubePoint.FRONT:
			return decoder.getColor(size + col - 1, size + row - 1);

		case CubePoint.BACK:
			return decoder.getColor(col-1, row-1);
			
		case CubePoint.RIGHT:
			return decoder.getColor(2 * size + col - 1, size + row - 1);
			
		case CubePoint.LEFT:
			return decoder.getColor(col - 1, size + row - 1);
			
		case CubePoint.TOP:
			return decoder.getColor(size + col -1, row -1);
			
		case CubePoint.BOTTOM:
			return decoder.getColor(size + col - 1, 2 * size + row - 1);
				
		}
		
		return 0;
	}
	
	
	
}
