package com.oom.group3;

import java.awt.Color;

public class Polygon3D {
    public Point3D v1, v2, v3;
    public Color color;

    public Polygon3D(Point3D v1, Point3D v2, Point3D v3, Color color) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.color = color;
    }
}