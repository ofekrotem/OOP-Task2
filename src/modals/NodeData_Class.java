package modals;

import api.GeoLocation;
import api.NodeData;

public class NodeData_Class implements NodeData {
    private int key, tag;
    private GeoLocation geo;
    private double weight;
    private String info;

    public NodeData_Class(int key, GeoLocation geo, double weight, String info, int tag) {
        this.key = key;
        this.geo = geo;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.geo;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.geo = p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
