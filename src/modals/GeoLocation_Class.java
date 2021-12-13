package modals;

import api.GeoLocation;

public class GeoLocation_Class implements GeoLocation {
    private  final double x, y, z;

    public GeoLocation_Class(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    //d = ((x2 - x1)^2 + (y2 - y1)^2 + (z2 - z1)^2)^1/2
    public double distance(GeoLocation g) {
        double X = Math.pow((this.x - g.x()), 2);
        double Y = Math.pow((this.y - g.y()), 2);
        double Z = Math.pow((this.z - g.z()), 2);
        return Math.pow((X + Y + Z), 0.5);
    }
}
