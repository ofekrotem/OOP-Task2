package modals;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class DirectedWeightedGraphAlgorithms_Class implements DirectedWeightedGraphAlgorithms {
    DirectedWeightedGraph_Class graph;
    Gson j = new Gson();

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = (DirectedWeightedGraph_Class) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        save("copy.json");
        DirectedWeightedGraph_Class g = new DirectedWeightedGraph_Class();
        DirectedWeightedGraphAlgorithms_Class algo = new DirectedWeightedGraphAlgorithms_Class();
        algo.init(g);
        algo.load("copy.json");
        return algo.getGraph();
    }

    @Override
    public boolean isConnected() {
        if (this.graph.nodeSize()<=1)
            return true;

        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        try {
            Writer fw = new FileWriter(file,false);
            JsonWriter jw = j.newJsonWriter(fw);
            jw.beginObject();
            jw.name("Edges").beginArray();
            for(String s : this.graph.getAllEdges().keySet()){
                String[] arr = s.split(",");
                int src = Integer.parseInt(arr[0]);
                int dest = Integer.parseInt(arr[1]);
                double w = this.graph.getAllEdges().get(s).getWeight();
                jw.beginObject().name("src").value(src);
                jw.name("w").value(w);
                jw.name("dest").value(dest);
                jw.endObject();
            }
            jw.endArray().flush();
            jw.name("Nodes").beginArray();
            for (int i : this.graph.getNodes().keySet()){
                double x = this.graph.getNodes().get(i).getLocation().x();
                double y = this.graph.getNodes().get(i).getLocation().y();
                double z = this.graph.getNodes().get(i).getLocation().z();
                jw.beginObject().name("pos").value(x+","+y+","+z);
                jw.name("id").value(i).endObject();
            }
            jw.endArray().endObject().flush();
            jw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean load(String file){
        try {
            File f = new File(file);
            JsonElement je = JsonParser.parseReader(new FileReader(f));
            JsonObject jo = je.getAsJsonObject();
            HashMap<Integer, NodeData> nodes = new HashMap<Integer, NodeData>();
            HashMap<Integer, HashMap<Integer, EdgeData>> edgesIn = new HashMap<Integer, HashMap<Integer, EdgeData>>();
            HashMap<Integer, HashMap<Integer, EdgeData>> edgesOut = new HashMap<Integer, HashMap<Integer, EdgeData>>();
            HashMap<String, EdgeData> allEdges = new HashMap<String, EdgeData>();
            //Initialize graph nodes:
            JsonArray nodeArr = jo.get("Nodes").getAsJsonArray();
            for (JsonElement i : nodeArr) {
                JsonObject job = i.getAsJsonObject();
                String s = job.get("pos").getAsString();
                String[] arr;
                arr = s.split(",");
                double x = Double.parseDouble(arr[0]);
                double y = Double.parseDouble(arr[1]);
                double z = Double.parseDouble(arr[2]);
                GeoLocation_Class geo = new GeoLocation_Class(x, y, z);
                NodeData_Class node = new NodeData_Class((job.get("id").getAsInt()), geo);
                nodes.put(job.get("id").getAsInt(), node);
            }
            //Initialize graph edges:
            JsonArray edgeArr = jo.get("Edges").getAsJsonArray();
            for (JsonElement i : edgeArr) {
                JsonObject job = i.getAsJsonObject();
                int src = (job.get("src").getAsInt());
                int dest = (job.get("dest").getAsInt());
                double w = (job.get("w").getAsDouble());
                EdgeData_Class edge = new EdgeData_Class(src, dest, w);
                String s = src + "," + dest;
                if (allEdges.containsKey(s)) {
                    allEdges.remove(s);
                    allEdges.put(s, edge);
                } else {
                    allEdges.put(s, edge);
                }
                if (edgesOut.containsKey(src)) {
                    if (!(edgesOut.get(src).containsKey(dest))) {
                        edgesOut.get(src).put(dest, edge);
                    } else {
                        edgesOut.get(src).remove(dest);
                        edgesOut.get(src).put(dest, edge);
                    }
                } else {
                    HashMap<Integer, EdgeData> t = new HashMap<Integer, EdgeData>();
                    t.put(dest, edge);
                    edgesOut.put(src, t);
                }
                if (edgesIn.containsKey(dest)) {
                    if (!(edgesIn.get(dest).containsKey(src))) {
                        edgesIn.get(dest).put(src, edge);
                    } else {
                        edgesIn.get(dest).remove(src);
                        edgesIn.get(dest).put(src, edge);
                    }

                } else {
                    HashMap<Integer, EdgeData> t = new HashMap<Integer, EdgeData>();
                    t.put(src, edge);
                    edgesIn.put(dest, t);
                }

            }
            this.graph.setNodes(nodes);
            this.graph.setAllEdges(allEdges);
            this.graph.setEdgesIn(edgesIn);
            this.graph.setEdgesOut(edgesOut);
            return true;
        } catch (FileNotFoundException f) {
            f.printStackTrace();
            return false;
        }

    }
}
