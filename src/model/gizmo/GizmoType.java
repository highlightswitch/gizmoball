package model.gizmo;

import java.util.ArrayList;
import java.util.Collections;

public enum GizmoType {

    FLIPPER(new GizmoPropertyType[]{
            GizmoPropertyType.NAME,
            GizmoPropertyType.ROTATION_DEG,
            GizmoPropertyType.IS_LEFT_ORIENTATED
    }),
    BALL(new GizmoPropertyType[]{
            GizmoPropertyType.NAME,
            GizmoPropertyType.VEL_X,
            GizmoPropertyType.VEL_Y
    }),
    CIRCLE_BUMPER(new GizmoPropertyType[]{
            GizmoPropertyType.NAME,
            GizmoPropertyType.ROTATION_DEG
    }),
    SQUARE_BUMPER(new GizmoPropertyType[]{
            GizmoPropertyType.NAME,
            GizmoPropertyType.ROTATION_DEG
    }),
    TRIANGLE_BUMPER(new GizmoPropertyType[]{
            GizmoPropertyType.NAME,
            GizmoPropertyType.ROTATION_DEG
    }),
    ABSORBER(new GizmoPropertyType[]{
            GizmoPropertyType.NAME,
            GizmoPropertyType.WIDTH,
            GizmoPropertyType.HEIGHT
    });

    private ArrayList<GizmoPropertyType> properties;

    GizmoType(GizmoPropertyType[] props){
        properties = new ArrayList<>();
        Collections.addAll(properties, props);
    }

    public ArrayList<GizmoPropertyType> getPropertyTypes(){
        return properties;
    }
}
