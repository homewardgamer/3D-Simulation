package com.oom.group3.util;

public class Vector {
    double x, y, z;

    Vector() {
        x = y = z = 0;
    }

    Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    Vector set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;

        return this;
    }

    Vector set(Vector _new) {
        this.x = _new.x;
        this.y = _new.y;
        this.z = _new.z;

        return this;
    }

    double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    double distance(Vector B) {
        return this.copy().sub(B).length();
    }

    Vector add(Vector B) {
        x += B.x;
        y += B.y;
        z += B.z;

        return this;
    }

    Vector sub(Vector B) {
        x -= B.x;
        y -= B.y;
        z -= B.z;

        return this;
    }

    Vector mul(double m) {
        x *= m;
        y *= m;
        z *= m;

        return this;
    }

    Vector div(double m) throws ArithmeticException {
        if (m == 0) {
            throw new ArithmeticException("Cannot divide by zero!");
        }

        x /= m;
        y /= m;
        z /= m;

        return this;
    }

    double dot(Vector B) {
        return x * B.x + y * B.y + z * B.z;
    }

    Vector cross(Vector B) {
        return new Vector(
                y * B.z - z * B.y,
                z * B.x - x * B.z,
                x * B.y - y * B.x
        );
    }

    Vector normalize() {
        double mag = length();

        if (mag != 0) {
            div(mag);
        }

        return this;
    }

    Vector rotateX(Vector origin, double theta) {
        Vector r = this.copy().sub(origin);

        double _sin = Math.sin(theta), _cos = Math.cos(theta);
        double y = r.y * _cos - r.z * _sin, z = r.y * _sin + r.z * _cos;

        r.y = y;
        r.z = z;

        return this.set(r.add(origin));
    }

    Vector rotateY(Vector origin, double theta) {
        Vector r = this.copy().sub(origin);

        double _sin = Math.sin(theta), _cos = Math.cos(theta);
        double x = this.x * _cos + this.z * _sin, z = this.x * _sin + this.z * _cos;

        r.x = x;
        r.z = z;

        return this.set(r.add(origin));
    }

    Vector rotateZ(Vector origin, double theta) {
        Vector r = this.copy().sub(origin);

        double _sin = Math.sin(theta), _cos = Math.cos(theta);
        double x = this.x * _cos - this.y * _sin, y = this.x * _sin + this.y * _cos;

        r.x = x;
        r.y = y;

        return this.set(r.add(origin));
    }

    Vector rotate(Vector origin, double alpha, double beta, double gamma) {
        return rotateX(origin, alpha).rotateY(origin, beta).rotateZ(origin, gamma);
    }

    double angleBetween(Vector B) {
        return angleBetween(this, B);
    }

    static double angleBetween(Vector A, Vector B) {
        return Math.acos(A.dot(B) / (A.length() * B.length()));
    }

    Vector copy() {
        return new Vector(x, y, z);
    }

    public static void main(String[] args) {
        System.out.println("Testing Vector...");

        {
            Vector test = new Vector(1, 1, 0);
            System.out.println(test);
            System.out.println(test.rotateX(new Vector(), Math.PI / 2));
        }

        {
            Vector test = new Vector(1, 1, 0);
            System.out.println(test);
            System.out.println(test.rotateY(new Vector(), Math.PI / 2));
        }

        {
            Vector test = new Vector(1, -1, 0);
            System.out.println(test);
            System.out.println(test.rotateZ(new Vector(), Math.PI / 2));
        }
    }
}
