package com.tanykoo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author ThinkPad
 * Created : 2019-05-27 14:45
 * @Since
 */
public class TestColor {
    public static void main(String[] args) throws AWTException, IOException {
        Robot robot = new Robot();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        BufferedImage image = robot.createScreenCapture(new Rectangle(0,0,toolkit.getScreenSize().width,toolkit.getScreenSize().height));

        JFrame jFrame = new JFrame("拾色器");

        jFrame.setSize(200,200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
//        jFrame.setUndecorated(true);
        jFrame.setVisible(true);
        Graphics g = jFrame.getGraphics();

    }
}
