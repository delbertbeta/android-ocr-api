package cc.delbertbeta.ocr.tools;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITesseract;

import java.io.File;

public class Tesseract {

    static public ITesseract getInstance() {
        if (instance == null) {
           instance = new net.sourceforge.tess4j.Tesseract();
           instance.setDatapath("eng_traineddata");
           instance.setLanguage("eng");
           instance.setTessVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
           instance.setTessVariable("tessedit_char_blacklist", "!@#$%^&*()_+=-[]}{;:'\"\\|~`,./<>?");
           instance.setPageSegMode(ITessAPI.TessPageSegMode.PSM_AUTO);
        }
        return instance;
    }

    static ITesseract instance;

}
