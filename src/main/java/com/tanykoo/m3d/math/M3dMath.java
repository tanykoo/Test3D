package com.tanykoo.m3d.math;

import com.tanykoo.m3d.Matrix;

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

    public static void main(String[] args) {
        double[][] d1 = new double[][]{{1,2,1},{0,1,0},{3,3,3}};
        double[][] d2 = new double[][]{{3,2,1},{0,1,0},{3,4,3}};
        Matrix matrix1 = new Matrix(d1);
        Matrix matrix2 = new Matrix(d2);

        Matrix matrix3 = M3dMath.mutl(matrix1,matrix2);

        Matrix matrix4 = M3dMath.sub(matrix1,matrix2);

        System.out.println(matrix4);
    }
}
