/*
 *	Author:      Nicolas Mattia
 *	Date:        5 avr. 2012
 */

package com.cowlabs.games.snakeitout.framework;

import com.cowlabs.games.snakeitout.framework.math.Sphere;
import com.cowlabs.games.snakeitout.framework.math.Vector3;



public class GameObject3D {
	public final Vector3 position;
    public final Sphere bounds;
    
    public GameObject3D(float x, float y, float z, float radius) {
        this.position = new Vector3(x,y,z);
        this.bounds = new Sphere(x, y, z, radius);
    }
}
