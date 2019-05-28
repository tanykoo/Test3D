package com.tanykoo.m3d;

import com.tanykoo.m3d.math.M3dMath;
import com.tanykoo.m3d.math.Matrix;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Spirit{
    Coordinate3D[] points ;
    List<Integer[]> index;
    Point[] screenPoints;

    ExternalParam externalParam;
    transient MouseListener mouseListener;
    boolean newEventsOnly = false;

    M3DPanel parent;


    public M3DPanel getParent() {
        return parent;
    }

    public void setParent(M3DPanel parent) {
        this.parent = parent;
    }

    public Spirit(){
        points = new Coordinate3D[11];
        points[0] = new Coordinate3D(0.5,0.5,0);
        points[1] = new Coordinate3D(0.5,-0.5,0);
        points[2] = new Coordinate3D(-0.5,-0.5,0);
        points[3] = new Coordinate3D(-0.5,0.5,0);

        points[4] = new Coordinate3D(0,0,0);
        points[5] = new Coordinate3D(0,1,0);
        points[6] = new Coordinate3D(1,0,0);

        points[7] = new Coordinate3D(-0.1,0.9,0);
        points[8] = new Coordinate3D(0.1,0.9,0);
        points[9] = new Coordinate3D(0.9,0.1,0);
        points[10] = new Coordinate3D(0.9,-0.1,0);

        index = new ArrayList();
        Integer[] index1 = new Integer[4];
        index1[0] = 0;
        index1[1] = 1;
        index1[2] = 2;
        index1[3] = 3;

        Integer[] index2 = new Integer[2];
        index2[0] = 4;
        index2[1] = 5;

        Integer[] index3 = new Integer[2];
        index3[0] = 4;
        index3[1] = 6;

        Integer[] index4 = new Integer[3];
        index4[0] = 5;
        index4[1] = 7;
        index4[2] = 8;

        Integer[] index5 = new Integer[3];
        index5[0] = 6;
        index5[1] = 9;
        index5[2] = 10;

        index.add(index1);
        index.add(index2);
        index.add(index3);
        index.add(index4);
        index.add(index5);

        externalParam = new ExternalParam();
        externalParam.setZoomMatrix(new ZoomParam(50,50,50).getMatrix());
        externalParam.setTranslateMatrix(new TranslateParam(new Coordinate3D(100,100,0)).getMatrix());
        externalParam.setRolateMatrix(new RolateParam(0,new Coordinate3D(0,0,1),new Coordinate3D(0,0,0),new ZoomParam(50,50,0)).getMatrix());



        screenPoints = new Point[points.length];

        for(int i = 0 ; i< screenPoints.length;i++){
            Matrix newPoint = M3dMath.mutl(points[i].getMatrix(),externalParam.getTransformMatrix());
            screenPoints[i]= new Point((int)newPoint.getMatrix()[0][0],(int)newPoint.getMatrix()[0][1]);
        }
    }

    public void addExternalParam(ExternalParam external){
        externalParam.setZoomMatrix(M3dMath.mutl(externalParam.getZoomMatrix(),external.getZoomMatrix()));
        externalParam.setRolateMatrix(M3dMath.mutl(externalParam.getRolateMatrix(),external.getRolateMatrix()));
        externalParam.setTranslateMatrix(M3dMath.mutl(externalParam.getTranslateMatrix(),external.getTranslateMatrix()));

        synchronized (screenPoints) {
            for (int i = 0; i < screenPoints.length; i++) {
                Matrix newPoint = M3dMath.mutl(points[i].getMatrix(), externalParam.getTransformMatrix());
                screenPoints[i] = new Point((int) newPoint.getMatrix()[0][0], (int) newPoint.getMatrix()[0][1]);
            }
        }
    }

    public void paint(Graphics g){
        synchronized (screenPoints){

            g.setColor(Color.BLUE);
            ((Graphics2D) g).setStroke(new BasicStroke(1f));
            for(int i = 0 ; i <index.size() ; i++){
                int[] xPoint = new int[index.get(i).length];
                int[] yPoint = new int[index.get(i).length];
                if(index.get(i).length == 2){
                    g.drawLine((int)screenPoints[index.get(i)[0]].getX(),(int)screenPoints[index.get(i)[0]].getY(),
                            (int)screenPoints[index.get(i)[1]].getX(),(int)screenPoints[index.get(i)[1]].getY());
                    continue;
                }
                for(int j=0; j< index.get(i).length;j++){
                    xPoint[j] = (int)screenPoints[index.get(i)[j]].getX();
                    yPoint[j] = (int)screenPoints[index.get(i)[j]].getY();
                }
                g.drawPolygon(xPoint,yPoint,xPoint.length);
                g.fillPolygon(xPoint,yPoint,xPoint.length);



                g.fillPolygon(new int[]{200,200,200},new int[]{200,200,200},3);
            }
        }
    }

    public synchronized void addMouseListener(MouseListener l) {
        if (l == null) {
            return;
        }
        mouseListener = AWTEventMulticaster.add(mouseListener,l);
        newEventsOnly = true;

        // if this is a lightweight component, enable mouse events
        // in the native container.

    }

}
