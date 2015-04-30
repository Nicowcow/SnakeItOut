/*
 *	Author:      Nicolas Mattia
 *	Date:        25 avr. 2012
 */

package com.cowlabs.games.snakeitout.framework.gl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;

public class MTLLoader {
	public static Material load(String file){
		
		InputStream in = null;
		try {
			in = AndroidGameGlobal.fileIO.readAsset(file);
			List<String> lines = readLines(in);
			
			Material material = new Material();
			
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				
				if (line.startsWith("Ns ")) {
					String[] tokens = line.split("[ ]+");
					material.setShininess(Float.parseFloat(tokens[1]));
					continue;
				}
				
				if (line.startsWith("Ka ")) {
					String[] tokens = line.split("[ ]+");
					material.setAmbient(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]), 1);
					continue;
				}
				
				if (line.startsWith("Kd ")) {
					String[] tokens = line.split("[ ]+");
					material.setDiffuse(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]), 1);
					continue;
				}
				
				if (line.startsWith("Ks ")) {
					String[] tokens = line.split("[ ]+");
					material.setSpecular(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]), 1);
					continue;
				}
				
				if (line.startsWith("d ")) {
					String[] tokens = line.split("[ ]+");
					material.setAlpha(Float.parseFloat(tokens[1]));
					continue;
				}
				
			}
			return material;

		} catch (Exception ex) {
			throw new RuntimeException("couldn't load '" + file + "'", ex);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (Exception ex) {

				}
		}
		
	}
	
	
	static List<String> readLines(InputStream in) throws IOException {
		List<String> lines = new ArrayList<String>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ((line = reader.readLine()) != null)
			lines.add(line);
		return lines;
	}
}
