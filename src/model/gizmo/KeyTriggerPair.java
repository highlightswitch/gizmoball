package model.gizmo;

public class KeyTriggerPair {

	public final int keyCode;
	public final TriggerType triggerType;

	public KeyTriggerPair(int keyCode, TriggerType triggerType){
		this.keyCode = keyCode;
		this.triggerType = triggerType;
	}

	@Override
	public String toString() {
		return "<"+keyCode+"_"+ triggerType +">";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof KeyTriggerPair &&
				this.keyCode == ((KeyTriggerPair) obj).keyCode &&
				this.triggerType == ((KeyTriggerPair) obj).triggerType;
	}
}
