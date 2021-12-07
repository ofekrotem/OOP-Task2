package modals;

import api.EdgeData;
import api.NodeData;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedGraph_ClassTest {
    DirectedWeightedGraph_Class graph = new DirectedWeightedGraph_Class();
    GeoLocation_Class geo1 = new GeoLocation_Class(1,2,3);
    NodeData_Class node1 = new NodeData_Class(1,geo1);
    GeoLocation_Class geo2 = new GeoLocation_Class(4,5,6);
    NodeData_Class node2 = new NodeData_Class(2,geo2);
    GeoLocation_Class geo3 = new GeoLocation_Class(7,8,9);
    NodeData_Class node3 = new NodeData_Class(3,geo3);
    @Test
    void getNodeTest() {
        graph.addNode(node1);
        assertEquals(node1,graph.getNode(1));
    }

    @Test
    void getEdgeTest() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.connect(node1.getKey(),node2.getKey(),5);
        assertEquals(1,graph.getEdge(node1.getKey(),node2.getKey()).getSrc());
        assertEquals(2,graph.getEdge(node1.getKey(),node2.getKey()).getDest());
        assertEquals(5,graph.getEdge(node1.getKey(),node2.getKey()).getWeight());
    }

    @Test
    void addNodeTest() {
        graph.addNode(node1);
        assertEquals(1,graph.nodeSize());
    }

    @Test
    void connectTest() {
        graph.connect(node1.getKey(),node2.getKey(),5);
        assertEquals(1,graph.edgeSize());
    }

    @Test
    void nodeIterTest() {
        graph.addNode(node1);
        graph.addNode(node2);
        Iterator<NodeData> iter = graph.nodeIter();
        int nodeCounter = 0;
        while (iter.hasNext()){
            nodeCounter++;
            iter.next();
        }
        assertEquals(2,nodeCounter);

        try {
            graph.addNode(node3);
        }catch (RuntimeException r){
            assertTrue(true);
        }
    }

    @Test
    void edgeIterTest() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.connect(node1.getKey(),node2.getKey(),5);
        Iterator<EdgeData> iter = graph.edgeIter();
        int edgeCounter = 0;
        while (iter.hasNext()){
            edgeCounter++;
            iter.next();
        }
        assertEquals(1,edgeCounter);
        try {
            graph.connect(node1.getKey(),node3.getKey(),3);
        }catch (RuntimeException r){
            assertTrue(true);
        }
    }

    @Test
    void EdgeIterWithNodeTest() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.connect(node1.getKey(),node2.getKey(),5);
        Iterator<EdgeData> iter = graph.edgeIter(node1.getKey());
        int edgeCounter = 0;
        while (iter.hasNext()){
            edgeCounter++;
            iter.next();
        }
        assertEquals(1,edgeCounter);
        try {
            graph.connect(node1.getKey(),node3.getKey(),3);
        }catch (RuntimeException r){
            assertTrue(true);
        }
    }

    @Test
    void removeNodeTest() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.connect(node1.getKey(),node2.getKey(),5);
        assertEquals(1,graph.edgeSize());
        assertEquals(2,graph.nodeSize());
        graph.removeNode(node1.getKey());
        assertEquals(1,graph.nodeSize());
        graph.removeNode(node2.getKey());
        assertEquals(0,graph.nodeSize());
        assertEquals(0,graph.edgeSize());
    }

    @Test
    void removeEdgeTest() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.connect(node1.getKey(),node2.getKey(),5);
        assertEquals(1,graph.edgeSize());
        graph.removeEdge(node1.getKey(),node2.getKey());
        assertEquals(0,graph.edgeSize());
    }

    @Test
    void nodeSizeTest() {
        graph.addNode(node1);
        assertEquals(1,graph.nodeSize());
    }

    @Test
    void edgeSizeTest() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.connect(node1.getKey(),node2.getKey(),5);
        assertEquals(1,graph.edgeSize());
    }

    @Test
    void getMCTest() {
        assertEquals(0,graph.getMC());
        graph.addNode(node1);
        assertEquals(1,graph.getMC());
        graph.addNode(node2);
        assertEquals(2,graph.getMC());
        graph.connect(node1.getKey(),node2.getKey(),5);
        assertEquals(3,graph.getMC());

    }
}