package tests;

import modals.GeoLocation_Class;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoLocation_ClassTest {
    GeoLocation_Class a = new GeoLocation_Class(1,2,3);
    GeoLocation_Class b = new GeoLocation_Class(3,5,6);

    @Test
    void xTest() {
        assertEquals(1,a.x());
    }

    @Test
    void yTest() {
        assertEquals(2,a.y());
    }

    @Test
    void zTest() {
        assertEquals(3,a.z());
    }

    @Test
    void distance() {
        assertEquals(4.69041575982343,a.distance(b));
    }
}