package model;

public interface Collidable {

    GameObject getGameObject();
    Object clone();

    boolean isAbsorber();

}
