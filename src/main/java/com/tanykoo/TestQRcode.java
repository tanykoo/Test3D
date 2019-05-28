package com.tanykoo;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Author ThinkPad
 * Created : 2019-05-26 15:55
 * @Since
 */
public class TestQRcode {
    private static Logger logger = LoggerFactory.getLogger(TestQRcode.class);

    public static void main(String[] args) throws Exception {
        String path = TestCamare.class.getResource("/1.png").getPath();
        BufferedImage image = ImageIO.read(new File(path));
//        image = rotateImage(image,180);
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        System.out.println(bitmap.getBlackMatrix());
//
//        QRCodeReader qrCodeReader = new QRCodeReader();
//        Result result = qrCodeReader.decode(bitmap);
//        logger.info(result.getText());
//        logger.info(result.getBarcodeFormat().name());
    }
}
