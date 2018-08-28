package com.tanykoo;

import com.tanykoo.m3d.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Author ThinkPad
 * Created : 2018-08-24 15:13
 * @Since
 */
public class Test extends JFrame{
    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        System.out.println(180/(Math.PI/Math.asin(1/Math.sqrt(2))));
        System.out.println(180/(Math.PI/Math.acos(1/-Math.sqrt(2))));

        JFrame frame = new JFrame();
        M3DPanel myPanel = new M3DPanel();
        myPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Image image = myPanel.getCurrentImage();
                try {
                    ImageIO.write((BufferedImage)image,"png",new File("e:/aaa.png"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println(e.getX());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println(e.getX());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println(e.getX());
            }
        });
        myPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println(e.getKeyCode());
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        Spirit spirit = new Spirit();

        Spirit spirit1 = new Spirit();
        ExternalParam externalParam = new ExternalParam();
        externalParam.setZoomMatrix(new ZoomParam().getMatrix());
        externalParam.setTranslateMatrix(new TranslateParam(new Coordinate3D(70,0,0)).getMatrix());
        externalParam.setRolateMatrix(new RolateParam(Math.PI).getMatrix());
        spirit1.addExternalParam(externalParam);

        myPanel.addSprint(spirit);
        myPanel.addSprint(spirit1);
        frame.add(myPanel);
        frame.setLocation(300,300);
        frame.setSize(300,300);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        myPanel.requestFocus();
        int x = 20;

        new Thread(new Runnable() {
            int times = 0;
            @Override
            public void run() {
                ExternalParam externalParam1 = new ExternalParam();
                externalParam1.setZoomMatrix(new ZoomParam().getMatrix());
                externalParam1.setTranslateMatrix(new TranslateParam(new Coordinate3D(0, 0, 0)).getMatrix());
                externalParam1.setRolateMatrix(new RolateParam(-0.02).getMatrix());

                while (true) {
                    try {
                        Thread.sleep(5);
                        spirit.addExternalParam(externalParam1);
                        ExternalParam externalParam2 = new ExternalParam();
                        externalParam2.setZoomMatrix(new ZoomParam().getMatrix());
                        externalParam2.setTranslateMatrix(new TranslateParam(new Coordinate3D((times > 300 ? -0.2 : 0.2), 0, 0)).getMatrix());
                        times = (++times) % 599;
                        externalParam2.setRolateMatrix(new RolateParam(0.02).getMatrix());
                        spirit1.addExternalParam(externalParam2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }


}




