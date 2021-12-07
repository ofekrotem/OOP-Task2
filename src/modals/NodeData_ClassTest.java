package modals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NodeData_ClassTest {
    GeoLocation_Class geo1 = new GeoLocation_Class(1,2,3);
    NodeData_Class node = new NodeData_Class(1,geo1);
    @Test
    void getKeyTest() {
        assertEquals(1,node.getKey());
    }

    @Test
    void getLocationTest() {
        assertEquals(geo1,node.getLocation());
    }

    @Test
    void setLocationTest() {
        GeoLocation_Class geo2 = new GeoLocation_Class(1,1,1);
        node.setLocation(geo2);
        assertEquals(geo2,node.getLocation());

    }

    @Test
    void getWeightTest() {
        assertEquals(0,node.getWeight());
    }

    @Test
    void setWeightTest() {
        node.setWeight(5);
        assertEquals(5,node.getWeight());
    }

    @Test
    void getInfoTest() {
        assertNull(node.getInfo());
    }

    @Test
    void setInfoTest() {
        node.setInfo("test");
        assertEquals("test",node.getInfo());
    }

    @Test
    void getTagTest() {
        assertEquals(0,node.getTag());
    }

    @Test
    void setTagTest() {
        node.setTag(5);
        assertEquals(5,node.getTag());
    }
}