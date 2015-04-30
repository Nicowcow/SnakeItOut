package com.cowlabs.games.framework.visual.layout;

import com.cowlabs.games.framework.visual.graphics.Picture;
import com.cowlabs.games.snakeitout.framework.events.EventListener;
import com.cowlabs.games.snakeitout.framework.gl.Texture;
import com.cowlabs.games.snakeitout.framework.gl.TextureRegion;
import com.cowlabs.games.snakeitout.framework.impl.AndroidGameGlobal;
import com.cowlabs.games.snakeitout.framework.impl.TouchEvent;
import com.cowlabs.games.snakeitout.framework.math.Vector2;

public class Button extends SubElement implements EventListener {
	
	private final Picture pic ;
	private final String ID;

	public Button(String ID, Texture texture, TextureRegion region) {
		this.ID = ID;
		this.pic = new Picture(texture, region, super.getShape());
		super.addPicture(pic);
//		AndroidGameGlobal.evtMgr.addListener(this, TouchEvent.class);
	}

	@Override
	float getAspectRatio() {
		return pic.getAspectRatio();
	}
	
	public boolean isTouching(Vector2 point){
		return super.getShape().overlap(point);
	}


	public Picture getPic() {
		return this.pic;
	}

	@Override
	public void handleEvent(Object event) {
//		if(event instanceof TouchEvent){
//			if(((TouchEvent) event).getType() == TouchEvent.TouchType.TOUCH_DOWN &&
//					super.getShape().overlap(((TouchEvent) event).getTouchPoint()))
//				AndroidGameGlobal.evtMgr.queueEvent(
//						 new ButtonPressedEvent(ButtonPressedEvent.ActionType.BUTTON_PRESSED,this.ID));
//			else if(((TouchEvent) event).getType() == TouchEvent.TouchType.TOUCH_UP && 
//					super.getShape().overlap(((TouchEvent) event).getTouchPoint()))
//					AndroidGameGlobal.evtMgr.queueEvent(
//							 new ButtonPressedEvent(ButtonPressedEvent.ActionType.BUTTON_RELEASED,this.ID));
//		}
	
		
		// TODO Remove this as a listener
	}
		


}
