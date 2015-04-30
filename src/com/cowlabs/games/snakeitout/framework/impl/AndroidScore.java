/*
 *	Author:      Nicolas Mattia
 *	Date:        1 juil. 2012
 */

package com.cowlabs.games.snakeitout.framework.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.cowlabs.games.snakeitout.framework.Score;

public class AndroidScore implements Score {
	
	private static final String DATABASE_NAME = "snakeItOutScores";
	
    private static final String KEY_PLAYER = "player";
    private static final String KEY_SCORE = "score";
	private Context context;
	
	public AndroidScore(Context context){
		this.context = context;
	}
	
	private class ScoreDatabase extends SQLiteOpenHelper {

		public ScoreDatabase(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	private ScoreDatabase scores = null;
	
	
	@Override
	public int readScore(int nFromBest, String context) {
		context = context.substring(3);
		
		if(scores != null){
			SQLiteDatabase db = scores.getReadableDatabase();
			
			if(tableExists(db, context)){
			
				Cursor cursor = db.query(context, null, null, null, null, null, KEY_SCORE + " DESC", "1");
				if(cursor != null){
					cursor.moveToFirst();
					return cursor.getInt(1);
				}
				else
					return 0;
			}else
				return 0;
			
		} else {
			load();
			return readScore(nFromBest, context);
		}
	}

	@Override
	public int[] readScores(int nFromBest, String context) {
		context = context.substring(3);
		if(scores != null){
			return null;
		} 
		else{
			load();
			return readScores(nFromBest, context);
		}
	}

	@Override
	public void saveScore(int score, String mapName) {
		
		String tableName = mapName.substring(3);


		if(scores != null){
			
			SQLiteDatabase db = scores.getWritableDatabase();
						 
		    ContentValues values = new ContentValues();
		    
		    values.put(KEY_PLAYER, "yah");
		    values.put(KEY_SCORE, score); 
		    
		    // Inserting Row
		    
		    String CREATE_MAP_TABLE = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
            + KEY_PLAYER + " VARCHAR(3)," + KEY_SCORE + " INT)";
	    	db.execSQL(CREATE_MAP_TABLE);
	    	
	    	db.insert(tableName, null, values);
		    	
		    db.close(); // Closing database connection
		    
		
		}
		else{
			load();
			saveScore(score, mapName); 		
		}
	}
	
	private void load(){
		scores = new ScoreDatabase(context,DATABASE_NAME,null,1);
	}
	
	private boolean tableExists(SQLiteDatabase db, String tableName)
	{
	    if (tableName == null || db == null || !db.isOpen())
	    {
	        return false;
	    }
	    Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
	    if (!cursor.moveToFirst())
	    {
	        return false;
	    }
	    int count = cursor.getInt(0);
	    cursor.close();
	    return count > 0;
	}
	

}

