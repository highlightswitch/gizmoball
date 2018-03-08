package model.gizmo;

import model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    private Ball ball;
    private Model model;
    private Map<GizmoPropertyType, String> properties;

    @BeforeEach
    void setUp() {
        properties = new HashMap<GizmoPropertyType, String>();
        properties.put(GizmoPropertyType.NAME, "Ball");
        properties.put(GizmoPropertyType.VEL_X, "0");
        properties.put(GizmoPropertyType.VEL_Y, "0.5");

        ball = new Ball(model, Color.white, 5, 10, properties);

    }

    @Test
    public void findAnnexedTiles() throws Exception {
    }

    @Test
    public void getPosition() throws Exception {
        double[] position = {5, 10};
        assertEquals(ball.getPosition(), position);
    }

    @Test
    public void fire() throws Exception {
    }

    @Test
    public void moveBall() throws Exception {
    }

    @Test
    public void getPrototypeGameObject() throws Exception {
    }

    @Test
    public void getGameObject() throws Exception {
    }

    @Test
    public void isAbsorber() throws Exception {
    }

    @Test
    public void getGizmoDrawingData() throws Exception {
    }

    @Test
    public void tick() throws Exception {
    }

    @Test
    public void keyDown() throws Exception {
    }

    @Test
    public void keyUp() throws Exception {
    }

    @Test
    public void genericTrigger() throws Exception {
    }

}