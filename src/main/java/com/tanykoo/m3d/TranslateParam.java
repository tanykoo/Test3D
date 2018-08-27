package com.tanykoo.m3d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 平移参数
 * @Author ThinkPad
 * Created : 2018-08-27 15:19
 * @Since
 */
public class TranslateParam {
    //平移向量
    public Coordinate3D vector;

    //平移矩阵
    public Matrix getMatrix(){
        double[][] d = new double[4][4];
        d[0][0] = 1;
        d[1][1] = 1;
        d[2][2] = 1;
        d[3][3] = 1;

        d[0][3] = vector.getX();
        d[1][3] = vector.getY();
        d[2][3] = vector.getZ();

        return new Matrix(d);
    }
}
