package net.miqiang.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author ThinkPad
 * Created : 2018-08-30 18:24
 * @Since
 */
public class TestGraphics {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(800,900);
        MyCanvas canvas = new MyCanvas();

        frame.add(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


}
class MyCanvas extends Canvas{
    @Override
    public void paint(Graphics g) {
        int width = getParent().getWidth();
        int height = getParent().getHeight();
        BufferedImage image = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i< height; i++){
                    synchronized (graphics) {
                        graphics.setColor(Color.BLUE);
                        graphics.drawLine(0, i, width / 2, i);
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {

                for(int i = 0; i< height; i++){
                    synchronized (graphics) {
                        graphics.setColor(Color.RED);
                        graphics.drawLine(width / 2, i, width, i);
                    }
                }
            }
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        g.drawImage(image,0,0,null);

    }
}