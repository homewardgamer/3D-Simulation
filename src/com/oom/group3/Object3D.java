package com.oom.group3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Object3D {
    public ArrayList<Polygon3D>polygons;
    public Object3D(){
        polygons = new ArrayList<>();
        
        //each face of a the cube consists of two triangles..
        // A
        polygons.add(new Polygon3D(new Point3D(-100, 100, 100, 1), new Point3D(100, 100, 100, 1),
                new Point3D(-100, 100, -100, 1), Color.CYAN));
        // B
        polygons.add(new Polygon3D(new Point3D(100, 100, 100, 1), new Point3D(100, 100, -100, 1),
                new Point3D(-100, 100, -100, 1), Color.CYAN));
        // C
        polygons.add(new Polygon3D(new Point3D(100, -100, 100, 1), new Point3D(100, 100, -100, 1),
                new Point3D(100, 100, 100, 1), Color.CYAN));
        // D
        polygons.add(new Polygon3D(new Point3D(100, -100, 100, 1), new Point3D(100, -100, -100, 1),
                new Point3D(100, 100, -100, 1), Color.CYAN));
        // E
        polygons.add(new Polygon3D(new Point3D(-100, -100, 100, 1), new Point3D(100, -100, 100, 1),
                new Point3D(-100, 100, 100, 1), Color.CYAN));
        // F
        polygons.add(new Polygon3D(new Point3D(100, -100, 100, 1), new Point3D(100, 100, 100, 1),
                new Point3D(-100, 100, 100, 1), Color.CYAN));
        // G
        polygons.add(new Polygon3D(new Point3D(-100, -100, 100, 1), new Point3D(-100, 100, 100, 1),
                new Point3D(-100, -100, -100, 1), Color.CYAN));
        // H
        polygons.add(new Polygon3D(new Point3D(-100, 100, 100, 1), new Point3D(-100, 100, -100, 1),
                new Point3D(-100, -100, -100, 1), Color.CYAN));
        // I
        polygons.add(new Polygon3D(new Point3D(-100, 100, -100, 1), new Point3D(100, 100, -100, 1),
                new Point3D(-100, -100, -100, 1), Color.CYAN));
        // J
        polygons.add(new Polygon3D(new Point3D(-100, -100, -100, 1), new Point3D(100, 100, -100, 1),
                new Point3D(100, -100, -100, 1), Color.CYAN));
        // K
        polygons.add(new Polygon3D(new Point3D(100, -100, 100, 1), new Point3D(-100, -100, 100, 1),
                new Point3D(-100, -100, -100, 1), Color.CYAN));
        // L
        polygons.add(new Polygon3D(new Point3D(-100, -100, -100, 1), new Point3D(100, -100, -100, 1),
                new Point3D(100, -100, 100, 1), Color.CYAN));
    }

    public ArrayList<Polygon3D> getPolygons() {
        return polygons;
    }
}
