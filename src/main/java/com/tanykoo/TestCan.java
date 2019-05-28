package com.tanykoo;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

/**
 * @Author ThinkPad
 * Created : 2019-05-23 1:16
 * @Since
 */
public class TestCan extends JFrame {
    private Logger logger = Logger.getLogger("TestCan");

    public static void main(String[] args) {
        TestCan test = new TestCan();
        try {
            test.test2();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void test2() throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix matrix = qrCodeWriter.encode("http://www.dangdang.com",BarcodeFormat.QR_CODE,10,10);
        logger.info(matrix.toString());

        JFrame jFrame = new JFrame("二维码");
        jFrame.setSize(500,500);
        jFrame.setVisible(true);
        MyCanvas canvas = new MyCanvas();
        canvas.setSize(500,500);
        jFrame.add(canvas);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getGraphics().setColor(Color.red);
        jFrame.getGraphics().drawOval(3,3,40,40);
        canvas.matrix = matrix;

    }

    class MyCanvas extends Canvas{

        private BitMatrix matrix;

        @Override
        public void paint(Graphics g){
            super.paint(g);
            paintSeparator(g);
            paintQRCode(g);

        }

        public void paintSeparator(Graphics g){
            Graphics2D graphics2D = (Graphics2D)g;
            graphics2D.setColor(Color.lightGray);
            graphics2D.setStroke(new BasicStroke(1));
            graphics2D.drawRect(30,30,410,410);
        }
        public void paintQRCode(Graphics g){
            Graphics2D graphics2D = (Graphics2D)g;


            for(int x =0 ; x < matrix.getWidth();x++){
                graphics2D.setColor(new Color(0x701C18));
                for(int y =0 ; y < matrix.getHeight();y++){
                    if(matrix.get(x,y)) {
                        graphics2D.fillRect(30 + (x * 410 / matrix.getWidth()), 30 + (y * 410 / matrix.getHeight()), 410 / matrix.getWidth()+1, 410 / matrix.getHeight()+1);
                    }
                }

            }

        }

    }

}
