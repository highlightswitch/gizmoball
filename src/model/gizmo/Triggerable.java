package model.gizmo;

import model.util.Procedure;

import java.util.HashSet;
import java.util.Set;

public abstract class Triggerable implements KeyEventTriggerable {

	private Set<Triggerable> connectedTriggerables;
	protected Procedure action;

	private boolean keyDown;

	protected Triggerable(){
		connectedTriggerables = new HashSet<>();
		setAction(GizmoActionType.CHANGE_COLOUR);
	}

	public abstract void setAction(GizmoActionType type);

	protected void trigger(){
		this.doAction();
		triggerAllConnected();
	}

	private void triggerAllConnected(){
		for(Triggerable t : connectedTriggerables){
			t.doAction();
		}
	}

	public void doAction(){
			action.invoke();
	}

	public void addActor(Triggerable actor){
		connectedTriggerables.add(actor);
	}

	public void removeActor(Triggerable actor){
		connectedTriggerables.remove(actor);
	}

	public void removeAllActors(){
		connectedTriggerables.clear();
	}

	public void keyDown(){
		keyDown = true;
	}

	public void keyUp(){
		keyDown = false;
	}

}
