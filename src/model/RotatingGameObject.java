package model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class RotatingGameObject extends GameObject {

    private Vect rotationPivot;
    private double angularVelocity;

    public RotatingGameObject(LineSegment[] lines, Circle[] circles, double reflectionCoefficient, Vect rotationPivot, double angularVelocity) {
        super(lines, circles, reflectionCoefficient);
        this.rotationPivot = rotationPivot;
        this.angularVelocity = angularVelocity;
    }

    @Override
    protected double timeUntilLineCollision(LineSegment line, Circle ballCircle, Vect ballVelocity) {
        return Geometry.timeUntilRotatingWallCollision(line, rotationPivot, angularVelocity, ballCircle, ballVelocity);
    }

    @Override
    protected Vect velocityOfLineCollision(LineSegment line, Circle ballCircle, Vect ballVelocity) {
        return Geometry.reflectRotatingWall(line, rotationPivot, angularVelocity, ballCircle, ballVelocity, reflectionCoefficient);
    }

    @Override
    protected double timeUntilCircleCollision(Circle circle, Circle ballCircle, Vect ballVelocity) {
        return Geometry.timeUntilRotatingCircleCollision(circle, rotationPivot, angularVelocity, ballCircle, ballVelocity);
    }

    @Override
    protected Vect velocityOfCircleCollision(Circle circle, Circle ballCircle, Vect ballVelocity) {
        return Geometry.reflectRotatingCircle(circle, rotationPivot, angularVelocity, ballCircle, ballVelocity, reflectionCoefficient);
    }
}
