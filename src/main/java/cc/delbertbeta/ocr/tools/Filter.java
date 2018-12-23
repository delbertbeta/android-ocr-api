package cc.delbertbeta.ocr.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter {

    private static Pattern pattern = Pattern.compile("<em>(.*?)</em>");

    public static String getRecognizedText(String sParam){
        if(sParam.isEmpty()){
            return "";
        }

        Matcher matcher = pattern.matcher(sParam.trim());
        StringBuilder bf = new StringBuilder();
        while (matcher.find()) {
            String sample = matcher.group(1);
            if (!sample.trim().isEmpty()) {
                bf.append(sample).append("\n");
            }
        }
        int len = bf.length();
        if (len > 0) {
            bf.deleteCharAt(len - 1);
        }
        return bf.toString();
    }
}
