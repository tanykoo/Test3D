package com.tanykoo.m3d;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

public class M3DPanel extends Canvas {
    List<Spirit> spiritList = new ArrayList<>();
    float fps = 0;
    int times = 0;

    private BufferedImage currentImage ;


    public void addSprint(Spirit spirit){
        spiritList.add(spirit);
        spirit.setParent(this);
    }

    public M3DPanel(){
        super();
        this.setBackground(Color.BLACK);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    repaint();
                }
            }
        }).start();

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(currentImage,0,0,null);
    }

    @Override
    public void update(Graphics g) {
        long time1 = new Date().getTime();
        currentImage = createImage();
        paint(g);
        long time2 = new Date().getTime();
        if(times++ % 20 == 0) {
            fps = 1000f / (time2 - time1 + 2);
            times = times%20;
        }
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public void paintFps(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString(String.format("%.2f",fps),getWidth()-40, getHeight()-20);
    }


    public float getFps() {
        return fps;
    }

    public BufferedImage createImage(){
        BufferedImage image = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        for(Spirit spirit:spiritList){
            spirit.paint(graphics);
        }
        paintFps(graphics);
        return image;
    }


}