package physics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeometryTest {

    Geometry g = new Geometry();
    Vect x = new Vect(0,3);
    Vect y = new Vect(4,4);
    Geometry.VectPair v = new Geometry.VectPair(x,y);


    @BeforeEach
    void setUp() {
    }

    @Test
    void setGeometry() {
    }

}