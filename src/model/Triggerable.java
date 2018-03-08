package model;

public interface Triggerable {

	void trigger();
	void triggerAllConnected();

	void doAction();

	void addActor(Triggerable t);

}
