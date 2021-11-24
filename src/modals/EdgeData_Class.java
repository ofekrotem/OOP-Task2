package modals;

import api.EdgeData;

public class EdgeData_Class implements EdgeData {
    private int src, dest, tag;
    private double weight;
    private String info;

    public EdgeData_Class(int src, int dest, int tag, double weight, String info) {
        this.src = src;
        this.dest = dest;
        this.tag = tag;
        this.weight = weight;
        this.info = info;
    }

    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
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
