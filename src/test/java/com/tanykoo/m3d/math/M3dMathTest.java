package com.tanykoo.m3d.math;

import com.tanykoo.m3d.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @Author ThinkPad
 * Created : 2018-08-27 14:42
 * @Since
 */
public class M3dMathTest {
    private static Logger logger = LoggerFactory.getLogger(M3dMathTest.class);

    public static void main(String[] args) {
        double[][] x = new double[][]{{1,3,0,1}};

        RolateParam rolateParam = new RolateParam(Math.PI/4,new Coordinate3D(0,0,1),new Coordinate3D(1,1,0));


        TranslateParam translateParam = new TranslateParam();
        translateParam.setVector(new Coordinate3D(0,0,0));

        ZoomParam zoomParam = new ZoomParam();


        ExternalParam externalParam = new ExternalParam();
        externalParam.setRolateMatrix(rolateParam.getMatrix());
        externalParam.setTranslateMatrix(translateParam.getMatrix());
        externalParam.setZoomMatrix(zoomParam.getMatrix());


        Matrix matrix1 = new Matrix(x);

        Matrix matrix1n = M3dMath.getReverseMartrix(rolateParam.getMatrix());
        System.out.println(externalParam.getTransformMatrix());
        System.out.println(M3dMath.mutl(matrix1,externalParam.getTransformMatrix()));

    }
}
