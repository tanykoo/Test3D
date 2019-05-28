package com.tanykoo;

import com.tanykoo.m3d.math.M3dMath;
import com.tanykoo.m3d.math.Matrix;

/**
 * @Author ThinkPad
 * Created : 2019-05-28 17:10
 * @Since
 */
public class ColorMe {
    Color1 color1 = new Color1(67,30,30);
    public static void main(String[] args) {
        Matrix matrix = new Matrix(new double[][]{{255,255,255}});
        Matrix matrix2 = new Matrix(new double[][]{
                {1,0,0},{0,1,0},{0,0,1}});
        System.out.println(M3dMath.mutl(matrix,matrix2));


        ColorMe colorMe = new ColorMe();
        System.out.println(M3dMath.mutl(M3dMath.mutl(matrix,colorMe.color1.getHMatrix()),colorMe.color1.getLMatrix()));

    }

    private class Color1{
        private int h;
        private int s;
        private int l;
        private final int MAX_H=240;
        private final int MAX_L=240;

        Color1(int h, int s, int l){
            this.h = h%MAX_H;
            this.s = s;
            this.l = l;
        }

        private Matrix getHMatrix(){
            double q = 0;
            int p = 0;
            int o = 0;
            p = h/(MAX_H/6);
            o = ((p%5)+1)/2;
            q = (double)(h%MAX_H)/ (double)(MAX_H/6);
            q = (p%2 == 0)? q - (int)q :(int)(q+1) - q;
            System.out.println("p=" + p + "  q=" + q + "  o="+o);
            Matrix matrix = new Matrix(new double[][]{
                    {o==0?1:((p==1||p==4)?q:0),0,0},
                    {0,o==1?1:((p==0||p==3)?q:0),0},
                    {0,0,o==2?1:((p==2||p==5)?q:0)},
            });
            return matrix;
        }
        private Matrix getLMatrix(){
            double q = l/(MAX_L/2.0);

            Matrix matrix = new Matrix(new double[][]{
                    {q,0,0},
                    {0,q,0},
                    {0,0,q}
            });
            return matrix;
        }
    }
}
