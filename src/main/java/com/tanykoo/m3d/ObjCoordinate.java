package com.tanykoo.m3d;

/**
 * 物理坐标
 * @Author ThinkPad
 * Created : 2018-08-24 10:20
 * @Since
 */
public class ObjCoordinate {
    private Coordinate3D coordinate3D;

    public ObjCoordinate(){
        coordinate3D = new Coordinate3D(0,0,0);
    }
    public ObjCoordinate(Coordinate3D coordinate3D){
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
