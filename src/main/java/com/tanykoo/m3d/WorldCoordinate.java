package com.tanykoo.m3d;

/**
 * 世界坐标
 * @Author ThinkPad
 * Created : 2018-08-22 9:39
 * @Since
 */
public class WorldCoordinate {
    private Coordinate3D coordinate3D;

    public WorldCoordinate(){
        coordinate3D = new Coordinate3D(0,0,0);
    }
    public WorldCoordinate(Coordinate3D coordinate3D){
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
