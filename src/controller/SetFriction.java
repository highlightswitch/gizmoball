package controller;

import model.IModel;
import model.ModelPropertyException;

public class SetFriction {
    public SetFriction(IModel m, double fx, double fy) throws ModelPropertyException {
        m.setFrictionConstants(new double[]{fx,fy});
    }
}
