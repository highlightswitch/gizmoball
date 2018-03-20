package model.gizmo;

import model.util.Procedure;

import java.util.HashSet;
import java.util.Set;

public abstract class Triggerable implements KeyEventTriggerable {

	private Set<Triggerable> connectedTriggerables;
	GizmoActionType actionType;
	Procedure action;


	Triggerable(){
		connectedTriggerables = new HashSet<>();
		setAction(GizmoActionType.CHANGE_COLOUR);
	}

	public GizmoActionType getActionType() {
		return actionType;
	}

	public abstract void setAction(GizmoActionType type);

	public Set<Triggerable> getConnections(){
		return connectedTriggerables;
	}

	void trigger(){
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
		doAction();
	}

	public void keyUp(){
		doAction();
	}

}
