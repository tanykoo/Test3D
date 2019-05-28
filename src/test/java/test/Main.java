package test;

import com.jogamp.newt.awt.NewtCanvasAWT;
import com.jogamp.opengl.GLProfile;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        GLProfile glProfile = GLProfile.get(GLProfile.GL2);
//        GLCapabilities capabilities = new GLCapabilities(glProfile);
        // The canvas
        NewtCanvasAWT newtCanvasAWT = new NewtCanvasAWT();
//        newtCanvasAWT.setSize(300,200);
        //creating frame
        final JFrame frame = new JFrame ("straight Line");
        //adding canvas to frame
        frame.getContentPane().add(newtCanvasAWT);
        frame.setSize(frame.getContentPane().getPreferredSize());

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Graphics g = newtCanvasAWT.getGraphics();
        g.setColor(Color.red);
        g.drawLine(10,10,200,200);


    }
}