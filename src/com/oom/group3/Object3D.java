package com.oom.group3;

import java.awt.Color;
import java.util.ArrayList;

public class Object3D {
    public ArrayList<Polygon3D>polygons;
    public Color color;

    public Object3D() {
        color = Color.CYAN;
        polygons = new ArrayList<>();
        
        // each face of the cube consists of two triangles each

        // front - top-left
        polygons.add(new Polygon3D(
                new Point3D(-100, -100, 100, 1),
                new Point3D(100, -100, 100, 1),
                new Point3D(-100, 100, 100, 1),
                color
        ));

        // front - bottom-right
        polygons.add(new Polygon3D(
                new Point3D(100, -100, 100, 1),
                new Point3D(100, 100, 100, 1),
                new Point3D(-100, 100, 100, 1),
                color
        ));

        // right - bottom-left
        polygons.add(new Polygon3D(
                new Point3D(100, -100, 100, 1),
                new Point3D(100, 100, -100, 1),
                new Point3D(100, 100, 100, 1),
                color
        ));

        // right - top-right
        polygons.add(new Polygon3D(
                new Point3D(100, -100, 100, 1),
                new Point3D(100, -100, -100, 1),
                new Point3D(100, 100, -100, 1),
                color
        ));

        // left - top-right
        polygons.add(new Polygon3D(
                new Point3D(-100, -100, 100, 1),
                new Point3D(-100, 100, 100, 1),
                new Point3D(-100, -100, -100, 1),
                color
        ));

        // left - bottom-left
        polygons.add(new Polygon3D(
                new Point3D(-100, 100, 100, 1),
                new Point3D(-100, 100, -100, 1),
                new Point3D(-100, -100, -100, 1),
                color
        ));

        // top - bottom-left
        polygons.add(new Polygon3D(
                new Point3D(100, -100, 100, 1),
                new Point3D(-100, -100, 100, 1),
                new Point3D(-100, -100, -100, 1),
                color
        ));

        // top - top-right
        polygons.add(new Polygon3D(
                new Point3D(-100, -100, -100, 1),
                new Point3D(100, -100, -100, 1),
                new Point3D(100, -100, 100, 1),
                color
        ));

        // bottom - bottom-left
        polygons.add(new Polygon3D(
                new Point3D(-100, 100, 100, 1),
                new Point3D(100, 100, -100, 1),
                new Point3D(-100, 100, -100, 1),
                color
        ));

        // bottom - top-right
        polygons.add(new Polygon3D(
                new Point3D(-100, 100, 100, 1),
                new Point3D(100, 100, 100, 1),
                new Point3D(100, 100, -100, 1),
               color
        ));

        // back - bottom-left
        polygons.add(new Polygon3D(
                new Point3D(-100, 100, -100, 1),
                new Point3D(100, 100, -100, 1),
                new Point3D(-100, -100, -100, 1),
                color
        ));

        // back - top-right
        polygons.add(new Polygon3D(
                new Point3D(-100, -100, -100, 1),
                new Point3D(100, 100, -100, 1),
                new Point3D(100, -100, -100, 1),
                color
        ));
    }

    public ArrayList<Polygon3D> getPolygons() {
        return polygons;
    }
}
