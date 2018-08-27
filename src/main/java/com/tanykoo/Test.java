package com.tanykoo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

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
        MyPanel myPanel = new MyPanel();
        frame.add(myPanel);
        frame.setLocation(300,300);
        frame.setSize(300,300);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);



    }


}
class MyPanel extends JPanel{

    @Override
    public void paint(Graphics g) {

        g.setColor(new Color(0,0,0,150));
        g.fillPolygon(new int[]{20,30,30},new int[]{10,10,40},3);
        g.fillPolygon(new int[]{30,30,50},new int[]{10,40,30},3);

        g.setColor(new Color(255,255,255,100));
        g.drawPolygon(new int[]{20,30,30},new int[]{10,10,40},3);
        g.drawPolygon(new int[]{30,30,50},new int[]{10,40,30},3);

        g.setColor(Color.BLUE);
        g.fillPolygon(new int[]{10,20,30},new int[]{10,10,40},3);
        g.fillPolygon(new int[]{20,30,50},new int[]{10,40,30},3);

        g.setColor(new Color(255,255,255,100));
        g.drawPolygon(new int[]{10,20,30},new int[]{10,10,40},3);
        g.drawPolygon(new int[]{20,30,50},new int[]{10,40,30},3);

    }
}