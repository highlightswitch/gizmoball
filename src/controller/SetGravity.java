package controller;

import model.IModel;
import model.ModelPropertyException;

public class SetGravity {
    public SetGravity(IModel m, double g) throws ModelPropertyException {
        m.setGravityConstant(g);
    }

}
