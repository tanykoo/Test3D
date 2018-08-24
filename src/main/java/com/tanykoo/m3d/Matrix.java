package com.tanykoo.m3d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author ThinkPad
 * Created : 2018-08-24 17:39
 * @Since
 */
public class Matrix {
    //矩阵
    private double[][] matrix;

    public Matrix(int row,int column){
        matrix = new double[row][column];
    }

    public Matrix(double[][] matrix){
        this.matrix = matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public int getColumn(){
        if(matrix.length > 0 ){
            return matrix[0].length;
        }else
            return 0;
    }
    public int getRow(){
        return matrix.length;
    }

    @Override
    public String toString() {
        String str = "";
        for(int i = 0; i < matrix.length;i++){
            str+= "[";
            for(int j = 0 ; j< matrix[i].length;j++){
                str += matrix[i][j];
                if(j < matrix[i].length-1){
                    str += ", ";
                }
            }
            str +="]\n";
        }
        return str;
    }
}
