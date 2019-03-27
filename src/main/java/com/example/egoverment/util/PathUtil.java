package com.example.egoverment.util;

public class PathUtil {

    private static String separator = System.getProperty("file.separator");

    public static String getImgBasePath() {

        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "E:/project/image";
        } else {
            basePath = "/home/xiangze/image";
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }


}
