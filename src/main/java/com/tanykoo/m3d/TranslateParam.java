package com.tanykoo.m3d;

import com.tanykoo.m3d.math.Matrix;

/**
 * 平移参数
 * @Author ThinkPad
 * Created : 2018-08-27 15:19
 * @Since
 */
public class TranslateParam {
    //平移向量
    private Coordinate3D vector;


    public TranslateParam(){
        this(new Coordinate3D(0,0,0));
    }
    public TranslateParam(Coordinate3D vector){
        this.vector = vector;
    }

    public Coordinate3D getVector() {
        return vector;
    }

    public void setVector(Coordinate3D vector) {
        this.vector = vector;
    }

    //平移矩阵
    public Matrix getMatrix(){
        double[][] d = new double[4][4];
        d[0][0] = 1;
        d[1][1] = 1;
        d[2][2] = 1;
        d[3][3] = 1;

        d[3][0] = vector.getX();
        d[3][1] = vector.getY();
        d[3][2] = vector.getZ();

        return new Matrix(d);
    }
}
