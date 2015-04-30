/*
 *	Author:      Nicolas Mattia
 *	Date:        13 avr. 2012
 */

package com.cowlabs.games.snakeitout;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;



import com.cowlabs.games.snakeitout.framework.gl.AmbientLight;
import com.cowlabs.games.snakeitout.framework.gl.DirectionalLight;
import com.cowlabs.games.snakeitout.framework.gl.LookAtCamera;
import com.cowlabs.games.snakeitout.framework.gl.SpriteBatcher;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;
import com.cowlabs.games.snakeitout.framework.impl.GLGraphics;
import com.cowlabs.games.snakeitout.framework.math.CubePoint;


public class WorldRenderer {
	GLGraphics glGraphics;
	LookAtCamera camera;
	AmbientLight ambientLight;
	DirectionalLight directionalLight;
	SpriteBatcher batcher;
	private float applesAngle = 0;
	
	
	
	public WorldRenderer(GLGraphics glGraphics) {
		this.glGraphics = AndroidGameGlobal.glGraphics;
		camera = new LookAtCamera(67, glGraphics.getWidth()
				/ (float) glGraphics.getHeight(), 0.1f, 100);
		camera.getPosition().set(0, 0, 13);
		camera.getLookAt().set(0, 0, 0);
		ambientLight = new AmbientLight();
		ambientLight.setColor(0.2f, 0.2f, 0.2f, 1.0f);
//		ambientLight.setColor(1f, 1f, 1f, 1f);
		directionalLight = new DirectionalLight();
		// TEST
		directionalLight.setSpecular(1, 1, 1, 1);
		//
		directionalLight.setDirection(0, -0.5f, -0.5f);
	}
	
	public void render(World world, float deltaTime){
		GL10 gl = glGraphics.getGL();
		
		
		
		camera.setMatrices(gl);
		
		world.rotationQuaternion.update(deltaTime);
		
		
		
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_LIGHTING);					
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
		directionalLight.enable(gl, GL10.GL_LIGHT0);
		gl.glDepthMask(false);
		gl.glDepthMask(true);
		
		gl.glPushMatrix();

		// Render textured objects
		gl.glEnable(GL10.GL_TEXTURE_2D);

		renderCube(gl, world.rotationQuaternion.getMatrix());
		renderTeleporters(gl, world.teleporters, deltaTime);
		
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
		/***/
		
		// Render non-textured objects

		renderSnake(gl, world.snake);
		renderWalls(gl, world.walls);
		
		

		/***/
		
		
		gl.glDisable(GL10.GL_COLOR_MATERIAL);

		// Render material objects
		renderApples(gl, world.apples, deltaTime);

		
		
		
		/***/
		
