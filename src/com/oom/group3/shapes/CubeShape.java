package com.oom.group3.shapes;

import com.oom.group3.Object3D;
import com.oom.group3.Point3D;
import com.oom.group3.Polygon3D;

import java.awt.Color;

public class CubeShape extends Object3D {
    public CubeShape() {
        // set the object color
        color = Color.CYAN;
        
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
}
