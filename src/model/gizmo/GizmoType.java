package model.gizmo;

import java.util.ArrayList;
import java.util.Collections;

public enum GizmoType {

    FLIPPER(new GizmoPropertyType[]{
            GizmoPropertyType.NAME,
            GizmoPropertyType.ROTATION_DEG,
            GizmoPropertyType.IS_LEFT_ORIENTATED,
            GizmoPropertyType.CURRENT_COLOUR,
            GizmoPropertyType.DEFAULT_COLOUR,
            GizmoPropertyType.ALT_COLOUR
    }),
    BALL(new GizmoPropertyType[]{
            GizmoPropertyType.NAME,
            GizmoPropertyType.VEL_X,
            GizmoPropertyType.VEL_Y,
            GizmoPropertyType.CURRENT_COLOUR,
            GizmoPropertyType.DEFAULT_COLOUR,
            GizmoPropertyType.ALT_COLOUR
    }),
    CIRCLE_BUMPER(new GizmoPropertyType[]{
            GizmoPropertyType.NAME,
            GizmoPropertyType.ROTATION_DEG,
            GizmoPropertyType.CURRENT_COLOUR,
            GizmoPropertyType.DEFAULT_COLOUR,
            GizmoPropertyType.ALT_COLOUR
    }),
    SQUARE_BUMPER(new GizmoPropertyType[]{
            GizmoPropertyType.NAME,
            GizmoPropertyType.ROTATION_DEG,
            GizmoPropertyType.CURRENT_COLOUR,
            GizmoPropertyType.DEFAULT_COLOUR,
            GizmoPropertyType.ALT_COLOUR
    }),
    TRIANGLE_BUMPER(new GizmoPropertyType[]{
            GizmoPropertyType.NAME,
            GizmoPropertyType.ROTATION_DEG,
            GizmoPropertyType.CURRENT_COLOUR,
            GizmoPropertyType.DEFAULT_COLOUR,
            GizmoPropertyType.ALT_COLOUR
    }),
    ABSORBER(new GizmoPropertyType[]{
            GizmoPropertyType.NAME,
            GizmoPropertyType.WIDTH,
            GizmoPropertyType.HEIGHT,
            GizmoPropertyType.CURRENT_COLOUR,
            GizmoPropertyType.DEFAULT_COLOUR,
            GizmoPropertyType.ALT_COLOUR
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
