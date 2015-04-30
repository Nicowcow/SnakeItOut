/*
 *	Author:      Nicolas Mattia
 *	Date:        3 sept. 2011
 */

package com.cowlabs.games.snakeitout.framework.math;

//import android.util.Log;



public class Quaternion {
	
	private Quaternion fQuat ;	//final Quaternion
	private Quaternion sQuat ;	//start Quaternion
	private boolean isAnimating = false;
	private float	animTime;
	private float	elapsedTime;
	
	// temp values	
    public float x,y,z,w; 
    private float new_x, new_y, new_z, new_w;	
	private float wx, wy, wz, xx, yy, yz, xy, xz, zz, x2, y2, z2;
	//
	
	
	
	private float[] delta_angles = new float[2];

    /**
     * Creation d'un nouveau quaternion i.x + j.y + k.z + w
     * Attention : le contructeur est privé il faut passer par les méthodes getXXX
     * @param x la valeur de X
     * @param y la valeur de Y
     * @param z la valeur de Z
     * @param w la valeur de W
     */
    private Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public void clone(Quaternion quat){
    	this.x = quat.x;
    	this.y = quat.y;
    	this.z = quat.z;
    	this.w = quat.w;
    }
    
    /**
     * Retourne un quaternion identité
     * @return le quaternion désiré
     */
    public static Quaternion getIdentity() {
    	return new Quaternion(0,0,0,1);
    }
    
    
    public static Quaternion getClone(Quaternion quat) {
    	Quaternion newQuat = new Quaternion(quat.x,quat.y,quat.z,quat.w);
    	
    	return newQuat;
    }
    
    public void update(float deltaTime){
    	if(isAnimating){
    		updateSlerp(deltaTime);
    	}
    	else
    		this.rotate(delta_angles[0]*deltaTime, delta_angles[1]*deltaTime);
    }
    
    
    public void startSlerp(Quaternion fQuat, float time){
    	if(!isAnimating){
    		this.animTime = time;
    		if(this.fQuat != null)
    			this.fQuat.clone(fQuat);
    		else
    			this.fQuat = Quaternion.getClone(fQuat);
    		
    		if(this.sQuat != null)
    			sQuat.clone(this);
    		else
    			this.sQuat = Quaternion.getClone(this);
    		this.isAnimating = true;
    	}
    }
    
    private void updateSlerp(float deltaTime){
    	this.elapsedTime += deltaTime;
    	if(elapsedTime >= animTime){
    		this.clone(fQuat);
    		isAnimating = false;
    		elapsedTime = 0;
    	} else{
    		float t = elapsedTime / animTime;
    		
    		double cosHalfTheta = sQuat.w * fQuat.w + sQuat.x * fQuat.x + sQuat.y * fQuat.y + sQuat.z * fQuat.z;
    		// if qa=qb or qa=-qb then theta = 0 and we can return qa
    		if (Math.abs(cosHalfTheta) >= 1.0){
    			this.w = sQuat.w;this.x = sQuat.x;this.y = sQuat.y;this.z = sQuat.z;
    			return ;
    		}
    		// Calculate temporary values.
    		double halfTheta = Math.acos(cosHalfTheta);
    		double sinHalfTheta = Math.sqrt(1.0 - cosHalfTheta*cosHalfTheta);
    		if (Math.abs(sinHalfTheta) < 0.001f){ // fabs is floating point absolute
    			this.w = (sQuat.w * 0.5f + fQuat.w * 0.5f);
    			this.x = (sQuat.x * 0.5f + fQuat.x * 0.5f);
    			this.y = (sQuat.y * 0.5f + fQuat.y * 0.5f);
    			this.z = (sQuat.z * 0.5f + fQuat.z * 0.5f);
    			return;
    		}
    		float ratioA = (float) (Math.sin((1 - t) * halfTheta) / sinHalfTheta);
    		float ratioB = (float) (Math.sin(t * halfTheta) / sinHalfTheta); 
    		//calculate Quaternion.
    		this.w = (sQuat.w * ratioA + fQuat.w * ratioB);
    		this.x = (sQuat.x * ratioA + fQuat.x * ratioB);
    		this.y = (sQuat.y * ratioA + fQuat.y * ratioB);
    		this.z = (sQuat.z * ratioA + fQuat.z * ratioB);
    		return;
    	}
    	
    }

