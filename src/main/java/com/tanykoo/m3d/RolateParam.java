package com.tanykoo.m3d;

import com.tanykoo.m3d.math.M3dMath;
import com.tanykoo.m3d.math.Matrix;

/**
 * 旋转参数
 *
 * @Author ThinkPad
 * Created : 2018-08-24 10:54
 * @Since
 */
public class RolateParam {

    ZoomParam zoomParam;

    //绕任意轴旋转幅度
    private double sita;

    //任意轴向量
    private Coordinate3D vector;

    //单位向量
    private Coordinate3D vector1;

    //过任意轴的点
    private Coordinate3D point;

    public RolateParam() {
        this(0);
    }

    /**
     * 绕Z轴旋转sita角度
     * @param sita
     */
    public RolateParam(double sita) {
        this(sita,new Coordinate3D(0,0,1));
    }

    /**
     * 绕过圆心点的向量轴vector 旋转sita角度
     * @param sita
     * @param vector
     */
    public RolateParam(double sita,Coordinate3D vector) {
        this(sita,vector,new Coordinate3D(0,0,0));
    }

    /**
     * 绕过point点的向量轴vector旋转sita角度
     * @param sita
     * @param vector
     * @param point
     */
    public RolateParam(double sita,Coordinate3D vector,Coordinate3D point) {
        this(sita,vector,point,new ZoomParam());
    }
    public RolateParam(double sita,Coordinate3D vector,Coordinate3D point,ZoomParam zoomParam) {
        if(vector.getX() ==0 && vector.getY()==0&& vector.getZ()==0){
            throw new RuntimeException("Vector's length must be not zero");
        }
        this.sita = sita ;
        this.vector = vector;
        double length = Math.sqrt(vector.getX()*vector.getX() + vector.getY() * vector.getY() + vector.getZ() * vector.getZ());
        this.vector1 = new Coordinate3D(vector.getX()/length ,vector.getY()/length ,vector.getZ()/length);
        this.point = point;
        this.zoomParam = zoomParam;
    }

    //旋转矩阵
    public Matrix getMatrix(){
        double[][] doubles = new double[4][4];
        double cos = Math.cos(sita);
        double sin = Math.sin(sita);
        double x2 = Math.pow(vector1.getX(),2);
        double y2 = Math.pow(vector1.getY(),2);
        double z2 = Math.pow(vector1.getZ(),2);

        double xy = vector1.getX() * vector1.getY();
        double xz = vector1.getX() * vector1.getZ();
        double yz = vector1.getY() * vector1.getZ();

        doubles[0][0] = x2 * (1-cos) + cos;
        doubles[1][0] = xy * (1-cos) + vector1.getZ() * sin;
        doubles[2][0] = xz * (1-cos) - vector1.getY() * sin;
        doubles[3][0] = 0;

        doubles[0][1] = xy * (1-cos) - vector1.getZ() * sin;
        doubles[1][1] = y2 * (1-cos) + cos;
        doubles[2][1] = yz * (1-cos) + vector1.getX() * sin;
        doubles[3][1] = 0;

        doubles[0][2] = xz * (1-cos) + vector1.getY() * sin;
        doubles[1][2] = yz * (1-cos) - vector1.getX() * sin;
        doubles[2][2] = z2 * (1-cos) + cos;
        doubles[3][2] = 0;

        doubles[0][3] = 0;
        doubles[1][3] = 0;
        doubles[2][3] = 0;
        doubles[3][3] = 1;
        Matrix roulate = new Matrix(doubles);

        Matrix zoomPoint = M3dMath.mutl(point.getMatrix(),zoomParam.getMatrix());

        Coordinate3D newPoint = new Coordinate3D(zoomPoint.getMatrix()[0][0],zoomPoint.getMatrix()[0][1],zoomPoint.getMatrix()[0][2]);

        TranslateParam translateParam = new TranslateParam();
        translateParam.setVector(newPoint);

        TranslateParam translateParam2 = new TranslateParam();
        translateParam2.setVector(new Coordinate3D(-newPoint.getX(),-newPoint.getY(),-newPoint.getZ()));

        return M3dMath.mutl(M3dMath.mutl(translateParam2.getMatrix(),roulate),translateParam.getMatrix());

    }

}
