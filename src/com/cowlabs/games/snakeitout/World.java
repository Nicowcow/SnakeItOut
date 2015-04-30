/*
 *	Author:      Nicolas Mattia
 *	Date:        12 avr. 2012
 */

package com.cowlabs.games.snakeitout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;

import com.cowlabs.games.snakeitout.framework.math.CubeMovingPoint;
import com.cowlabs.games.snakeitout.framework.math.CubePoint;
import com.cowlabs.games.snakeitout.framework.math.Quaternion;


public class World {

	
	public final static int FREE = Color.WHITE;
	public final static int APPLE = Color.RED;
	public final static int WALL = Color.BLACK;
	public final static int SNAKE = Color.GREEN;
	
	// TELEPORTER = everything but white/red/black/green
	
	public interface WorldListener {
		public void appleAte();
		public void teleport(int face);
		public void snakeExplodes();
		public void gameOver();
	}

	WorldListener listener;
	
	final int size;

	int score = 0;
	
	final List<Apple> apples = new ArrayList<Apple>();
	final List<Wall> walls = new ArrayList<Wall>();
	final List<Teleporter> teleporters = new ArrayList<Teleporter>();
	final Snake snake;
	
	public Quaternion rotationQuaternion = Quaternion.getIdentity();
	
	private int applesToAdd;
	private CubePoint newApplePosition = new CubePoint(1,1,1);

	private final Map map;
	Random random;
	
	public World(Map map, int speed){
		this.map = map;
		this.size = CubePoint.size = map.size;
		initializeGrid(map);
		applesToAdd = map.nbApples;
		random = new Random();
		CubeMovingPoint snakeStartPosition = new CubeMovingPoint(0, 0, 0, 0);
		snakeStartPosition.copy(map.startPosition);
		snake = new Snake(snakeStartPosition, map.nbPortion, speed);
		
	}
	
	
	public void update(float deltaTime) {
		updateSnake(deltaTime);
		
		for(int i = 0; applesToAdd > 0 && i < 10; i++){
			if(addApple()){
				--applesToAdd;
			}
		}
	}
	
	private void updateSnake(float deltaTime){
		Teleporter teleporter;
		if(snake.isAlive()){
			if( snake.checkForMove(deltaTime)){
				if(appleAt(snake.newHeadPosition, true)){
					listener.appleAte();
					snake.addPortion();
					snake.moveToNewHeadPosition();
					score += snake.speed;
					applesToAdd++;
				}
				else if((teleporter = teleporterAt(snake.newHeadPosition))!= null){
					listener.teleport(teleporter.twin.position.face);
					snake.newHeadPosition.copy(teleporter.twin.position);
					snake.moveToNewHeadPosition();
				}
				else if(wallAt(snake.newHeadPosition)){
					listener.snakeExplodes();
					snake.kill();
					listener.gameOver();
				}
				else if(snakeAt(snake.newHeadPosition)){
					listener.snakeExplodes();
					snake.kill();
					listener.gameOver();
				}
				else
					snake.moveToNewHeadPosition();
			}}
		else if(snake.isDying()){
			int len = snake.body.size();
			if(len > 0){
				CubePoint newWallPosition = snake.body.remove(len-1).position;
				walls.add(new Wall(newWallPosition));
			}
			else{
				snake.setDead();
				
			}
		}
			
			
	}
	
	private boolean addApple(){
		newApplePosition.row = random.nextInt(8)+1;
		newApplePosition.col = random.nextInt(8)+1;
		newApplePosition.face = random.nextInt(6);
		
		if(!appleAt(newApplePosition, false) && !wallAt(newApplePosition) && !snakeAt(newApplePosition) && teleporterAt(newApplePosition) == null){
			Apple newApple = new Apple(newApplePosition);
			newApple.dephasage = 360 * random.nextFloat();
			apples.add(newApple);
			return true;
		}
		
		return false;
		
	}
	private boolean appleAt(CubePoint position, boolean remove){
		int len = apples.size();
		Apple apple;
		for(int i = 0; i < len; i++){
			apple = apples.get(i);
			if(apple.position.isAt(position)){
				if(remove){
					apples.remove(apple);
				}
				return true;
			}
		}
		return false;
	}
	
	private boolean wallAt(CubePoint position){
		for(int i = 0; i < walls.size(); i++){
			if(walls.get(i).position.isAt(position)){
				return true;
			}
		}
		return false;
	}
	
	private boolean snakeAt(CubePoint position){
		for(int i = 0; i < snake.body.size();i++){
			if(snake.body.get(i).position.isAt(position))
				return true;
		}
		
		return false;
	}
	
	private Teleporter teleporterAt(CubePoint position){
		for(int i = 0; i < teleporters.size(); i++){
			Teleporter teleporter = teleporters.get(i);
			if(teleporter.position.isAt(position)){
				return teleporter;
			}
		}
		return null;
	}
	
	
	
	
	public void setWorldListener(WorldListener worldListener) {
		this.listener = worldListener;
	}

	private void initializeGrid(Map map){
		for(int row = 1; row <= size; row ++ ){
			for(int col = 1; col <= size; col ++){
				for(int face = CubePoint.FRONT; face <= CubePoint.BOTTOM; face ++){
					int element = map.getElement(face, row, col);
					if(element == WALL){
						walls.add(new Wall(new CubePoint(face, row, col)));
						continue;
					}
					else if(element == FREE)
						continue;
					else{
						Teleporter newTeleporter = new Teleporter(new CubePoint(face,row,col), element);
						Teleporter twinTeleporter;
						int len = teleporters.size();
						for(int i = 0; i < len; i++){
							if((twinTeleporter = teleporters.get(i)).color == newTeleporter.color){
								twinTeleporter.twin = newTeleporter;
								newTeleporter.twin = twinTeleporter;
								break;
							}
						}
						teleporters.add(newTeleporter);
						
					}	
				}
			}	
		}
	}
	
	public void newSnakeDirection(float alpha){
		CubeMovingPoint headPosition = snake.body.get(0).position;		
		
		alpha-= rotationQuaternion.getAlpha(headPosition.face);
		
		alpha += Math.PI / 4;
		while(alpha < 0)
			alpha += 2*Math.PI;
		while(alpha > 2*Math.PI)
			alpha -= 2*Math.PI;
		
		int newDirection = (-(int)((alpha)/(Math.PI/2)) + 5) % 4;
		if((newDirection + 2)%4 != snake.newHeadPosition.direction)
			headPosition.direction = newDirection;

	}
	
	public Map getMap(){
		return this.map;
	}
}