    public void rotate(float alpha_x,float alpha_y){
    	alpha_x *= 0.5f; alpha_y *= 0.5f;
    	float sin_x = (float) Math.sin(alpha_x); float sin_y = (float) Math.sin(alpha_y);
    	float cos_x = (float) Math.cos(alpha_x); float cos_y = (float) Math.cos(alpha_y);
    	
    	float x_r = cos_y * sin_x, y_r = sin_y * cos_x, z_r = sin_y * sin_x, w_r = cos_y * cos_x;
    	
    	new_x = w_r * x + x_r * w + y_r * z - z_r * y;
    	new_y = w_r * y + y_r * w + z_r * x - x_r * z;
    	new_z = w_r * z + z_r * w + x_r * y - y_r * x;
    	new_w = w_r * w - x_r * x - y_r * y - z_r * z;
    	
    	this.x = new_x;
    	this.y = new_y;
    	this.z = new_z;
    	this.w = new_w;
    }
    
    public void setDeltaAngles(float a1, float a2){
    	delta_angles[0] = a1; delta_angles[1] = a2;
    }
    
    public void setAngles(float[] angles){
    	float alpha = angles[0]; float beta = angles[1];
    	float sin_alpha = (float) Math.sin(alpha); float sin_beta = (float) Math.sin(beta);
    	float cos_alpha = (float) Math.cos(alpha); float cos_beta = (float) Math.cos(beta);
    	
    	this.x = cos_beta*sin_alpha;
    	this.y = sin_beta*cos_alpha;
    	this.z = sin_beta*sin_alpha;
    	this.w = cos_beta*cos_alpha;
    }
    
    public float[] getAngles(){
    	float[] angles = new float[2];
    	angles[0] = (float) (Math.atan2(z,y));
    	angles[1] = (float) (Math.atan2(z,x));
    	
    	return angles;
    }
    
    public void setLongLatAngle(float[] longLatAngle){
    	
    	float sin_long = (float) Math.sin(longLatAngle[0]/2);
    	float cos_long = (float) Math.cos(longLatAngle[0]/2);
    	
    	float sin_lat = (float) Math.sin(longLatAngle[1]/2);
    	float cos_lat = (float) Math.cos(longLatAngle[1]/2);
    	
    	float sin_angle = (float) Math.sin(longLatAngle[2]/2);
    	float cos_angle = (float) Math.cos(longLatAngle[2]/2);
    	
    	x = sin_angle * cos_lat * sin_long;
    	y = sin_angle * sin_lat;
    	z = sin_angle * sin_lat * cos_long;
    	w = cos_angle;
    	
    }
    
    
    public float[] getLongLatAngle(){
    	float[] longLatAngle = new float[3];
    	longLatAngle[0] = (float) (x*x + z*z<0?0:Math.atan2(x,z));
    	if(longLatAngle[0] < 0)
    		longLatAngle[0] += 2*Math.PI;
    	
    	longLatAngle[1] = (float) -Math.asin(y);
    	longLatAngle[2] = (float) (2 * Math.acos(w));
    	
    	return longLatAngle;
    }

    /**
     * Retourne la matrice de rotation issue du quaternion
     * @return la matrice de rotation
     */
    public float[] getMatrix() {

    	x2 = x + x;	y2 = y+y;	z2 = z+z;
    	xx = x * x2;	xy = x * y2;	xz = x * z2;
    	yy = y * y2;	yz = y * z2;	zz = z * z2;
    	wx = w * x2;	wy = w * y2;	wz = w * z2;

    	return new float[] {
    	1.0f-(yy + zz), xy + wz,        xz - wy,        0.0f,
    	xy - wz,        1.0f-(xx + zz), yz + wx,        0.0f,
    	xz + wy,        yz - wx,        1.0f-(xx + yy), 0.0f,
    	0.0f,           0.0f,           0.0f,           1.0f};
    }
    
//    public void dump(){
//    	Log.d("MERDASSE", "Quaternion :");
//    	Log.d("MERDASSE", "x = " + x + ", y = " + y + ", z = " + z + ", w = " + w);
//    }
    
    public float getAlpha(int face){
    	switch(face){
		case CubeMovingPoint.FRONT:	// OK
			return - (float) Math.atan2(x*y*2 - w*z*2, 1.0f - (y*y*2 + z*z*2));

		case CubeMovingPoint.BACK:  // OK
			return (float) Math.atan2(x*y*2 - w * z*2, 1.0f - (y*y*2 + z*z*2));

		case CubeMovingPoint.TOP:
			return (float) Math.atan2(x * z*2, 1.0f - (y*y*2+z*z*2));

		case CubeMovingPoint.BOTTOM:
			return - (float) Math.atan2(x*z*2, 1.0f - (y*y*2 + z*z*2));
			
		case CubeMovingPoint.RIGHT:
			return (float) Math.atan2(-(2*x*y - 2*w*z),-(2*x*z + 2*w*y));
			
		case CubeMovingPoint.LEFT:			
			return  (float) Math.atan2(-(2*x*y - 2*w*z),(2*x*z + 2*w*y));		
		}
    	
    	
    	return 0f;
    }
}