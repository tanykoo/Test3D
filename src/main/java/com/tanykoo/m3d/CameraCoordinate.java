package com.tanykoo.m3d;

/**
 * 相机坐标
 * @Author ThinkPad
 * Created : 2018-08-24 10:21
 * @Since
 */
public class CameraCoordinate {
    private Coordinate3D coordinate3D;

    public CameraCoordinate(){
        coordinate3D = new Coordinate3D(0,0,0);
    }
    public CameraCoordinate(Coordinate3D coordinate3D){
        this.coordinate3D = coordinate3D;
    }

    public Coordinate3D getCoordinate3D() {
        return coordinate3D;
    }

    public void setCoordinate3D(Coordinate3D coordinate3D) {
        this.coordinate3D = coordinate3D;
    }

    public void getX(){
        coordinate3D.getX();
    }
}
