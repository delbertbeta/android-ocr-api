package cc.delbertbeta.ocr.controller;

import cc.delbertbeta.ocr.model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class Decode {
    @PostMapping("/decode")
    @ResponseBody
    public Result handleFileUpload(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        MultipartFile file = params.getFile("file");
        byte[] bytes = file.getBytes();
        int width = Integer.valueOf(params.getParameter("width"));
        int height = Integer.valueOf(params.getParameter("height"));
        int left = Integer.valueOf(params.getParameter("left"));
        int top = Integer.valueOf(params.getParameter("top"));
        int canvasHeight = Integer.valueOf(params.getParameter("canvasHeight"));
        int canvasWidth = Integer.valueOf(params.getParameter("canvasWidth"));
        cc.delbertbeta.ocr.tools.Decode decode = new cc.delbertbeta.ocr.tools.Decode();
        return decode.decode(bytes, width, height, left, top, canvasHeight, canvasWidth);
    }
}
