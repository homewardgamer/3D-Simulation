package com.oom.group3;

import java.awt.Color;

// Polygon3D is a polygon (triangle) in 3D space
public class Polygon3D {
    public Point3D A, B, C;
    public Color color;

    // to store three vertices and color of the Triangle (the simplest polygon)
    public Polygon3D(Point3D A, Point3D B, Point3D C, Color color) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.color = color;
    }
}
