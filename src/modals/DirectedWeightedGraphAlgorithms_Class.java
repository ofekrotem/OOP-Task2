package modals;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import java.io.*;
import java.util.*;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;

public class DirectedWeightedGraphAlgorithms_Class implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph_Class graph;

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
        if (this.graph.nodeSize() <= 1) {
            return true;
        }
        if (graph.edgeSize() < graph.nodeSize() - 1) {
            return false;
        }
        this.graph.makeTagsZero();
        NodeData curr = this.graph.FirstNode();
        NodeData next;
        if (curr == null) return false;
        Queue<NodeData> passed = new LinkedList<>();
        passed.add(curr);
        curr.setTag(3);
        while (!passed.isEmpty()) {
            curr = passed.poll();
            Iterator<EdgeData> edgeIter = this.graph.edgeIter(curr.getKey());
            while (edgeIter.hasNext()) {
                EdgeData edge = edgeIter.next();
                next = this.graph.getNode(edge.getDest());
                if (next.getTag() == 3) {
                    continue;
                } else {
                    passed.add(next);
                    next.setTag(3);
                }
            }
        }
        return this.graph.checkTags();
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) return 0;
        List<NodeData> help = shortestPath(src, dest);
        if (help != null && help.size() > 0) {
            return this.graph.getNode(dest).getWeight();
        }
        return (-1);
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        if (src == dest) return new LinkedList<>();
        this.graph.DijkstraPreparation(src);
        this.graph.makeTagsZero();
        HashMap<Integer, NodeData> visited = new HashMap<Integer, NodeData>();
        HashMap<Integer, NodeData> tempVisited = new HashMap<Integer, NodeData>();
        PriorityQueue<NodeData> queue = new PriorityQueue<NodeData>(new Comparator<NodeData>() {
            @Override
            public int compare(NodeData o1, NodeData o2) {
                return Double.compare(o1.getWeight(), o2.getWeight());
            }
        });
        List<NodeData> ans = new LinkedList<>();
        NodeData curr = this.graph.getNode(src);
        visited.put(src,curr);
        Iterator<EdgeData> edgeIter;
        queue.add(curr);
        NodeData nextNode;
        EdgeData nextEdge;
        double dist;
        while (!queue.isEmpty()) {
            curr = queue.poll();
            edgeIter = this.graph.edgeIter(curr.getKey());
            curr.setTag(3);
            if (curr.getKey() == dest) {
                this.graph.makeTagsZero();
                Iterator<NodeData> nodeIter = visited.values().iterator();
                while (nodeIter.hasNext()){
                    NodeData node = nodeIter.next();
                    node.setTag(3);
                    ans.add(node);
                }
                return ans;
            }
            while (edgeIter.hasNext()) {
                nextEdge = edgeIter.next();
                nextNode = this.graph.getNode(nextEdge.getDest());
                dist = curr.getWeight() + nextEdge.getWeight();
                if (nextNode.getKey() == dest) {
                    if (dist < graph.getNode(dest).getWeight()) {
                        graph.getNode(dest).setWeight(dist);
                        if (!visited.containsKey(dest)) {
                            visited.put(dest, curr);
                            queue.add(graph.getNode(dest));
                        } else {
                            visited.replace(dest, curr);
                        }
                    }
                } else {
                    tempVisited.putIfAbsent(nextNode.getKey(), nextNode);
                    if (nextNode.getTag() == 3) {
                        continue;
                    } else {
                        if (dist < nextNode.getWeight()) {
                            nextNode.setWeight(dist);
                            visited.putIfAbsent(nextNode.getKey(), nextNode);
                            queue.add(nextNode);
                        }
                    }

                }
            }
        }
        return null;
    }

    @Override
    public NodeData center() {
        if (!isConnected()) return null;
        int N = graph.nodeSize();
        double[] sumArr = new double[N];
        for (int i =0;i<N;i++){
            sumArr[i] = 0;
        }
        for (int i=0;i<N;i++){
            for (int j=0;j<N;j++){
                sumArr[i]+=shortestPathDist(i,j);
            }
        }
        double minSum = sumArr[0];
        int index = 0;
        for (int i=1;i<N;i++){
            if (minSum>sumArr[i]){
                minSum=sumArr[i];
                index=i;
            }
        }
        this.graph.makeTagsZero();
        this.graph.getNode(index).setTag(1);
        return this.graph.getNode(index);
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        Iterator<NodeData> citiesIter = cities.iterator();
        List<NodeData> tsp = new LinkedList<NodeData>();
        List<NodeData> tempTsp = new LinkedList<NodeData>();
        double best_W = Double.MAX_VALUE;
        double temp_W;
        while (citiesIter.hasNext()){
            NodeData curr = citiesIter.next();
            Iterator<EdgeData> currEdgeOutIter = graph.edgeIter(curr.getKey());
            tempTsp.clear();
            tempTsp = pathChecker(tempTsp,cities,curr,currEdgeOutIter);
            temp_W = W_Checker(tempTsp);
            if (temp_W<best_W){
                best_W = temp_W;
                tsp.clear();
                tsp.addAll(tempTsp);
            }
        }
        this.graph.makeTagsZero();
        for (NodeData node : tsp){
            node.setTag(2);
        }
        return tsp;
    }

    private double W_Checker(List<NodeData> tempTsp) {
        if (tempTsp.size()==0) return Double.MAX_VALUE;
        double sum = 0;
        Iterator<NodeData> iterator = tempTsp.iterator();
        NodeData curr = iterator.next();
        while (iterator.hasNext()){
            NodeData next = iterator.next();
            sum+=graph.getEdge(curr.getKey(),next.getKey()).getWeight();
            curr = iterator.next();
        }
        return sum;
    }


    private List<NodeData> pathChecker(List<NodeData> tempTsp, List<NodeData> cities, NodeData curr, Iterator<EdgeData> currEdgeOutIter) {
        if (tempTsp.size() == cities.size()){
            return tempTsp;
        }
        while (currEdgeOutIter.hasNext()){
            EdgeData currEdge = currEdgeOutIter.next();
            NodeData nextNode = graph.getNode(currEdge.getDest());
            if ((!cities.contains(nextNode))||tempTsp.contains(nextNode)){
                continue;
            }
            tempTsp.add(nextNode);
            Iterator<EdgeData> newEdgeOutIter = this.graph.edgeIter(nextNode.getKey());
            pathChecker(tempTsp,cities,nextNode,newEdgeOutIter);
            if (tempTsp.size() == cities.size()){
                return tempTsp;
            }
        }
        if (tempTsp.contains(curr)){
            tempTsp.remove(curr);
        }

        return tempTsp ;
    }


    @Override
    public boolean save(String file) {
        try {
            Gson j = new Gson();
            Writer fw = new FileWriter(file, false);
            JsonWriter jw = j.newJsonWriter(fw);
            jw.beginObject();
            jw.name("Edges").beginArray();
            for (String s : this.graph.getAllEdges().keySet()) {
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
            for (int i : this.graph.getNodes().keySet()) {
                double x = this.graph.getNodes().get(i).getLocation().x();
                double y = this.graph.getNodes().get(i).getLocation().y();
                double z = this.graph.getNodes().get(i).getLocation().z();
                jw.beginObject().name("pos").value(x + "," + y + "," + z);
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
    public boolean load(String file) {
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
