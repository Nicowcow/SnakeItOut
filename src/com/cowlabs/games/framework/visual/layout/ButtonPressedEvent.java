package com.cowlabs.games.framework.visual.layout;

public class ButtonPressedEvent {

	public enum ActionType {
		BUTTON_PRESSED,
		BUTTON_RELEASED
	}
	
	private final String ID;
	private final ActionType type;
	
	public ButtonPressedEvent(ActionType type, String ID){
		this.type = type;
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}

	public ActionType getType() {
		return type;
	}

}
