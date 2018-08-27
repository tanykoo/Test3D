package com.tanykoo.m3d.math;

import com.tanykoo.m3d.Matrix;
import com.tanykoo.m3d.RolateParam;
import com.tanykoo.m3d.ZoomParam;
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
        double[][] x = new double[][]{{3,5,1,1},
                {3,3,3,1}};
        ZoomParam zoomParam = new ZoomParam();
        zoomParam.setZoomX(1.5);
        zoomParam.setZoomY(2);
        zoomParam.setZoomZ(4);

        RolateParam rolateParam = new RolateParam(Math.PI/3);

        Matrix matrix1 = new Matrix(x);

        Matrix matrix1n = M3dMath.getReverseMartrix(rolateParam.getMatrix());
        System.out.println(M3dMath.mutl(matrix1,rolateParam.getMatrix()));
        System.out.println(M3dMath.mutl(M3dMath.mutl(matrix1,rolateParam.getMatrix()),matrix1n));

    }
}
