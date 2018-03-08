package model;

public interface Collidable extends Triggerable {

    GameObject getGameObject();
    Object clone();

    boolean isAbsorber();

}
