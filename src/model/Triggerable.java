package model;

import model.gizmo.GizmoActionType;
import model.gizmo.KeyEventTriggerable;
import model.util.Procedure;

import java.util.HashSet;
import java.util.Set;

public abstract class Triggerable implements KeyEventTriggerable {

	private Set<Triggerable> connectedTriggerables;
	protected Procedure action;

	private boolean keyDown;

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
		if(!keyDown)
			action.invoke();
	}

	void addActor(Triggerable actor){
		connectedTriggerables.add(actor);
	}

	public void keyDown(){
		keyDown = true;
	}

	public void keyUp(){
		keyDown = false;
	}

}
