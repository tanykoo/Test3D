package com.tanykoo.m3d.math;

import com.tanykoo.m3d.Coordinate3D;
import com.tanykoo.m3d.Matrix;
import com.tanykoo.m3d.RolateParam;

/**
 * @Author ThinkPad
 * Created : 2018-08-24 17:41
 * @Since
 */
public class M3dMath {

    public static Matrix mutl(Matrix matrix1 , Matrix matrix2) throws IndexOutOfBoundsException{
        if(matrix1.getColumn() != matrix2.getRow()){
            throw new IndexOutOfBoundsException(String.format("Matrix can not multiplicative,because matrix1's column [%d] is not equeas to matrix2's row[%d]",matrix1.getColumn(),matrix2.getRow()));
        }
        double[][] matrix = new double[matrix1.getRow()][matrix2.getColumn()];

        for(int i = 0 ; i < matrix1.getRow(); i++){
            for(int j = 0 ; j < matrix2.getColumn() ; j++){
                for(int k = 0; k< matrix1.getColumn() ; k++){
                    matrix[i][j] += matrix1.getMatrix()[i][k] * matrix2.getMatrix()[k][j];
                }
            }
        }
        Matrix matrix3 = new Matrix(matrix);

        return matrix3;
    }

    public static Matrix add(Matrix matrix1 , Matrix matrix2) throws IndexOutOfBoundsException{
        if(matrix1.getColumn() != matrix2.getColumn() || matrix1.getRow() != matrix2.getRow()){
            throw new IndexOutOfBoundsException(String.format("Matrix can not be add,because matrix1 [%d x %d] is not equeas to matrix2 [%d x %d]",matrix1.getRow(),matrix1.getColumn(),matrix2.getRow(),matrix2.getColumn()));
        }
        double[][] matrix = new double[matrix1.getRow()][matrix1.getColumn()];
        for(int i = 0 ; i < matrix1.getRow(); i++){
            for(int j = 0 ; j < matrix1.getColumn() ; j++){
                matrix[i][j] = matrix1.getMatrix()[i][j] + matrix2.getMatrix()[i][j];
            }
        }
        Matrix matrix3 = new Matrix(matrix);
        return matrix3;
    }

    public static Matrix sub(Matrix matrix1 , Matrix matrix2) throws IndexOutOfBoundsException{
        if(matrix1.getColumn() != matrix2.getColumn() || matrix1.getRow() != matrix2.getRow()){
            throw new IndexOutOfBoundsException(String.format("Matrix can not be add,because matrix1 [%d x %d] is not equeas to matrix2 [%d x %d]",matrix1.getRow(),matrix1.getColumn(),matrix2.getRow(),matrix2.getColumn()));
        }
        double[][] matrix = new double[matrix1.getRow()][matrix1.getColumn()];
        for(int i = 0 ; i < matrix1.getRow(); i++){
            for(int j = 0 ; j < matrix1.getColumn() ; j++){
                matrix[i][j] = matrix1.getMatrix()[i][j] - matrix2.getMatrix()[i][j];
            }
        }
        Matrix matrix3 = new Matrix(matrix);
        return matrix3;
    }



    public static Matrix getReverseMartrix(Matrix data) {
        double[][] newdata = new double[data.getRow()][data.getColumn()];
        double A = getMartrixResult(data.getMatrix());

        for(int i=0; i<data.getRow(); i++) {
            for(int j=0; j<data.getColumn(); j++) {
                if((i+j)%2 == 0) {
                    newdata[i][j] = getMartrixResult(getConfactor(data.getMatrix(), i+1, j+1)) / A;
                }else {
                    newdata[i][j] = -getMartrixResult(getConfactor(data.getMatrix(), i+1, j+1)) / A;
                }

            }
        }
        newdata = trans(newdata);

        return new Matrix(newdata);
    }

    /*
     * 计算行列式的值
     */
    private static double getMartrixResult(double[][] data) {
        /*
         * 二维矩阵计算
         */
        if(data.length == 2) {
            return data[0][0]*data[1][1] - data[0][1]*data[1][0];
        }
        /*
         * 二维以上的矩阵计算
         */
        float result = 0;
        int num = data.length;
        double[] nums = new double[num];
        for(int i=0; i<data.length; i++) {
            if(i%2 == 0) {
                nums[i] = data[0][i] * getMartrixResult(getConfactor(data, 1, i+1));
            }else {
                nums[i] = -data[0][i] * getMartrixResult(getConfactor(data, 1, i+1));
            }
        }
        for(int i=0; i<data.length; i++) {
            result += nums[i];
        }

//      System.out.println(result);
        return result;
    }

    /*
     * 求(h,v)坐标的位置的余子式
     */
    private static double[][] getConfactor(double[][] data, int h, int v) {
        int H = data.length;
        int V = data[0].length;
        double[][] newdata = new double[H-1][V-1];
        for(int i=0; i<newdata.length; i++) {
            if(i < h-1) {
                for(int j=0; j<newdata[i].length; j++) {
                    if(j < v-1) {
                        newdata[i][j] = data[i][j];
                    }else {
                        newdata[i][j] = data[i][j+1];
                    }
                }
            }else {
                for(int j=0; j<newdata[i].length; j++) {
                    if(j < v-1) {
                        newdata[i][j] = data[i+1][j];
                    }else {
                        newdata[i][j] = data[i+1][j+1];
                    }
                }
            }
        }

//      for(int i=0; i<newdata.length; i ++)
//          for(int j=0; j<newdata[i].length; j++) {
//              System.out.println(newdata[i][j]);
//          }
        return newdata;
    }

    private static double[][] trans(double[][] newdata) {
        // TODO Auto-generated method stub
        double[][] newdata2 = new double[newdata[0].length][newdata.length];
        for(int i=0; i<newdata.length; i++)
            for(int j=0; j<newdata[0].length; j++) {
                newdata2[j][i] = newdata[i][j];
            }
        return newdata2;
    }


    public static void main(String[] args) {
        Coordinate3D vector = new Coordinate3D(Math.sqrt(1.0/3),Math.sqrt(1.0/3),Math.sqrt(1.0/3));

        try {
            RolateParam rolateParam = new RolateParam(Math.PI *2/3 ,vector);
            Matrix rolateM = rolateParam.getMatrix();
            double[][] doubles = new double[][]{{3,-3,3,1}};
            System.out.println(M3dMath.mutl(new Matrix(doubles),rolateM));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
