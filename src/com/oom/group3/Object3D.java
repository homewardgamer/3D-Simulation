package com.oom.group3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Object3D {
    public ArrayList<Polygon3D>polygons;
    public Object3D(){
        polygons = new ArrayList<>();

        // A
        polygons.add(new Polygon3D(new Point3D(-100, 100, 100, 1), new Point3D(100, 100, 100, 1),
                new Point3D(-100, 100, -100, 1), Color.WHITE));
        // B
        polygons.add(new Polygon3D(new Point3D(100, 100, 100, 1), new Point3D(100, 100, -100, 1),
                new Point3D(-100, 100, -100, 1), Color.WHITE));
        // C
        polygons.add(new Polygon3D(new Point3D(100, -100, 100, 1), new Point3D(100, 100, -100, 1),
                new Point3D(100, 100, 100, 1), Color.WHITE));
        // D
        polygons.add(new Polygon3D(new Point3D(100, -100, 100, 1), new Point3D(100, -100, -100, 1),
                new Point3D(100, 100, -100, 1), Color.WHITE));
        // E
        polygons.add(new Polygon3D(new Point3D(-100, -100, 100, 1), new Point3D(100, -100, 100, 1),
                new Point3D(-100, 100, 100, 1), Color.WHITE));
        // F
        polygons.add(new Polygon3D(new Point3D(100, -100, 100, 1), new Point3D(100, 100, 100, 1),
                new Point3D(-100, 100, 100, 1), Color.WHITE));
        // G
        polygons.add(new Polygon3D(new Point3D(-100, -100, 100, 1), new Point3D(-100, 100, 100, 1),
                new Point3D(-100, -100, -100, 1), Color.WHITE));
        // H
        polygons.add(new Polygon3D(new Point3D(-100, 100, 100, 1), new Point3D(-100, 100, -100, 1),
                new Point3D(-100, -100, -100, 1), Color.WHITE));
        // I
        polygons.add(new Polygon3D(new Point3D(-100, 100, -100, 1), new Point3D(100, 100, -100, 1),
                new Point3D(-100, -100, -100, 1), Color.WHITE));
        // J
        polygons.add(new Polygon3D(new Point3D(-100, -100, -100, 1), new Point3D(100, 100, -100, 1),
                new Point3D(100, -100, -100, 1), Color.WHITE));
        // K
        polygons.add(new Polygon3D(new Point3D(100, -100, 100, 1), new Point3D(-100, -100, 100, 1),
                new Point3D(-100, -100, -100, 1), Color.WHITE));
        // L
        polygons.add(new Polygon3D(new Point3D(-100, -100, -100, 1), new Point3D(100, -100, -100, 1),
                new Point3D(100, -100, 100, 1), Color.WHITE));
    }

    public ArrayList<Polygon3D> getPolygons() {
        return polygons;
    }
}
