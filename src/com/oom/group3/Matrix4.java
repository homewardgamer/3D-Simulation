package com.oom.group3;

public class Matrix4 {
    // list of matrix values
    double[] values;

    public Matrix4(double[] values) {
        this.values = values;
    }

    /**
     * multiply the current matrix with 'other' matrix
     *
     * @param other: the other matrix to multiply with
     * @return Matrix4: the resulting matrix
     */
    public Matrix4 multiply(Matrix4 other) {
        double[] result = new double[16];

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                for (int i = 0; i < 4; i++) {
                    result[row * 4 + col] += this.values[row * 4 + i] * other.values[i * 4 + col];
                }
            }
        }

        return new Matrix4(result);
    }

    /**
     * apply the transform to the given point
     *
     * @param point3D: the point to which to apply the transformation
     * @return Point3D: the resulting point after applying the transformation
     */
    public Point3D transform(Point3D point3D) {
        return new Point3D(
                point3D.x * values[0] + point3D.y * values[4] + point3D.z * values[8] + point3D.w * values[12],
                point3D.x * values[1] + point3D.y * values[5] + point3D.z * values[9] + point3D.w * values[13],
                point3D.x * values[2] + point3D.y * values[6] + point3D.z * values[10] + point3D.w * values[14],
                point3D.x * values[3] + point3D.y * values[7] + point3D.z * values[11] + point3D.w * values[15]
        );
    }
}