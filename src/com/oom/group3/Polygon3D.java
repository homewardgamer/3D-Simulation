package com.oom.group3;

import java.awt.Color;
import java.util.ArrayList;

public class Polygon3D {
    ArrayList<Point3D> points;
    Color color;

    Polygon3D(Color color) {
        this.points = new ArrayList<Point3D>();
        this.color = color;
    }

    void draw() {
        // draw the polygon
    }

    void add(Point3D point) {
        points.add(point);
    }
}
