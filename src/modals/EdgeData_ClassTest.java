package modals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeData_ClassTest {
    EdgeData_Class edge = new EdgeData_Class(1,3,1.5);
    @Test
    void getSrcTest() {
        assertEquals(1,edge.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(3,edge.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(1.5,edge.getWeight());
    }

    @Test
    void getInfo() {
        assertNull(edge.getInfo());
    }

    @Test
    void setInfo() {
        edge.setInfo("test");
        assertEquals("test",edge.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(0,edge.getTag());
    }

    @Test
    void setTag() {
        edge.setTag(5);
        assertEquals(5,edge.getTag());
    }
}