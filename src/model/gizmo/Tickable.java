package model.gizmo;

public interface Tickable extends Cloneable {

    void tick();
    Object clone();

}