		gl.glPopMatrix();

		
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_DEPTH_TEST);	
		

	}
	
	private void renderCube(GL10 gl, float[] rotationMatrix){
		Assets.cube8Texture.bind();
		Assets.cube8Model.bind();
		
		gl.glMultMatrixf(rotationMatrix, 0);
		
		Assets.cube8Model.draw(GL10.GL_TRIANGLES, 0,
				Assets.cube8Model.getNumVertices());
		Assets.cube8Model.unbind();
	}
	
	
	private void renderSnake(GL10 gl, Snake snake){
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);		
		gl.glColor4f(0, 1, 0, 1f);
		Assets.snakePortionModel.bind();
		int len = snake.body.size();
		float delta = 0.5f / len;
		float g = 1f;
		for(int i = 0; i < len; i++){
			SnakePortion snakePortion = snake.body.get(i);
			gl.glColor4f(0, g, 0, 1);
			gl.glPushMatrix();
			setMatricesByPosition(gl, snakePortion.position);
			Assets.snakePortionModel.draw(GL10.GL_TRIANGLES, 0,
					Assets.snakePortionModel.getNumVertices());
			gl.glPopMatrix();
			g-=delta;
		}
		Assets.snakePortionModel.unbind();
		gl.glColor4f(1, 1, 1, 1f);		
		gl.glDisable(GL10.GL_BLEND);
		
	}
	
	private void renderApples(GL10 gl, List<Apple> apples, float deltaTime){
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);		
		gl.glColor4f(1, 0, 0, 1f);
		applesAngle += 70 * deltaTime;
		Assets.appleModel.bind();
		Assets.appleMaterial.enable(gl);
		int len = apples.size();
		for(int i = 0; i < len; i++){
			Apple apple = apples.get(i);
			gl.glPushMatrix();
			setMatricesByPosition(gl, apple.position);
			gl.glRotatef(applesAngle + apple.dephasage, 0, 1, 0);
			Assets.appleModel.draw(GL10.GL_TRIANGLES, 0,
					Assets.appleModel.getNumVertices());
			gl.glPopMatrix();
		}
		Assets.appleModel.unbind();
		gl.glColor4f(1, 1, 1, 1f);		
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void renderWalls(GL10 gl, List<Wall> walls){
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);		
		gl.glColor4f(0.5f, 0.5f, 0.5f, 1f);
		Assets.wallModel.bind();
		int len = walls.size();
		for(int i = 0; i < len; i++){
			Wall wall = walls.get(i);
			gl.glPushMatrix();
			setMatricesByPosition(gl, wall.position);
			Assets.wallModel.draw(GL10.GL_TRIANGLES, 0,
					Assets.wallModel.getNumVertices());
			gl.glPopMatrix();
		}
		Assets.wallModel.unbind();
		gl.glColor4f(1, 1, 1, 1f);		
		gl.glDisable(GL10.GL_BLEND);
		
	}
	
	private void renderTeleporters(GL10 gl, List<Teleporter> teleporters, float deltaTime){
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		Assets.teleporterTopModel.bind();
		int len = teleporters.size();
		for(int i = 0; i < len; i++){
			Teleporter teleporter = teleporters.get(i);
			gl.glColor4f(teleporter.floatColor[0], teleporter.floatColor[1], teleporter.floatColor[2], teleporter.floatColor[3]);
			gl.glPushMatrix();
			setMatricesByPosition(gl, teleporter.position);
			gl.glRotatef(2*applesAngle, 0, 1, 0);
			Assets.teleporterTopModel.draw(GL10.GL_TRIANGLES, 0,
					Assets.teleporterTopModel.getNumVertices());
			gl.glPopMatrix();
		}
		Assets.teleporterTopModel.unbind();
//		Assets.teleporterExtModel.bind();
//		for(int i = 0; i< len; i++){
//			Teleporter teleporter = teleporters.get(i);
//			gl.glColor4f(teleporter.floatColor[0], teleporter.floatColor[1], teleporter.floatColor[2], teleporter.floatColor[3]);
//			gl.glPushMatrix();
//			setMatricesByPosition(gl, teleporter.position);
//			gl.glRotatef(-2*applesAngle, 0, 1, 0);
//			Assets.teleporterTopModel.draw(GL10.GL_TRIANGLES, 0,
//					Assets.teleporterExtModel.getNumVertices());
//			gl.glPopMatrix();
//		}
//		Assets.teleporterTopModel.unbind();
		gl.glColor4f(1, 1, 1, 1f);		
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void setMatricesByPosition(GL10 gl, CubePoint position){
		int size = CubePoint.size;
		int sizeByTwo = size /2;
		float delta = sizeByTwo + 0.5f;
		
		switch(position.face){
		case CubePoint.FRONT:
			gl.glTranslatef(position.col - delta, delta - position.row, sizeByTwo);
			gl.glRotatef(90, 1, 0, 0);
			break;
			
		case CubePoint.BACK:
			gl.glTranslatef(position.col - delta, position.row - delta, - sizeByTwo);
			gl.glRotatef(-90, 1, 0, 0);
			break;
			
		case CubePoint.RIGHT:
			gl.glTranslatef(sizeByTwo, delta - position.row, delta - position.col);
			gl.glRotatef(-90, 0, 0, 1);
			break;
			
		case CubePoint.LEFT:
			gl.glTranslatef(-sizeByTwo, delta - position.row, position.col - delta);
			gl.glRotatef(90, 0, 0, 1);
			break;
			
		case CubePoint.TOP:
			gl.glTranslatef(position.col - delta, sizeByTwo, position.row - delta);
			break; 
			
		case CubePoint.BOTTOM:
			gl.glTranslatef(position.col - delta, -sizeByTwo, delta - position.row);
			gl.glRotatef(180, 1, 0, 0);
			break;
		}
	}
	
	
}
