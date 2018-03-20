package model.gizmo;

public class KeyTriggerPair {

	private final int keyCode;
	private final TriggerType triggerType;

	public KeyTriggerPair(int keyCode, TriggerType triggerType){
		this.keyCode = keyCode;
		this.triggerType = triggerType;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public TriggerType getTriggerType() {
		return triggerType;
	}
}
