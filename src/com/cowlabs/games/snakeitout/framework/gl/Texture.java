package com.cowlabs.games.snakeitout.framework.gl;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import com.cowlabs.games.snakeitout.framework.Ressource;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;


public class Texture implements Ressource {
	String fileName;
	int textureId;
	int minFilter;
	int magFilter;
	public int width;
	public int height;
	boolean mipmapped;
	boolean inAssets;
	boolean hasAlpha;

	public Texture(String fileName) {
		this(fileName, false, true);
	}

	public Texture(String fileName, boolean mipmapped, boolean inAssets) {
		this.fileName = fileName;
		this.mipmapped = mipmapped;
		this.inAssets = inAssets;
		load();
	}

	
	@Override
	public void load() {
		GL10 gl = AndroidGameGlobal.glGraphics.getGL();
		int[] textureIds = new int[1];
		gl.glGenTextures(1, textureIds, 0);
		textureId = textureIds[0];

		InputStream in = null;
		try {
			if(this.inAssets)
				in = AndroidGameGlobal.fileIO.readAsset(fileName);
			else
				in = AndroidGameGlobal.fileIO.readFile(fileName);
			
			Bitmap bitmap = BitmapFactory.decodeStream(in);
			this.hasAlpha = bitmap.hasAlpha();
			
			if (mipmapped) {
				createMipmaps(gl, bitmap);
			} else {
				gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
				GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
				setFilters(GL10.GL_NEAREST, GL10.GL_NEAREST);
				gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
				width = bitmap.getWidth();
				height = bitmap.getHeight();
				bitmap.recycle();
			}
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load texture '" + fileName
					+ "'", e);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}
	}

	private void createMipmaps(GL10 gl, Bitmap bitmap) {
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);

		width = bitmap.getWidth();
		height = bitmap.getHeight();
		setFilters(GL10.GL_LINEAR_MIPMAP_NEAREST, GL10.GL_LINEAR);

		int level = 0;
		int newWidth = width;
		int newHeight = height;
		while (true) {
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, bitmap, 0);
			newWidth = newWidth / 2;
			newHeight = newHeight / 2;
			if (newWidth <= 0)
				break;

			Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
			bitmap.recycle();
			bitmap = newBitmap;
			level++;
		}

		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		bitmap.recycle();
	}

	private void setFilters(int minFilter, int magFilter) {
		this.minFilter = minFilter;
		this.magFilter = magFilter;
		GL10 gl = AndroidGameGlobal.glGraphics.getGL();
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				minFilter);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				magFilter);
	}

	public void bind() {
		GL10 gl = AndroidGameGlobal.glGraphics.getGL();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
	}

	@Override
	public void dispose() {
		GL10 gl = AndroidGameGlobal.glGraphics.getGL();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		int[] textureIds = { textureId };
		gl.glDeleteTextures(1, textureIds, 0);
	}
}