package tests;

import api.NodeData;
import modals.DirectedWeightedGraphAlgorithms_Class;
import modals.DirectedWeightedGraph_Class;
import modals.GeoLocation_Class;
import modals.NodeData_Class;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedGraphAlgorithms_ClassTest {
    DirectedWeightedGraph_Class graph = new DirectedWeightedGraph_Class();
    DirectedWeightedGraphAlgorithms_Class algo = new DirectedWeightedGraphAlgorithms_Class();
    DirectedWeightedGraphAlgorithms_Class algo1 = new DirectedWeightedGraphAlgorithms_Class();

    @Test
    void initTest() {
        algo.init(graph);
        assertEquals(this.graph,algo.getGraph());
    }

    @Test
    void getGraphTest() {
        algo.init(graph);
        assertEquals(this.graph,algo.getGraph());
    }

    @Test
    void copyTest() {
        algo.init(graph);
        GeoLocation_Class geo = new GeoLocation_Class(1,2,3);
        NodeData_Class node = new NodeData_Class(1,geo);
        graph.addNode(node);
        DirectedWeightedGraph_Class newgraph = (DirectedWeightedGraph_Class) algo.copy();
        assertEquals(graph.nodeSize(),newgraph.nodeSize());
    }

    @Test
    void isConnectedTest() {
        algo.init(graph);
        algo.load("data/G1.json");
        assertTrue(algo.isConnected());
    }

    @Test
    void shortestPathDistTest() {
        algo.init(graph);
        algo.load("data/G1.json");
        assertEquals(4.8330358613553095,algo.shortestPathDist(1,7));
    }

    @Test
    void shortestPathTest() {
        algo.init(graph);
        algo.load("data/G1.json");
        assertEquals(11,algo.shortestPath(1,7).size());
    }

    @Test
    void centerTest() {
        algo.init(graph);
        algo.load("data/G1.json");
        assertEquals(2,algo.center().getKey());
    }

    @Test
    void tspTest() {
        algo.init(graph);
        algo.load("data/G1.json");
        List<NodeData> l = new LinkedList<NodeData>();
        l.add(graph.getNode(1));
        l.add(graph.getNode(2));
        l.add(graph.getNode(3));
        assertEquals(3,algo.tsp(l).size());
    }

    @Test
    void saveTest() {
        algo.init(graph);
        algo.load("data/G1.json");
        algo.save("copy.json");
        DirectedWeightedGraph_Class g = new DirectedWeightedGraph_Class();
        DirectedWeightedGraphAlgorithms_Class a = new DirectedWeightedGraphAlgorithms_Class();
        a.init(g);
        a.load("copy.json");
        assertEquals(17,g.nodeSize());
    }

    @Test
    void loadTest() {
        algo.init(graph);
        algo.load("data/G1.json");
        assertEquals(17,graph.nodeSize());
    }
}