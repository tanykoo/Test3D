package com.tanykoo.m3d;

/**
 * @Author ThinkPad
 * Created : 2018-08-22 10:07
 * @Since
 */
public class ExternalParam {
    //物体原点在世界坐标系中的坐标
    private WorldCoordinate oCoorDinate;
    //旋转矩阵
    private Matrix rolateMatrix;

    //缩放矩阵
    private Matrix zoomMatrix;

    public WorldCoordinate getoCoorDinate() {
        return oCoorDinate;
    }

    public void setoCoorDinate(WorldCoordinate oCoorDinate) {
        this.oCoorDinate = oCoorDinate;
    }

    public Matrix getRolateMatrix() {
        return rolateMatrix;
    }

    public void setRolateMatrix(Matrix rolateMatrix) {
        this.rolateMatrix = rolateMatrix;
    }

    public Matrix getZoomMatrix() {
        return zoomMatrix;
    }

    public void setZoomMatrix(Matrix zoomMatrix) {
        this.zoomMatrix = zoomMatrix;
    }
}
