package modals;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;


import java.util.*;

public class DirectedWeightedGraph_Class implements DirectedWeightedGraph {
    private int mc;
    private int mcNI;
    private int mcEI;
    private int mcEIN;
    private HashMap<Integer, NodeData> nodes;
    private HashMap<Integer, HashMap<Integer, EdgeData>> edgesIn;
    private HashMap<Integer, HashMap<Integer, EdgeData>> edgesOut;
    private HashMap<String, EdgeData> allEdges;

    public void setNodes(HashMap<Integer, NodeData> nodes) {
        this.nodes = nodes;
    }

    public void setEdgesIn(HashMap<Integer, HashMap<Integer, EdgeData>> edgesIn) {
        this.edgesIn = edgesIn;
    }

    public void setEdgesOut(HashMap<Integer, HashMap<Integer, EdgeData>> edgesOut) {
        this.edgesOut = edgesOut;
    }

    public void setAllEdges(HashMap<String, EdgeData> allEdges) {
        this.allEdges = allEdges;
    }

    public HashMap<Integer, NodeData> getNodes() {
        return nodes;
    }
    public HashMap<String, EdgeData> getAllEdges() {
        return allEdges;
    }

    public DirectedWeightedGraph_Class() {
        this.mc = 0;
        this.mcEI = 0;
        this.mcEIN = 0;
        this.mcNI = 0;
        this.allEdges = new HashMap<String, EdgeData>();
        this.edgesIn = new HashMap<Integer, HashMap<Integer, EdgeData>>();
        this.edgesOut = new HashMap<Integer, HashMap<Integer, EdgeData>>();
        this.nodes = new HashMap<Integer, NodeData>();
    }

    @Override
    public NodeData getNode(int key) {
        return nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return edgesOut.get(src).get(dest);
    }

    @Override
    public void addNode(NodeData n) throws RuntimeException {
        mc++;
        if (mcNI != 0 && mcNI != mc) {
            new RuntimeException("The graph changed since the Node Iterator has been initialized!").printStackTrace();
        }
        if (mcEI != 0 && mcEI != mc) {
            new RuntimeException("The graph changed since the Edge Iterator has been initialized!").printStackTrace();
        }
        if (mcEIN != 0 && mcEIN != mc) {
            new RuntimeException("The graph changed since the Edge Iterator from Node has been initialized!").printStackTrace();
        }
        nodes.put(n.getKey(), n);
    }

    @Override
    public void connect(int src, int dest, double w) throws RuntimeException {
        mc++;
        if (mcNI != 0 && mcNI != mc) {
            new RuntimeException("The graph changed since the Node Iterator has been initialized!").printStackTrace();
        }
        if (mcEI != 0 && mcEI != mc) {
            new RuntimeException("The graph changed since the Edge Iterator has been initialized!").printStackTrace();
        }
        if (mcEIN != 0 && mcEIN != mc) {
            new RuntimeException("The graph changed since the Edge Iterator from Node has been initialized!").printStackTrace();
        }
        EdgeData_Class e = new EdgeData_Class(src, dest, w);
        String s = src + "," + dest;
        if (allEdges.containsKey(s)) {
            allEdges.remove(s);
            allEdges.put(s, e);
        } else {
            allEdges.put(s, e);
        }
        if (edgesOut.containsKey(src)) {
            if (!(edgesOut.get(src).containsKey(dest))) {
                edgesOut.get(src).put(dest, e);
            } else {
                edgesOut.get(src).remove(dest);
                edgesOut.get(src).put(dest, e);
            }
        }
        else {
            HashMap<Integer, EdgeData> t = new HashMap<Integer, EdgeData>();
            t.put(dest, e);
            edgesOut.put(src, t);
        }
        if (edgesIn.containsKey(dest)) {
            if (!(edgesIn.get(dest).containsKey(src))) {
                edgesIn.get(dest).put(src, e);
            } else {
                edgesIn.get(dest).remove(src);
                edgesIn.get(dest).put(src, e);
            }

        } else {
            HashMap<Integer, EdgeData> t = new HashMap<Integer, EdgeData>();
            t.put(src, e);
            edgesIn.put(dest, t);
        }
    }

    @Override
    public Iterator<NodeData> nodeIter()  {
        mcNI = mc;
        return nodes.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        mcEI = mc;
        return allEdges.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        mcEIN = mc;
        return edgesOut.get(node_id).values().iterator();
    }

    @Override
    public NodeData removeNode(int key) {
        mc++;
        if (mcNI != 0 && mcNI != mc) {
            new RuntimeException("The graph changed since the Node Iterator has been initialized!").printStackTrace();
        }
        if (mcEI != 0 && mcEI != mc) {
            new RuntimeException("The graph changed since the Edge Iterator has been initialized!").printStackTrace();
        }
        if (mcEIN != 0 && mcEIN != mc) {
            new RuntimeException("The graph changed since the Edge Iterator from Node has been initialized!").printStackTrace();
        }
        edgesIn.remove(key);
        edgesOut.remove(key);
        for(String k : allEdges.keySet()){
            String[] arr = k.split(",");
            if (Integer.parseInt(arr[0])==key||Integer.parseInt(arr[1])==key)
                allEdges.remove(k);
        }
        return nodes.remove(key);

    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        mc++;
        if (mcNI != 0 && mcNI != mc) {
            new RuntimeException("The graph changed since the Node Iterator has been initialized!").printStackTrace();
        }
        if (mcEI != 0 && mcEI != mc) {
            new RuntimeException("The graph changed since the Edge Iterator has been initialized!").printStackTrace();
        }
        if (mcEIN != 0 && mcEIN != mc) {
            new RuntimeException("The graph changed since the Edge Iterator from Node has been initialized!").printStackTrace();
        }
        String s = src+","+dest;
        allEdges.remove(s);
        edgesIn.get(dest).remove(src);
        return edgesOut.get(src).remove(dest);
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return allEdges.size();
    }

    @Override
    public int getMC() {
        return this.mc;
    }
}
