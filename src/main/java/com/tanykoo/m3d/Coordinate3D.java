package com.tanykoo.m3d;


/**
 * @Author ThinkPad
 * Created : 2018-08-24 9:53
 * @Since
 */
public class Coordinate3D {
    private double x;
    private double y;
    private double z;

    public Coordinate3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
