package cc.delbertbeta.ocr.tools;

import cc.delbertbeta.ocr.model.Result;
import com.github.jaiimageio.stream.RawImageInputStream;
import com.google.zxing.PlanarYUVLuminanceSource;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Decode {

    public Result decode(byte[] data, int width, int height, int left, int top, int canvasHeight, int canvasWidth) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);

        double angle = Math.toRadians(90);
        AffineTransform at = new AffineTransform();
        at.rotate(angle, height / 2.0, width / 2.0);
        at.translate(height / 2.0 - width / 2.0, width / 2.0 - height / 2.0);
        BufferedImage rotatedImage = new BufferedImage(height, width, image.getType());
        Graphics2D rotatedGrapgics = rotatedImage.createGraphics();
        rotatedGrapgics.setTransform(at);
        rotatedGrapgics.drawImage(image, null, null);

        rotatedImage = op.filter(rotatedImage, null);
        ImageFilter cropFilter = new CropImageFilter(left, top, canvasWidth, canvasHeight);
        Image img = Toolkit.getDefaultToolkit().createImage(
                new FilteredImageSource(rotatedImage.getSource(),
                        cropFilter));



        BufferedImage tag = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = tag.getGraphics();
        g.drawImage(img, 0, 0, canvasWidth, canvasHeight, null);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(tag, "png", byteArrayOutputStream);


        ITesseract tessEngine = Tesseract.getInstance();
        String result = null;
        try {
//            result = tessEngine.doOCR(canvasWidth, canvasHeight, ByteBuffer.wrap(bitmap), new Rectangle(0, 0, canvasWidth, canvasHeight), 32);
            result = tessEngine.doOCR(tag);
        } catch (
                TesseractException e) {
            e.printStackTrace();
            result = "";
        }

//        result = Filter.getRecognizedText(result);
        return new Result(result, new String(Base64.encodeBase64(byteArrayOutputStream.toByteArray())));
    }

}
