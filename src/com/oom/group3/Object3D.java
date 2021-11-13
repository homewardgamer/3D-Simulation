package com.oom.group3;

import java.util.ArrayList;

public class Object3D {
    ArrayList<Polygon3D> polygons;

    Object3D() {
        this.polygons = new ArrayList<Polygon3D>();
    }

    void draw() {
        // draw the object
    }

    void add(Polygon3D polygon) {
        polygons.add(polygon);
    }
}
