package model;

import physics.Vect;

public interface Collidable extends Triggerable {

    GameObject getGameObject();
    Object clone();

    boolean isAbsorber();

    CollisionDetails timeUntilCollisionWithBall(GameObject go, Vect ballVelocity);

}
