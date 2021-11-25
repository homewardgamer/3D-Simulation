package com.oom.group3;

import java.awt.Color;

//Here Polygon3D is the Triangle
public class Polygon3D {
    public Point3D v1, v2, v3;
    public Color color;

    public Polygon3D(Point3D v1, Point3D v2, Point3D v3, Color color) {          //to render three points  and color of Triangle 
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.color = color;
    }
}