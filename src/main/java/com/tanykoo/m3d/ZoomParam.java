package com.tanykoo.m3d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author ThinkPad
 * Created : 2018-08-24 10:55
 * @Since
 */
public class ZoomParam {
    //在X轴的缩放比例
    private double zoomX = 1;
    //在Y轴的缩放比例
    private double zoomY = 1;
    //在Z轴的缩放比例
    private double zoomZ = 1;

    public ZoomParam(){
    }


    public double getZoomX() {
        return zoomX;
    }

    public void setZoomX(double zoomX) {
        this.zoomX = zoomX;
    }

    public double getZoomY() {
        return zoomY;
    }

    public void setZoomY(double zoomY) {
        this.zoomY = zoomY;
    }

    public double getZoomZ() {
        return zoomZ;
    }

    public void setZoomZ(double zoomZ) {
        this.zoomZ = zoomZ;
    }

    //缩放矩阵
    public Matrix getMatrix(){
        double[][] doubles = new double[4][4];

        doubles[0][0] = zoomX;
        doubles[1][1] = zoomY;
        doubles[2][2] = zoomZ;
        doubles[3][3] = 1;

        return new Matrix(doubles);
    }
}
