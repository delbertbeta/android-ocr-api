package cc.delbertbeta.ocr.controller;

import cc.delbertbeta.ocr.model.Result;
import cc.delbertbeta.ocr.tools.Filter;
import cc.delbertbeta.ocr.tools.Tesseract;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class Ocr {
    @RequestMapping("/ocr")
    @ResponseBody
    public Result handle(@RequestParam("file")MultipartFile file) throws IOException {
        byte[] fileByte = file.getBytes();

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(fileByte));

        ITesseract tessEngine = Tesseract.getInstance();
        String result = null;
        try {
            result = tessEngine.doOCR(image);
        } catch (TesseractException e) {
            result = "";
        }
//        result = Filter.getRecognizedText(result);
        return new Result(result, "");
    }
}
