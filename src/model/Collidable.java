package model;

import physics.Vect;

public interface Collidable {

    GameObject getGameObject();
    Object clone();
    boolean isAbsorber();
    CollisionDetails timeUntilCollisionWithBall(GameObject go, Vect ballVelocity);
    void collide();
}
