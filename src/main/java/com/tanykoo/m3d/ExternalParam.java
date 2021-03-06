package com.tanykoo.m3d;

import com.tanykoo.m3d.math.M3dMath;
import com.tanykoo.m3d.math.Matrix;

/**
 * 外部参数
 * @Author ThinkPad
 * Created : 2018-08-22 10:07
 * @Since
 */
public class ExternalParam {
    //平移矩阵
    private Matrix translateMatrix;
    //旋转矩阵
    private Matrix rolateMatrix;
    //缩放矩阵
    private Matrix zoomMatrix;

    public Matrix getTranslateMatrix() {
        return translateMatrix;
    }

    public void setTranslateMatrix(Matrix translateMatrix) {
        this.translateMatrix = translateMatrix;
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

    public Matrix getTransformMatrix(){
        return M3dMath.mutl(M3dMath.mutl(zoomMatrix,rolateMatrix),translateMatrix);
    }
}
