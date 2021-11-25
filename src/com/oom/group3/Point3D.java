package com.oom.group3;

import com.oom.group3.util.Vector;

// Point3D is a point in 3D space
public class Point3D extends Vector {
    // temporary value for 4th dimension of the 'Matrix4'
    // used for the transformations
    public double w;

    // to store coordinates of a Vertex
    public Point3D(double x, double y, double z, double w) {
        super(x, y, z);
        this.w = w;
    }
}
