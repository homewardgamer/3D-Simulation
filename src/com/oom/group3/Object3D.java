package com.oom.group3;

import java.awt.Color;
import java.util.ArrayList;

// this is a blueprint to implement a 3D object
// Object3D is an object in 3D space
public abstract class Object3D {
    public ArrayList<Polygon3D> polygons = new ArrayList<>();
    public Color color = Color.WHITE;

    // return the list of polygons
    public ArrayList<Polygon3D> getPolygons() {
        return polygons;
    }
}
