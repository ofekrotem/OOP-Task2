package modals;

import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
import javax.swing.*;
import java.awt.*;

import java.util.HashMap;

public class MyPanel extends JPanel {
    private DirectedWeightedGraph_Class graph;
    private HashMap<Integer, NodeData> nodes = null;
    private HashMap<String, EdgeData> edges = null;
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;
    private final int Width = 700;
    private final int Height = 600;
    private MyFrame frame;
    private final int Regular = 0, Center = 1, tsp = 2, shortestPath = 3;

    public MyPanel(MyFrame frame) {
        this.frame = frame;
        this.setVisible(true);
    }

    public void setGraph(DirectedWeightedGraph_Class graph) {
        this.graph = graph;
        setNodes(graph.getNodes());
        setEdges(graph.getAllEdges());
    }

    public void setEdges(HashMap<String, EdgeData> edges) {
        this.edges = edges;
    }

    public void setNodes(HashMap<Integer, NodeData> nodes) {
        this.nodes = nodes;
        minX = Double.MAX_VALUE;
        maxX = Double.MIN_VALUE;
        minY = Double.MAX_VALUE;
        maxY = Double.MIN_VALUE;
        for (NodeData node : nodes.values()) {
            GeoLocation geo = node.getLocation();
            if (geo.x() < minX) minX = geo.x();
            if (geo.x() > maxX) maxX = geo.x();
            if (geo.y() < minY) minY = geo.y();
            if (geo.y() > maxY) maxY = geo.y();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.nodes != null) {
            for (NodeData node : this.nodes.values()) {
                drawNode((NodeData_Class) node, 5, g);
            }
        }
        if (this.edges != null) {
            for (EdgeData edge : this.edges.values()) {
                drawEdge(edge, g);
            }
        }
    }

    private double ScaleY(double y) {
        return ((Height * (y - minY) / (maxY - minY)) / 1.5) + 20;
    }

    private double ScaleX(double x) {
        return ((Width * ((x - minX) / (maxX - minX))) / 1.5) + 20;
    }

    public void drawNode(NodeData_Class n, int r, Graphics g) {
        if (n.getTag() == Regular) g.setColor(Color.BLUE);
        if (n.getTag() == Center) g.setColor(Color.GREEN);
        if (n.getTag() == tsp) g.setColor(Color.RED);
        if (n.getTag() == shortestPath) g.setColor(Color.CYAN);
        GeoLocation_Class geo = (GeoLocation_Class) n.getLocation();
        GeoLocation_Class ScaledGeo = new GeoLocation_Class(ScaleX(geo.x()), ScaleY(geo.y()), 0.0);
        g.fillOval((int) ScaledGeo.x() - r, (int) ScaledGeo.y() - r, 2 * r, 2 * r);
        g.drawString("" + n.getKey(), (int) ScaledGeo.x(), (int) ScaledGeo.y() - 2 * r);
    }

    public void drawEdge(EdgeData e, Graphics g) {
        g.setColor(Color.BLACK);
        GeoLocation_Class src = (GeoLocation_Class) frame.getGraph().getNode(e.getSrc()).getLocation();
        GeoLocation_Class dest = (GeoLocation_Class) frame.getGraph().getNode(e.getDest()).getLocation();
        g.drawLine((int) ScaleX(src.x()), (int) ScaleY(src.y()), (int) ScaleX(dest.x()), (int) ScaleY(dest.y()));
    }
}
