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
    private float zoomX;
    //在Y轴的缩放比例
    private float zoomY;
    //在Z轴的缩放比例
    private float zoomZ;

    public float getZoomX() {
        return zoomX;
    }

    public void setZoomX(float zoomX) {
        this.zoomX = zoomX;
    }

    public float getZoomY() {
        return zoomY;
    }

    public void setZoomY(float zoomY) {
        this.zoomY = zoomY;
    }

    public float getZoomZ() {
        return zoomZ;
    }

    public void setZoomZ(float zoomZ) {
        this.zoomZ = zoomZ;
    }
}
