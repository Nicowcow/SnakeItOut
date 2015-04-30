/*
 *	Author:      Nicolas Mattia
 *	Date:        1 juil. 2012
 */

package com.cowlabs.games.snakeitout.framework;

public interface Score {
	
	public void saveScore(int score, String context); // Context: map, for instance
	
	public int readScore(int nFromBest, String context); // returns the (n+1)th best scores
	
	public int[] readScores(int nFromBest, String context); // returns the n best scores
	
	

}
