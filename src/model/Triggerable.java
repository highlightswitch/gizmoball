package model;

import model.gizmo.GizmoActionType;

import java.util.HashSet;
import java.util.Set;

public abstract class Triggerable {

	private Set<Triggerable> connectedTriggerables;
	protected Runnable action;

	protected Triggerable(){
		connectedTriggerables = new HashSet<>();
		setAction(GizmoActionType.PRINT_TO_CONSOLE);
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
		action.run();
	}

	void addActor(Triggerable actor){
		connectedTriggerables.add(actor);
	}

}
